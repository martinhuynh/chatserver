package com.martin.chatserver;

import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JPanel {

    private WebSocket webSocket;
    public String room;
    private JTextArea textArea = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private JTextField textField = new JTextField("");
    private JButton button = new JButton("Send");
    private GridBagConstraints gc = new GridBagConstraints();

    public MessagePanel(String room) {
        this.room = room;
        GridBagLayout gb = new GridBagLayout();
        setLayout(gb);
        setSize(600,600);
        initScrollPane();
        initTextField();
        initButton();

        setVisible(true);
    }

    public void run() {
        webSocket = new WebSocket(this);
    }

    private void initButton() {
        gc = new GridBagConstraints();
        gc.gridx = 5;
        gc.gridy = 5;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 0;
        gc.weighty = 0;
        gc.ipady = 10;
        add(button, gc);

        button.addActionListener(action -> {
            webSocket.send(textField.getText());
            textField.setText("");
        });
    }

    private void initTextField() {
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 5;
        gc.gridwidth = 4;
        gc.gridheight = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 0;
        gc.ipady = 10;
        add(textField, gc);

        textField.addActionListener(action -> {
            webSocket.send(textField.getText());
            textField.setText("");
        });
    }

    public void initScrollPane() {
        textArea.setLineWrap(true);
        textArea.setEditable(false);

        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 6;
        gc.gridheight = 5;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 1;
        add(scrollPane, gc);
    }

    public void userJoined(String user) {
        textArea.append(user + " Joined.\n");
    }

    public void userLeft(String user) {
        textArea.append(user + " Left.\n");
    }

    public void receivedMessage(String user, String msg) {
        textArea.append(user + ": " + msg + "\n");
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }
}
