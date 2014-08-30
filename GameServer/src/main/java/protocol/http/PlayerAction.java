package protocol.http;

import com.fasterxml.jackson.databind.node.ObjectNode;
import common.net.HttpPacket;
import common.utils.JsonUtils;

@HttpProtocol(0x01)
public class PlayerAction extends HttpAction{

	@Override
	public String excute(HttpPacket packet) {
		ObjectNode node=JsonUtils.createObjectNode();
		node.put("hey", "man");
		return node.toString();
	}
	
}
