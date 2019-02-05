package com.wildgroup.deck_package;

/**
 * @author Marc Rohwedder Kær
 * @date 04-02-2019
 */
@SuppressWarnings("WeakerAccess")
public class StandardCard extends Card {

    private Suit suit;
    private int value;

    //region Description getter & setter
    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    //endregion

    public StandardCard(Suit suit, String name, int value) {
        super(name);
        this.suit = suit;
        this.value = value;
    }

}
