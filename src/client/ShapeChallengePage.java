package client;

import client.model.Score;
import mindgameinterface.LoginInterface;
import mindgameinterface.ScoreBoardInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ShapeChallengePage {

    private ScoreBoardInterface myService;

    {
        try {
            myService = (ScoreBoardInterface) Naming.lookup("rmi://localhost:1099/ScoreBoardService");
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    class Card {
        String cardName;
        ImageIcon cardImageIcon;

        Card(String cardName, ImageIcon cardImageIcon) {
            this.cardName = cardName;
            this.cardImageIcon = cardImageIcon;
        }

        public String toString() {
            return cardName;
        }
    }

    String[] cardList = {
            "circle", "diamond", "hexagon", "octagon", "oval",
            "pentagon", "rectangle", "square", "star", "triangle"
    };

    int rows = 4;
    int columns = 5;
    int cardWidth = 160;
    int cardHeight = 300;
    int cardWidth1 = 350;
    int cardHeight1 =200;

    ArrayList<Card> cardSet;
    ImageIcon cardBackImageIcon;

    JFrame frame = new JFrame("Shape Challenge Cards");
    JLabel textLabel = new JLabel();
    JLabel timerLabel = new JLabel("Time: 120s"); // Timer display
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel logoutPanel = new JPanel();
    JButton logoutButton = new JButton("Logout");

    JLabel scoreLabel = new JLabel();

    int errorCount = 0;
    int matchedCount = 0;
    int totalPairs = cardList.length;
    int errorLimit = 5;

    ArrayList<JButton> board;
    Timer hideCardTimer;
    Timer gameTimer; // Game countdown timer
    boolean gameReady = false;
    JButton card1Selected;
    JButton card2Selected;

    int timeRemaining = 120; // 120-second timer
    int finalScore = 0;

    ShapeChallengePage() {
        setupCards();
        shuffleCards();



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizes the frame
        frame.setUndecorated(false); // Set to true if you want no window decorations
        frame.setLocationRelativeTo(null);

        // Configure textPanel with updated layout
        textPanel.setLayout(new GridLayout(1, 3)); // Three columns for Errors, Time, and Score
        textPanel.setPreferredSize(new Dimension(800, 40));

// Configure textLabel
        textLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("<html><b>Errors:</b> " + errorCount + "</html>");
        textPanel.add(textLabel);

// Configure timerLabel
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        timerLabel.setText("<html><b>Time:</b> " + timeRemaining + "s</html>");
        textPanel.add(timerLabel);

// Configure scoreLabel
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setText("<html><b>Score:</b> " + finalScore + "</html>");
        textPanel.add(scoreLabel);

// Add textPanel to the frame
        frame.add(textPanel, BorderLayout.NORTH);

        board = new ArrayList<>();
        boardPanel.setLayout(new GridLayout(rows, columns, 10, 10));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < cardSet.size(); i++) {
            JButton tile = new JButton();
            tile.setPreferredSize(new Dimension(cardWidth, cardHeight));
            tile.setOpaque(true);
            tile.setIcon(cardSet.get(i).cardImageIcon);
            tile.setFocusable(false);
            tile.addActionListener(new CardClickListener());
            board.add(tile);
            boardPanel.add(tile);
        }
        frame.add(boardPanel, BorderLayout.CENTER);

        logoutButton.setFont(new Font("Arial", Font.PLAIN, 18));
        logoutButton.setPreferredSize(new Dimension(800, 40));
        logoutButton.setFocusable(false);
        logoutButton.addActionListener(new LogoutButtonListener());
        logoutPanel.add(logoutButton);
        frame.add(logoutPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        hideCardTimer = new Timer(1500, new HideCardsListener());
        hideCardTimer.setRepeats(false);
        hideCardTimer.start();

        gameTimer = new Timer(1000, new GameTimerListener()); // Timer for countdown
        gameTimer.start();
    }

    class GameTimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timeRemaining--;
            timerLabel.setText("Time: " + timeRemaining + "s");
            if (timeRemaining <= 0) {
                gameTimer.stop();
                showGameOverMessage();
            }
        }
    }

    class CardClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameReady) return;

            JButton tile = (JButton) e.getSource();
            if (tile.getIcon() == cardBackImageIcon) {
                if (card1Selected == null) {
                    card1Selected = tile;
                    int index = board.indexOf(card1Selected);
                    card1Selected.setIcon(cardSet.get(index).cardImageIcon);
                } else if (card2Selected == null) {
                    card2Selected = tile;
                    int index = board.indexOf(card2Selected);
                    card2Selected.setIcon(cardSet.get(index).cardImageIcon);

                    if (card1Selected.getIcon() != card2Selected.getIcon()) {
                        errorCount++;
                        textLabel.setText("Errors: " + errorCount);

                        if (errorCount > errorLimit) {
                            showTryAgainMessage();
                        } else {
                            hideCardTimer.start();
                        }
                    } else {
                        matchedCount++;
                        calculateScore(); // Calculate score after each match
                        card1Selected = null;
                        card2Selected = null;

                        if (matchedCount == totalPairs) {
                            gameTimer.stop();
                            Score score = new Score();
                            score.setPlayer_name(LoginGUI.mySessionCookie);
                            score.setScore(finalScore);
                            score.setGame("Shape Game");
                            try {
                                myService.addScore(score);
                            } catch (RemoteException ex) {
                                throw new RuntimeException(ex);
                            }
                            showSuccessMessage();
                        }
                    }
                }
            }
        }
    }

    private void calculateScore() {
        int scorePerMatch;
        switch (errorCount) {
            case 0: scorePerMatch = 200; break;
            case 1: scorePerMatch = 100; break;
            case 2: scorePerMatch = 80; break;
            case 3: scorePerMatch = 60; break;
            case 4: scorePerMatch = 40; break;
            case 5: scorePerMatch = 20; break;
            default: scorePerMatch = 0; break;
        }
        finalScore += scorePerMatch;
    }

    private void showSuccessMessage() {
        JOptionPane.showMessageDialog(frame, "Congratulations! You finished the game!\nScore: " + finalScore, "Success", JOptionPane.INFORMATION_MESSAGE);
        logoutButton.setEnabled(true); // Enable the logout button after game ends
    }

    private void showTryAgainMessage() {
        JOptionPane.showMessageDialog(frame, "Too many errors. Try Again!", "Game Over", JOptionPane.WARNING_MESSAGE);
        resetGame();
    }

    private void showGameOverMessage() {
        JOptionPane.showMessageDialog(frame, "Time's up! Final Score: " + finalScore, "Time Over", JOptionPane.WARNING_MESSAGE);
        resetGame();
    }

    class HideCardsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (card1Selected != null && card2Selected != null) {
                card1Selected.setIcon(cardBackImageIcon);
                card2Selected.setIcon(cardBackImageIcon);
                card1Selected = null;
                card2Selected = null;
            }
            // If not two cards selected, just flip all
            else {
                for (JButton cardButton : board) {
                    cardButton.setIcon(cardBackImageIcon);
                }
                gameReady = true; // Allow player to start flipping cards
            }
        }
    }

    class LogoutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameReady) return;

            // Display logout confirmation message
            int choice = JOptionPane.showConfirmDialog(
                    frame,
                    "You are logged out. Do you want to go back to the home page?",
                    "Logged Out",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (choice == JOptionPane.OK_OPTION) {
                gameTimer.stop();
                frame.dispose(); // Close current game frame
                new ShapeChallengeHome(); // Open new instance for the home page
            }
        }
    }

    void hideCards() {
        if (gameReady && card1Selected != null && card2Selected != null) { //only flip 2 cards
            card1Selected.setIcon(cardBackImageIcon);
            card1Selected = null;
            card2Selected.setIcon(cardBackImageIcon);
            card2Selected = null;
        }
        else { //flip all cards face down
            for (int i = 0; i < board.size(); i++) {
                board.get(i).setIcon(cardBackImageIcon);
            }
            gameReady = true;
            logoutButton.setEnabled(true);
        }
    }
    void resetGame() {
        gameReady = false;
        //logoutButton.setEnabled(false);
        card1Selected = null;
        card2Selected = null;
        matchedCount = 0;
        errorCount = 0;
        timeRemaining = 120;
        finalScore = 0;
        textLabel.setText("Errors: " + errorCount);
        timerLabel.setText("Time: 120s");

        shuffleCards();
        for (int i = 0; i < board.size(); i++) {
            board.get(i).setIcon(cardBackImageIcon);
        }
        hideCardTimer.start();
        gameTimer.restart();
    }

    void setupCards() {
        cardSet = new ArrayList<Card>();
        for (String cardName : cardList) {
            // Load each card image and scale it
            Image cardImg = new ImageIcon(getClass().getResource("../img/" + cardName + ".jpg")).getImage();
            ImageIcon cardImageIcon = new ImageIcon(cardImg.getScaledInstance(cardWidth, cardWidth, java.awt.Image.SCALE_SMOOTH)); // Setting width and height to be the same for square
            Card card = new Card(cardName, cardImageIcon);
            cardSet.add(card);
        }
        cardSet.addAll(cardSet); // Double the deck

        // Load the back card image with square dimensions
        Image cardBackImg = new ImageIcon(getClass().getResource("../img/ShapeBackImage.jpg")).getImage();
        cardBackImageIcon = new ImageIcon(cardBackImg.getScaledInstance(cardWidth1, cardHeight1, java.awt.Image.SCALE_SMOOTH)); // Ensure it's scaling correctly
    }

    void shuffleCards() {
        System.out.println(cardSet);
        //shuffle
        for (int i = 0; i < cardSet.size(); i++) {
            int j = (int) (Math.random() * cardSet.size()); //get random index
            //swap
            Card temp = cardSet.get(i);
            cardSet.set(i, cardSet.get(j));
            cardSet.set(j, temp);
        }
        System.out.println(cardSet);
    }

    public static void main(String[] args) {
        new ShapeChallengePage();
    }

}
