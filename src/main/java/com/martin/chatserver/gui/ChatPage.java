package com.martin.chatserver.gui;

import com.martin.chatserver.MessagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChatPage extends Page {
    private JTextField nameField, groupField;
    JButton setName, createGroup;
    final JTabbedPane tabbedPane = new JTabbedPane();
    private boolean start = false;
    public static String name = "";
    private PageManager pageManager;

    public ChatPage(PageManager pageManager) {
        super(pageManager);

        MessagePanel panel = new MessagePanel("Public Room");
        tabbedPane.add(panel.room, panel);

        initName();
        initGroup();
        initPane();
    }

    public void initName() {
        GridBagConstraints gc = new GridBagConstraints();

        ActionListener run = action -> {
            if (nameField.getText().equals("")) return;
            name = nameField.getText();
            if (!start) {
                ((MessagePanel) tabbedPane.getComponentAt(0)).run();
                start = true;
            }
        };

        nameField = new JTextField();
        nameField.addActionListener(run);
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 2;
        gc.gridheight = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 0.1;
        gc.weighty = 0;
        gc.ipadx = 10;
        gc.ipady = 10;
        add(nameField, gc);

        gc = new GridBagConstraints();
        setName = new JButton("Set Name");
        setName.addActionListener(run);
        gc.gridx = 2;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.ipadx = 10;
        gc.ipady = 10;
        add(setName, gc);
    }

    public void initGroup() {
        GridBagConstraints gc = new GridBagConstraints();

        ActionListener run = action -> {
            if (groupField.getText().equals("")) return;
            MessagePanel panel = new MessagePanel(groupField.getText());
            tabbedPane.add(groupField.getText(), panel);
            panel.run();
            groupField.setText("");
        };

        groupField = new JTextField();
        groupField.addActionListener(run);
        gc.gridx = 3;
        gc.gridy = 0;
        gc.gridwidth = 2;
        gc.gridheight = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 0.1;
        gc.weighty = 0;
        gc.ipadx = 10;
        gc.ipady = 10;
        add(groupField, gc);

        gc = new GridBagConstraints();
        createGroup = new JButton("Create Group");
        createGroup.addActionListener(run);
        gc.gridx = 5;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.ipadx = 10;
        gc.ipady = 10;
        add(createGroup, gc);
    }

    public void initPane() {
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 0;
        gc.gridy = 2;
        gc.gridwidth = 6;
        gc.gridheight = 6;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.ipady = 0;
        add(tabbedPane, gc);
    }

}
