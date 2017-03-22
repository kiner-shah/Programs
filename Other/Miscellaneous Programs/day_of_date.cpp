#include <iostream>
#include <string>
#include <cmath>
#include <cstdio>
using namespace std;
bool is_leap(int y) {
	return (y % 4 == 0 && y % 100 != 0) || y % 400 == 0;
}
int findnearest(int a[11], int y) {
	int i;
	for(i = 0; i < 11; i++) {
		if(a[i] > y) break;
	} 
	return a[i - 1];
}
int find_odd_this(int x[12], int d, int m, int y) {
	int i, sum = 0;
	bool flag = is_leap(y);
	for(i = 0; i < m - 1; i++) {
		if(i == 1 && flag) sum +=x[i] + 1;
		else sum += x[i];
	}
	sum += d;
	return sum % 7;
}
int main() {
	// your code goes here
	string days[7] = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	int four_multiples[11] = {400,800,1200,1600,2000,2400,2800,3200,3600,4000,4400};
	int months[12] = {31,28,31,30,31,30,31,31,30,31,30,31};
	int d,m,y,odd = 0;
	cin>>d>>m>>y;
	int temp = (y/100) * 100; 
	int diff = temp - findnearest(four_multiples, temp); //cout<<diff<<endl;
	if(diff == 100) { odd += 5; }
	else if(diff == 200) { odd += 3; }
	else if(diff == 300) { odd += 1; }
	else if(diff == 400) { odd += 0; }
	//cout<<odd<<endl;
	int last_two = (y) % 100;
	int leap_last_two = (last_two - 1) / 4; //round((double)((last_two - 1) * 76) / 100);
	int ord_last_two = (last_two - 1 - leap_last_two);
	int odd_last_two = (leap_last_two * 2 + ord_last_two) % 7; //cout<<odd_last_two<<endl;
	odd = (odd + odd_last_two) % 7;
	if(odd < 0) odd = 6 + odd;
	//cout<<odd<<endl;
	int odd_this = find_odd_this(months, d, m, y);
	//cout<<odd_this<<endl;
	odd = (odd_this + odd) % 7;
	cout<<days[odd];
	return 0;
}