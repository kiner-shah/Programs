/*
* Hexadecimal Addition and Multiplication (Not a generic implementation)
* by, KINER SHAH
* 22nd December 2015
* Note: Works only for inputs in range (00000 to FFFFF)
*/
#include<stdio.h>
#include<iostream.h>
#include<math.h>
#include<string.h>
#include<conio.h>
#include<stdlib.h>
char *zeroes(char a[],int no)
{
char *c; int i,j;
c=(char*)calloc(5,sizeof(char));
for(i=4,j=strlen(a)-1;j>=0;i--,j--)
	c[i]=a[j];
for(i=0;i<no;i++)
	c[i]='0';
return c;
}
int findpos(int ch,char a[])
{
int i;
for(i=0;i<=15;i++) {
	if(a[i]==ch)
		break;
}
return i;
}
void add(char a[],char b[])
{
int j,e,c,i,sum,car=0,len=0;
char d[5];
char hv[16]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
if(5>strlen(b))
{	b=zeroes(b,5-strlen(b)); len=strlen(a); }
if(strlen(a)<5)
{	a=zeroes(a,5-strlen(a)); len=strlen(b); }
else
	len=strlen(a);
cout<<"The sum is: ";
for(i=4,j=len-1;i>=0;i--) {
	e=findpos(a[j],hv);
	c=findpos(b[j],hv);
	//cout<<e<<" "<<c<<endl;
	sum=e+c+car;
	car=sum/16;
	//cout<<sum<<" "<<car<<endl;
	d[i]=hv[sum%16];
	j--;
}
cout<<d<<endl;
}
void add1(char a[],char b[],int no)
{
int j,e,c,i,k,sum,car=0,len=0;
//char *d=(char*)calloc(5,sizeof(char));
char hv[16]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
if(5>strlen(b))
{	b=zeroes(b,5-strlen(b)); len=strlen(a); }
if(strlen(a)<5)
{	a=zeroes(a,5-strlen(a)); len=strlen(b); }
else
	len=strlen(a);
for(k=0;k<no;k++) {
for(i=4,j=len-1;i>=0;i--) {
	e=findpos(a[j],hv);
	c=findpos(b[j],hv);
	//cout<<e<<" "<<c<<endl;
	sum=e+c+car;
	car=sum/16;
	//cout<<sum<<" "<<car<<endl;
	a[i]=hv[sum%16];
	j--;
}
}
cout<<"Product is: "<<a<<endl;
}
void multiply(char a[],char b[])
{
int i,sum=0;
char sum1[6];
char hv[16]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
strcpy(sum1,"00000");
for(i=strlen(b)-1;i>=0;i--) {
	sum=sum+findpos(b[i],hv)*pow(16,strlen(b)-i-1);
}
add1(sum1,a,sum);
}
void main()
{
char a[6],b[6];
int i,j;
clrscr();
printf("Enter first hexadecimal number: ");
scanf("%s",a);
printf("Enter second hexadecimal number: ");
scanf("%s",b);
add(a,b);
multiply(a,b);
getch();
}
