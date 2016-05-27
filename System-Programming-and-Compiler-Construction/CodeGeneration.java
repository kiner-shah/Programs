/*
Author: KINER SHAH 
Experiment 6: Code Generation using Sethi-Ullman algorithm
Subject: System Programming and Compiler Construction
Date: 31st March 2016
*/
import java.util.*;
import java.io.*;
class Tree {
    public Tree left,right;
    public char val;
    public int reg;
    Tree(char val, Tree l, Tree r) {
        this.val = val;
        this.left = l;
        this.right = r;
        this.reg = -1;
    }
}
class CodeGeneration {
    public static int prio(char c) {
        int x = 0;
        if(c == '+' || c == '-') x = 1;
        else if(c == '*') x = 2;
        else if(c == '/') x = 3;
        return x;
    }
    public static String evaluatepostfix(String a) {
        Stack<Character> s = new Stack(); //s.push('$');
        StringBuffer b = new StringBuffer();
        for(int i = 0; i < a.length(); i++) {
            if((a.charAt(i) >= 'a' && a.charAt(i) <= 'z') || (a.charAt(i) >= '0' && a.charAt(i) <= '9')) b.append(a.charAt(i));
            else {
                if(a.charAt(i) == '(') s.push(a.charAt(i));
                else if(a.charAt(i) == ')') {
                    while(!s.empty() && s.peek() != '(') {
                        b.append(s.peek());
                        s.pop();
                    }
                    if(!s.empty()) s.pop();
                }
                else if(a.charAt(i) == '+' || a.charAt(i) == '-' || a.charAt(i) == '*' || a.charAt(i) == '/') {
                    if(s.empty()) s.push(a.charAt(i));
                    else {
                        while(!s.empty() && s.peek() != '(' && prio(a.charAt(i)) > prio(s.peek())) {
                            b.append(s.peek());
                            s.pop();
                        }
                        s.push(a.charAt(i));
                    }
                }
            }
        }
        while(!s.empty()) { b.append(s.peek()); s.pop(); }
        return b.toString();
    }
    public static void inorder(Tree t) {
        if(t != null) {
            inorder(t.left);
            System.out.println(t.val+" "+t.reg);
            inorder(t.right);
        }
    }
    public static String registers[];
    public static void generatecode(Tree t) {
        if(t.left == null && t.right == null) return;
        else {
            generatecode(t.left);
            if(t.left.reg >= 1) {
                if(t.reg == t.left.reg) { 
                    if(registers[t.reg - 1].equals("empty")) {
                        System.out.print("MOV "+t.left.val+",R"+(t.reg - 1));
                        registers[t.reg - 1] = ""+t.left.val;
                        System.out.print("\t");
                        for(int i = 0; i < registers.length; i++) System.out.print("R"+i+":"+registers[i]+" ");
                        System.out.println();
                    }
                }
                else if(t.left.reg >= 1 && t.reg > t.left.reg) {
                    System.out.print("MOV R"+(t.left.reg - 1)+",R"+(t.reg - 1));
                    registers[t.reg - 1] = registers[t.left.reg - 1];
                    registers[t.left.reg - 1] = "empty";
                    System.out.print("\t");
                    for(int i = 0; i < registers.length; i++) System.out.print("R"+i+":"+registers[i]+" ");
                    System.out.println();
                }
            }
            generatecode(t.right);
            if(t.right.reg == 0) {
                if(t.val == '+') {
                    System.out.println("ADD "+t.right.val+",R"+(t.reg - 1));
                    registers[t.reg - 1] = registers[t.left.reg - 1] + '+' + t.right.val;
                }
                else if(t.val == '-') {
                    System.out.println("SUB "+t.right.val+",R"+(t.reg - 1));
                    registers[t.reg - 1] = registers[t.left.reg - 1] + '-' + t.right.val;
                }
                else if(t.val == '*') {
                    System.out.println("MUL "+t.right.val+",R"+(t.reg - 1));
                    registers[t.reg - 1] = registers[t.left.reg - 1] + '*' + t.right.val;
                }
                else if(t.val == '/') {
                    System.out.println("DIV "+t.right.val+",R"+(t.reg - 1));
                    registers[t.reg - 1] = registers[t.left.reg - 1] + '/' + t.right.val;
                }
            } 
            else {
                if(t.val == '+') {
                    System.out.print("ADD R"+(t.right.reg - 1)+",R"+(t.reg - 1));
                    registers[t.reg - 1] = registers[t.reg - 1] + '+' + registers[t.right.reg - 1];
                }
                else if(t.val == '-') {
                    System.out.print("SUB R"+(t.right.reg - 1)+",R"+(t.reg - 1));
                    registers[t.reg - 1] = registers[t.reg - 1] + '-' + registers[t.right.reg - 1];
                }
                else if(t.val == '*') {
                    System.out.print("MUL R"+(t.right.reg - 1)+",R"+(t.reg - 1));
                    registers[t.reg - 1] = registers[t.reg - 1] + '*' + registers[t.right.reg - 1];
                }
                else if(t.val == '/') {
                    System.out.print("DIV R"+(t.right.reg - 1)+",R"+(t.reg - 1));
                    registers[t.reg - 1] = registers[t.reg - 1] + '/' + registers[t.right.reg - 1];
                }
                registers[t.right.reg - 1] = "empty";
                System.out.print("\t");
                for(int i = 0; i < registers.length; i++) System.out.print("R"+i+":"+registers[i]+" ");
                System.out.println();
            }
            return;
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter input expression");
        String in = r.readLine();
        String subin = in.substring(in.indexOf('=')+1);
        String postin = evaluatepostfix(subin);
        System.out.println("Postfix is: "+postin);
        /* Construct tree */
        Stack<Tree> s = new Stack();
        for(int i = 0; i < postin.length(); i++) {
            char c = postin.charAt(i);
            if((c >= 'a' && c <= 'z')  || (c >= '0' && c <= '9')) {
                Tree t = new Tree(c,null,null);
                s.push(t);
            }
            else if(c == '+' || c == '-' || c == '*' || c == '/') {
                Tree o2 = s.peek(); s.pop(); 
                Tree o1 = s.peek(); s.pop(); 
                if(o1.reg == -1) o1.reg = 1;
                if(o2.reg == -1) o2.reg = 0;
                Tree t = new Tree(c,o1,o2);
                if(o1.reg != o2.reg) t.reg = (o2.reg > o1.reg) ? o2.reg : o1.reg;
                else t.reg = o1.reg + 1;
                s.push(t);
            }
        }
        Tree t = s.peek(); s.pop();
        int maxreg = t.reg;
        //inorder(t);
        registers = new String[maxreg];
        for(int i = 0; i < maxreg; i++) registers[i] = "empty";
        System.out.println("Generated Code is\n");
        generatecode(t);
        System.out.println("MOV R"+(maxreg-1)+","+in.charAt(0));
        //System.out.println(maxreg);
    }
}
/*
OUTPUT
Enter input expression
x=((b+c)*(f+g))+(c*3)
Postfix is: bc+fg+*c3*+
Generated Code is

MOV b,R0	R0:b R1:empty 
ADD c,R0
MOV R0,R1	R0:empty R1:b+c 
MOV f,R0	R0:f R1:b+c 
ADD g,R0
MUL R0,R1	R0:empty R1:b+c*f+g 
MOV c,R0	R0:c R1:b+c*f+g 
MUL 3,R0
ADD R0,R1	R0:empty R1:b+c*f+g+c*3 
MOV R1,x
*/
