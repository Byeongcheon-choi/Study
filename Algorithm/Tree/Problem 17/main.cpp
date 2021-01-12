#include <iostream>
#include <vector>

#define MAX 10002

int node[MAX];

using namespace std;

vector<int> v[MAX];
bool pass[MAX];
bool choice[MAX];

class NODE {
public:
	int inclu, noclud;
};

NODE tree(NODE * ex, int start) {
	pass[start] = true;
	for (int brige : v[start]) {
		if (!pass[brige])
		{
			NODE temp = tree(ex, start);
			ex[start].inclu += temp.noclud;
			ex[start].noclud += max(temp.inclu, temp.noclud);

			if (temp.inclu > temp.noclud) choice[brige] = true;
		}
	}
	return ex[start];
}



int main(void) {
	int num;
	cin >> num;
	NODE* init = new NODE[num];
	memset(pass, false, sizeof(pass));
	memset(choice, false, sizeof(pass));

	for (int i = 0; i < num; i++) {
		cin >> node[i];
	}

	for (int k = 0; k < num - 1; k++) {
		int a, b;
		cin >> a >> b;
		v[a].push_back(b);
		v[b].push_back(a);
	}

	tree(init,1);
	int summ = 0;
	for (int result = 0; result < num; result++) {
		if (choice[result]) summ += node[result];
	}
	cout << summ;
	return 0;
}
