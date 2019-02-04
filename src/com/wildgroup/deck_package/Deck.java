package com.wildgroup.deck_package;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Marc Rohwedder Kær
 * @date 04-02-2019
 */
public class Deck implements Shufferable {
    private Collection<Card> cards;

    public Collection<Card> getCards() {
        return cards;
    }

    public void setCards(Collection<Card> cards) {
        this.cards = cards;
    }

    @Override
    public void shuffle() {
        Collections.shuffle((ArrayList)this.cards);
    }

    public Deck() {
        this.cards = new ArrayList<>();
    }
}
