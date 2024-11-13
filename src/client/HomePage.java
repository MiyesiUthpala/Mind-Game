package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class HomePage extends JFrame {

    private static final long serialVersionUID = 1L;

    public HomePage() {
        JFrame newPage = new JFrame("Home Page");

        // Set new page size to desktop size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        newPage.setSize(screenSize.width, screenSize.height);
        newPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load the background image for the new page
        BufferedImage newPageBackgroundImage = null;
        try {
            newPageBackgroundImage = ImageIO.read(new File("C:/Users/miyes/OneDrive/Documents/Mind Game/home.jpg"));
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

        // Create a panel to hold buttons, aligned to the left
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false); // Make button panel transparent
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Create buttons with HTML text and images, set preferred size
        JButton btnFeature1 = createStyledButtonWithImage("Level 1", "<html><b style='font-size:14px; color:white;'>Level 1</b></html>", "path_to_level1_image.png");
        JButton btnFeature2 = createStyledButtonWithImage("Level 2", "<html><b style='font-size:14px; color:white;'>Level 2</b></html>", "path_to_level2_image.png");
        JButton btnFeature3 = createStyledButtonWithImage("Level 3", "<html><b style='font-size:14px; color:white;'>Level 3</b></html>", "path_to_level3_image.png");
        JButton btnLogoutNewPage = createStyledButton("Logout", "<html><b style='font-size:14px; color:white;'>Logout</b></html>");

        // Set preferred button size to make them smaller
        Dimension buttonSize = new Dimension(150, 40);
        btnFeature1.setPreferredSize(buttonSize);
        btnFeature2.setPreferredSize(buttonSize);
        btnFeature3.setPreferredSize(buttonSize);
        btnLogoutNewPage.setPreferredSize(buttonSize);

        // Add action listeners
        btnLogoutNewPage.addActionListener(e -> {
            try {
                newPage.dispose();
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnFeature2.addActionListener(e -> {
            try {
                newPage.dispose();
                ShapeChallengeHome shapeChallengeHome = new ShapeChallengeHome();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Add buttons to the button panel with spacing
        buttonPanel.add(btnFeature1);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btnFeature2);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btnFeature3);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btnLogoutNewPage);

        // Add button panel to the left of the main panel
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
            button.setHorizontalTextPosition(SwingConstants.RIGHT);
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
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }
}
