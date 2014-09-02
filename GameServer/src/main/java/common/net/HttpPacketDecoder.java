package common.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;

import java.util.List;

import common.log.Logger;
import common.log.LoggerManger;
import common.utils.StringUtils;

public class HttpPacketDecoder extends MessageToMessageDecoder<FullHttpRequest> {
	private static Logger logger=LoggerManger.getLogger();
	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out){
		try {
			if (!msg.getDecoderResult().isSuccess()) {
				ctx.close();
				return;
			}
			String deviceid=msg.headers().get("deviceid");
			String token=msg.headers().get("token");
			String protocolStr=msg.headers().get("protocol");
			logger.debug("Received protocol "+protocolStr);
			int protocol=Integer.valueOf(protocolStr.replace("0x", "").trim(),16);
			String data = msg.content().toString(CharsetUtil.UTF_8);
			String playeridStr=msg.headers().get("playerid");
			int playerid=-1;
			if(StringUtils.isNotBlank(playeridStr))
				playerid=Integer.valueOf(playeridStr.trim());
			HttpPacket packet = new HttpPacket(playerid,deviceid,token,protocol,data,msg);
			out.add(packet);
		} catch (Exception e) {
			ctx.close();
		}
	}
}
