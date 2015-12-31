#include <stdio.h>
#include <stdlib.h>
int main(void) {
	// your code goes here
	int n,i,sum,avg,*a,count,flag,count1;
	scanf("%d",&n);
	while(n!=-1) {
		a=(int*)calloc(n,sizeof(int));
		sum=0; count=0; flag=0; count1=0;
		for(i=0;i<n;i++) {
			scanf("%d",&a[i]);
			sum+=a[i];
		}
		avg=sum/n; 
		//printf("sum: %d\n",n*avg);
		if(n*avg != sum) flag=1;
		//printf("avg: %d\n",avg);
		for(i=0;i<n;i++) {
			if(a[i]<avg) {
				count+=(avg-a[i]);
			}
			if(a[i]>=avg) {
				if(flag==1 && a[i]==avg+1) 
					count1=-1;
				//printf("reached: %d %d\n",a[i],count1);
			}
		}
		if(count1!=-1 && count==0) printf("0\n");
		else if(count1==-1) printf("-1\n");
		else printf("%d\n",count);
		scanf("%d",&n);
	}
	return 0;
}
