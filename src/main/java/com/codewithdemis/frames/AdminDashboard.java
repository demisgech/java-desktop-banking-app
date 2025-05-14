package com.codewithdemis.frames;

import javax.swing.*;

 public class AdminDashboard extends JPanel {
    private JTabbedPane tabbedPane;

    public AdminDashboard() {
//        setTitle("Banking Admin Dashboard");
//        setSize(1000, 700);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);

        // Create tabbed interface
        tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.addTab("Dashboard", new DashboardPanel());
        tabbedPane.addTab("User Management", new UserManagementPanel());
        tabbedPane.addTab("Account Management", new AccountManagementPanel());
        tabbedPane.addTab("Transactions", new TransactionPanel());

        add(tabbedPane);

        setVisible(true);
    }
}

