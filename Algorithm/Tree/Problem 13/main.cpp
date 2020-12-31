#include <iostream>
#include <vector>

using namespace std;

#define MAX 100002

vector<pair<int,int>> v[MAX];
bool pass[MAX];
int parent[MAX]; 
int dep[MAX];
int Min[MAX], Max[MAX];


void dfs(int root, int depth, int distance){
    pass[root] = true;
    dep[root] = depth;

    for(pair<int,int> point : v[root]){
        if(!pass[point.first]){
            dfs(point.first, depth+1, distance+point.second);
            parent[point.first] = root; // save the data in array for decreasing time complex
            Min[point.first] = point.second;
            Max[point.first] = point.second;

        }
    }
}

pair<int,int> LCA(pair<int,int> result){
    int start = result.first;
    int fin = result.second;
    if(dep[start] > dep[fin])
    {
        swap(start, fin);
    }
    
    int r1 = 1e18;
    int r2 = -1e18;
    while(dep[start] != dep[fin])
   {
       r1 = max(r1, Max[start]);
       r2 = min(r2, Min[start]);
       start = parent[start];
   }
    if(start == fin) return make_pair(r1, r2);
    while(parent[start] != parent[fin]){ // does not include the distance when matching the parent node
        r2 = min({r2, Min[fin], Min[start]});
        r1 = max({r1, Max[fin], Max[start]});
        start = parent[start];
        fin = parent[fin];
    }
    
    int result1, result2;
    result1 = max({r1, Max[start], Max[fin]}); // Should be include the distance beteen last children and root
    result2= min({r2, Min[start], Min[fin]});
    return make_pair(result1, result2);
}
int main(void){
    
    int kase;
    cin >> kase;
    for(int i = 0; i < kase-1; i++){
        int a,b,length;
        cin >> a >> b >> length;
        v[a].push_back(make_pair(b,length));
        v[b].push_back(make_pair(a, length));
    }
    dfs(1,0,0);

    int lase;
    cin >> lase;
    for(int k = 0; k < lase ; k++){
        int c,d;
        cin >> c >> d;
        pair<int,int> result = make_pair(c, d);
        pair<int,int> finalResult = LCA(result);
        cout << finalResult.second << ' ' <<finalResult.first << endl;
    }
    
    return 0;
}
