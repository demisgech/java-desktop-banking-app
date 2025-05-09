package com.codewithdemis.components;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.Border;

public class BankTextAreaField extends JTextArea {

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

    public BankTextAreaField(String placeholder) {
        this(placeholder, "primary");
    }

    public BankTextAreaField(String placeholder, String variant) {
        super();
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
        putClientProperty("JTextArea.placeholderText", placeholder);

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
        int height = Math.max(size.height, 100); // Height for multi-line text
        int width = Math.max(size.width, 200);  // reasonable default width
        return new Dimension(width, height);
    }
}
