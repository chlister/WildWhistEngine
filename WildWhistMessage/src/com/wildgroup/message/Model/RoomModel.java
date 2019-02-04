package com.wildgroup.message.Model;

public class RoomModel {
    private String name;
    private int maxPlayer;
    private int currentJoined;
    private String gameName;
    private boolean isSpectator;

    public RoomModel(String name, int maxPlayer, int currentJoined, String gameName, boolean isSpectator) {
        this.name = name;
        this.maxPlayer = maxPlayer;
        this.currentJoined = currentJoined;
        this.gameName = gameName;
        this.isSpectator = isSpectator;
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public int getCurrentJoined() {
        return currentJoined;
    }

    public String getGameName() {
        return gameName;
    }

    public boolean isSpectator() {
        return isSpectator;
    }
}
