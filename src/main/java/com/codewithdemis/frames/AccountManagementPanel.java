package com.codewithdemis.frames;

import com.codewithdemis.components.BankButton;
import com.codewithdemis.components.BankTextField;
import com.codewithdemis.db.Database;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class AccountManagementPanel extends JPanel {
    private JTable table;
    private BankTextField searchField;
    private NavigatorListener navigationListener;

    public AccountManagementPanel(NavigatorListener navigatorListener) {
        this.navigationListener = navigatorListener;
        setLayout(new BorderLayout());
//        JPanel searchPanel = getSearchPanel();
        JLabel title = new JLabel("Welcome to Account Management");
        title.setFont(new Font("OpenSans", Font.BOLD, 28));
        title.setForeground(new Color(200, 200, 200));

        var header = new JPanel();
        header.add(title);
        header.setOpaque(false);
//        header.add(searchPanel);


        // Accounts table
        String[] columns = {"Account Number", "User", "Balance", "Status"};
        Object[][] data = {
                {"ACC1001", "John Doe", "$1,500.00", "ACTIVE"},
                {"ACC1002", "Jane Smith", "$3,200.50", "BLOCKED"}
        };

        List<Object[]> accountData = getAccountData();

        for (var d : data)
            accountData.add(d);
        var dataArray = accountData.toArray(new Object[0][]);
        DefaultTableModel model = new DefaultTableModel(dataArray, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);

        styleTable(table);

        JPanel tableWrapper = getTableWrapper(table);
        JScrollPane scrollPane = getjScrollPane(tableWrapper);
        JPanel buttonPanel = getActionButtonPanel();

//        add(searchPanel, BorderLayout.NORTH);
        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel getSearchPanel() {
        // Search panel
        JPanel searchPanel = new JPanel();
        searchField = new BankTextField("Search...");
        BankButton searchButton = new BankButton("Search", "success");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.setOpaque(false);
        return searchPanel;
    }

    private JPanel getActionButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton createButton = new BankButton("Create Account");
        JButton blockButton = new BankButton("Block/Unblock", "warning");
        JButton deleteButton = new BankButton("Delete Account", "danger");
        buttonPanel.add(createButton);
        buttonPanel.add(blockButton);
        buttonPanel.add(deleteButton);
        buttonPanel.setOpaque(false);

        createButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigate("AccountCreatorPanel");
            }
        });

        // Add delete action listener
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select an account to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String accountNumber = (String) table.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete account " + accountNumber + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                deleteAccount(accountNumber);
                // Remove row from table model
                ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
            }
        });
        return buttonPanel;
    }

    private void deleteAccount(String accountNumber) {
        String sql = "DELETE FROM accounts WHERE account_number = ?";
        try (var conn = Database.getInstance().getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null, "Account deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting account: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static JScrollPane getjScrollPane(JPanel tableWrapper) {
        JScrollPane scrollPane = new JScrollPane(tableWrapper,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private JPanel getTableWrapper(JTable table) {
        JPanel tableWrapper = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(36, 39, 58, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        tableWrapper.setOpaque(false);
        tableWrapper.setBorder(new EmptyBorder(20, 30, 30, 30));
        tableWrapper.add(table.getTableHeader(), BorderLayout.NORTH);
        tableWrapper.add(table, BorderLayout.CENTER);
        return tableWrapper;
    }

    private static void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(28);
        table.setForeground(new Color(248, 248, 242));
        table.setBackground(new Color(36, 39, 58));
        table.setGridColor(new Color(60, 63, 85));
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(60, 63, 85));
        table.getTableHeader().setForeground(new Color(189, 147, 249));

        // Auto resize columns based on content
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 75; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 20, width);
            }
            table.getColumnModel().getColumn(column).setPreferredWidth(width);
        }
    }

    private static List<Object[]> getAccountData() {
        List<Object[]> accountData = new ArrayList<>();

        try {

            var instance = Database.getInstance();
            var connection = instance.getConnection();
            var statement = connection.createStatement();
            String sql = """
                    SELECT  a.account_number, CONCAT(u.first_name, '  ',u.last_name) AS full_name,a.balance,ast.status_name AS status
                    FROM accounts a
                    JOIN users u ON a.user_id = u.id
                    JOIN account_status ast ON a.status_id = ast.id;
                    """;
            var result = statement.executeQuery(sql);
            while (result.next()) {
                accountData.add(new Object[]{
                        result.getString("account_number"),
                        result.getString("full_name"),
                        result.getString("balance"),
                        result.getString("status")
                });
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accountData;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        Color top = new Color(20, 22, 34);
        Color bottom = new Color(45, 52, 70);
        GradientPaint gradient = new GradientPaint(0, 0, top, 0, getHeight(), bottom);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

}
