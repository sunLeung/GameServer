package protocol.http;

import game.dao.PlayerDao;
import game.player.Player;
import game.player.PlayerBean;
import game.player.PlayerCache;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import common.net.HttpPacket;
import common.utils.JsonUtils;
import common.utils.StringUtils;
import common.utils.UUID;

@HttpProtocol(0x01)
public class RegisterAction extends HttpAction{

	@Override
	public String excute(HttpPacket packet) {
		Map<String,Object> result=new HashMap<String, Object>();
		try {
			String data=packet.getData();
			JsonNode node=JsonUtils.decode(data);
			String name = JsonUtils.getString("name",node);
			int sex = JsonUtils.getInt("sex",node);
			String phone = JsonUtils.getString("phone",node);
			String email = JsonUtils.getString("email",node);
			String password1 = JsonUtils.getString("password1",node);
			String password2 = JsonUtils.getString("password2",node);
			
			if(StringUtils.isBlank(name)||sex==-1||(StringUtils.isBlank(phone)&&StringUtils.isBlank(email))||StringUtils.isBlank(password1)||StringUtils.isBlank(password2)){
				result.put("code", 1);
				result.put("msg", "The necessary parameters cannot be null.");
				return JsonUtils.encode2Str(result);
			}
			
			if(!password1.equals(password2)){
				result.put("code", 2);
				result.put("msg", "Two the password is not the same.");
				return JsonUtils.encode2Str(result);
			}
			
			if(password1.length()<6||password1.length()>16){
				result.put("code", 3);
				result.put("msg", "Password length must between 6 and 16.");
				return JsonUtils.encode2Str(result);
			}
			
			if(StringUtils.isNotBlank(phone)){
				Pattern pattern = Pattern.compile("[0-9]*");
				Matcher isNum = pattern.matcher(phone);
				if(!isNum.matches()){
					result.put("code", 4);
					result.put("msg", "Phone parameter is wrong.");
					return JsonUtils.encode2Str(result);
				}
			}
			
			if(StringUtils.isNotBlank(email)){
				Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
				Matcher isEmail = pattern.matcher(email);
				if(!isEmail.matches()){
					result.put("code", 5);
					result.put("msg", "Email parameter is wrong.");
					return JsonUtils.encode2Str(result);
				}
			}
			PlayerBean p=PlayerDao.loadByName(name);
			if(p!=null){
				result.put("code", 6);
				result.put("msg", "Name has been registered.");
				return JsonUtils.encode2Str(result);
			}
			
			p=PlayerDao.loadByPhone(phone);
			if(p!=null){
				result.put("code", 7);
				result.put("msg", "Phone has been registered.");
				return JsonUtils.encode2Str(result);
			}
			
			p=PlayerDao.loadByEmail(phone);
			if(p!=null){
				result.put("code", 8);
				result.put("msg", "Email has been registered.");
				return JsonUtils.encode2Str(result);
			}
			
			PlayerBean bean=new PlayerBean();
			bean.setDeviceid(packet.getDeviceid());
			bean.setEmail(email);
			bean.setName(name);
			bean.setPassword(password1);
			bean.setPhone(phone);
			bean.setSex(sex);
			bean.setToken(UUID.createUUIDString());
			
			int id=PlayerDao.save(bean);
			if(id!=-1){
				Player player=PlayerCache.getPlayer(id);
				result.put("code", 0);
				result.put("player", player.getBean());
				return JsonUtils.encode2Str(result);
			}
			result.put("code", 9);
			result.put("msg", "Create player fail.");
			return JsonUtils.encode2Str(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 10);
			result.put("msg", "Catch exception.");
			return JsonUtils.encode2Str(result);
		}
	}
	
}
