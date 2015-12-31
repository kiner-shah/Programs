/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.math.*;

/* Name of the class has to be "Main" only if the class is public. */
class Ideone
{
	public static void main (String[] args)
	{
		// your code goes here
		Scanner s=new Scanner(System.in);
		int t,i,n;
		t=s.nextInt();
		BigInteger temp,sum,x;
		while(t-- > 0) {
			n=s.nextInt(); temp=new BigInteger(""+n);
			sum=new BigInteger("0");
			for(i=0;i<n;i++) {
				x=s.nextBigInteger();
				sum=sum.add(x);
			}
			if(sum.remainder(temp).compareTo(new BigInteger("0"))==0)
				System.out.println("YES");
			else
				System.out.println("NO");
		}
	}
}
