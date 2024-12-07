package client;

import mindgameinterface.RegisterInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RegisterInputWindow extends JFrame implements ActionListener{
    private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private JTextField last_nameField;

    private JTextField first_nameField;

    private JTextField emailField;
    private JButton registerButton;
    private JButton loginButton;
    private RegisterInterface myService;

    public RegisterInputWindow(){
        setTitle("Register");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize the RMI service
        try {
            myService = (RegisterInterface) Naming.lookup("rmi://localhost:1099/RegisterService");
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to service.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        // Set the background image
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("../img/background/register.jpg"));
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
        formBox.setBackground(new Color(255, 255, 255, 239)); // Semi-transparent white
        formBox.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Padding around the box

        // Add formBox to shadowBox
        shadowBox.add(formBox, BorderLayout.CENTER);
        formBox.setPreferredSize(new Dimension(900, 650));

        first_nameField = new JTextField(40);
        first_nameField.setFont(new Font("Arial", Font.PLAIN, 20));

        last_nameField = new JTextField(40);
        last_nameField.setFont(new Font("Arial", Font.PLAIN, 20));

        emailField = new JTextField(40);
        emailField.setFont(new Font("Arial", Font.PLAIN, 20));

        // Add username and password fields
        usernameField = new JTextField(40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20));

        passwordField = new JPasswordField(40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));

        // Add username and password fields
        formBox.add(createLabelCenter("<html><b style=\"font-size: 22px; display: block; text-align: center;\">Register</b></html>"));
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(createLabel("<html><b style=\"font-size: 18px;\">First Name:</b></html>"));
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(first_nameField);
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(createLabel("<html><b style=\"font-size: 18px;\">Last Name:</b></html>"));
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(last_nameField);
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(createLabel("<html><b style=\"font-size: 18px;\">Email Address:</b></html>"));
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(emailField);
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

        // Register Button
        registerButton = createStyledButton("Register", "<html><b style='font-size:16px; color:white;'>Register</b></html>");
        registerButton.setPreferredSize(new Dimension(150, 40));
        registerButton.addActionListener(this);
        buttonPanel.add(registerButton); // Add to panel

        // Login Button
        loginButton = createStyledButton("Login", "<html><b style='font-size:16px; color:white;'>Login</b></html>");
        loginButton.setPreferredSize(new Dimension(150, 40));
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton); // Add to panel

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
        if (e.getSource().equals(registerButton)) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String first_name = (first_nameField.getText().trim());
            String last_name = (last_nameField.getText());
            String email = (emailField.getText());


            if (username.isEmpty() || password.isEmpty() || first_name.isEmpty() || last_name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                String result = myService.register( username,  password, first_name, last_name, email);

                if (result.startsWith("error#")) {
                    JOptionPane.showMessageDialog(this, result.substring(6), "Register Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    LoginInputWindow loginInputWindow = new LoginInputWindow();
                    loginInputWindow.setVisible(true);
                    this.dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error occurred during login.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else if (e.getSource().equals(loginButton))
        {
            LoginInputWindow loginInputWindow = new LoginInputWindow();
            loginInputWindow.setVisible(true);
            this.dispose(); // Close the current page
        }
    }
}
