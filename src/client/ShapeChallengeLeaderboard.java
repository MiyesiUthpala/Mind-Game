package client;

import client.model.Score;
import mindgameinterface.ScoreBoardInterface;
import server.ScoreBoardService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShapeChallengeLeaderboard extends JFrame {
    private JTable leaderboardTable;
    private DefaultTableModel tableModel;




    // Constructor to set up the leaderboard window
    public ShapeChallengeLeaderboard() {
        super("Leaderboard");

        // Set up the frame size and behavior
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a panel to hold the table
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create a table model with column names
        String[] columnNames = {"Rank", "Player Name", "Score"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Create the JTable with the table model
        leaderboardTable = new JTable(tableModel);

        // Load leaderboard data from the database
        loadLeaderboardData();

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the main panel to the frame
        getContentPane().add(mainPanel);
    }

    // Method to load leaderboard data from the database
    private void loadLeaderboardData() {

        int rank = 1;

        ScoreBoardInterface scoreBoardInterface = new ScoreBoardService();
        List<Score> scoreList =  scoreBoardInterface.getScoreList();
       for (int i=0; i<scoreList.size(); i++){
            tableModel.addRow(new Object[]{rank++, scoreList.get(i).getPlayer_name(),scoreList.get(i).getScore()});
        };
    }

    public static void main(String[] args) {
        // Launch the leaderboard window
        SwingUtilities.invokeLater(() -> {
            ShapeChallengeLeaderboard shapeChallengeLeaderboard = new ShapeChallengeLeaderboard();
            shapeChallengeLeaderboard.setVisible(true);
        });
    }

}
