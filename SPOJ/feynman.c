#include <stdio.h>

int main(void) {
	// your code here
	int n;
	int a[101],i; a[1]=1; a[2]=5; a[3]=14;
	for(i=4;i<=100;i++) {
		a[i]=(i-1)*(i-1) + a[i-1] + (i*2 - 1);
	}
	while(1) {
		scanf("%d",&n);
		if(n==0)
			break;
		else
			printf("%d\n",a[n]);
	}
	return 0;
}
