package namingresolution;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
/**
 *
 * @author Kiner Shah
 */
public class NameResolutionClient {
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter a name to be resolved: ");
        String name = r.readLine();
        while(!name.trim().isEmpty()) {
            Socket s = new Socket("localhost", 6666);
            if(!s.isConnected()) System.out.println("Error connecting to server!");
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader r1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out.println(name);
            String ans = r1.readLine();
            //System.out.println(ans);
            String received[] = ans.split("::");
            System.out.println("Sending request to " + received[0] + "\n" + "Message = " + received[1]);
            name = received[1];
            r1.close();
            r.close();
            out.close();
        }
    }
}
