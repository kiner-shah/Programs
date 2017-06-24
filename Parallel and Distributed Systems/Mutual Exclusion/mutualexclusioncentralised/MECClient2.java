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
import java.net.Socket;

/**
 *
 * @author Kiner Shah
 */
public class MECClient2 {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 6666);
        BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter w = new PrintWriter(s.getOutputStream(), true);
        w.println("2,12"); System.out.println("Sent request to " + s.getInetAddress() + "(" + s.getPort() + ")");
        String str = r.readLine();
        if(str.trim().equals("OK")) {
            System.out.println("EXECUTING IN CRITICAL SECTION");
            try {
                Thread.sleep(5000);
                System.out.println("LEFT CRITICAL SECTION");
                w.println("LEFT");
            }
            catch(InterruptedException e) { e.printStackTrace(); }
        }
        s.close();
    }
}
