package com.wildgroup.websocket_package;

import com.wildgroup.deck_package.Card;
import com.wildgroup.game_package.Game;
import com.wildgroup.game_package.GameFunctionHandler;
import com.wildgroup.game_package.Pile;
import com.wildgroup.game_package.models.Player;
import com.wildgroup.message.Message;
import com.wildgroup.message.MessageMethods;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author Martin Juul Johansen
 * @date 29/01/2019
 */
public abstract class GameRoom {
    private String name;
    private List<Session> spectators;
    private List<Session> players;
    private Game roomGame;

    public GameRoom(String name) {
        this.spectators = new ArrayList<>();
        this.players = new ArrayList<>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Session> getSpectators() {
        return spectators;
    }

    public List<Session> getPlayers() {
        return players;
    }

    public Game getRoomGame() {
        return roomGame;
    }

    public void setRoomGame(Game roomGame) {
        this.roomGame = roomGame;
        this.roomGame.setHandler(new GameFunctionHandler() {
            @Override
            public void selectFromArray(String[] stringArray, int seatID) throws IOException {
                String str = "";
                for(int i=0;i<stringArray.length;i++)
                    str+=stringArray[i]+"(" + i + ")  ";
                players.get(seatID).getBasicRemote().sendText(new Message(MessageMethods.SELECTFROMARRAY, str).encode());
            }

            @Override
            public void messageDebug(String message) throws IOException {
                for (Session p: players) {
                    p.getBasicRemote().sendText(new Message(MessageMethods.GAMEMESSAGE, message).encode());
                }
            }

            @Override
            public void selectACard(int seatId) throws IOException {
                players.get(seatId).getBasicRemote().sendText(new Message(MessageMethods.REQUESTCARDSELECT, "Select a card").encode());
            }

            @Override
            public void dealCards(Collection<Pile> piles) {

            }

            @Override
            public void updatePiles(Collection<Pile> piles) throws IOException {
                for (Pile p: piles) {
                    if(p.getPileOwner() != 5) // TODO: THIS IS HARDCODED TO SUPPORT WHIST:: FIX LATER
                    players.get(p.getPileOwner()).getBasicRemote().sendText(new Message(MessageMethods.UPDATEPILES, p.getCardsInPile()).encode());
                }
            }

            @Override
            public void scoreUpdate(HashMap<Integer, Integer> scoreSet) {

            }

            @Override
            public void tricksUpdate(int[] tricksscore) {

            }
        });
    }

    public void AddSpectator(Session session) { this.spectators.add(session); }

    public void RemoveSpectator(Session session) {this.spectators.remove(session);}

    public void AddPlayers(Session session) { this.players.add(session); }

    public void RemovePlayers(Session session) {this.players.remove(session);}
}
