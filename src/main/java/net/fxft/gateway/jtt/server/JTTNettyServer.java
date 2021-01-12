package net.fxft.gateway.jtt.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import net.fxft.gateway.jtt.config.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;
/**
 * 基于netty 框架的tcp 服务器
 * @author admin
 *
 */
@Slf4j
//@Service("jt808TcpNettyServer")
public class JTTNettyServer{
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	ProtocolServerHandler protocolServerHandler;
	//监听线程组，主要是监听客户端请求
	EventLoopGroup bossGroup;
	//工作线程组，主要是处理与客户端的数据通讯
	EventLoopGroup workerGroup;
	ChannelFuture f;
	//ServerBootStrap是Netty服务端启动配置类，BootStrap是Netty客户端启动配置类。
	ServerBootstrap b;


	@PostConstruct
	public void init() {
		Thread t1 = new Thread(()-> start(), "jtt-server");
		t1.start();
	}
	public boolean start()  {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		//超过20分钟未收到客户端消息则自动断开客户端连接
		try {
			b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
								@Override
								public void initChannel(SocketChannel ch) throws Exception {
			                        ch.pipeline().addLast("idleStateHandler",
			                                new IdleStateHandler(20, 0, 0, TimeUnit.MINUTES));
									ch.pipeline().addLast("decoder",
											new ProtocolDecoder());
									ch.pipeline().addLast("encoder",
											new ByteArrayEncoder());
									ch.pipeline().addLast(protocolServerHandler);
								}
							}).option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, false);


			f = b.bind(systemConfig.getPort()).sync();

			// 等待服务器 socket 关闭 。
			// 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
			// f.channel().closeFuture().sync();
			return true;
		} catch(Exception ex) {
			log.error("",ex);
		}
		return false;
	}

	@PreDestroy
	public void Stop() {
		log.error("netty server stop");
		try {
			// f.channel().closeFuture().sync();
			f.channel().close();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		try {
			workerGroup.shutdownGracefully();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		try {
			bossGroup.shutdownGracefully();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	




}
