/*
** Author: Kiner Shah
*/
#include <iostream>
#include <vector>
#include <cmath>
#include <algorithm>
#include <map>
#include <set>
#define INIT 0
#define UP 1
#define DOWN 2
#define LEFT 3
#define RIGHT 4
using namespace std;
class AStar {
    int init[9];
    int fin[9];
    int mapping[9][3];
    int heuristic_val;
protected:
    bool checkSolvable();
    set<int> findmoves(int x, int y, int prev);
public:
    int heuristic();
    AStar(int a[9], int b[9]);
    void solve();
};
AStar::AStar(int a[9], int b[9]) {
    int i;
    for(i = 0; i < 9; i++) {
        this->init[i] = a[i];
        this->fin[i] = b[i];
    }
    this->mapping[0][0] = 0; this->mapping[0][1] = 0; this->mapping[0][2] = 0;
    this->mapping[1][0] = 1; this->mapping[1][1] = 0; this->mapping[1][2] = 1;
    this->mapping[2][0] = 2; this->mapping[2][1] = 0; this->mapping[2][2] = 2;
    this->mapping[3][0] = 3; this->mapping[3][1] = 1; this->mapping[3][2] = 0;
    this->mapping[4][0] = 4; this->mapping[4][1] = 1; this->mapping[4][2] = 1;
    this->mapping[5][0] = 5; this->mapping[5][1] = 1; this->mapping[5][2] = 2;
    this->mapping[6][0] = 6; this->mapping[6][1] = 2; this->mapping[6][2] = 0;
    this->mapping[7][0] = 7; this->mapping[7][1] = 2; this->mapping[7][2] = 1;
    this->mapping[8][0] = 8; this->mapping[8][1] = 2; this->mapping[8][2] = 2;
}
bool AStar::checkSolvable() {
    int i, j, sum1 = 0, sum2 = 0;
    for(i = 1; i < 9; i++) {
        if(this->init[i] == 0) continue;
        for(j = 0; j < i; j++) {
            if(!this->init[j] == 0 && this->init[i] < this->init[j]) sum1++;
        }
    }
    for(i = 1; i < 9; i++) {
        if(this->fin[i] == 0) continue;
        for(j = 0; j < i; j++) {
            if(!this->fin[i] == 0 && this->fin[i] < this->fin[j]) sum2++;
        }
    }
    if(sum1 % 2 == sum2 % 2) return true;
    else return false;
}
int AStar::heuristic() {
    int i, k, j, sum = 0;
    for(i = 0; i < 9; i++) {
        for(j = 0; j < 9; j++) {
            if(this->fin[j] == this->init[i]) break;
        }
        int pos1 = i, pos2 = j, pos1x = 0, pos1y = 0, pos2x = 0, pos2y = 0;
        pos1x = this->mapping[pos1][1]; pos1y = this->mapping[pos1][2];
        pos2x = this->mapping[pos2][1]; pos2y = this->mapping[pos2][2];
        sum += abs(pos1x - pos2x) + abs(pos1y - pos2y);
    }
    return sum;
}
set<int> AStar::findmoves(int x, int y, int prev) {
    set<int> s;
    s.insert(UP); s.insert(DOWN); s.insert(LEFT); s.insert(RIGHT);
    s.erase(prev - 1);
    if(x == 0 && y == 0) { s.erase(UP); s.erase(LEFT); }
    else if(x == 0 && y == 1) { s.erase(UP); }
    else if(x == 0 && y == 2) { s.erase(RIGHT); s.erase(UP); }
    else if(x == 1 && y == 0) { s.erase(LEFT); }
    else if(x == 1 && y == 2) { s.erase(RIGHT); }
    else if(x == 2 && y == 0) { s.erase(DOWN); s.erase(LEFT); }
    else if(x == 2 && y == 1) { s.erase(DOWN); }
    else if(x == 2 && y == 2) { s.erase(DOWN); s.erase(RIGHT); }
    return s;
}
inline void swap(int &a, int &b) { int t = a; a = b; b = t; }
void AStar::solve() {
    //cout<<this->heuristic()<<endl;
    int no = 0, i;
    int val = this->heuristic() + no;
    if(!this->checkSolvable()) { cout<<"Goal state not reachable\n"; exit(1); }
    vector<pair<int*, int>> v;
    int t[9];
    v.push_back(make_pair(this->init, val));
    while(!v.empty()) {
        // find pos of 0 and map it to (x,y) coordinates
        // find all possible moves for 0 pos
        no++;
        if(v.size() == 1) {
            pair<int*, int> p = v[0];
            v.erase(v.begin());
        }
        for(i = 0; i < 9; i++) {
            if(this->init[i] == 0) break;
        }
        int zposx = this->mapping[i][1], zposy = this->mapping[i][2];
        //if(p.second == INIT) {
            set<int> temp = findmoves(zposx, zposy, INIT);
            for(int move : temp) {
                if(move == UP) swap(this->init[i], this->init[i - 3]);
                else if(move == DOWN) swap(this->init[i], this->init[i + 3]);
                else if(move == LEFT) swap(this->init[i], this->init[i - 1]);
                else if(move == RIGHT) swap(this->init[i], this->init[i + 1]);
                int heur = this->heuristic() + no;
                pair<int*, int> tt; tt.second = heur;
                tt.first = new int[9];
                for(int k = 0; k < 9; k++) tt.first[k] = this->init[k];
                v.push_back(tt);
                //for(int k = 0; k < 9; k++) cout<<this->init[k]<<" "; cout<<endl;
                if(move == UP) swap(this->init[i], this->init[i - 3]);
                else if(move == DOWN) swap(this->init[i], this->init[i + 3]);
                else if(move == LEFT) swap(this->init[i], this->init[i - 1]);
                else if(move == RIGHT) swap(this->init[i], this->init[i + 1]);
                //for(int k = 0; k < 9; k++) cout<<this->init[k]<<" "; cout<<endl;
            }
            int min = 100001, cc = 0, pos = 0;
            //cout<<"--------------------\n";
            for(pair<int*, int> q : v) { 
                if(min > q.second) {
                    min = q.second;
                    pos = cc;
                }
                cc++;
                //for(int k = 0; k < 9; k++) cout<<q.first[k]<<" "; cout<<"\t"<<q.second<<endl;
            } //cout<<"--------------------\n";
            pair<int*, int> q = v[pos];
            for(int k = 0; k < 9; k++) this->init[k] = q.first[k];
            v.erase(v.begin() + pos);
            bool is_fin = false;
            for(int k =0 ; k < 9; k++) {
                if(k == 3 || k == 6) cout<<"\n"<<this->init[k]<<" ";
                else cout<<this->init[k]<<" ";
            } cout<<"\n----------"<<endl;
            for(int k = 0; k < 9; k++) {
                if(this->init[k] == this->fin[k]) is_fin = true;
                else { is_fin = false; break; }
            }
            if(is_fin) break;
    }
}
int main() {
    int init[9], fin[9], i, j;
    for(i = 0; i < 9; i++) cin>>init[i];
    for(j = 0; j < 9; j++) cin>>fin[j];
    AStar a(init, fin);
    a.solve();
    return 0;
}
