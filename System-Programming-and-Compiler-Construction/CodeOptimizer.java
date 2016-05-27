/*
Author: KINER SHAH 
Experiment : Code optimization
Subject: System Programming and Compiler Construction
Date: 17th March 2016
*/
import java.util.*;
import java.io.*;
class CodeOptimizer {
    public static void main(String[] args) throws Exception {
        int i,j,n;
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Common Sub-Expression Elimination\nEnter no. of lines of code: ");
        n = Integer.parseInt(r.readLine());
        String code[] = new String[n];
        /* Elimination of common sub-expression */
        System.out.println("Enter code");
        Vector<String> exp = new Vector<String>();
        StringBuffer c = new StringBuffer(), p = new StringBuffer();
        for(i = 0; i < n; i++) code[i] = r.readLine();
        for(i = 0; i < n; i++) {
            int pos = code[i].indexOf('=');
            String temp = code[i].substring(pos + 2);
            boolean flag = false;
            for(j = 0; j < temp.length(); j++) {
                char d = temp.charAt(j);
                if(d >= 'a' && d <= 'z') { p.append(d); c.append(d); flag = true; }
                else if(d >= 'A' && d <= 'Z') { p.append(d); c.append(d); flag = true; }
                else if(d == ' ') {
                    if(flag && !exp.contains(c.toString())) exp.addElement(c.toString());
                    if(flag && !exp.contains(p.toString())) exp.addElement(p.toString());
                    c.delete(0, c.length()); p.delete(0, p.length());
                    flag = false;
                }
                else {
                    p.append(d);
                    if(!exp.contains(c.toString())) exp.addElement(c.toString());
                    c.delete(0, c.length());
                }
            }
        }
        System.out.println("Optimized code");
        HashMap<String,String> m = new HashMap<String,String>();
        for(i = 0; i < exp.size(); i++) {
            StringBuffer ss = new StringBuffer();
            String t = exp.get(i), pp = "";
            boolean flag = false;
            for(j = 0; j < t.length(); j++) {
                char d = t.charAt(j);
                if((d >= 'a' && d <= 'z') || (d >= 'A' && d <= 'Z')) {
                    ss.append(d);
                    if(j == t.length() - 1 && m.containsKey(""+d)) pp += m.get(""+d);
                }
                else {
                    if(exp.contains(ss.toString()) && m.containsKey(ss.toString())) pp += m.get(ss.toString()) + d;
                    //System.out.println(pp);
                    flag = true;
                    ss.append(d);
                }
            }
            //System.out.println(pp);
            if(!flag && ss.length() > 0 && exp.contains(ss.toString()) && !m.containsKey(ss.toString())) m.put(ss.toString(), "t"+(m.size()+1));
            else if(flag && !m.containsKey(pp)) m.put(pp, "t"+(m.size()+1));
            ss.delete(0,ss.length());
        }
        /*Collection<String> ll = m.values();
        System.out.println(ll);*/
        Set<Map.Entry<String, String>> set = m.entrySet();
        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(set);
        Collections.sort(list, new Comparator<Map.Entry<String, String>>()
        {
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        });
        for(Map.Entry<String, String> entry : list){
            System.out.println(entry.getValue()+" = "+entry.getKey());
        }
        for(i = 0; i < n; i++) {
            int pos = code[i].indexOf('=');
            String temp = code[i].substring(pos+2);
            System.out.print(code[i].substring(0,pos+1) + " ");
            String t[] = temp.split("\\ ");
            for(j = 0; j < t.length; j++) {
                if(m.containsKey(t[j])) System.out.print(m.get(t[j])+" ");
                else System.out.print(t[j]+" ");
            }
        }
        System.out.println();
        /* Loop optimization */
        System.out.print("\nLoop Optimization\nEnter no. of lines of code: ");
        n = Integer.parseInt(r.readLine()); code = new String[n];
        System.out.println("Enter code");
        for(i = 0; i < n; i++) code[i] = r.readLine();
        Vector<String> lv = new Vector<String>(); //Loop variable
        Vector<String> ex = new Vector<String>(); //for storing expressions with no loop variable
        for(i = 0; i < n; i++) {
            if(code[i].startsWith("for")) {
                int pos = code[i].indexOf(";");
                String temp[] = code[i].substring(4, pos).split(",");
                for(j = 0; j < temp.length; j++) {
                    lv.addElement(""+temp[j].charAt(0));
                }
            }
            else {
                boolean nl = false,flag = false;
                StringBuffer ss = new StringBuffer();
                for(j = 0; j < code[i].length(); j++) {
                    //System.out.println(flag+" "+code[i].charAt(j));
                    if(!flag && code[i].charAt(j) == '[') flag = true;
                    else if(flag && code[i].charAt(j) == ']') flag = false;
                    else if(flag && !lv.contains(""+code[i].charAt(j))) nl = true; 
                    else if(nl && (code[i].charAt(j) == ' ' || code[i].charAt(j) == ';')) {
                        ex.addElement(ss.toString());
                        ss.delete(0,ss.length());
                        //System.out.println(ss.length());
                        nl = false;
                    }
                    else if(!nl && (code[i].charAt(j) == ' '|| code[i].charAt(j) == ';')) {
                        ss.delete(0,ss.length());
                        //System.out.println(ss.length());
                    }
                    ss.append(code[i].charAt(j));
                    //System.out.println(code[i].charAt(j) + " " + nl);
                    //System.out.println(ss);
                }
                ss.delete(0,ss.length());
            }
        }
        System.out.print("Loop variable(s) are: ");
        for(i = 0; i < lv.size() - 1; i++) System.out.print(lv.get(i) + " ");
        System.out.print(lv.get(i) + "\n");
        System.out.println("Optimized loop");
        for(i = 0; i < ex.size(); i++) {
            System.out.println("t"+(i+1)+"="+ex.get(i)+";");
        }
        for(i = 0; i < n; i++) {
            if(code[i].startsWith("for")) {
                System.out.println(code[i]);
            }
            else {
                boolean nl = false,flag = false;
                StringBuffer ss = new StringBuffer();
                for(j = 0; j < code[i].length(); j++) {
                    //System.out.println(flag+" "+code[i].charAt(j));
                    if(!flag && code[i].charAt(j) == '[') flag = true;
                    else if(flag && code[i].charAt(j) == ']') flag = false;
                    else if(flag && !lv.contains(""+code[i].charAt(j))) nl = true;  
                    else if(nl && (code[i].charAt(j) == ' ' || code[i].charAt(j) == ';')) {
                        System.out.print("t"+(ex.indexOf(ss.toString()) + 1) +code[i].charAt(j));
                        ss.delete(0,ss.length());
                        //System.out.println(ss.length());
                        nl = false;
                    }
                    else if(!nl && (code[i].charAt(j) == ' '|| code[i].charAt(j) == ';')) {
                        System.out.print(ss.toString()+code[i].charAt(j));
                        ss.delete(0,ss.length());
                        //System.out.println(ss.length());
                    }
                    ss.append(code[i].charAt(j));
                    //System.out.println(code[i].charAt(j) + " " + nl);
                    //System.out.println(ss);
                }
                if(!nl && !ss.toString().equals(";")) System.out.println("\n"+ss.toString());
                ss.delete(0,ss.length());
            }
        }
    }
}
