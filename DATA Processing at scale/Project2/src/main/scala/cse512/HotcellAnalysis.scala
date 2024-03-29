package cse512

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.udf
import org.apache.spark.sql.functions._

object HotcellAnalysis {
  Logger.getLogger("org.spark_project").setLevel(Level.WARN)
  Logger.getLogger("org.apache").setLevel(Level.WARN)
  Logger.getLogger("akka").setLevel(Level.WARN)
  Logger.getLogger("com").setLevel(Level.WARN)

  def runHotcellAnalysis(spark: SparkSession, pointPath: String): DataFrame = {
    // Load the original data from a data source
    var pickupInfo = spark.read.format("com.databricks.spark.csv").option("delimiter", ";").option("header", "false").load(pointPath);
    pickupInfo.createOrReplaceTempView("nyctaxitrips")
    pickupInfo.show()

    // Assign cell coordinates based on pickup points
    spark.udf.register("CalculateX", (pickupPoint: String) => ((
      HotcellUtils.CalculateCoordinate(pickupPoint, 0)
      )))
    spark.udf.register("CalculateY", (pickupPoint: String) => ((
      HotcellUtils.CalculateCoordinate(pickupPoint, 1)
      )))
    spark.udf.register("CalculateZ", (pickupTime: String) => ((
      HotcellUtils.CalculateCoordinate(pickupTime, 2)
      )))
    pickupInfo = spark.sql("select CalculateX(nyctaxitrips._c5),CalculateY(nyctaxitrips._c5), CalculateZ(nyctaxitrips._c1) from nyctaxitrips")
    var newCoordinateName = Seq("x", "y", "z")
    pickupInfo = pickupInfo.toDF(newCoordinateName: _*)
    pickupInfo.show()

    // Define the min and max of x, y, z
    val minX = -74.50 / HotcellUtils.coordinateStep
    val maxX = -73.70 / HotcellUtils.coordinateStep
    val minY = 40.50 / HotcellUtils.coordinateStep
    val maxY = 40.90 / HotcellUtils.coordinateStep
    val minZ = 1
    val maxZ = 31
    val numCells = ((maxX - minX + 1) * (maxY - minY + 1) * (maxZ - minZ + 1)).toDouble

    //CREATE the table with constrain
    val MainTable = pickupInfo.select("x", "y", "z").where("x >= " + minX + " AND y >= " + minY + " AND z >= " + minZ + " AND x <= " + maxX + " AND y <= " + maxY + " AND z <= " + maxZ)
    val calculateTable = MainTable.groupBy("x", "y", "z").count().withColumnRenamed("count", "pcount") // group point for remove duplicated point and count it

    val fomulaTable = calculateTable.agg(sum("pcount"),pow(sum("pcount"), 2))
    val averagePoint = fomulaTable.first().getLong(0).toDouble / numCells
    val stdPoint = math.sqrt((fomulaTable.first.getDouble(1).toDouble / numCells ) - (math.pow(averagePoint,2)))

    calculateTable.createOrReplaceTempView("temp1")
    var findadjacent = spark.sql("SELECT h1.x AS x, h1.y AS y, h1.z AS z, "
        + "sum(h2.pcount) AS pcountSum "
        + "FROM temp1 AS h1, temp1 AS h2 "
        + "WHERE (h2.y = h1.y+1 OR h2.y = h1.y OR h2.y = h1.y-1) AND (h2.x = h1.x+1 OR h2.x = h1.x OR h2.x = h1.x-1) AND (h2.z = h1.z+1 OR h2.z = h1.z OR h2.z = h1.z-1)"
        + "GROUP BY h1.z, h1.y, h1.x "
        + "ORDER BY h1.z, h1.y, h1.x" )
 
    //create a function for checking adjacent point
    var calculateNumberAdj = udf((minX: Int, minY: Int, minZ: Int, maxX: Int, maxY: Int, maxZ: Int, X: Int, Y: Int, Z: Int) => HotcellUtils.AdjacentPoint(minX, minY, minZ, maxX, maxY, maxZ, X, Y, Z))
    //add the number of adjacent point
    var adjacentresult = findadjacent.withColumn("adresultpoint", calculateNumberAdj(lit(minX), lit(minY), lit(minZ), lit(maxX), lit(maxY), lit(maxZ), col("x"), col("y"), col("z")))
    
    //create the function for calculating gscore
    var gScore = udf((numCells: Double , adresultpoint: Int, pcountSum: Long, averagePoint: Double, stdPoint: Double) => HotcellUtils.GScoreFunction(numCells, adresultpoint, pcountSum, averagePoint, stdPoint))
    // add the gscore result
    var gScoreresult = adjacentresult.withColumn("gScore", gScore(lit(numCells), col("adresultpoint"), col("pcountSum"), lit(averagePoint), lit(stdPoint))).orderBy(desc("gScore")).limit(50)
    

    var finalResult1 = gScoreresult.coalesce(1).select(col("x"), col("y"), col("z")) // create the result table
    finalResult1
  }
}
