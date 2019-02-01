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

    static final String select = "SELECT * FROM %s "; // SELECT * FROM [TableName]
    static final String insert = "INSERT INTO %s %s "; // INSERT INTO [TableName] [VALUES]
    static final String update = "UPDATE  %s SET %s = "; // UPDATE [TableName] SET [Field] =
    static final String addField = ", %s = "; // , [Field] =
    static final String whereClause = " WHERE %s = %d"; // Where [PrimaryKey] = [id]
    static final String delete = "DELETE * FROM %s WHERE %s = %d"; // DELETE * FROM [TableName] WHERE PrimaryKey = [id]
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
     * @param sql insert statement
     * Method for inserting data into the database
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     */
    int insert(String sql) {
        return dbUpdater(sql);
    }

    /**
     * @param sql statement
     * @return int number of rows affected
     * @author Marc Rohwedder Kær
     * @date 30-01-2019
     */
    private int dbUpdater(String sql) {
        try {
            st = con.createStatement();
            return st.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param sql Update statement
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     * Method for updating data in the database
     */
    int update(String sql) {
        return dbUpdater(sql);
    }

    /**
     * @param sql Delete statement
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     * Method for deleting data in the database
     */
    int delete(String sql) {
        return dbUpdater(sql);
    }

    /**
     * @param sql Select statement
     * @return Collection<T> where T -> EntityObject
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     * Method for getting multiple rows of a EntityObject
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
     * @param sql Select statement
     * @return T where T -> EntityObject
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     * Method for getting multiple rows of a EntityObject
     */
    T get(String sql) {
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
//            System.out.println(rs.getRow());
            if (rs.next())
                return populate(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param rs ResultSet
     * @return T where T -> EntityObject
     * @throws SQLException Exception
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     * Abstract method for populating the generic EntityObject
     */
    abstract T populate(ResultSet rs) throws SQLException;
    //endregion
}
