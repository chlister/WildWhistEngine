package com.wildgroup.db_package;

import com.wildgroup.user_package.models.Player;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */
public class PlayerRepository extends DBRepository<Player> {
    @Override
    Player populate(ResultSet rs) {
        Player pl = null;
        try{
            pl = new Player();
            pl.setId(rs.getInt("id"));
            pl.setAvatar(rs.getBlob("avatar"));
            pl.setName(rs.getString("name"));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return pl;
    }
}
