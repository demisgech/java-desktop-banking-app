package com.codewithdemis.components;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class MainContentPanel extends JPanel {
    private CardLayout cardLayout;
    private final Map<String,JPanel> pages;

    public MainContentPanel(){
        this.cardLayout = new CardLayout();
        this.pages = new HashMap<>();

        setLayout(cardLayout);
//        setBackground(new Color(245,245,245));
        setBackground(new Color(68, 71, 90));
        setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    // Register a page with a unique name
    public void addPage(String name,JPanel page){
        if(!pages.containsKey(name)){
            pages.put(name,page);
            add(page,name);
        }
    }
//    Switch to a specific page by name
    public void showPage(String name){
        if(pages.containsKey(name)){
            cardLayout.show(this,name);
        }
    }

    public void removePage(String name) {
        if (pages.containsKey(name)) {
            remove(pages.get(name));
            pages.remove(name);
        }
    }

    public void clear(){
        pages.clear();
    }
}
