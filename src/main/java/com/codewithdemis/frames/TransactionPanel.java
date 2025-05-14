package com.codewithdemis.frames;

import java.awt.*;
import javax.swing.*;

 public class TransactionPanel extends JPanel {
    public TransactionPanel() {
        setLayout(new BorderLayout());

        // Filter panel
        JPanel filterPanel = new JPanel();
        JComboBox<String> typeFilter = new JComboBox<>(new String[]{"ALL", "DEPOSIT", "WITHDRAW", "TRANSFER"});
        JButton filterButton = new JButton("Filter");
        JButton exportButton = new JButton("Export to CSV");
        filterPanel.add(new JLabel("Type:"));
        filterPanel.add(typeFilter);
        filterPanel.add(filterButton);
        filterPanel.add(exportButton);

        // Transactions table
        String[] columns = {"ID", "Type", "Amount", "From Account", "To Account", "Date"};
        Object[][] data = {
                {"1", "DEPOSIT", "$500.00", "-", "ACC1001", "2023-05-15"},
                {"2", "TRANSFER", "$200.00", "ACC1001", "ACC1002", "2023-05-15"}
        };
        JTable transactionsTable = new JTable(data, columns);

        add(filterPanel, BorderLayout.NORTH);
        add(new JScrollPane(transactionsTable), BorderLayout.CENTER);
    }
}