package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mindgameinterface.LoginInterface;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LoginInputWindow extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private LoginInterface myService;

    public LoginInputWindow() {
        setTitle("Enter Username and Password");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize the RMI service
        try {
            myService = (LoginInterface) Naming.lookup("rmi://localhost:1099/LoginService");
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to service.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        // Set the background image
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("../img/background/background.jpg"));
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
        formBox.setBackground(new Color(255, 255, 255, 208)); // Semi-transparent white
        formBox.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Padding around the box

        // Add formBox to shadowBox
        shadowBox.add(formBox, BorderLayout.CENTER);
        formBox.setPreferredSize(new Dimension(800, 420));

        // Add username and password fields
        usernameField = new JTextField(40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20));

        passwordField = new JPasswordField(40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));

        // Add username and password fields
        formBox.add(createLabelCenter("<html><b style=\"font-size: 22px; display: block; text-align: center;\">Login</b></html>"));
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(createLabel("<html><b style=\"font-size: 18px;\">User Name:</b></html>"));
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(usernameField);
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(createLabel("<html><b style=\"font-size: 18px;\">Password:</b></html>"));
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(passwordField);
        formBox.add(Box.createVerticalStrut(20));

        // Create a new JPanel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0)); // 10px gap between buttons
        buttonPanel.setOpaque(false); // Make transparent to blend with background


        // Login Button
        loginButton = createStyledButton("Login", "<html><b style='font-size:16px; color:white;'>Login</b></html>");
        loginButton.setPreferredSize(new Dimension(150, 40));
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton); // Add to panel

        // Register Button
        registerButton = createStyledButton("Register", "<html><b style='font-size:16px; color:white;'>Register</b></html>");
        registerButton.setPreferredSize(new Dimension(150, 40));
        registerButton.addActionListener(this);
        buttonPanel.add(registerButton); // Add to panel

        // Add button panel to the formBox
        formBox.add(Box.createVerticalStrut(20)); // Add spacing above buttons
        formBox.add(buttonPanel); // Add the button panel


        containerPanel.add(formBox);
        setContentPane(background);
        add(containerPanel);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JLabel createLabelCenter(String text) {
        JLabel label = new JLabel(text, JLabel.CENTER); // Center-align the text
        label.setAlignmentX(Component.CENTER_ALIGNMENT); // Align the label itself to the center
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
        if (e.getSource().equals(loginButton)) {
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
                    LoginGUI.mySessionCookie = result;
                    new HomePage(); // Navigate to HomePage
                    this.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error occurred during login.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else if (e.getSource().equals(registerButton))
        {
            RegisterInputWindow registerInputWindow = new RegisterInputWindow();
            registerInputWindow.setVisible(true);
            this.dispose(); // Close the current page
        }
    }
}
