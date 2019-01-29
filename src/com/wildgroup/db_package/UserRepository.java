package com.wildgroup.db_package;

import com.wildgroup.user_package.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */
public class UserRepository extends DBRepository<User> {
    @Override
    User populate(ResultSet rs) {
        User us = null;
        try{
            us = new User();
            us.setFirstName(rs.getString("firstName"));
            us.setMiddleName(rs.getString("middleName"));
            us.setLastName(rs.getString("lastName"));
            us.setBirthday(rs.getDate("birthday"));
            us.setEmail(rs.getString("email"));
            us.setId(rs.getInt("id"));
            return new User();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return us;
    }
}
