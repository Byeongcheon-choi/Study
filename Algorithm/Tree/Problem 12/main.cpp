#include <iostream>
#include <vector>
#include <queue>

using namespace std;

#define MAX 40002

vector<pair<int, int>> v[MAX];
queue<pair<int, int>> tree;
bool pass[MAX];
int Totallength[MAX];

int bfs(vector<pair<int, int>> v[MAX], int start, int finish) {
	memset(Totallength, 0, sizeof(Totallength));
	memset(pass, false, sizeof(pass));
	pass[start] = true;
	tree.push(make_pair(start,0));

	while (!tree.empty()) {
		int point = tree.front().first;
		int length = tree.front().second;

		tree.pop();
		for (pair<int,int> k : v[point]) {
			if (!pass[k.first] && k.second != finish) {
				pass[k.first] = true;
				Totallength[k.first] = length + k.second;
				tree.push(make_pair(k.first, length + k.second));
			}
		}

	}
	return Totallength[finish];
}
int main(void) {
	int l;
	cin >> l;
	for (int i = 0; i < l - 1; i++) {
		int a, b, dis;
		cin >> a >> b >> dis;
		v[a].push_back(make_pair(b, dis));
		v[b].push_back(make_pair(a, dis));
	}

	int m;
	cin >> m;
	for (int k = 0; k < m; k++) {
		int d, c;
		cin >>  c  >>  d;
		int result = 0;
		result = bfs(v, c, d);
		cout << result << endl;
	}




	return 0;
}
