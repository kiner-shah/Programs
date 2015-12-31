#include <iostream>
#include <algorithm>
using namespace std;
bool myfunction(int i, int j) {
	return i<j;
}
int main() {
	// your code goes here
	long long int t,n,*a,*b,i,sum;
	cin>>t;
	while(t-- > 0) {
		cin>>n; sum=0;
		a=new long long int[n];
		b=new long long int[n];
		for(i=0;i<n;i++) cin>>a[i];
		for(i=0;i<n;i++) cin>>b[i];
		sort(a, a + n, myfunction);
		sort(b, b + n, myfunction);
		for(i=0;i<n;i++) {
			sum+=(a[i]*b[i]);
		}
		cout<<sum<<endl;
	}
	return 0;
}
