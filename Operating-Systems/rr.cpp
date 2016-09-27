/*
Round Robin (RR) implementation
by KINER SHAH
*/
#include <iostream.h>
#include <conio.h>
#include <stdio.h>
#include <stdlib.h>
typedef struct process {
	int at,bt,wt,ft,flag;
}pr;

/*int comp(const void *a,const void *b) {
	return ((pr*)a)->at - ((pr*)b)->at;
}*/

int active(int c,pr *p,int np) {
	int i,count=0;
	for(i=0;i<np;i++) {
		if(p[i].at<=c) count++;
		else break;
	}
	return count;
}

int main() {
	int ts,i,j,np;
	clrscr();
	cout<<"Enter no. of processes: "; cin>>np;
	pr *p=new pr[np],temp; int total_b=0;
	for(i=0;i<np;i++) {
		cout<<"Process "<<i+1<<endl;
		cout<<"Enter arrival time: "; cin>>p[i].at;
		cout<<"Enter burst time: "; cin>>p[i].bt;
		total_b+=p[i].bt; p[i].flag=0;
		p[i].wt=0; p[i].ft=p[i].at;
	}
	cout<<"Enter time slice: "; cin>>ts;
	//qsort(p,np,sizeof(pr),comp);
	for(i=0;i<np;i++) {
		for(j=0;j<np-1;j++) {
			if(p[i].at>p[i+1].at) {
				temp=p[i];
				p[i]=p[i+1];
				p[i+1]=temp;
			}
		}
	}
	//for(i=0;i<np;i++) cout<<"P"<<i+1<<" "<<p[i].at<<" "<<p[i].bt<<endl;
	int c=0,tt=0,wt=0;
	int na=active(c,p,np);
	while(c<total_b) {
		for(i=0;i<na;i++) {
			if(!p[i].flag && p[i].bt>ts) {
				cout<<"P"<<i+1<<"\t"<<c<<"\t";
				p[i].bt-=ts;
				p[i].wt=c-p[i].ft;
				p[i].ft=c+ts;
				c+=ts; wt+=p[i].wt;
				cout<<c<<endl;
			}
			else if(!p[i].flag && p[i].bt==ts) {
				cout<<"P"<<i+1<<"\t"<<c<<"\t";
				p[i].bt=0;
				p[i].wt=c-p[i].ft;
				p[i].ft=c+ts;
				p[i].flag=1; wt+=p[i].wt;
				tt+=(c+ts)-p[i].at;
				c+=ts;
				cout<<c<<endl;
			}
			else if(!p[i].flag && p[i].bt<ts) {
				cout<<"P"<<i+1<<"\t"<<c<<"\t";
				p[i].wt=c-p[i].ft;
				p[i].ft=c+p[i].bt;
				c+=p[i].bt; wt+=p[i].wt;
				tt+=c-p[i].at;
				p[i].bt=0;
				p[i].flag=1;
				cout<<c<<endl;
			}	
			if(na==np) continue;
			else na=active(c,p,np);
		}
	}
	cout<<"Average turnaround time: "<<(float)tt/np<<endl;
	cout<<"Average waiting time: "<<(float)wt/np<<endl;
	getch();
	return 0;
}
