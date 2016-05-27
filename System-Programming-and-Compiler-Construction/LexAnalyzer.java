/*
Author: KINER SHAH 
Experiment 1: Lexical Analyzer
Subject: System Programming and Compiler Construction
Date: 14th January 2016
*/
import java.util.*;
import java.io.*;
class LexAnalyzer {
    public static void main(String[] args) throws Exception {
        /*Read from file sample_prog.txt*/
        BufferedReader r = new BufferedReader(new FileReader("C:\\Users\\user\\Desktop\\sample_prog.txt"));
        /*Some possible keywords stored in keywords[] array*/
        String keywords[] = {"void","int","float","double","char","if","else","for","while","do","const","return","auto","break","continue","signed","unsigned"};
        Vector<String> kw = new Vector<String>();
        kw.addAll(Arrays.asList(keywords));
        /*Some possible built-in functions stored in builtinfunc[] array*/
        String builtinfunc[] = {"main","scanf","printf","clrscr","pow","sqrt","getch","getchar","putchar","cos","log","abs","floor","ceil","cosh"};
        Vector<String> bf = new Vector<String>();
        bf.addAll(Arrays.asList(builtinfunc));
        /*Some possible operators stored in operators[] array*/
        String operators[] = {"{","}","=","+","-","/","*","%","^","&","&&","|","||","==","+=","-=","*=","/=","%=","<<",">>","(",")","!"};
        Vector<String> op = new Vector<String>();
        op.addAll(Arrays.asList(operators));
        String d;
        /*Symbol Table*/
        Vector<String> st = new Vector<String>(); 
        /*Read till end of file*/
        while((d = r.readLine())!=null) {
        	d = d.trim();
        	if(d.isEmpty()) continue;
            System.out.println("LINE UNDER PROCESS: "+d);
            String line[] = d.split(";|\\ |\\,"); //Split using delimiters ';', ',' and blankspace
            /*for(int i=0;i<line.length;i++) System.out.print(line[i]+" ");
            System.out.println();*/
            boolean flag=false;
            for(int i=0;i<line.length;i++) {
                if(kw.contains(line[i])) System.out.print("Keyword "+kw.indexOf(line[i])+" "); //if found in keywords, display its position in Vector
                else if(bf.contains(line[i])) System.out.print("Built-In function "+bf.indexOf(line[i])+" "); //if found in builtinfunc, display its position in Vector
                else if(op.contains(line[i])) System.out.print("Operator "+op.indexOf(line[i])+" "); //if found in operators, display its position in Vector
                else {
                    StringBuffer ss=new StringBuffer();
                    for(int j=0;j<line[i].length();j++) { //parse character by character for each word of line
                        if(!flag && line[i].charAt(j)>='a' && line[i].charAt(j)<='z') ss.append(line[i].charAt(j));
                        else if(!flag && line[i].charAt(j)>='A' && line[i].charAt(j)<='Z') ss.append(line[i].charAt(j));
                        else if(!flag && line[i].charAt(j)>='0' && line[i].charAt(j)<='9') ss.append(line[i].charAt(j));
                        else if(!flag && line[i].charAt(j)=='"') flag=true; //ignore everything between "". Set flag to true to avoid any unnecessary processing
                        else if(flag && line[i].charAt(j)=='"') flag=false;
                        else if(!flag && line[i].charAt(j)=='(' && line[i].charAt(j+1)==')') { //if no arguments in function
                            if(ss.length() > 0) {
                                if(kw.contains(ss.toString())) System.out.print("Keyword "+kw.indexOf(ss.toString())+" ");
                                else if(bf.contains(ss.toString())) System.out.print("Built-In function "+bf.indexOf(ss.toString())+" ");
                                else if(op.contains(ss.toString())) System.out.print("Operator "+op.indexOf(ss.toString())+" ");
                                else if(st.contains(ss.toString())) System.out.print("Symbol "+st.indexOf(ss.toString())+" ");
                                else {
                                    System.out.print("Symbol "+st.size()+" ");
                                    st.addElement(ss.toString());
                                }
                                ss.delete(0, ss.length());
                            }
                            System.out.print("Operator "+op.indexOf(""+line[i].charAt(j))+" ");
                            System.out.print("Operator "+op.indexOf(""+line[i].charAt(j+1))+" ");
                            j++;
                        }
                        else if(!flag && line[i].charAt(j)=='(' || line[i].charAt(j)==')') { //if arguments are there in function
                            if(kw.contains(ss.toString())) System.out.print("Keyword "+kw.indexOf(ss.toString())+" ");
                            else if(bf.contains(ss.toString())) System.out.print("Built-In function "+bf.indexOf(ss.toString())+" ");
                            else if(op.contains(ss.toString())) System.out.print("Operator "+op.indexOf(ss.toString())+" ");
                            else if(st.contains(ss.toString())) System.out.print("Symbol "+st.indexOf(ss.toString())+" ");
                            else {
                                System.out.print("Symbol "+st.size()+" ");
                                st.addElement(ss.toString());
                            }
                            ss.delete(0, ss.length());
                            System.out.print("Operator "+op.indexOf(""+line[i].charAt(j))+" ");
                        }
                        else if(!flag && line[i].charAt(j)=='&' && (j+1)<line[i].length() && line[i].charAt(j+1)!='&') { //if & character comes before any variable
                            System.out.print("Operator "+op.indexOf("&")+" ");
                        }
                    }
                    if(ss.length() > 0) { //if the string is a variable not between (). Eg. int a,b;
                    	if(kw.contains(ss.toString())) System.out.print("Keyword "+kw.indexOf(ss.toString())+" ");
                        else if(bf.contains(ss.toString())) System.out.print("Built-In function "+bf.indexOf(ss.toString())+" ");
                        else if(op.contains(ss.toString())) System.out.print("Operator "+op.indexOf(ss.toString())+" ");
                        else if(st.contains(ss.toString())) System.out.print("Symbol "+st.indexOf(ss.toString())+" ");
                        else {
                            System.out.print("Symbol "+st.size()+" ");
                            st.addElement(ss.toString());
                        }
                        ss.delete(0,ss.length());
                    }
                }
            }
            System.out.println();
        }
    }
}
