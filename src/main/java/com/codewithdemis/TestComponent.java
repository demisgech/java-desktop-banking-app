package com.codewithdemis;

import com.codewithdemis.components.Navbar;
import java.awt.*;
import javax.swing.*;

public class TestComponent {
    public static void show() {
        JFrame frame = new JFrame("Banking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(800, 600);

        var navbar = new Navbar("Home","About","Services","Login");
        frame.add(navbar);

        frame.setVisible(true);
    }
}

