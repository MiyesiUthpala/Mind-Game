package mindgameinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginInterface extends Remote {

    public String login(String username, String password) throws RemoteException;


    public String logout(String cookie) throws RemoteException;

}