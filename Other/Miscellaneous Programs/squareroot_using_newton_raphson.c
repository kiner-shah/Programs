#include <stdio.h>
#include <math.h>
// Square root using Newton-Raphson Method
double f(double x, double n) {
    return (x * x - n);
}
double f_dash(double x) {
    return 2.0 * x;
}
int main() {
    int c = 1000;
    double n, x0 = 10.0;
    printf("Enter a number whose square root has to be determined: ");
    scanf("%lf", &n);
    while(c-- > 0) {
        x0 = x0 - f(x0, n) / f_dash(x0);
    }
    printf("Square root is:\t\t\t%0.30lf\n", x0);
    printf("Actual square root is:\t%0.30lf\n", sqrt(n));
    return 0;
}