#include <iostream>
#include <algorithm>
#include <cmath>
#include <vector>
#include <set>
#include <map>
#define PLAYER 0
#define AGENT 1
using namespace std;
typedef struct node {
    int r, c;
} Cell;
int findscore(int init[3][3]) {
    int i, j, score = 0;
    bool flag1 = false, flag2 = false;
    for(i = 0; i < 3; i++) {
        if(init[i][0] == init[i][1] && init[i][1] == init[i][2]) {
            if(init[i][0] == AGENT) score = 10;
            else score = -10;
            flag1 = true;
            break;
        }
    }
    if(!flag1) {
        for(i = 0; i < 3; i++) {
            if(init[0][i] == init[1][i] && init[1][i] == init[2][i]) {
                if(init[0][i] == AGENT) score = 10;
                else score = -10;
                flag2 = true;
                break;
            }
        }
    }
    if(!flag1 && !flag2) {
        if(init[0][0] == init[1][1] && init[1][1] == init[2][2]) {
            if(init[0][0] == AGENT) score = 10;
            else score = -10;
        }
        else if(init[0][2] == init[1][1] && init[1][1] == init[2][0]) {
            if(init[0][2] == AGENT) score = 10;
            else score = -10;
        }
    }
    return score;
}
int minimax(int init[3][3], bool is_max) {
    int min1 = 100001, max1 = -100001;
    int i, j, val = findscore(init);
    if(val != 0) return val;
    bool flag = false;
    for(i = 0; i < 3; i++) {
        for(j = 0; j < 3; j++) {
            if(init[i][j] == -1) {
                flag = true;
                break;
            }
        }
        if(flag) break;
    }
    if(flag && is_max) {
        for(i = 0; i < 3; i++) {
            for(j = 0; j < 3; j++) {
                if(init[i][j] == -1) {
                    init[i][j] = AGENT;
                    max1 = max(max1, minimax(init, !is_max));
                    init[i][j] = -1;
                }
            }
        }
        return max1;
    }
    else if(flag && !is_max) {
        for(i = 0; i < 3; i++) {
            for(j = 0; j < 3; j++) {
                if(init[i][j] == -1) {
                    init[i][j] = PLAYER;
                    min1 = min(min1, minimax(init, !is_max));
                    init[i][j] = -1;
                }
            }
        }
        return min1;
    }
    else return 0;
}
Cell findmove(int init[3][3]) {
    Cell b; int i, j, max1 = -100001;
    b.r = -1, b.c = -1;
    for(i = 0; i < 3; i++) {
        for(j = 0; j < 3; j++) {
            if(init[i][j] == -1) {
                init[i][j] = AGENT;
                int val = minimax(init, false);
                init[i][j] = -1;
                if(max1 < val) {
                    max1 = val;
                    b.r = i; b.c = j;
                }
            }
        }
    }
    if(max1 == 10) cout<<"Best possible outcome: AGENT wins\n";
    else if(max1 == -10) cout<<"Best possible outcome: PLAYER wins\n";
    else cout<<"Best possible outcome: DRAW\n";
    return b;
}
int main() {
    int init[3][3], i, j;
    cout<<"Note: Input 1 for computer's move, 0 for player's move and -1 for blank\nEnter initial board\n";
    for(i = 0; i < 3; i++) {
        for(j = 0; j < 3; j++) cin>>init[i][j];
    }
    Cell b = findmove(init);
    cout<<"Best possible move: "<<b.r<<" "<<b.c<<endl;
    return 0;
}
