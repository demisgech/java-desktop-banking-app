package com.codewithdemis.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Navbar extends JPanel {
    private Color navbarColor = new Color(40, 42, 54); // Dracula background (#282a36), lighter for contrast
    private Color textColor = new Color(248, 248, 242); // Dracula text color (#f8f8f2)
    private Color hoverColor = new Color(68, 71, 90); // Dracula hover color (#44475a)
    private Color accentColor = new Color(255, 121, 198); // Vibrant pink (#ff79c6) for accents

    private JPanel menuPanel;
    private JButton hamburgerButton;
    private boolean isMenuVisible = false;

    public Navbar(String... menuItems) {
        setLayout(new BorderLayout());
        setBackground(navbarColor);
        setPreferredSize(new Dimension(800, 60));  // Set a preferred height for the navbar

        menuPanel = new JPanel();
        menuPanel.setBackground(navbarColor);
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        // Loop through menu items to create buttons
        for (String item : menuItems) {
            JButton menuButton = createMenuButton(item);
            menuPanel.add(menuButton);
        }

        // Hamburger button for small screens
        hamburgerButton = createHamburgerButton();

        // Add menuPanel and hamburgerButton to the navbar
        add(menuPanel, BorderLayout.CENTER);
        add(hamburgerButton, BorderLayout.EAST);

        // Initially hide the menu on small screens
        menuPanel.setVisible(true);

        // Add a resize listener to toggle visibility of the hamburger button and menu
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                if (getWidth() <= 768) {
                    hamburgerButton.setVisible(true);
                    menuPanel.setVisible(false);
                } else {
                    hamburgerButton.setVisible(false);
                    menuPanel.setVisible(true);
                }
            }
        });
    }

    private JButton createMenuButton(String item) {
        JButton button = new JButton(item);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setForeground(textColor);
        button.setBackground(navbarColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(navbarColor);
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(item + " clicked");
                // You can handle your menu actions here (e.g., navigate to a different page or view).
            }
        });

        return button;
    }

    private JButton createHamburgerButton() {
        JButton button = new JButton("☰");
        button.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        button.setForeground(textColor);
        button.setBackground(navbarColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Action for hamburger button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isMenuVisible = !isMenuVisible;
                menuPanel.setVisible(isMenuVisible);
            }
        });

        return button;
    }
}
