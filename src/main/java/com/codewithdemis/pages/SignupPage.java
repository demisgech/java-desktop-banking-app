package com.codewithdemis.pages;

import com.codewithdemis.components.SignupForm;
import com.codewithdemis.models.User;
import java.util.function.Consumer;
import javax.swing.*;
import java.awt.*;

public class SignupPage extends JPanel {
    private SignupForm formPanel;
    public SignupPage() {
        setLayout(new BorderLayout());
        setBackground(new Color(68, 71, 90));

        formPanel = new SignupForm();

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smooth scroll
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Add scrollPane to main layout
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setOnSignupComplete(Consumer<User> callback) {
        this.formPanel.setOnSignupComplete(callback);
    }
}