package com.codewithdemis.frames;

import java.awt.*;
import javax.swing.*;

public class RoundedPanelFrame extends JPanel {
    private int radius;

    public RoundedPanelFrame(int radius) {
        this.radius = radius;
        setOpaque(false); // Important to make background transparent
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setBackground(new Color(68, 71, 90));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set up the graphics to make rounded corners
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded rectangle with the background color
        g2d.setColor(new Color(40, 42, 54));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);  // 30 is the corner radius

        // Set up for menuPanel's rounded corners
        g2d.setColor(new Color(68, 71, 90));  // Menu background color
        g2d.fillRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 20, 20);  // Menu panel's rounded corners
    }
}
