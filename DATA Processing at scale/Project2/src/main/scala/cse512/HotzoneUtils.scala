package cse512

object HotzoneUtils {

    def ST_Contains(queryRectangle: String, pointString: String ): Boolean = {

        val rectangleArr = queryRectangle.split(",")
        var x1 = rectangleArr(0).toDouble
        var y1 = rectangleArr(1).toDouble
        var x2 = rectangleArr(2).toDouble
        var y2 = rectangleArr(3).toDouble

        val pointArr = pointString.split(",")
        var x = pointArr(0).toDouble
        var y = pointArr(1).toDouble

        if (x >= x1 && x <= x2 && y >= y1 && y <= y2)
            return true
        else if (x >= x2 && x <= x1 && y >= y2 && y <= y1)
            return true
        else
            return false
    }

}