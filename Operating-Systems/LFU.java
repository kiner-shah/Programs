/*
Least Frequently Used(LFU) implementation
by, KINER SHAH
*/
import java.util.*;
class LFU {
    public static int find(int val,int frames[]) {
        int i,pos=-1;
        for(i=0;i<frames.length;i++) {
            if(val == frames[i]) { pos=i; break; }
        }
        return pos;
    }
    public static int findinlist(int val, ArrayList<ArrayList<Integer>> l) {
        int i,pos=-1;
        for(i=0;i<l.size();i++) {
            if(l.get(i).contains(val)) { pos=i; break; }
        }
        return pos;
    } 
    public static int findminframeno(int frames[], ArrayList<ArrayList<Integer>> l) {
        int i,j,pos=-1,fpos=-1,min=9999,lpos=-1;
        for(i=0;i<frames.length;i++) {
            //System.out.print(frames[i]+" ");
            for(j=0;j<l.size();j++) {
                if(l.get(j).contains(frames[i])) {
                    if(j<min) { min=j; fpos=i; lpos=l.get(j).indexOf(frames[i]); }
                    else if(j==min) { 
                        lpos=lpos>l.get(j).indexOf(frames[i])?l.get(j).indexOf(frames[i]):lpos; 
                        fpos=find(l.get(j).get(lpos),frames);
                    }
                }
            }
            //System.out.println(min+" "+fpos);
        }
        return fpos;
    }
    public static void main(String[] args) {
        int i,j,nf,np,pages[];
        ArrayList<ArrayList<Integer>> v=new ArrayList<ArrayList<Integer>>();
        //ArrayList<Integer> l=new ArrayList<Integer>();
        //Initialise every count lists
        int frames[];
        for(i=0;i<4;i++) {
            v.add(new ArrayList<Integer>());
        }
        Scanner s = new Scanner(System.in);
        System.out.print("Enter no. of frames: "); nf=s.nextInt();
        System.out.print("Enter no. of pages: "); np=s.nextInt();
        pages=new int[np]; frames=new int[nf];
        for(i=0;i<np;i++) {
            pages[i]=s.nextInt(); 
            if(v.get(0).contains(pages[i])==false)
                v.get(0).add(pages[i]); 
            /*else {
                int pos0=v.get(0).indexOf(pages[i]);
                v.get(0).remove(pos0); v.get(1).add(pages[i]);
            }*/
            //System.out.print(v.get(0)+" ");
        }
        //for(j=0;j<4;j++) System.out.println(v.get(j));
        int hits=0;
        for(i=0;i<np;i++) {
            if(i<nf) {
                frames[i]=pages[i];
                int pos0=v.get(0).indexOf(pages[i]);
                v.get(0).remove(pos0); v.get(1).add(pages[i]);
            }
            else {
                if(find(pages[i],frames)!=-1) {
                    int pos=findinlist(pages[i],v);
                    int pos2=v.get(pos).indexOf(pages[i]);
                    v.get(pos).remove(pos2); //System.out.print(v.get(pos));
                    v.get(pos+1).add(pages[i]); hits++; //System.out.print(" "+v.get(pos+1));
                }
                else {
                    int pos=findminframeno(frames,v); 
                    int pos2=findinlist(frames[pos],v);
                    int pos1=v.get(pos2).indexOf(frames[pos]);
                    v.get(pos2).remove(pos1);
                    frames[pos]=pages[i];
                    //int pos1=findinlist(pages[i],v); //System.out.println(pos+" "+pos1); 
                    v.get(1).add(pages[i]); 
                    int pos0=v.get(0).indexOf(pages[i]); v.get(0).remove(pos0);
                }
            }
            //for(j=0;j<4;j++) System.out.print(v.get(j)+" ");
            for(j=0;j<frames.length;j++) System.out.print(frames[j]+" ");
            System.out.println(); 
        }
        System.out.println("Hit rate: "+(float)hits/np);
    }
}
