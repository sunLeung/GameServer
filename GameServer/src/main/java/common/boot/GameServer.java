package common.boot;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.net.InetSocketAddress;

import protocol.http.HttpProtocolContent;
import common.config.Config;
import common.log.Logger;
import common.log.LoggerManger;
import common.net.HttpPacketDecoder;
import common.net.HttpServerHandler;

public class GameServer {
	private static Logger logger=LoggerManger.getLogger();
	private static ChannelFuture future;
	private static EventLoopGroup bossGroup = new NioEventLoopGroup(1);;
	private static EventLoopGroup workerGroup = new NioEventLoopGroup();;
	private static DefaultEventExecutorGroup executorGroup = new DefaultEventExecutorGroup(10);
	
	public static void main(String[] args) {
		init();
		startHttpServer(4000);
		logger.info("GameServer started.");
	}
	
	public static void init(){
		LoggerManger.initLoggerConfig(Config.CONFIG_DIR+"logger.xx");
		HttpProtocolContent.init();
	}
	
	public static void stop(){
		try {
			future.channel().close();
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
			executorGroup.shutdownGracefully();
			System.out.println("GameServer stoped.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	 public static void startHttpServer(final int port){
			try {
			    ServerBootstrap b = new ServerBootstrap();
			    b.group(bossGroup, workerGroup)
				    .channel(NioServerSocketChannel.class)
				    .childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
						    ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
						    ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
						    ch.pipeline().addLast("packet-decoder",new HttpPacketDecoder());
						    ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
						    ch.pipeline().addLast(executorGroup,"HttpServerHandler",new HttpServerHandler());
//						    ch.pipeline().addLast("HttpServerHandler",new HttpServerHandler());
						}
				    });
			future = b.bind(new InetSocketAddress(port)).sync();
			logger.info("HTTP server started.Listening:" + port);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
