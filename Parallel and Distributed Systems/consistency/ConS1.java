/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consistency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Kiner Shah
 */
public class ConS1 {
    public static int x;
    public static void main(String[] args) throws IOException {
        x = 0;
        ServerSocket ss = new ServerSocket(6666);
        Socket s2s = new Socket("localhost", 3434);
        PrintWriter ww = new PrintWriter(s2s.getOutputStream(), true);
        BufferedReader rr = new BufferedReader(new InputStreamReader(s2s.getInputStream()));
        Thread tt = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        String readOrder = rr.readLine();
                        if(!readOrder.equals("null")) {
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                            System.out.println(sdf.format(cal.getTime()) + " Write order is: " + readOrder);
                            String temp[] = readOrder.split("[,]");
                            for (String temp1 : temp) { x = Integer.parseInt(temp1); }
                        }
                        //else break;
                    }
                }
                catch(IOException e) { e.printStackTrace(); }
            }
        }); tt.start();
        while(true) {
            Socket s = ss.accept();
            Thread t = new Thread(new Runnable() {
                @Override 
                public void run() {
                    try {
                        BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
                        PrintWriter w = new PrintWriter(s.getOutputStream(), true);
                        while(s.isConnected()) {
                            String mes = r.readLine();
                            if(mes == null) {
                                w.close(); r.close();
                                s.close(); break;
                            }
                            else if(mes.equals("R")) w.println(x);
                            else ww.println(mes.split("\\t")[1]);
                        }
                    }   
                    catch(IOException e) { e.printStackTrace(); }
                }
            }); t.start();
        }
    }
}
