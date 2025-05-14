package com.codewithdemis.frames;

import java.awt.*;
import javax.swing.*;
 public  class DashboardPanel extends JPanel {
    public DashboardPanel() {
        setLayout(new BorderLayout());

        // Stats panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 3));
        statsPanel.add(createStatCard("Total Users", "1,024"));
        statsPanel.add(createStatCard("Total Accounts", "2,548"));
        statsPanel.add(createStatCard("Total Transactions", "15,872"));

        // Recent transactions
        String[] columns = {"ID", "Type", "Amount", "Account", "Date"};
        Object[][] data = {
                {"1", "DEPOSIT", "$500.00", "ACC1001", "2023-05-15"},
                {"2", "WITHDRAW", "$200.00", "ACC1002", "2023-05-15"}
        };
        JTable recentTransactions = new JTable(data, columns);

        add(statsPanel, BorderLayout.NORTH);
        add(new JScrollPane(recentTransactions), BorderLayout.CENTER);
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createTitledBorder(title));
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }
}