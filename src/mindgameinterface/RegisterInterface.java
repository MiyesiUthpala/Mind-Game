package mindgameinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegisterInterface extends Remote {

    public String register(String username, String password,String first_name,String last_name,String email) throws RemoteException;
}
