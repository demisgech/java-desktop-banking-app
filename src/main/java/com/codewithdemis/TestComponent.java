package com.codewithdemis;

import com.codewithdemis.frames.MainFrame;
import javax.swing.*;

public class TestComponent {
    public static void show() {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}

