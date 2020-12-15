## A tree is an undirected graph with no cycles. In the tree, no matter which node is selected, there will always be only one path between them. When selecting any two nodes in the tree and pulling them to either side, there will be a case of the longest stretch. In this case, all the nodes of the tree are placed in a circle with these two nodes as the end points of the diameter. The length of the path between these two nodes is called the diameter of the tree. To be precise, it is the length of the longest of all paths in the tree. Write a program that calculates the diameter of the tree and outputs it when you give a tree with a root as input as weighted edges.
### Input :The first line of the file is the number of nodes n (1 ¡Â n ¡Â 10,000). From the second line, n-1 lines contain information about each edge. Information about the trunk line consists of three integers. The first integer represents the number of the parent node among the two nodes connected by the edge, the second integer represents the child node, and the third integer represents the weight of the edge. For information on trunk lines, those with a smaller parent node number are input first, and if the parent node number is the same, those with a smaller child node number are input first. It is assumed that the number of the root node is always 1, and the weight of the edge is a positive integer not greater than 100.
### Output : Print the tree diameter on the first line.

##### EX)
##### INPUT:                         
##### 12
##### 1 2 3
##### 1 3 2
##### 2 4 5
##### 3 5 11
##### 3 6 9
##### 4 7 1
##### 4 8 7
##### 5 9 15
##### 5 10 4
##### 6 11 6
##### 6 12 10


##### OUTPUT :
##### 45