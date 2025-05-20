package com.codewithdemis.frames;

import com.codewithdemis.components.MainContentPanel;
import com.codewithdemis.components.Navbar;
import com.codewithdemis.components.Sidebar;
import com.codewithdemis.core.Session;
import com.codewithdemis.pages.*;


import javax.swing.*;
import java.awt.*;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.swing.FontIcon;

public class MainFrame extends JFrame {
    private final Sidebar sidebar = new Sidebar();
    private Navbar navbar = new Navbar();
    private final MainContentPanel mainContent = new MainContentPanel();

    public MainFrame() {
        super("Bank System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(navbar, BorderLayout.NORTH);

        // Add all pages
        setupPages();

        // Handle navbar actions
        navbar.onLogin(e -> mainContent.showPage("Login"));
        navbar.onSignup(e -> mainContent.showPage("Signup"));
        navbar.onLogout(event->{
            Session.getInstance().logout(); // Clear user session

            remove(sidebar);
            var loginPage = loginPage();

            mainContent.addPage("Login",loginPage);
            mainContent.showPage("Login");

            navbar.showLoggedOut();
            revalidate();
            repaint();
            JOptionPane.showMessageDialog(this, "You've been logged out.");
        });

        // Setup sidebar interactions
        setupSidebarNavigation();

        // Rounded wrapper for main content
        RoundedPanelFrame roundedPanel = new RoundedPanelFrame(20);
        roundedPanel.setLayout(new BorderLayout());
        roundedPanel.add(mainContent, BorderLayout.CENTER);

        // Show sidebar only if logged in
        if (Session.getInstance().isLoggedIn()) {
            System.out.println(Session.getInstance().isLoggedIn());
            setupSidebarMenu();
            add(sidebar, BorderLayout.WEST);
            mainContent.showPage("Dashboard");
        } else {
            mainContent.showPage("Login");
            System.out.println(Session.getInstance().isLoggedIn());
        }

        add(roundedPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private LoginPage loginPage() {
        return new LoginPage(user -> {
            Session.getInstance().login(user);
            // Optional: Render profile if you want it always available
            ProfilePage profilePage = new ProfilePage(user);
            mainContent.addPage("Profile", profilePage);
            sidebar.addMenuItem("Profile", FontIcon.of(FontAwesome.USER_CIRCLE, 20, Color.white));
            sidebar.onMenuClick("Profile", e -> mainContent.showPage("Profile"));
            handleLoginSuccess("Dashboard");
            navbar.showLoggedIn();
        });
    }

    private void setupPages() {

        var loginPage = loginPage();
        mainContent.addPage("Login", loginPage);


        // Signup Page
        SignupPage signupPage = new SignupPage(user->{
            Session.getInstance().login(user);
            ProfilePage profilePage = new ProfilePage(user);
            mainContent.addPage("Profile", profilePage);
            mainContent.showPage("Profile");
            sidebar.addMenuItem("Profile", FontIcon.of(FontAwesome.USER_CIRCLE, 20, Color.white));
            sidebar.onMenuClick("Profile", e -> mainContent.showPage("Profile"));
            handleLoginSuccess("Profile");
        });

        mainContent.addPage("Signup", signupPage);

        // Authenticated Pages
        mainContent.addPage("Dashboard", new DashboardPage());
        mainContent.addPage("Transactions", new TransactionsPage(mainContent::showPage));
        mainContent.addPage("Account Management", new AccountManagementPanel(mainContent::showPage));
        mainContent.addPage("User Management", createUserManagementPanel());
        mainContent.addPage("TransferTransactionPanel", new TransferTransactionPanel());
        mainContent.addPage("WithdrawDepositTransactionPanel", new WithdrawDepositTransactionPanel());
        mainContent.addPage("AccountCreatorPanel", new AccountCreatorPanel());
        mainContent.addPage("RoleAssignment", new RoleAssignmentPanel());
    }

    private void setupSidebarMenu() {
        sidebar.addMenuItem("Dashboard", FontIcon.of(FontAwesome.DASHBOARD, 20, Color.white));
        sidebar.addMenuItem("Transactions", FontIcon.of(FontAwesome.MONEY, 20, Color.white));
        sidebar.addMenuItem("User Management", FontIcon.of(FontAwesome.USERS, 20, Color.white));
        sidebar.addMenuItem("Account Management", FontIcon.of(FontAwesome.BANK, 20, Color.white));
    }

    private void setupSidebarNavigation() {
        sidebar.onMenuClick("Dashboard", e -> showIfAuthenticated("Dashboard"));
        sidebar.onMenuClick("Transactions", e -> showIfAuthenticated("Transactions"));
        sidebar.onMenuClick("User Management", e -> showIfAuthenticated("User Management"));
        sidebar.onMenuClick("Account Management", e -> showIfAuthenticated("Account Management"));
    }


    private JPanel createUserManagementPanel() {
        UserManagementPanel panel = new UserManagementPanel();

        panel.onAdd(e -> mainContent.showPage("Signup"));
        panel.onAssignRole(e -> mainContent.showPage("RoleAssignment"));
        panel.onDelete(e -> {
            // Handle deletion
        });

        return panel;
    }

    private void showIfAuthenticated(String page) {
        if (Session.getInstance().isLoggedIn()) {
            mainContent.showPage(page);
        } else {
            mainContent.showPage("Login");
        }
    }

    private void handleLoginSuccess(String pageName) {
        setupSidebarMenu();
        add(sidebar, BorderLayout.WEST);
        revalidate();
        repaint();
        mainContent.showPage(pageName);
    }
}
