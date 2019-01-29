package test;

import com.wildgroup.db_package.DbConnection;
import org.junit.*;
import java.sql.*;

/**
 * @author Marc Rohwedder Kær
 * @date 28-01-2019
 * Test class for testing Database package
 */
public class DbManagerTest {

    @Test
    public void runTest(){
        Assert.fail("Not implemented");
    }

    /**
     * @author Marc Rohwedder Kær
     * @date 28-01-2019
     * Connection test, checks if object gets initialized
     */
    @Test
    public void DbConnectionTest() {

        DbConnection con = new DbConnection();
        Assert.assertNotNull("Connection is null", con);
        TestConnectionOpen(con);
    }

    /**
     * @author Marc Rohwedder Kær
     * @date 28-01-2019
     * Checks if the connection is open
     */
    private void TestConnectionOpen(DbConnection con) {
        try {
            Assert.assertFalse("Connection is closed",con.getCon().isClosed());
            System.out.println("Connection is open");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Marc Rohwedder Kær
     * @date 28-01-2019
     * Test returning a resultset from the database
     */
    @Test
    public void getResultSet(){
        try {
        DbConnection con = new DbConnection();
        ResultSet rs = con.executeSt("SELECT * FROM game");
        while (rs.next()){
            System.out.println("Id: " + rs.getInt("Id"));
            System.out.println("Name: " + rs.getString("Name"));
        }
            System.out.println(rs.getStatement());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
