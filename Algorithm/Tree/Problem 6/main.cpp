#include <iostream>
#include <string>
#include <queue>

using namespace std;

#define MAX 10001
#define number 10

class Finder {
private:
	Finder* c[number];
public:
	Finder() {
		for (int i = 0; i < number; i++) {
			c[i] = NULL;
		}
	}
	~Finder() {
		for (int i = 0; i < number; i++) {
			delete c[i];
		}
	}

	int convertNumber(char a) {
		int s = tolower(a) - '0';
		return s;
	}

	void insert(const char* a) {
		if (*a == '\0')        
			return;

		int next = convertNumber(*a);

		if (c[next] == NULL) {
			c[next] = new Finder();
		}
		c[next]->insert(a + 1);
	}

	bool find(const char* a) {
		int next = convertNumber(*a);

		if (*a == '\0')
			return true;

		if (c[next] == NULL)
			return false;

		return c[next]->find(a + 1);
	}

};

int main(void) {
	int Scase;
	cin >> Scase;
	Finder finder;
	queue<string> q;

	for (int i = 0; i < Scase; i++) {
		int Nline;
		char a[MAX][100];
		int flag = 1;

		cin >> Nline;
		for (int k = 0; k < Nline; k++)
		{
			cin >> a[k];	
		}
		for (int j = 1; j < Nline; j++) {
			finder.insert(a[j]);

			if (finder.find(a[0])) {
				flag = 0;
				break;
			}		
		}
		if (flag == 0) q.push("NO");
		else q.push("YES");
	}
	while (!q.empty()) {
		string s = q.front();
		cout << s << endl;
		q.pop();
	}
	return 0;
}