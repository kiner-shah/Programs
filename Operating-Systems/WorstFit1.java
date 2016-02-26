import java.io.*;
import java.util.*;
class WorstFit1 {
    public static void main(String[] args) throws Exception {
        int i,process[],block[],nb,np,flag[];
        Scanner s = new Scanner(System.in);
        System.out.print("Enter no. of blocks: "); nb=s.nextInt();
        Vector<Integer> v=new Vector<Integer>();
        block=new int[nb]; flag=new int[nb];
        for(i=0;i<nb;i++) {
            System.out.print("Block "+(i+1)+" size: ");
            block[i]=s.nextInt();
            v.addElement(block[i]);
            flag[i]=-1;
        }
        System.out.print("Enter no. of processes: "); np=s.nextInt();
        process=new int[np];
        for(i=0;i<np;i++) {
            System.out.print("Process "+(i+1)+" size: ");
            process[i]=s.nextInt();
        }
        for(i=0;i<np;i++) {
            int max_block=Collections.max(v);
            System.out.println(max_block);
            int pos=v.indexOf(max_block);
            if(process[i]<=max_block) {
                v.set(pos, 0);
                flag[pos]=i;
            }
        }
        System.out.println("Worst fit allocation goes as follows");
        for(i=0;i<nb;i++) {
            if(flag[i]==-1) System.out.println("Block "+(i+1)+" is not allocated to any process");
            else System.out.print("Block "+(i+1)+"->Process "+(flag[i]+1)+"\n");
        }
    } 
}
