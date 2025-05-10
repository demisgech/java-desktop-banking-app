package com.codewithdemis.pages;

import javax.swing.*;
import java.awt.*;

public class SettingsPage extends JPanel {
    public SettingsPage() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Settings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        formPanel.add(new JLabel("Username:"));
        formPanel.add(new JTextField("john_doe"));

        formPanel.add(new JLabel("Email:"));
        formPanel.add(new JTextField("john@example.com"));

        formPanel.add(new JLabel("Notifications:"));
        JCheckBox notifications = new JCheckBox("Enable");
        notifications.setSelected(true);
        formPanel.add(notifications);

        add(formPanel, BorderLayout.CENTER);
    }
}
