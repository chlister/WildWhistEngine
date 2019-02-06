package com.wildgroup.game_package;

import com.sun.xml.internal.bind.api.impl.NameConverter;
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
public class Whist extends Game implements DealerToken, GameFunctionRespondable {
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
    int[] playerCalls = new int[this.getMAX_PLAYER()];


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
    public void run() {
        //TODO: Wait for Play Condition
        try {
            System.out.println("Hello");
            synchronized (waiter) {
                play();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void play() throws InterruptedException {
// TODO: make work
        setScoreSet(initScore());
        initPiles();
        boolean done = true;
        while (done) {
            this.getDeck().shuffle();
            deal();
            callRound();
            startRound();
            done = false;

        }
    }

    private void startRound() throws InterruptedException {
        switch (calls.values()[currentCall]) {
            case SOL:
                setAceValue(true);
                solRound();
                break;
            case PAS:
                setAceValue(false);
                passRound();
                break;
            case GRAND:
                setAceValue(false);
                grandOrDiRound(false);
                break;
            case GRANDI:
                setAceValue(false);
                grandOrDiRound(true);
                break;
        }
    }

    private void solRound() throws InterruptedException {
        //Player Chooses card
        //Next Player
        final int first = 0;
        int[] tricks = new int[4];
        Arrays.fill(tricks, 0);
        int startingPlayer = getActivePlayer();
        do {
            getHandler().selectACard(getActivePlayer());
            waiter.wait();
            // Has all players played a card? -> then who tricks?
            if (((ArrayList<Pile>) getPiles()).get(getMAX_PLAYER()).getCardsInPile().size() == getMAX_PLAYER()) {
                int winner = -1;
                int highestVal = -1;
                for (Card c : ((ArrayList<Pile>) getPiles()).get(getMAX_PLAYER()).getCardsInPile()) {
                    if (((StandardCard) c).getValue() > highestVal &&
                            ((StandardCard) ((ArrayList) ((ArrayList<Pile>) getPiles())
                                    .get(getMAX_PLAYER()).getCardsInPile()).get(first))
                                    .getSuit().equals(((StandardCard) c).getSuit())) {
                        winner = ((ArrayList<Card>) ((ArrayList<Pile>) getPiles())
                                .get(getMAX_PLAYER()).getCardsInPile()).indexOf(c);
                        highestVal = ((StandardCard) c).getValue();
                    }
                }
                // calc score
                tricks[winner]++;
            }
            //TODO: if Sol player has 2 tricks -> end game
            // check if all card have been played
            if (((ArrayList<Pile>) getPiles()).get(first).getCardsInPile().isEmpty())
                currentState = GameState.ROUNDDONE;
        }
        while (currentState == GameState.PLAYING);
        // check if SOL player won
        for (int i :
                playerCalls) {
            
        }
        setScoreSet();

    }

    private void passRound() {

    }

    private void grandOrDiRound(boolean di) {

    }


    private void callRound() throws InterruptedException {
        currentState = GameState.CALLROUND;
        Arrays.fill(playerCalls, 0);
        String[] theCall = new String[calls.values().length];
        int index = 0;

        for (calls c : calls.values()
        ) {
            theCall[index++] = c.name();
        }

        while (currentState == GameState.CALLROUND) {
            //Checks if everyone choose Pass
            if (!Arrays.equals(playerCalls, new int[]{3, 3, 3, 3})) {
                //Checks if player have chosen a call
                if (playerCalls[getActivePlayer()] != 3) {
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
                            getHandler().messageDebug("It works");
                            break;
                    }

                    playerCalls[getActivePlayer()] = responseIndex;
                    if (currentState == GameState.CALLROUND)
                        if (getActivePlayer() < this.getMAX_PLAYER() - 1)
                            setActivePlayer(getActivePlayer() + 1);
                        else
                            setActivePlayer(0);
                }
            } else {
                currentState = GameState.PLAYING;
                currentCall = 3;
                getHandler().messageDebug("It works");
            }
        }


    }

    private void deal() {
        int size = getDeck().getCards().size();
        for (int i = 0; i < size / getMAX_PLAYER(); i++) {
            for (int j = 0; j < this.getMAX_PLAYER(); j++) {
                //TODO: Refactor this line, Add Pop override to Deck
                //region Perfection
                ((Pile) ((ArrayList) getPiles()).get(j)).getCardsInPile().add((Card) ((ArrayList) getDeck().getCards()).get(0));
                ((ArrayList<Card>) getDeck().getCards()).remove(0);
                //endregion

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

    /**
     * @param isSol is the current GameMode Sol?
     */
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

    @Override
    public void setCallResponse(int i) {
        callResponse = i;
        synchronized (waiter) {
            waiter.notify();
        }
    }

    @Override
    public void selectedCardResponse(int seatId, Card card) {
        StandardCard selected = (StandardCard) card;
        synchronized (waiter) {
            if (((ArrayList<Pile>) getPiles()).get(4).getCardsInPile().isEmpty()) {
                updateCardAndNotify(seatId, selected);
            } else {
                Card firstCard = ((Card) ((ArrayList) ((ArrayList<Pile>) getPiles()).get(4).getCardsInPile()).get(0));
                if (((StandardCard) firstCard).getSuit() != selected.getSuit()) {
                    for (Card c : ((ArrayList<Pile>) getPiles()).get(seatId).getCardsInPile()) {
                        if (((StandardCard) c).getSuit().equals(selected.getSuit())) {
                            getHandler().selectACard(seatId);
                            return;
                        }
                    }
                    updateCardAndNotify(seatId, selected);
                } else {
                    updateCardAndNotify(seatId, selected);
                }
            }
        }

    }

    private void updateCardAndNotify(int seatId, StandardCard card) {
        synchronized (waiter) {
            ((ArrayList<Pile>) getPiles()).get(4).getCardsInPile().add(card);
            ((ArrayList<Pile>) getPiles()).get(seatId).getCardsInPile().remove(card);
            //TODO: Update Pile for Seat
            waiter.notify();

        }
    }
}
