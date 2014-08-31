package protocol.http;

import game.player.Player;
import game.player.PlayerService;
import common.net.HttpPacket;
import common.utils.JsonUtils;

@HttpProtocol(0x01)
public class PlayerAction extends HttpAction{

	@Override
	public String excute(HttpPacket packet) {
		Player p=PlayerService.getPlayer(1);
		String resp=JsonUtils.encode2Str(p);
		return resp;
	}
	
}
