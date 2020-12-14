#include <iostream>
#include <queue>
#include <vector>

using namespace std;

#define MAX 1000001
vector<pair<int, int>> rout[MAX];


pair<int, int> BFS(vector<pair<int, int>> rout[MAX] ,int startPoint) {
    
    bool check[MAX];
    memset(check, false, MAX * sizeof(check[0]));
    queue<pair<int, int>> q;

    check[startPoint] = true;
    int fPoint = 0;
    int fLenght = 0;

    q.push(make_pair(startPoint, 0));


    while (!q.empty()) {
        int P = q.front().first;
        int D = q.front().second;
        q.pop();

        for (int i = 0; i < rout[P].size(); i++) {
            int Qpoint = rout[P][i].first;
            int QLength = rout[P][i].second;

            if (!check[Qpoint]) {
                check[Qpoint] = true;
                q.push(make_pair(Qpoint, D + QLength));

                if (D + QLength > fLenght) {
                    fLenght = D + QLength;
                    fPoint = Qpoint;
                }
            }
        }
    }

    return make_pair(fPoint, fLenght);
}



int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL); cout.tie(NULL);

    int a;
    cin >> a;

    for (int i = 1; i <= a; i++) {
        int num, Point;
        cin >> num;

        while (cin >> Point && Point != -1) {
            
            int length;
            cin >> length;

            rout[num].push_back(make_pair(Point, length));
        }
    }

    pair<int, int> x = BFS(rout, 1);
    pair<int, int> y = BFS(rout, x.first);

    cout << y.second;

    return 0;
}



