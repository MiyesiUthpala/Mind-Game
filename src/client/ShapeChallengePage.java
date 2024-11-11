package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShapeChallengePage {
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

    String[] cardList = { //track cardNames
            "darkness",
            "double",
            "fairy",
            "fighting",
            "fire",
            "grass",
            "lightning",
            "metal",
            "psychic",
            "water"
    };

    int rows = 4;
    int columns = 5;
    int cardWidth = 120;
    int cardHeight = 200;

    ArrayList<Card> cardSet; //create a deck of cards with cardNames and cardImageIcons
    ImageIcon cardBackImageIcon;

    int boardWidth = columns * cardWidth; //5*128 = 640px
    int boardHeight = rows * cardHeight; //4*90 = 360px

    JFrame frame = new JFrame("Shape Challenge Cards");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel restartGamePanel = new JPanel();
    JButton restartButton = new JButton();

    int errorCount = 0;
    ArrayList<JButton> board;
    Timer hideCardTimer;
    boolean gameReady = false;
    JButton card1Selected;
    JButton card2Selected;

    ShapeChallengePage() {
        setupCards();
        shuffleCards();

        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = (int) (screenSize.width * 0.6); // 60% of screen width
        int frameHeight = (int) (screenSize.height * 0.8); // 80% of screen height

        // Set up the frame
        frame.setSize(frameWidth, frameHeight);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the error text label
        textLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Errors: " + errorCount);

        textPanel.setPreferredSize(new Dimension(frameWidth, 40));
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        // Card game board
        board = new ArrayList<>();
        boardPanel.setLayout(new GridLayout(rows, columns, 10, 10)); // Added spacing between cards
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

        // Restart button
        restartButton.setFont(new Font("Arial", Font.PLAIN, 18));
        restartButton.setText("Restart Game");
        restartButton.setPreferredSize(new Dimension(frameWidth, 40));
        restartButton.setFocusable(false);
        restartButton.setEnabled(false);
        restartButton.addActionListener(new RestartButtonListener());
        restartGamePanel.add(restartButton);
        frame.add(restartGamePanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        // Start game with hide card timer
        hideCardTimer = new Timer(1500, new HideCardsListener());
        hideCardTimer.setRepeats(false);
        hideCardTimer.start();
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
                        errorCount += 1;
                        textLabel.setText("Errors: " + errorCount);
                        hideCardTimer.start();
                    } else {
                        card1Selected = null;
                        card2Selected = null;
                    }
                }
            }
        }
    }

    private class RestartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameReady) return;

            gameReady = false;
            restartButton.setEnabled(false);
            card1Selected = null;
            card2Selected = null;
            shuffleCards();

            // Reassign buttons with new cards
            for (int i = 0; i < board.size(); i++) {
                board.get(i).setIcon(cardSet.get(i).cardImageIcon);
            }

            errorCount = 0;
            textLabel.setText("Errors: " + errorCount);
            hideCardTimer.start();
        }
    }

    private class HideCardsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            hideCards();
        }
    }

    void setupCards() {
        cardSet = new ArrayList<Card>();
        for (String cardName : cardList) {
            //load each card image
            Image cardImg = new ImageIcon(getClass().getResource("../img/" + cardName + ".jpg")).getImage();
            ImageIcon cardImageIcon = new ImageIcon(cardImg.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH));

            //create card object and add to cardSet
            Card card = new Card(cardName, cardImageIcon);
            cardSet.add(card);
        }
        cardSet.addAll(cardSet);

        //load the back card image
        Image cardBackImg = new ImageIcon(getClass().getResource("../img/back.jpg")).getImage();
        cardBackImageIcon = new ImageIcon(cardBackImg.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH));
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
            restartButton.setEnabled(true);
        }
    }
}
