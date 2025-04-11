package com.moviebooking.views;

import com.moviebooking.controllers.UserController;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {
    private static final long serialVersionUID = 1L;
    private MainFrame mainFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;

    public LoginView(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Title
        JLabel titleLabel = new JLabel("Welcome to Movie Booking", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, gbc);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Please log in to continue", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(subtitleLabel, gbc);

        // Add some vertical space
        add(Box.createVerticalStrut(20), gbc);

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(200, 30));
        add(usernameLabel, gbc);
        add(usernameField, gbc);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(200, 30));
        add(passwordLabel, gbc);
        add(passwordField, gbc);

        // Add some vertical space
        add(Box.createVerticalStrut(10), gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> handleLogin());
        add(loginButton, gbc);

        // Message label
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.RED);
        add(messageLabel, gbc);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        UserController userController = new UserController();
        boolean isValidUser = userController.authenticateUser(username, password);
        if (isValidUser) {
            mainFrame.switchToPanel("MovieSelection");
        } else {
            messageLabel.setText("Invalid credentials. Please try again.");
        }
    }
}
