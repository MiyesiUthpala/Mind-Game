package client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ShapeChallengeHome {

//    private static final long serialVersionUID = 1L;

    public ShapeChallengeHome() {
        JFrame newPage = new JFrame("Shape Challenge Home Page");

        // Set new page size to desktop size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        newPage.setSize(screenSize.width, screenSize.height);
        newPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load the background image for the new page
        BufferedImage newPageBackgroundImage = null;
        try {
            newPageBackgroundImage = ImageIO.read(getClass().getResource("../img/background/shapehome.jpg"));
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

        // Create logout button with an icon
        JButton btnLogoutNewPage = createStyledButtonWithImage(
                "Logout",
                "<html><b style='font-size:16px; color:white;'></b></html>",
                "src/img/icons/home.png"// Replace this with the actual path to your home/logout icon
        );
        btnLogoutNewPage.addActionListener(e -> {
            try {
                newPage.dispose();
                HomePage homePage = new HomePage();
                homePage.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        topPanel.add(btnLogoutNewPage);

        // Create a panel to hold the main buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        // Create buttons
        JButton  btnFeature4= createStyledButton("Shape Challenge Game", "<html><b style='font-size:20px;'>Shape Challenge Game</b></html>", "path_to_level1_image.png");
        JButton btnFeature5 = createStyledButton("How To Play", "<html><b style='font-size:20px;'>How To Play</b></html>", "path_to_level2_image.png");
        JButton btnFeature6 = createStyledButton("Leaderboard", "<html><b style='font-size:20px;'>Leaderboard</b></html>", "path_to_level3_image.png");


        // Add action listeners

        btnFeature4.addActionListener(e -> {
            try {
                newPage.dispose();
                ShapeChallengePage shapeChallengePage = new ShapeChallengePage();
//                shapeChallengePage.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnFeature5.addActionListener(e -> {
            try {
                newPage.dispose();
                ShapeHowToPlay shapeHowToPlay = new ShapeHowToPlay();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnFeature6.addActionListener(e -> {
            try {
                newPage.dispose();
                ShapeChallengeLeaderboard shapeChallengeLeaderboard = new ShapeChallengeLeaderboard();
                shapeChallengeLeaderboard.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        // Set button size

        Dimension buttonSize = new Dimension(350, 120);
        btnFeature4.setPreferredSize(buttonSize);
        btnFeature5.setPreferredSize(buttonSize);
        btnFeature6.setPreferredSize(buttonSize);

        // Add buttons to the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(btnFeature4, gbc);
        gbc.gridy++;
        buttonPanel.add(btnFeature5, gbc);
        gbc.gridy++;
        buttonPanel.add(btnFeature6, gbc);

        // Add panels to main layout
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        newPage.add(mainPanel);
        newPage.setVisible(true);
    }

    private JButton createStyledButtonWithImage(String text, String htmlText, String imagePath) {
        JButton button = new JButton(htmlText) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                // Enable anti-aliasing for smoother gradients
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw a gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(70, 130, 180),
                        getWidth(), getHeight(), new Color(100, 149, 237)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Draw shadow
                g2d.setColor(new Color(0, 0, 0, 50)); // Semi-transparent black
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 15, 15);

                super.paintComponent(g2d);
                g2d.dispose();
            }
        };

        // Set button properties
        button.setFocusPainted(false);
        button.setContentAreaFilled(false); // Disable default background
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Load and resize the icon
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Set desired icon size
            icon = new ImageIcon(scaledImage);
            button.setIcon(icon);
            button.setHorizontalTextPosition(SwingConstants.RIGHT); // Align text to the right of the icon
        } catch (Exception e) {
            e.printStackTrace();
        }

        return button;
    }

    private JButton createStyledButton(String text, String htmlText, String imagePath) {
        JButton button = new JButton(htmlText) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                // Enable anti-aliasing for smoother gradients
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw a gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(21, 45, 87),
                        getWidth(), getHeight(), new Color(87, 159, 220)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Draw shadow
                g2d.setColor(new Color(0, 0, 0, 50)); // Semi-transparent black
                g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 15, 15);

                super.paintComponent(g2d);
                g2d.dispose();
            }
        };

        // Set button properties
        button.setFocusPainted(false);
        button.setContentAreaFilled(false); // Disable default background
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Load and resize the icon
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Set desired icon size
            icon = new ImageIcon(scaledImage);
            button.setIcon(icon);
            button.setHorizontalTextPosition(SwingConstants.RIGHT); // Align text to the right of the icon
        } catch (Exception e) {
            e.printStackTrace();
        }

        return button;
    }


}
