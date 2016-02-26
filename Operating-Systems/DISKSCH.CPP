/*
Disk Scheduling algorithms(FCFS, SSTF, SCAN, CSCAN, LOOK)
by, KINER SHAH
*/
#include<iostream.h>
#include<conio.h>
#include<stdio.h>
#include<math.h>
void fcfs(int h,int n,int *b) {
int i,cur=h,st=0;
st+=abs(b[0]-h);
cout<<"Order: "<<h<<" "<<b[0]<<" ";
for(i=1;i<n;i++) {
	cout<<b[i]<<" ";
	st+=abs(b[i]-b[i-1]);
}
cout<<"\nAverage Seek time: "<<(float)st/n<<endl;
}

void sstf(int h,int n,int *b) {
int i,pos=-1,cur=h,st=0;
int *flag=new int[n];
for(i=0;i<n;i++) flag[i]=0;
cout<<"Order: "<<h<<" ";
while(1) {
	int min=9999; pos=-1;
	for(i=0;i<n;i++) {
		if(!flag[i] && min>abs(cur-b[i])) {
			min=abs(cur-b[i]); pos=i;
		}
	}
	if(min!=9999) {
		st+=min; cur=b[pos]; flag[pos]=1;
		cout<<b[pos]<<" ";
	}
	for(i=0;i<n;i++) {
		if(!flag[i]) break;
	}
	if(i==n) break;
}
cout<<"\nAverage seek time: "<<(float)st/n<<endl;
}

void scan(int h,int n,int *b,int m) {
int i,j,cur=h,pos=-1,st=0,*flag;
cout<<"Order: "<<h<<" ";
//sort blocks
flag=new int[n];
for(i=0;i<n;i++){
	for(j=0;j<n-1;j++) {
		if(b[j]>b[j+1]) {
			int temp=b[j];
			b[j]=b[j+1];
			b[j+1]=temp;
		}
	}
}
//for(i=0;i<n;i++) cout<<b[i]<<" ";
for(i=0;i<n;i++) {
	if(h<b[i]) break;
}
for(j=i-1;j>=0;j--) {
	st+=abs(cur-b[j]);
	cur=b[j]; cout<<b[j]<<" ";
}
st+=b[0]; cur=0; cout<<"0 ";
for(j=i;j<n;j++) {
	st+=abs(cur-b[j]);
	cur=b[j]; cout<<b[j]<<" ";
}
cout<<"\nAverage seek time: "<<(float)st/n<<endl;
}

void cscan(int h,int n,int *b,int m) {
int i,j,cur=h,st=0;
cout<<"Order: "<<h<<" ";
for(i=0;i<n;i++){
	for(j=0;j<n-1;j++) {
		if(b[j]>b[j+1]) {
			int temp=b[j];
			b[j]=b[j+1];
			b[j+1]=temp;
		}
	}
}
for(i=0;i<n;i++) {
	if(h<b[i]) break;
}
for(j=i-1;j>=0;j--) {
	st+=abs(cur-b[j]);
	cur=b[j]; cout<<b[j]<<" ";
}
st+=b[0]; cur=m-1; cout<<"0 "<<m-1<<" ";
for(j=n-1;j>=i;j--) {
	st+=abs(cur-b[j]);
	cur=b[j]; cout<<b[j]<<" ";
}
cout<<"\nAverage seek time: "<<(float)st/n<<endl;
}

void look(int h,int n,int *b) {
int i,cur=h,st=0,j;
cout<<"Order: "<<h<<" ";
for(i=0;i<n;i++) {
	for(j=0;j<n-1;j++) {
		if(b[j]>b[j+1]) {
			int temp=b[j];
			b[j]=b[j+1];
			b[j+1]=temp;
		}
	}
}
for(i=0;i<n;i++) {
	if(h<b[i]) break;
}
for(j=i-1;j>=0;j--) {
	st+=abs(cur-b[j]);
	cur=b[j]; cout<<b[j]<<" ";
}
for(j=i;j<n;j++) {
	st+=abs(cur-b[j]);
	cur=b[j]; cout<<b[j]<<" ";
}
cout<<"\nAverage seek time: "<<(float)st/n<<endl;
}

int main()
{
int i,c,h,*b,n,m;
clrscr();
cout<<"Enter total no. of blocks: "; cin>>m;
cout<<"Enter no. of blocks to be processed: "; cin>>n;
cout<<"Enter blocks\n";
b=new int[n];
for(i=0;i<n;i++) cin>>b[i];
cout<<"Enter header position: "; cin>>h;
cout<<"1.FCFS\n2.SSTF\n3.SCAN\n4.CSCAN\n5.LOOK\n";
cout<<"Enter choice: "; cin>>c;
switch(c) {
	case 1: fcfs(h,n,b); break;
	case 2: sstf(h,n,b); break;
	case 3: scan(h,n,b,m); break;
	case 4: cscan(h,n,b,m); break;
	case 5: look(h,n,b); break;
	default: cout<<"\nInvalid input"; break;
}
getch();
return 0;
}