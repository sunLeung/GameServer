package common.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;

import java.lang.reflect.Method;

import com.fasterxml.jackson.databind.JsonNode;
import common.config.Config;
import common.utils.HttpRespUtils;
import common.utils.JsonUtils;
import common.utils.StringUtils;

public class AdminServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg){
		try {
			if(msg instanceof  FullHttpRequest){
				FullHttpRequest request=(FullHttpRequest)msg;
				if (!request.getDecoderResult().isSuccess()) {
					ctx.close();
					return;
				}
				String security=request.headers().get("security");
				String data = request.content().toString(CharsetUtil.UTF_8);
				if(Config.SECURITY.equals(security)&&StringUtils.isNotBlank(data)){
					JsonNode node=JsonUtils.decode(data);
					String clazz="common.admin.AdminService";
					String method=JsonUtils.getString("method", node);
					String params=JsonUtils.getString("params", node);
					if(StringUtils.isNotBlank(method)&&StringUtils.isBlank(params)){
						Class c=Class.forName(clazz);
						Method m=c.getDeclaredMethod(method);
						Object r=m.invoke(c);
						String rstr="";
						if(r!=null)rstr=r.toString();
						HttpRespUtils.response(ctx, rstr);
						return;
					}else if(StringUtils.isNotBlank(method)&&StringUtils.isNotBlank(params)){
						Class c=Class.forName(clazz);
						Method m=c.getDeclaredMethod(method,String.class);
						Object r=m.invoke(c, params);
						String rstr="";
						if(r!=null)rstr=r.toString();
						HttpRespUtils.response(ctx, rstr);
						return;
					}
				}
			}
			HttpRespUtils.response(ctx, HttpResponseStatus.BAD_REQUEST);
		} catch (Exception e) {
			HttpRespUtils.response(ctx, HttpResponseStatus.BAD_REQUEST);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
