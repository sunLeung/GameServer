package protocol.http;

import com.fasterxml.jackson.databind.JsonNode;

import common.boot.GameServer;
import common.net.HttpPacket;
import common.utils.JsonUtils;

@HttpProtocol(0x315)
public class SystemAction extends HttpAction{

	@Override
	public String excute(HttpPacket packet) {
		try {
			JsonNode node=JsonUtils.decode(packet.getData());
			String pin = node.get("pin").asText();
			if("#a".equals(pin)){
				GameServer.stop();
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
