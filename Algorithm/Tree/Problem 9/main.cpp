#include <iostream>
#include <vector>

using namespace std;

#define MAX 50001

vector<int> v[MAX];
int dep[MAX];
int parent[MAX];

void BFS(int root, int length)
{
	dep[root] = length++;
	for (int s:v[root]) {
		if (!dep[s]) {
			parent[s] = root;
			BFS(s, length);
		}
		
	}
}

int main(void) {
	int num;
	cin >> num;
	for (int i = 0; i < num-1; i++)
	{
		int a, b;
		cin >> a >> b;
		v[a].push_back(b);
		v[b].push_back(a);

	}

	BFS(1, 1);

	int goal;
	cin >> goal;

	for (int i = 0; i < goal; i++) {
		int c, d;
		cin >> c >> d;
		if (dep[c] < dep[d])
		{
			swap(c, d);
		}
	
		while (dep[c] != dep[d]) c = parent[c];
		while (c != d) c = parent[c], d = parent[d];
		cout << c << endl;
	}

	return 0;
}