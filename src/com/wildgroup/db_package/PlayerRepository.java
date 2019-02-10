package com.wildgroup.db_package;

import com.wildgroup.db_package.dbModels.DBTable.PlayerDb;
import com.wildgroup.db_package.dbModels.DBTable.TableNames;
import com.wildgroup.db_package.dbModels.PlayerEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

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

    /**
     * Create a new player in the database
     * @param player Player to be created -> has to be paired with a user_id
     * @return int rows affected
     */
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

    /**
     * Input the Player object to be updated
     *
     * @param player player to be updated
     * @return int rows updated
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     */
    public int updatePlayer(PlayerEntity player){
        PlayerEntity dbPlayer = get(String.format(select, TableNames.player) + String.format(whereClauseId, PlayerDb.id, player.getId()));
        // If user exists update the variables
        if (dbPlayer != null) {
            dbPlayer.setName(player.getName());
            dbPlayer.setAvatar(player.getAvatar());
            StringBuilder sb = new StringBuilder();
            sb
                    .append(String.format(update, TableNames.player, PlayerDb.name))
                    .append(" ").append(dbPlayer.getName())
                    .append(String.format(addField, PlayerDb.avatar))
                    .append(" ").append(dbPlayer.getAvatar())
                    .append(String.format(whereClauseId, PlayerDb.id, dbPlayer.getId()));

            System.out.println(sb);
            return update(sb.toString());
//            return 1;
        } else
            return 0;
    }

    /**
     * Select a single player via id
     *
     * @param id int player primary key
     * @return player object
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     */
    public PlayerEntity selectUser(int id) {
        StringBuilder sb = new StringBuilder();
        // Select + table name
        sb.append(String.format(select, TableNames.player));
        sb.append(String.format(whereClauseId, PlayerDb.id, id));
        return get(sb.toString());
    }

    /**
     * @author Marc Rohwedder Kær
     * @date 29-01-2019
     * @return Collection<PlayerEntity>
     */
    public Collection<PlayerEntity> getAllUsers() {
        return getAll(String.format(select, TableNames.player));
    }

    // TODO: delete player
}
