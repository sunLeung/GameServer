package common.utils;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

public class HttpRespUtils {
	
	public static void response(ChannelHandlerContext ctx, HttpResponseStatus status) {
		String result="Failure: " + status.toString()+ "\r\n";
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
				status, Unpooled.copiedBuffer(result, CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
		response.headers().set(HttpHeaders.Names.CONNECTION, "close");
		response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, result.getBytes().length);
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
	
	public static void response(ChannelHandlerContext ctx,String data){
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
				HttpResponseStatus.OK, Unpooled.copiedBuffer(data, CharsetUtil.UTF_8));
		response.headers().set(CONTENT_TYPE, "application/json;charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
}
