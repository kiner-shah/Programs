/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class Ideone
{
	public static void main (String[] args) throws java.lang.Exception
	{
		// your code goes here
		int t; String s;
		Vector<String> v=new Vector<String>();
		BufferedReader r=new BufferedReader(new InputStreamReader(System.in));
		t=Integer.parseInt(r.readLine());
		s=r.readLine();
		while(t-- > 0) {
			s=r.readLine();
			for(String a : s.split("\\ "))
				v.addElement(a);
			for(int i=0;i<v.size();i++) {
				String a=v.elementAt(i);
				if(a.contains("machula")) {
					if(i==0) {
						int ans=Integer.parseInt(v.elementAt(4))-Integer.parseInt(v.elementAt(2));
						System.out.println(ans+" + "+v.elementAt(2)+" = "+v.elementAt(4));
					}
					else if(i==2) {
						int ans=Integer.parseInt(v.elementAt(4))-Integer.parseInt(v.elementAt(0));
						System.out.println(v.elementAt(0)+" + "+ans+" = "+v.elementAt(4));
					}
					else {
						int ans=Integer.parseInt(v.elementAt(0))+Integer.parseInt(v.elementAt(2));
						System.out.println(v.elementAt(0)+" + "+v.elementAt(2)+" = "+ans);
					}
					break;
				}
			}
			v.clear();
			s=r.readLine();
		}
	}
}
