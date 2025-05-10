package com.codewithdemis.pages;

import javax.swing.*;
import java.awt.*;

public class AccountPage extends JPanel {
    public AccountPage() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setOpaque(false);

        JLabel title = new JLabel("Account Info");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 0));

        add(title, BorderLayout.NORTH);

        JTextArea profileDetails = new JTextArea(
                "Name: John Doe\n" +
                        "Email: john.doe@example.com\n" +
                        "Role: Administrator"
        );
        profileDetails.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        profileDetails.setEditable(false);
        profileDetails.setBackground(new Color(240, 240, 240));
        profileDetails.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(profileDetails, BorderLayout.CENTER);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set up the graphics to make rounded corners
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded rectangle with the background color
        g2d.setColor(new Color(40, 42, 54));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);  // 30 is the corner radius

        // Set up for menuPanel's rounded corners
        g2d.setColor(new Color(68, 71, 90));  // Menu background color
        g2d.fillRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 20, 20);  // Menu panel's rounded corners
    }

}
