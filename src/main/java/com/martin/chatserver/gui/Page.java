package com.martin.chatserver.gui;

import javax.swing.*;
import java.awt.*;

abstract class Page extends JPanel {
    GridBagLayout gridBagLayout = new GridBagLayout();
    PageManager pageManager;

    public Page(PageManager pageManager) {
        this.pageManager = pageManager;
        init();
    }

    private void init() {
        setLayout(gridBagLayout);
        setVisible(true);
    }
}
