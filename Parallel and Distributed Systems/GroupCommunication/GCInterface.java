package GroupCommunication;
import java.rmi.*;
public interface GCInterface extends Remote {
	public String joinGroup(String name) throws RemoteException;
	public String leaveGroup(String name) throws RemoteException;
	public boolean sendMessage(String msg, String sname) throws RemoteException;
	public String[] getMessages(String rname) throws RemoteException;
}