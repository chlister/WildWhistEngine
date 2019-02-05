package com.wildgroup.game_package.models;

/**
 * @author Marc Rohwedder Kær
 * @date 04-02-2019
 */
public class Player {
    String name;
    int id;

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
