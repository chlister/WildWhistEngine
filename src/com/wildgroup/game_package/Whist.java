package com.wildgroup.game_package;

import com.sun.xml.internal.bind.api.impl.NameConverter;
import com.wildgroup.deck_package.Card;
import com.wildgroup.deck_package.DeckFactory;
import com.wildgroup.deck_package.StandardCard;
import com.wildgroup.deck_package.Suit;
import com.wildgroup.game_package.models.Player;

import java.io.IOException;
import java.util.*;

/**
 * (NOTE: Par-programmeret)
 * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
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

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Method imlemented from Runnable - Used for starting a new thread
     */
    @Override
    public void run() {
        //TODO: Wait for Play Condition
        try {
            while(currentState == GameState.WAITING) {
            }
            synchronized (waiter) {
                play();
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * play method override from game. Implementation rns a whist game
     * @throws InterruptedException
     */
    @Override
    public void play() throws InterruptedException, IOException {
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

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Method starts the round given the specific call made in the call round
     * @throws InterruptedException
     */
    private void startRound() throws InterruptedException, IOException {
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

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Method runs a Sol round
     * @throws InterruptedException
     */
    private void solRound() throws InterruptedException, IOException {
        //Player Chooses card
        //Next Player
        int firstPlayer = getActivePlayer();
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
                int win = winner + first;
                if(win > getMAX_PLAYER()-1)
                    tricks[win%getMAX_PLAYER()-1]++;
                else
                    tricks[win]++;

                setActivePlayer(winner);
                getHandler().messageDebug("+++ TRICKS GOES TO: " + winner + "   ( " + tricks[0] + " , " + tricks[1] + " , " + tricks[2] + " , " + tricks[3] + " )");
                ((ArrayList<Pile>) getPiles()).get(getMAX_PLAYER()).getCardsInPile().clear();

                int solPlayers = 0;
                int solLosers = 0;
                int playerIndex = 0;
                for (int i: playerCalls){
                    if(i == 1){
                        solPlayers++;
                        if(tricks[playerIndex] > 2)
                            solLosers++;
                    }
                    playerIndex++;
                }
                if(solLosers >= solPlayers)
                    currentState = GameState.ROUNDDONE;
            }


            // check if all card have been played
            if (((ArrayList<Pile>) getPiles()).get(first).getCardsInPile().isEmpty())
                currentState = GameState.ROUNDDONE;

            //:TODO NEXT PLAYER
            nextPlayer();
        }
        while (currentState == GameState.PLAYING);
        // check if SOL player won
        int solPlayers = 0;
        int solWinners = 0;
        int playerIndex = 0;
        for (int i : playerCalls) {
            if(i == 1){
                solPlayers++; // Find all sol players
                if (tricks[playerIndex] <= 2)
                {
                    solWinners++; // Find sol winners
                    getScoreSet().put(playerIndex, getScoreSet().get(playerIndex) + 3);
                }
                else
                    getScoreSet().put(playerIndex, getScoreSet().get(playerIndex) - 3);
            }
            playerIndex++;
        }
        int solLosers = solPlayers-solWinners; // Find number of those who lost
        playerIndex = 0;
        for (int i: playerCalls){
            if (i != 1 && solWinners != 0){
                getScoreSet().put(playerIndex, getScoreSet().get(playerIndex) - ((int) Math.round(Math.pow(3, solWinners-1))));
            }
            if(i != 1 && solLosers != 0) {
                getScoreSet().put(playerIndex, getScoreSet().get(playerIndex) + ((int) Math.round(Math.pow(3, solLosers - 1))));
            }
            playerIndex++;
        }
        getHandler().scoreUpdate(getScoreSet()); // Send updated score to the handler
    }

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Method runs a Pas round
     * @throws InterruptedException
     */
    private void passRound() {

    }

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Method runs a Grand or Grandi round
     * @throws InterruptedException
     */
    private void grandOrDiRound(boolean di) {

    }

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Method gets the calls from the players joined
     * @throws InterruptedException
     */
    private void callRound() throws InterruptedException, IOException {
        currentState = GameState.CALLROUND;
        Arrays.fill(playerCalls, -1);
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
                    getHandler().selectFromArray(theCall, getActivePlayer());
                    System.out.println("We are waitin'");
                    waiter.wait();
                    int responseIndex = callResponse;
                    //Checks if a player choose Grandi

                    switch (responseIndex) {

                        case 0: //If Player Chooses Grand
                            theCall = Arrays.copyOfRange(theCall, responseIndex + 1, theCall.length);
                            int passers = 0;
                            for (int i = 0; i < getMAX_PLAYER(); i++) {
                                if (i != getActivePlayer() && playerCalls[i] != -1)
                                    passers++;
                            }
                            if (passers >= getMAX_PLAYER() - 1) {
                                currentState = GameState.PLAYING;
                            }
                            currentCall = responseIndex;
                            break;

                        case 1: //If Player Chooses Sol
                            theCall = Arrays.copyOfRange(theCall, responseIndex, theCall.length);
                            passers = 0;
                            int sol = 0;
                            for (int i = 0; i < getMAX_PLAYER(); i++) {
                                if (i != getActivePlayer() && playerCalls[i] != responseIndex && playerCalls[i] != -1)
                                    passers++;
                                if (playerCalls[i] == responseIndex)
                                    sol++;
                            }
                            if (passers >= getMAX_PLAYER()-sol) {
                                currentState = GameState.PLAYING;
                            }
                            currentCall = responseIndex;
                            break;

                        case 2: //If Player Chooses Grandi
                            currentState = GameState.PLAYING;
                            currentCall = responseIndex;
                            break;

                        case 3:
                            boolean b = true;
                            for (int i = 0; i < getMAX_PLAYER(); i++){
                                if(playerCalls[i] == -1 && i != getActivePlayer())
                                    b = false;
                            }
                            if(b)
                                currentState = GameState.PLAYING;
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

            }
        }
        currentState = GameState.PLAYING;
        getHandler().messageDebug("It works - callRound-End");

    }

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Method deals cards to the players joined
     */
    private void deal() throws IOException {
        int size = getDeck().getCards().size();
        for (int i = 0; i < size / getMAX_PLAYER(); i++) {
            for (int j = 0; j < this.getMAX_PLAYER(); j++) {
                //region Perfection
                ((Pile) ((ArrayList) getPiles()).get(j)).getCardsInPile().add((Card) ((ArrayList) getDeck().getCards()).get(0));
                ((ArrayList<Card>) getDeck().getCards()).remove(0);
                //endregion

            }
        }
        getHandler().updatePiles(getPiles());
    }

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Checks if the game is over -> one player has 5 points
     * @return Boolean
     */
    @Override
    boolean winningCondition() {
        return true;


    }

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Method initializes the piles with owners being thier seatId
     */
    @Override
    void initPiles() {
        setPiles(new ArrayList<>());
        for (int i = 0; i < getMAX_PLAYER(); i++) {
            getPiles().add(new Pile(new ArrayList<>(), i, false, true));
        }
        getPiles().add(new Pile(new ArrayList<>(), getMAX_PLAYER(), true, true));

    }

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Method gives the dealer token to the next player
     */
    @Override
    public void nextDealer() {
        currentDealer++;
        if (currentDealer >= getMAX_PLAYER())
            currentDealer = 0;
    }


    public void nextPlayer() {
        setActivePlayer(getActivePlayer()+1);
        if (getActivePlayer() >= getMAX_PLAYER())
            setActivePlayer(0);
    }

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Sets trump
     * @param t Suit
     */
    public void setTrump(Suit t) {
        trump = t;
    }

    void distributePoints() {

    }

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Sets value of ace
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

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Initializes score table
     * @return HashMap<Integer, Integer> -> <seatId, score>
     */
    private HashMap<Integer, Integer> initScore() {
        HashMap<Integer, Integer> hm = new HashMap<>();
        int playerNum = 0;
        for (Player p : getJoinedPlayers()) {
            hm.put(playerNum++, 0);
        }
        return hm;
    }

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Sets call response from server, and notifies
     * @param i int -> calls index
     */
    @Override
    public void setCallResponse(int i) {
        callResponse = i;
        synchronized (waiter) {
            waiter.notify();
        }
    }

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Gets the card that the player wants to play and checks if it's a legal play
     * @param seatId int
     * @param card Card
     */
    @Override
    public void selectedCardResponse(int seatId, Card card) throws IOException {
        StandardCard selected = (StandardCard) card;
        synchronized (waiter) {
            if (((ArrayList<Pile>) getPiles()).get(4).getCardsInPile().isEmpty()) {
                updateCardAndNotify(seatId, selected);
            } else {
                Card firstCard = ((Card) ((ArrayList) ((ArrayList<Pile>) getPiles()).get(4).getCardsInPile()).get(0));
                if (((StandardCard) firstCard).getSuit() != selected.getSuit()) {
                    for (Card c : ((ArrayList<Pile>) getPiles()).get(seatId).getCardsInPile()) {
                        if (((StandardCard) c).getSuit().equals(((StandardCard) firstCard).getSuit())) {
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

    /**
     * @author Marc Rohwedder Kær, Dennis F. J. Dupont, Martin Juul Johansen
     * Updates the play pile and notifies the players of the new card in the pile
     * @param seatId int
     * @param card Card
     */
    private void updateCardAndNotify(int seatId, StandardCard card) throws IOException {
        final int centerPile = getMAX_PLAYER();
        synchronized (waiter) {
            ((ArrayList<Pile>) getPiles()).get(centerPile).getCardsInPile().add(card);
            ((ArrayList<Pile>) getPiles()).get(seatId).getCardsInPile().remove(card);
            getHandler().updatePiles(getPiles()); // Send updated piles to handler
            waiter.notify();

        }
    }

    private void startPlaying(){
        if(getJoinedPlayers().size() == getMAX_PLAYER()){
            currentState = GameState.PLAYING;
        }
    }
}
