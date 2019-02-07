package com.wildgroup.api;

import com.wildgroup.message.Message;
import com.wildgroup.message.Model.CardModel;
import com.wildgroup.message.Model.RoomModel;
import com.wildgroup.message.Model.UserModel;
import com.wildgroup.message.MessageMethods;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Martin Juul Johansen
 * @date 29/01/2019
 */

public class Client {
    private WebsocketClientEndpoint clientEndPoint;

    public Client(String url){
        try {
            if(url.isEmpty())
                clientEndPoint = new WebsocketClientEndpoint(new URI(url));
            else
                clientEndPoint = new WebsocketClientEndpoint(new URI("ws://localhost:8080/WildWhistEngine_war_exploded/ws"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        // add listener
        clientEndPoint.addMessageHandler(new MessageHandler() {
            @Override
            public void handleMessage(String message) {
                System.out.println(message);
            }

            @Override
            public void handleSelectCards(ArrayList<CardModel> cards) {

            }
        });
    }

    public void addNewMessageHandler(MessageHandler mh){
        clientEndPoint.addMessageHandler(mh);
    }


    public void login(String email, String password){
        UserModel userModel = new UserModel(0, "", email, password, new Date());
        Message message = new Message(MessageMethods.LOGIN, userModel);
        clientEndPoint.sendMessage(message.encode());
    }

    public void createUser(String email, String username, String password)//TODO: Add birthday to method;
    {
        UserModel userModel = new UserModel(0, username, email, password, new Date());
        Message message = new Message(MessageMethods.CREATEUSER, userModel);
        clientEndPoint.sendMessage(message.encode());
    }

    public void createRoom(String name){
        RoomModel roomModel = new RoomModel(name, 2, 0, "Test", false);
        Message message = new Message(MessageMethods.CREATEROOM, roomModel);
        clientEndPoint.sendMessage(message.encode());
    }

    public  void joinRoom(String name){
        RoomModel roomModel = new RoomModel(name, 2, 0, "Test", false);
        Message message = new Message(MessageMethods.JOINROOM, roomModel);
        clientEndPoint.sendMessage(message.encode());
    }

    public void selectArrayResponse(int i){
        clientEndPoint.sendMessage(new Message(MessageMethods.SELECTFROMARRAY, i).encode());
    }

    public void selectCardResponse(CardModel cm){
        clientEndPoint.sendMessage(new Message(MessageMethods.SELECTCARDS, cm).encode());
    }
}
