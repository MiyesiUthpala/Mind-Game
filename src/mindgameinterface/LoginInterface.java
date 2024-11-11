package mindgameinterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * Interface to demonstrate login with Cookies and using services.
 * @author vim
 *
 */
public interface LoginInterface extends Remote {
    /**
     * A service that can be accessed without authentication.
     * @return
     * @throws RemoteException
     */
    public String sayHello() throws RemoteException;

    /**
     * A service that needs authentication for it to be used.
     * @param cookie
     * @return
     * @throws RemoteException
     */
    public String getSecretMessage(String cookie) throws RemoteException;

    /**
     * Login with a password. If the password is correct you can access the secret message.
     * @param password
     * @return
     * @throws RemoteException
     */
    public String login(String password) throws RemoteException;

    /**
     * Logout of the system.
     * @param cookie
     * @return
     * @throws RemoteException
     */
    public String logout(String cookie) throws RemoteException;

}