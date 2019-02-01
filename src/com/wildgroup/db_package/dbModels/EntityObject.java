package com.wildgroup.db_package.dbModels;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */
public abstract class EntityObject {
    private int id;

    public EntityObject(){}

    public EntityObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
