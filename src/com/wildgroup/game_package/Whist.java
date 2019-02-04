package com.wildgroup.game_package;

import com.wildgroup.deck_package.Card;
import com.wildgroup.deck_package.StandardCard;
import com.wildgroup.deck_package.Suit;

/**
 * @author Marc Rohwedder Kær
 * @date 04-02-2019
 */
public class Whist extends Game implements DealerToken {
    enum calls {
        GRANDI,
        SOL,
        GRAND,
        PAS
    }

    private Suit trump;
    private int currentCall;
    private int currentDealer;

    public Whist() {
        super(4, 4);

    }

    //region getter & setter
    public Suit getTrump() {
        return trump;
    }

    public int getCurrentCall() {
        return currentCall;
    }

    public int getCurrentDealer() {
        return currentDealer;
    }
    //endregion

    @Override
    void play() {
// TODO: make work

        System.out.println("Doesn't work");
    }

    @Override
    public void nextDealer() {
        currentDealer++;
        if (currentDealer >= getMAX_PLAYER())
            currentDealer = 0;
    }

    public void setTrump(Suit t) {
        trump = t;
    }

    void distributePoints() {

    }

    void setAceValue(boolean isSol) {
        // if ace is value 1
        for (Card c : this.getDeck().getCards()) {
            if (((StandardCard) c).getValue() == 1 || ((StandardCard) c).getValue() == 14)
                if (isSol)
                    ((StandardCard) c).setValue(1);
                else
                    ((StandardCard) c).setValue(14);
        }
    }
}
