package protocol.http;

import game.player.Player;
import game.player.PlayerBean;
import game.player.PlayerCache;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import common.net.HttpPacket;
import common.utils.JsonUtils;
import common.utils.StringUtils;

@HttpProtocol(0x0a)
public class PlayerAction extends HttpAction {

	@Override
	public String excute(HttpPacket packet) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			int playerid = packet.getPlayerid();
			if (playerid != -1) {
				Player p = PlayerCache.getPlayer(playerid);
				if (p != null) {
					if (StringUtils.isNotBlank(packet.getData())) {
						JsonNode node = JsonUtils.decode(packet.getData());
						String attribute = JsonUtils.getString("attribute",node);
						if (StringUtils.isNotBlank(attribute)) {
							String[] attrs = attribute.split("#");
							Map<String, Object> data = new HashMap<String, Object>();
							for (String attr : attrs) {
								PlayerBean bean = p.getBean();
								Field field = bean.getClass().getDeclaredField(attr);
								field.setAccessible(true);
								Object val = field.get(bean);
								data.put(attr, val);
							}
							result.put("code", 0);
							result.put("player", data);
							return JsonUtils.encode2Str(result);
						}else{
							result.put("code", 0);
							result.put("player", p.getBean());
							return JsonUtils.encode2Str(result);
						}
					} else {
						result.put("code", 0);
						result.put("player", p.getBean());
						return JsonUtils.encode2Str(result);
					}
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

	public static void main(String[] args) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		Player p = PlayerCache.getPlayer(4);
		Field field = p.getBean().getClass().getDeclaredField("adf");
		field.setAccessible(true);
		System.out.println(field.get(p.getBean()));
	}
}
