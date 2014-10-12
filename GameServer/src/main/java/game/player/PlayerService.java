package game.player;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import game.dao.PlayerDao;
import common.utils.JsonUtils;
import common.utils.SecurityUtils;
import common.utils.StringUtils;

public class PlayerService {
	
	/**
	 * 验证玩家是否登录
	 * @param playerid
	 * @param deviceid
	 * @param token
	 * @return
	 */
	public static boolean authPlayer(int playerid,String deviceid,String token){
		boolean result=false;
		Player p=PlayerCache.getPlayer(playerid);
		if(p!=null&&p.getId()==playerid&&deviceid.equals(p.getDeviceid())&&token.equals(p.getToken())){
			result=true;
		}
		return result;
	}
	
	/**
	 * 登录
	 * @param identity
	 * @param password
	 * @param deviceid
	 * @return
	 */
	public static Player login(String identity,String password,String deviceid){
		Player p=null;
		//使用email登录
		if(identity.contains("@")){
			PlayerBean bean=PlayerDao.loadByEmail(identity);
			if(bean!=null&&password.equals(SecurityUtils.decryptPassword(bean.getPassword()))){
				bean.setDeviceid(deviceid);
				bean.setToken(SecurityUtils.createUUIDString());
				p=PlayerCache.initPlayer(bean);
			}
		}else{//使用手机登录
			PlayerBean bean=PlayerDao.loadByPhone(identity);
			if(bean!=null&&password.equals(SecurityUtils.decryptPassword(bean.getPassword()))){
				bean.setDeviceid(deviceid);
				bean.setToken(SecurityUtils.createUUIDString());
				p=PlayerCache.initPlayer(bean);
			}
		}
		return p;
	}
	
	public static Player thirdPartylogin(String unionid,String deviceid,String data){
		Player p=null;
		if("1".equals(unionid)){//微信登陆
			JsonNode jsonData=JsonUtils.decode(data);
			String openid=JsonUtils.getString("openid", jsonData);
			if(StringUtils.isBlank(openid)){
				return null;
			}
			PlayerBean bean=PlayerDao.loadByEmail(openid);
			if(bean!=null){
				bean.setDeviceid(deviceid);
				bean.setToken(SecurityUtils.createUUIDString());
				p=PlayerCache.initPlayer(bean);
			}else{
				bean=new PlayerBean();
				bean.setDeviceid(deviceid);
				bean.setSecret(SecurityUtils.createUUIDString());
				int sex=JsonUtils.getInt("sex", jsonData);
				sex=sex==-1?0:sex;
				bean.setSex(sex);
				bean.setToken(SecurityUtils.createUUIDString());
				
				int id=PlayerDao.save(bean);
				if(id!=-1){
					p=PlayerCache.getPlayer(id);
				}
			}
		}
		return p;
	}
	
	/**
	 * 检测玩家是否有该歌曲
	 * @param playerid
	 * @param songid
	 * @return
	 */
	public static boolean hasThisSong(int playerid,int songid){
		boolean result=false;
		Player p=PlayerCache.getPlayer(playerid);
		if(p!=null){
			result = p.getSongs().contains(songid);
		}
		return result;
	}
	
}
