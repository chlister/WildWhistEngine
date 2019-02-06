package com.wildgroup.game_package;

import com.wildgroup.deck_package.Card;
import com.wildgroup.deck_package.DeckFactory;
import com.wildgroup.deck_package.StandardCard;
import com.wildgroup.deck_package.Suit;
import com.wildgroup.game_package.models.Player;

import java.util.*;

/**
 * @author Marc Rohwedder Kær, Dennis F. J. Dupont
 * @date 04-02-2019
 */
public class Whist extends Game implements DealerToken {
    @Override
    public void run() {
        //TODO: Wait for Play Condition
        try {
            System.out.println("Hello");
            play();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    enum calls {
        GRAND,
        SOL,
        GRANDI,
        PAS
    }

    private Suit trump;
    private int currentCall;
    private int currentDealer;
    public GameState currentState;
    int callResponse;
    Object waiter;

    public Whist() {
        super(4, 4);
        this.setDeck(DeckFactory.getStandardDeck());
        currentDealer = 0;
        setActivePlayer(currentDealer + 1);
        waiter = new Object();

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
    public void play() throws InterruptedException {
// TODO: make work
        setScoreSet(initScore());
        initPiles();
        while (winningCondition()) {
            this.getDeck().shuffle();
            deal();
            callRound();
            startRound();
        }
        System.out.println("Doesn't work");
    }

    private void startRound() {

    }

    private void callRound() throws InterruptedException {
        currentState = GameState.CALLROUND;
        int[] intArray = new int[this.getMAX_PLAYER()];
        Arrays.fill(intArray, 0);
        String[] theCall = new String[calls.values().length];
        int index = 0;

        for (calls c : calls.values()
        ) {
            theCall[index++] = c.name();
        }

        while (currentState == GameState.CALLROUND) {
            //Checks if everyone choose Pass
            if (!Arrays.equals(intArray, new int[]{3, 3, 3, 3})) {
                //Checks if player have chosen a call
                if (intArray[getActivePlayer()] != 3) {
                    getHandler().selectFromArray(theCall, ((Player) ((ArrayList) getJoinedPlayers()).get(getActivePlayer())));
                    System.out.println("We are waitin'");
                    waiter.wait();
                    int responseIndex = callResponse; //TODO: Replace with response from server
                    //Checks if a player choose Grandi

                    switch (responseIndex) {

                        case 0: //If Player Chooses Grand
                            theCall = Arrays.copyOfRange(theCall, responseIndex + 1, theCall.length);
                            break;

                        case 1: //If Player Chooses Sol
                            theCall = Arrays.copyOfRange(theCall, responseIndex, theCall.length);
                            break;

                        case 2: //If Player Chooses Grandi
                            currentState = GameState.PLAYING;
                            currentCall = responseIndex;
                            break;
                    }

                    intArray[getActivePlayer()] = responseIndex;

                    if (getActivePlayer() < this.getMAX_PLAYER() - 1)
                        setActivePlayer(getActivePlayer() + 1);
                    else
                        setActivePlayer(0);
                }
            } else {
                currentState = GameState.PLAYING;
                currentCall = 3;
            }
        }


    }

    private void deal() {
        int size = getDeck().getCards().size();
        for (int i = 0; i < size / getMAX_PLAYER(); i++) {
            for (int j = 0; j < this.getMAX_PLAYER(); j++) {
                //TODO: Refactor this line, Add Pop override to Deck
                //region Perfection
//                ((Pile) ((Vector) getPiles()).get(j)).getCardsInPile().add(((Card) ((Stack) getDeck().getCards()).pop()));
                //endregion
                ((Pile)((ArrayList) getPiles()).get(j)).getCardsInPile().add((Card)((ArrayList)getDeck().getCards()).get(0));
                ((ArrayList<Card>) getDeck().getCards()).remove(0);
            }
        }
    }

    @Override
    boolean winningCondition() {

        return true;
    }

    @Override
    void initPiles() {
        setPiles(new ArrayList<>());
        for (int i = 0; i < 4; i++) {
            getPiles().add(new Pile(new ArrayList<>(), i, false, true));
        }
        getPiles().add(new Pile(new ArrayList<>(), 5, true, true));

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

    private HashMap<Player, Integer> initScore() {
        HashMap<Player, Integer> hm = new HashMap<>();
        for (Player p : getJoinedPlayers()) {
            hm.put(p, 0);
        }
        return hm;
    }

   public void setCallResponse(int i){
        callResponse = i;
        waiter.notify();
    }
}
