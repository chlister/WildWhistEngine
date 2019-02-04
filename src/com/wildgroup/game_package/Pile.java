package com.wildgroup.game_package;

import com.wildgroup.db_package.dbModels.PlayerEntity;
import com.wildgroup.deck_package.Card;

import java.util.Collection;

/**
 * @author Marc Rohwedder Kær
 * @date 04-02-2019
 */
public class Pile {
    Collection<Card> cardsInPile;
    Collection<PlayerEntity> pileOwners;
    boolean visibleForAll;
    boolean visibleForOwner;

}
