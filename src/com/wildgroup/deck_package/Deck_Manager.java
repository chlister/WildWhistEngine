package com.wildgroup.deck_package;

/**
 * @author Marc Rohwedder Kær
 * @date 28-01-2019
 */
public class Deck_Manager {
    public static Deck getStandardDeck(){
        Deck d = new Deck();
        d.setCards(DeckFactory.getStandardDeck());
        return d;
    }
}
