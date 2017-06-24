/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consistency;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kiner Shah
 */
public class ConC2 {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 6666);
        if(s.isConnected()) System.out.println("Connected to server...");
        PrintWriter w = new PrintWriter(s.getOutputStream(), true);
        for(int i = 0; i < 15; i++) {
            int val = ThreadLocalRandom.current().nextInt(100, 1000);
            try {
                Thread.sleep(1000);
                System.out.println("Write " + val);
                w.println("W\t" + val);
            } catch (InterruptedException ex) {
                Logger.getLogger(ConC1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        w.close();
        s.close();
    }
}
