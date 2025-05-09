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

    private static final Map<String, Color[]> VARIANT_COLORS = Map.of(
            "primary", new Color[]{new Color(30, 144, 255), new Color(0, 105, 217), new Color(0, 90, 190), new Color(0, 78, 175)},
            "danger", new Color[]{new Color(220, 53, 69), new Color(200, 35, 51), new Color(180, 30, 45), new Color(140, 30, 40)},
            "success", new Color[]{new Color(40, 167, 69), new Color(30, 140, 60), new Color(20, 120, 50), new Color(20, 100, 40)},
            "warning", new Color[]{new Color(255, 193, 7), new Color(255, 174, 0), new Color(240, 160, 0), new Color(210, 140, 0)},
            "secondary", new Color[]{new Color(108, 117, 125), new Color(92, 104, 112), new Color(78, 90, 97), new Color(58, 68, 72)}
    );

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
        Color[] colors = VARIANT_COLORS.getOrDefault(variant, VARIANT_COLORS.get("primary"));
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

