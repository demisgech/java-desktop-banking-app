package com.codewithdemis.pages;

import javax.swing.*;
import java.awt.*;

public class DashboardPage extends JPanel {
    public DashboardPage() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 0));

        add(title, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        statsPanel.setBackground(Color.WHITE);

        statsPanel.add(createCard("Total Users", "1,250"));
        statsPanel.add(createCard("Revenue", "$13,500"));
        statsPanel.add(createCard("Transactions", "3,204"));

        add(statsPanel, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(240, 240, 240));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setPreferredSize(new Dimension(150, 100));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
}
