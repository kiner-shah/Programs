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
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kiner Shah
 */
public class ConC3 {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 6666);
        if(s.isConnected()) System.out.println("Connected to server...");
        PrintWriter w = new PrintWriter(s.getOutputStream(), true);
        BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
        int count = 0;
        while(true) {
            try {
                Thread.sleep(1000);
                int val = ThreadLocalRandom.current().nextInt(0, 4);
                if(val == 0) {
                    count++;
                    w.println("R");
                    int readValue = Integer.parseInt(r.readLine());
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    System.out.println("Read value: " + readValue + " at " + sdf.format(cal.getTime()));
                }
                if(count == 10) break;
            } catch (InterruptedException ex) {
                Logger.getLogger(ConC3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        s.close();
    }
}
