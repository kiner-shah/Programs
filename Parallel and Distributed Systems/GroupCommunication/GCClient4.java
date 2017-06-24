package GroupCommunication;
import java.rmi.registry.*;
import java.util.concurrent.*;
import java.io.*;
public class GCClient4 {
	public static void main(String args[]) throws IOException {	
		String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 6000);
            final GCInterface stub = (GCInterface) registry.lookup("GCInterface");
            BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
            int c;
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
			Runnable periodicTask = new Runnable() {
    			public void run() {
    				try {
    					String msgs[] = stub.getMessages("Client4");
    					for(String s : msgs) { 
    						if(!s.trim().isEmpty()) System.out.println(s); 
    					}
    				}
    				catch(Exception e) { e.printStackTrace(); }		    			
    			}
			};
			executor.scheduleAtFixedRate(periodicTask, 10, 10, TimeUnit.SECONDS);
            while(true) {
            	System.out.println("1. Join group\n2. Leave group\n3. Send message\n5. Exit\nEnter your choice:");
            	c = Integer.parseInt(r.readLine());
            	switch(c) {
            		case 1: String message = stub.joinGroup("Client4"); 
            				System.out.println(message);
            		break;
            		case 2: String message2 = stub.leaveGroup("Client4");
            				System.out.println(message2);
            		break;
            		case 3: //System.out.println("Enter receiver's name: ");
            				//String rname = r.readLine();
            				System.out.println("Enter message: ");
            				String msg = r.readLine();
            				boolean status = stub.sendMessage(msg, "Client4");
                                        if(!status) System.out.println("Message couldn't be sent. You need to be a part of the group to exchange messages.");
            		break;
            		case 5: System.exit(0); break;
            		default: System.out.println("Invalid option");
            		break;
            	}
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
	}
}