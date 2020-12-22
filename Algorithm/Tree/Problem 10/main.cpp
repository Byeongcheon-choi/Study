#include <iostream>

using namespace std;

class NODE {
public:
	int data;
	NODE* right, * left;
	NODE(int n) : data(n), right(NULL), left(NULL)
	{

	}
};

void result(NODE* root) {
	if (root) {
		result(root->left);
		result(root->right);
		cout << root->data;
	}
}

int main(void) {

	int num;
	NODE *init = NULL;

	while (cin >> num) {
		NODE *temp = new NODE(num);

		if (init == NULL) {
			init = temp;
		}
		else {
			NODE* current = init;
			while (true) {
				if (current->data > temp->data) {
					if (current->left) {
						current = current->left;
					}
					else {
						current->left = temp;
						break;
					}
				}
				else {
					if (current->right) {
						current = current->right;
					}
					else {
						current->right = temp;
						break;
					}
					
				}
			}
		}

	}

	result(init);

	return 0;

}