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
        howToPlayFrame.setSize(screenSize.width, screenSize.height); // Full screen size
        howToPlayFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
        howToPlayFrame.setLayout(new BorderLayout());
        howToPlayFrame.setLocationRelativeTo(null);
        howToPlayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Custom panel with background image
        BackgroundPanel backgroundPanel = new BackgroundPanel(HomePage.imagePath+"/MathsHowToPlay.png");
        backgroundPanel.setLayout(new GridBagLayout()); // Center components in the middle
        howToPlayFrame.add(backgroundPanel);

        String instructions = "<html><body style='padding: 10px; font-family: Arial, sans-serif;'>" +
                "<h2 style='text-align: center; color: #3C7735; text-shadow: 1px 1px 2px #888; font-size: 22px;'>Maths Challenge How to Play</h2>" +
                "<ol style='line-height: 2.0; margin-top: 10px; font-size: 20px;'>" + // Adjusted line-height
                "<li>Understand the Layout</li><br>" +
                "<li>Identify the Operations</li><br>" +
                "<li>Solve the First Equation</li><br>" +
                "<li>Move to the Next Row or Column</li><br>" +
                "<li>Check for Multiplication and Other Operations</li><br>" +
                "<li>Verify the Answers</li><br>" +
                "</ol>" +
                "<p style='margin-top: 20px; font-size: 20px; text-align: center; color: #555;'>" + // Adjusted line-height for the paragraph
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
                new EmptyBorder(30, 30, 30, 30) // Increased padding for a larger box
        ));

        // Set preferred size for the box
        boxPanel.setPreferredSize(new Dimension(900, 520)); // Adjust size as needed
        boxPanel.add(instructionsLabel);

        // Center the instructions box in the frame
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 50, 50, 50); // Add padding around the box
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(boxPanel, gbc);

        // Button panel with Logout and Back buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make button panel transparent
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Add spacing between buttons

        // Custom button settings
        Font buttonFont = new Font("Arial", Font.BOLD, 20); // Larger font for buttons
        Border buttonBorder = BorderFactory.createEmptyBorder(25, 50, 25, 50); // Increased padding for larger buttons
        Color hoverColor = new Color(150, 200, 255); // Light blue hover color
        Color borderColor = new Color(255, 255, 255); // White border color

        // Back button
        backButton.setFont(buttonFont);
        backButton.setBackground(new Color(76, 65, 126)); // Background color
        backButton.setForeground(Color.WHITE); // Text color
        backButton.setFocusPainted(false); // Remove focus border
        backButton.setBorder(buttonBorder); // Increased padding for larger size
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        backButton.setContentAreaFilled(false); // Remove default button look
        backButton.setOpaque(true); // Enable custom background
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
        logoutButton.setBackground(new Color(23, 110, 45)); // Background color
        logoutButton.setForeground(Color.WHITE); // Text color
        logoutButton.setFocusPainted(false); // Remove focus border
        logoutButton.setBorder(buttonBorder); // Increased padding for larger size
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        logoutButton.setContentAreaFilled(false); // Remove default button look
        logoutButton.setOpaque(true); // Enable custom background
        logoutButton.setBorder(BorderFactory.createLineBorder(borderColor, 4, true)); // Rounded border with white outline
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
                logoutButton.setBackground(new Color(23, 110, 45)); // Revert to original color
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

    class LogoutButtonListener implements ActionListener {
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
                new MathsChallengeHome(); // Create a new instance of the home page
            }
        }
    }
}
