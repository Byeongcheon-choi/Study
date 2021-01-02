#include <cstdio>
#include <vector>
#include <cstring>
#include <algorithm>

using namespace std;

const int INC = 1, N_INC = 2;
vector<int> ans;
short choice[10003];

class P {
public :
    int inc, n_inc;
};

P dfs(vector<int> G[], P arr[], int v, bool visited[]) { 
    visited[v] = true;
    for(int adj:G[v]) { 
        if(!visited[adj]) {
            P tmp = dfs(G,arr,adj,visited);
            arr[v].inc += tmp.n_inc;
            arr[v].n_inc += max(tmp.n_inc, tmp.inc);
        
            if (arr[adj].inc > arr[adj].n_inc) choice[adj] = INC;
            else choice[adj] = N_INC;

        }
    }
    return arr[v];
}

void chosen(vector<int> G[], bool visited[], int cur, int Choice) {

    visited[cur] = true;
    if (Choice == INC) {
        ans.push_back(cur);
        for (auto next : G[cur]) {
            if(!visited[next]) chosen(G, visited, next, N_INC); /
        }
    } else { 
        for(auto next : G[cur])
            if(!visited[next]) chosen(G, visited, next, choice[next]);
           
    }
}

int main() {
    int N;
    cin >> N;
  
    P arr[N+1];
    for(int i=1;i<=N;++i)
        arr[i] = {1,0};
    
    for(int i=1;i<=N;++i)
        cin >> arr[i].inc;
    
    vector<int> G[N+1];
    
    for(int i=0;i<N-1;++i) {
        int u, v;
        cin >> u >> v;
        G[u].push_back(v);
        G[v].push_back(u);
    }

    bool visited[N+1];
    memset(visited,0, sizeof(visited));

    dfs(G,arr,1,visited); 

    memset(visited,0, sizeof(visited));
    
    if (arr[1].inc > arr[1].n_inc) 
        chosen(G,visited,1,INC);
    else
        chosen(G,visited,1,N_INC);
    cout << max(arr[1].inc, arr[1].n_inc) << endl;
    sort(ans.begin(),ans.end());
    for(auto e:ans)
        cout << e << ' ';
}
