package com.wildgroup.user_package.models;

import com.wildgroup.db_package.EntityObject;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */
public class User extends EntityObject {
    private String firstname;
    private String middelname;
    private String lastname;
    private String email;
    private String password;
    private Date birthday;

    /**
     *
     * @param firstname String
     * @param middelname String
     * @param lastname String
     * @param password String
     * @param email String
     * @param birthday Date
     */
    public User(String firstname, String middelname, String lastname, String password, String email, Date birthday) {
        this.firstname = firstname;
        this.middelname = middelname;
        this.lastname = lastname;
        setPassword(password);
        this.email = email;
        this.birthday = birthday;
    }
    /**
     * @param firstname String
     * @param middelname String
     * @param lastname String
     * @param password String
     * @param email String
     * @param birthday Date
     * @param id Int
     */
    public User(String firstname, String middelname, String lastname, String password, String email, Date birthday, int id) {
        super(id);
        this.firstname = firstname;
        this.middelname = middelname;
        this.lastname = lastname;
        setPassword(password);
        this.email = email;
        this.birthday = birthday;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddelname() {
        return middelname;
    }

    public void setMiddelname(String middelname) {
        this.middelname = middelname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
        MessageDigest md;
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
