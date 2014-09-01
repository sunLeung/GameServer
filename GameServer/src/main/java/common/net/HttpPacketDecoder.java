package common.net;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import game.player.PlayerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

import java.util.List;

import common.log.Logger;
import common.log.LoggerManger;
import common.utils.HttpRespUtils;
import common.utils.StringUtils;

public class HttpPacketDecoder extends MessageToMessageDecoder<FullHttpRequest> {
	private static Logger logger=LoggerManger.getLogger();
	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out){
		try {
			if (!msg.getDecoderResult().isSuccess()) {
				HttpRespUtils.response(ctx, BAD_REQUEST);
				return;
			}
			String deviceid=msg.headers().get("deviceid");
			String token=msg.headers().get("token");
			String protocolStr=msg.headers().get("protocol");
			logger.debug("Received protocol "+protocolStr);
			int protocol=Integer.valueOf(protocolStr.replace("0x", ""),16);
			if(StringUtils.isBlank(deviceid)){
				HttpRespUtils.response(ctx, HttpResponseStatus.BAD_REQUEST);
				return;
			}
			int playerid=-1;
			if(protocol>=10){
				//验证玩家是否已经登录
				playerid=Integer.valueOf(msg.headers().get("playerid"));
				if(StringUtils.isBlank(token)||!PlayerService.authPlayer(playerid, deviceid, token)){
					HttpRespUtils.response(ctx, HttpResponseStatus.UNAUTHORIZED);
					return;
				}
			}
			
			String data = msg.content().toString(CharsetUtil.UTF_8);
			HttpPacket packet = new HttpPacket(playerid,deviceid,token,protocol,data,msg);
			out.add(packet);
		} catch (Exception e) {
			HttpRespUtils.response(ctx, BAD_REQUEST);
		}
	}
}
