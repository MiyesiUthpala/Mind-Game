package server;

import mindgameinterface.RegisterInterface;
import server.util.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class RegisterService extends UnicastRemoteObject implements RegisterInterface {

    private static final long serialVersionUID = -2041305498663694836L;


    protected RegisterService() throws RemoteException {
    }

    @Override
    public String register(String username, String password,String first_name,String last_name,String email) throws RemoteException {
        String checkUserQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        String insertUserQuery = "INSERT INTO users (username, password,first_name,last_name,email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkUserQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertUserQuery)) {

            // Check if username already exists
            checkStmt.setString(1, username);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return "error#Username already exists.";
                }
            }

            // Insert new user into the database
            insertStmt.setString(1, username);
            insertStmt.setString(2, hashPassword(password));
            insertStmt.setString(3, first_name);
            insertStmt.setString(4, last_name);
            insertStmt.setString(5, email);
            int rowsInserted = insertStmt.executeUpdate();

            if (rowsInserted > 0) {
                return "success#User registered successfully.";
            } else {
                return "error#Failed to register user.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error#An error occurred while processing the registration.";
        }
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
