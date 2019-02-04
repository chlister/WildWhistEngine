package com.wildgroup.message.Model;

import com.google.gson.internal.LinkedTreeMap;

/**
 * @author Martin Juul Johansen
 * @date 04/02/2019
 */

public class RoomModel {
    private String name;
    private int maxPlayer;
    private int currentJoined;
    private String gameName;
    private boolean isSpectator;

    public RoomModel(){

    }

    public RoomModel(String name, boolean isSpectator){
        this.name = name;
        this.isSpectator = isSpectator;
    }

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

    public static RoomModel Deserialize(LinkedTreeMap ltm){
        RoomModel u = new RoomModel();
        u.name = (String) ltm.get("name");
        u.gameName = (String) ltm.get("gameName");
        u.isSpectator = (boolean) ltm.get("isSpectator");
        return u;
    }
}
