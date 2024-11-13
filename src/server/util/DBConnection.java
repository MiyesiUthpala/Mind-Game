package server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mind_game"; // Update the URL if needed
    private static final String USER = "root"; // Your database username
    private static final String PASSWORD = "root"; //  database password (empty string if no password)

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
