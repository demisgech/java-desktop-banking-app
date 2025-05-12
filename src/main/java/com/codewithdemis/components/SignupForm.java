package com.codewithdemis.components;

import com.codewithdemis.models.User;
import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;

public class SignupForm extends JPanel {
    private Consumer<User> onSignupComplete;

    private BankLabeledInputField firstNameField;
    private BankLabeledInputField lastNameField;
    private BankLabeledInputField ageField;
    private BankLabeledInputField emailField;
    private BankLabeledInputField phoneField;
    private BankLabeledInputField usernameField;
    private BankPasswordLabeledInputField passwordField;
    private BankPasswordLabeledInputField confirmPasswordField;

    private JComboBox<String> genderComboBox;

    public SignupForm() {
        setLayout(new GridBagLayout());
        setBackground(new Color(68, 71, 90)); // Dracula background

        JPanel container = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(248, 248, 255));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        container.setOpaque(false);
        container.setBorder(new RoundedLineBorder(new Color(248, 248, 255), 2, 24));
        container.setPreferredSize(new Dimension(520, 620));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 16, 10, 16);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Row 1: First Name and Last Name
        firstNameField = new BankLabeledInputField("First Name", "John");
        lastNameField = new BankLabeledInputField("Last Name", "Doe");
        c.gridx = 0; c.gridy = row;
        formPanel.add(firstNameField, c);
        c.gridx = 1;
        formPanel.add(lastNameField, c);

        // Row 2: Age and Gender (side by side)
        ageField = new BankLabeledInputField("Age", "30");

        // Gender ComboBox
        genderComboBox = new JComboBox<>(new String[] {"Select Gender", "Male", "Female", "Other"});
        genderComboBox.setBackground(new Color(248, 248, 255));
        genderComboBox.setForeground(Color.BLACK);
        genderComboBox.setFont(new Font("OpenSans",Font.BOLD,16));
        genderComboBox.setBorder(BorderFactory.createCompoundBorder(
                new RoundedLineBorder(Color.BLUE, 1, 12),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        c.gridx = 0; row++;
        c.gridy = row;
        formPanel.add(ageField, c);

        GridBagConstraints genderConstraints = (GridBagConstraints) c.clone();
        genderConstraints.gridx = 1;
        genderConstraints.gridy = row;
        genderConstraints.insets = new Insets(30, 10, 10, 10); // Push down by 15px more than age
        formPanel.add(genderComboBox, genderConstraints);

        // Row 3: Email and Phone
        emailField = new BankLabeledInputField("Email", "john.doe@example.com");
        phoneField = new BankLabeledInputField("Phone", "+1234567890");
        row++;
        c.gridx = 0; c.gridy = row;
        formPanel.add(emailField, c);
        c.gridx = 1;
        formPanel.add(phoneField, c);

        // Row 4: Username
        usernameField = new BankLabeledInputField("Username", "johndoe");
        row++;
        c.gridx = 0; c.gridy = row; c.gridwidth = 2;
        formPanel.add(usernameField, c);
        c.gridwidth = 1;

        // Row 5: Password and Confirm Password
        passwordField = new BankPasswordLabeledInputField("Password", "Create password...");
        confirmPasswordField = new BankPasswordLabeledInputField("Confirm Password", "Re-enter password...");
        row++;
        c.gridy = row;
        c.gridx = 0;
        formPanel.add(passwordField, c);
        c.gridx = 1;
        formPanel.add(confirmPasswordField, c);

        gbc.gridy = 0;
        container.add(formPanel, gbc);

        BankButton signupButton = new BankButton("Sign Up", "primary");
        gbc.gridy++;
        container.add(signupButton, gbc);

        // Add to main panel
        add(container, new GridBagConstraints());


        signupButton.addActionListener(e -> {
            String firstName = firstNameField.getTextField().getText();
            String lastName = lastNameField.getTextField().getText();
            String age = ageField.getTextField().getText();
            String email = emailField.getTextField().getText();
            String phone = phoneField.getTextField().getText();
            String username = usernameField.getTextField().getText();
            String password = new String(passwordField.getPasswordField().getPassword());
            String confirmPassword = new String(confirmPasswordField.getPasswordField().getPassword());

            String gender = (String) genderComboBox.getSelectedItem();

            User newUser = new User(firstName, lastName,username, email);

            if (onSignupComplete != null)
                onSignupComplete.accept(newUser);

            if (firstName.isEmpty() || lastName.isEmpty() || age.isEmpty()
                    || email.isEmpty() || phone.isEmpty() || username.isEmpty()
                    || password.isEmpty() || confirmPassword.isEmpty() || gender == null || gender.equals("Select Gender")) {
                JOptionPane.showMessageDialog(SignupForm.this,
                        "Please fill out all fields.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(SignupForm.this,
                        "Passwords do not match.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(SignupForm.this,
                        "Signup successful!\nWelcome, " + firstName + " (" + gender + ")!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                // Save user account logic goes here
            }
        });
    }

    public void setOnSignupComplete(Consumer<User> onSignupComplete) {
        this.onSignupComplete = onSignupComplete;
    }
}
