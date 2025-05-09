package com.codewithdemis.components;

import java.awt.*;
import javax.swing.*;

public class DateLabeledInputField extends JPanel {
    private JLabel label;
    private BankDateField dateField;

    public DateLabeledInputField(String labelText, String placeholder) {
        this(labelText, placeholder, "primary");
    }

    public DateLabeledInputField(String labelText, String placeholder, String variant) {

        setLayout(new BorderLayout(0, 10));
        setOpaque(false); // Ensure the panel is transparent for custom backgrounds


        label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(new Color(60, 60, 60)); // Dark gray text for label

        dateField = new BankDateField(placeholder, variant);

        add(label, BorderLayout.NORTH);
        add(dateField, BorderLayout.CENTER);
    }

    public BankDateField getDateField() {
        return dateField;
    }
}
