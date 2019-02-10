import com.wildgroup.db_package.UserRepository;
import com.wildgroup.db_package.dbModels.UserEntity;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 * Testing multiple CRUD operations
 */
public class DB_CRUDTest {

    // TODO: Find a better way to implement dates for MySQL
    static private final String date = "%s-%s-%s"; // yyyy-MM-dd


    @Test
    public void userCreate() {
        String year = "1990";
        String month = "04";
        String day = "07";
        String bday = String.format(date, year, month, day);
        UserRepository ur = new UserRepository();
        UserEntity u = new UserEntity(
                "Test2",
                "",
                "Tester",
                "SecurePass!",
                "sometest@test.org",
                Date.valueOf(bday),
                0);
//        System.out.println(u.getPassword());
        int rows = ur.insertBuilder(u);
        System.out.println("Number of rows affected: " + rows);
        Assert.assertEquals("Zero rows affected.", 1, rows);
    }


    @Test
    public void userUpdate() {
        UserRepository ur = new UserRepository();
        UserEntity dbUser = ur.selectUser(1);
        dbUser.setFirst_name("John");
        int res = ur.updateUser(dbUser);
        System.out.println(res);
        Assert.assertEquals("Nothing was updated", 1, res);
    }

    @Test
    public void getUser() {
        UserRepository ur = new UserRepository();
        UserEntity u = ur.selectUser(1);
        Assert.assertNotNull("Object couldn't be parsed, or no entries in database", u);
    }

    @Test
    public void multiUserCreate() {
        String year = "1990";
        String month = "04";
        String day = "07";
        String bday = String.format(date, year, month, day);
        UserRepository ur = new UserRepository();
        for (int i = 0; i < 10; i++) {

            UserEntity u = new UserEntity(
                    "Test" + i,
                    "",
                    "Tester",
                    "SecurePass!",
                    "sometest@test.org",
                    Date.valueOf(bday),
                    0);
            System.out.println("Number of rows affected: " + ur.insertBuilder(u));
        }
    }
    // TODO: MultipleUserGet
    // TODO: UserDelete

    // TODO: GameCreate
    // TODO: GameGet
    // TODO: GameUpdate
    // TODO: GameDelete

    // TODO: PlayerCreate
    // TODO: PlayerUpdate
    // TODO: PlayerGet
    // TODO: PlayerDelete

    // TODO: PlayerHistoryCreate
    // TODO: PlayerHistoryUpdate
    // TODO: PlayerHistoryGet
    // TODO: PlayerHistoryMultipleCreate
    // TODO: PlayerHistoryMultipleGet
    // TODO: PlayerHistoryDelete
}

