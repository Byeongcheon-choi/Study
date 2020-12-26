#include <iostream>
#include <vector>
using namespace std;

#define MAX 10002

vector<int> dis[MAX];

int orlength[10001];
int length = 0;

class NODE {
public:
	int root = 0;
	NODE* right, * left;
	NODE(int num) {
		root = num;
		right = NULL;
		left = NULL;
	}
};

void maketree(NODE* tree, int a, int b, int c) {
	if (tree == NULL) return;
	if (tree->root != a) {
		maketree(tree->left, a, b, c);
		maketree(tree->right, a, b, c);
		
	}
	else {
		tree->left = new NODE(b);
		tree->right = new NODE(c);

		return;

	
	}
}

void inorder(NODE* tree, int depth) {
	if (tree == NULL || tree->root == 0) return;
	depth++;
	inorder(tree->left, depth);

	dis[depth].push_back(tree->root);
	
	length++;
	orlength[tree->root] = length;
	
	inorder(tree->right, depth);
}

int main(void) {
	NODE* tree = new NODE(0);

	

	int kcase;
	cin >> kcase;
	for (int i = 0; i < kcase; i++) {
		int a, b, c;
		cin >> a >> b >> c;
		
		if (b == -1) b = NULL;
		if (c == -1) c = NULL;
		if (tree->root == 0)
		{
			tree->root = a;
			tree->left = new NODE(b);
			tree->right = new NODE(c);
		}
		else {
			maketree(tree, a, b, c);
		}
	}


	inorder(tree,0);

	int count = 1;
	int bigmax = 0;
	int point = 0;
	int small, big;



	while (count++)
	{
		if (dis[count].empty()) {
			break;
		}

		big = orlength[dis[count].back()];
		small = orlength[dis[count].front()]; 

		int compare = big - small;
		if (bigmax < compare) {
			bigmax = compare;
			point = count;
		}
	

	}

	cout << point << ' ' << bigmax+1;
	return 0;
}
