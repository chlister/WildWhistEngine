package com.wildgroup.game_package;

import com.wildgroup.deck_package.Card;
import com.wildgroup.game_package.models.Player;

import java.util.Collection;

/**
 * @author Marc Rohwedder Kær
 * @date 04-02-2019
 */
public class Pile {
    Collection<Card> cardsInPile;
    Collection<Player> pileOwners;
    boolean visibleForAll;
    boolean visibleForOwner;

}
