package com.martin.chatserver.gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PageManager extends JPanel {
    final Map<Pages, JPanel> pages = new HashMap<>();
    Pages currentPage = Pages.LOGIN;

    public PageManager() {
        init();
    }

    private void init() {
        setLayout(new GridLayout());
    }

    public void addPage(Pages page, JPanel panel) {
        pages.put(page, panel);
    }

    public void setPage(Pages page) {
        currentPage = page;
        removeAll();
        add(pages.get(currentPage));
        validate();
    }
}
