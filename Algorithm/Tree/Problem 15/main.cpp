#include <cstdio>
#include <vector>
#include <cstring>
#include <algorithm>

using namespace std;
int N;
struct P {int inc, n_inc;};
const int INC = 1, N_INC = 2;
vector<int> ans;
short choice[10003];

P dfs(vector<int> G[], P arr[], int v, bool visited[]) { // 최대 독립집합의 가중치 합 구하기
    visited[v] = true;
    for(int adj:G[v]) { // v를 포함하지 않는 최대 독립 집합 -> 자식 노드들 포함/포함x
        if(!visited[adj]) {
            P tmp = dfs(G,arr,adj,visited);
            arr[v].inc += tmp.n_inc;
            arr[v].n_inc += max(tmp.n_inc, tmp.inc);
            // v를 포함하지 않는 최대 독립집합 -> 포함/포함x 경우의 최대 합
            if (arr[adj].inc > arr[adj].n_inc) choice[adj] = INC;
            else choice[adj] = N_INC;
            // 각 정점별 어떤 값을(포함/포함x) 취했는지 choice 배열에 저장
        }
    }
    return arr[v];
}

void chosen(vector<int> G[], bool visited[], int cur, int Choice) {
    // 정점별 choice를 이용해 최대 독립집합을 구하기
    visited[cur] = true;
    if (Choice == INC) {
        ans.push_back(cur);
        for (auto next : G[cur]) {
            if(!visited[next]) chosen(G, visited, next, N_INC); // 다음을 포함하지 않는 경우
            // choice[next]로 해도 무관
        }
    } else { // 현재 정점을 포함 하는 경우
        for(auto next : G[cur])
            if(!visited[next]) chosen(G, visited, next, choice[next]);
            // 각 정점의 선택을 따름
    }
}

int main() {
    scanf("%d",&N);
    P arr[N+1];
    for(int i=1;i<=N;++i)
        arr[i] = {1,0};
    for(int i=1;i<=N;++i)
        scanf("%d",&arr[i].inc);
    vector<int> G[N+1];
    for(int i=0;i<N-1;++i) {
        int u, v;
        scanf("%d%d",&u,&v);
        G[u].push_back(v);
        G[v].push_back(u);
    }

    bool visited[N+1];
    memset(visited,0, sizeof(visited));

    dfs(G,arr,1,visited); // 최대 독립 집합 구하기

    memset(visited,0, sizeof(visited));
    if (arr[1].inc > arr[1].n_inc) // 가중치의 합이 가장 큰 최대 독립 집합
        chosen(G,visited,1,INC);
    else
        chosen(G,visited,1,N_INC);
    printf("%d\n",max(arr[1].inc, arr[1].n_inc));
    sort(ans.begin(),ans.end());
    for(auto e:ans)
        printf("%d ",e);
}
