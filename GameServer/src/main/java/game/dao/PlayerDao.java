package game.dao;

import static common.db.DbUtils.dbUtils;
import game.player.Player;

import java.sql.SQLException;

import common.log.Logger;
import common.log.LoggerManger;


public class PlayerDao {
	private static Logger log=LoggerManger.getLogger();
	
	
	public static int save(Player player){
		try {
			return dbUtils.insert(player);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return -1;
	}
}
