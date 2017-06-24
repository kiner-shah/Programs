package mutualexclusion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class MEClient2 extends Thread {
    static int timestamp, id;
    static Socket s1, s2;
    static ArrayList<String> requestQ;
    static int execute, n;
    MEClient2() {
        n = 0;
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        requestQ = new ArrayList();
        ServerSocket ss = new ServerSocket(3434);
        System.out.println("Running Client2");
        Socket sss1 = ss.accept(); if(sss1.getInetAddress().isReachable(30000)) System.out.println("Server connected to client 1");
        //Socket sss2 = ss.accept(); if(sss2.getInetAddress().isReachable(30000)) System.out.println("Connected to client 3");
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader r = new BufferedReader(new InputStreamReader(sss1.getInputStream()));
                    System.out.println("Waiting for messages");
                    String temp[] = new String[2];
                    temp = r.readLine().split("[,]");
                    System.out.println("Received message: " + temp[0] + "," + temp[1]);
                    if(n == 1) {
                        int ts = Integer.parseInt(temp[1]);
                        PrintWriter w = new PrintWriter(sss1.getOutputStream(), true);
                        if(ts < 12) {
                            w.println("OK"); requestQ.add(temp[0] + "," + temp[1]);
                        }
                        else {
                            w.println("WAIT"); requestQ.add(temp[0] + "," + temp[1]);
                        }
                    }
                    else {
                        PrintWriter w = new PrintWriter(sss1.getOutputStream(), true);
                        w.println("OK");
                        requestQ.add(temp[0] + "," + temp[1]);
                    }
                    String enter = r.readLine(); //System.out.println("Received message: " + enter);
                    if(enter.equals("CRITICAL SECTION")) {
                        System.out.println("CRITICAL SECTION IN USE");
                        String leave = r.readLine();
                        if(leave.equals("LEFT CS")) System.out.println("CRITICAL SECTION FREE");
                        sss1.close();
                    }
                }
                catch(IOException e) { e.printStackTrace(); }
            }    
        }); //t1.start();
        /*Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader r = new BufferedReader(new InputStreamReader(sss2.getInputStream()));
                    System.out.println("Waiting for messages");
                    String temp[] = r.readLine().split("[,]");
                    System.out.println("Received message");
                    if(n == 1) {
                        int ts = Integer.parseInt(temp[1]);
                        PrintWriter w = new PrintWriter(sss2.getOutputStream(), true);
                        if(ts < 8) {
                            w.println("OK"); requestQ.add(temp[0] + "," + temp[1]);
                        }
                        else {
                            w.println("WAIT"); requestQ.add(temp[0] + "," + temp[1]);
                        }
                    }
                    else {
                        PrintWriter w = new PrintWriter(sss2.getOutputStream(), true);
                        w.println("OK");
                        requestQ.add(temp[0] + "," + temp[1]);
                    }
                    String enter = r.readLine();
                    // Do something
                }
                catch(IOException e) { e.printStackTrace(); }
            }    
        }); //t2.start(); */
        s1 = new Socket("localhost", 3433);
        Thread.sleep(5000);
        s2 = new Socket("localhost", 4444);
        // Check if process is already in critical section
        // if yes, do nothing
        // if no, broadcast message to all and update the queue
        n = 1;
        PrintWriter w1 = new PrintWriter(s1.getOutputStream(), true);
        PrintWriter w2 = new PrintWriter(s2.getOutputStream(), true);
        w1.println("2,12"); w2.println("2,12"); System.out.println("Sent requests to: " + s1.getInetAddress() + "(" + s1.getPort() + "), " + s2.getInetAddress() + "(" + s2.getPort() + ")");
        t1.start(); //t2.start();
        // Check if process has received response from all other processes for critical section
        // if yes, enter critical section
        // if no, wait
        Thread receiveResponse = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader r1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
                    BufferedReader r2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));
                    String str1 = r1.readLine();
                    String str2 = r2.readLine();
                    if(str1.equals("OK") && str2.equals("OK")) {
                        System.out.println("RECEIVED OK FROM CLIENT 1");
                        System.out.println("RECEIVED OK FROM CLIENT 3");
                        w1.println("CRITICAL SECTION");
                        w2.println("CRITICAL SECTION");
                        execute = 1; requestQ.remove("2,12");
                        try {
                            if(execute == 1) {
                                new MEClient2().start();
                                sleep(3100);
                                w1.println("LEFT CS");
                                w2.println("LEFT CS"); execute = 0;
                            }
                        }
                        catch(InterruptedException e) { e.printStackTrace(); }
                    }
                    else if(str1.equals("WAIT") && str2.equals("WAIT")) {
                        if(str1.equals("OK")) System.out.println("RECEIVED OK FROM CLIENT 1");
                        else System.out.println("RECEIVED WAIT FROM CLIENT 1");
                        if(str2.equals("OK")) System.out.println("RECEIVED OK FROM CLIENT 3");
                        else System.out.println("RECEIVED WAIT FROM CLIENT 3");
                        execute = 0;
                    }
                }
                catch(IOException e) { e.printStackTrace(); }
            } 
        });
        receiveResponse.start();
        // Receive messages
        /*Thread receiveMessage = new Thread(new Runnable() {
           @Override
            public void run() {
                
           }
        });
        receiveMessage.start();*/
        // Execute in Critical section if execute is set, else do nothing
        // Send entering message to others
        
    }
    // Run function for Server Connections
    @Override
    public void run() {
        try {
            System.out.println("Executing in critical section");
            Thread.sleep(3000);
        }
        catch(InterruptedException e) { e.printStackTrace(); }
    }
}