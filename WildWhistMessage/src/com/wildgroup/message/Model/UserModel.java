package com.wildgroup.message.Model;

import com.google.gson.internal.LinkedTreeMap;

import java.util.Date;

/**
 * @author Martin Juul Johansen
 * @date 04/02/2019
 */

public class UserModel {
        private int id;
        private String name;
        private String email;
        private String password;
        private Date birthday;


        public UserModel(int id, String name, String email, String password, Date birthday) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = password;
            this.birthday = birthday;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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
            this.password = password;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }

        public static UserModel Deserialize(LinkedTreeMap ltm){
            double d = (double)ltm.get("id");
            UserModel u = new UserModel(
                    (int) d ,
                    (String) ltm.get("name"),
                    (String) ltm.get("email"),
                    (String) ltm.get("password"),
                    new Date()
            );
            return  u;
        }

}
