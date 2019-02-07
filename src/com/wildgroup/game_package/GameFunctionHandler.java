package com.wildgroup.game_package;

import com.wildgroup.game_package.models.Player;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author Marc Rohwedder Kær
 * @date 04-02-2019
 */
public interface GameFunctionHandler {
    void selectFromArray(String[] stringArray, int seatId) throws IOException;
    void messageDebug(String message) throws IOException;
    void selectACard(int seatId) throws IOException;
    void dealCards(Collection<Pile> piles);
    void updatePiles(Collection<Pile> piles) throws IOException;
    void scoreUpdate(HashMap<Integer, Integer> scoreSet);
}
