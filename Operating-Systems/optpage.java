/*
Implementation of Optimal Page Replacement algorithm
by KINER SHAH SE-B3(COMP) 1311113
*/
//Here we assume the no. of available frames to be a constant equal to 3
import java.util.*;
class optpage {
    public static int isthere(int a,int p[][],int col) {
        int i,x=-1;
        for(i=0;i<3;i++) {
            if(p[i][col]==a) {
                x=i; break;
            }
        }
        return x;
    }
    public static int getmaxindex(int hit[],int start,int a[]) {
        int j,in[]=new int[3];
        for(int i=0;i<3;i++) {
            for(j=start;j<a.length;j++) {
                if(hit[i]==a[j])
                    break;
            }
            if(j==a.length)
                in[i]=9999;
            in[i]=j;
        }
        int max;
        if(in[0]>in[1] && in[0]>in[2])
            max=0;
        else if(in[1]>in[0] && in[1]>in[2])
            max=1;
        else
            max=2;
        return max;
    }
    public static void main(String[] args) {
        int a[],p[][],n,hit[],i,j,fault=0,hits=0;
        float hr;
        System.out.print("Enter total no. of values: ");
        Scanner s = new Scanner(System.in);
        n=s.nextInt();
        a=new int[n];
        hit=new int[3];
        System.out.println("Enter values");
        for(i=0;i<n;i++)
            a[i]=s.nextInt();
        p=new int[3][n];
        p[0][0]=a[0]; p[1][0]=-1; p[2][0]=-1;
        p[0][1]=a[0]; p[1][1]=a[1]; p[2][1]=-1;
        p[0][2]=a[0]; p[1][2]=a[1]; p[2][2]=a[2];
        hit[0]=p[0][2]; hit[1]=p[1][2]; hit[2]=p[2][2];
        for(j=3;j<n;j++) {
            if(isthere(a[j],p,j-1)>=0) {
                p[0][j]=p[0][j-1];
                p[1][j]=p[1][j-1];
                p[2][j]=p[2][j-1];
                hits++;
            }
            else {
                int c=getmaxindex(hit,j+1,a);
                p[c][j]=a[j];
                if(c==0) {
                    p[1][j]=p[1][j-1];
                    p[2][j]=p[2][j-1];
                }
                else if(c==1) {
                    p[0][j]=p[0][j-1];
                    p[2][j]=p[2][j-1];
                }
                else {
                    p[0][j]=p[0][j-1];
                    p[1][j]=p[1][j-1];
                }
                hit[0]=p[0][j];
                hit[1]=p[1][j];
                hit[2]=p[2][j];
                fault++;
            }
        }
        for(i=0;i<3;i++) {
            for(j=0;j<n;j++) {
                System.out.print(p[i][j]+"\t");
            }
            System.out.println();
        }
        hr=(float)hits/n;
        System.out.println("No. of page faults: "+fault);
        System.out.println("No. of hits: "+(hits));
        System.out.println("Hit rate: "+hr);
    }
}
/*
OUTPUT
Enter total no. of values: 20
Enter values
7 0 1 2 0 3 0 4 2 3 0 3 2 1 2 0 1 7 0 1
7	7	7	2	2	2	2	2	2	2	2	2	2	2	2	2	2	7	7	7	
-1	0	0	0	0	0	0	4	4	4	0	0	0	0	0	0	0	0	0	0	
-1	-1	1	1	1	3	3	3	3	3	3	3	3	1	1	1	1	1	1	1	
No. of page faults: 6
No. of hits: 11
Hit rate: 0.55
*/