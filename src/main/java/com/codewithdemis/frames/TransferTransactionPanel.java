package com.codewithdemis.frames;

import com.codewithdemis.components.BankButton;
import com.codewithdemis.dao.TransactionOperation;
import com.codewithdemis.db.Database;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class TransferTransactionPanel extends JPanel {
    private JComboBox<String> sourceAccountDropdown;
    private JComboBox<String> destinationAccountDropdown;
    private JComboBox<String> transactionTypeDropdown;
    private JTextField amountField, descriptionField;
    private JButton createButton;

    public TransferTransactionPanel() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel title = new JLabel("Create New Transaction");
        title.setFont(new Font("OpenSans", Font.BOLD, 24));
        title.setForeground(new Color(189, 147, 249));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Source Account

        JLabel sourceAccountLabel = new JLabel("Source Account:");
        sourceAccountLabel.setForeground(Color.white);
        sourceAccountLabel.setFont(new Font("OpenSans", Font.BOLD, 20));

        add(sourceAccountLabel, gbc);
        gbc.gridx = 1;
        sourceAccountDropdown = new JComboBox<>();
        styleComboBox(sourceAccountDropdown);
        loadAccounts(sourceAccountDropdown);
        add(sourceAccountDropdown, gbc);

        // Destination Account
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel destinationAccountLabel = new JLabel("Destination Account:");
        destinationAccountLabel.setForeground(Color.white);
        destinationAccountLabel.setFont(new Font("OpenSans", Font.BOLD, 20));

        add(destinationAccountLabel, gbc);
        gbc.gridx = 1;
        destinationAccountDropdown = new JComboBox<>();
        styleComboBox(destinationAccountDropdown);
        loadAccounts(destinationAccountDropdown);
        add(destinationAccountDropdown, gbc);

        // Transaction Type
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel transactionTypeLabel = new JLabel("Transaction Type:");
        transactionTypeLabel.setForeground(Color.white);
        transactionTypeLabel.setFont(new Font("OpenSans", Font.BOLD, 20));

        add(transactionTypeLabel, gbc);
        gbc.gridx = 1;
        transactionTypeDropdown = new JComboBox<>(new String[]{"TRANSFER"});
        styleComboBox(transactionTypeDropdown);
        add(transactionTypeDropdown, gbc);

        // Amount
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setForeground(Color.white);
        amountLabel.setFont(new Font("OpenSans", Font.BOLD, 20));

        add(amountLabel, gbc);
        gbc.gridx = 1;
        amountField = new JTextField(20);
        styleTextField(amountField);
        add(amountField, gbc);

        // Description
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setForeground(Color.white);
        descriptionLabel.setFont(new Font("OpenSans", Font.BOLD, 20));

        add(descriptionLabel, gbc);
        gbc.gridx = 1;
        descriptionField = new JTextField(20);
        styleTextField(descriptionField);
        add(descriptionField, gbc);

        // Create Button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        createButton = new BankButton("Create Transaction");

        add(createButton, gbc);

        createButton.addActionListener(this::createTransaction);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBackground(new Color(36, 39, 58));
        field.setForeground(new Color(248, 248, 242));
        field.setCaretColor(new Color(189, 147, 249));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 63, 85)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comboBox.setBackground(new Color(36, 39, 58));
        comboBox.setForeground(new Color(248, 248, 242));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(60, 63, 85)));
        comboBox.setFocusable(false);
    }

    private void loadAccounts(JComboBox<String> comboBox) {
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, account_number FROM accounts");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String number = rs.getString("account_number");
                comboBox.addItem(id + " - " + number);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load accounts", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createTransaction(ActionEvent e) {
        String source = (String) sourceAccountDropdown.getSelectedItem();
        String destination = (String) destinationAccountDropdown.getSelectedItem();
        String type = (String) transactionTypeDropdown.getSelectedItem();
        String amountText = amountField.getText().trim();
        String description = descriptionField.getText().trim();

        if (source == null || destination == null || type == null || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (source.equals(destination)) {
            JOptionPane.showMessageDialog(this, "Source and destination accounts must differ", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int sourceId = Integer.parseInt(source.split(" - ")[0]);
            int destinationId = Integer.parseInt(destination.split(" - ")[0]);
            double amount = Double.parseDouble(amountText);

            var operation = new TransactionOperation();
            try {
                operation.transfer(sourceId, destinationId, amount, description);
                JOptionPane.showMessageDialog(this, "Transaction created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, "Database error", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount entered", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        Color top = new Color(20, 22, 34);
        Color bottom = new Color(45, 52, 70);
        GradientPaint gradient = new GradientPaint(0, 0, top, 0, getHeight(), bottom);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }

    public static class DepositWithdrawTransactionPanel extends JPanel {
        private JComboBox<String> accountDropdown, typeDropdown;
        private JTextField amountField;
        private JTextArea descriptionArea;
        private JButton submitButton;

        public DepositWithdrawTransactionPanel() {
            setOpaque(false);
            setLayout(new GridBagLayout());
            setBorder(new EmptyBorder(30, 30, 30, 30));

            JLabel title = new JLabel("Perform Transaction");
            title.setFont(new Font("OpenSans", Font.BOLD, 24));
            title.setForeground(new Color(189, 147, 249));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = 2;
            gbc.insets = new Insets(0, 0, 20, 0);
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(title, gbc);

            gbc.gridwidth = 1;
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.LINE_END;

            // Account dropdown
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(new JLabel("Select Account:"), gbc);
            accountDropdown = new JComboBox<>();
            loadAccounts();
            styleComboBox(accountDropdown);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            add(accountDropdown, gbc);

            // Transaction type dropdown
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.anchor = GridBagConstraints.LINE_END;
            add(new JLabel("Transaction Type:"), gbc);
            typeDropdown = new JComboBox<>(new String[]{"Deposit", "Withdrawal"});
            styleComboBox(typeDropdown);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            add(typeDropdown, gbc);

            // Amount
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.anchor = GridBagConstraints.LINE_END;
            add(new JLabel("Amount:"), gbc);
            amountField = new JTextField(20);
            styleTextField(amountField);
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            add(amountField, gbc);

            // Description
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.anchor = GridBagConstraints.FIRST_LINE_END;
            add(new JLabel("Description:"), gbc);
            descriptionArea = new JTextArea(4, 20);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setBackground(new Color(36, 39, 58));
            descriptionArea.setForeground(new Color(248, 248, 242));
            descriptionArea.setCaretColor(new Color(189, 147, 249));
            descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            descriptionArea.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(60, 63, 85)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            add(new JScrollPane(descriptionArea), gbc);

            // Submit button
            submitButton = new JButton("Submit Transaction");
            styleButton(submitButton);
            gbc.gridwidth = 2;
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(20, 5, 5, 5);
            add(submitButton, gbc);

            submitButton.addActionListener(e -> performTransaction());
        }

        private void loadAccounts() {
            try (Connection conn = Database.getInstance().getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT id, account_number FROM accounts");
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String acc = rs.getString("account_number");
                    accountDropdown.addItem(id + " - " + acc);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to load accounts", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void performTransaction() {
            String selectedAccount = (String) accountDropdown.getSelectedItem();
            String type = (String) typeDropdown.getSelectedItem();
            String amountText = amountField.getText().trim();
            String description = descriptionArea.getText().trim();

            if (selectedAccount == null || type == null || amountText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int accountId = Integer.parseInt(selectedAccount.split(" - ")[0]);
                double amount = Double.parseDouble(amountText);

                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Amount must be positive.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (type.equals("Withdrawal")) {
                    amount = -amount;
                }

                try (Connection conn = Database.getInstance().getConnection()) {
                    conn.setAutoCommit(false);

                    // Check for sufficient funds if Withdrawal
                    if (type.equals("Withdrawal")) {
                        try (PreparedStatement checkStmt = conn.prepareStatement("SELECT balance FROM accounts WHERE id = ?")) {
                            checkStmt.setInt(1, accountId);
                            ResultSet rs = checkStmt.executeQuery();
                            if (rs.next()) {
                                double currentBalance = rs.getDouble("balance");
                                if (currentBalance + amount < 0) {
                                    JOptionPane.showMessageDialog(this, "Insufficient funds for withdrawal.", "Error", JOptionPane.ERROR_MESSAGE);
                                    conn.rollback();
                                    return;
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
                                conn.rollback();
                                return;
                            }
                        }
                    }

                    // Update account balance
                    try (PreparedStatement updateStmt = conn.prepareStatement(
                            "UPDATE accounts SET balance = balance + ? WHERE id = ?")) {
                        updateStmt.setDouble(1, amount);
                        updateStmt.setInt(2, accountId);
                        int rowsUpdated = updateStmt.executeUpdate();
                        if (rowsUpdated == 0) {
                            JOptionPane.showMessageDialog(this, "Account update failed.", "Error", JOptionPane.ERROR_MESSAGE);
                            conn.rollback();
                            return;
                        }
                    }

                    // Insert transaction record
                    try (PreparedStatement insertStmt = conn.prepareStatement(
                            "INSERT INTO transactions (account_id, amount, description) VALUES (?, ?, ?)")) {
                        insertStmt.setInt(1, accountId);
                        insertStmt.setDouble(2, amount);
                        insertStmt.setString(3, description);
                        insertStmt.executeUpdate();
                    }

                    conn.commit();
                    JOptionPane.showMessageDialog(this, "Transaction successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    amountField.setText("");
                    descriptionArea.setText("");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Transaction failed due to database error.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void styleTextField(JTextField field) {
            field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            field.setBackground(new Color(36, 39, 58));
            field.setForeground(new Color(248, 248, 242));
            field.setCaretColor(new Color(189, 147, 249));
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(60, 63, 85)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
        }

        private void styleComboBox(JComboBox<String> comboBox) {
            comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            comboBox.setBackground(new Color(36, 39, 58));
            comboBox.setForeground(new Color(248, 248, 242));
            comboBox.setBorder(BorderFactory.createLineBorder(new Color(60, 63, 85)));
            comboBox.setFocusable(false);
        }

        private void styleButton(JButton button) {
            button.setFont(new Font("OpenSans", Font.BOLD, 16));
            button.setBackground(new Color(123, 104, 238));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(104, 91, 224));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBackground(new Color(123, 104, 238));
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            Color top = new Color(20, 22, 34);
            Color bottom = new Color(45, 52, 70);
            GradientPaint gradient = new GradientPaint(0, 0, top, 0, getHeight(), bottom);
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
        }
    }
}
