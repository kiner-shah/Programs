/*
Shortest Job First (SJF) implementation
by KINER SHAH
*/
#include<iostream.h>
#include<conio.h>
#include<stdio.h>
#include<stdlib.h>
//Define structure for process
//Arrival time,Burst time,Start time,Finish time,Process index
typedef struct node {
int at,bt,st,ft,index;
}pr;
//Main code
void main()
{
int i,n,j;
pr *p,temp;
clrscr();
cout<<"Enter no. of processes: ";
cin>>n;
p=new pr[n];
//Input burst time and arrival time for each process
for(i=0;i<n;i++) {
	cout<<"Process "<<i+1<<endl;
	cout<<"Burst time: "; cin>>p[i].bt;
	p[i].index=i;
	cout<<"Arrival time: "; cin>>p[i].at;
}
//Sort processes according to increasing arrival time
//Bubble sort used. Time complexity for sorting: O(n^2)
for(i=0;i<n;i++) {
	for(j=0;j<n-1;j++) {
		if(p[j].at>p[j+1].at) {
			temp=p[j];
			p[j]=p[j+1];
			p[j+1]=temp;
		}
	}
}
//For all processes arrived at time p[0].at select the
//process with minimum burst time
int jobfirst=p[0].bt,whichjob=0;
for(i=1;i<n;i++) {
	if(p[i].at==p[0].at) {
		if(p[i].bt<jobfirst) {
			jobfirst=p[i].bt;
			whichjob=i;
		}
	}
	else
		break;
}
//Start with the first process in the queue
p[whichjob].st=p[whichjob].at; p[whichjob].ft=p[whichjob].st+jobfirst;
//Find process with minimum burst time which is arrived
//on or before finish time of previous process
int flag1=1,min,last=p[whichjob].ft,pos;
int *flag=new int[n];
flag[whichjob]=1;
while(flag1==1) {
min=9999;
for(i=0;i<n;i++) {
	if(p[i].at<=last && p[i].bt<min) {
		if(flag[i]!=1) {
			min=p[i].bt;
			pos=i;
			//cout<<p[i].at<<" "<<p[i].bt<<endl;
		}
	}
}
flag[pos]=1;
p[pos].st=last; p[pos].ft=p[pos].st+min;
last=p[pos].ft;
for(i=0;i<n;i++) {
	if(flag[i]!=1)
		break;
}
if(i==n)  {
	flag1=0;
	break;
}
}
//Display process scheduling
cout<<"Process\tArrival\tBurst\tStart\tFinish\n";
for(i=0;i<n;i++) {
	cout<<"P"<<p[i].index + 1<<"\t";
	cout<<p[i].at<<"\t"<<p[i].bt<<"\t";
	cout<<p[i].st<<"\t"<<p[i].ft<<endl;
}
//Calculate and display average waiting and turnaround time
int sum1=0,sum2=0;
for(i=0;i<n;i++) {
	sum1+=p[i].st-p[i].at;
	sum2+=p[i].ft-p[i].at;
}
cout<<"Average waiting time: "<<(float)sum1/n<<endl;
cout<<"Average turnaround time: "<<(float)sum2/n;
free(p); p=NULL;
free(flag); flag=NULL;
getch();
}