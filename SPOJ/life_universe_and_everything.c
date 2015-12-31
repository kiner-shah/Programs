#include<stdio.h>
int main() {
int x,flag=0;
while(scanf("%d",&x)!= EOF) {
	if(flag==0 && x!=42)
		printf("%d\n",x);
	else if(x==42)
		flag=1;
}
return 0;
}
