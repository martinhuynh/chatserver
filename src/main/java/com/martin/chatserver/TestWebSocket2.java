package com.martin.chatserver;

import com.martin.chatserver.controller.model.Message;
import com.martin.chatserver.controller.model.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class TestWebSocket2 {
    final static String name = "Ass";
    final static String room = "test";
    final static String URL = "ws://73.42.143.17:8080/ws";
    public static void main(String[] args) throws ExecutionException, InterruptedException {
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
                session.subscribe("/room/" + room, this);
                Message msg = new Message();
                msg.setStatus(Status.JOIN);
                msg.setSenderName(name);
                session.send("/room/" + room, msg);
                logger.info("You joined.");
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                Message msg = (Message) payload;
                switch ((Status) msg.getStatus()) {
                    case JOIN:
                        if (msg.getSenderName().equals(name)) break;
                        logger.info(msg.getSenderName() + " Joined.");
                        break;
                    case LEAVE:
                        logger.info(msg.getSenderName() + " Left.");
                        break;
                    case MESSAGE:
                        logger.info(msg.getSenderName() + ": " + msg.getMessage());
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
                msg.setSenderName(name);
                session.send("/room/" + room, msg);
            }

            @Override
            public void handleTransportError(StompSession session, Throwable exception) {
                Message msg = new Message();
                msg.setStatus(Status.LEAVE);
                msg.setSenderName(name);
                session.send("/room/" + room, msg);
            }
        };
        ListenableFuture<StompSession> session = stompClient.connect(URL, sessionHandler);
        Scanner scanner = new Scanner(System.in);
        Message message = new Message();
        String input = scanner.nextLine();
        while (!input.equals("quit")) {
            message.setSenderName(name);
            message.setMessage(input);
            message.setStatus(Status.MESSAGE);
            session.get().send("/app/room/" + room, message);
            input = scanner.nextLine();
        }
        new Scanner(System.in).nextLine();
    }
}
