package com.codewithdemis.pages;

import com.codewithdemis.components.BankButton;
import com.codewithdemis.db.Database;
import com.codewithdemis.frames.NavigatorListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


public class TransactionsPage extends JPanel {
    private NavigatorListener navigationListener;
    private JTable table;

    public TransactionsPage(NavigatorListener navigationListener) {
        this.navigationListener = navigationListener;
        setLayout(new BorderLayout());
        setBackground(new Color(248, 248, 255)); // Same soft white


        JLabel title = new JLabel("Transactions");
        title.setForeground(new Color(248, 248, 242));
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setBorder(new EmptyBorder(30, 30, 10, 0));
        add(title, BorderLayout.NORTH);


        String[] columns = {"ID", "Type", "Amount", "From Account", "To Account", "Date", "Description"};
//        Object[][] data = {
//                {"1","DEPOSIT","$5000","-", "ACC1002", "2025-04-02"},
//                {"2","DEPOSIT","$400.0","-", "ACC1002", "2025-04-02"},
//                {"3","WITHDRAW","$50.0","ACC1003", "-", "2025-05-02"},
//                {"4", "DEPOSIT", "$500.00", "-", "ACC1001", "2023-05-25"},
//                {"5", "TRANSFER", "$200.00", "ACC1001", "ACC1002", "2023-05-15"}
//        };

        var transactions = fetchTransactions();
//        for (var d: data)
//            transactions.add(d);

        var dataArray = transactions.toArray(new Object[0][]);

        DefaultTableModel model = new DefaultTableModel(dataArray, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Makes cells non-editable
            }
        };
        table = new JTable(model);

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

        // === Scroll with padding ===
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

        JScrollPane scrollPane = new JScrollPane(tableWrapper,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel buttonPanel = getActionButtonPanel();

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }


    private JPanel getActionButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton createButton = new BankButton("Transfer");
        JButton withdrawDeposit = new BankButton("Deposit/Withdraw", "secondary");
        JButton deleteButton = new BankButton("Delete Transaction", "danger");

        buttonPanel.add(createButton);
        buttonPanel.add(withdrawDeposit);
        buttonPanel.add(deleteButton);
        buttonPanel.setOpaque(false);

        createButton.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigate("TransferTransactionPanel");
            }
        });

        withdrawDeposit.addActionListener(e -> {
            if (navigationListener != null) {
                navigationListener.onNavigate("WithdrawDepositTransactionPanel");
            }
        });

        // Add delete action listener
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a transaction to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Object value = table.getValueAt(selectedRow, 0);
            String stringValue = String.valueOf(value); // Safely convert anything to String

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to delete transaction " + stringValue + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                deleteTransaction(stringValue); // Pass the ID or value
                ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
            }
        });

        return buttonPanel;
    }

    private void deleteTransaction(String transactionId) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (var conn = Database.getInstance().getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transactionId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null, "Transaction deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Transaction not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting transaction: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Object[]> fetchTransactions() {
        List<Object[]> rows = new ArrayList<>();

        String query = """
                SELECT
                        t.id,
                        tt.type_name,
                        t.amount,
                        t.description,
                        COALESCE(
                            CASE
                                WHEN tt.type_name IN ('WITHDRAWAL', 'TRANSFER') THEN sender.account_number
                                ELSE NULL
                            END,
                            '-'
                        ) AS from_account,
                        COALESCE(
                            CASE
                                WHEN tt.type_name IN ('DEPOSIT', 'TRANSFER') THEN receiver.account_number
                                ELSE NULL
                            END,
                            '-'
                        ) AS to_account,
                        t.transaction_date
                    FROM transactions t
                    JOIN transaction_types tt ON t.transaction_type_id = tt.id
                    LEFT JOIN accounts sender ON t.account_id = sender.id
                    LEFT JOIN accounts receiver ON t.recipient_account_id = receiver.id
                    ORDER BY t.transaction_date DESC;
                """;

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                rows.add(new Object[]{
                        resultSet.getInt("id"),
                        resultSet.getString("type_name"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("from_account"),
                        resultSet.getString("to_account"),
                        resultSet.getTimestamp("transaction_date"),
                        resultSet.getString("description")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
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
