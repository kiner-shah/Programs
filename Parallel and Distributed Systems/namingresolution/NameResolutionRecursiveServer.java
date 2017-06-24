/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namingresolution;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Kiner Shah
 */
public class NameResolutionRecursiveServer extends Thread {
    ArrayList<Servers> server_list;
    Socket current_soc;
    public NameResolutionRecursiveServer(Socket soc) {
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
            new Thread(new NameResolutionRecursiveServer(s)).start();
        }
    }
    public String lookupServer(String name, String ans) {
        if(name.isEmpty()) return ans;
        String search, rest = ""; //System.out.println("REACHED");
        if(name.contains(".")) {
            int i;
            String temp[] = name.split("[.]");
            search = temp[0];
            for(i = 1; i < temp.length - 1; i++) rest += temp[i] + ".";
            rest += temp[i];
            //System.out.println("REACHED");
        }
        else search = name;
        for(Servers s : server_list) {
            String sname = s.name;
            if(sname.equals(search)) {
                ans += s.IP + ","; break;
            }
        }
        System.out.println(rest + " " + ans);
        return lookupServer(rest, ans);
    }
    @Override
    public void run() {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(current_soc.getInputStream()));
            String name = r.readLine();
            String lookup = lookupServer(name, "");
            PrintWriter w = new PrintWriter(current_soc.getOutputStream(), true);
            System.out.println("Resolved...replying to client: " + lookup);
            w.println(lookup);
            w.close();
            r.close();
        }
        catch(IOException e) { e.printStackTrace(); }
    }
}
