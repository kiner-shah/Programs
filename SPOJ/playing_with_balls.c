#include <stdio.h>

int main(void) {
	// your code goes here
	long long int t,w,b;
	float ans;
	scanf("%lld",&t);
	while(t-- > 0) {
		ans=0;
		scanf("%lld %lld",&w,&b);
		if(b%2 == 0) ans=0;
		else ans=1;
		printf("%.6f\n",ans);
	}
	return 0;
}
