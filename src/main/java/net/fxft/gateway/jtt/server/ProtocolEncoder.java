package net.fxft.gateway.jtt.server;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.fxft.gateway.jtt.vo.GpsMessage;

/**
 * 
 */
public class ProtocolEncoder extends MessageToByteEncoder<GpsMessage> {

	/**
	 * 
	 */
	public ProtocolEncoder() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param outboundMessageType
	 */
	public ProtocolEncoder(Class<? extends GpsMessage> outboundMessageType) {
		super(outboundMessageType);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param preferDirect
	 */
	public ProtocolEncoder(boolean preferDirect) {
		super(preferDirect);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param outboundMessageType
	 * @param preferDirect
	 */
	public ProtocolEncoder(Class<? extends GpsMessage> outboundMessageType,
			boolean preferDirect) {
		super(outboundMessageType, preferDirect);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see io.netty.handler.codec.MessageToByteEncoder#encode(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.buffer.ByteBuf)
	 */
	@Override
	protected void encode(ChannelHandlerContext ctx, GpsMessage msg,
			ByteBuf out) throws Exception {
		
		byte[] data = msg.WriteToBytes();		
		out.writeBytes(data);
	}

}
