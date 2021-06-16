#!/usr/bin/python2.7
#
# Interface for the assignement
#

import psycopg2

def getOpenConnection(user='postgres', password='1234', dbname='postgres'):
    return psycopg2.connect("dbname='" + dbname + "' user='" + user + "' host='localhost' password='" + password + "'")

RoundIndex = 0
RangeIndex = 0


def loadRatings(ratingstablename, ratingsfilepath, openconnection):
    cur1 = openconnection.cursor() #create the table 
 
    cur1.execute("CREATE TABLE RATINGS( UserID integer, e1 char, MovieID integer, e2 char, Rating float, e3 char, timestamp bigint)") 
    
    with open(ratingsfilepath, 'r') as file1:   #Add the data to the table
        cur1.copy_from(file1, 'RATINGS', sep = ':')

    table11 = "ALTER TABLE RATINGS DROP COLUMN e1, DROP COLUMN e2, DROP COLUMN e3, DROP COLUMN timestamp" #delete data redundancy
    cur1.execute(table11)
    openconnection.commit()
    cur1.close()


def rangePartition(ratingstablename, numberofpartitions, openconnection):
    cur2 = openconnection.cursor()

    RangeIndex = numberofpartitions # set the Number of partition number for Insert function

    Psize = 5/numberofpartitions # set the range of partition
    Startpoint = 0

    for num in range(numberofpartitions):

        table2 = """CREATE TABLE range_part{}
                    (
                        UserID int,
                        MovieID int,
                        Rating float)
        """.format(num)

        cur2.execute(table2)

        if num == 0:
            fomula1 = "SELECT * FROM {} WHERE {}.Rating >= {} AND {}.Rating <= {}" .format(ratingstablename, ratingstablename,  Startpoint, ratingstablename,  Startpoint+Psize) # For the case of first of partition
            cur2.execute(fomula1)
        else:
            fomula2 = "SELECT * FROM {} WHERE {}.Rating > {} AND {}.Rating <= {}".format(ratingstablename, ratingstablename,  Startpoint, ratingstablename,  Startpoint+Psize)
            cur2.execute(fomula2)

        Startpoint = Startpoint+Psize

        tempFile = cur2.fetchall()

        for v in tempFile:
            adding = "INSERT INTO range_part{}(UserID, MovieID, Rating) VALUES ({},{},{})".format(num, v[0], v[1], v[2]) # ADD the row to each number of range_part table
            cur2.execute(adding)


    openconnection.commit()
    cur2.close()


def roundRobinPartition(ratingstablename, numberofpartitions, openconnection):
    cur3 = openconnection.cursor()
    RoundIndex = numberofpartitions # set the value for Round insert function

    for a in range(numberofpartitions):
        cur3.execute(
                """
                CREATE TABLE rrobin_part{} (
                    UserID int,
                    MovieID int,
                    Rating float
                )
                """.format(a)
            )

    Load = "SELECT * FROM {}".format(ratingstablename)
    cur3.execute(Load)
    tempFile1 = cur3.fetchall()

    for x,y in enumerate(tempFile1):
        group = x % numberofpartitions # Deside which table to add the row of tmepfile1
        adding1 = "INSERT INTO rrobin_part{}(UserID, MovieID, Rating) VALUES ({},{},{})".format(group, y[0], y[1], y[2])
        cur3.execute(adding1)
    openconnection.commit()
    cur3.close()


def roundrobininsert(ratingstablename, userid, itemid, rating, openconnection):
    cur4 = openconnection.cursor()

    currentState = 0
    insert1 = "INSERT INTO rrobin_part{}(UserID,MovieID,Rating) VALUES ({},{},{})".format(currentState,userid,itemid,rating)
    cur4.execute(insert1)
    currentState += 1
    if(currentState == RoundIndex):
        currentState = 0


    openconnection.commit()
    cur4.close()



def rangeinsert(ratingstablename, userid, itemid, rating, openconnection):
  
    cur5 = openconnection.cursor()

    Collection = "SELECT count(table_name) FROM information_schema.tables WHERE table_name LIKE 'range_part%'"
    cur5.execute(Collection)

    numP = int(cur5.fetchone()[0])
    RangeNumber = 5 / numP
    Psize = rating/RangeNumber
    if rating % RangeNumber == 0 and Psize !=0:
        Psize = Psize -1

    RangeIn = "INSERT INTO range_part{}(UserID, MovieID, Rating) VALUES ({},{},{})".format(Psize, userid, itemid, rating)
    cur5.execute(RangeIn)
    

    openconnection.commit()
    cur5.close()



def createDB(dbname='dds_assignment'):
    """
    We create a DB by connecting to the default user and database of Postgres
    The function first checks if an existing database exists for a given name, else creates it.
    :return:None
    """
    # Connect to the default database
    con = getOpenConnection(dbname='postgres')
    con.set_isolation_level(psycopg2.extensions.ISOLATION_LEVEL_AUTOCOMMIT)
    cur = con.cursor()

    # Check if an existing database with the same name exists
    cur.execute('SELECT COUNT(*) FROM pg_catalog.pg_database WHERE datname=\'%s\'' % (dbname,))
    count = cur.fetchone()[0]
    if count == 0:
        cur.execute('CREATE DATABASE %s' % (dbname,))  # Create the database
    else:
        print 'A database named {0} already exists'.format(dbname)

    # Clean up
    cur.close()
    con.close()

def deletepartitionsandexit(openconnection):
    cur = openconnection.cursor()
    cur.execute("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'")
    l = []
    for row in cur:
        l.append(row[0])
    for tablename in l:
        cur.execute("drop table if exists {0} CASCADE".format(tablename))

    cur.close()

def deleteTables(ratingstablename, openconnection):
    try:
        cursor = openconnection.cursor()
        if ratingstablename.upper() == 'ALL':
            cursor.execute("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'")
            tables = cursor.fetchall()
            for table_name in tables:
                cursor.execute('DROP TABLE %s CASCADE' % (table_name[0]))
        else:
            cursor.execute('DROP TABLE %s CASCADE' % (ratingstablename))
        openconnection.commit()
    except psycopg2.DatabaseError, e:
        if openconnection:
            openconnection.rollback()
        print 'Error %s' % e
    except IOError, e:
        if openconnection:
            openconnection.rollback()
        print 'Error %s' % e
    finally:
        if cursor:
            cursor.close()