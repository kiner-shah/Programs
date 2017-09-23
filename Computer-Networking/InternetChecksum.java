/*
KINER SHAH TE-COMP(B3) 1311113
*/
import java.util.*;
class InternetChecksum {
    public static char[] tohex(int a) {
        char x[]=new char[2];
        char hex[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        x[1]=hex[a%16];
        x[0]=hex[a/16];
        return x;	
    }
    public static int findpos(char a, char b[]) {
        int i;
        for(i=0;i<b.length;i++) {
            if(b[i]==a)
                break;
        }
        return i;
    }
    public static char[] hexadd(char a[][], int len) {
        char hex[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        int i,j,k=0,sum,car=0;
        char b[]=new char[4];
        for(j=3;j>=0;j--) {
            sum=0;
            for(i=0;i<len;i++) {
                sum+=findpos(a[i][j],hex);
            }
            //System.out.println(j);
            b[j]=hex[(sum+car)%16]; 
            car=(sum+car)/16;
        }
        b[3]+=car;
        return b;
    }
    public static void main(String args[]) {
        /*TRANSMITTER PART*/
	String t; int i,j; char trans[],ftrans[][];
	System.out.print("Enter the string to be transmitted: ");
        char hex[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	Scanner s=new Scanner(System.in);
	t=s.next();
	int n;
	if(t.length()%2==0) {
            n=t.length()/2;
            trans=t.toCharArray();
	}
	else {
            n=t.length()/2+1;
            t=t.concat("0");
            trans=t.toCharArray();
	}
	int k=0;
	ftrans=new char[n][4];
	for(i=0;i<trans.length;i+=2) {
            char x[]=tohex(trans[i]);
            char y[]=tohex(trans[i+1]);
            ftrans[k][0]=x[0]; ftrans[k][1]=x[1];
            ftrans[k][2]=y[0]; ftrans[k][3]=y[1];
            k++;
	}
        for(i=0;i<n;i++) {
            for(j=0;j<4;j++)
                System.out.print(ftrans[i][j]+"");
            System.out.println();
        }
        System.out.println("-----");
	char add[]=hexadd(ftrans,n);
        for(i=0;i<add.length;i++)
            System.out.print(add[i]+"");
        System.out.println();
        System.out.print("Data sent will be\n");
        for(i=0;i<n;i++) {
            for(j=0;j<4;j++)
                System.out.print(ftrans[i][j]+"");
            System.out.println();
        }
        for(i=0;i<add.length;i++) {
            System.out.print(hex[15-findpos(add[i],hex)]+"");
        }
        System.out.println();
        /*RECEIVER PART*/
        String x; int m; char c[][];
        System.out.print("Enter no. of codewords received: ");
        m=s.nextInt();
        c=new char[m][4];
        i=0;
        System.out.println("Enter codewords");
        while(i<m) {
            x=s.next();
            c[i]=x.toCharArray();
            i++;
        }
        char add1[]=hexadd(c,m-1);
        char rec[][]=new char[2][4];
        System.arraycopy(add1, 0, rec[0], 0, 4);
        System.arraycopy(c[m-1], 0, rec[1], 0, 4);
        char add2[]=hexadd(rec,2);
        for(i=0;i<4;i++) {
            if(add2[i]!='F')
                break;
        }
        if(i<4)
            System.out.println("Error exists");
        else
            System.out.println("No error");
    }
}









