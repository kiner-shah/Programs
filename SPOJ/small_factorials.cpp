#include <iostream>
#define MAX 500
using namespace std;
int multiply(int x,int res[],int size) {
	int car=0,p,i;
	for(i=0;i<size;i++) {
		p=res[i]*x+car;
		res[i]=p%10;
		car=p/10;
	}
	while(car>0) {
		res[size]=car%10;
		car/=10;
		size++;
	}
	return size;
}
void fact(int n) {
	int res[MAX]; res[0]=1;
	int size=1;
	for(int i=2;i<=n;i++)
		size=multiply(i,res,size);
	for(int i=size-1;i>=0;i--)
		cout<<res[i];
	cout<<endl;
}

int main() {
	// your code goes here
	int t,n;
	cin>>t;
	while(t-- > 0) {
		cin>>n;
		fact(n);
	}
	return 0;
}
