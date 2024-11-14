package client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MathsChallengeHome extends JFrame {

    private static final long serialVersionUID = 1L;

    public MathsChallengeHome (){
        JFrame newPage = new JFrame("Shape Challenge Home Page");

        // Set new page size to desktop size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        newPage.setSize(screenSize.width, screenSize.height);
        newPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load the background image for the new page
        BufferedImage newPageBackgroundImage = null;
        try {
            newPageBackgroundImage = ImageIO.read(new File("C:/Users/miyes/OneDrive/Documents/Mind Game/shapehome.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Custom JPanel to paint the background image
        BufferedImage finalNewPageBackgroundImage = newPageBackgroundImage;
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (finalNewPageBackgroundImage != null) {
                    g.drawImage(finalNewPageBackgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Create a panel to hold buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false); // Make button panel transparent

        // Create buttons with HTML text and images
        JButton btnFeature4 = createStyledButtonWithImage("Maths Challenge Game", "<html><b style='font-size:12px; color:white;'>Maths Challenge Game</b></html>", "path_to_level1_image.png");
        JButton btnFeature5 = createStyledButtonWithImage("How To Play", "<html><b style='font-size:12px; color:white;'>How To Play</b></html>", "path_to_level2_image.png");
        JButton btnFeature6 = createStyledButtonWithImage("Leaderboard", "<html><b style='font-size:12px; color:white;'>Leaderboard</b></html>", "path_to_level3_image.png");
        JButton btnLogoutNewPage = createStyledButton("Logout", "<html><b style='font-size:12px; color:white;'>Logout</b></html>");

        // Set button sizes and actions
        Dimension buttonSize = new Dimension(250, 70);
        btnFeature4.setPreferredSize(buttonSize);
        btnFeature5.setPreferredSize(buttonSize);
        btnFeature6.setPreferredSize(buttonSize);
        btnLogoutNewPage.setPreferredSize(buttonSize);

        btnLogoutNewPage.addActionListener(e -> {
            try {
                newPage.dispose();
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnFeature4.addActionListener(e -> {
            try {
                newPage.dispose();
                MathsChallengeGameStart mathsChallengeGameStart = new MathsChallengeGameStart();
                mathsChallengeGameStart.setVisible(true); // Ensure the game window is visible
            } catch (Exception ex) {
                System.out.println("A problem occurred: " + ex.toString());
                ex.printStackTrace();
            }
        });
        btnFeature5.addActionListener(e -> {
            try {
                newPage.dispose();
                MathsHowToPlay mathsHowToPlay = new MathsHowToPlay();
            } catch (Exception ex) {
                System.out.println("A problem occurred: " + ex.toString());
                ex.printStackTrace();
            }
        });

        // Add buttons to panel with spacing
        buttonPanel.add(btnFeature4);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btnFeature5);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btnFeature6);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btnLogoutNewPage);

        // Align buttons to the left of the main panel
        mainPanel.add(buttonPanel, BorderLayout.WEST);

        // Add main panel to the frame and make it visible
        newPage.add(mainPanel);
        newPage.setVisible(true);
    }

    private JButton createStyledButtonWithImage(String text, String htmlText, String imagePath) {
        JButton button = new JButton(htmlText);
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Load and set the image
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            button.setIcon(icon);
            button.setHorizontalTextPosition(SwingConstants.RIGHT); // Adjust text and icon positioning
        } catch (Exception e) {
            e.printStackTrace();
        }

        return button;
    }

    private JButton createStyledButton(String text, String htmlText) {
        JButton button = new JButton(htmlText);
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Adjust padding
        return button;
    }
}
