#include <iostream>

using namespace std;

class Node{
public:
    char data;
    Node *L, *R;
    Node(char Init)
    {
        data = Init;
        L = NULL;
        R = NULL;
    }
};
void Start(Node *n, char mid, char left, char right)
{
    if(n == NULL) return;
    
    if(n->data != mid)
    {
        Start(n->L, mid, left, right);
        Start(n->R, mid, left, right);
    }
    else{
        n->L = new Node(left);
        n->R = new Node(right);
        return;
    }
    
}
void result1(Node *n) {
    if (n == NULL || n->data == NULL) return;
    cout << n->data;
    result1(n->L);
    result1(n->R);
}
void result2(Node *n) {
    if (n == NULL || n->data == NULL) return;
    result2(n->L);
    cout << n->data;
    result2(n->R);
}
void result3(Node *n) {
    if (n == NULL || n->data == NULL) return;
    result3(n->L);
    result3(n->R);
    cout << n->data;
}

int main(){
    Node *n = new Node(NULL);
    int number;
    cin >> number;
    
    for(int i =0; i < number ; i++){
        char mid, left,right;
        cin >> mid >> left >> right;
        
        if (left == '.') left = NULL;
        
        if (right == '.') right = NULL;
        
        if (n->data == NULL) {
            n->data = mid;
            n->L = new Node(left);
            n->R = new Node(right);
        }
        else
        {
            Start(n,mid,left,right);
        }
    }
    
    result1(n);
    cout << endl;
    result2(n);
    cout << endl;
    result3(n);
    cout << endl;
    
    return 0;
}
