package game.dao;

import static common.db.DbUtils.dbUtils;
import game.player.PlayerBean;

import java.sql.SQLException;

import common.log.Logger;
import common.log.LoggerManger;


public class PlayerDao {
	private static Logger log=LoggerManger.getLogger();
	
	
	public static int save(PlayerBean bean){
		try {
			return dbUtils.insert(bean);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 加载玩家数据
	 * @param playerid
	 * @return
	 */
	public static PlayerBean loadById(int playerid){
		PlayerBean bean=null;
		try {
			bean=dbUtils.read(PlayerBean.class, "where id=?", playerid);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
	public static PlayerBean loadByEmail(String email){
		PlayerBean bean=null;
		try {
			bean=dbUtils.read(PlayerBean.class, "where email=?", email);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
	public static PlayerBean loadByPhone(String phone){
		PlayerBean bean=null;
		try {
			bean=dbUtils.read(PlayerBean.class, "where email=?", phone);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
	
	public static PlayerBean loadByName(String name){
		PlayerBean bean=null;
		try {
			bean=dbUtils.read(PlayerBean.class, "where name=?", name);
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}
}
