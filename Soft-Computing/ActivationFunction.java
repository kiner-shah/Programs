import java.util.*;
import java.io.*;
class ActivationFunction {
	public static void main(String args[]) throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter three numbers");
		int x1, x2, x3;
		String temp[] = r.readLine().split("\\s+");
		x1 = Integer.parseInt(temp[0]);
		x2 = Integer.parseInt(temp[1]);
		x3 = Integer.parseInt(temp[2]);
		System.out.println("Enter the weights");
		temp = r.readLine().split("\\s+");
		int w1 = Integer.parseInt(temp[0]);
		int w2 = Integer.parseInt(temp[1]);
		int w3 = Integer.parseInt(temp[2]);
		boolean flag = true;
		double sum = (double) x1 * w1 + x2 * w2 + x3 * w3;
		while(flag) {
			System.out.println("\n1. Identity\n2. Binary Step\n3. Binary Sigmoid\n4. Bipolar Sigmoid\n5. Binary Step Function");
			System.out.print("Select any one function: ");
			int c = Integer.parseInt(r.readLine());
			double ans = 0.0;
			switch(c) {
				case 1:
					ans = sum;
				break;
				case 2:
					if(sum >= 0) ans = 1;
					else ans = 0;
				break;
				case 3:
					ans = (double) 1 / (1 + Math.exp(sum));
				break;
				case 4:
					ans = (double) (1 - Math.exp(sum)) / (1 + Math.exp(sum));
				break;
				case 5:
					if(sum >= 0) ans = 1;
					else ans = -1;
				break;
				default: flag = false; System.out.println("Invalid option"); break;
			}
			System.out.println("Output: " + ans);
		}
	}
}
