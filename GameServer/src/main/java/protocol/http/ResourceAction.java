package protocol.http;

import game.player.PlayerService;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import common.net.HttpPacket;
import common.utils.Def;
import common.utils.JsonUtils;

@HttpProtocol(Def.PROTOCOL_RESOURCE)
public class ResourceAction extends HttpAction{

	@Override
	public String excute(HttpPacket packet) {
		Map<String,Object> result=new HashMap<String, Object>();
		try {
			String data=packet.getData();
			JsonNode node=JsonUtils.decode(data);
			int resourceid = JsonUtils.getInt("resourceid", node);
			int playerid=packet.getPlayerid();
			if(resourceid!=-1&&playerid!=-1){
				boolean b=PlayerService.hasThisSong(playerid, resourceid);
				if(b){
					result.put("code", 0);
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
