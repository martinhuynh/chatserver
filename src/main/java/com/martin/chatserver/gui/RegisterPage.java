package com.martin.chatserver.gui;

import javax.swing.*;
import java.awt.*;

public class RegisterPage extends Page {

    private JTextField usernameField = new JTextField("");
    private JTextField emailField = new JTextField("");
    private JPasswordField passwordField;
    private JPasswordField verifyField;
    private JButton registerButton = new JButton("Register");
    private JButton backButton = new JButton("Back");
    private JLabel title = new JLabel("Register Account");
    private JLabel usernameTitle = new JLabel("Username");
    private JLabel emailTitle = new JLabel("Email Address");
    private JLabel passwordTitle = new JLabel("Password");
    private JLabel verfiyTitle = new JLabel("Verify Password");

    public RegisterPage(PageManager pageManager) {
        super(pageManager);
        initRegister();
        initBackButton();
    }

    private void initBackButton() {
        backButton.addActionListener((action) -> pageManager.setPage(Pages.LOGIN));
    }

    // private void initRegisterButton() {

    // }

    private void initRegister() {
        GridBagConstraints gc = new GridBagConstraints();

        // Page title
        Font titleFont = title.getFont();
        title.setFont(new Font(titleFont.getName(), Font.PLAIN, 26));
        gc.gridx = 0;
        gc.gridy = 1;
        gc.gridwidth = 2;
        gc.insets = new Insets(0, 0, 10, 0);
        add(title, gc);

        // Username title
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = new Insets(0, 5, 0, 0);
        add(usernameTitle, gc);

        // Username box
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 3;
        gc.gridwidth = 2;
        gc.ipady = 7;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(0, 0, 9, 0);
        add(usernameField, gc);

        // Email box title
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 4;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = new Insets(0, 5, 0, 0);
        add(emailTitle, gc);

        // Email box
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 5;
        gc.gridwidth = 2;
        gc.ipady = 7;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(0, 0, 9, 0);
        add(emailField, gc);

        // Password box title
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 6;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = new Insets(0, 5, 0, 0);
        add(passwordTitle, gc);

        // Password Box
        gc = new GridBagConstraints();
        passwordField = new JPasswordField();
        gc.gridx = 0;
        gc.gridy = 7;
        gc.gridwidth = 2;
        gc.ipady = 7;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(0, 0, 9, 0);
        add(passwordField, gc);

        // Verify Password Box title
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 8;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = new Insets(0, 5, 0, 0);
        add(verfiyTitle, gc);

        // Verify Password Box
        gc = new GridBagConstraints();
        verifyField = new JPasswordField();
        gc.gridx = 0;
        gc.gridy = 9;
        gc.gridwidth = 2;
        gc.ipady = 7;
        gc.insets = new Insets(0, 0, 9, 0);
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(verifyField, gc);

        // Back Button
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 10;
        gc.gridwidth = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(backButton, gc);

        // Register Button
        gc = new GridBagConstraints();
        gc.gridx = 1;
        gc.gridy = 10;
        gc.gridwidth = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(registerButton, gc);
    }

}
