package protocol.http;

import game.player.Player;
import game.player.PlayerCache;
import game.player.PlayerService;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import common.net.HttpAction;
import common.net.HttpPacket;
import common.net.HttpProtocol;
import common.utils.Def;
import common.utils.JsonRespUtils;
import common.utils.JsonUtils;

@HttpProtocol(Def.PROTOCOL_RESOURCE)
public class ResourceAction extends HttpAction{

	@Override
	public String excute(HttpPacket packet) {
		try {
			String data=packet.getData();
			JsonNode node=JsonUtils.decode(data);
			int resourceid = JsonUtils.getInt("resourceid", node);
			int playerid=packet.getPlayerid();
			if(resourceid!=-1&&playerid!=-1){
				boolean b=PlayerService.hasThisSong(playerid, resourceid);
				if(b){
					Map<String, Object> r=new HashMap<String, Object>();
					Player p=PlayerCache.getPlayer(playerid);
					r.put("secret", p.getBean().getSecret());
					return JsonRespUtils.success(r);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonRespUtils.fail(Def.CODE_QUERY_RESOURCE_EXCEPTION, "Query resource catch exception.");
		}
		return JsonRespUtils.fail(Def.CODE_QUERY_RESOURCE_FAIL, "Player has not this resource.");
	}
	
}
