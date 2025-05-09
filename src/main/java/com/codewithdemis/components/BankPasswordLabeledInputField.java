package com.codewithdemis.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class BankPasswordLabeledInputField extends BankLabeledInputField {
    private JPasswordField passwordField;

    public BankPasswordLabeledInputField(String labelText, String placeholder) {
        this(labelText, placeholder, "primary");
    }

    public BankPasswordLabeledInputField(String labelText, String placeholder, String variant) {
        super(labelText, placeholder, variant);
        Color variantColor = switch (variant.toLowerCase()) {
            case "danger" -> new Color(220, 53, 69);
            case "success" -> new Color(40, 167, 69);
            case "warning" -> new Color(255, 193, 7);
            default -> new Color(30, 144, 255); // primary
        };

        passwordField = new BankPasswordField(placeholder);
        passwordField.setBorder(createRoundedBorder(variantColor));  // Keep border color

        // Replace text field with password field
        remove(getTextField());  // Remove existing text field
        add(passwordField, BorderLayout.CENTER);  // Add password field
    }

    private Border createRoundedBorder(Color color) {
        return new RoundedLineBorder(color, 2, 16);
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    private class BankPasswordField extends JPasswordField {
        private String placeholder;
        private Color borderColor = new Color(30, 144, 255); // default
        private Font font = new Font("Segoe UI", Font.PLAIN, 16);

        public BankPasswordField(String placeholder) {
            this.placeholder = placeholder;
            setFont(font);
            setForeground(Color.DARK_GRAY);
            setBackground(Color.WHITE);
            setCaretColor(Color.DARK_GRAY);
            setBorder(new RoundedLineBorder(borderColor, 2, 16));
            setMargin(new Insets(10, 14, 10, 14));
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getPassword().length == 0 && !hasFocus()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setFont(font.deriveFont(Font.PLAIN));
                g2.setColor(Color.GRAY);
                Insets insets = getInsets();
                g2.drawString(placeholder, insets.left + 2, getHeight() / 2 + getFont().getSize() / 2 - 4);
                g2.dispose();
            }
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            int height = Math.max(size.height, 38); // Bootstrap input height ≈ 38px
            int width = Math.max(size.width, 200);  // reasonable default width
            return new Dimension(width, height);
        }
    }
}
