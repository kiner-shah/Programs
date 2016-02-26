/*
First Come First Serve (FCFS) implementation
by,
KINER SHAH TE-B3 (COMP) 1311113
*/
#include<stdlib.h>
#include<conio.h>
#include<iostream.h>
typedef struct process {
int at,bt;
}pr;

int main() {
int n,i,j,temp1,*seq,*start,*finish,sum1=0,sum2=0;
float avwt,avtt;
pr *p,temp;
clrscr();
cout<<"Enter no. of processes: ";
cin>>n;
start=(int*)calloc(n,sizeof(int));
finish=(int*)calloc(n,sizeof(int));
seq=(int*)calloc(n,sizeof(int));
p=(pr*)calloc(n,sizeof(pr));
for(i=0;i<n;i++) {
	cout<<"Process "<<i+1;
	seq[i]=i+1;
	cout<<"\nBurst time: "; cin>>p[i].bt;
	cout<<"Arrival time: "; cin>>p[i].at;
}
for(i=0;i<n;i++) {
	for(j=0;j<n-1;j++) {
		if(p[j].at>p[j+1].at) {
			temp=p[j];
			p[j]=p[j+1];
			p[j+1]=temp;
			temp1=seq[j]; seq[j]=seq[j+1]; seq[j+1]=temp1;
		}
	}
}
/*for(i=0;i<n;i++)
	cout<<p[i].bt<<" ";*/
start[0]=0;
for(i=0;i<n;i++) {
	finish[i]=start[i]+p[i].bt;
	if(i+1 < n)
		start[i+1]=finish[i];
}
/*for(i=0;i<n;i++)
	cout<<start[i]<<" "<<finish[i]<<endl;*/
for(i=0;i<n;i++) {
	sum1+=(start[i]-p[i].at);
	sum2+=(finish[i]-p[i].at);
}
for(i=0;i<n;i++)
	cout<<"  P"<<seq[i]<<"  ";
cout<<"\n|";
for(i=0;i<n;i++)
	cout<<"-----|";
cout<<endl;
cout<<start[0];
for(i=0;i<n;i++)
	cout<<"     "<<finish[i];
cout<<endl;
cout<<"Average waiting time is: "<<(float)sum1/n;
cout<<"\nAverage turnaround time is: "<<(float)sum2/n;
getch();
return 0;
}
