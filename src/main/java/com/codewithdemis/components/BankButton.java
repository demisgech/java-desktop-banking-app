package com.codewithdemis.components;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import javax.swing.*;

public class BankButton extends JButton {

    private Color primaryColor;
    private Color hoverColor;
    private Color pressedShade;

    private static final Map<String, Color[]> variantColors = VariantColor.getVariantColors();

    public BankButton(String text) {
        this(text, "primary");
    }

    public BankButton(String text, String variant) {
        super(text);

        applyStyle(variant);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setBackground(primaryColor);

        // Prevent default painting
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);


        setFont(new Font("Segoe UI", Font.BOLD, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // padding);

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedShade);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground( hoverColor);
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground( hoverColor);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground( primaryColor);
                repaint();
            }
        });
    }

    public void applyStyle(String variant) {
        Color[] colors = variantColors.getOrDefault(variant, variantColors.get("primary"));
        primaryColor = colors[0];
        hoverColor = colors[1];
        pressedShade = colors[2];
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g.create();
        graphics2D.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (getBackground() != Color.white) {
            graphics2D.setColor(getBackground());
            graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);  // Rounded corners
        }
        graphics2D.dispose();
        super.paintComponent(g);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}

