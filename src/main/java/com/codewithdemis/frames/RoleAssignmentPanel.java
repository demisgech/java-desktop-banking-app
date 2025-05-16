package com.codewithdemis.frames;

import com.codewithdemis.components.BankButton;
import com.codewithdemis.db.Database;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

public class RoleAssignmentPanel extends JPanel {

    private JComboBox<String> userDropdown;
    private JComboBox<String> roleDropdown;
    private JButton assignButton;

    public RoleAssignmentPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(36, 39, 58));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

       var icon =  FontIcon.of(FontAwesome.USER_CIRCLE,25,Color.WHITE);
        JLabel titleLabel = new JLabel(icon);
        titleLabel.setText("Assign Role to User");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(0x00FFC6));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

       var selectUserIcon =  FontIcon.of(FontAwesome.MEH_O,20,Color.YELLOW);

        JLabel userLabel = new JLabel(selectUserIcon);
        userLabel.setText("Select User:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        add(userLabel, gbc);

        userDropdown = new JComboBox<>();
        styleComboBox(userDropdown);
        gbc.gridx = 1;
        add(userDropdown, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
       var selectRoleIcon =  FontIcon.of(FontAwesome.USER_SECRET,20,Color.blue);
        JLabel roleLabel = new JLabel(selectRoleIcon);
        roleLabel.setText("Select Role");
        roleLabel.setForeground(Color.WHITE);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        add(roleLabel, gbc);

        roleDropdown = new JComboBox<>();
        styleComboBox(roleDropdown);
        gbc.gridx = 1;
        add(roleDropdown, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        assignButton = new BankButton("Assign Role","success");

        add(assignButton, gbc);

        assignButton.addActionListener(e -> assignRole());

        loadUsers();
        loadRoles();
    }

    private void loadUsers() {
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, username FROM users");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                userDropdown.addItem(id + " - " + username);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Failed to load users", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadRoles() {
        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, name FROM roles");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                roleDropdown.addItem(id + " - " + name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Failed to load roles", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void assignRole() {
        String selectedUser = (String) userDropdown.getSelectedItem();
        String selectedRole = (String) roleDropdown.getSelectedItem();

        if (selectedUser == null || selectedRole == null) {
            JOptionPane.showMessageDialog(this, "⚠️ Please select both user and role!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId = Integer.parseInt(selectedUser.split(" - ")[0]);
        int roleId = Integer.parseInt(selectedRole.split(" - ")[0]);

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM user_roles WHERE user_id = ? AND role_id = ?");
             PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)")) {

            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, roleId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "ℹ️ User already has this role", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, roleId);
            insertStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Role assigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error assigning role", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        comboBox.setBackground(new Color(50, 50, 50));
        comboBox.setForeground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(0x00FFC6), 1));
        comboBox.setPreferredSize(new Dimension(200, 30));
    }
}
