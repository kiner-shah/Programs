import java.util.*;
import java.lang.*;
import java.math.BigInteger;
class Main
{
	public static void main (String[] args) throws java.lang.Exception
	{
		int i=10; BigInteger tot,n,x,y,two=new BigInteger("2");
		Scanner s=new Scanner(System.in);
		while(i-- > 0) {
			tot=s.nextBigInteger();
			n=s.nextBigInteger();
			x=(tot.add(n)).divide(two);
			y=(tot.subtract(n)).divide(two);
			System.out.print(x+"\n"+y+"\n");
		}
	}
}
