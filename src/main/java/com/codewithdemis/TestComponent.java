package com.codewithdemis;

import com.codewithdemis.components.BankTextField;
import java.awt.*;
import javax.swing.*;

public class TestComponent {
        public static void show() {
            JFrame frame = new JFrame("Banking System");
            frame.setSize(300, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            var textFieldPrimary = new BankTextField("Enter your name?");
            var textFieldSecondary = new BankTextField("Enter your name?","secondary");
            var textFieldDanger = new BankTextField("Enter your name?","danger");
            var textFieldSuccess = new BankTextField("Enter your name?","success");
            var textFieldWarning = new BankTextField("Enter your name?","warning");

            frame.add(textFieldDanger);
            frame.add(textFieldPrimary);
            frame.add(textFieldSecondary);
            frame.add(textFieldSuccess);
            frame.add(textFieldWarning);
            frame.setVisible(true);
        }


}

