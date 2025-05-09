package com.codewithdemis.components;

import java.awt.*;
import javax.swing.*;

public class BankLabeledInputFieldWithIcon extends BankLabeledInputField {
    private Icon icon;
    public BankLabeledInputFieldWithIcon(String labelText, String placeholder, Icon icon) {
        super(labelText, placeholder);
        this.icon = icon;
        setInputFieldIcon(icon);
    }

    public BankLabeledInputFieldWithIcon(String labelText, String placeholder, String variant, Icon icon) {
        super(labelText, placeholder, variant);
        this.icon = icon;
        setInputFieldIcon(icon);
    }

    public void setInputFieldIcon(Icon icon){
        // Wrap text field in a panel to hold both the icon and the input
        var panel = new JPanel(new BorderLayout(5,0));
        panel.setOpaque(false);

        // Create a label for the icon and set it in the text field
        var iconLabel = new JLabel(icon);
        panel.add(iconLabel,BorderLayout.WEST);
        panel.add(getTextField(),BorderLayout.CENTER);

        // Replace the default input field with this new one
        add(panel,BorderLayout.CENTER);
    }
}
