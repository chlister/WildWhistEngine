package com.wildgroup.db_package;

import com.wildgroup.db_package.dbModels.UserDb;
import com.wildgroup.user_package.models.User;
import javafx.scene.control.Tab;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */
public class UserRepository extends DBRepository<User> {

    static private final String values = " (" +
            UserDb.firstName + ", " +
            UserDb.middleName + ", " +
            UserDb.lastName + ", " +
            UserDb.password + ", " +
            UserDb.email + ", " +
            UserDb.birthday + ") " +
            "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')";


    @Override
    User populate(ResultSet rs) {
        User us = null;
        try {
            us = new User(rs.getString(UserDb.firstName),
                    rs.getString(UserDb.middleName),
                    rs.getString(UserDb.lastName),
                    rs.getString(UserDb.password),
                    rs.getString(UserDb.email),
                    rs.getDate(UserDb.birthday),
                    rs.getInt(UserDb.id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return us;
    }

    // TODO: insertBuilder
    public int insertBuilder(User user) {
        StringBuilder sb = new StringBuilder(
                String.format(insert,
                        TableNames.users,
                        String.format(values,
                                user.getFirstname(),
                                user.getMiddelname(),
                                user.getLastname(),
                                user.getPassword(),
                                user.getEmail(),
                                user.getBirthday()
                        )));
        System.out.println(sb);
        return insert(sb.toString());
    }

    // TODO: updateBuilder

    public int updateUser(User user) {
        // First select the user in the database
        User dbUser = get(String.format(select, TableNames.users) + String.format(whereClause, UserDb.id, user.getId()));
//        System.out.println(dbUser.getFirstname());
        // If user exists update the variables
        if (dbUser != null) {
            dbUser.setEmail(user.getEmail());
            dbUser.setFirstname(user.getFirstname());
            dbUser.setMiddelname(user.getMiddelname());
            dbUser.setLastname(user.getLastname());
            dbUser.setBirthday(user.getBirthday());
            dbUser.setPassword(user.getPassword());
            StringBuilder sb = new StringBuilder();
            sb
                    .append(String.format(update, TableNames.users, UserDb.firstName))
                    .append(" ").append(dbUser.getFirstname())
                    .append(String.format(addField, UserDb.middleName))
                    .append(" ").append(dbUser.getMiddelname())
                    .append(String.format(addField, UserDb.lastName))
                    .append(" ").append(dbUser.getLastname())
                    .append(String.format(addField, UserDb.password))
                    .append(" ").append(dbUser.getPassword())
                    .append(String.format(addField, UserDb.email))
                    .append(" ").append(dbUser.getEmail())
                    .append(String.format(addField, UserDb.birthday))
                    .append(" ").append(dbUser.getBirthday())
                    .append(String.format(whereClause, UserDb.id, dbUser.getId()));
            System.out.println(sb);
//           return update(String.format(update, TableNames.users, UserDb.firstName));
            return 1;
        } else
            return 0;
    }

    // TODO: selectBuilder -> remember overloads
    public User selectUser(int id) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(select, TableNames.users));
        sb.append(String.format(whereClause, UserDb.id, id));
        System.out.println(sb);
        return get(sb.toString());
    }

    public Collection<User> getAllUsers() {
        return getAll(String.format(select, TableNames.users));
    }
    // TODO: deleteBuilder

}
