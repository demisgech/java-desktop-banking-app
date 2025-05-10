package com.codewithdemis.components;

import java.awt.*;
import javax.swing.*;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

public class MainFrame extends JFrame {
    private JPanel contentPanel; // Card Layout

    public MainFrame(){
        super("Bank System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Navbar
        add(new Navbar("Home","Services","About"),BorderLayout.NORTH);

        // Left Sidebar

        Sidebar drawer = new Sidebar("Account", "Transactions", "Settings", "Dashboard");
        add(drawer,BorderLayout.WEST);

        drawer.addMenuItem("Reports", FontIcon.of(FontAwesome.RECYCLE,18,Color.white));
        drawer.onMenuClick("Account",(event)->{
            System.out.println("Account tab clicked");
        });

        // Center CardLayout

        // Center Content Area
//        contentPanel = new JPanel(new CardLayout());
//        contentPanel.add(new DashboardPanel(), "Dashboard");
//        contentPanel.add(new AccountsPanel(), "Accounts");
//        contentPanel.add(new TransactionsPanel(), "Transactions");


//        add(contentPanel, BorderLayout.CENTER);
    }
    public void showPage(String name) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, name);
    }
}
