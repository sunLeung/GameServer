package protocol.http;

import game.player.Player;
import game.player.PlayerService;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import common.net.HttpPacket;
import common.utils.JsonUtils;
import common.utils.StringUtils;

@HttpProtocol(0x00)
public class LoginAction extends HttpAction{

	@Override
	public String excute(HttpPacket packet) {
		Map<String,Object> result=new HashMap<String, Object>();
		try {
			String data=packet.getData();
			JsonNode node=JsonUtils.decode(data);
			String identity = JsonUtils.getString("identity", node);
			String password = JsonUtils.getString("password",node);
			if(StringUtils.isNotBlank(identity)&&StringUtils.isNotBlank(password)){
				Player p=PlayerService.login(identity, password, packet.getDeviceid());
				if(p!=null){
					result.put("code", 0);
					result.put("player", p.getBean());
					return JsonUtils.encode2Str(result);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 2);
			return JsonUtils.encode2Str(result);
		}
		result.put("code", 1);
		return JsonUtils.encode2Str(result);
	}
	
}
