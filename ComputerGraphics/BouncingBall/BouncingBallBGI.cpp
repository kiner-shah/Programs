#include <stdio.h>
#include <stdlib.h>
#include <graphics.h>
#include <dos.h>
#include <conio.h>
#include <string.h>
#include <cmath>

static void* ball_ptr = NULL;
static void* reset_ball_ptr = NULL;

static int max_x;
static int max_y;

static int line_x;
static int line_y;

const int ball_r = 20;
const int ball_d = 40;
const double coeff_restitution = 0.75;
const double pi = 3.141592654;

static void draw_ball(int x, int y) {
	//printf("Inside draw_ball()\n");
	setcolor(WHITE);
	setfillstyle(SOLID_FILL, YELLOW);
	circle(x, y, ball_r);
	floodfill(x, y, WHITE);
}

static void bounce() {
    float  theta = 290.0,
           v0 = 70.0,
           theta_rad = (theta * pi) / 180.0,
           vx = v0 * cos(theta_rad),
           vy = v0 * sin(theta_rad),
           g = 9.8,
           range = (v0 * v0 * sin(2 * theta_rad)) / g,
           init_x = range / 2.0, /*-30.0,*/
           init_y = max_y - 100.0,
           t = 0.1,
           prev_x = 0.0,
           prev_y = 0.0,
           x = 0.0,
           y = 0.0;
    int bounce_cnt = 0, active_pg = 0;
    //printf("[%f %f]\n", vx, vy);
    //printf("=====%d=====\n", bounce_cnt);
    while (bounce_cnt <= 7) {
        x = init_x + vx * t;
        y = init_y + vy * t + 0.5 * g * t * t;
        t += 0.33;

        if (y >= max_y - 99) {
            bounce_cnt++;
            //printf("=====%d=====\n", bounce_cnt);
            v0 *= coeff_restitution;
            vx = v0 * cos(theta_rad);
            vy = v0 * sin(theta_rad);
            init_y = max_y - 100;
            init_x = x;
            t = 0.1;
            putimage((int)prev_x, (int)prev_y, reset_ball_ptr, COPY_PUT);
        }
        else {
            //printf("(%f, %f)\n", x, y);
            //printf("Pg: %d\n", active_pg);
            putimage((int)prev_x, (int)prev_y, reset_ball_ptr, COPY_PUT);
            setactivepage(active_pg);
            putimage((int) x, (int) y, ball_ptr, COPY_PUT);
            line(0, init_y + ball_d, max_x, init_y + ball_d);
            delay(33);
            setvisualpage(active_pg);
            active_pg = 1 - active_pg;
            prev_x = x;
            prev_y = y;
        }
    }
    putimage((int) prev_x, (int) prev_y, ball_ptr, COPY_PUT);
    line(0, init_y + ball_d, max_x, init_y + ball_d);
}

int main() {
	int gdriver = EGA, gmode = EGAHI, area, errorcode;
	initgraph(&gdriver, &gmode, (char*) "D:\\TC\\BGI");
	errorcode = graphresult();
	/* an error occurred */
	if (errorcode != grOk) {
	   printf("Graphics error: %s\n", grapherrormsg(errorcode));
	   printf("Press any key to halt:");
	   getch();
	   exit(EXIT_FAILURE);             /* return with error code */
	}

    //printf("[%d %d]\n", gdriver, gmode);

	draw_ball(ball_r + 1, ball_r);
	area = imagesize(1, 0, ball_d, ball_d);
	ball_ptr = malloc(area);
	getimage(1, 0, ball_d, ball_d, ball_ptr);

	cleardevice();
	reset_ball_ptr = malloc(area);
	getimage(1, 0, ball_d, ball_d, reset_ball_ptr);

	line_y = ball_r * 13;
	max_x = line_x = getmaxx();
	max_y = getmaxy();

	bounce();

	getch();
	closegraph();
	return 0;
}
