#include <iostream>
#include <vector>
#include <queue>

using namespace std;

#define MAX 100001

vector<pair<int, int>> tree[MAX];
bool check[MAX];

pair<int, int> BFS(int a) {
	int maxLen = 0;
	int maxPoint = 0;
	queue<pair<int, int>> q;
	q.push(make_pair(a, 0));
	memset(check, false, sizeof(check));
	check[a] = true;

	while (!q.empty()) {
		int point = q.front().first;
		int leng = q.front().second;
		
		q.pop();
		
		for (int i = 0; i < tree[point].size(); i++) {
			if (!check[tree[point][i].first]) {
				
				check[tree[point][i].first] = true;
				q.push(make_pair(tree[point][i].first, leng + tree[point][i].second));

				if (maxLen < leng + tree[point][i].second) {
					maxLen = leng + tree[point][i].second;
					maxPoint = tree[point][i].first;
				}
			}
		}
	}

	cout << maxLen << "              "  << maxPoint << "     ";
	return make_pair(maxPoint,maxLen);
}
int main(void) {
	int a;
	cin >> a;

	for (int i = 0; i < a-1; i++) {
		int parent, child, len;
		cin >> parent >> child >> len;

		tree[parent].push_back(make_pair(child, len));
		tree[child].push_back(make_pair(parent, len));
	}
	
	pair<int, int> result = BFS(1);
	pair<int, int> fresult = BFS(result.first);

	cout << fresult.second;

	return 0;
}