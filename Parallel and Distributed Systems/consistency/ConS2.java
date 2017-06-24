/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consistency;

import java.io.BufferedReader;
//import java.io.BufferedWriter;
    //import java.io.File;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kiner Shah
 */
public class ConS2 {
    public static ArrayList<String> l;
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(3434);
        Socket s = ss.accept();
        l = new ArrayList();
        BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter w = new PrintWriter(s.getOutputStream(), true);
        //BufferedWriter w1 = new BufferedWriter(new FileWriter(new File("log.txt")));
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        String val = r.readLine();
                        l.add(val);
                        //w1.append("" + val + "\n");
                        //w1.flush();
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        System.out.println(val + " at " + sdf.format(cal.getTime()));
                    }
                }
                catch(IOException e) { e.printStackTrace(); }
            }
        }); t1.start();
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        Thread.sleep(500);
                        String fin = "";
                        if(l.isEmpty()) w.println("null");
                        else {
                            for(int i = 0; i < l.size() - 1; i++) fin += l.get(i) + ",";
                            fin += l.get(l.size() - 1);
                            w.println(fin);
                        }
                    }
                } 
                catch (InterruptedException ex) {
                    Logger.getLogger(ConS2.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }); t2.start();
    }
}
