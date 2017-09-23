/*
KINER SHAH TE-B3(COMP) 1311113
*/
import java.util.*;
class CRC {
    public static void main(String[] args) {
        /*TRANSMISSION PART*/
        String t,d; int trans[],div[],i,j,k,temp[],zer[],fintr[];
        Scanner s = new Scanner(System.in);
        System.out.print("Enter data to be transmitted: ");
        t=s.next(); //let length be k
        System.out.print("Enter divisor: ");
        d=s.next(); //let length be n-k+1, n-length of CRC code
        trans=new int[t.length()+d.length()-1];
        fintr=new int[t.length()+d.length()-1];
        for(i = 0; i < t.length(); i++)
            trans[i]=t.charAt(i)-'0';
        System.arraycopy(trans, 0, fintr, 0, trans.length);
        div=new int[d.length()];
        zer=new int[d.length()];
        temp=new int[d.length()];
        for(i = 0; i < d.length(); i++)
            div[i]=d.charAt(i)-'0';
        for(i = 0; i < t.length(); i++) {
            if(trans[i]==0)
                System.arraycopy(zer, 0, temp, 0, zer.length);
            else
                System.arraycopy(div, 0, temp, 0, div.length);
            j=0; k=i;
            while(j<d.length()) {
                if(temp[j]==trans[k])
                    trans[k]=0;
                else
                    trans[k]=1;
                j++; k++;
            }
        }
        for(i = t.length(); i<trans.length; i++)
            fintr[i]=trans[i];
        System.out.print("CRC code is: ");
        for(i=0;i<trans.length;i++)
            System.out.print(fintr[i]);
        
        
        /*RECEIVER PART*/
        String r,d1; int rec[],div1[],temp1[],zer1[];
        System.out.print("\nEnter the received string: ");
        r=s.next();
        System.out.print("Enter the divisor used: ");
        d1=s.next();
        rec=new int[r.length()];
        div1=new int[d1.length()];
        temp1=new int[d1.length()];
        zer1=new int[d1.length()];
        for(i = 0; i < r.length(); i++)
            rec[i]=r.charAt(i)-'0';
        for(i = 0; i < d1.length(); i++)
            div1[i]=d1.charAt(i)-'0';
        for(i = 0; i < r.length()-d1.length()+1; i++) {
            if(rec[i]==0)
                System.arraycopy(zer1, 0, temp1, 0, zer1.length);
            else
                System.arraycopy(div1, 0, temp1, 0, div1.length);
            j=0; k=i;
            while(j<d1.length()) {
                if(temp1[j]==rec[k])
                    rec[k]=0;
                else
                    rec[k]=1;
                //System.out.print(rec[k]);
                j++; k++;
            }
            //System.out.println();
        }
        int flag=0;
        for (i = r.length()-d1.length()+1; i < r.length(); i++) {
            if(rec[i]==1)
            {    flag=1; break; }
        }
        if(flag==1)
            System.out.println("Error exists");
        else
            System.out.println("No error");
    }
}
/*
OUTPUT
Enter data to be transmitted: 1101011
Enter divisor: 11001
CRC code is: 11010111010
Enter the received string: 11111111010
Enter the divisor used: 11001
Error exists
*/