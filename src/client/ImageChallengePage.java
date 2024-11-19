package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ImageChallengePage {
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
            "earth", "jupiter", "mars", "meteorite", "moon",
            "neptune", "saturn", "spacecraft", "stars", "uranus"
    };

    int rows = 4;
    int columns = 5;
    int cardWidth = 120;
    int cardHeight = 200;

    ArrayList<Card> cardSet;
    ImageIcon cardBackImageIcon;

    JFrame frame = new JFrame("Shape Challenge Cards");
    JLabel textLabel = new JLabel();
    JLabel timerLabel = new JLabel("Time: 120s"); // Timer display
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel logoutPanel = new JPanel();
    JButton logoutButton = new JButton("Logout");


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

    ImageChallengePage() {
        setupCards();
        shuffleCards();

        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Errors: " + errorCount);

        timerLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        timerLabel.setHorizontalAlignment(JLabel.CENTER);

        textPanel.setLayout(new BorderLayout());
        textPanel.setPreferredSize(new Dimension(800, 40));
        textPanel.add(textLabel, BorderLayout.WEST);
        textPanel.add(timerLabel, BorderLayout.EAST);
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
            tile.addActionListener(new ImageChallengePage.CardClickListener());
            board.add(tile);
            boardPanel.add(tile);
        }
        frame.add(boardPanel, BorderLayout.CENTER);

        logoutButton.setFont(new Font("Arial", Font.PLAIN, 18));
        logoutButton.setPreferredSize(new Dimension(800, 40));
        logoutButton.setFocusable(false);
        logoutButton.addActionListener(new ImageChallengePage.LogoutButtonListener());
        logoutPanel.add(logoutButton);
        frame.add(logoutPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        hideCardTimer = new Timer(1500, new ImageChallengePage.HideCardsListener());
        hideCardTimer.setRepeats(false);
        hideCardTimer.start();

        gameTimer = new Timer(1000, new ImageChallengePage.GameTimerListener()); // Timer for countdown
        gameTimer.start();
    }

    private class GameTimerListener implements ActionListener {
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

    private class CardClickListener implements ActionListener {
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

    private class HideCardsListener implements ActionListener {
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

    private class LogoutButtonListener implements ActionListener {
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
                frame.dispose(); // Close current game frame
                new ImageChallengeHome(); // Open new instance for the home page
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
            ImageChallengePage.Card card = new ImageChallengePage.Card(cardName, cardImageIcon);
            cardSet.add(card);
        }
        cardSet.addAll(cardSet); // Double the deck

        // Load the back card image with square dimensions
        Image cardBackImg = new ImageIcon(getClass().getResource("../img/backimage.jpg")).getImage();
        cardBackImageIcon = new ImageIcon(cardBackImg.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH)); // Ensure it's scaling correctly
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
        new ImageChallengePage();
    }

}
