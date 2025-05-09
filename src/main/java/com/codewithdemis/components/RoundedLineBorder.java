package com.codewithdemis.components;

import java.awt.*;
import javax.swing.border.AbstractBorder;

public class RoundedLineBorder extends AbstractBorder {
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
