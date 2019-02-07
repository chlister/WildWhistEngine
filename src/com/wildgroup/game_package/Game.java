package com.wildgroup.game_package;

import com.wildgroup.deck_package.Deck;
import com.wildgroup.game_package.models.Player;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */
public abstract class Game implements Runnable {
    private final int MAX_PLAYER;
    private final int MIN_PLAYER;
    private Collection<Player> joinedPlayers;
    private int activePlayer;
    private GameState currentState;
    private String name;
    private Deck deck;
    private Collection<Pile> piles;
    private GameFunctionHandler handler;
    private HashMap<Integer, Integer> scoreSet;

    Game(int maxplayer, int minplayer) {
        MAX_PLAYER = maxplayer;
        MIN_PLAYER = minplayer;
        currentState = GameState.WAITING;
    }

    //region Getters & setters

    public HashMap<Integer, Integer> getScoreSet() {
        return scoreSet;
    }

    public void setScoreSet(HashMap<Integer, Integer> scoreSet) {
        this.scoreSet = scoreSet;
    }

    public void setHandler(GameFunctionHandler handler) {
        this.handler = handler;
    }

    GameFunctionHandler getHandler() {
        return handler;
    }

    public int getMAX_PLAYER() {
        return MAX_PLAYER;
    }

    public int getMIN_PLAYER() {
        return MIN_PLAYER;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public String getName() {
        return name;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Collection<Player> getJoinedPlayers() {
        return joinedPlayers;
    }

    public void setJoinedPlayers(Collection<Player> joinedPlayers) {
        this.joinedPlayers = joinedPlayers;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }

    public Collection<Pile> getPiles() {
        return piles;
    }
    public Pile getPile(int i) {
        for(Pile p: getPiles()){
            if(p.getPileOwner() == i)
                return p;
        }
        return null;
    }

    public void setPiles(Collection<Pile> piles) {
        this.piles = piles;
    }
    //endregion

    abstract void play() throws InterruptedException, IOException;

    abstract boolean winningCondition();

    abstract void initPiles();

    abstract void nextPlayer();
}
