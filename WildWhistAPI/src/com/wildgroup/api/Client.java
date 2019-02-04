package com.wildgroup.api;

import com.wildgroup.message.Message;
import com.wildgroup.message.Model.RoomModel;
import com.wildgroup.message.Model.UserModel;
import com.wildgroup.message.MessageMethods;

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

    public void addNewMessageHandler(WebsocketClientEndpoint.MessageHandler mh){
        clientEndPoint.addMessageHandler(mh);
    }


    public void login(String email, String password){
        UserModel userModel = new UserModel("", email, password, new Date());
        Message message = new Message(MessageMethods.LOGIN, userModel);
        clientEndPoint.sendMessage(message.encode());
    }

    public void createUser(String email, String username, String password)//TODO: Add birthday to method;
    {
        UserModel userModel = new UserModel(username, email, password, new Date());
        Message message = new Message(MessageMethods.CREATEUSER, userModel);
        clientEndPoint.sendMessage(message.encode());
    }

    public void createRoom(String name){
        RoomModel roomModel = new RoomModel(name, 2, 0, "Test", false);
        Message message = new Message(MessageMethods.JOINROOM, roomModel);
        clientEndPoint.sendMessage(message.encode());
    }
}
