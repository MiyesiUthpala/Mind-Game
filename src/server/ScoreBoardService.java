package server;

import client.LoginGUI;
import mindgameinterface.ScoreBoardInterface;
import client.model.Score;
import server.util.DBConnection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoardService extends UnicastRemoteObject implements ScoreBoardInterface {

    private static final long serialVersionUID = -2041305498663694835L;

    public ScoreBoardService() throws RemoteException {
        // TODO Auto-generated constructor stub
    }
    @Override
    public List<Score> getScoreList(String game) {

        List<Score> scoreList = new ArrayList<>();

        // SQL query to fetch data from the database
        String query = "SELECT player_name, score FROM leaderboard WHERE game = ? ORDER BY score DESC LIMIT 10";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set the parameter for the prepared statement
            pstmt.setString(1, game);

            // Execute the query
            try (ResultSet rs = pstmt.executeQuery()) {
                // Iterate through the result set and add rows to the list
                while (rs.next()) {
                    Score score = new Score();
                    score.setPlayer_name(rs.getString("player_name"));
                    score.setScore(rs.getInt("score"));

                    scoreList.add(score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scoreList;
    }


    @Override
    public void addScore(Score score) {

        // Queries to check existence and perform insert/update
        String checkExistenceQuery = "SELECT COUNT(*) FROM leaderboard WHERE player_name = ?";
        String updateQuery = "UPDATE leaderboard SET score = GREATEST(score, ?) WHERE player_name = ? AND game = ?";
        String insertQuery = "INSERT INTO leaderboard (player_name, game, score) VALUES (?, ?, ?)";

        System.out.println(score.getPlayer_name());
        try (Connection connection = DBConnection.getConnection()) {

            // Step 1: Check if the record exists
            boolean recordExists;
            try (PreparedStatement checkStatement = connection.prepareStatement(checkExistenceQuery)) {
                checkStatement.setString(1, score.getPlayer_name());
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    resultSet.next();
                    recordExists = resultSet.getInt(1) > 0;
                }
            }

            // Step 2: Update if exists, otherwise insert
            if (recordExists) {
                // Update the score if the player exists
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setInt(1, score.getScore());
                    updateStatement.setString(2, score.getPlayer_name());
                    updateStatement.setString(3, score.getGame());
                    int rowsUpdated = updateStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Score updated successfully!");
                    }
                }
            } else {
                // Insert a new record if the player does not exist
                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                    insertStatement.setString(1, score.getPlayer_name());
                    insertStatement.setString(2, score.getGame());
                    insertStatement.setInt(3, score.getScore());
                    int rowsInserted = insertStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Score inserted successfully!");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while upserting score: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
