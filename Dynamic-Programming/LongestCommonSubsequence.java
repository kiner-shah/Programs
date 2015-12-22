/*Longest Common Subsequence
* by, KINER SHAH
* 22nd December 2015
*/
import java.util.*;
class LongestCommonSubsequence {
    public static void main(String[] args) {
        int c[][],i,j; String x,y;
        Scanner s = new Scanner(System.in);
        System.out.print("Enter first string: ");
        x=s.next(); 
        System.out.print("Enter second string: ");
        y=s.next();
        c=new int[x.length()+1][y.length()+1];
        for(i=1;i<x.length()+1;i++) 
            c[i][0]=0;
        for(j=0;j<y.length()+1;j++)
            c[0][j]=0;
        for(i=1;i<x.length()+1;i++) {
            for(j=1;j<y.length()+1;j++) {
                if(x.charAt(i-1)==y.charAt(j-1))
                    c[i][j]=c[i-1][j-1]+1;
                else if(c[i-1][j]>=c[i][j-1])
                    c[i][j]=c[i-1][j];
                else
                    c[i][j]=c[i][j-1];
            }
        }
        i=x.length(); j=y.length();
        Vector<Character> v=new Vector<Character>(); 
        while(i>0 && j>0) {    
            if(c[i-1][j]==c[i][j])
                    i--;
            else if(c[i-1][j]!=c[i][j]) {
                if(c[i][j-1]==c[i][j])
                    j--;
                else {
                    j--; i--; v.add(0, x.charAt(i));
                }
            }
        }
        Enumeration e=v.elements();
        System.out.print("Longest common subsequence: ");
        while(e.hasMoreElements()) {
            System.out.print((Character)e.nextElement());
        }
        System.out.println();
    }
}
