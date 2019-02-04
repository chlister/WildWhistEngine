package com.wildgroup.deck_package;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Marc Rohwedder Kær
 * @date 04-02-2019
 */
public class Deck implements Shufferable {
    private Collection<Card> deck;

    public Collection<Card> getDeck() {
        return deck;
    }

    public void setDeck(Collection<Card> deck) {
        this.deck = deck;
    }

    @Override
    public void shuffle() {
        Collections.shuffle((ArrayList)this.deck);
    }

    public Deck() {
        this.deck = new ArrayList<>();
    }
}
