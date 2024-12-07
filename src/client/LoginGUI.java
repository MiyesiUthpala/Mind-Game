package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import mindgameinterface.LoginInterface;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.border.AbstractBorder;
import java.awt.image.BufferedImage;

public class LoginGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = -1077856539035386635L;
    private LoginInterface myService = (LoginInterface) Naming.lookup("rmi://localhost:1099/LoginService");
    JButton btnStart = createStyledButton("Start", "<html><b style='font-size:30px; color:white;'>Start</b></html>");
    static String mySessionCookie = "not set";

    public LoginGUI() throws MalformedURLException, NotBoundException, RemoteException {
        super("Mind Challenge Game");

        // Set JFrame to fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the main panel with BorderLayout
        JPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new BorderLayout());

        // Add this inside the LoginGUI constructor after defining the mainPanel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30)); // Adjust vertical space (20px margin)
        titlePanel.setOpaque(false); // Set the title panel to be transparent

        // Create the title label
        JLabel titleLabel = new JLabel("Mind Challenge Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60)); // Set font size and style
        titleLabel.setForeground(Color.BLACK); // Text color

        // Create a panel for the title with background color only behind the name
        JPanel titleNamePanel = new JPanel();
        titleNamePanel.setBackground(new Color(165, 208, 208, 255)); // Set the background color (Golden)
        titleNamePanel.setOpaque(true);
        titleNamePanel.add(titleLabel);

        // Add the titleNamePanel to the titlePanel
        titlePanel.add(titleNamePanel);

        // Add the titlePanel to the top of the mainPanel
        mainPanel.add(titlePanel, BorderLayout.NORTH);


        // Centering the button using GridBagLayout
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); // Make the button panel transparent

        // Add the button with constraints to center it
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        buttonPanel.add(btnStart, gbc);

        // Position the button panel in the center of the main panel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        getContentPane().add(mainPanel);

        // Add ActionListener for the button
        btnStart.addActionListener(this);

        setVisible(true);
    }

    private static JButton createStyledButton(String text, String htmlText) {
        JButton button = new JButton(htmlText);
        button.setFocusPainted(false);
        button.setBackground(new Color(23, 110, 45));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 40));
        button.setBorder(new RoundedBorder(20)); // Curve the button
        button.setPreferredSize(new Dimension(300, 80)); // Set the size of the button

        // Add shadow effect to the button
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(57, 171, 90));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(23, 110, 45));
            }
        });

        // Paint shadow effect
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw shadow
                g2.setColor(Color.GRAY);
                g2.fillRoundRect(5, 5, button.getWidth() - 5, button.getHeight() - 5, 20, 20);

                // Draw button background
                g2.setColor(button.getBackground());
                g2.fillRoundRect(0, 0, button.getWidth() - 5, button.getHeight() - 5, 20, 20);

                // Draw button label
                super.paint(g2, c);
                g2.dispose();
            }
        });

        return button;
    }

    // Custom border for rounded corners
    static class RoundedBorder extends AbstractBorder {
        private final int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(c.getBackground());
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    //https://stackoverflow.com/questions/1466240/how-to-set-an-image-as-a-background-for-frame-in-swing-gui-of-java
    // Custom JPanel to paint the background image
    class BackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;
        public BackgroundPanel() {
            try {
                // Load the background image from file path
                backgroundImage = ImageIO.read(getClass().getResource("../img/background/home.jpg"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Draw the background image, scaling it to fit the panel size
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // Action listener for the Start button
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnStart)) {
            // Open the Login Input window with fields for username and password
            LoginInputWindow loginInputWindow = new LoginInputWindow();
            loginInputWindow.setVisible(true);
            this.dispose(); // Close the current page
        }
    }

    public static void main(String[] args) throws Exception {
        new LoginGUI();
    }
}
