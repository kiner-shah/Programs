/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
/**
 *
 * @author Kiner Shah
 */
public class ElectionAlgorithms {
    public static int bullyAlgorithm(int pid[], int init, int failed) {
        int l = pid.length, k = init;
        TreeSet<Integer> ll = new TreeSet();
        while(true) {
            ll.clear();
            for(int i = 0; i < l; i++) {
                if(pid[i] != k)  {
                    if(pid[i] != failed && pid[i] > k) {
                        System.out.println("RECEIVED REPLY FROM PID " + pid[i]);
                        ll.add(pid[i]);
                    }
                }
            }
            System.out.println(ll);
            //ll.remove(k); System.out.println(k);
            if(ll.size() == 0) break;
            k = ll.first();
        }
        return k;
    }
    public static int tokenRingAlgorithm(int pid[], int init, int failed) {
        int i, j, l = pid.length;
        for(i = 0; pid[i] != init; i++);
        j = i + 1;
        ArrayList<Integer> ll = new ArrayList();
        ll.add(pid[i]); System.out.println("RECEIVED MESSAGE BY = " + ll);
        while(j != i) {
            if(pid[j] != failed) {
                ll.add(pid[j]);
                System.out.println("RECEIVED MESSAGE BY = " + ll);
            }
            j = (j + 1) % l;
        }
        int max = Integer.MIN_VALUE, size = ll.size();
        for(int k = 0; k < size; k++) {
            if(ll.get(k) > max) {
                max = ll.get(k);
            }
        }
        return max;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        int np;
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter no. of processes: "); 
        np = Integer.parseInt(r.readLine());
        int processId[] = new int[np];
        for(int i = 0; i < np; i++) { 
            processId[i] = ThreadLocalRandom.current().nextInt(100, 1000);
        }
        System.out.print("Processes are: ");
        for(int i = 0; i < np; i++) System.out.print(processId[i] + " ");
        System.out.println("\n--------------------------------------------------------------------------------------");
        int max = Integer.MIN_VALUE, pos = -1;
        for(int i = 0; i < np; i++) {
            if(processId[i] > max) {
                max = processId[i];
                pos = i;
            }
        }
        System.out.println("Process with PID " + max + " has failed!");
        System.out.print("Enter election initiator: "); 
        int init = Integer.parseInt(r.readLine());
        System.out.println("CHOOSE ELECTION ALGORITHM\n1. Bully Algorithm\n2. Token Ring Algorithm");
        System.out.print("Enter choice: ");
        int c = Integer.parseInt(r.readLine()), newCoordinator;
        switch(c) {
            case 1:
                newCoordinator = bullyAlgorithm(processId, init, max);
                System.out.println("COORDINATOR CHOSEN BY BULLY ALGORITHM: " + newCoordinator);
            break;
            case 2:
                newCoordinator = tokenRingAlgorithm(processId, init, max);
                System.out.println("COORDINATOR CHOSEN BY TOKEN RING ALGORITHM: " + newCoordinator);
            break;
            default: System.out.println("Invalid choice!");
            break;
        }
    }
    
}
