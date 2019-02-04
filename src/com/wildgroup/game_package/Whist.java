package com.wildgroup.game_package;

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

    Suit trump;
    int currentCall;
    int currentDealer;

    public Whist() {
        super(4, 4);

    }

    @Override
    void play() {
// TODO: make work
        
        System.out.println("Doesn't work");
    }

    @Override
    public void nextDealer() {

    }
    public void setTrump(Suit t){
        trump = t;
    }
    void distributePoints(){

    }
    void setAceValue(){

    }
}
