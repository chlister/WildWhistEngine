package com.wildgroup.game_package;

import com.wildgroup.deck_package.Card;
import com.wildgroup.deck_package.DeckFactory;
import com.wildgroup.deck_package.StandardCard;
import com.wildgroup.deck_package.Suit;
import com.wildgroup.game_package.models.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;

/**
 * @author Marc Rohwedder Kær, Dennis F. J. Dupont
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
        this.setDeck(DeckFactory.getStandardDeck());

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
        setScoreSet(initScore());
        while (winningCondition()) {
            this.getDeck().shuffle();
            deal();
        }
        System.out.println("Doesn't work");
    }

    private void deal() {
        int size = getDeck().getCards().size();
        for (int i = 0 ; i < size; i++){
            for (int j = 0 ; j < this.getMAX_PLAYER(); j++){
                //TODO: Refactor this line, Add Pop override to Deck
                ((Pile) ((ArrayList) getPiles()).get(j)).getCardsInPile().add(((Card) ((Stack) getDeck().getCards()).pop()));
            }
        }
    }

    @Override
    boolean winningCondition() {

        return false;
    }

    @Override
    void initPiles() {
        setPiles(new ArrayList<>());
        for(int i = 0; i < 4; i++){
            getPiles().add(new Pile(new ArrayList<>(),i,false,true));
        }
        getPiles().add(new Pile(new ArrayList<>(),5,true,true));

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

    HashMap<Player, Integer> initScore() {
        HashMap<Player, Integer> hm = new HashMap<>();
        for (Player p : getJoinedPlayers()) {
            hm.put(p, 0);
        }
        return hm;
    }
}
