package com.codewithdemis.pages;

import javax.swing.*;
import java.awt.*;

public class TransactionsPage extends JPanel {
    public TransactionsPage() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("Transactions");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 0));
        add(title, BorderLayout.NORTH);

        String[] columns = {"Date", "Type", "Amount"};
        Object[][] data = {
                {"2025-05-10", "Deposit", "$500"},
                {"2025-05-09", "Withdrawal", "$200"},
                {"2025-05-08", "Transfer", "$750"}
        };

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(scrollPane, BorderLayout.CENTER);
    }
}
