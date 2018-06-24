package com.example.iotserver.main.websocket;

import com.example.iotserver.main.models.*;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(
        value = "/websocket",
        encoders = { MessageEncoder.class },
        decoders = { MessageDecoder.class }
)

public class WebSocketServer {
    private static Set<Session> connected_clients = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void onMessage(MessageWS input, Session session)
            throws IOException, InterruptedException, EncodeException {
        MessageWS response = new MessageWS();
        System.out.println("RECEIVED MESSAGE" + input.getType());
        session.getBasicRemote().sendText("dasdasd");
        switch(input.getType()) {
            case "newuser":
                System.out.println("received newuser");
                break;
            case "refresh":
                System.out.println("received refresh");
                break;
        }
    }

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException{
        //connected_clients.add(session);
        System.out.println("CLIENT CONNECTED");
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        connected_clients.remove(session);
        System.out.println("CLIENT DISCONNECTED");
    }


}