/* Program to convert decimals of form entire_part.non-periodic_part (period) to fractions of form p/q */
#include <iostream>
#include <string>
#include <algorithm>
using namespace std;
long long gcd(long long a,long long b) {
    while(b>0) { b^=(a^=(b^=(a%=b))); }
    return a;
}
int main() {
	// your code goes here
	string a; cin>>a;
	int p = (int)a.find("("); //cout<<p<<endl;
	double d = stod(a.substr(0,p));
	//cout<<d<<endl;
	int len = a.length() - 2 - (p + 1) + 1;
	string repeated = a.substr(p+1,len);
	double rep = stod(repeated);
	//cout<<rep<<endl;
	string temp = a.substr(2,p-2); int l = temp.length();
	//cout<<temp<<" "<<l<<endl;
	long long lhs1 = 1, lhs2 = 1, lhs, rhs;
	lhs1 *= pow(10,l + repeated.length());
	lhs2 *= pow(10,l);
	double rhs1 = d + rep/pow(10,l + repeated.length());
	rhs1 *= lhs1;
	double rhs2 = d * lhs2;
	lhs = lhs1 - lhs2;
	rhs = rhs1 - rhs2;
	//cout<<repeated<<endl;
	//cout<<"LHS1 & RHS1: "<<lhs1<<" "<<rhs1<<endl;
	//cout<<"LHS2 & RHS2: "<<lhs2<<" "<<rhs2<<endl;
	long long g = gcd(lhs, rhs);
	cout<<rhs/g<<"/"<<lhs/g<<endl;
	return 0;
}