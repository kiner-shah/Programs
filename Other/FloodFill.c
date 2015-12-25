#include <stdio.h>
#include <conio.h>
#include <graphics.h>
void floodfill1(int x,int y,int tc,int rc)
{
if(getpixel(x,y)==tc) {
	putpixel(x,y,rc);
	floodfill1(x+1,y,tc,rc);
	floodfill1(x-1,y,tc,rc);
	floodfill1(x,y+1,tc,rc);
	floodfill1(x,y-1,tc,rc);
}
}
void main()
{
int gdriver=DETECT,gmode,xc,yc,oc,nc,r;
initgraph(&gdriver,&gmode,"D:\\TC\\BGI");
printf("Enter coordinates of center: ");
scanf("%d %d",&xc,&yc);
printf("Enter radius: ");
scanf("%d",&r);
printf("Enter target color: ");
scanf("%d",&oc);
printf("Enter replacement color: ");
scanf("%d",&nc);
setcolor(WHITE);
circle(xc,yc,r);
floodfill1(xc,yc,oc,nc);
getch();
closegraph();
}
