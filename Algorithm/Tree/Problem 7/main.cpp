#include <iostream>
#include <queue>
using namespace std;

queue<int> q;

int main(void) {
	int a;
	cin >> a;
	for (int i = 0; i < a; i++)
	{
		int s, d;
		cin >> s >> d;
		for (int k = 0; k < d; k++) {
			int first, final;
			cin >> first >> final;
		}
		q.push(s - 1);
	}

	for (int w = 0; w < a; w++) {
		int result = q.front();
		cout << result << endl;
		q.pop();
	}
	
	return 0;
}