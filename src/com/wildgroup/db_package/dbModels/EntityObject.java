package com.wildgroup.db_package.dbModels;

/**
 * Entity model for all entities
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */
public abstract class EntityObject {
    private int id;

    EntityObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
