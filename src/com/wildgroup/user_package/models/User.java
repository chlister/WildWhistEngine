package com.wildgroup.user_package.models;

import com.wildgroup.db_package.dbModels.EntityObject;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */
public class User extends EntityObject {
    private String first_name;
    private String middle_name;
    private String last_name;
    private String email;
    private String password;
    private Date birthday;
    
    /**
     * @param first_name String
     * @param middle_name String
     * @param last_name String
     * @param password String
     * @param email String
     * @param birthday Date
     * @param id Int
     */
    public User(String first_name, String middle_name, String last_name, String password, String email, Date birthday, int id) {
        super(id);
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        setPassword(password);
        this.email = email;
        this.birthday = birthday;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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
