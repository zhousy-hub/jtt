package net.fxft.gateway.jtt.server;


import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import net.fxft.gateway.jtt.service.MessageProcessService;
import net.fxft.gateway.jtt.vo.GpsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 */
@Slf4j
@ChannelHandler.Sharable
@Service("protocolServerHandler")
public class ProtocolServerHandler extends SimpleChannelInboundHandler<Object> {
	@Autowired
	MessageProcessService messageProcessService;
	private static AttributeKey<String> key = AttributeKey.valueOf("simNo");
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object obj) {

		if (obj instanceof GpsMessage) {
			GpsMessage tm = (GpsMessage) obj;
			if (messageProcessService != null) {
				messageProcessService.processMsg(tm);
			}


		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//logger.info("read complete");
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx)
			throws Exception {
		log.info("channel register");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx){
		log.error("channel unregister");

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
		log.error("netty连接异常:" + cause.getMessage(), cause);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx){
		//logger.error("==============channel-active==============");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx){
		//logger.error("==============channel-inactive==============");
	}





}
