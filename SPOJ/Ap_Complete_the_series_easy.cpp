#include <stdio.h>

int main(void) {
	// your code here
	int t; long long a,b,sum;
	scanf("%d",&t);
	while(t-- > 0) {
		scanf("%lld %lld %lld",&a,&b,&sum); long long i;
		long long n=(sum*2)/(a+b);
		printf("%lld\n",n);
		long long d=(b-a)/(n-5); 
		long long x=a-2*d; //printf("%lld\n",d);
		long long last=x+(n-1)*d; //printf("%lld\n",last);
		if(d<0) {
			for(i=x;i>=last;i+=d)
				printf("%lld ",i);
		}
		else if(d>0) {
			for(i=x;i<=last;i+=d)
				printf("%lld ",i);
		}
		else {
			for(i=0;i<n;i++) printf("%lld ",x);
		}
		printf("\n");
	}
	return 0;
}
