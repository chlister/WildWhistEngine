package com.wildgroup.db_package;

import com.wildgroup.db_package.dbModels.DBTable.GameDb;
import com.wildgroup.db_package.dbModels.GameEntity;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */
public class GameRepository extends DBRepository<GameEntity> {
    @Override
    GameEntity populate(ResultSet rs) {
        GameEntity entity = null;
        try {
            entity = new GameEntity(rs.getInt(GameDb.id), rs.getString(GameDb.name));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return entity;
    }
}
