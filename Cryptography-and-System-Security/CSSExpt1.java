/* KINER SHAH
 * TRANSPOSITION CIPHER
 * BE COMPS B3, 1311113
 */
import java.util.*;
import java.io.*;
class Pair {
	char first;
	int second;
}
class CSSExpt1 {
	public static void main(String args[]) throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		/* ENCRYPTION */
		String key, plaintext;
		//PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.in)));
		System.out.print("ENCRYPTION\nEnter plaintext: "); plaintext = r.readLine();
		System.out.print("Enter key: "); key = r.readLine();
		plaintext = plaintext.replaceAll(" ","");
		int rows, cols = key.length(), len = plaintext.length();
		if(len % key.length() != 0) rows = len / key.length() + 1;
		else rows = len / key.length();
		char m[][] = new char[rows+1][cols];
		int i,j, k = 0;
		for(i = 0; i < key.length(); i++) m[0][i] = key.charAt(i);
		for(i = 1; i < rows + 1; i++) {
			for(j = 0; j < cols; j++) {
				if(k < plaintext.length()) {
					m[i][j] = plaintext.charAt(k);
					k++;
				}
			}
		}
		/*for(i = 0; i < rows; i++) {
			for(j = 0; j < cols; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}*/
		Pair p[] = new Pair[key.length()];
		for(i = 0; i < key.length(); i++) {
			p[i] = new Pair();
			p[i].first = m[0][i];
		}
		Arrays.sort(p, new Comparator<Pair>() {
			public int compare(Pair a, Pair b) {
				return (a.first < b.first ? -1 : 1);
			}
		});
		boolean flag[] = new boolean[key.length()];
		Arrays.fill(flag, false);
		String ciphertext = "";
		for(i = 0; i < key.length(); i++) p[i].second = i;
		for(i = 0; i < p.length; i++) {
			//System.out.println(p[i].first);
			for(j = 0; j < cols; j++) {
				if(!flag[j] && p[i].first == m[0][j]) {
					for(k = 1; k < rows+1; k++) ciphertext += m[k][j];
					ciphertext += ' ';
					flag[j] = true;
				}
			}
		}
		System.out.println("Ciphertext generated is: " + ciphertext);
		/* DECRYPTION */
		System.out.print("\nDECRYPTION\nEnter ciphertext: ");
		ciphertext = r.readLine();
		System.out.print("Enter key: ");
		key = r.readLine();
		String c[] = ciphertext.split("\\s+");
		char carr[] = key.toCharArray();
		Arrays.sort(carr);
		Pair q[] = new Pair[carr.length];
		for(i = 0; i < carr.length; i++) {
			q[i] = new Pair();
			q[i].first = carr[i];
			q[i].second = i;
		}
		flag = new boolean[carr.length];
		m = new char[ciphertext.length() / key.length()][key.length()];
		j = 0;
		for(i = 0; i < key.length(); i++) {
			for(Pair a : q) {
				if(!flag[a.second] && a.first == key.charAt(i)) {
					flag[a.second] = true;
					//System.out.println(c[a.second]);
					for(k = 0; k < c[a.second].length(); k++) m[k][j] = c[a.second].charAt(k);
					j++;
				}
			}
		}
		System.out.print("Plaintext retrieved: ");
		for(i = 0; i < rows; i++) {
			for(j = 0; j < cols; j++) System.out.print(m[i][j]);
		}
	}
}