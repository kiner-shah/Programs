/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namingresolution;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
/**
 *
 * @author Kiner Shah
 */
class Servers {
    String name; String IP;
}
public class NameResolutionServer extends Thread {
    ArrayList<Servers> server_list;
    Socket current_soc;
    public NameResolutionServer(Socket soc) {
        server_list = new ArrayList();
        Servers s1 = new Servers();
        s1.name = "edu"; s1.IP = "256.78.23.12";
        Servers s2 = new Servers();
        s2.name = "somaiya"; s2.IP = "256.78.24.1";
        Servers s3 = new Servers();
        s3.name = "kjsce"; s3.IP = "256.78.25.1";     
        server_list.add(s1);
        server_list.add(s2);
        server_list.add(s3);
        this.current_soc = soc;
    }
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(6666);
        while(true) {
            Socket s = ss.accept();
            new Thread(new NameResolutionServer(s)).start();
        }
    }
    public String lookupServer(String name) {
        String IPAns = "";
        for(Servers ss : this.server_list) {
            String sname = ss.name;
            if(sname.equals(name)) {
                IPAns = ss.IP; break;
            }
        }
        return IPAns;
    }
    @Override
    public void run() {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(current_soc.getInputStream()));
            String rest = "", name = r.readLine();
            String temp[], lookup;
            if(name.contains(".")) { 
                temp= name.split("[.]");
                int i;
                for(i = 1; i < temp.length - 1; i++) rest = rest + temp[i] + ".";
                rest += temp[i];
                lookup = lookupServer(temp[0]);
            } 
            else { lookup = lookupServer(name); rest = " "; }
            String message;
            if(lookup.isEmpty()) message = "WRONG NAME::" + rest;
            else message = lookup + "::" + rest;
            PrintWriter w = new PrintWriter(current_soc.getOutputStream(), true);
            System.out.println("Resolved...replying to client: " + message);
            w.println(message);
            w.close();
            r.close();
        }
        catch(IOException e) { e.printStackTrace(); }
    }
}
