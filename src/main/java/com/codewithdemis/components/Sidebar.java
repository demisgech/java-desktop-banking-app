package com.codewithdemis.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

public class Sidebar extends JPanel {
    private final int sidebarWidth = 250;  // Fixed width for the sidebar

    private final JPanel menuPanel;

    private String selectedMenuLabel = null;

    private Map<String , java.util.List<ActionListener>> pendingListeners = new HashMap<>();
    private java.util.List<MenuSelectionListener> menuSelectionListeners = new ArrayList<>();
    private Map<String, JButton> menuButtons = new HashMap<>();

    private final Map<String, FontIcon> defaultIcons = Map.of(
            "Profile", FontIcon.of(FontAwesome.USER_CIRCLE, 20, Color.white),
            "Settings", FontIcon.of(FontAwesome.GEAR, 20, Color.white),
            "Transactions", FontIcon.of(FontAwesome.ARCHIVE, 20, Color.white),
            "Dashboard", FontIcon.of(FontAwesome.DASHBOARD, 20, Color.white),
            "Reports", FontIcon.of(FontAwesome.RECYCLE,20,Color.white),
            "User Management", FontIcon.of(FontAwesome.USERS,20,Color.white),
            "Account Management", FontIcon.of(FontAwesome.BOOK,20,Color.white)
    );

    public Sidebar(String ...menuItems) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(sidebarWidth, 600)); // Fixed width
        setBackground(new Color(40, 42, 54)); // Dracula background

        // Menu panel
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        menuPanel.setOpaque(false);
        add(menuPanel, BorderLayout.WEST);
        for (String item : menuItems) {
            addMenuItem(item, defaultIcons.getOrDefault(item, null));
        }
    }

    // --- Create individual menu button ---
    private JButton createMenuButton(String label, FontIcon icon) {
        JButton btn = new JButton(label, icon);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setIconTextGap(10);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(68, 71, 90));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(98, 114, 164)); // hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(68, 71, 90));
            }
        });

        return btn;
    }

    public void addMenuItem(String label, FontIcon icon) {
        if (menuButtons.containsKey(label)) return; // avoid duplicates

        JButton btn = createMenuButton(label, icon);
        menuButtons.put(label, btn);
        menuPanel.add(btn);

        // Attach any previously registered listeners
        if (pendingListeners.containsKey(label)) {
            for (ActionListener l : pendingListeners.get(label)) {
                btn.addActionListener(wrapWithStateUpdate(label, l));
            }
        }

        revalidate();
        repaint();
    }

    // --- Hook for setting action listeners ---
    public void onMenuClick(String label, ActionListener listener) {
        JButton btn = menuButtons.get(label);
        if (btn != null) {
            btn.addActionListener(e -> {
                setActiveMenu(label);
                listener.actionPerformed(e);
            });
        }else{
            pendingListeners.computeIfAbsent(label, k -> new ArrayList<>())
                    .add(listener);
        }
    }
    public void selectMenu(String label) {
        setActiveMenu(label);
        JButton btn = menuButtons.get(label);
        if (btn != null) {
            for (ActionListener al : btn.getActionListeners()) {
                al.actionPerformed(new ActionEvent(btn, ActionEvent.ACTION_PERFORMED, label));
            }
        }
    }

    private void setActiveMenu(String label) {
        if (!menuButtons.containsKey(label)) return;

        if (selectedMenuLabel != null) {
            JButton prev = menuButtons.get(selectedMenuLabel);
            prev.setBackground(new Color(68, 71, 90));
        }

        JButton current = menuButtons.get(label);
        current.setBackground(new Color(98, 114, 164));
        selectedMenuLabel = label;
        notifyMenuSelected(label);
    }

    private void notifyMenuSelected(String label){
        for (var listener:menuSelectionListeners)
            listener.onMenuSelected(label);
    }

    public void addMenuSelectionListener(MenuSelectionListener listener){
        menuSelectionListeners.add(listener);
    }

    private ActionListener wrapWithStateUpdate(String label,ActionListener original){
        return e->{
          setActiveMenu(label);
          original.actionPerformed(e);
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set up the graphics to make rounded corners
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the rounded rectangle with the background color
        g2d.setColor(new Color(40, 42, 54));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        // Set up for menuPanel's rounded corners
        g2d.setColor(new Color(68, 71, 90));  // Menu background color
        g2d.fillRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 20, 20);
    }

    // --- External interface for event communication ---
    public interface MenuSelectionListener {
        void onMenuSelected(String label);
    }
}
