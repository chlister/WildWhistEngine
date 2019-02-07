package com.wildgroup.api;

import com.wildgroup.message.Model.CardModel;

import java.util.ArrayList;

public  interface MessageHandler {
    void handleMessage(String message);
    void handleSelectCards(ArrayList<CardModel> cards);
}