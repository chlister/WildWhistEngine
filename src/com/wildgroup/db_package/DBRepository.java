package com.wildgroup.db_package;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 * Abstract db manager class
 */
abstract class DBRepository<T> {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    DBRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wildgameenginedb?user=wilduser&password=wildwhist");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //region Methods

    /**
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     * Method for inserting data into the database
     * @param sql insert statement
     */
    void insert(String sql) {
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     * Method for updating data in the database
     * @param sql Update statement
     */
    void update(String sql) {
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     * Method for deleting data in the database
     * @param sql Delete statement
     */
    void delete(String sql) {
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     * Method for getting multiple rows of a EntityObject
     * @param sql Select statement
     * @return Collection<T> where T -> EntityObject
     */
    Collection<T> getAll(String sql) {
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            ArrayList<T> list = new ArrayList<>();
            while (rs.next())
                try {
                    list.add(populate(rs));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     * Method for getting multiple rows of a EntityObject
     * @param sql Select statement
     * @return T where T -> EntityObject
     */
    T get(String sql) {
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            return populate(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     * Abstract method for populating the generic EntityObject
     * @param rs ResultSet
     * @return T where T -> EntityObject
     * @throws SQLException Exception
     */
    abstract T populate(ResultSet rs) throws SQLException;
    //endregion
}
