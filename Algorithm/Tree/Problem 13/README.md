# Problem : There is a road network consisting of N cities and N-1 roads connecting them. Every pair of cities has a unique path connecting them, and the length of each road is given as input. A total of K city pairs are given. At this time, write a program to find the length of the shortest road and the length of the longest road on the route connecting the two cities.

### Input : N is given in the first line. (2 ≤ N ≤ 100,000) The next N-1 lines are given three integers A, B, and C to represent roads. It means that there is a road of length C between A and B. The length of the road is a positive integer less than or equal to 1,000,000. On the next line, K is given. (1 ≤ K ≤ 100,000) The next K lines are given two different natural numbers D and E. In the route connecting D and E, the length of the shortest road and the length of the longest road can be calculated and output.

### Output : In the route connecting D and E in a total of K lines, the length of the shortest road and the length of the longest road are output.

#### EX)
###### input :                                 output:
###### 5
###### 2 3 100
###### 4 3 200
###### 1 5 150
###### 1 3 50
###### 3
###### 2 4
###### 3 5
###### 1 2

###### output:
###### 100 200
###### 50 150
###### 50 100
