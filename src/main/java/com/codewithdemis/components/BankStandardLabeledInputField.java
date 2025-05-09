package com.codewithdemis.components;

public class BankStandardLabeledInputField extends BankLabeledInputField {
    public BankStandardLabeledInputField(String labelText, String placeholder) {
        super(labelText, placeholder);
    }

    public BankStandardLabeledInputField(String labelText, String placeholder, String variant) {
        super(labelText, placeholder, variant);
        // Basic input with no additional features
    }
}
