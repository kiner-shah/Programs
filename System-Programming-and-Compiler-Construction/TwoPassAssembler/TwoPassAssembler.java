/*
** Author: KINER SHAH
** Subject: System Programming And Compiler Construction
** Date: 11th February 2016
*/
import java.util.*;
import java.io.*;
class SymbolTable { //Class for symbol table
    String sym;
    int length,val;
    char relocation;
    SymbolTable(String a, int b, int c, char d) {
        this.sym = a;
        this.val = b;
        this.length = c;
        this.relocation = d;
    }
}
class LiteralTable { //Class for literal table
    String sym;
    int length,val;
    char relocation;
    LiteralTable(String a, int b, int c, char d) {
        this.sym = a;
        this.val = b;
        this.length = c;
        this.relocation = d;
    }
}
class MOT { //Class for machine opcode table (MOT) 
    String name;
    int length;
    MOT(String a, int b) {
        this.name = a;
        this.length = b;
    }
}
class TwoPassAssembler {
    public static boolean searchp(String a[], String b) { //to search in pseudo opcode table
        int i;
        for(i = 0; i < a.length; i++) {
            if(a[i].equals(b)) break;
        }
        if(i != a.length) return true;
        else return false;
    }
    public static int searchm(MOT a[], String b) { //to search in machine opcode table and return the index if found
        int i,pos = -1;
        for(i = 0; i < a.length; i++) {
            if(a[i].name.equals(b)) {
                pos = i;
                break;
            }
        }
        return pos;
    }
    public static int searchst(Vector<SymbolTable> v, String s) {
        int i,pos = -1;
        for(i = 0; i < v.size(); i++) {
            SymbolTable t = v.get(i);
            if(t.sym.equals(s)) { pos = i; break; }
        }
        return pos;
    }
    public static int searchlt(Vector<LiteralTable> v, String s) {
        int i,pos = -1;
        for(i = 0; i < v.size(); i++) {
            LiteralTable t = v.get(i);
            if(t.sym.equals(s)) { pos = i; break; }
        }
        return pos;
    }
    public static int findnearest(int BT[], int val) {
        int i, min = 99999, pos = 0;
        for(i = 0; i < BT.length; i++) {
            if(Math.abs(BT[i] - val) < min) { min = Math.abs(BT[i] - val); pos = i; }
        }
        return pos;
    }
    public static void main(String[] args) throws Exception {
        String pot[] = {"START","END","LTORG","DC","DS","DROP","USING","EQU"};
        MOT mot[] = new MOT[10];
        mot[0] = new MOT("LA",4);
        mot[1] = new MOT("SR",2);
        mot[2] = new MOT("L",4);
        mot[3] = new MOT("AR",2);
        mot[4] = new MOT("A",4);
        mot[5] = new MOT("C",4);
        mot[6] = new MOT("BNE",4);
        mot[7] = new MOT("LR",2);
        mot[8] = new MOT("ST",4);
        mot[9] = new MOT("BR",2);
        BufferedReader r = new BufferedReader(new FileReader("C:\\Users\\user\\Documents\\NetBeansProjects\\GUIFormExamples\\src\\input.txt"));
        String d;
        Vector<SymbolTable> st = new Vector<SymbolTable>();
        Vector<LiteralTable> lt = new Vector<LiteralTable>();
        int loc_count = 0; //location counter
        int lc = 0; //line count;
        int BT[] = new int[16]; //Base Table: 1st column-availability, 2nd column-content
        for(int i = 0; i < 16; i++)  BT[i] = 0;  
        /* PASS 1 */
        while((d = r.readLine()) != null) { //read till eof of input file
            d = d.trim();
            if(d.isEmpty()) continue;
            String temp[] = d.split("\\s+");
            if(lc == 0) { //add the first line i.e name of program as label for START instruction
                st.addElement(new SymbolTable(temp[0],0,1,'R'));
            }
            else {
                if(searchp(pot,temp[0])) { //if first field isn't label ans found in POT
                    if(temp[0].equals("LTORG")) {
                        if(loc_count%8 != 0)  {
                            int div = loc_count/8;
                            loc_count = 8 * (div + 1);
                        }
                        for(int i = 0; i < lt.size(); i++) {
                            LiteralTable t = lt.get(i);
                            t.length = 4;
                            t.val = loc_count;
                            loc_count += 4;
                            lt.remove(i);
                            lt.add(i, t);
                        }
                    } 
                    else continue;
                }
                else if(searchp(pot,temp[1])) { //if first field is label and found in POT
                    if(temp[1].equals("EQU")) {
                        if(temp[2].equals("*")) st.addElement(new SymbolTable(temp[0],loc_count,1,'A'));
                        else st.addElement(new SymbolTable(temp[0],Integer.parseInt(temp[2]),1,'A'));
                    }
                    if(temp[1].equals("DC")) {
                        st.addElement(new SymbolTable(temp[0],loc_count,4,'R'));
                        loc_count += 4;
                    }
                    if(temp[1].equals("DS")) {
                        st.addElement(new SymbolTable(temp[0],loc_count,4,'R'));
                        loc_count += Integer.parseInt(temp[2].substring(0, temp[2].length()-1)) * 4; 
                    }
                }
                else if(searchm(mot,temp[0]) != -1) { //if first field isn't label and found in MOT
                    int pos = searchm(mot,temp[0]);
                    boolean flag = false, af = false;
                    StringBuffer ss = new StringBuffer();
                    for(int j = 0; j < temp[1].length(); j++) {
                        if(temp[1].charAt(j) == '=') { flag = true; af = true; } //check for literals
                        else if(flag) {
                            if(j == temp[1].length() ||temp[1].charAt(j) == ',') flag = false; //if found before , or at end
                            else ss.append(temp[1].charAt(j));
                        }
                    }
                    if(af) lt.addElement(new LiteralTable(ss.toString(),0,0,'R'));
                    loc_count += mot[pos].length;
                }
                else if(searchm(mot,temp[1]) != -1) { //if first field is label and found in MOT
                    int pos = searchm(mot,temp[1]);
                    boolean flag = false, af = false;
                    StringBuffer ss = new StringBuffer();
                    for(int j = 0; j < temp[1].length(); j++) {
                        if(temp[1].charAt(j) == '=') { flag = true; af = true; }
                        else if(flag) {
                            if(j == temp[1].length()-1 || temp[1].charAt(j) == ',') flag = false;
                            else ss.append(temp[1].charAt(j));
                        }
                    }
                    if(af) lt.addElement(new LiteralTable(ss.toString(),0,0,'R'));
                    st.addElement(new SymbolTable(temp[0],loc_count,mot[pos].length,'R'));
                    loc_count += mot[pos].length;
                }
            }
            lc++;
        }
        System.out.println("\nPASS 1\n");
        System.out.println("SYMBOL TABLE");
        System.out.printf("%-10s %-10s %-10s %-10s\n","Symbol","Value","Length","R/A");
        for(int i = 0; i < st.size(); i++) {
            SymbolTable s = st.get(i);
            System.out.printf("%-10s %-10d %-10d %-10c\n",s.sym,s.val,s.length,s.relocation);
        }
        System.out.println("\nLITERAL TABLE");
        System.out.printf("%-10s %-10s %-10s %-10s\n","Symbol","Value","Length","R/A");
        for(int i = 0; i < lt.size(); i++) {
            LiteralTable s = lt.get(i);
            System.out.printf("%-10s %-10d %-10d %-10c\n",s.sym,s.val,s.length,s.relocation);
        }
        /* PASS 2 */
        loc_count = 0;
        r = new BufferedReader(new FileReader("C:\\Users\\user\\Documents\\NetBeansProjects\\GUIFormExamples\\src\\input.txt"));
        System.out.println("-------------------------------------------------\nPASS 2\n");
        while((d = r.readLine()) != null) {
            d = d.trim();
            if(d.isEmpty()) continue;
            String temp[] = d.split("\\s+"); //System.out.println(d);
            if(temp[0].equals("USING")) {
                String temp1[] = temp[1].split("\\,");
                int pos1 = searchst(st,temp1[1]);
                int pos2 = searchst(st,temp1[0]);
                if(pos2 == -1) {
                    if(pos1 == -1) BT[Integer.parseInt(temp1[1])] = Integer.parseInt(temp1[0]);
                    else BT[st.get(pos1).val] = Integer.parseInt(temp1[0]);
                }
                else {
                    if(pos1 == -1) BT[Integer.parseInt(temp1[1])] = st.get(pos2).val;
                    else BT[st.get(pos1).val] = st.get(pos2).val;
                }
            }
            if(temp[0].equals("LA") || temp[0].equals("L") || temp[0].equals("C") || temp[0].equals("A") || temp[0].equals("ST") || temp[1].equals("L")) {
                String temp1[];
                if(temp[0].equals("LA") || temp[0].equals("L") || temp[0].equals("C") || temp[0].equals("A") || temp[0].equals("ST"))
                    temp1 = temp[1].split("\\,"); //split the second argument with ',' as delimiter
                else 
                    temp1 = temp[2].split("\\,");
                //int pos1 = searchst(st,temp1[1]);
                //int pos2 = searchst(st,temp1[0]);
                //Incomplete
                if(temp1[0].matches("^[0-9]*$")) { //if first operand is integer
                    if(temp[0].equals("LA") || temp[0].equals("L") || temp[0].equals("C") || temp[0].equals("A") || temp[0].equals("ST"))
                        System.out.print(temp[0]+" "+temp1[0]+",");
                    else 
                        System.out.print(temp[1]+" "+temp1[0]+",");
                    String p, q; int pos3, pos4, pos5, baseno;
                    if(temp1[1].contains("(")) { //if second operand contains '('
                        System.out.println("CONTAINS (");
                        p = temp1[1].substring(0, temp1[1].indexOf('(')); //take everything before '('
                        if(p.matches("^[0-9]*$")) { //if integer, search base table for nearest value
                            pos3 = findnearest(BT,Integer.parseInt(p));
                            int val = Integer.parseInt(p) - BT[pos3];
                            baseno = pos3;
                            System.out.print(val+"("); 
                        }
                        else { //if not integer, search the symbol table, extract value and search nearest value in base table
                            pos3 = searchst(st,p);
                            if(pos3 != -1) { //if found in symbol table
                                SymbolTable t1 = st.get(pos3);
                                pos4 = findnearest(BT,t1.val);
                                int val = t1.val - BT[pos4];
                                baseno = pos4;
                                System.out.print(val+"(");
                            }
                            else { //if not found in symbol table, search in literal table
                                pos4 = searchlt(lt,p.substring(1,p.length()));
                                LiteralTable t1 = lt.get(pos4);
                                pos5 = findnearest(BT,t1.val);
                                int val = t1.val - BT[pos5];
                                baseno = pos5;
                                System.out.print(val+"(");
                            }
                        }
                        q = temp1[1].substring(temp1[1].indexOf('(') + 1,temp1[1].indexOf(')') - 1);
                        if(q.matches("^[0-9]*$")) { //search for inside the braces; if integer
                            System.out.print(q+","+baseno+")\n"); 
                        }
                        else { //if not integer, search in symbol table
                            pos5 = searchst(st,q);
                            SymbolTable t1 = st.get(pos5);
                            System.out.print(t1.val+","+baseno+")\n");
                        }
                    }
                    else {
                        p = temp1[1];
                        if(p.matches("^[0-9]*$")) { //if integer, search base table for nearest value
                            pos3 = findnearest(BT,Integer.parseInt(p));
                            int val = Integer.parseInt(p) - BT[pos3];
                            baseno = pos3;
                            System.out.print(val+"(0,"+baseno+")\n"); 
                        }
                        else { //if not integer, search the symbol table, extract value and search nearest value in base table
                            pos3 = searchst(st,p);
                            if(pos3 != -1) { //if found in symbol table
                                SymbolTable t1 = st.get(pos3);
                                pos4 = findnearest(BT,t1.val);
                                int val = t1.val - BT[pos4];
                                baseno = pos4;
                                System.out.print(val+"(0,"+baseno+")\n");
                            }
                            else { //if not found in symbol table, search in literal table
                                pos4 = searchlt(lt,p.substring(1,p.length()));
                                LiteralTable t1 = lt.get(pos4);
                                pos5 = findnearest(BT,t1.val);
                                int val = t1.val - BT[pos5];
                                baseno = pos5;
                                System.out.print(val+"(0,"+baseno+")\n");
                            }
                        }
                    }
                }
                else {
                    int pos22 = searchst(st,temp1[0]); //System.out.println("NOT "+temp1[0]);
                    if(temp[0].equals("LA") || temp[0].equals("L") || temp[0].equals("C") || temp[0].equals("A") || temp[0].equals("ST"))
                        System.out.print(temp[0]+" "+st.get(pos22).val+",");
                    else 
                        System.out.print(temp[1]+" "+st.get(pos22).val+",");
                    String p, q; int pos3, pos4, pos5, baseno;
                    if(temp1[1].contains("(")) {
                        p = temp1[1].substring(0, temp1[1].indexOf('('));
                        if(p.matches("^[0-9]*$")) { //if integer, search base table for nearest value
                            pos3 = findnearest(BT,Integer.parseInt(p));
                            int val = Integer.parseInt(p) - BT[pos3];
                            baseno = pos3;
                            System.out.print(val+"("); 
                        }
                        else { //if not integer, search the symbol table, extract value and search nearest value in base table
                            pos3 = searchst(st,p);
                            if(pos3 != -1) { //if found in symbol table
                                SymbolTable t1 = st.get(pos3);
                                pos4 = findnearest(BT,t1.val);
                                int val = t1.val - BT[pos4];
                                baseno = pos4;
                                System.out.print(val+"(");
                            }
                            else { //if not found in symbol table, search in literal table
                                pos4 = searchlt(lt,p.substring(1,p.length()));
                                LiteralTable t1 = lt.get(pos4);
                                pos5 = findnearest(BT,t1.val);
                                int val = t1.val - BT[pos5];
                                baseno = pos5;
                                System.out.print(val+"(");
                            }
                        }
                        q = temp1[1].substring(temp1[1].indexOf('(') + 1,temp1[1].indexOf(')') - 1);
                        if(q.matches("^[0-9]*$")) { //search for inside the braces; if integer
                            System.out.print(q+","+baseno+")\n"); 
                        }
                        else { //if not integer, search in symbol table
                            pos5 = searchst(st,q);
                            SymbolTable t1 = st.get(pos5);
                            System.out.print(t1.val+","+baseno+")\n");
                        }
                    }
                    else {
                        p = temp1[1];
                        if(p.matches("^[0-9]*$")) { //if integer, search base table for nearest value
                            pos3 = findnearest(BT,Integer.parseInt(p));
                            int val = Integer.parseInt(p) - BT[pos3];
                            baseno = pos3;
                            System.out.print(val+"(0,"+baseno+")\n"); 
                        }
                        else { //if not integer, search the symbol table, extract value and search nearest value in base table
                            pos3 = searchst(st,p);
                            if(pos3 != -1) { //if found in symbol table
                                SymbolTable t1 = st.get(pos3);
                                pos4 = findnearest(BT,t1.val);
                                int val = t1.val - BT[pos4];
                                baseno = pos4;
                                System.out.print(val+"(0,"+baseno+")\n");
                            }
                            else { //if not found in symbol table, search in literal table
                                //System.out.println("NOT THIS "+ p.substring(1));
                                pos4 = searchlt(lt,p.substring(1));
                                LiteralTable t1 = lt.get(pos4);
                                pos5 = findnearest(BT,t1.val);
                                int val = t1.val - BT[pos5];
                                baseno = pos5;
                                System.out.print(val+"(0,"+baseno+")\n");
                            }
                        }
                    }
                }
            }
            if(temp[0].equals("SR") || temp[0].equals("AR") || temp[0].equals("LR")) {
                String temp1[] = temp[1].split("\\,");
                int pos1 = searchst(st,temp1[1]);
                int pos2 = searchst(st,temp1[0]);
                if(pos2 == -1) {
                    if(pos1 == -1) System.out.println(temp[0]+" "+temp1[0]+","+temp1[1]);
                    else System.out.println(temp[0]+" "+temp1[0]+","+st.get(pos1).val);
                }
                else {
                    if(pos1 == -1) System.out.println(temp[0]+" "+st.get(pos2).val+","+temp[1]);
                    else System.out.println(temp[0]+" "+st.get(pos2).val+","+st.get(pos1).val);
                }
            }
            if(temp[0].equals("BNE")) {
                System.out.println("BC 7,6(0,15)");
            }
            if(temp[0].equals("BR")) {
                System.out.println("BCR 15,"+temp[1]);
            }
        }
        /*for(int i = 0; i < BT.length; i++)
        System.out.println(BT[i]);*/
    }
}
