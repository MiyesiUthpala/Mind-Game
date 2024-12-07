package client;

import client.ImageChallengeHome;
import client.LoginGUI;
import client.MathsChallengeHome;
import client.ShapeChallengeHome;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class HomePage extends JFrame {

    private static final long serialVersionUID = 1L;
    public static String imagePath = "/Users/miyes/OneDrive/Documents/Mind Game";

    public static boolean mathsGameComplete = false;
    private boolean isMathChallengeCompleted = false;

    public HomePage() {
//        JFrame newPage = new JFrame("Home Page");

        // Set new page size to desktop size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // chat gpt
        this.setSize(screenSize.width, screenSize.height);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load the background image for the new page
        BufferedImage newPageBackgroundImage = null;
        try {
            newPageBackgroundImage = ImageIO.read(getClass().getResource("../img/background/home.jpg"));
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
                imagePath+"/icon/home.png"
        );
        btnLogoutNewPage.addActionListener(e -> {
            try {
                this.dispose();
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
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

        // Create main buttons
        JButton btnFeature1 = createStyledButton("Maths Challenge", "<html><b style='font-size:20px; color:white;'>Maths Challenge</b></html>", "path_to_level1_image.png");
        JButton btnFeature2 = createStyledButton("Shape Challenge", "<html><b style='font-size:20px; color:white;'>Shape Challenge</b></html>", "path_to_level2_image.png");
        JButton btnFeature3 = createStyledButton("Image Challenge", "<html><b style='font-size:20px; color:white;'>Image Challenge</b></html>", "path_to_level3_image.png");

        if (mathsGameComplete)
//        if (true)
        {
            btnFeature2.setEnabled(true);
            btnFeature3.setEnabled(true);
        } else {
            btnFeature2.setEnabled(false);
            btnFeature3.setEnabled(false);
        }
        // Add action listeners

        btnFeature1.addActionListener(e -> {
            try {
                this.dispose();
                MathsChallengeHome mathsChallengeHome = new MathsChallengeHome();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        btnFeature2.addActionListener(e -> {
            try {
                    this.dispose();
                    ShapeChallengeHome shapeChallengeHome = new ShapeChallengeHome();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnFeature3.addActionListener(e -> {
            try {
                    this.dispose();
                    ImageChallengeHome imageChallengeHome = new ImageChallengeHome();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        // Set button size
        Dimension buttonSize = new Dimension(300, 100);
        btnFeature1.setPreferredSize(buttonSize);
        btnFeature2.setPreferredSize(buttonSize);
        btnFeature3.setPreferredSize(buttonSize);

        // Add buttons to the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        buttonPanel.add(btnFeature1, gbc);
        gbc.gridy++;
        buttonPanel.add(btnFeature2, gbc);
        gbc.gridy++;
        buttonPanel.add(btnFeature3, gbc);

        // Add panels to the main layout
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add main panel to the frame and make it visible
        this.add(mainPanel);
        this.setVisible(true);
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
                        0, 0, new Color(36, 96, 31),
                        getWidth(), getHeight(), new Color(32, 190, 76)
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
}