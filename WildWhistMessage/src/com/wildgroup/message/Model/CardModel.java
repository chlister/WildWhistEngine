package com.wildgroup.message.Model;

import com.google.gson.internal.LinkedTreeMap;

public class CardModel {
    private String name;
    private int value;
    private int suit;

    public CardModel(String name, int value, int suit) {
        this.name = name;
        this.value = value;
        this.suit = suit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSuit() {
        return suit;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public static CardModel Deserialize(LinkedTreeMap ltm){
        double d = (double)ltm.get("value");
        double d2 = (double)ltm.get("suit");
        CardModel u = new CardModel(
                (String) ltm.get("name"),
                (int) d,
                (int) d2
        );
        return u;
    }
}
