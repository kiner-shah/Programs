/*
FIFO page replacement is same as LRU..just comment one line!
*/
#include<iostream.h>
#include<conio.h>
#include<stdlib.h>
int isthere(int a,int p[][100],int col)
{
int i,x=-1;
for(i=0;i<3;i++) {
	if(p[i][col]==a)
	{	x=i; break; }
}
return x;
}
int findpos(int key[])
{
int i,min=key[0],pos=0;
for(i=1;i<3;i++) {
	if(min>key[i])
	{
		min=key[i];
		pos=i;
	}
}
return pos;
}
void main()
{
int *hit,*a,n,c,p[3][100],i,j,k,count=3,faults=0,hits=0;
float hr;
clrscr();
cout<<"Enter total no. of values: ";
cin>>n;
a=(int*)calloc(n,sizeof(int));
hit=(int*)calloc(3,sizeof(int));
hit[0]=1;
hit[1]=2;
hit[2]=3;
cout<<"Enter values: ";
for(i=0;i<n;i++)
	cin>>a[i];
p[0][0]=a[0]; p[1][0]=-1; p[2][0]=-1;
p[0][1]=a[0];
p[1][1]=a[1]; p[2][1]=-1;
p[0][2]=a[0];
p[1][2]=a[1];
p[2][2]=a[2];
/*for(i=0;i<3;i++) {
	for(j=0;j<3;j++)
		cout<<p[i][j]<<" ";
	cout<<endl;
} */
for(j=3;j<n;j++) {
	if(isthere(a[j],p,j-1)>=0)
	{
		hit[isthere(a[j],p,j-1)]= ++count; //for fifo comment this line
		p[0][j]=p[0][j-1];
		p[1][j]=p[1][j-1];
		p[2][j]=p[2][j-1];
		hits++;
	}
	else {
		c=findpos(hit);
		p[c][j]=a[j];
		if(c==0)
		{
			p[1][j]=p[1][j-1];
			p[2][j]=p[2][j-1];
		}
		else if(c==1)
		{
			p[0][j]=p[0][j-1];
			p[2][j]=p[2][j-1];
		}
		else if(c==2)
		{
			p[0][j]=p[0][j-1];
			p[1][j]=p[1][j-1];
		}
		hit[c]= ++count;
		faults++;
	}
}
for(i=0;i<3;i++) {
	for(j=0;j<n;j++)
		cout<<p[i][j]<<" ";
	cout<<endl;
}
cout<<"No. of faults: "<<faults<<endl;
cout<<"Hit rate: "<<(float)hits/n;
getch();
}