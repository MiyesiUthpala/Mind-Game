package client;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MathsHowToPlay {

    JFrame howToPlayFrame = new JFrame("How to Play");
    JButton logoutButton = new JButton("Logout");
    JButton backButton = new JButton("Back to Game");

    MathsHowToPlay() {
        // Get screen dimensions and set frame size to full screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        howToPlayFrame.setSize(screenSize.width, screenSize.height);
        howToPlayFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        howToPlayFrame.setLayout(new BorderLayout());
        howToPlayFrame.setLocationRelativeTo(null);
        howToPlayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Custom panel with background image
        BackgroundPanel backgroundPanel = new BackgroundPanel(HomePage.imagePath+"/MathsHowToPlay.png");
        backgroundPanel.setLayout(new GridBagLayout()); // Center components in the middle
        howToPlayFrame.add(backgroundPanel);

        String instructions = "<html><body style='padding: 10px; font-family: Arial, sans-serif;'>" +
                "<h2 style='text-align: center; color: #3C7735; text-shadow: 1px 1px 2px #888; font-size: 22px;'>Maths Challenge How to Play</h2>" +
                "<ol style='line-height: 2.0; margin-top: 10px; font-size: 20px;'>" +
                "<li>Understand the Layout</li><br>" +
                "<li>Identify the Operations</li><br>" +
                "<li>Solve the First Equation</li><br>" +
                "<li>Move to the Next Row or Column</li><br>" +
                "<li>Check for Multiplication and Other Operations</li><br>" +
                "<li>Verify the Answers</li><br>" +
                "</ol>" +
                "<p style='margin-top: 20px; font-size: 20px; text-align: center; color: #555;'>" +
                "Good luck and have fun!</p>" +
                "</body></html>";



        JLabel instructionsLabel = new JLabel(instructions);
        instructionsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        instructionsLabel.setVerticalAlignment(SwingConstants.TOP);
        instructionsLabel.setHorizontalAlignment(SwingConstants.CENTER);


        // Box panel for instructions (reduced transparency)
        JPanel boxPanel = new JPanel();
        boxPanel.setBackground(new Color(255, 255, 255, 180));  // Chat-gpt
        boxPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),
                new EmptyBorder(30, 30, 30, 30)
        ));

        // Set preferred size for the box
        boxPanel.setPreferredSize(new Dimension(900, 520));
        boxPanel.add(instructionsLabel);

        // Center the instructions box in the frame
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 50, 50, 50);
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(boxPanel, gbc);

        // Button panel with Logout and Back buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make button panel transparent
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Custom button settings
        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        Border buttonBorder = BorderFactory.createEmptyBorder(25, 50, 25, 50);
        Color hoverColor = new Color(150, 200, 255);
        Color borderColor = new Color(255, 255, 255);

        // Back button
        backButton.setFont(buttonFont);
        backButton.setBackground(new Color(76, 65, 126));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(buttonBorder);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(true);
        backButton.setBorder(BorderFactory.createLineBorder(borderColor, 4, true)); // Rounded border with white outline
        backButton.addActionListener(e -> {
            howToPlayFrame.dispose(); // Close HowToPlayPage window
            new MathsChallengeHome(); // Redirect to Maths Challenge home page
        });

        // Add hover effect for Back button
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(hoverColor); // Change to hover color
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(76, 65, 126)); // Revert to original color
            }
        });

        // Logout button
        logoutButton.setFont(buttonFont);
        logoutButton.setBackground(new Color(23, 110, 45));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(buttonBorder);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setContentAreaFilled(false);
        logoutButton.setOpaque(true);
        logoutButton.setBorder(BorderFactory.createLineBorder(borderColor, 4, true));
        logoutButton.addActionListener(e -> {
            howToPlayFrame.dispose(); // Close HowToPlayPage window
            LoginInputWindow loginInputWindow = new LoginInputWindow();
            loginInputWindow.setVisible(true);
        });
        // Add hover effect for Logout button
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(hoverColor); // Change to hover color
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutButton.setBackground(new Color(23, 110, 45));
            }
        });

        // Add buttons to the panel
        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        // Add button panel to the frame
        gbc.gridy = 1;
        backgroundPanel.add(buttonPanel, gbc);

        // Show the frame
        howToPlayFrame.setVisible(true);


    }

    // Custom JPanel to draw the background image
    static class BackgroundPanel extends JPanel {
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

}
