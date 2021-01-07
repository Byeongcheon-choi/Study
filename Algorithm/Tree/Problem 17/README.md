# Problem : Satisfying the following three conditions, it attempts to select some of the N villages as'Excellent Villages'. The sum of the number of villagers selected as'Excellent Villages' should be maximized. In order to avoid conflict between villages, if two villages are adjacent, neither village can be selected as an'excellent village'. In other words,'excellent villages' cannot be adjacent to each other. In order to arouse awareness of a village that has not been selected, a village that has not been selected as an'excellent village' must be adjacent to at least one'excellent village'. Given the number of residents of each village and information about the roads between villages, write a program that selects the'best villages' to satisfy the given conditions.


## Input : The integer N is given on the first line. (1≤N≤10,000) In the second line, N natural numbers representing the number of villagers are given with blank spaces in between. It is given in order from village 1 to village N, and the number of residents is 10,000 or less. From the third line, the numbers of two adjacent villages are given across N-1 lines, with a blank space between them.

### Output : In the first line, the total number of residents of the'Excellent Village' is printed.

##### ex) Input:
##### 7
##### 1000 3000 4000 1000 2000 2000 7000
##### 1 2
##### 2 3
##### 4 3
##### 4 5
##### 6 2
##### 6 7
##### output:
##### 14000
