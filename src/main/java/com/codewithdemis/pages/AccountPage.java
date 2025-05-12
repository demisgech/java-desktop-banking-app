package com.codewithdemis.pages;

import com.codewithdemis.models.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class AccountPage extends JPanel {

    public AccountPage(User user) {
        setLayout(new BorderLayout());
        setOpaque(false); // for gradient background

        // === MAIN CONTENT (Scrollable) ===
        JPanel scrollablePanel = new JPanel(new GridBagLayout());
        scrollablePanel.setOpaque(false);

        JPanel cardPanel = createCardPanel(user);
        scrollablePanel.add(cardPanel, new GridBagConstraints());

        JScrollPane scrollPane = new JScrollPane(scrollablePanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createCardPanel(User user) {
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setOpaque(false);
        mainContent.setBorder(new EmptyBorder(30, 30, 30, 30));

        // === PROFILE IMAGE ===
        JPanel imageWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imageWrapper.setOpaque(false);
        ImageIcon profileIcon = new ImageIcon(getClass().getResource("/static/logo.png"));
        Image scaledImage = profileIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        CircularImagePanel imagePanel = new CircularImagePanel(new ImageIcon(scaledImage));
        imageWrapper.add(imagePanel);

        // === NAME ===
        JPanel nameWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nameWrapper.setOpaque(false);
        nameWrapper.setBorder(new EmptyBorder(10, 0, 20, 0));
        JLabel nameLabel = new JLabel(user.getFirstName() + " " + user.getLastName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        nameLabel.setForeground(new Color(248, 248, 242));
        nameWrapper.add(nameLabel);
        nameLabel.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(new Color(189, 147, 249, 100));
                g2.drawString(((JLabel) c).getText(), 1, c.getFontMetrics(c.getFont()).getAscent() + 1);
                g2.dispose();
                super.paint(g, c);
            }
        });

        // === USER INFO ===
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;
        addLabeledField(infoPanel, "Username:", user.getUsername(), gbc, row++);
        addLabeledField(infoPanel, "Email:", user.getEmail(), gbc, row++);
        addLabeledField(infoPanel, "First Name:", user.getFirstName(), gbc, row++);
        addLabeledField(infoPanel, "Last Name:", user.getLastName(), gbc, row++);
        addLabeledField(infoPanel, "Role:", "Administrator", gbc, row++);

        mainContent.add(imageWrapper);
        mainContent.add(nameWrapper);
        mainContent.add(infoPanel);

        JPanel cardPanel = new JPanel(new BorderLayout()) {
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
                return new Dimension(500, 600);
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        cardPanel.add(mainContent, BorderLayout.CENTER);
        return cardPanel;
    }

    private void addLabeledField(JPanel panel, String label, String value, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel labelComp = new JLabel(label);
        labelComp.setForeground(new Color(189, 147, 249));
        labelComp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel.add(labelComp, gbc);

        gbc.gridx = 1;
        JTextField field = new JTextField(value);
        styleTextField(field);
        panel.add(field, gbc);
    }

    private void styleTextField(JTextField field) {
        field.setEditable(false);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBackground(new Color(40, 42, 54));
        field.setForeground(new Color(248, 248, 242));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(98, 114, 164), 1),
                new EmptyBorder(6, 10, 6, 10)
        ));
    }

    private static class CircularImagePanel extends JPanel {
        private final ImageIcon imageIcon;

        public CircularImagePanel(ImageIcon imageIcon) {
            this.imageIcon = imageIcon;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int size = Math.min(getWidth(), getHeight());
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Shape circle = new Ellipse2D.Double(0, 0, size, size);
            g2.setClip(circle);
            g2.drawImage(imageIcon.getImage(), 0, 0, size, size, this);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(120, 120);
        }
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
