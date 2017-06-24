package GroupCommunication;
import java.rmi.registry.*;
import java.io.*;
import java.util.*;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
class Member {
	int mem_id, gid;
	String doj, mname;
	String dol;
}
public class GCServer implements GCInterface {
	public ArrayList<Member> members;
	public int latest_id, max_limit;
	public ArrayList<String> messages;
        public HashMap<String, Integer> broadCastRecord;
	public GCServer() {
		members = new ArrayList();
		messages = new ArrayList();
		latest_id = -1; max_limit = 3;
                broadCastRecord = new HashMap();
	}
	public String joinGroup(String name) {
		Member newmem = new Member();
		newmem.mem_id = this.latest_id + 1; this.latest_id = newmem.mem_id;
		newmem.doj = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
		newmem.dol = null; newmem.gid = 1;
		newmem.mname = name;
		System.out.println("Member joined: " + newmem.mname + "\t" +  newmem.mem_id + "\t" + newmem.doj);
		this.members.add(newmem); 
		return "GROUP " + newmem.gid + " JOINED";
	}
	public String leaveGroup(String name) {
		int pos = 0;
		for(Member m : this.members) {
			if(m.mname.equals(name)) {
				break;
			}
			pos++;
		}
		Member m = this.members.get(pos);
		m.dol = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
		System.out.println("Member left: " + m.mname + "\t" +  m.mem_id + "\t" + m.doj + "\t" + m.dol);
                this.max_limit--;
		return "LEFT GROUP " + m.gid + " SUCCESSFULLY";
		
	}
	public boolean sendMessage(String msg, String sname) {
                for(Member m : this.members) {
                    if(m.mname.equals(sname)) {
                        //messages.add(dname + ": " + msg);
                        String ts = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
                        if(msg.contains("=>")) {
                            int pos = msg.indexOf("=>");
                            String rname = msg.substring(pos + 2);
                            this.messages.add(sname + ";;" + rname + ";;" + msg.substring(0, pos) + ";;" + ts);
                        }
                        else {
                            this.messages.add(sname + ";;BROADCAST;;" + msg + ";;" + ts);
                            this.broadCastRecord.put(ts, 0);
                        }
                        return true;
                    }
                }	
                return false;
	}
	public String[] getMessages(String rname) {
		ArrayList<String> designated = new ArrayList();
                ArrayList<Integer> storedposition = new ArrayList();
                int j = 0;
		for(String m : this.messages) {
                    String temp[] = m.split(";;");
                    if(temp[1].equals(rname)) {
                        designated.add(temp[0] + ": " + temp[2]);
                        storedposition.add(j); j++;
                    }
                    else if(temp[1].equals("BROADCAST") && !temp[0].equals(rname)) {
                        designated.add(temp[0] + ": " + temp[2]);
                        int val = this.broadCastRecord.get(temp[3]);
                        this.broadCastRecord.remove(temp[3]);
                        if(val + 1 == this.max_limit) { storedposition.add(j); j++; }
                        else this.broadCastRecord.put(temp[3], val + 1);
                    }
		}
		Object o[] = designated.toArray();
		String ss[] = Arrays.copyOf(o, o.length, String[].class);
		for(int p : storedposition) { this.messages.remove(p); }
		return ss;
	}
	public static void main(String args[]) throws IOException {	
		try {
            GCServer obj = new GCServer();
            GCInterface stub = (GCInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(6000);
            registry.bind("GCInterface", stub);
			
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
	}
}