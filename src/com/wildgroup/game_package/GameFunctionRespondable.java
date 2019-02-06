package com.wildgroup.game_package;

import com.wildgroup.deck_package.Card;

/**
 * Author: Dennis F. J. Dupont
 * Date: 06-02-2019
 * Time: 11:43
 */
public interface GameFunctionRespondable {
    void setCallResponse(int i);
    void selectedCardResponse(int seatId, Card card);
}
