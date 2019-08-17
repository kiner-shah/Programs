#include <stdio.h>
#include <conio.h>
#include <graphics.h>
#include <dos.h>
void main() {
	int i,xc=100,yc=100,max=400,min=150,gdriver=DETECT,gmode;
	initgraph(&gdriver,&gmode,"D:\\TC\\BGI");
	printf("\n\n\n\t\t\tPROJECT BY-\n");
	printf("\t\t\tKINER SHAH\tSE-B3\t1311113\n\t\t\t");
	printf("DARSHAN SHAH\tSE-B3\t1311111");
	outtextxy(250,200,"BOUNCING BALL");
	outtextxy(250,250,"Loading...");
	printf("\n\n\n\n\n\n\n\n\n\n\n\n\t");
	for(i=0;i<50;i++) {
		printf(">");
		delay(80);
	}
	setcolor(WHITE);
	setfillstyle(SOLID_FILL,YELLOW);
	cleardevice();
	while(xc<=500 && min<=max) {
		while(yc<=max) {
			cleardevice();
			//line(50,100,550,100);
			circle(xc,yc,50);
			floodfill(xc,yc,WHITE);
			line(50,450,550,450);
			xc=xc+1;
			yc=yc+10;
			delay(10);
		}
		yc-=10;
		while(yc>=min) {
			cleardevice();
			//line(50,100,550,100);
			circle(xc,yc,50);
			floodfill(xc,yc,WHITE);
			line(50,450,550,450);
			xc=xc+1;
			yc=yc-10;
			delay(10);
		}
		yc+=10;  min=min+25;
	}
	getch();
	closegraph();
}
