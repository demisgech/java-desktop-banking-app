package com.codewithdemis.components;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Map;
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

    public class BankPasswordField extends JPasswordField {
        private String placeholder;
        private Color borderColor;
        private Color focusColor;
        private static final Color backgroundColor = Color.WHITE;
        private static final Font font = new Font("Segoe UI", Font.PLAIN, 16);

        public BankPasswordField(String placeholder) {
            this(placeholder, "primary");
        }

        public BankPasswordField(String placeholder, String variant) {
            super();
            this.placeholder = placeholder;

            Map<String, Color[]> variantColors = VariantColor.getVariantColors();
            Color[] colors = variantColors.getOrDefault(variant, variantColors.get("primary"));
            this.borderColor = colors[0];
            this.focusColor = colors[1];

            setFont(font);
            setForeground(Color.DARK_GRAY);
            setCaretColor(Color.DARK_GRAY);
            setBackground(backgroundColor);
            setOpaque(false);
            setBorder(createRoundedBorder(borderColor));
            setMargin(new Insets(10, 14, 10, 14));

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    setBorder(createRoundedBorder(focusColor));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    setBorder(createRoundedBorder(borderColor));
                }
            });
        }

        private Border createRoundedBorder(Color color) {
            return new RoundedLineBorder(color, 2, 16);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(backgroundColor);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
            g2.dispose();

            super.paintComponent(g);

            if (getPassword().length == 0 && !hasFocus()) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setFont(font.deriveFont(Font.PLAIN));
                g2d.setColor(Color.GRAY);
                Insets insets = getInsets();
                g2d.drawString(placeholder, insets.left + 2, getHeight() / 2 + getFont().getSize() / 2 - 4);
                g2d.dispose();
            }
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            int height = Math.max(size.height, 38);
            int width = Math.max(size.width, 200);
            return new Dimension(width, height);
        }
    }

}
