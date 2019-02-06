package com.wildgroup.game_package;

import com.wildgroup.game_package.models.Player;

import java.util.Collection;

/**
 * @author Marc Rohwedder Kær
 * @date 04-02-2019
 */
public interface GameFunctionHandler {
    void selectFromArray(String[] stringArray, Player p);
    void selectFromArray(String[] stringArray, Player p, int i);
    void messageDebug(String message);
    void selectACard(int seatId);
    void dealCards(Collection<Pile> piles);



}
