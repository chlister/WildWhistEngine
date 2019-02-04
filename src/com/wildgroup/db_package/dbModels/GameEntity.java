package com.wildgroup.db_package.dbModels;

/**
 * Entity model for Game
 * @author Marc Rohwedder Kær
 * @date 04-02-2019
 */
public class GameEntity extends EntityObject {
    String name;

    public GameEntity(int id, String name) {
        super(id);
    }
}
