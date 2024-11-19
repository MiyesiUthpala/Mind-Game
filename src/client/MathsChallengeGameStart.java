package client;

import server.MathsChallengeEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MathsChallengeGameStart extends JFrame implements ActionListener {

    private static final long serialVersionUID = -107785653906635L;

    private JLabel questArea = null;
    private MathsChallengeEngine myGame = null;
    private BufferedImage currentGame = null;
    private JTextArea infoArea = null;
    private Timer timer;
    private int timeLeft = 120; // 120 seconds countdown
    private int turnCount = 0; // Game limit is 10 turns
    private int score = 0;

    private JButton logoutButton;

    // Track whether the game is ready for action or not
    private boolean gameReady = true;

    /**
     * Method that is called when a button has been pressed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoutButton) {
            // Action for logout button
            System.out.println("Logging out...");
            // Close the current game and return to home page
            gameReady = false;  // Indicate that the game is no longer active
            new LogoutButtonListener().actionPerformed(e);  // Trigger the logout flow
            return;
        }

        if (turnCount >= 10 || timeLeft <= 0) {
            infoArea.setText("Game Over! Final Score: " + score);
            return;
        }

        int solution = Integer.parseInt(e.getActionCommand());
        boolean correct = myGame.checkSolution(solution);

        if (correct) {
            if (turnCount == 0) { // First time getting it correct in the turn
                score += 100; // 100 points for correct answer on first try
            }
            currentGame = myGame.nextGame();
            ImageIcon ii = new ImageIcon(currentGame);
            questArea.setIcon(ii);
            infoArea.setText("Good!  Score: " + score);
        } else {
            infoArea.setText("Oops. Try again!  Score: " + score);
        }

        // Increment turn count and update game state
        turnCount++;
        if (turnCount == 10 || timeLeft <= 0) {
            infoArea.setText("Game Over! Final Score: " + score);
        }
    }

    /**
     * Initializes the game.
     * @param player
     */
    private void initGame(String player) {
        setSize(690, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("What is the missing value?");
        JPanel panel = new JPanel();

        myGame = new MathsChallengeEngine(player);
        currentGame = myGame.nextGame();

        infoArea = new JTextArea(1, 40);
        infoArea.setEditable(false);
        infoArea.setText("What is the value of the banana?   Score: 0");

        JScrollPane infoPane = new JScrollPane(infoArea);
        panel.add(infoPane);

        ImageIcon ii = new ImageIcon(currentGame);
        questArea = new JLabel(ii);
        questArea.setSize(330, 600);

        JScrollPane questPane = new JScrollPane(questArea);
        panel.add(questPane);

        // Add number buttons
        for (int i = 0; i < 10; i++) {
            JButton btn = new JButton(String.valueOf(i));
            panel.add(btn);
            btn.addActionListener(this);
        }

        // Set up timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                    infoArea.setText("Time Left: " + timeLeft + " sec  Score: " + score);
                } else {
                    infoArea.setText("Time's up! Final Score: " + score);
                    timer.stop();
                }
            }
        });
        timer.start();

        // Add logout button and its action listener
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new LogoutButtonListener()); // Assign LogoutButtonListener to logout button
        panel.add(logoutButton);

        getContentPane().add(panel);
        panel.repaint();
    }

    /**
     * Default player is null.
     */
    public MathsChallengeGameStart() {
        super();
        initGame(null);
    }

    /**
     * Use this to start GUI, e.g., after login.
     *
     * @param player
     */
    public MathsChallengeGameStart(String player) {
        super();
        initGame(player);
    }

    /**
     * Show the home page after logout.
     */
    private void showHomePage() {
        // Here you can either display an HTML page or a new JFrame
        JFrame homePage = new JFrame("Home Page");
        homePage.setSize(500, 400);
        homePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel welcomeLabel = new JLabel("Welcome to the Maths Challenge Game!");
        welcomeLabel.setBounds(100, 150, 300, 50);
        homePage.add(welcomeLabel);
        homePage.setVisible(true);
    }

    /**
     * Main entry point into the equation game. Can be used without login for testing.
     *
     * @param args not used.
     */
    public static void main(String[] args) {
        MathsChallengeGameStart myGUI = new MathsChallengeGameStart();
        myGUI.setVisible(true);
    }

    // LogoutButtonListener to handle logout functionality
    class LogoutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameReady) return;

            // Display logout confirmation message
            int choice = JOptionPane.showConfirmDialog(
                    MathsChallengeGameStart.this,
                    "You are logged out. Do you want to go back to the home page?",
                    "Logged Out",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (choice == JOptionPane.OK_OPTION) {
                dispose(); // Close current game frame
                new MathsChallengeHome(); // Open the home page
            }
        }
    }
}
