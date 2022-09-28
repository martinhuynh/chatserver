package com.martin.chatserver.gui;

import javax.swing.*;

public class Start extends JFrame {
    final PageManager pageManager = new PageManager();

    public Start() {
        add(pageManager);
        ChatPage chatPage = new ChatPage(pageManager);
        pageManager.addPage(Pages.CHAT, chatPage);
        LoginPage loginPage = new LoginPage(pageManager);
        pageManager.addPage(Pages.LOGIN, loginPage);
        pageManager.setPage(Pages.LOGIN);
        RegisterPage regPage = new RegisterPage(pageManager);
        pageManager.addPage(Pages.REGISTER, regPage);
        init();
    }

    public static void main(String[] args) {
        new Start();
    }

    public void init() {
        // setLayout(new GridLayout(4,4));
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        setSize(1280, 720);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Chatroom");
    }
}
