package com.wildgroup.game_package;

import com.wildgroup.db_package.dbModels.EntityObject;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */
public class Game extends EntityObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
