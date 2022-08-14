package com.martin.chatserver;

import com.martin.chatserver.controller.model.Message;
import com.martin.chatserver.controller.model.Status;
import com.martin.chatserver.gui.ChatPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WebSocket {
    final static String IP = "localhost";
    final static String URL = "ws://" + IP + ":8080/ws";
    private MessagePanel messagePanel;
    private ListenableFuture<StompSession> session;
    public WebSocket(MessagePanel messagePanel) {
        this.messagePanel = messagePanel;
        init();
    }

    private void init() {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompSessionHandler sessionHandler = new StompSessionHandler() {
            private Logger logger = LogManager.getLogger(StompSessionHandler.class);
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Message.class;
            }

            @Override
            public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                session.subscribe("/room/" + messagePanel.room, this);
                Message msg = new Message();
                msg.setStatus(Status.JOIN);
                msg.setSenderName(ChatPage.name);
                session.send("/room/" + messagePanel.room, msg);
                logger.info("You joined.");
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                Message msg = (Message) payload;
                switch ((Status) msg.getStatus()) {
                    case JOIN:
                        messagePanel.userJoined(msg.getSenderName());
                        logger.info(msg.getSenderName() + " Joined. " + messagePanel.room);
                        break;
                    case LEAVE:
                        messagePanel.userLeft(msg.getSenderName());
                        logger.info(msg.getSenderName() + " Left. " + messagePanel.room);
                        break;
                    case MESSAGE:
                        messagePanel.receivedMessage(msg.getSenderName(), msg.getMessage());
                        logger.info(messagePanel.room + " " + msg.getSenderName() + ": " + msg.getMessage());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                logger.error("Exception", exception);
                Message msg = new Message();
                msg.setStatus(Status.LEAVE);
                msg.setSenderName(ChatPage.name);
                session.send("/room/" + messagePanel.room, msg);
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                Message msg = new Message();
                msg.setStatus(Status.LEAVE);
                msg.setSenderName(ChatPage.name);
                session.send("/room/" + messagePanel.room, msg);
            }
        };
        StompHeaders connectHeaders = new StompHeaders();
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.put("username", Arrays.asList("martinhuynh@rocketmail.com"));
        headers.put("password", Arrays.asList("12345678"));
        connectHeaders.add("username", "martinhuynh@rocketmail.com");
        connectHeaders.add("password", "12345678");
//        stompClient.connect(WS_HOST_PORT, new WebSocketHttpHeaders(), connectHeaders, new MySessionHandler());
        session = stompClient.connect(URL, headers, connectHeaders, sessionHandler);

    }

    public void send(String text) {
        Message msg = new Message();
        msg.setSenderName(ChatPage.name);
        msg.setStatus(Status.MESSAGE);
        msg.setMessage(text);
        try {
            session.get().send("/room/" + messagePanel.room, msg);
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }
}
