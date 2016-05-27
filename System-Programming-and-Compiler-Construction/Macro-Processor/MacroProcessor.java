/*
Author: KINER SHAH 
Experiment 3: Two Pass Macro Processor
Subject: System Programming and Compiler Construction
Date: 3rd March 2016
*/
import java.util.*;
import java.io.*;
class MDT {
    int index;
    String def;
    MDT(int i, String a) {
        this.index = i;
        this.def = a;
    }
}
class MNT {
    int index,mdtind;
    String name;
    Vector<String> ala;
    MNT(int i, String a, int ind, Vector<String> b) {
        this.index = i;
        this.name = a;
        this.mdtind = ind;
        this.ala = b;
    }
}
class MacroProcessor {
    public static int searchmnt(Vector<MNT> a, String b) {
        int i,pos = -1;
        for(i = 0; i < a.size(); i++) {
            MNT x = a.get(i);
            if(x.name.equals(b)) { pos = i; break; }
        }
        return pos;
    }
    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new FileReader("C:\\Users\\user\\Documents\\NetBeansProjects\\GUIFormExamples\\src\\inputmp.txt"));
        BufferedReader r1 = new BufferedReader(new InputStreamReader(System.in));
        Vector<MDT> mdt = new Vector<MDT>();
        Vector<MNT> mnt = new Vector<MNT>();
        Vector<String> isc = new Vector<String>();
        String d;
        boolean flag = false;
        /* PASS 1 */
        while((d = r.readLine()) != null) {
            d = d.trim();
            if(d.isEmpty()) continue;
            String temp[] = d.split("\\s+");
            if(d.contains("MACRO")) flag = true; //start entering into MDT
            if(flag) {
                d = r.readLine(); 
                String t[] = d.split("\\s+");
                String t1[] = t[1].split("\\,");
                Vector<String> arg = new Vector<String>();
                for(int i = 0; i < t1.length; i++) {
                    String p = t1[i];
                    if(t1[i].contains("=")) p = t1[i].substring(0,t1[i].indexOf('='));
                    arg.addElement(p);
                }
                mnt.addElement(new MNT(mnt.size() + 1, t[0], mdt.size() + 1, arg));
                mdt.addElement(new MDT(mdt.size() + 1, d));
                d = r.readLine();
                while(!d.equals("MEND")) {
                    if(d.contains("&")) {
                        int i = d.indexOf('&');
                        MNT h = mnt.get(mnt.size() - 1);
                        int j;
                        for(j = 0; j < h.ala.size(); j++) {
                            if(d.substring(i).equals(h.ala.get(j)))
                                break;
                        }
                        mdt.addElement(new MDT(mdt.size() + 1, d.substring(0,i)+"#"+(j+1)));
                    }
                    else mdt.addElement(new MDT(mdt.size() + 1, d));
                    d = r.readLine();
                }
                if(d.equals("MEND")) mdt.addElement(new MDT(mdt.size() + 1, d));
                flag = false;
            }
            else {
                isc.addElement(d);
            }
        }
        System.out.println("\nPASS 1\n");
        System.out.println("MDT");
        for(int i = 0; i < mdt.size(); i++) {
            MDT t = mdt.get(i);
            System.out.println(t.index+" "+t.def);
        }
        System.out.println("\nMNT");
        for(int i = 0; i < mnt.size(); i++) {
            MNT t = mnt.get(i);
            System.out.print(t.index+" "+t.name+" "+t.mdtind+"\tALA: ");
            for(int j = 0; j < t.ala.size(); j++) {
                System.out.print(t.ala.get(j)+" ");
            } 
            System.out.println();
        }
        System.out.println("\nIntermediate code");
        for(int i = 0; i < isc.size(); i++) System.out.println(isc.get(i));
        System.out.println("--------------------------------\nPASS 2\n");
        /* PASS 2 */
        for(int i = 0; i < isc.size(); i++) {
            String temp[] = isc.get(i).split("\\s+");
            int pos1 = searchmnt(mnt,temp[0]);
            if(pos1 == -1) System.out.println(isc.get(i));
            else if(pos1 != -1) {
                MNT x = mnt.get(pos1);
                int mdt_ind = x.mdtind;
                String ala1[] = temp[1].split("\\,");
                //System.out.println(ala1[0] + " " + ala1[1]);
                StringBuffer ss = new StringBuffer();
                for(int j = mdt_ind; j < mdt.size(); j++) {
                    flag = false;
                    String def = mdt.get(j).def;
                    if(def.equals("MEND")) break;
                    else {
                        for(int k = 0; k < def.length(); k++) {
                            if(!flag && def.charAt(k) != '#') System.out.print(def.charAt(k));
                            else if(!flag && def.charAt(k) == '#') flag = true;
                            else if(flag && def.charAt(k) == ',') {
                                //System.out.println(ss.toString());
                                int pos = Integer.parseInt(ss.toString());
                                System.out.print(ala1[pos - 1]);
                                ss.delete(0, ss.length());
                                flag = false;
                            }
                            else if(flag) {
                                ss.append(def.charAt(k));
                            }
                        }
                        if(ss.length() > 0) {
                            int pos = Integer.parseInt(ss.toString());
                            System.out.print(ala1[pos - 1]);
                            System.out.println();
                            ss.delete(0, ss.length());
                        }
                    }
                }
            }
        }
    }
}
