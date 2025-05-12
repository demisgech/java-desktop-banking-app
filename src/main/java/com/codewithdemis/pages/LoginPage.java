
package com.codewithdemis.pages;

import com.codewithdemis.components.BankButton;
import com.codewithdemis.components.BankLabeledInputField;
import com.codewithdemis.components.BankPasswordLabeledInputField;
import com.codewithdemis.components.EmailLabeledInputField;
import com.codewithdemis.components.RoundedLineBorder;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class LoginPage extends JPanel {

    private BankPasswordLabeledInputField passwordField;
    private BankLabeledInputField usernameField;
    private JLabel errorLabel;

    public LoginPage() {
//        setLayout(new GridBagLayout());
        setLayout(new BorderLayout());
        setBackground(new Color(68, 71, 90));

        // Center container panel for the login card
        JPanel formPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fill with rounded rectangle background
                g2.setColor(new Color(248, 248, 255)); // Soft white
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);

                g2.dispose();
                super.paintComponent(g);
            }
        };
        formPanel.setOpaque(false); // Let paintComponent do the background

        formPanel.setBackground(new Color(100, 100, 255));

        formPanel.setBorder(new RoundedLineBorder(new Color(248, 248, 255), 2, 24));
        formPanel.setPreferredSize(new Dimension(380, 300)); // Fixed size card


        // GridBagLayout constraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;

        GridBagConstraints inner = new GridBagConstraints();
        inner.insets = new Insets(10, 20, 10, 20);
        inner.fill = GridBagConstraints.HORIZONTAL;
        inner.anchor = GridBagConstraints.CENTER;

        // Username Field
        usernameField = new BankLabeledInputField("Username", "Username...");
        var emailField = new EmailLabeledInputField("Email","Email...");
        inner.gridx = 0;
        inner.gridy = 0;
//        container.add(usernameField, inner);
        formPanel.add(emailField, inner);

        // Password Field
        passwordField = new BankPasswordLabeledInputField("Password", "Password...");
        inner.gridy = 1;
        formPanel.add(passwordField, inner);

        // Login Button
        BankButton loginButton = new BankButton("Login", "primary");
        inner.gridy = 2;
        formPanel.add(loginButton, inner);

        // Error Label
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inner.gridy = 3;
        formPanel.add(errorLabel, inner);

        // === Centering wrapper ===
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.add(formPanel, new GridBagConstraints());

        // === Scroll Pane ===
        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smooth scrolling

        add(scrollPane, BorderLayout.CENTER);


        // Action Listener for Login Button
        loginButton.addActionListener(e -> {
            String username = usernameField.getTextField().getText();  // Use getText to retrieve the text
            char[] password = passwordField.getPasswordField().getPassword();

            if (authenticate(username, String.valueOf(password))) {
                // Proceed to next screen (Dashboard)
                JOptionPane.showMessageDialog(null, "Login successful!");
                // Switch to the Dashboard or main frame here
            } else {
                String errorMsg = "Invalid username or password.";
                errorLabel.setText(errorMsg);
                JOptionPane.showMessageDialog(LoginPage.this, errorMsg, "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Mock authentication method
    private boolean authenticate(String username, String password) {
        // Replace this with actual authentication logic
        return ((username.equals("user") && password.equals("password")));
    }

    private Border createRoundedBorder(Color color) {
        return new RoundedLineBorder(color, 2, 16);
    }
}
