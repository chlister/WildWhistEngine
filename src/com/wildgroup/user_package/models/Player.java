package com.wildgroup.user_package.models;

import com.wildgroup.db_package.dbModels.EntityObject;
import java.sql.Blob;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */
public class Player extends EntityObject {
    private String name;
    private Blob avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Blob getAvatar() {
        return avatar;
    }

    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
    }
}
