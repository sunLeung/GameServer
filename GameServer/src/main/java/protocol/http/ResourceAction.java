package protocol.http;

import game.player.PlayerService;

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
					return JsonRespUtils.success("Player has this resource.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonRespUtils.fail(Def.CODE_QUERY_RESOURCE_EXCEPTION, "Query resource catch exception.");
		}
		return JsonRespUtils.fail(Def.CODE_QUERY_RESOURCE_FAIL, "Player has not this resource.");
	}
	
}
