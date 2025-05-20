package com.codewithdemis;

import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
//            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        TestComponent.show();
    }
}