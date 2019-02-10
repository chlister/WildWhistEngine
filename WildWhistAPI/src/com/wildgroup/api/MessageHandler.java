package com.wildgroup.api;

import com.wildgroup.message.Model.CardModel;

import java.util.ArrayList;


/**
 * @author Martin Juul Johansen
 * @date 06/02/2019
 */
public  interface MessageHandler {
    void handleMessage(String message);
    void handleSelectCards(ArrayList<CardModel> cards);
}