#!/usr/bin/python2.7
#
# Assignment3 Interface
#

import psycopg2
import os
import sys

# Donot close the connection inside this file i.e. do not perform openconnection.close()
def ParallelSort (InputTable, SortingColumnName, OutputTable, openconnection):
    #Implement ParallelSort Here.

    cur1 = openconnection.cursor()
    


    
    Phase1 = "SELECT * FROM %s ORDER BY %s" % (InputTable, SortingColumnName) # ORDER OF SPECIFIC COLUMN
    cur1.execute(Phase1)

    tempfile1 = cur1.fetchall() # COPY THE TABLE

    Phase2 = "CREATE TABLE %s(UserID INT, MovieID INT, Rating REAL)" % (OutputTable) #CREATE THE OUTPUT TABLE
    cur1.execute(Phase2)

    for a in tempfile1: #INSERT INTO EMPTY OUTPUT TABLE
        cur1.execute(
            "INSERT INTO %s(UserID, MovieID, Rating) VALUES (%s, %s, %s)" % (OutputTable, a[0], a[1], a[2])
        )




def ParallelJoin (InputTable1, InputTable2, Table1JoinColumn, Table2JoinColumn, OutputTable, openconnection):
    cur2 = openconnection.cursor()
    Phase3 = "CREATE TABLE %s AS SELECT * FROM %s INNER JOIN %s ON %s.%s = %s.%s"%(OutputTable, InputTable1, InputTable2, InputTable1, Table1JoinColumn, InputTable2, Table2JoinColumn)
    cur2.execute(Phase3)







################### DO NOT CHANGE ANYTHING BELOW THIS #############################


# Donot change this function
def getOpenConnection(user='postgres', password='1234', dbname='postgres'):
    return psycopg2.connect("dbname='" + dbname + "' user='" + user + "' host='localhost' password='" + password + "'")

# Donot change this function
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
    con.commit()
    con.close()
