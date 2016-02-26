#include<iostream.h>
#include<conio.h>
#include<stdlib.h>
void main()
{
int *flag,n,m,*pr,*bl,i,j;
clrscr();
cout<<"Enter no. of processes: ";
cin>>n;
pr=(int*)calloc(n,sizeof(int));
for(i=0;i<n;i++) {
	cout<<"Enter size of process "<<i+1<<": ";
	cin>>pr[i];
}
cout<<"Enter no. of blocks: ";
cin>>m;
flag=(int*)calloc(m,sizeof(int));
bl=(int*)calloc(m,sizeof(int));
for(i=0;i<m;i++) {
	cout<<"Enter size of block "<<i+1<<": ";
	cin>>bl[i];
}
if(n<=m) {
for(i=0;i<n;i++) {
	for(j=0;j<m;j++) {
		if(flag[j]==0 && pr[i]<=bl[j]) {
			flag[j]=i+1;
			break;
		}
	}
}
for(i=0;i<m;i++)
	if(flag[i]>0)
		cout<<"Process "<<flag[i]<<"->Block "<<i+1<<endl;
}
getch();
}
