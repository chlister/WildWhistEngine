package com.wildgroup.db_package;

import com.wildgroup.db_package.dbModels.DBTable.PlayerDb;
import com.wildgroup.db_package.dbModels.DBTable.TableNames;
import com.wildgroup.db_package.dbModels.PlayerEntity;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */
public class PlayerRepository extends DBRepository<PlayerEntity> {

    static private final String values = " (" +
            PlayerDb.name + ", " +
            PlayerDb.avatar + ", " +
            PlayerDb.fk_user_id + " " +
            "VALUES ('%s', '%s', '%d')";

    /**
     * @author Marc Rohwedder Kær
     * @date 01-02-2019
     * @param rs ResultSet
     * @return PlayerEntity
     */
    @Override
    PlayerEntity populate(ResultSet rs) {
        PlayerEntity pl = null;
        try{
            pl = new PlayerEntity(rs.getInt(PlayerDb.id), rs.getString(PlayerDb.name), rs.getBlob(PlayerDb.avatar), rs.getInt(PlayerDb.fk_user_id));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return pl;
    }

    public int insertBuilder(PlayerEntity player) {
        StringBuilder sb = new StringBuilder(
                String.format(insert,
                        TableNames.users,
                        String.format(values,
                                player.getName(),
                                player.getAvatar(),
                                player.getUser_id()
                        )));
        System.out.println(sb);
        return insert(sb.toString());
    }
    // TODO: update player
    // TODO: select player
    // TODO: delete player
}
