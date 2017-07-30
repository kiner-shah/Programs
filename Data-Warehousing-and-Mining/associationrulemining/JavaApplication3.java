/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package associationrulemining;
import java.io.*;
import java.util.*;
/**
 *
 * @author Kiner Shah
 */
class Transactions {
    HashSet<String> trans;
}
public class JavaApplication3 {

    /**
     * @param a
     * @param b
     * @return count count value
     */
    public static int containsInSet(HashSet a, ArrayList<Transactions> b) {
        int count = 0;
        for(Transactions bb : b) {
            boolean flag = false;
            for (Iterator it = a.iterator(); it.hasNext();) {
                String p = (String) it.next();
                if(bb.trans.contains(p)) flag = true;
                else { flag = false; break; }
            }
            if(flag) count++;
        }
        return count;
    }
    /*
    * @param args the command line arguments
     * @throws IOException
    */
    public static void main(String[] args) throws IOException {
        // 
        HashMap<HashSet<String>, Integer> count = new HashMap();
        InputStream in = JavaApplication3.class.getResourceAsStream("transactions.txt");
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        String temp;
        ArrayList<Transactions> t = new ArrayList();
        while((temp = r.readLine()) != null) {
            Transactions temp1 = new Transactions();
            temp1.trans = new HashSet(Arrays.asList(temp.split("[,]")));
            for(String p : temp1.trans) {
                HashSet<String> hs = new HashSet();
                hs.add(p);
                if(count.containsKey(hs)) {
                    int val = count.get(hs);
                    count.remove(hs);
                    count.put(hs, val + 1);
                }
                else {
                    count.put(hs, 1);
                }
            }
            t.add(temp1);
        }
        /*for(Map.Entry<HashSet<String>, Integer> e : count.entrySet()) {
            HashSet hs1 = e.getKey();
            int val = e.getValue();
            System.out.println(hs1 + "\t" + val);
        }*/
        //BufferedReader r1 = new BufferedReader(new InputStreamReader(System.in));
        int threshold = 2, size = 1;
        while(size <= 3) {
            ArrayList<HashSet> l = new ArrayList();
            for(Map.Entry<HashSet<String>, Integer> e : count.entrySet()) {
                HashSet hs1 = e.getKey();
                int val = e.getValue();
                if(hs1.size() == size) {
                    if(val >= threshold) l.add(hs1);
                } 
            }
            for(int i = 0; i < l.size(); i++) {
                for(int j = i + 1; j < l.size(); j++) {
                    HashSet firstset = l.get(i);
                    HashSet secondset = l.get(j), unionset = new HashSet();
                    unionset.addAll(secondset);
                    unionset.addAll(firstset);
                    //System.out.print(unionset + " ");
                    if(unionset.size() == size+1) {
                        int value = containsInSet(unionset, t); //System.out.println(unionset + " " + value);
                        if(value >= threshold) count.put(unionset, value);
                    }
                }
            }
            size++;
        }
        for(Map.Entry<HashSet<String>, Integer> e : count.entrySet()) {
            HashSet hs1 = e.getKey();
            int val = e.getValue();
            System.out.println("Frequent items are:");
            if(hs1.size() == 3) {
                if(val >= threshold) System.out.println(hs1 + " with frequency " + val);
            } 
        }
    }
    
}
