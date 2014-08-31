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
import common.net.HttpPacketDecoder;
import common.net.HttpServerHandler;

public class GameServer {
	private static ChannelFuture future;
	private static EventLoopGroup bossGroup;
	private static EventLoopGroup workerGroup;
	private static DefaultEventExecutorGroup executorGroup = new DefaultEventExecutorGroup(10);
	
	public static void main(String[] args) {
		init();
		startHttpServer(4000);
		System.out.println("GameServer started.");
	}
	
	public static void init(){
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
			bossGroup = new NioEventLoopGroup(1);
			workerGroup = new NioEventLoopGroup();
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
			System.out.println("HTTP server started.Listening:" + port);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
