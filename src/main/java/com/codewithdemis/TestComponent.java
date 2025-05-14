package com.codewithdemis;

import com.codewithdemis.frames.MainFrame;
import com.codewithdemis.models.Account;
import javax.swing.*;

public class TestComponent {
    public static void show() {
        SwingUtilities.invokeLater(MainFrame::new);
        var account = new Account(1,1,"123",10);

    }
}

