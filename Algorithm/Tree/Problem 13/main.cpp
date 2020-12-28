#include <iostream>
#include <vector>
#include <queue>

using namespace std;

#define MAX 100002
vector<pair<int,int>> v[MAX];
queue<pair<int,int>> r;
bool pass[MAX];


pair<int,int> BFS(vector<pair<int,int>> v[MAX], int start, int finish)
{
    memset(pass, false, sizeof(pass));
    queue<pair<int,int>> route;
    int small = v[start].front().second;
    int big = v[start].front().second;
    pass[start] = true;
    route.push(v[start].front());
    cout << "start : "<< strat << endl;
    while(!route.empty()){
        int point = route.front().first;
        int distance = route.front().second;
        cout << "distance : "<<distance << endl;
        if(small > distance) small = distance;
        if(big < distance) big = distance;
        if(point == finish) break;
        route.pop();
        cout << "point : "<< point << endl;
        for(pair<int,int> d : v[point]){
            cout << "second point : "<< d.first << endl;
            if(!pass[d.first]){
                pass[d.first] = true;
                route.push(d);
            }
            
        }
    }
    
    cout << "raw     " << small << "   " << big << endl;
    
    return make_pair(small, big);
}
int main(void){
    int num;
    cin >> num;
    
    for(int i=0; i < num-1; i++){
        int a,b, length;
        cin >> a >> b >> length;
        v[a].push_back(make_pair(b, length));
        v[b].push_back(make_pair(a, length));
    }
    int kase;
    cin >> kase;
    
    for(int j =0 ; j <kase; j++){
        int c, d;
        cin >> c >> d;
        pair<int,int> result = BFS(v, c, d);
        r.push(result);
    }
    
    
    for (int k = 0; k < kase; k++) {
        cout << r.front().first <<  " " <<r.front().second << endl;
        r.pop();
    }
    
    return 0;
    
}
