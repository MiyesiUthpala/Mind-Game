package client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageHowToPlay {

    JFrame howToPlayFrame = new JFrame("How to Play");
    JButton logoutButton = new JButton("Logout");
    JButton backButton = new JButton("Back to Game");

    ImageHowToPlay() {
        // Get screen dimensions and set frame size to full screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        howToPlayFrame.setSize(screenSize.width, screenSize.height); // Full screen size
        howToPlayFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
        howToPlayFrame.setLayout(new BorderLayout());
        howToPlayFrame.setLocationRelativeTo(null);
        howToPlayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Custom panel with background image
        ImageHowToPlay.BackgroundPanel backgroundPanel = new ImageHowToPlay.BackgroundPanel(HomePage.imagePath+"/background.jpg");
        backgroundPanel.setLayout(new GridBagLayout()); // Center components in the middle
        howToPlayFrame.add(backgroundPanel);

        // Instructions text with a box
        String instructions = "<html><body style='padding: 10px; font-family: Arial, sans-serif;'>" +
                "<h2 style='text-align: center; color: #4CAF50; text-shadow: 1px 1px 2px #888; font-size: 22px;'>How to play Shape Challenge Game</h2>" +
                "<ol style='line-height: 2.5; margin-top: 20px; font-size: 20px;'>" + // Increased line height and margin top
                "<li>Start the Game</li><br>" +
                "<li>Preview the Shapes (First 5 Seconds)</li><br>" +
                "<li>Start Matching</li><br>" +
                "<li>Check for a Match</li><br>" +
                "<li>Continue Matching</li><br>" +
                "<li>Win the Game</li><br>" +
                "</ol>" +
                "<p style='margin-top: 20px; font-size: 20px; text-align: center; color: #555;'>" +
                "Good luck and have fun!</p>" +
                "</body></html>";


        JLabel instructionsLabel = new JLabel(instructions);
        instructionsLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Adjusted font size
        instructionsLabel.setVerticalAlignment(SwingConstants.TOP);
        instructionsLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center text horizontally

        // Box panel for instructions (reduced transparency)
        JPanel boxPanel = new JPanel();
        boxPanel.setBackground(new Color(255, 255, 255, 180)); // Less transparency for better visibility
        boxPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                new EmptyBorder(20, 20, 20, 20) // Increased padding for a larger box
        ));

        // Set preferred size for the box
        boxPanel.setPreferredSize(new Dimension(850, 500)); // Adjust size as needed
        boxPanel.add(instructionsLabel);

        // Center the instructions box in the frame
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20); // Add padding around the box
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(boxPanel, gbc);

        // Button panel with Logout and Back buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make button panel transparent
        buttonPanel.setLayout(new FlowLayout());

        // Back button to return to the image Challenge home page
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBackground(new Color(76, 65, 126)); // Set a background color for the button
        backButton.setForeground(Color.WHITE); // Set text color to white
        backButton.setFocusPainted(false); // Remove focus border
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding inside the button
        backButton.addActionListener(e -> {
            howToPlayFrame.dispose(); // Close HowToPlayPage window
            new ImageChallengeHome(); // Redirect to image Challenge home page
        });

        // Logout button
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutButton.setBackground(new Color(23, 110, 45)); // Set a background color for logout
        logoutButton.setForeground(Color.WHITE); // Set text color to white
        logoutButton.setFocusPainted(false); // Remove focus border
        logoutButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding inside the button
        logoutButton.addActionListener(new ImageHowToPlay.LogoutButtonListener());

        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        gbc.gridy = 1;
        backgroundPanel.add(buttonPanel, gbc);

        howToPlayFrame.setVisible(true);
    }

    // Custom JPanel to draw the background image
    private static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
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

    private class LogoutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(
                    howToPlayFrame,
                    "Are you sure you want to log out?",
                    "Logout Confirmation",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (choice == JOptionPane.OK_OPTION) {
                howToPlayFrame.dispose(); // Close HowToPlayPage window
                LoginInputWindow loginInputWindow = new LoginInputWindow();
                loginInputWindow.setVisible(true);
            }
        }
    }
}
