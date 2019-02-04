package com.wildgroup.deck_package;

import java.util.Collection;

/**
 * @author Marc Rohwedder Kær
 * @date 04-02-2019
 */
public class DeckFactory {



    public static Collection<Card> getStandardDeck(){
        Deck d = new Deck();
        String name, type;
        int value;

        for (int i = 0; i < Suit.values().length; i++){
            for (int j = 1; j < 14; j++){
                type = Suit.values()[i].toString();
                switch (j){
                    case 1:
                        name = "Ace";
                        break;
                    case 11:
                        name = "Jack";
                        break;
                    case 12:
                        name = "Queen";
                        break;
                    case 13:
                        name = "King";
                        break;
                        default:
                            name = "" + j;
                }
                value = j;

                d.getCards().add(new StandardCard(Suit.values()[i], name + " of " + type, value));

            }
        }
        return d.getCards();
    }
}
