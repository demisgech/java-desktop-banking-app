package com.codewithdemis.components;

import javax.swing.*;
import java.awt.*;

public class BankLabeledInputField extends JPanel {
    private JLabel label;
    private BankTextField textField;

    public BankLabeledInputField(String labelText, String placeholder) {
        this(labelText, placeholder, "primary");
    }

    public BankLabeledInputField(String labelText, String placeholder, String variant) {

        setLayout(new BorderLayout(0, 10));
        setOpaque(false); // Ensure the panel is transparent for custom backgrounds


        label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(new Color(60, 60, 60)); // Dark gray text for label

        textField = new BankTextField(placeholder, variant);

        add(label, BorderLayout.NORTH);
        add(textField, BorderLayout.CENTER);
    }

    public BankTextField getTextField() {
        return textField;
    }
}
