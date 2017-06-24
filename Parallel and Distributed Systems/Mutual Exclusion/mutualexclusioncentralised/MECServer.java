/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mutualexclusioncentralised;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Kiner Shah
 */
public class MECServer {
    static boolean criticalSection;
    //static HashMap<String, Socket> requestQ;
    private final static Object lock = new Object();
    /*public static String lockUnlock() {
        boolean a = criticalSection.compareAndSet(true, false);
        if(a) return "LEFT";
        else return "ENTERED CRITICAL SECTION";
    }*/
    public static boolean compareAndSet(boolean src, boolean dest) {
    	boolean flag = false;
    	synchronized(lock) {
    		if(criticalSection == src) {
    			criticalSection = dest;
    			flag = true;
    		}
    	}
    	//System.out.println("Flag value: " + flag + " (" + src + ", " + dest + ")");
    	return flag;
	}
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(6666);
        requestQ = new HashMap();
        //criticalSection = new AtomicBoolean(false);
        criticalSection = false;
        while(true) {
            Socket s = ss.accept();
            Thread tt = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        PrintWriter w = new PrintWriter(s.getOutputStream(), true);
                        String x = r.readLine(); System.out.println("Received Message: " + x);
                        String temp[] = x.split("[,]"); boolean optimize_flag = false;
                        /*while(criticalSection) { //criticalSection.get()
                            //System.out.println("Thread " + this.getClass().getName() + " " + optimize_flag);
                            if(!optimize_flag) {
                                requestQ.put(x, s);
                                optimize_flag = true;
                            }
                            //System.out.println("Critical Section already in use...waiting");
                        }*/
                        optimize_flag = false;
                        //else {
                            //System.out.println("Processing request of " + s.getInetAddress() + "(" + s.getPort() + ")");
                            //criticalSection.compareAndSet(false, true);
                            //assert entry : "Problem occured during entry";
                            while(!compareAndSet(false, true));
                            w.println("OK");
                            System.out.println("Client " + temp[0] + " executing in Critical Section");
                            System.out.println("Critical section: " + criticalSection); //criticalSection.get()
                            String y = r.readLine();
                            if(y.equals("LEFT")) {
                                boolean depart = compareAndSet(true, false); //criticalSection.compareAndSet(true, false);
                                //assert depart:"Problem occured during depart";
                                System.out.println("Client " + temp[0] + " left Critical Section");
                                System.out.println("Critical section: " + criticalSection); //criticalSection.get()
                                /*Map.Entry<String, Socket> entry = requestQ.entrySet().iterator().next();
                                String key = entry.getKey();
                                String t[] = key.split("[,]");
                                Socket value = entry.getValue();
                                System.out.println("Processing request of " + value.getInetAddress() + "(" + value.getPort() + ")");
                                requestQ.remove(key);
                                PrintWriter w1 = new PrintWriter(value.getOutputStream());
                                w1.println("OK"); System.out.println("Client " + t[0] + " executing in Critical Section"); 
                                criticalSection.compareAndSet(false, true);*/
                            }
                        //}
                    }
                    catch(Exception e) { e.printStackTrace(); }
                }
            });
            tt.start();
        }
    }
}
