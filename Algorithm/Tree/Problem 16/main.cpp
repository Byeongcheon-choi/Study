#include <iostream>

using namespace std;

#define MAX 10002

int list1[MAX], list2[MAX];

void tree(int start, int end, int root)
{
	for (int i = start; i < end; ++i) {
		if (list1[root] == list2[i]) {
			tree(start, i, root + 1);
			tree(i + 1, end, root + i - start + 1);
			cout << list1[root] << ' ';
		}
	}

}
int main(void) {
	int num;
	cin >> num;
	
	for (int i = 0; i < num; i++) {

		int node;
		cin >> node;

		memset(list1, 0, sizeof(list1));
		memset(list2, 0, sizeof(list2));

		for (int j = 0; j < node; j++)
		{
			cin >> list1[j];
		}
			
		for (int k = 0; k < node; k++)
		{
			cin >> list2[k];
		}
		
		cout << 'answer : ';
		tree(0, node, 0);
	}

	return 0;
}
