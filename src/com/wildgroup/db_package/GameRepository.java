package com.wildgroup.db_package;

import com.wildgroup.game_package.Game;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Marc Rohwedder Kær
 * @date 29-01-2019
 */
public class GameRepository extends DBRepository<Game> {
    @Override
    Game populate(ResultSet rs) {
        Game game = null;
        try {
            game = new Game();
            game.setName(rs.getString("name"));
//            game.setId(rs.getInt("id"));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return game;
    }
}
