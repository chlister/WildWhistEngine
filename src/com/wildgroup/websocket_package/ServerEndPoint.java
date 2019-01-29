package com.wildgroup.websocket_package;

import com.wildgroup.api.*;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author Martin Juul Johansen
 * @date 29/01/2019
 */

@ServerEndpoint("/ws")
public class ServerEndPoint {

    @OnOpen
    public void open(Session session) throws IOException, EncodeException {
    }

    @OnClose
    public void close(Session session) throws IOException, EncodeException {
    }

    @OnMessage
    public void handleMessage(String s_message, Session session) throws IOException, EncodeException {
        Message message = new Message(s_message);
        switch (message.getMethod()){
            case MessageMethods.CREATEUSER:
                // Do CreateUSER function;

                break;
            case MessageMethods.LOGIN:
                // Do Login function;
                break;
            default:
                session.getBasicRemote().sendText("Massage not implemented");
                break;
        }
    }
}
