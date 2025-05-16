package com.codewithdemis.frames;

import com.codewithdemis.dao.TransactionOperation;
import com.codewithdemis.db.Database;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WithdrawDepositTransactionPanel extends JPanel {

    private JComboBox<String> accountDropdown, typeDropdown;
    private JTextField amountField;
    private JTextArea descriptionArea;
    private JButton submitButton;

    public WithdrawDepositTransactionPanel() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Perform Transaction");
        title.setFont(new Font("OpenSans", Font.BOLD, 24));
        title.setForeground(new Color(189, 147, 249));

        // Initialize components
        accountDropdown = new JComboBox<>();
        loadAccounts();
        styleComboBox(accountDropdown);

        typeDropdown = new JComboBox<>(new String[]{"Deposit", "Withdrawal"});
        styleComboBox(typeDropdown);

        amountField = new JTextField(20);
        styleTextField(amountField);

        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        styleTextArea(descriptionArea);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);

        submitButton = new JButton("Submit Transaction");
        styleButton(submitButton);

        // Layout setup using GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();

        // Title label - span two columns, centered, with extra spacing
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 30, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(title, gbc);

        // Reset for form fields
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Account label and dropdown
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        JLabel accountLabel = new JLabel("Select Account:");
        accountLabel.setForeground(Color.white);
        accountLabel.setFont(new Font("OpenSans", Font.BOLD, 20));


        add(accountLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        add(accountDropdown, gbc);

        // Transaction type label and dropdown
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;

        JLabel transactionType = new JLabel("Transaction Type:");
        transactionType.setForeground(Color.white);
        transactionType.setFont(new Font("OpenSans", Font.BOLD, 20));

        add(transactionType, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        add(typeDropdown, gbc);

        // Amount label and field
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.2;
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setForeground(Color.white);
        amountLabel.setFont(new Font("OpenSans", Font.BOLD, 20));

        add(amountLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        add(amountField, gbc);

        // Description label and textarea scroll pane
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.2;
        gbc.anchor = GridBagConstraints.NORTHWEST;  // Align label top-left for multiline area

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setForeground(Color.white);
        descriptionLabel.setFont(new Font("OpenSans", Font.BOLD, 20));

        add(descriptionLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.8;
        gbc.anchor = GridBagConstraints.WEST;
        add(scrollPane, gbc);

        // Submit button - centered, spanning two columns, with extra top spacing
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(30, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(submitButton, gbc);

        // Action listener
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
                throw new NumberFormatException("Amount must be positive.");
            }

            try {
                var operation = new TransactionOperation();
                if(type.equalsIgnoreCase("WITHDRAWAL"))
                    operation.withdraw(accountId, amount, description);
                else if(type.equalsIgnoreCase("DEPOSIT"))
                    operation.deposit(accountId,amount,description);
                JOptionPane.showMessageDialog(this, "Transaction successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                amountField.setText("");
                descriptionArea.setText("");

            } catch (RuntimeException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Transaction failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setBackground(new Color(36, 39, 58));
        field.setForeground(new Color(248, 248, 242));
        field.setCaretColor(new Color(189, 147, 249));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 63, 85)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    private void styleTextArea(JTextArea area) {
        area.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        area.setBackground(new Color(36, 39, 58));
        area.setForeground(new Color(248, 248, 242));
        area.setCaretColor(new Color(189, 147, 249));
        area.setBorder(BorderFactory.createCompoundBorder(
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
