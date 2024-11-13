package server;

import mindgameinterface.ScoreBoardInterface;
import client.model.Score;
import server.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreBoardService implements ScoreBoardInterface {

    @Override
    public List<Score> getScoreList() {

        List<Score> scoreList = new ArrayList<>();

        // SQL query to fetch data from the database
        String query = "SELECT player_name, score FROM shape_game_leaderboard ORDER BY score DESC LIMIT 10";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Iterate through the result set and add rows to the table
            while (rs.next()) {
                Score score = new Score();
//                score.setId(rs.getInt("id"));
                score.setPlayer_name(rs.getString("player_name"));
                score.setScore(rs.getInt("score"));

                scoreList.add(score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scoreList;
    }
}
