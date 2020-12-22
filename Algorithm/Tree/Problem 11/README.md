# Problem : I try to draw a binary tree in a grid-shaped frame with numbered rows and columns according to the following rules. At this time, I try to draw according to the following rules.

# -Nodes at the same level in the binary tree are located on the same row.
# -There is only one node in a row.
# -Nodes in the left subtree of an arbitrary node are located in the column to the left of the node, and nodes in the right subtree are located in the column to the right of the node.
# -There are no nodes and no empty columns between the leftmost and right columns where the nodes are placed.
# When drawing a binary tree according to this rule, the width of each level is defined as 1 plus the value obtained by subtracting the column number of the leftmost node from the column number of the rightmost node among the nodes allocated to the level. The level of the tree is 1 at the top of the root node and increases by 1 at the bottom. 

### input : In the first line, an integer N (1 ≤ N ≤ 10,000) representing the number of nodes is given. In the next N lines, the node number and the number of the left child node and the right child node of the node are given in order for each line. Nodes are numbered from 1 to N, and if there are no children, -1 is given to the child node number.
### Output : On the first line, the level with the widest width and the width of the level are printed in order. When there are two or more levels with the widest width, the level with the smaller number is output.
