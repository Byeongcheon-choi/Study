#include <iostream>
#include <vector>


using namespace std;

#define MAX 100002

vector<int> inorder[MAX],postorder[MAX];


class NODE{
public:
    int root;
    NODE *right,*left;
    NODE(int num){
        root = num;
        right = NULL;
        left = NULL;
    }
};

NODE *final;

NODE* TREE(int i1, int i2, int p1, int p2){ //inorder start i1, finish i2, postorder start p1, finish p2
    int r = postorder[p2].front();
    int dis = inorder[r].front() - i1;
    if(p1 == p2) return new NODE(r);
    
    if(dis != 0) final->left = TREE(i1, i1-1+dis, p1, p1+dis-1);
    if(dis + i1 <=  p2-1 ) final->right = TREE(i1+dis, i2-1 , p1+dis+1, p2);
    
    return new NODE(r);
};


void result(NODE *final) {
    if (final == NULL || final->root == 0) return;
    cout << final->root << ' ';
    result(final->left);
    result(final->right);
}

int main(void){
    int kcase;
    cin >> kcase;
    

    for(int i = 0; i <kcase; i++ ){ // inorder, index = nodenum, value = length
        int n1;
        cin >> n1;
        inorder[n1].push_back(i);
    }
    
    for(int k = 0; k <kcase; k++ ){ //postorder, index = length, value = nodenum
        int n2;
        cin >> n2;
        postorder[k].push_back(n2);
    }
    
    TREE(0, kcase-1, 0, kcase-1);
    
    result(final);
    return 0;
}


