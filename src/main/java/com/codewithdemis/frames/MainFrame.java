package com.codewithdemis.frames;

import com.codewithdemis.components.MainContentPanel;
import com.codewithdemis.components.Navbar;
import com.codewithdemis.components.Sidebar;
import com.codewithdemis.pages.AccountPage;
import com.codewithdemis.pages.DashboardPage;
import com.codewithdemis.pages.LoginPage;
import com.codewithdemis.pages.SettingsPage;
import com.codewithdemis.pages.SignupPage;
import com.codewithdemis.pages.TransactionsPage;

import java.awt.*;
import javax.swing.*;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

public class MainFrame extends JFrame {

    public MainFrame(){
        super("Bank System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Navbar
        Navbar navbar = new Navbar("Home", "Services", "About");
        add(navbar,BorderLayout.NORTH);


        // Sidebar menu
        var sidebar = new Sidebar("Dashboard", "Transactions", "Settings","Reports");

        // Main content panel with CardLayout
        MainContentPanel mainContent = new MainContentPanel();
        mainContent.addPage("Dashboard", new DashboardPage());
//        mainContent.addPage("Account", new AccountPage(null));
        mainContent.addPage("Transactions", new TransactionsPage());
        mainContent.addPage("Settings", new SettingsPage());
        mainContent.addPage("Login", new LoginPage());

        SignupPage signupPage = new SignupPage();

        signupPage.setOnSignupComplete(user -> {
            AccountPage accountPage = new AccountPage(user);
            mainContent.addPage("Account", accountPage);
            mainContent.showPage("Account");
            sidebar.addMenuItem("Account", FontIcon.of(FontAwesome.USER_CIRCLE,20,Color.white));
        });

        mainContent.addPage("Signup",signupPage);

        // Hook up menu clicks to show pages
        sidebar.onMenuClick("Dashboard", e -> mainContent.showPage("Dashboard"));
        sidebar.onMenuClick("Account", e -> mainContent.showPage("Account"));
        sidebar.onMenuClick("Transactions", e -> mainContent.showPage("Transactions"));
        sidebar.onMenuClick("Settings", e -> mainContent.showPage("Settings"));

        navbar.onLogin( e -> mainContent.showPage("Login"));
        navbar.onSignup(e->mainContent.showPage("Signup"));

        var roundedPanel = new RoundedPanelFrame(20);

        roundedPanel.setLayout(new BorderLayout());
        roundedPanel.add(mainContent,BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(roundedPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
