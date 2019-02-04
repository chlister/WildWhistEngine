package com.wildgroup.websocket_package;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.wildgroup.db_package.UserRepository;
import com.wildgroup.db_package.dbModels.UserEntity;
import com.wildgroup.message.Message;
import com.wildgroup.message.MessageMethods;
import com.wildgroup.message.MessageResponse;
import com.wildgroup.message.Model.RoomModel;
import com.wildgroup.message.Model.ToastLevel;
import com.wildgroup.message.Model.ToastModel;
import com.wildgroup.message.Model.UserModel;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

/**
 * @author Martin Juul Johansen
 * @date 29/01/2019
 */

@ServerEndpoint("/ws")
public class ServerEndPoint {
    private ArrayList<GameSession> gameSessions = new ArrayList<>();
    private ArrayList<UserEntity> loginUsers = new ArrayList<>();

    @OnOpen
    public void open(Session session){
        // Api-Client has a OnOpen method, so nothing needs to be done here.
    }

    @OnClose
    public void close(Session session) throws IOException {
        String loginMail = (String)session.getUserProperties().get(SessionPropertie.userMail);
        if(loginMail != null) {
            if (!loginMail.isEmpty()) {
                for (UserEntity u : loginUsers) {
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
    public void handleMessage(String message, Session session) throws IOException {
        Message myMessage = new Message(message); // this Constructor decode the string message from Json format to a Message object.
        if(checkIfLogin(session, myMessage)){
            handleMessageMethods(session, myMessage);
        }
        else{
            //TODO: User is not log in response
        }
    }

    private boolean checkIfLogin(Session session, Message message) throws IOException {
        if(message.getMethod() == MessageMethods.CREATEUSER){
            UserModel newUser = UserModel.Deserialize((LinkedTreeMap) message.getMobject());

            UserRepository ur = new UserRepository();


            UserEntity u = new UserEntity(
                    newUser.getName(),
                    "",
                    "",
                    newUser.getPassword(),
                    newUser.getEmail(),
                    (newUser.getBirthday()),
                    0);

            if(ur.selectUser(u.getEmail()) != null) // checks if user exist
            {
                session.getBasicRemote().sendText(new Message(MessageMethods.CREATEUSER, MessageResponse.CREATEUSER_ALREADY_EXITS).encode());
            }
            else{
                int rows = ur.insertBuilder(u);
                if(rows > 0)
                    session.getBasicRemote().sendText(new Message(MessageMethods.CREATEUSER, MessageResponse.CREATEUSER_SUCCESS).encode());
                else
                    session.getBasicRemote().sendText(new Message(MessageMethods.CREATEUSER, MessageResponse.CREATEUSER_FAILED).encode());
            }
            return false; // no other codes need to be executed
        }
        else if(message.getMethod() == MessageMethods.LOGIN){
            UserModel loginUser =  UserModel.Deserialize((LinkedTreeMap) message.getMobject());

            UserRepository ur;
            ur = new UserRepository();

            UserEntity u = new UserEntity(
                    loginUser.getName(),
                    "",
                    "",
                    loginUser.getPassword(),
                    loginUser.getEmail(),
                    (loginUser.getBirthday()),
                    0);
            UserEntity user = ur.selectUser(u.getEmail());
                for(UserEntity _u : loginUsers) {
                    if (_u.getEmail().equals(loginUser.getEmail())) { // Checks if user is already log in.
                        session.getBasicRemote().sendText(new Message(MessageMethods.LOGIN, MessageResponse.LOGIN_ALREADY_LOGIN).encode());
                        return false;
                    }
                }

                if(user != null) // checks if user exist
                {
                    if(user.getPassword().equals(loginUser.getPassword())) // check if user password match
                        session.getBasicRemote().sendText(new Message(MessageMethods.LOGIN, MessageResponse.LOGIN_SUCCESS).encode());
                    else {
                        session.getBasicRemote().sendText(new Message(MessageMethods.LOGIN, MessageResponse.LOGIN_PASSWORD_WRONG).encode());
                        return false;
                    }

                }
                else{
                    session.getBasicRemote().sendText(new Message(MessageMethods.LOGIN, MessageResponse.LOGIN_FAILED).encode());
                    return false;
                }
            // Here we set session properties
            session.getUserProperties().put(SessionPropertie.userMail, loginUser.getEmail()); // this is needed to remove user from loginUsers list when Session is closed.

            return false; // no other codes need to be executed
        }

        String loginMail = (String)session.getUserProperties().get(SessionPropertie.userMail);
        if(loginMail != null) {
            if (!loginMail.isEmpty()) {
                for (UserEntity u : loginUsers) {
                    if (u.getEmail().equals(loginMail)) {
                        return true; // user is found in loginUsers list. And method can now be executed.
                    }
                }
            }
        }
        return false;
    }

    private void handleMessageMethods(Session session, Message message) throws IOException {
        switch (message.getMethod()){
            case MessageMethods.CREATEROOM:
                CreateRoomMethod(session, message);
                break;
            case MessageMethods.JOINROOM:
                JoinRoomMethod(session, message);
                break;
            case MessageMethods.LEAVEROOM:
                JoinRoomMethod(session, message);
                break;
            default:
                //TODO: Method not found error
                break;
        }
    }

    private void CreateRoomMethod(Session session, Message message) throws IOException {
        RoomModel room = (RoomModel) message.getMobject();
        for (GameSession gs : gameSessions){
            if(gs.getName().equals(room.getName()));
            {
                session.getBasicRemote().sendText(new Message(MessageMethods.CREATEROOM, MessageResponse.CREATEUSER_ALREADY_EXITS).encode());
                return;
            }
        }
        Room newRoom = new Room(room.getName());
        newRoom.AddPlayers(session);
        gameSessions.add(newRoom);
        session.getBasicRemote().sendText(new Message(MessageMethods.CREATEROOM, MessageResponse.CREATEUSER_SUCCESS).encode());
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
                session.getBasicRemote().sendText(new Message(MessageMethods.JOINROOM, MessageResponse.LOGIN_SUCCESS).encode()); // JOIN SUCCESS
                return;
            }
        }
        session.getBasicRemote().sendText(new Message(MessageMethods.JOINROOM, MessageResponse.LOGIN_FAILED).encode()); // JOIN FAILED
    }

    private void LeaveRoomMethod(Session session) throws IOException {
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
                            for (Session spectator: gs.getSpectators()) {
                                spectator.getBasicRemote().sendText(new Message(MessageMethods.TOAST, new ToastModel(ToastLevel.INFO,  "Somebody has left the room")).encode()); //TODO: Change message
                            }
                            for (Session player: gs.getPlayers()) {
                                player.getBasicRemote().sendText(new Message(MessageMethods.TOAST, new ToastModel(ToastLevel.INFO,  "Somebody has left the room")).encode()); //TODO: Change message
                            }
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
