package com.codewithdemis;

import com.codewithdemis.components.BankButton;
import java.awt.*;
import javax.swing.*;

public class TestComponent {
        public static void show() {
            JFrame frame = new JFrame("Banking System");

            // Default Primary Button
            BankButton primaryBtn = new BankButton("Login", "primary");

            // Custom Success Button
            BankButton successBtn = new BankButton("Success", "success");
            BankButton secondaryBtn = new BankButton("Secondary", "secondary");

            // Danger Button
            BankButton dangerBtn = new BankButton("Delete Account", "danger");


            frame.setLayout(new FlowLayout());

            frame.add(secondaryBtn);
            frame.add(primaryBtn);
            frame.add(successBtn);
            frame.add(dangerBtn);

            frame.setSize(300, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }


}

