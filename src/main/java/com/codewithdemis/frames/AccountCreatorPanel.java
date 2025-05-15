package com.codewithdemis.frames;

import com.codewithdemis.db.Database;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

public class AccountCreatorPanel extends JPanel {
    private JTextField accountNumberField, balanceField;
    private JComboBox<String> userDropdown, statusDropdown;
    private JButton createButton;

    public AccountCreatorPanel() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(30, 30, 30, 30)); // padding around the panel

        // Title label
        JLabel title = new JLabel("Create New Account");
        title.setFont(new Font("OpenSans", Font.BOLD, 24));
        title.setForeground(new Color(189, 147, 249));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        // Reset for form fields
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.LINE_END;

        // User dropdown label
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Select User:"), gbc);

        // User dropdown field
        userDropdown = new JComboBox<>();
        loadUsers();
        styleComboBox(userDropdown);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        add(userDropdown, gbc);

        // Account number label
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Account Number:"), gbc);

        // Account number field
        accountNumberField = new JTextField(20);
        styleTextField(accountNumberField);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        add(accountNumberField, gbc);

        // Balance label
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Initial Balance:"), gbc);

        // Balance field
        balanceField = new JTextField(20);
        styleTextField(balanceField);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        add(balanceField, gbc);

        // Status label
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Account Status:"), gbc);

        // Status dropdown
        statusDropdown = new JComboBox<>();
        loadStatuses();
        styleComboBox(statusDropdown);
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 1;
        add(statusDropdown, gbc);

        // Create button
        createButton = new JButton("Create Account");
        styleButton(createButton);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        add(createButton, gbc);

        createButton.addActionListener(e -> createAccount());
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
        button.setBackground(new Color(123, 104, 238)); // purple-ish
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

    // --- Existing methods: loadUsers(), loadStatuses(), createAccount() ---
    // (unchanged; copy your original methods here)

    private void loadUsers() {
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, first_name, last_name FROM users");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("first_name") + " " + rs.getString("last_name");
                userDropdown.addItem(id + " - " + fullName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load users", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadStatuses() {
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, status_name FROM account_status");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("status_name");
                statusDropdown.addItem(id + " - " + name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load statuses", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createAccount() {
        String accountNumber = accountNumberField.getText().trim();
        String balanceText = balanceField.getText().trim();
        String selectedUser = (String) userDropdown.getSelectedItem();
        String selectedStatus = (String) statusDropdown.getSelectedItem();

        if (accountNumber.isEmpty() || balanceText.isEmpty() || selectedUser == null || selectedStatus == null) {
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double balance = Double.parseDouble(balanceText);
            int userId = Integer.parseInt(selectedUser.split(" - ")[0]);
            int statusId = Integer.parseInt(selectedStatus.split(" - ")[0]);

            try (Connection conn = Database.getInstance().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO accounts (account_number, user_id, balance, status_id) VALUES (?, ?, ?, ?)")) {

                stmt.setString(1, accountNumber);
                stmt.setInt(2, userId);
                stmt.setDouble(3, balance);
                stmt.setInt(4, statusId);

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid balance input", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
