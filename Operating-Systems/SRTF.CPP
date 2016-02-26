/*
Shortest Remaining Time First (SRTF) implementation
by, KINER SHAH
*/
#include<iostream.h>
#include<stdio.h>
#include<math.h>
#include<conio.h>
#include<stdlib.h>
typedef struct process {
int wt,at,bt,ft;
}pr;

int comp(const void *a, const void *b) {
return ((pr*)a)->at - ((pr*)b)->at;
}

int findmin(pr *p,int n,int cur) {
int i,min=9999,pos=-1;
for(i=0;i<n;i++) {
	if(p[i].at<=cur) {
		if(p[i].bt<min) {
			min=p[i].bt;
			pos=i;
		}
	}
}
return pos;
}

int main()
{
int np,i,min,c,sum=0;
clrscr();
cout<<"Enter no. of processes: "; cin>>np;
pr *p=new pr[np];
for(i=0;i<np;i++) {
	cout<<"Process "<<i+1<<endl;
	cout<<"Enter arrival time: "; cin>>p[i].at;
	cout<<"Enter burst time: "; cin>>p[i].bt;
	sum+=p[i].bt;
	p[i].ft=p[i].at; p[i].wt=0;
}
qsort(p,np,sizeof(pr),comp);
cout<<"As per arrival time\nProcess\tArrival\tBurst\n";
for(i=0;i<np;i++) cout<<"P"<<i+1<<"\t"<<p[i].at<<"\t"<<p[i].bt<<endl;
c=0; int pos,tt=0,wt=0;
cout<<"After scheduling\nProcess\tStart\tFinish\n";
while(c<sum) {
	pos=findmin(p,np,c);
	cout<<"P"<<pos+1<<"\t"<<c<<"\t"<<c+1<<endl;
	p[pos].bt--;  p[pos].wt=c-p[pos].ft;
	p[pos].ft=c+1; wt+=p[pos].wt;
	if(p[pos].bt == 0) {
		p[pos].bt=9999;
		tt+=c-p[pos].at+1;
		//cout<<c+1-p[pos].at<<endl;
	}
	c++;
}
//cout<<"Total turnaround time: "<<tt<<endl;
cout<<"Average turnaround time: "<<(float)tt/np<<endl;
cout<<"Average waiting time: "<<(float)(wt)/np<<endl;
getch();
return 0;
}
/*
For test case, follow this link:
http://www.gatefundas.com/shortest-remaining-time-first-srtf-cpu-scheduling/
*/