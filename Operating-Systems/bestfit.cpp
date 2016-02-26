#include <iostream.h>
#include <conio.h>
#include <stdlib.h>
void main()
{
int *pr,*bl,min,*util,*flag,pos,i,j,n,m;
clrscr();
cout<<"Enter no. of processes: ";
cin>>n;
util=(int*)calloc(n,sizeof(int));
pr=(int*)calloc(n,sizeof(int));
for(i=0;i<n;i++) {
	cout<<"Enter size of process "<<i+1<<": ";
	cin>>pr[i];
}
cout<<"Enter no. of blocks: ";
cin>>m;
bl=(int*)calloc(m,sizeof(int));
flag=(int*)calloc(m,sizeof(int));
for(i=0;i<m;i++) {
	cout<<"Enter size of block "<<i+1<<": ";
	cin>>bl[i];
}
for(i=0;i<m;i++) {
	min=bl[i]-pr[0]; pos=0;
	for(j=1;j<n;j++) {
		if((util[j]==0 && flag[i]==0) && (bl[i]>=pr[j] && bl[i]-pr[j]<min)) {
			min=bl[i]-pr[j];
			pos=j;
		}
	}
	flag[i]=pos+1;
	util[pos]=1;
}
for(i=0;i<m;i++) {
	cout<<"Process "<<flag[i]<<"->Block "<<i+1<<endl;
}
getch();
}
