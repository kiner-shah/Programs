/*
Author: KINER SHAH
Title: FUZZY INFERENCE SYSTEM FOR DETERMINING BRAKE POWER TO BE APPLIED GIVEN THE SPEED OF THE TRAIN AND THE DISTANCE FROM THE
DESTINATION STATION
*/
#include <iostream>
#include <cstdio>
#include <cmath>
#include <set>
#include <vector>
#include <algorithm>
using namespace std;
template<typename T> inline T Min(T a, T b) { return (a < b ? a : b); }
inline bool comp(pair<string, float> a, pair<string, float> b) { return a.second > b.second; }
string rules(string a, string b) {
    string c;
    if((a == "very near" && b == "low") ||
       (a == "near" && b == "low") ||
       (a == "near" && b == "medium") ||
       (a == "far" && b == "low") ||
       (a == "far" && b == "medium")) {
           c = "low";
    } 
    else if((a == "near" && b == "high") || (a == "far" && b == "high")) {
        c = "medium";
    }
    else {
        c = "high";
    }
    return c;
}
float findpower(string category, float deg) {
    float p;
    if(category == "low") {
        if(deg == 1) p = 3.0;
        else p = 5 - 2 * deg;
    }
    else if(category == "medium") {
        if(deg == 1) p = 6.0;
        else p = 8.5 - 1.5 * deg;
    }
    else if(category == "high") {
        if(deg == 1) p = (float) (10 + 8.5) / 2;
        else p = 7.5 + deg;
    }
    return p;
}
int main() {
   float degree, dist, speed;
   cout<<"Enter distance: "; cin>>dist;
   cout<<"Enter speed: "; cin>>speed;
   set<pair<string, float>> s;
   set<pair<string, float>> s1;
   // Descriptors for distance
   if(dist < 0) cerr<<"Error: Negative distance entered\n";
   if(dist >= 0 && dist <= 3) s.insert(make_pair("very near", 1.0));
   if(dist > 3 && dist < 5) {
       degree = (float) (5 - dist) / 2;
       s.insert(make_pair("very near", degree));
   }
   if(dist >= 4 && dist < 5) {
       degree = (float) (dist - 4);
       s.insert(make_pair("near", degree));
   }
   if(dist >= 5 && dist <= 8) s.insert(make_pair("near", 1.0));
   if(dist > 8 && dist < 9) {
       degree = (float) (9 - dist);
       s.insert(make_pair("near", degree));
   }
   if(dist > 8 && dist < 9) {
       degree = (float) (dist - 8);
       s.insert(make_pair("far", degree));
   }
   if(dist >= 9) s.insert(make_pair("far", 1.0));
   // Descriptors for speed
   if(speed < 0) cerr<<"Error: Negative speed entered\n";
   if(speed >= 0 && speed <= 4) s1.insert(make_pair("low", 1.0));
   if(speed > 4 && speed < 6) {
       degree = (float) (6 - speed) / 2;
       s1.insert(make_pair("low", degree));
   }
   if(speed >= 5 && speed <= 6) {
       degree = (float) speed - 5;
       s1.insert(make_pair("medium", degree));
   }
   
   if(speed >= 6 && speed <= 9) s1.insert(make_pair("medium", 1.0));
   if(speed > 9 && speed < 10) {
       degree = (float) 10 - speed;
       s1.insert(make_pair("medium", degree));
   }
   if(speed >= 9.5 && speed <= 10) {
       degree = (float) speed - 9.5;
       s1.insert(make_pair("high", degree));
   }
   if(speed >= 10 && speed <= 12) s1.insert(make_pair("high", 1.0));
   
   // for(pair<string, float> p : s) cout<<p.first<<" "<<p.second<<endl;
   // for(pair<string, float> p : s1) cout<<p.first<<" "<<p.second<<endl;
   vector<pair<string, float>> v;
   for(pair<string, float> p : s) {
       for(pair<string, float> p1 : s1) {
           string c = rules(p.first, p1.first);
           v.push_back(make_pair(c, Min(p.second, p1.second)));
       }
   }
   sort(v.begin(), v.end(), comp);
   cout<<"Braking power category: "<<v[0].first<<endl;
   float power = findpower(v[0].first, v[0].second);
   cout<<"Braking power: "<<power<<" units\n";
   cout<<"Defuzzification method: Largest of Maximum\n";
   return 0;
}
/*
OUTPUT
Enter distance: 4.75                                                           
Enter speed: 5.85
Braking power category: low                                                    
Braking power: 3.5 units                                                       
Defuzzification method: Largest of Maximum   

BRAKING POWER FUNCTION
low: p = 1, 0 <= x <= 3
       = (5 - x) / 2, 3 < x < 5
medium: p = x - 4, 4 < x < 5
          = 1, 5 <= x <= 7
          = 8.5 - 1.5 * x, 7 < x < 8.5
high: p = x - 7.5, 7.5 < x < 8.5
        = 1, 8.5 <= x <= 10
*/
