package com.codewithdemis.pages;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TransactionsPage extends JPanel {
    public TransactionsPage() {
        setLayout(new BorderLayout());
//        setBackground(new Color(248, 248, 255)); // Match soft background
//        setBackground(new Color(20, 22, 34)); // Match soft background
        setBackground(new Color(248, 248, 255)); // Same soft white


        JLabel title = new JLabel("Transactions");
        title.setForeground(new Color(248, 248, 242));
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setBorder(new EmptyBorder(30, 30, 10, 0));
        add(title, BorderLayout.NORTH);


        String[] columns = {"Date", "Type", "Amount"};
        Object[][] data = {
                {"2025-05-10", "Deposit", "$500"},
                {"2025-05-09", "Withdrawal", "$200"},
                {"2025-05-08", "Transfer", "$750"},
                {"2025-05-07", "Payment", "$1,200"},
                {"2025-05-06", "Refund", "$150"}
        };

        JTable table = new JTable(data, columns);

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

        add(scrollPane, BorderLayout.CENTER);

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
