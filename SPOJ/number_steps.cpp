#include <iostream>
#include <cmath>
using namespace std;

int main() {
	// your code goes here
	int t;
	cin>>t;
	while(t-- > 0) {
		int a,b,ans;
		cin>>a>>b;
		if(a==b) {
			if(a%2==0)
				ans=1*(a/2)+3*(a/2);
			else
				ans=1*ceil((float)a/2)+3*(a/2);
			cout<<ans<<endl;
		}
		else if(a>=2 && a-b == 2) {
			if(b%2==0) {
				ans=2 + 1*(b/2)+ 3*(b/2);
			}
			else
				ans=2+ 1*ceil((float)b/2) + 3*(b/2);
			cout<<ans<<endl;
		}
		else {
			cout<<"No Number\n";
		}
	}
	return 0;
}
