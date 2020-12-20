# Problem : The binary search tree is a binary tree that satisfies the following three conditions.

## -The key of all nodes in the left subtree of the node is less than the key of the node.
## -The key of all nodes in the right subtree of the node is greater than the key of the node.
## -The left and right subtrees are also binary search trees.

# The prefix traversal (root-left-right) visits the root, then the left subtree, and the right subtree, in order, and prints the key of the node. Backward traversal (left-right-root) outputs keys in the order of left subtree, right subtree, and root node. For example, the result of the preceding traversal of the binary search tree above is 50 30 24 5 28 45 98 52 60, and the result of the backward traversal is 5 28 24 45 30 60 52 98 50. Given the result of traversing a binary search tree, write a program that finds the result of traversing this tree backwards.

### input : The result of traversing the tree is given. The value of the key contained in the node is a positive integer less than 10^6. All values ​​are given one per line, and the number of nodes is less than 10,000. There is no node with the same key.


### Output : Outputs the result of traversing the binary search tree given as input, one per line.



##### EX)
##### input :				
##### 50
##### 30
##### 24
##### 5
##### 28
##### 45
##### 98
##### 52
##### 60



##### output:
##### 5
##### 28
##### 24
##### 45
##### 30
##### 60
##### 52
##### 98
##### 50
