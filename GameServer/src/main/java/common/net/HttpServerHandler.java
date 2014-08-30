package common.net;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import protocol.http.HttpAction;
import protocol.http.HttpProtocolContent;

import common.utils.HttpRespUtils;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg){
		if(msg instanceof  HttpPacket){
			HttpPacket packet=(HttpPacket)msg;
			
			HttpAction action=HttpProtocolContent.httpProtocolContent.get(((HttpPacket) msg).getProtocol());
			if(action!=null){
				HttpAction clone=action.clone();
				String result=clone.handle(packet);
				if(result==null)result="";
				FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
						HttpResponseStatus.OK, Unpooled.copiedBuffer(result, CharsetUtil.UTF_8));
				response.headers().set(CONTENT_TYPE, "application/json;charset=UTF-8");
				response.headers().set(HttpHeaders.Names.CONNECTION, "close");
				response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, result.getBytes().length);
				ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
				return;
			}
		}
		HttpRespUtils.response(ctx, HttpResponseStatus.BAD_REQUEST);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		if (ctx.channel().isActive()) {
			HttpRespUtils.response(ctx, INTERNAL_SERVER_ERROR);
		}
	}
}
