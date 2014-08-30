package common.net;

import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

import java.util.List;

import common.utils.HttpRespUtils;
import common.utils.StringUtils;

public class HttpPacketDecoder extends MessageToMessageDecoder<FullHttpRequest> {
	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out){
		try {
			if (!msg.getDecoderResult().isSuccess()) {
				HttpRespUtils.response(ctx, BAD_REQUEST);
				return;
			}
			String deviceid=msg.headers().get("deviceid");
			String token=msg.headers().get("token");
			int protocol=Integer.valueOf(msg.headers().get("protocol").replace("0x", ""),16);
			if(StringUtils.isBlank(deviceid)||StringUtils.isBlank(token)){
				HttpRespUtils.response(ctx, HttpResponseStatus.UNAUTHORIZED);
				return;
			}
			String data = msg.content().toString(CharsetUtil.UTF_8);
			HttpPacket packet = new HttpPacket(deviceid,token,protocol,data,msg);
			out.add(packet);
		} catch (Exception e) {
			HttpRespUtils.response(ctx, BAD_REQUEST);
		}
	}
}
