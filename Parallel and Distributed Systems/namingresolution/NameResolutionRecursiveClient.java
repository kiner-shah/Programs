/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namingresolution;
import java.io.*;
import java.net.Socket;
/**
 *
 * @author Kiner Shah
 */
public class NameResolutionRecursiveClient {
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter a name to be resolved: ");
        String name = r.readLine();
        Socket s = new Socket("localhost", 6666);
        if(!s.isConnected()) System.out.println("Error connecting to server!");
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader r1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out.println(name);
        String ans = r1.readLine();
        //System.out.println(ans);
        String received[] = ans.split(",");
        for(String ss : received) System.out.println(ss);
        r1.close();
        r.close();
        out.close();
    }
}
