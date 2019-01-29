package com.wildgroup.api;

import com.sun.xml.internal.bind.v2.TODO;
import com.wildgroup.api.Model.UserModel;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

/**
 * @author Martin Juul Johansen
 * @date 29/01/2019
 */

public class Client {
    private WebsocketClientEndpoint clientEndPoint;

    public Client(){
        try {
            clientEndPoint = new WebsocketClientEndpoint(new URI("ws://localhost:8080/WildWhistEngine_war_exploded/ws"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        // add listener
        clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
            public void handleMessage(String message) {
                System.out.println(message);
            }
        });
    }

    public void Login(String email, String username, String password){
        UserModel userModel = new UserModel(username, email, password, new Date()); //TODO: Add birthday to method;
        Message message = new Message(MessageMethods.LOGIN, userModel);
        clientEndPoint.sendMessage(message.encode());
    }
}
