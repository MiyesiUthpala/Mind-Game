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
        ImageIcon originalIcon = new ImageIcon(HomePage.imagePath+"/background.jpg");
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
        formBox.setPreferredSize(new Dimension(800, 420));

        // Add username and password fields
        usernameField = new JTextField(40);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 20));

        passwordField = new JPasswordField(40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));

        formBox.add(createLabelCenter("<html><b style=\"font-size: 22px; display: block; text-align: center;\">Register</b></html>\n"));
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(createLabel("<html><b style=\"font-size: 18px;\">User Name:</b></html>"));
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(usernameField);
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(createLabel("<html><b style=\"font-size: 18px;\">Password:</b></html>"));
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(passwordField);
        formBox.add(Box.createVerticalStrut(20));

        registerButton = createStyledButton("Register", "<html><b style='font-size:16px; color:white;'>Register</b></html>");
        // Adjust button size (shorter height)
        registerButton.setPreferredSize(new Dimension(150, 40)); // Width: 150px, Height: 40px
        registerButton.setMaximumSize(new Dimension(150, 40));   // Optional: limit max size
        registerButton.setMinimumSize(new Dimension(150, 40));  // Optional: limit min size
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.addActionListener(this);

        loginButton = createStyledButton("Login", "<html><b style='font-size:16px; color:white;'>Login</b></html>");
        // Adjust button size (shorter height)
        loginButton.setPreferredSize(new Dimension(150, 40)); // Width: 150px, Height: 40px
        loginButton.setMaximumSize(new Dimension(150, 40));   // Optional: limit max size
        loginButton.setMinimumSize(new Dimension(150, 40));  // Optional: limit min size
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(this);

        formBox.add(registerButton);
        formBox.add(Box.createVerticalStrut(20));
        formBox.add(loginButton);

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

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and Password cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                String result = myService.register(username, password);

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
