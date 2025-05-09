package com.codewithdemis;

import com.codewithdemis.components.BankDateField;
import com.codewithdemis.components.BankLabeledInputField;
import com.codewithdemis.components.BankNumberField;
import com.codewithdemis.components.BankPasswordLabeledInputField;

import com.codewithdemis.components.BankTextAreaField;
import com.codewithdemis.components.DateLabeledInputField;
import com.codewithdemis.components.EmailField;
import com.codewithdemis.components.EmailLabeledInputField;
import com.codewithdemis.components.NumberLabeledInputField;
import com.codewithdemis.components.TextAreaLabeledInputField;
import java.awt.*;
import javax.swing.*;

public class TestComponent {
    public static void show() {
        JFrame frame = new JFrame("Banking System");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        var textFieldPrimary = new BankLabeledInputField("Enter your name?", "soemo", "primary");
        var password1 = new BankPasswordLabeledInputField("Password", "Enter your password", "primary");
        ;
        var password2 = new BankPasswordLabeledInputField("Password", "Enter your password", "success");
        ;
        var date = new DateLabeledInputField("Date", "Please Enter date...");
        var numberField = new NumberLabeledInputField("age", "Enter your age...");
        var email = new EmailLabeledInputField("Email","Enter email");
        var text  = new TextAreaLabeledInputField("Comment","comment...");

        frame.add(textFieldPrimary);
        frame.add(password1);
        frame.add(password2);
        frame.add(date);
        frame.add(numberField);
        frame.add(email);
        frame.add(text);
        frame.setVisible(true);
    }
}

