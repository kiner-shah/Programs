#include <iostream>
using namespace std;

int main() {
	// your code goes here
	int t;
	unsigned long long int k,ans;
	cin>>t;
	while(t-- > 0) {
		cin>>k;
		ans=192+(250*(k-1));
		cout<<ans<<endl;
	}
	return 0;
}
