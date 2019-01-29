package com.wildgroup.websocket_package;

import com.wildgroup.api.*;
import com.wildgroup.api.Message;
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
    public void open(Session session){
    }

    @OnClose
    public void close(Session session) {
    }

    @OnError
    public void error(Session session, Throwable throwable) throws Throwable {
        throw throwable;
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        Message myMessage = new Message(message);
        switch (myMessage.getMethod()){
            case MessageMethods.CREATEUSER:
                // Do CreateUSER function;

                break;
            case MessageMethods.LOGIN:
                // Do Login function;
                break;
            default:
                try {
                    session.getBasicRemote().sendText("Massage not implemented");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
