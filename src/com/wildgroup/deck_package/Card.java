package com.wildgroup.deck_package;

/**
 * @author Marc Rohwedder Kær
 * @date 04-02-2019
 */
@SuppressWarnings("WeakerAccess")
public abstract class Card {
    private boolean visible;
    private String name;

    //region Getters & setters
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }
    //endregion


    public Card(String name) {
        this.name = name;
    }
}
