package com.codewithdemis.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Navbar extends JPanel {
    private Color navbarColor = new Color(40, 42, 54); // Dracula background (#282a36), lighter for contrast
    private Color textColor = new Color(248, 248, 242); // Dracula text color (#f8f8f2)
    private Color hoverColor = new Color(68, 71, 90); // Dracula hover color (#44475a)
    private Color accentColor = new Color(255, 121, 198); // Vibrant pink (#ff79c6) for accents

    private JPanel menuPanel;
    private List<JButton> menuButtons; // Dynamically track menu buttons
    private JButton loginBtn, signupBtn, logoutBtn;

    public Navbar(String... menuItems) {
        setLayout(new BorderLayout());
        setBackground(navbarColor);
        setPreferredSize(new Dimension(800, 60));  // Set a preferred height for the navbar

        menuPanel = new JPanel();
        menuPanel.setBackground(navbarColor);
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        menuButtons = new ArrayList<>();

        // Loop through menu items to create buttons dynamically
        for (String item : menuItems) {
            JButton menuButton = createMenuButton(item);
            menuPanel.add(menuButton);
            menuButtons.add(menuButton); // Store button references for possible future use
        }

        // Auth buttons (Login, Sign Up, Logout)
        JPanel authPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        authPanel.setOpaque(false);

        loginBtn = createNavButton("Login");
        signupBtn = createNavButton("Sign Up");
        logoutBtn = createNavButton("Logout");

        // Add to authPanel initially
        authPanel.add(loginBtn);
        authPanel.add(signupBtn);
        authPanel.add(logoutBtn);

        add(menuPanel, BorderLayout.WEST); // Adding menuPanel to the left
        add(authPanel, BorderLayout.EAST); // Adding authPanel to the right

        // Initially hide the menu and logout button (only show login and sign up)
        showLoggedOut(); // Example to start with logged out state
    }

    // --- Create menu button ---
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
                // Handle menu actions here (e.g., navigate to a different page or view).
            }
        });

        return button;
    }

    // --- Auth Button ---
    private JButton createNavButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(68, 71, 90));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    // --- Handle logged-in and logged-out states ---
    public void showLoggedIn() {
        loginBtn.setVisible(false);
        signupBtn.setVisible(false);
        logoutBtn.setVisible(true);
    }

    public void showLoggedOut() {
        loginBtn.setVisible(true);
        signupBtn.setVisible(true);
        logoutBtn.setVisible(false);
    }

    // --- Event Listeners ---
    public void onLogin(ActionListener listener) {
        loginBtn.addActionListener(listener);
    }

    public void onSignup(ActionListener listener) {
        signupBtn.addActionListener(listener);
    }

    public void onLogout(ActionListener listener) {
        logoutBtn.addActionListener(listener);
    }

    // --- Method to dynamically add a new menu item ---
    public void addMenuItem(String item) {
        JButton newItem = createMenuButton(item);
        menuPanel.add(newItem);
        menuButtons.add(newItem);
        menuPanel.revalidate();
        menuPanel.repaint();
    }

    // --- Method to remove a menu item ---
    public void removeMenuItem(String item) {
        for (JButton button : menuButtons) {
            if (button.getText().equals(item)) {
                menuPanel.remove(button);
                menuButtons.remove(button);
                menuPanel.revalidate();
                menuPanel.repaint();
                break;
            }
        }
    }
}

