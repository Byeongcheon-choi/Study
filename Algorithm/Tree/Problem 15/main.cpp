#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;
#define MAX 10000

vector<int> v[MAX];
vector<int> re;

class NODE {
public:
	int accept, nonaccept;
};

bool pass1[MAX] , pass2[MAX], c[MAX];

NODE DFS(NODE* ex, vector<int> v[MAX], int start) {
	pass1[start] = true;
	for (int x : v[start]) {
		if (!pass1[x]) {
			NODE t = DFS(ex, v, x);
			ex[start].accept += t.nonaccept;
			ex[start].nonaccept += max(t.nonaccept, t.accept);

			if (ex[start].accept > ex[start].nonaccept) c[start] = true;
			else c[start] = false;
		}
	}

	return ex[start];
}

void result(vector<int> v[MAX], int top, bool select);

int main(void) {
	int num;
	cin >> num;

	NODE *ex = new NODE[num];

	for (int i = 0; i < num; i++) {
		int w;
		cin >> w;

		ex[i].accept = w;
	}

	for (int j = 0; j < num - 1; j++) {
		int a, b;
		cin >> a >> b;
		v[a].push_back(b);
		v[b].push_back(a);
	}

	DFS(ex, v, 1);

	if (ex[1].accept > ex[1].nonaccept) {
		cout << ex[1].accept;
		result(v, 1, true);
	}
	else
	{
		cout << ex[1].nonaccept;
		result(v, 1, false);
	}
	sort(re.begin(), re.end());
	for (int d : re) {
		cout << d << " ";
	}
	return 0;
}

void result(vector<int> v[MAX], int top, bool select) {
	pass2[top] = true;
	if (select) {
		re.push_back(top);
		for (int k : v[top]) {
			if (!pass2[k]) {
				result(v, k, c[k]);
			}
		}
	}
	else {
		for (int h : v[top]) {
			if (!pass2[h]) {
				result(v, h, c[h]);
			}
		}
	}
}
