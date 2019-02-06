package com.wildgroup.game_package;

import com.wildgroup.deck_package.Card;
import com.wildgroup.game_package.models.Player;

import java.util.Collection;

/**
 * @author Marc Rohwedder Kær, Dennis F. J. Dupont
 * @date 04-02-2019
 */
public class Pile {
    private Collection<Card> cardsInPile;
    private int pileOwner;
    private boolean visibleForAll;
    private boolean visibleForOwner;

    Pile(Collection<Card> cardsInPile, int pileOwner, boolean visibleForAll, boolean visibleForOwner) {
        this.cardsInPile = cardsInPile;
        this.pileOwner = pileOwner;
        this.visibleForAll = visibleForAll;
        this.visibleForOwner = visibleForOwner;
    }

    public Collection<Card> getCardsInPile() {
        return cardsInPile;
    }

    public int getPileOwner() {
        return pileOwner;
    }

    public boolean isVisibleForAll() {
        return visibleForAll;
    }

    public boolean isVisibleForOwner() {
        return visibleForOwner;
    }


}
