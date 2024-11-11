package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoginServer {

    public static void main(String[] args) {
        System.out.println("Attempting to start the Login Server...");
        try {
            LoginService myHello = new LoginService();
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("LoginService", myHello);

            System.out.println("Service started. Welcome to the RMI Login Service!");

        } catch (RemoteException e) {
            System.out.println("An error occured: "+e.toString());
            e.printStackTrace();
        }
    }
}