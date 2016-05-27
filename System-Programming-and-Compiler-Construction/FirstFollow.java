import java.util.*;
import java.io.*;
class Rule {
    char nt;
    String prod[];
}
class FirstFollow {
    public static Rule r1[];
    public static Vector<String> findfirstset(String prod) {
        Vector<String> t = new Vector<String>();
        if(prod.charAt(0) < 'A' || prod.charAt(0) > 'Z') {
            t.addElement(""+prod.charAt(0)); //System.out.println(prod.charAt(0));
        }
        else {
            t.addElement("First("+prod.charAt(0)+")"); //System.out.println("First of "+prod.charAt(0));
        }
        return t;
    }
    public static Vector<String> findfollowset(char k, HashMap<Character,Vector<String>> f) {
        Vector<String> t = new Vector<String>();
        for(int i = 0; i < r1.length; i++) {
            for(int j = 0; j < r1[i].prod.length; j++) {
                if(r1[i].prod[j].contains(""+k)) {
                    int pos = r1[i].prod[j].indexOf(k);
                    if(pos == r1[i].prod[j].length() - 1) {
                        if(r1[i].nt != k) t.addElement("Follow("+r1[i].nt+")");
                    }
                    else {
                        char c = r1[i].prod[j].charAt(pos + 1);
                        if(c != 'ε' && (c < 'A' || c > 'Z')) t.addElement(""+c);
                        else {
                            Vector<String> v = f.get(c);
                            for(int l = 0; l < v.size(); l++) {
                                if(v.get(l).equals("?")) {
                                    pos += 2;
                                    if(pos == r1[i].prod[j].length()) {
                                        if(r1[i].nt != k) t.addElement("Follow("+r1[i].nt+")");
                                    }
                                    else {
                                        char cc = r1[i].prod[j].charAt(pos);
                                        if(cc != 'ε' && (cc < 'A' || cc > 'Z')) t.addElement(""+cc);
                                        //else  //Not done
                                    }
                                }
                                else t.addElement(v.get(l));
                            }
                        }
                    }
                }
            }
        }
        return t;
    }
    public static void main(String[] args) throws Exception {
        /* ASCII value for epsilon is 949 */
        /* Example: 
        E -> TP
        P -> +TP | ε
        T -> i
        */
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Note:\n1.PLEASE INPUT NON-RECURSIVE GRAMMAR\n2.USE ASCII VALUE 949 FOR EPSILON (Alt + 238)");
        System.out.print("Enter no. of non-terminals: ");
        int n = Integer.parseInt(r.readLine());
        r1 = new Rule[n];
        for (int i = 0; i < n; i++) {
            r1[i] = new Rule();
            System.out.print("Enter non-terminal symbol: ");
            char sym = r.readLine().charAt(0);
            r1[i].nt = sym;
            System.out.print("Enter no. of production rules for "+r1[i].nt+": ");
            int no = Integer.parseInt(r.readLine());
            String p[] = new String[no];
            r1[i].prod = new String[no];
            for (int j = 0; j < no; j++) {
                System.out.print("Enter RHS of production rule "+(j+1)+": ");
                p[j] = r.readLine();
            }
            System.arraycopy(p, 0, r1[i].prod, 0, no);
        }
        /* FIRST SET */
        System.out.println("FIRST SET");
        HashMap<Character,Vector<String>> first = new HashMap();
        for(int i = 0; i < n; i++) {
            char key = r1[i].nt;
            Vector<String> v = new Vector<String>();
            for(int j = 0; j < r1[i].prod.length; j++) {
                Vector<String> t = findfirstset(r1[i].prod[j]);
                v.addAll(t);
            }
            first.put(key, v);
        }
        for (Map.Entry<Character, Vector<String>> entry : first.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            Vector<String> temp = entry.getValue();
            for(int j = 0; j < temp.size(); j++) {
                if(temp.get(j).equals("?")) System.out.print('ε'+" "); //because of some problem with printing of ε
                else System.out.print(temp.get(j)+" ");
            }
            System.out.println();
        }
        /* FOLLOW SET */
        System.out.println("\nFOLLOW SET");
        HashMap<Character,Vector<String>> follow = new HashMap();
        for(int i = 0; i < n; i++) {
            char key = r1[i].nt;
            Vector<String> v = new Vector<String>();
            if(i == 0) v.addElement("$");
            Vector<String> t = findfollowset(key,first);
            Set<String> ss = new HashSet(t);
            v.addAll(ss);
            follow.put(key, v);
        }
        for (Map.Entry<Character, Vector<String>> entry : follow.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            Vector<String> temp = entry.getValue();
            for(int j = 0; j < temp.size(); j++) {
                if(temp.get(j).equals("?")) System.out.print('ε'+" "); //because of some problem with printing of ε
                else System.out.print(temp.get(j)+" ");
            }
            System.out.println();
        }
    }
}
