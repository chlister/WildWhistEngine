package com.wildgroup.db_package;

import java.sql.*;

/**
 * @author Marc Rohwedder Kær
 * @date 28-01-2019
 */
public class DbConnection {
    private Connection con;
    private Statement st;
    private ResultSet rs;


    public DbConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/wildgameenginedb?&useLegacyDatetimeCode=false&serverTimezone=GMT","wilduser", "wildwhist");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Connection getCon() {
        return con;
    }

    public ResultSet executeSt(String sql){
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            return rs;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
