package com.wildgroup.deck_package;

/**
 * @author Marc Rohwedder Kær
 * @date 04-02-2019
 */
@SuppressWarnings("WeakerAccess")
public class StandardCard extends Card {

    private Suit suit;
    private byte value;

    //region Description
    public Suit getSuit() {
        return suit;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }
    //endregion

    public StandardCard(Suit suit, String name) {
        super(name);

    }

}
