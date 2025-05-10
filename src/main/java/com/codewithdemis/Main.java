package com.codewithdemis;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(()->{
            TestComponent.show();
        });
    }
}