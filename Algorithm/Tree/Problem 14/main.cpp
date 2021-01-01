#include <iostream>

using namespace std;

#define alphabet 25

class NODE{
public:
    
    NODE* c[alphabet];
    
    NODE(){
        for(int i = 0; i < alphabet; i++ ){
            c[i] = NULL;
        }
    }
    ~NODE(){
        for(int j = 0; j < alphabet; j++)
        {
            delete c[j];
        }
        
    }
    int convertNUMBER(const char con){
        int newone = towlower(con) - 'a';
        return newone;
    }
    void insert(const char *in){
        if(*in == '\0') return;
        
        int num = convertNUMBER(*in);
        
        if(c[num] == NULL){
            c[num] = new NODE();
        }
        return c[num] -> insert(in + 1);
        
    }
    bool finder(const char *out){
        int num1 = convertNUMBER(*out);
        if(*out == '\0') return true;
        if(c[num1] == NULL) return false;
        return c[num1]->finder(out+1);
        
    }
};

int main(void){
    
    NODE* result = new NODE;
    int a,b;
    int answer =0;
    
    cin >> a >> b;
    
    for(int k =0; k< a ;k ++){
        char ins[501];
        cin >> ins;
        result->insert(ins);
    }
    
    for(int h =0; h< b ;h ++){
        char outs[501];
        cin >> outs;
        bool turn = result->finder(outs);
        if(turn) answer++;
    }
    
    cout << answer;
    
    return 0;
}
