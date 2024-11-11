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
    JButton btnHello = createStyledButton("Hello", "<html><b style='font-size:14px; color:white;'>Hello</b></html>");
    JButton btnLogin = createStyledButton("Login", "<html><b style='font-size:14px; color:white;'>Login</b></html>");
    JButton btnSecret = createStyledButton("Secret", "<html><b style='font-size:14px; color:white;'>Secret</b></html>");
    JButton btnLogout = createStyledButton("Logout", "<html><b style='font-size:14px; color:white;'>Logout</b></html>");
    String mySessionCookie = "not set";

    public LoginGUI() throws MalformedURLException, NotBoundException, RemoteException {
        JFrame loginPage = new JFrame("Login Page");

        // Set new page size to desktop size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        loginPage.setSize(screenSize.width, screenSize.height);
        loginPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
        btnHello.setMaximumSize(buttonSize);
        btnLogin.setMaximumSize(buttonSize);
        btnSecret.setMaximumSize(buttonSize);
        btnLogout.setMaximumSize(buttonSize);

        // Add buttons with spacing to button panel
        buttonPanel.add(btnHello);
        btnHello.addActionListener(this);
        buttonPanel.add(Box.createVerticalStrut(15)); // Spacing between buttons
        buttonPanel.add(btnLogin);
        btnLogin.addActionListener(this);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(btnSecret);
        btnSecret.addActionListener(this);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(btnLogout);
        btnLogout.addActionListener(this);

        buttonPanel.add(Box.createVerticalGlue()); // Adds space at the bottom to keep buttons centered

        // Position button panel on the left side of main panel
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        getContentPane().add(mainPanel);

        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        if (e.getSource().equals(btnHello)) {
            try {
                String result = myService.sayHello();
                System.out.println("Result: " + result);
            } catch (RemoteException ex) {
                System.out.println("A problem occurred: " + ex.toString());
                ex.printStackTrace();
            }
        } else if (e.getSource().equals(btnLogin)) {
            String str = JOptionPane.showInputDialog("Please enter the password.");
            try {
                String result = myService.login(str);
                if (result.equals("wrong")) {
                    JOptionPane.showMessageDialog(this, "Wrong Password. Try again!", "Login Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    mySessionCookie = result;
                    new HomePage(); // Open the new page
                    this.dispose(); // Close the login page
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource().equals(btnSecret)) {
            try {
                String result = myService.getSecretMessage(mySessionCookie);
                System.out.println("Result: " + result);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
        new LoginGUI().setVisible(true);
    }
}
