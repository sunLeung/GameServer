package protocol.http;

import game.player.Player;
import game.player.PlayerCache;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import common.net.HttpPacket;
import common.utils.JsonUtils;

@HttpProtocol(0x0a)
public class PlayerAction extends HttpAction{

	@Override
	public String excute(HttpPacket packet) {
		Map<String,Object> result=new HashMap<String, Object>();
		try {
			int playerid=packet.getPlayerid();
			if(playerid!=-1){
				Player p=PlayerCache.getPlayer(playerid);
				if(p!=null){
					result.put("code", 0);
					result.put("player", p.getBean());
					return JsonUtils.encode2Str(result);
				}
			}
			result.put("code", 1);
			return JsonUtils.encode2Str(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 2);
			return JsonUtils.encode2Str(result);
		}
	}
	
}
