package com.codewithdemis.frames;

import com.codewithdemis.components.MainContentPanel;
import com.codewithdemis.components.Navbar;
import com.codewithdemis.components.Sidebar;
import com.codewithdemis.core.Session;
import com.codewithdemis.pages.ProfilePage;
import com.codewithdemis.pages.DashboardPage;
import com.codewithdemis.pages.LoginPage;
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
        var sidebar = new Sidebar(
                "Dashboard", "Transactions",
                "User Management","Account Management");

        // Main content panel with CardLayout
        MainContentPanel mainContent = new MainContentPanel();
        mainContent.addPage("Dashboard", new DashboardPage());
//        mainContent.addPage("Account", new AccountPage(null));
        mainContent.addPage("TransferTransactionPanel", new TransferTransactionPanel());
        mainContent.addPage("WithdrawDepositTransactionPanel", new WithdrawDepositTransactionPanel());
//        mainContent.addPage("Settings", new SettingsPage());
        mainContent.addPage("Login", new LoginPage());
        mainContent.addPage("AccountCreatorPanel",new AccountCreatorPanel());

        mainContent.addPage("Dashboard", new DashboardPanel());
        UserManagementPanel userManagement = new UserManagementPanel();

        mainContent.addPage("User Management", userManagement);

        AccountManagementPanel accountManagementPanel = new AccountManagementPanel(pageName->{
            mainContent.showPage(pageName);
        });

        TransactionsPage transactions = new TransactionsPage((page)->{
            mainContent.showPage(page);
        });


        mainContent.addPage("Transactions",transactions);
        mainContent.addPage("TransactionPanel",new WithdrawDepositTransactionPanel());
        mainContent.showPage("Transactions");
        mainContent.addPage("Account Management",accountManagementPanel);

        mainContent.addPage("RoleAssignment",new RoleAssignmentPanel());

        userManagement.onAdd(e -> mainContent.showPage("Signup"));;
        userManagement.onAssignRole(e -> mainContent.showPage("RoleAssignment"));;
        userManagement.onDelete(e -> {
         // Delete user from the database here
        });;

        SignupPage signupPage = new SignupPage();

        signupPage.setOnSignupComplete(user -> {
            user = Session.getInstance().getCurrentUser();
            ProfilePage accountPage = new ProfilePage(user);
            mainContent.addPage("Profile", accountPage);
            mainContent.showPage("Profile");
            sidebar.addMenuItem("Profile", FontIcon.of(FontAwesome.USER_CIRCLE,20,Color.white));
        });

        mainContent.addPage("Signup",signupPage);


        // Hook up menu clicks to show pages
//        sidebar.onMenuClick("Account", e -> mainContent.showPage("Account"));
//        sidebar.onMenuClick("Settings", e -> mainContent.showPage("Settings"));

        sidebar.onMenuClick("Dashboard", e -> mainContent.showPage("Dashboard"));
        sidebar.onMenuClick("Transactions", e -> mainContent.showPage("Transactions"));
        sidebar.onMenuClick("User Management", e -> mainContent.showPage("User Management"));
        sidebar.onMenuClick("Account Management", e -> mainContent.showPage("Account Management"));

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
