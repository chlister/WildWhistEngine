package com.wildgroup.websocket_package;

import com.google.gson.Gson;
import com.wildgroup.message.Message;
import com.wildgroup.message.MessageMethods;
import com.wildgroup.message.Model.RoomModel;
import com.wildgroup.message.Model.ToastLevel;
import com.wildgroup.message.Model.ToastModel;
import com.wildgroup.user_package.models.User;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Martin Juul Johansen
 * @date 29/01/2019
 */

@ServerEndpoint("/ws")
public class ServerEndPoint {
    private ArrayList<GameSession> gameSessions = new ArrayList<>();
    private ArrayList<User> loginUsers = new ArrayList<>();

    @OnOpen
    public void open(Session session){
        // Api-Client has a OnOpen method, so nothing needs to be done here.
    }

    @OnClose
    public void close(Session session) {
        String loginMail = (String)session.getUserProperties().get(SessionPropertie.userMail);
        if(loginMail != null) {
            if (!loginMail.isEmpty()) {
                for (User u : loginUsers) {
                    if (u.getEmail().equals(loginMail)) {
                        loginUsers.remove(u); // removes user from loginUser list.
                        break;
                    }
                }
            }
        }
        LeaveRoomMethod(session);
    }

    @OnError
    public void error(Session session, Throwable throwable) throws Throwable {
        //TODO: Inform API-client of error
        throw  throwable;
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        Message myMessage = new Message(message); // this Constructor decode the string message from Json format to a Message object.
        if(checkIfLogin(session, myMessage)){
            // TODO: Call method that handlers all other MessageMethods
        }
        else{
            //TODO: User is not log in response
        }
    }

    private boolean checkIfLogin(Session session, Message message) {
        if(message.getMethod() == MessageMethods.CREATEUSER){
            User newUser = (User)message.getMobject();

            // TODO:Call the Create User method and handel that creation response.
            //  USER ALREADY EXIST
            //  USER WAS CREATED
            //  USER COULD NOT BE CREATE (other reason)

            return false; // no other codes need to be executed
        }
        else if(message.getMethod() == MessageMethods.LOGIN){
            try {
                session.getBasicRemote().sendText("Server: Login works "); //TODO: Delete this line
            } catch (IOException e) {
                e.printStackTrace();
            }
            User loginUser = (User)message.getMobject();

            // TODO:Call the read User method and handel response.
            //  USER DOES NOT EXIST
            //  USER PASSWORD IS WRONG
            //  USER EXITS (OK / Continue)

            for(User u : loginUsers) {
                if (u.getEmail().equals(loginUser.getEmail())) {
                    // TODO:USER IS ALREADY LOGIN
                }
            }
            // Here we set session properties
            session.getUserProperties().put(SessionPropertie.userMail, loginUser.getEmail()); // this is needed to remove user from loginUsers list when Session is closed.

            return false; // no other codes need to be executed
        }

        String loginMail = (String)session.getUserProperties().get(SessionPropertie.userMail);
        if(loginMail != null) {
            if (!loginMail.isEmpty()) {
                for (User u : loginUsers) {
                    if (u.getEmail().equals(loginMail)) {
                        return true; // user is found in loginUsers list. And method can now be executed.
                    }
                }
            }
        }
        return false;
    }

    private void handleMessageMethods(Session session, Message message){
        switch (message.getMethod()){
            case MessageMethods.CREATEROOM:
                //TODO: Call CreateRoom Method
                break;
            case MessageMethods.JOINROOM:
                //TODO: Call JoinRoom method
                break;
            case MessageMethods.LEAVEROOM:
                //TODO: Call LeaveRoom method
                break;
            default:
                //TODO: Method not found error
                break;
        }
    }

    private void CreateRoomMethod(Session session, Message message){
        RoomModel room = (RoomModel) message.getMobject();
        for (GameSession gs : gameSessions){
            if(gs.getName().equals(room.getName()));
            {
                //TODO: ROOM ALREADY EXITS RESPONSE
                return;
            }
        }
        Room newRoom = new Room(room.getName());
        newRoom.AddPlayers(session);
        gameSessions.add(newRoom);
        // TODO: ROOM HAS BEEN CREATED RESPONSE
    }

    private void JoinRoomMethod(Session session, Message message) throws IOException {
        RoomModel room = (RoomModel) message.getMobject();
        for (GameSession gs : gameSessions) {
            if(gs.getName().equals(room.getName()));
            {
                LeaveRoomMethod(session); // Check if session is in a room and Leaves that room
                session.getUserProperties().put(SessionPropertie.inRoom, room.getName());
                if(room.isSpectator()) {
                    gs.AddSpectator(session);
                    session.getUserProperties().put(SessionPropertie.inRoomAsSpectator, true);
                }
                else{
                    gs.AddSpectator(session);
                    session.getUserProperties().put(SessionPropertie.inRoomAsSpectator, false);
                }
                // TODO JOIN ROOM SUCCESS REPSONSE
                return;
            }
        }
        session.getBasicRemote().sendText(new Message(MessageMethods.TOAST, new ToastModel(ToastLevel.ERROR, "Error when trying to join room")).encode());
    }

    private void LeaveRoomMethod(Session session){
        String inRoom = (String)session.getUserProperties().get(SessionPropertie.inRoom);
        boolean inRoomAsSpectator = (boolean)session.getUserProperties().get(SessionPropertie.inRoomAsSpectator);
        if(inRoom != null) {
            if (!inRoom.isEmpty()) {
                session.getUserProperties().remove(SessionPropertie.inRoom);
                for (GameSession gs : gameSessions){
                    if(gs.getName().equals(inRoom)){
                        if(inRoomAsSpectator)
                            gs.RemoveSpectator(session);
                        else {
                            gs.RemovePlayers(session);
                            //TODO: call a Player has left room method & Reponse to client that room has been left
                        }
                        session.getUserProperties().remove(SessionPropertie.inRoomAsSpectator);
                        return;
                    }
                }
            }
        }
    }
}
