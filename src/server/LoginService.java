package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import mindgameinterface.LoginInterface;

public class LoginService extends UnicastRemoteObject implements LoginInterface {


    private static final long serialVersionUID = -2041305498663694835L;


    private String sessionCookie = "abc"+Math.random();
    /**
     * The services returns a secret message after login. It also provides a greeting that does not need login.
     * @throws RemoteException
     */
    public LoginService() throws RemoteException {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String sayHello() throws RemoteException {

        return "Hello! Have a good day!";
    }

    @Override
    public String getSecretMessage(String cookie) throws RemoteException {
        if( cookie.equals(sessionCookie)) {
            return "This is a secret message: Alice is in a relationship with Bob. It is complicated.";
        } else {
            return "You must login to read this message";
        }
    }

    @Override
    public String login(String password) throws RemoteException {
        /* Note and Warning! This setup demonstrates the interaction between cookies and
         * login. Actually security is not in the scope of this demo.
         * Usually you wouldn't hardcode a password in production code but the password
         * were to be checked up against an (encrypted) database.
         */
        if(password != null && password.equals("hello")) {
            sessionCookie = "xyz"+Math.random();
            return sessionCookie;
        } else {
            return "wrong";
        }
    }

    @Override
    public String logout(String cookie) throws RemoteException {
        sessionCookie = "abc"+Math.random();
        return "logout successful";
    }

}

