#include <iostream>
#include <cmath>
using namespace std;

int main()
{
    float in[4][2] = {
       {0, 1},
       {1, 2},
       {2, 2},
       {2, 1}
    };
    float w[4] = {1, 2, 4, 5};
    int i;
    for(i = 0; i < 4; i++) {
        float net1 = in[i][0] * w[0] + in[i][1] * w[1];
        float out1 = (float) 1 / (1 + exp(-net1));
        float net2 = in[i][0] * w[2] + in[i][1] * w[3];
        float out2 = (float) 1 / (1 + exp(-net2));
        w[0] = w[0] + 0.7 * in[i][0] * out1;
        w[1] = w[1] + 0.7 * in[i][0] * out2;
        w[2] = w[2] + 0.7 * in[i][1] * out1;
        w[3] = w[3] + 0.7 * in[i][1] * out2;
        printf("After %d input, news weights are %0.3f\t%0.3f\t%0.3f\t%0.3f\t", i+1, w[0], w[1], w[2], w[3]);
        printf("Output: %0.3f %0.3f\n", out1, out2);
    }
    return 0;
}

