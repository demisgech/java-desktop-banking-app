package com.codewithdemis.components;

import java.awt.*;
import javax.swing.*;

public class NumberLabeledInputField extends JPanel {
   private JLabel label;
    private BankNumberField numberField;

    public NumberLabeledInputField(String labelText, String placeholder) {
        this(labelText, placeholder, "primary");
    }

    public NumberLabeledInputField(String labelText, String placeholder, String variant) {

        setLayout(new BorderLayout(0, 10));
        setOpaque(false); // Ensure the panel is transparent for custom backgrounds


        label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(new Color(60, 60, 60)); // Dark gray text for label

        numberField = new BankNumberField(placeholder, variant);

        add(label, BorderLayout.NORTH);
        add(numberField, BorderLayout.CENTER);
    }

    public BankNumberField getNumberField() {
        return numberField;
    }
}
