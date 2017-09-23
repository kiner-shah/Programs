import java.util.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class Hamming
{
    public static void main (String[] args) throws java.lang.Exception
    {
        // transmission part
        String a; 
        StringBuffer b;
        int j,code[],i,k;
        Vector<Integer> v=new Vector<Integer>();
        for(i=0;i<20;i++)
            v.addElement((int)Math.pow(2,i));
        Scanner s=new Scanner(System.in);
        System.out.print("Enter the string to be transmitted: ");
        a=s.next();
        b=new StringBuffer(a);
        b.reverse();
        code=new int[100];
        Arrays.fill(code,0);
        for(i=1,j=0;j<b.length();i++) {
            if(v.contains(i)==true) 
                continue;
            code[i]=b.charAt(j)-'0'; j++;
        }
        for(k=1;k<=i-1;k++) {
            if(v.contains(k)==true) {
                j=k+1; int t=0;
                while(j<=i-1) {
                    String temp=Integer.toBinaryString(j);
                    StringBuffer temp1=new StringBuffer(temp);
                    temp1.reverse();
                    //System.out.println(temp1);
                    int pos=v.indexOf(k);
                    if(temp1.charAt(pos)=='1') {
                            t=t ^ code[j];
                            //System.out.println(code[j]);
                    }
                    j++;
                }
                //System.out.println(t);
                code[k]=t;
            }
        }
        System.out.print("Hamming code is: ");
        for(k=i-1;k>=1;k--)
                System.out.print(code[k]);
        System.out.println();
        //reciever part
        String rec; int code1[],code2[],l;
        System.out.print("Enter the string received: ");
        rec=s.next(); //received string
        StringBuffer reccode=new StringBuffer(rec);
        reccode.reverse();
        List<Integer> x=new ArrayList<Integer>();
        //System.out.println(reccode);
        code1=new int[reccode.length()+1];
        code2=new int[10];
        for(i=0;i<reccode.length();i++)
                code1[i+1]=reccode.charAt(i)-'0';
        /*for(i=1;i<reccode.length()+1;i++)
                System.out.println(code1[i]);*/
        for(k=1;k<=i;k++) {
            if(v.contains(k)==true) {
                j=k; int t=0;
                while(j<=i) {
                    String temp=Integer.toBinaryString(j);
                    StringBuffer temp1=new StringBuffer(temp);
                    temp1.reverse();
                    //System.out.println(temp1);
                    int pos=v.indexOf(k);
                    if(temp1.charAt(pos)=='1') {
                            t=t ^ code1[j];
                            //System.out.println(t);
                    }
                    j++;
                }
                x.add(0,t);
            }
        }
        if(x.contains(1)==false)
            System.out.println("No error");
        else {
            j=0; int sum=0;
            for(i=x.size()-1;i>=0;i--,j++)
                sum+=Math.pow(2,j)*x.get(i);
            System.out.println("Error at position "+(sum));
        }
    }
}
/*
Enter the string to be transmitted: 1011101010101010010111
Hamming code is: 101110101011010100100110101
Enter the string received: 110101011110
Error at position 9
*/