package com.wildgroup.db_package;

import com.wildgroup.db_package.dbModels.DBTable.TableNames;
import com.wildgroup.db_package.dbModels.DBTable.UserDb;
import com.wildgroup.db_package.dbModels.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */

@SuppressWarnings("StringBufferReplaceableByString")
public class UserRepository extends DBRepository<UserEntity> {

    static private final String values = " (" +
            UserDb.firstName + ", " +
            UserDb.middleName + ", " +
            UserDb.lastName + ", " +
            UserDb.password + ", " +
            UserDb.email + ", " +
            UserDb.birthday + ") " +
            "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')";


    /**
     * Method populates a user object
     *
     * @param rs ResultSet
     * @return User object
     */
    @Override
    UserEntity populate(ResultSet rs) {
        UserEntity us = null;
        try {
            us = new UserEntity(rs.getString(UserDb.firstName),
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

    /**
     * Method for creating a user using a User object
     *
     * @param user User to be created
     * @return int rows affected
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     */
    public int insertBuilder(UserEntity user) {
        StringBuilder sb = new StringBuilder(
                String.format(insert,
                        TableNames.users,
                        String.format(values,
                                user.getFirst_name(),
                                user.getMiddle_name(),
                                user.getLast_name(),
                                user.getPassword(),
                                user.getEmail(),
                                user.getBirthday()
                        )));
        System.out.println(sb);
        return insert(sb.toString());
    }

    /**
     * Input the User object to be updated
     *
     * @param user User to be updated
     * @return int rows updated
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     * Update user method - given a user object it will update
     */
    public int updateUser(UserEntity user) {
        // First select the user in the database
        UserEntity dbUser = get(String.format(select, TableNames.users) + String.format(whereClauseId, UserDb.id, user.getId()));
        // If user exists update the variables
        if (dbUser != null) {
            dbUser.setEmail(user.getEmail());
            dbUser.setFirst_name(user.getFirst_name());
            dbUser.setMiddle_name(user.getMiddle_name());
            dbUser.setLast_name(user.getLast_name());
            dbUser.setBirthday(user.getBirthday());
            dbUser.setPassword(user.getPassword());
            StringBuilder sb = new StringBuilder();
            sb
                    .append(String.format(update, TableNames.users, UserDb.firstName))
                    .append(" ").append(dbUser.getFirst_name())
                    .append(String.format(addField, UserDb.middleName))
                    .append(" ").append(dbUser.getMiddle_name())
                    .append(String.format(addField, UserDb.lastName))
                    .append(" ").append(dbUser.getLast_name())
                    .append(String.format(addField, UserDb.password))
                    .append(" ").append(dbUser.getPassword())
                    .append(String.format(addField, UserDb.email))
                    .append(" ").append(dbUser.getEmail())
                    .append(String.format(addField, UserDb.birthday))
                    .append(" ").append(dbUser.getBirthday())
                    .append(String.format(whereClauseId, UserDb.id, dbUser.getId()));
            System.out.println(sb);
            return update(String.format(update, TableNames.users, UserDb.firstName));
//            return 1;
        } else
            return 0;
    }

    /**
     * Select a single user via id
     *
     * @param id int user primary key
     * @return User object
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     */
    public UserEntity selectUser(int id) {
        StringBuilder sb = new StringBuilder();
        // Select + table name
        sb.append(String.format(select, TableNames.users));
        sb.append(String.format(whereClauseId, UserDb.id, id));
        return get(sb.toString());
    }

    /**
     * Select a single user via id
     *
     * @param email String user email
     * @return User object
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     */
    public UserEntity selectUser(String email) {
        StringBuilder sb = new StringBuilder();
        // Select + table name
        sb.append(String.format(select, TableNames.users));
        sb.append(String.format(whereClauseString, UserDb.email, email));
        return get(sb.toString());
    }

    /**
     * Select a single user via column + identifier
     *
     * @param column     UserDb column name
     * @param identifier String search identifier
     * @return User object
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     */
    public UserEntity selectUser(UserDb column, String identifier) {
        StringBuilder sb = new StringBuilder();
        // select table
        sb.append(String.format(select, TableNames.users));
        // where column = identifier
        sb.append(String.format(whereClauseString, column, identifier));
        System.out.println(sb);
        return get(sb.toString());
    }

    public Collection<UserEntity> getAllUsers() {
        return getAll(String.format(select, TableNames.users));
    }
    // TODO: deleteBuilder

}
