package com.wildgroup.websocket_package;

import com.wildgroup.game_package.Game;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public class Room extends GameSession{
    private RoomFunctionHandler handler;

    public Room(String name) {
        super(name);
    }
}
