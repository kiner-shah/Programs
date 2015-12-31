import java.util.*;
import java.math.BigInteger;

class Main
{
	public static void main (String[] args) throws java.lang.Exception
	{
		int t; BigInteger a,b;
		Scanner s=new Scanner(System.in);
		t=s.nextInt();
		while(t-- > 0) {
			a=s.nextBigInteger(); b=s.nextBigInteger();
			System.out.println(a.multiply(b));
		}
	}
}
