#include <iostream>
#include <list>
#include <queue>

using namespace std;

#define MAX 100001

list<int> tree[MAX];
bool check[MAX];
int fnumber;
bool flag;

int BFS(int a, int delet) {
	
	check[a] = true;
	queue<int> q;
	q.push(a);
	
	while (!q.empty())
	{
		int point = q.front();
		q.pop();
		flag = true ;
		for(int i : tree[point])
			if (!check[i] && i != delet)
			{
				check[i] = true;
				q.push(i);
				flag = false;
			}
		if (flag) fnumber++;
	}

	return fnumber;
}

int main(void)
{
	int a;
	cin >> a;
	int startPoint;
	for (int i = 0; i < a; i++) {
		int k;
		cin >> k;
		if (k == -1) {
			startPoint = i;
		}
		else {
			tree[k].push_back(i);
			tree[i].push_back(k);
		}
		
	}

	int delet;
	cin >> delet;

	if (startPoint == delet) {
		cout << 0;
		return 0;
	}

	int result = BFS(startPoint, delet);

	cout << result;
	return 0;
}