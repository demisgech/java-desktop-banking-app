
package com.codewithdemis.components;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

public class BankDateField extends JFormattedTextField {
    private Color borderColor;
    private Color focusColor;
    private static final Color backgroundColor = Color.WHITE;

    private static final Map<String, Color[]> VARIANT_COLORS = Map.of(
            "primary", new Color[]{new Color(206, 212, 218), new Color(0, 123, 255)},
            "danger", new Color[]{new Color(220, 53, 69), new Color(200, 35, 51)},
            "success", new Color[]{new Color(40, 167, 69), new Color(30, 140, 60)},
            "warning", new Color[]{new Color(255, 193, 7), new Color(255, 174, 0)},
            "secondary", new Color[]{new Color(108, 117, 125), new Color(73, 80, 87)}
    );

    public BankDateField(String placeholder) {
        this(placeholder, "primary");
    }

    public BankDateField(String placeholder, String variant) {
        super(createDateFormatter());
        setText(""); // Empty initially, use placeholder separately

        Color[] colors = VARIANT_COLORS.getOrDefault(variant, VARIANT_COLORS.get("primary"));
        this.borderColor = colors[0];
        this.focusColor = colors[1];

        setFont(new Font("Segoe UI", Font.PLAIN, 16));
        setForeground(Color.DARK_GRAY);
        setCaretColor(Color.DARK_GRAY);
        setBackground(backgroundColor);
        setOpaque(false); // We'll paint the rounded background manually

        setBorder(createRoundedBorder(borderColor));
        setMargin(new Insets(10, 14, 10, 14)); // Bootstrap-like padding

        // Placeholder support (basic)
        putClientProperty("JFormattedTextField.placeholderText", placeholder);

        // Focus effect: update border color
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

    private static DateFormat createDateFormatter() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public Color getBorderColor() {
        return borderColor;
    }

    private Border createRoundedBorder(Color color) {
        return new RoundedLineBorder(color, 2, 16);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(backgroundColor);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16); // subtle rounding
        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        int height = Math.max(size.height, 38); // Bootstrap input height ≈ 38px
        int width = Math.max(size.width, 200);  // reasonable default width
        return new Dimension(width, height);
    }

    class RoundedLineBorder extends AbstractBorder {
        private final Color color;
        private final int thickness;
        private final int arc;

        public RoundedLineBorder(Color color, int thickness, int arc) {
            this.color = color;
            this.thickness = thickness;
            this.arc = arc;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawRoundRect(x + thickness / 2, y + thickness / 2,
                    width - thickness, height - thickness, arc, arc);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(10, 14, 10, 14);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(10, 14, 10, 14);
            return insets;
        }
    }
}

