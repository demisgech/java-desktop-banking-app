package com.codewithdemis;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(new FlatLightLaf());
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(()->{
            TestComponent.show();
        });
    }
}