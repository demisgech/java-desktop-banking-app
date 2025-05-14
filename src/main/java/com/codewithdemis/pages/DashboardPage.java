package com.codewithdemis.pages;

import com.codewithdemis.components.BankButton;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.util.List;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

public class DashboardPage extends JPanel {
    private List<String> recentActivities = new ArrayList<>();
    private List<String> quickActions = List.of("Add User", "Generate Report", "Settings", "Backup");

    public DashboardPage() {
        setLayout(new BorderLayout());
        setOpaque(false); // for gradient baTransackground

        // === Top Panel with Title and User Profile ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(20, 30, 10, 30));

        // === Title ===
        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(248, 248, 242));
        title.setBorder(new EmptyBorder(30, 30, 10, 0));

        // User profile (right-aligned)
        JPanel userPanel = createUserProfilePanel();

        topPanel.add(title, BorderLayout.WEST);
        topPanel.add(userPanel, BorderLayout.EAST);

        // === Stats Panel ===
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(new EmptyBorder(20, 30, 30, 30));

        statsPanel.add(createStatCard("Total Users", "1,250"));
        statsPanel.add(createStatCard("Revenue", "$13,500"));
        statsPanel.add(createStatCard("Transactions", "3,204"));

        // === Additional Components Below Stats ===
        JPanel lowerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        lowerPanel.setOpaque(false);
        lowerPanel.setBorder(new EmptyBorder(0, 30, 30, 30));

        // Recent Activity Panel
        lowerPanel.add(createRecentActivityPanel());

        // Quick Actions Panel
        lowerPanel.add(createQuickActionsPanel());

        // Wrap all content
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(statsPanel, BorderLayout.NORTH);
        wrapper.add(lowerPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Add all components to main panel
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createUserProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);

        JLabel nameLabel = new JLabel("Admin User");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setForeground(new Color(248, 248, 242));

        // Simulated profile icon (circle with initial)

        JLabel profileIcon = new JLabel(FontIcon.of(FontAwesome.USER_CIRCLE, 20, Color.white)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(189, 147, 249));
                g2.fillOval(0, 0, getWidth(), getHeight() );
                g2.setColor(Color.WHITE);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        profileIcon.setPreferredSize(new Dimension(36, 36));
        profileIcon.setHorizontalAlignment(SwingConstants.CENTER);
        profileIcon.setFont(new Font("Segoe UI", Font.BOLD, 16));
        profileIcon.setForeground(Color.WHITE);

        panel.add(nameLabel, BorderLayout.CENTER);
        panel.add(profileIcon, BorderLayout.EAST);

        return panel;
    }

    private JPanel createRecentActivityPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Sample activities
        recentActivities.add("System updated to v2.4 (Today)");
        recentActivities.add("New user registered (Today)");
        recentActivities.add("Backup completed (Yesterday)");
        recentActivities.add("Security audit passed (2 days ago)");

        // Title
        JLabel title = new JLabel("Recent Activity");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(248, 248, 242));
        title.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Activity list
        JList<String> activityList = new JList<>(recentActivities.toArray(new String[0]));
        activityList.setOpaque(false);
        activityList.setForeground(new Color(248, 248, 242));
        activityList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        activityList.setFixedCellHeight(30);
        activityList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(new EmptyBorder(0, 10, 0, 0));
                label.setIcon(new ImageIcon(getBulletIcon()));
                return label;
            }

            private Image getBulletIcon() {
                BufferedImage img = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = img.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(189, 147, 249));
                g2.fillOval(0, 0, 8, 8);
                g2.dispose();
                return img;
            }
        });

        JScrollPane scrollPane = new JScrollPane(activityList);
        styleScrollPane(scrollPane);

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(36, 39, 58, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(200, 120);
            }
        };

        card.setOpaque(false);
        card.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        titleLabel.setForeground(new Color(189, 147, 249));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(new Color(248, 248, 242));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createQuickActionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        // Title
        JLabel title = new JLabel("Quick Actions");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(248, 248, 242));
        title.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Action buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        buttonPanel.setOpaque(false);

        for (String action : quickActions) {
            BankButton button = new BankButton(action);
            styleButton(button);
            buttonPanel.add(button);
        }

        panel.add(title, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }
    // === Styling Helper Methods ===

    private void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 63, 85), 1),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));
        scrollPane.getVerticalScrollBar().setUnitIncrement(8);
    }

    private void styleButton(BankButton button) {
        button.addActionListener(e -> JOptionPane.showMessageDialog(button,
                "Action: " + button.getText(),
                "Quick Action",
                JOptionPane.INFORMATION_MESSAGE));
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
