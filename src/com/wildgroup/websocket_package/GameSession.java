package com.wildgroup.websocket_package;

import com.wildgroup.game_package.Game;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public abstract class GameSession {
    private String name;
    private List<Session> spectators;
    private List<Session> players;
    private Game roomGame;

    public GameSession(String name) {
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
    }

    public void AddSpectator(Session session) { this.spectators.add(session); }

    public void RemoveSpectator(Session session) {this.spectators.remove(session);}

    public void AddPlayers(Session session) { this.players.add(session); }

    public void RemovePlayers(Session session) {this.players.remove(session);}
}
