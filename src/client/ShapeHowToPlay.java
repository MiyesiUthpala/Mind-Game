package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShapeHowToPlay {
    JFrame howToPlayFrame = new JFrame("How to Play");
    JButton logoutButton = new JButton("Logout");
    JButton backButton = new JButton("Back to Game");

    ShapeHowToPlay() {
        howToPlayFrame.setSize(600, 400);
        howToPlayFrame.setLayout(new BorderLayout());
        howToPlayFrame.setLocationRelativeTo(null);
        howToPlayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Instructions text
        String instructions = "<html><body>" +
                "<h2>How to Play:</h2>" +
                "<ol>" +
                "<li>Understand the Layout</li>" +
                "<li>Identify the Operations</li>" +
                "<li>Solve the First Equation</li>" +
                "<li>Move to the Next Row or Column</li>" +
                "<li>Check for Multiplication and Other Operations</li>" +
                "<li>Verify the Answers</li>" +
                "<li>Complete the Challenge</li>" +
                "</ol>" +
                "</body></html>";

        JLabel instructionsLabel = new JLabel(instructions);
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionsLabel.setVerticalAlignment(SwingConstants.TOP);

        JScrollPane scrollPane = new JScrollPane(instructionsLabel);
        howToPlayFrame.add(scrollPane, BorderLayout.CENTER);

        // Button panel with Logout and Back buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Back button to return to the Shape Challenge home page
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(e -> {
            howToPlayFrame.dispose(); // Close HowToPlayPage window
            new ShapeChallengePage(); // Redirect to Shape Challenge home page
        });

        // Logout button
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutButton.addActionListener(new LogoutButtonListener());

        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);

        howToPlayFrame.add(buttonPanel, BorderLayout.SOUTH);
        howToPlayFrame.setVisible(true);
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
                new ShapeChallengeHome(); // Create a new instance of the home page
            }
        }
    }
}
