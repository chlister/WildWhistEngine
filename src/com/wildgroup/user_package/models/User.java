package com.wildgroup.user_package.models;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */
public class User {
    private String name, email, password;
    private Date birthday;
    int id;

    //region Getters & setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        MessageDigest md = null;
        byte[] digest = null;
        try {
            // Defines the form of hashing used
            md = MessageDigest.getInstance("MD5");
            // MessageDigest needs password to be converted to bytes
            md.update(password.getBytes());
            digest = md.digest();
        } catch (NoSuchAlgorithmException | NullPointerException e) {
            e.printStackTrace();
        }
        this.password = DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    //endregion
}
