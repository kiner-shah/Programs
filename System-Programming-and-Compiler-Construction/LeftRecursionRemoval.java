import java.util.*;
import java.io.*;
class NT {
    char nt;
    Vector<String> prod;
}
class LeftRecursionRemoval {
    public static String[] getrules(NT a[], char sym) {
        int i;
        for(i = 0; i < a.length; i++) {
            if(a[i].nt == sym) break;
        }
       String rules[] = new String[a[i].prod.size()];
        for(int j = 0; j < rules.length; j++) {
            rules[j] = a[i].prod.get(j);
        }
        return rules;
    }
    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter no. of non-terminals: ");
        int n = Integer.parseInt(r.readLine());
        NT a[] = new NT[100];
        for (int i = 0; i < n; i++) {
            a[i] = new NT();
            System.out.print("Enter non-terminal symbol: ");
            a[i].nt = r.readLine().charAt(0);
            System.out.print("Enter no. of production rules: ");
            a[i].prod = new Vector();
            int np = Integer.parseInt(r.readLine());
            for (int j = 0; j < np; j++) {
                System.out.print("Enter rule "+(j+1)+": ");
                a[i].prod.addElement(r.readLine());
            }
        }
        for (int i = 0; i < n; i++) { //for each non-terminal on lhs
            for (int j = 0; j < n; j++) { //for each non-terminal
                for (int k = 0; k < a[i].prod.size(); ) { //for each rule corresponding to non-terminal on lhs
                    String temp = a[i].prod.get(k); //get the rule
                    if(a[j].nt == temp.charAt(0) && a[j].nt == a[i].nt) break;
                    if(a[j].nt == temp.charAt(0)) { //if of form A->B§ where B is the non-terminal corresponding to inner loop
                        a[i].prod.remove(k);
                        String store = temp.substring(1);
                        String b[] = getrules(a,a[j].nt); //get the rules of B
                        for(int l = 0; l < b.length; l++) {
                            a[i].prod.addElement(b[l] + store);
                        }
                    }
                    else k++;
                }
                //Remove left recursion if any
                Vector<String> v1 = new Vector(); // for storing non-recursive productions
                Vector<String> v2 = new Vector(); //for storing recursive productions
                for(int k = 0; k < a[i].prod.size(); k++) {
                    String temp = a[i].prod.get(k);
                    if(temp.charAt(0) == a[i].nt) {
                        v2.addElement(temp.substring(1));
                    }
                    else v1.addElement(temp);
                }
                if(!v2.isEmpty()) {
                    a[i].prod.removeAllElements();
                    a[n] = new NT();
                    a[n].nt = 'Z';
                    a[n].prod = new Vector();
                    for(int k = 0; k < v1.size(); k++) a[i].prod.addElement(v1.get(k) + 'Z');
                    for(int k = 0; k < v2.size(); k++) a[n].prod.addElement(v2.get(k) + 'Z');
                    a[n].prod.addElement("e");
                    n++;
                    v1.clear(); v2.clear();
                }
            }
        }
        for(int i = 0; i < n; i++) {
            int j;
            System.out.print(a[i].nt+"->");
            for(j = 0; j < a[i].prod.size() - 1; j++) System.out.print(a[i].prod.get(j) + " | ");
            System.out.println(a[i].prod.get(j));
        }
    }
}
/*
OUTPUT
Enter no. of non-terminals: 4
Enter non-terminal symbol: A
Enter no. of production rules: 2
Enter rule 1: Bxy
Enter rule 2: x
Enter non-terminal symbol: B
Enter no. of production rules: 1
Enter rule 1: CD
Enter non-terminal symbol: C
Enter no. of production rules: 2
Enter rule 1: A
Enter rule 2: c
Enter non-terminal symbol: D
Enter no. of production rules: 1
Enter rule 1: d
A->xZ | cDxyZ
B->AD | cD
C->c | xZ | cDxyZ
D->d
Z->e | dxyZ
*/