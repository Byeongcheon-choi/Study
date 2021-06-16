#!/usr/bin/python2.7
#
# Assignment2 Interface
#

import psycopg2
import os
import sys



# Donot close the connection inside this file i.e. do not perform openconnection.close()
def RangeQuery(ratingsTableName, ratingMinValue, ratingMaxValue, openconnection):
    cur1 = openconnection.cursor()

    #RangeRatingsMetadata (PartitionNum, MinRating, MaxRating)
    phase1 = "SELECT PartitionNum FROM RangeRatingsMetadata WHERE MinRating BETWEEN %s AND %s OR MaxRating BETWEEN %s AND %s" % (ratingMinValue, ratingMaxValue, ratingMinValue, ratingMaxValue)
    cur1.execute(phase1)

    temptable = cur1.fetchall()

    result1 = []

    for x in temptable:
        name1 = 'RangeRatingsPart' + str(x[0])
        phase2 = "SELECT * FROM %s WHERE Rating BETWEEN %s AND %s " % (name1, ratingMinValue, ratingMaxValue)
        cur1.execute(phase2)
        temptable1 = cur1.fetchall()
        for y in temptable1:
            result1.append([name1] + list(y))

    phase11 = "SELECT table_name FROM information_schema.tables WHERE table_name LIKE 'roundrobinratingspart%'"
    cur1.execute(phase11)

    temptable2 = cur1.fetchall()

    for a in temptable2:
        phase3 = "SELECT * FROM %s WHERE Rating BETWEEN %s AND %s " % (a[0], ratingMinValue, ratingMaxValue)
        cur1.execute(phase3)
        temptable3 = cur1.fetchall()
        for k in temptable3:
            result1.append([a[0]] + list(k))

    writeToFile("RangeQueryOut.txt", result1)


        



def PointQuery(ratingsTableName, ratingValue, openconnection):
    cur2 = openconnection.cursor()
    phase13 = "SELECT PartitionNum FROM RangeRatingsMetadata WHERE %s BETWEEN MinRating AND MaxRating" % (ratingValue)
    cur2.execute(phase13)

    temptable3 = cur2.fetchall()

    result2 = []

    for b in temptable3:
        name2 = 'RangeRatingsPart' + str(b[0])
        phase2 = "SELECT * FROM %s WHERE Rating =  %s " % (name2, ratingValue)
        cur2.execute(phase2)
        temptable31 = cur2.fetchall()
        for j in temptable31:
            result2.append([name2] + list(j))
            
    phase14 = "SELECT table_name FROM information_schema.tables WHERE table_name LIKE 'roundrobinratingspart%'"
    cur2.execute(phase14)

    temptable32 = cur2.fetchall()

    for s in temptable32:
        phase31 = "SELECT * FROM %s WHERE Rating = %s " % (s[0], ratingValue)
        cur2.execute(phase31)
        temptable33 = cur2.fetchall()
        for l in temptable33:
            result2.append([s[0]] + list(l))

    writeToFile("PointQueryOut.txt", result2)



def writeToFile(filename, rows):
    f = open(filename, 'w')
    for line in rows:
        f.write(','.join(str(s) for s in line))
        f.write('\n')
    f.close()