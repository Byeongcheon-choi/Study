#include <iostream>
#include <vector>
#include <queue>

using namespace std;

#define number 100001

vector<int> EX[number];
queue<int> result;
int path[number];
bool Pass[number];

void Start(vector<int> EX[number]) {
	path[1] = 0;
	Pass[1] = true;
	result.push(1);

	while (!result.empty())
	{
		int k = result.front();
		result.pop();
		for (int i : EX[k]) {
			if (!Pass[i]) {
				Pass[i] = true;
				path[i] = k;
				result.push(i);
			}
		}
	}

}

int main(void) {

	int a;
	cin >> a;

	for (int i = 1; i < a; i++) {
		int first, second;
		cin >> first >> second;

		EX[first].push_back(second);
		EX[second].push_back(first);
	}

	Start(EX);

	for (int i = 2; i <= a; i++)
		cout << path[i] << "\n";

	return 0;
}