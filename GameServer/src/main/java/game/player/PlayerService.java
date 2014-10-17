package game.player;

import game.dao.PlayerDao;

import com.fasterxml.jackson.databind.JsonNode;
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
	
	/**
	 * 第三方渠道登陆
	 * @param deviceid
	 * @param data
	 * @return
	 */
	public static Player thirdPartylogin(String deviceid,String data){
		Player p=null;
		JsonNode jsonData=JsonUtils.decode(data);
		String unionid=JsonUtils.getString("unionid", jsonData);
		if("1".equals(unionid)){//微信登陆
			String openid=JsonUtils.getString("identity", jsonData);
			if(StringUtils.isBlank(openid)){
				return null;
			}
			PlayerBean bean=PlayerDao.loadByThirdParty(openid);
			if(bean!=null){
				bean.setDeviceid(deviceid);
				bean.setToken(SecurityUtils.createUUIDString());
				p=PlayerCache.initPlayer(bean);
			}else{
				bean=new PlayerBean();
				bean.setDeviceid(deviceid);
				bean.setSecret(SecurityUtils.createUUIDString());
				int sex=JsonUtils.getInt("sex", jsonData);
				bean.setSex(sex);
				String name=JsonUtils.getString("name", jsonData);
				if(StringUtils.isBlank(name)){
					name=StringUtils.randomName();
				}
				bean.setName(name);
				bean.setToken(SecurityUtils.createUUIDString());
				
				bean.setMoney(100);
				
				int id=PlayerDao.save(bean);
				if(id!=-1){
					p=PlayerCache.getPlayer(id);
				}
			}
		}
		return p;
	}
	
	public static boolean updatePlayer(int playerid,String data){
		JsonNode jsonData=JsonUtils.decode(data);
		Player p=PlayerCache.getPlayer(playerid);
		if(p!=null){
			String name=JsonUtils.getString("name", jsonData);
			if(StringUtils.isNotBlank(name))
				p.getBean().setName(name);
			int sex=JsonUtils.getInt("sex", jsonData);
			if(sex!=-1)
				p.getBean().setSex(sex);
			int r=PlayerDao.update(p.getBean());
			if(r!=-1){
				return true;
			}
		}
		return false;
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
