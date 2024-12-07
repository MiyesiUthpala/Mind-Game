package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mindgameinterface.LoginInterface;
import server.util.DBConnection;

public class LoginService extends UnicastRemoteObject implements LoginInterface {

    // Serial version UID for class version control during serialization
    private static final long serialVersionUID = -2041305498663694835L;


    // Session cookie initialized with a random value for each session
    private String sessionCookie = "abc"+Math.random();

    public LoginService() throws RemoteException {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String login(String username, String password) throws RemoteException {
        String query = "SELECT password FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set the username parameter
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Retrieve the stored password
                    String storedPassword = rs.getString("password");

                    // Compare the provided password with the stored one
                    if (storedPassword.equals(RegisterService.hashPassword(password))) { // Ideally, use hashing here
                        // Generate a session cookie (could be a UUID for better uniqueness)
                        sessionCookie =  username;
                        return sessionCookie;
                    } else {
                        return "error#Invalid password.";
                    }
                } else {
                    return "error#Username not found.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error#An error occurred while processing the login.";
        }
    }

    @Override
    public String logout(String cookie) throws RemoteException {
        sessionCookie = "abc"+Math.random();
        return "logout successful";
    }

}

