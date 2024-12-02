package client;

import server.MathsChallengeEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MathsChallengeGameStart extends JFrame implements ActionListener {

    private static final long serialVersionUID = -107785653906635L;

    private JLabel questArea = null;
    private MathsChallengeEngine myGame = null;
    private BufferedImage currentGame = null;

    private JLabel timeLabel;
    private JLabel scoreLabel;

    private Timer timer;
    private int timeLeft = 120; // 120 seconds countdown
    private int turnCount = 0; // Game limit is 5 turns
    private int score = 0;

    private JButton backButton;

    // Track whether the game is ready for action or not
    private boolean gameReady = true;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            System.out.println("Returning to home...");
            gameReady = false;
            timer.stop();
            new BackButtonListener().actionPerformed(e);
            return;
        }

        if (turnCount >= 5 || timeLeft <= 0) {
            // End game due to time running out or completing 5 turns
            timer.stop();
            if (timeLeft <= 0) {
                JOptionPane.showMessageDialog(this, "Time is Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Show success message with the score
                int option = JOptionPane.showConfirmDialog(this,
                        "Successfully Done. Congratulations!\nYour Final Score: " + score,
                        "Game Complete",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);

                // If "OK" is clicked (the default option)
                if (option == JOptionPane.DEFAULT_OPTION) {
                    // Redirect to Maths Challenge Home Page
                    dispose(); // Close the current window
                    new MathsChallengeHome(); // Create and show the home page
                }
            }
            return;
        }

        int solution;
        try {
            solution = Integer.parseInt(e.getActionCommand());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please select a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean correct = myGame.checkSolution(solution);

        if (correct) {
            turnCount++;
            score += turnCount * 50; // Incremental score for correct attempts
            updateScoreLabel();
            if (turnCount <= 5) {
                currentGame = myGame.nextGame();
                ImageIcon ii = new ImageIcon(currentGame);
                questArea.setIcon(ii);
            }
        } else {
            // Reduce score by 50 for incorrect attempts
            score -= 50;
            if (score < 0) score = 0; // Ensure score doesn't go negative
            updateScoreLabel();

            // Optionally display a feedback message for incorrect answers
            JOptionPane.showMessageDialog(this, "Incorrect answer! 50 points deducted.", "Try Again", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateTimeLabel() {
        timeLabel.setText("Time Left: " + timeLeft + " sec");
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    private void initGame(String player) {
        setTitle("What is the missing value?");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main game panel
        JPanel gamePanel = new JPanel();
        gamePanel.setOpaque(false);
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

        // Add the "What is the banana value?" label
        JLabel bananaValueLabel = new JLabel("What is the banana value?");
        bananaValueLabel.setFont(new Font("Courier New", Font.ITALIC | Font.BOLD, 28));
        bananaValueLabel.setForeground(Color.WHITE);
        bananaValueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bananaValueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Set a transparent background with alpha
        bananaValueLabel.setOpaque(true);
        bananaValueLabel.setBackground(new Color(78, 162, 160, 255)); // Black with 50% transparency (alpha = 128)
        gamePanel.add(bananaValueLabel);

        // Load and scale the background image
        ImageIcon originalBackground = new ImageIcon(HomePage.imagePath + "/MathsGame.jpg");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Image scaledBackground = originalBackground.getImage().getScaledInstance(
                screenSize.width,
                screenSize.height,
                Image.SCALE_SMOOTH
        );
        JLabel backgroundLabel = new JLabel(new ImageIcon(scaledBackground));
        backgroundLabel.setLayout(new BorderLayout());
        add(backgroundLabel, BorderLayout.CENTER);

        // Header panel for time, score, and back button
        JPanel headerPanel = new JPanel(new GridLayout(1, 3));
        headerPanel.setOpaque(false);

        // Back button panel
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setOpaque(false);
        backButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        backButton = new JButton("Back");
        backButton.addActionListener(new BackButtonListener());
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.setBackground(new Color(76, 65, 126));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        backButtonPanel.add(backButton);
        headerPanel.add(backButtonPanel);

        // Time label
        timeLabel = new JLabel("Time Left: " + timeLeft + " sec");
        timeLabel.setOpaque(true);
        timeLabel.setBackground(new Color(108, 161, 103, 255));  // Semi-transparent background
        timeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(timeLabel);


        // Score label
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(new Color(23, 110, 45, 255));  // Semi-transparent background
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(scoreLabel);
        backgroundLabel.add(headerPanel, BorderLayout.NORTH);

        // Main game panel
//        JPanel gamePanel = new JPanel();
//        gamePanel.setOpaque(false);
//        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

        myGame = new MathsChallengeEngine(player);
        currentGame = myGame.nextGame();

        Image scaledImage = currentGame.getScaledInstance(800, 500, Image.SCALE_SMOOTH);
        ImageIcon ii = new ImageIcon(scaledImage);

        questArea = new JLabel(ii);
        questArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        questArea.setPreferredSize(new Dimension(800, 500));  // Set the fixed size for questArea
        questArea.setMaximumSize(new Dimension(800, 500));  // Ensures the questArea cannot exceed this size
        questArea.setMinimumSize(new Dimension(800, 500));  // Ensures the questArea won't shrink below this size
        gamePanel.add(questArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout());

        for (int i = 0; i < 10; i++) {
            JButton btn = new JButton(String.valueOf(i));
            buttonPanel.add(btn);
            btn.addActionListener(this);
        }
        gamePanel.add(buttonPanel);

        backgroundLabel.add(gamePanel, BorderLayout.CENTER);

        // Timer setup
        timer = new Timer(1000, e -> {
            if (timeLeft > 0) {
                timeLeft--;
                updateTimeLabel();
            } else {
                timer.stop();
                // Show the "Time is Over!" message
                int option = JOptionPane.showConfirmDialog(
                        this,
                        "Time is Over!",
                        "Game Over",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE
                );

                // Redirect to Maths Challenge Home Page after "OK" is clicked
                if (option == JOptionPane.DEFAULT_OPTION) {
                    dispose();
                    SwingUtilities.invokeLater(() -> new MathsChallengeHome());
                }
            }
        });
        timer.start();
    }

    public MathsChallengeGameStart() {
        super();
        initGame(null);
    }

    public static void main(String[] args) {
        MathsChallengeGameStart myGUI = new MathsChallengeGameStart();
        myGUI.setVisible(true);
    }

    class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameReady) return;

            dispose();
            new MathsChallengeHome();
        }
    }
}
