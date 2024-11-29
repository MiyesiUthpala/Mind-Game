package client;

import client.model.Score;
import mindgameinterface.ScoreBoardInterface;
import server.ScoreBoardService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

public class ImageChallengeLeaderboard extends JFrame {
    private JTable leaderboardTable;
    private DefaultTableModel tableModel;

    // Constructor to set up the leaderboard window
    public ImageChallengeLeaderboard() throws RemoteException {
        super("Image Leaderboard");

        // Make the window full-screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height); // Set to full screen size
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a panel to hold the components
        JPanel mainPanel = new BackgroundPanel(); // Custom panel with background image
        mainPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering

        // Create a blue panel for the title
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Set shadow color and draw shadow for the label
                g.setColor(new Color(0, 0, 0, 100)); // Shadow color (semi-transparent)
            }
        };
        titlePanel.setBackground(new Color(0, 102, 102)); // Blue color
        titlePanel.setPreferredSize(new Dimension(800, 80));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true)); // Curved border

        // Create a label for the title with HTML and CSS styling
        JLabel titleLabel = new JLabel("<html><body style='text-align:center; font-size:36px; color:white; font-weight:bold;'>Image Challenge Leaderboard</body></html>");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel, BorderLayout.CENTER); // Add the label to the title panel

        // Create the logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 20));
        logoutButton.setBackground(new Color(255, 0, 0)); // Red color
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(100, 40));

        // Action listener for logout button to navigate to the Shape Challenge home page
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the leaderboard and navigate to the home page
                dispose(); // Close the leaderboard window
                ImageChallengeHome homePage = new ImageChallengeHome(); // Assuming you have a ShapeChallengeHomePage class
                homePage.setVisible(true); // Show the home page
            }
        });

        // Create a panel to hold the logout button and align it to the left
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        logoutPanel.setOpaque(false); // Make the panel transparent
        logoutPanel.add(logoutButton);

        // Create a panel for the table with padding
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setOpaque(false); // Make the panel transparent

        // Create a table model with column names
        String[] columnNames = {"Rank", "Player Name", "Score"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Create the JTable with the table model
        leaderboardTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table non-editable
            }
        };

        // Set table styles
        leaderboardTable.setFont(new Font("Arial", Font.PLAIN, 20));
        leaderboardTable.setRowHeight(60);
        leaderboardTable.setForeground(Color.BLACK);
        leaderboardTable.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent background
        leaderboardTable.setGridColor(Color.LIGHT_GRAY);

        // Center align cell content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        leaderboardTable.setDefaultRenderer(Object.class, centerRenderer);

        // Style the table header
        JTableHeader header = leaderboardTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));
        header.setBackground(new Color(34, 34, 34));
        header.setForeground(Color.WHITE);

        // Load leaderboard data from the database
        loadLeaderboardData();

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add the scroll pane to the table panel
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.setPreferredSize(new Dimension(800, 500)); // Set a smaller size for the table

        // Add the components to the main panel with centering
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 20, 10); // Add padding around the title panel
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titlePanel, gbc);

        gbc.gridy = 1;
        mainPanel.add(tablePanel, gbc);

        // Add the logout panel at the top left
        gbc.gridy = 0;
        gbc.gridx = 0;
        mainPanel.add(logoutPanel, gbc);

        // Add the main panel to the frame
        getContentPane().add(mainPanel);
    }

    // Method to load leaderboard data from the database
    private void loadLeaderboardData() throws RemoteException {
        int rank = 1;

        ScoreBoardInterface scoreBoardInterface = null;
        try {
            scoreBoardInterface = new ScoreBoardService();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        List<Score> scoreList = scoreBoardInterface.getScoreList("Image Game");
        for (Score score : scoreList) {
            tableModel.addRow(new Object[]{rank++, score.getPlayer_name(), score.getScore()});
        }
    }

    public static void main(String[] args) {
        // Launch the leaderboard window
        SwingUtilities.invokeLater(() -> {
            ImageChallengeLeaderboard imageChallengeLeaderboard = null;
            try {
                imageChallengeLeaderboard = new ImageChallengeLeaderboard();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            imageChallengeLeaderboard.setVisible(true);
        });
    }

    // Custom JPanel to draw a background image
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                backgroundImage = Toolkit.getDefaultToolkit().getImage("C:/Users/miyes/OneDrive/Documents/Mind Game/imageleaderboard.jpg");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
