# Problem : In the graph G(V, E), if all the vertex pairs in the subset S of vertices are not adjacent to each other (there is no edge connecting the vertex pairs), S is called an independent set. The size of the independent set refers to the number of vertices belonging to the independent set when no weight is given to the vertex, and is defined as the sum of the weights of the vertices belonging to the independent set when a weight is given to the vertex. When the independent set is an empty set, let's say its size is zero. The largest independent set is called the largest independent set. The problem is not a normal graph, but a tree (connected and cycleless graph) and the maximum independent set given the weights of each vertex are given as positive integers.

### Input : The first line is given the number of vertices n in the tree. n is a positive integer less than or equal to 10,000. Assume that an integer from 1 to n is the vertex of the tree. In the second line, n integers w1, w2, ..., wn are given, where wi is the weight of vertex i (1 ≤ i ≤ n). From the third line to the last line, an edge list is given, indicating one edge per line. Edges are given as pairs of vertices. There are no commas between input integers, and instead there is one or more spaces. The values ​​of the weights are natural numbers not exceeding 10,000.

### Output : On the first line, print the maximum independent set size. In the second line, vertices belonging to the largest independent set are displayed in ascending order. If there is more than one maximum independent set, only one needs to be output.

#### EX)
###### input :                                 
###### 7
###### 10 30 40 10 20 20 70
###### 1 2
###### 2 3
###### 4 3
###### 4 5
###### 6 2
###### 6 7

###### output:
###### 140
###### 1 3 5 7
