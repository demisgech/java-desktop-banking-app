package com.codewithdemis.frames;

import com.codewithdemis.components.BankButton;
import com.codewithdemis.components.BankTextField;
import com.codewithdemis.db.Database;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class UserManagementPanel extends JPanel {
    private BankTextField searchField;
    private BankButton addButton;
    private BankButton editButton;
    private BankButton deleteButton;

    public UserManagementPanel() {
        setLayout(new BorderLayout());

        // Search panel
        JPanel searchPanel = getSearchPanel();
        JLabel title = new JLabel("Welcome to User Management");
        title.setFont(new Font("OpenSans",Font.BOLD,28));
        title.setForeground(new Color(200,200,200));

        var header = new JPanel();
        header.add(title);
        header.setOpaque(false);
//        header.add(searchPanel);

        // Users table
        String[] columns = {"ID", "First Name", "Last Name", "Email", "Role"};
        Object[][] data = {
                {"1", "John", "Doe", "john@example.com", "CUSTOMER"},
                {"2", "Jane", "Smith", "jane@example.com", "ADMIN"}
        };

        List<Object[]> userData = getUserData();
        for (var d: data)
            userData.add(d);

        // Convert List<Object[]> to Object[][]
        Object[][] dataArray = userData.toArray(new Object[0][]);

        JTable table = new JTable(dataArray, columns);

        styleTable(table);

        JPanel tableWrapper = getTableWrapper();
        tableWrapper.add(table.getTableHeader(), BorderLayout.NORTH);

        tableWrapper.add(table, BorderLayout.CENTER);

        JScrollPane scrollPane = getScrollPane(tableWrapper);

        //  Action buttons
        JPanel buttonPanel = getBottomButtonPanel();

//        add(searchPanel, BorderLayout.NORTH);
        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel getSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchField = new BankTextField("Search...");
        BankButton searchButton = new BankButton("Search", "success");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.setOpaque(false);
        return searchPanel;
    }

    private JPanel getBottomButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        addButton = new BankButton("Add User");
        editButton = new BankButton("Edit");
        deleteButton = new BankButton("Delete", "danger");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        return buttonPanel;
    }

    private static JScrollPane getScrollPane(JPanel tableWrapper) {
        JScrollPane scrollPane = new JScrollPane(tableWrapper,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }

    private void styleTable(JTable table) {
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

    private static List<Object[]> getUserData() {
        List<Object[]> userData = new ArrayList<>();
        try {
            var instance = Database.getInstance();
            var connection = instance.getConnection();

            var statement = connection.createStatement();
            String sql = """
                    SELECT u.id, u.first_name, u.last_name, u.email, r.name AS role_name
                    FROM users u
                    JOIN user_roles ur ON u.id = ur.user_id
                    JOIN roles r ON ur.role_id = r.id;
                    """;

            var result = statement.executeQuery(sql);

            while (result.next()) {
                userData.add(new Object[]{
                        result.getString("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("email"),
                        result.getString("role_name")
                });
            }
            statement.close();
            instance.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userData;
    }

    private JPanel getTableWrapper() {
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
        return tableWrapper;
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

    public void onAdd(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void onEdit(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void onDelete(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
}

