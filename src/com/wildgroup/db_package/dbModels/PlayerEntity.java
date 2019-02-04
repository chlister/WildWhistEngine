package com.wildgroup.db_package.dbModels;

import java.sql.Blob;

/**
 * @author Marc Rohwedder Kær
 * @date 04-02-2019
 */
public class PlayerEntity extends EntityObject {
    public PlayerEntity(int id) {
        super(id);
    }
    private String name;
    private Blob avatar;
    private int user_id;

    public PlayerEntity(int id, String name, Blob avatar, int user_id) {
        super(id);
        this.name = name;
        this.avatar = avatar;
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

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
