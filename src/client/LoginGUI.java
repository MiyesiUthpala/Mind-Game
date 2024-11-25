package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import mindgameinterface.LoginInterface;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class LoginGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = -1077856539035386635L;
    private LoginInterface myService = (LoginInterface) Naming.lookup("rmi://localhost:1099/LoginService");
    JButton btnLogin = createStyledButton("Login", "<html><b style='font-size:14px; color:white;'>Login</b></html>");
    JButton btnLogout = createStyledButton("Logout", "<html><b style='font-size:14px; color:white;'>Logout</b></html>");
    static String mySessionCookie = "not set";

    public LoginGUI() throws MalformedURLException, NotBoundException, RemoteException {
        super("Login Page");

        // Set JFrame to fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the main panel with BorderLayout
        JPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new BorderLayout());

        // Button panel setup with vertical BoxLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false); // Make button panel transparent

        // Add vertical padding for centering the button panel
        buttonPanel.add(Box.createVerticalGlue());

        // Set button sizes and alignment within button panel
        Dimension buttonSize = new Dimension(250, 50);
        btnLogin.setMaximumSize(buttonSize);
        btnLogout.setMaximumSize(buttonSize);

        // Add buttons with spacing to button panel
        buttonPanel.add(btnLogin);
        btnLogin.addActionListener(this);
        buttonPanel.add(Box.createVerticalStrut(15)); // Spacing between buttons
        buttonPanel.add(btnLogout);
        btnLogout.addActionListener(this);

        buttonPanel.add(Box.createVerticalGlue()); // Adds space at the bottom to keep buttons centered

        // Position button panel on the left side of main panel
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        getContentPane().add(mainPanel);

        setVisible(true);
    }

    private static JButton createStyledButton(String text, String htmlText) {
        JButton button = new JButton(htmlText);
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    // Custom JPanel to paint the background image
    class BackgroundPanel extends JPanel {
        private BufferedImage backgroundImage;

        public BackgroundPanel() {
            try {
                // Load the background image from file path
                backgroundImage = ImageIO.read(new File("C:/Users/miyes/OneDrive/Documents/Mind Game/home.jpg"));
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

    // Action listener for the buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnLogin)) {
            // Open the Login Input window with fields for username and password
            LoginInputWindow loginInputWindow = new LoginInputWindow();
            loginInputWindow.setVisible(true);
            this.dispose(); // Close the login page
        } else if (e.getSource().equals(btnLogout)) {
            try {
                String result = myService.logout(mySessionCookie);
                System.out.println("Result: " + result);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new LoginGUI();
    }

    // Updated LoginInputWindow class
    class LoginInputWindow extends JFrame implements ActionListener {
        private JTextField usernameField;
        private JPasswordField passwordField;
        private JButton startButton;

        public LoginInputWindow() {
            setTitle("Enter Username and Password");
            setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Set the background image
            ImageIcon originalIcon = new ImageIcon("C:/Users/miyes/OneDrive/Documents/Mind Game/background.jpg");
            Image scaledImage = originalIcon.getImage().getScaledInstance(
                    Toolkit.getDefaultToolkit().getScreenSize().width,
                    Toolkit.getDefaultToolkit().getScreenSize().height,
                    Image.SCALE_SMOOTH
            );
            JLabel background = new JLabel(new ImageIcon(scaledImage));
            background.setLayout(new GridBagLayout()); // Use layout to center components

            // Main container panel for centering
            JPanel containerPanel = new JPanel(new GridBagLayout());
            containerPanel.setOpaque(false); // Make it transparent to show the background

            // Create a panel to simulate the shadow effect
            JPanel shadowBox = new JPanel();
            shadowBox.setLayout(new BorderLayout());
            shadowBox.setBackground(new Color(200, 200, 200)); // Light gray for shadow effect
            shadowBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Shadow padding

            // Configure the main formBox
            JPanel formBox = new JPanel();
            formBox.setLayout(new BoxLayout(formBox, BoxLayout.Y_AXIS));
            formBox.setBackground(new Color(255, 255, 255, 150)); // Semi-transparent white
            formBox.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Padding around the box

            // Add formBox to shadowBox
            shadowBox.add(formBox, BorderLayout.CENTER);
            formBox.setPreferredSize(new Dimension(800, 375));
            formBox.setMaximumSize(new Dimension(800, 375));
            formBox.setMinimumSize(new Dimension(800, 375));

            // Add username and password fields
            usernameField = new JTextField(40);
            usernameField.setFont(new Font("Arial", Font.PLAIN, 20));

            passwordField = new JPasswordField(40);
            passwordField.setFont(new Font("Arial", Font.PLAIN, 20));

            Dimension fieldSize = new Dimension(300, 100);
            usernameField.setPreferredSize(fieldSize);
            passwordField.setPreferredSize(fieldSize);

            formBox.add(createLabel("<html><b style=\"font-size: 20px;\">User Name:</b></html>"));
            formBox.add(Box.createVerticalStrut(20));
            formBox.add(usernameField);
            formBox.add(Box.createVerticalStrut(20));
            formBox.add(createLabel("<html><b style=\"font-size: 20px;\">Password:</b></html>"));
            formBox.add(Box.createVerticalStrut(20));
            formBox.add(passwordField);
            formBox.add(Box.createVerticalStrut(20));

            startButton = createStyledButton("Start", "<html><b style='font-size:16px; color:white;'>Start</b></html>");
            startButton.setPreferredSize(new Dimension(150, 50)); // Width: 150px, Height: 50px
            startButton.setMaximumSize(new Dimension(150, 50));   // Optional: limit max size
            startButton.setMinimumSize(new Dimension(150, 50));  // Optional: limit min size
            startButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Ensure the button aligns in the center
            startButton.addActionListener(this);
            formBox.add(startButton);

            containerPanel.add(formBox);
            setContentPane(background);
            add(containerPanel);
        }

        private JLabel createLabel(String text) {
            JLabel label = new JLabel(text);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            return label;
        }

        private JButton createStyledButton(String text, String htmlText) {
            JButton button = new JButton(htmlText);
            button.setFocusPainted(false);
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            return button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(startButton)) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Username and Password cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    String result = myService.login(username, password);

                    if (result.startsWith("error#")) {
                        JOptionPane.showMessageDialog(this, result.substring(6), "Login Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        mySessionCookie = result;
                        new HomePage(); // Navigate to HomePage
                        this.dispose();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error occurred during login.", "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }
    }
}
