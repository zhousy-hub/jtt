package net.fxft.gateway.jtt.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import net.fxft.gateway.jtt.vo.GpsMessage;

import java.util.List;

/**
 * 808协议解析器，专用于nettry server
 * 7E
 * 00 3B 长度=59
 * 0D AD 区域代码=3501
 * 0A 车牌号大小=10
 * C3 F6 41 43 38 39 32 35 BB C6 车牌号=闽AC8925黄
 * 07 E1 08 01 02 3B 03 B0 时间=2017-08-01 10:23:42
 * 07 15 C1 C2 经度=118.86637
 * 01 91 04 92 纬度=26.281106
 * 00 00 00 1E 高度=30
 * 00 00 速度=0
 * 00 3F 方向=63
 * 00 00 EB 06 里程=60166
 * 00 00 00 00 00 03 00 03状态=196611
 * 00 00 00 00 00 00 00 00报警=0
 * 63 车辆类型=99
 * 3B 校验
 */
@Slf4j
public class ProtocolDecoder extends ByteToMessageDecoder {
	private static final int MIN_HEADER_SIZE = 10;


	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out){
		if (in == null) {
			return;
		}

		in.markReaderIndex();

		while (in.isReadable(5)) {
			in.markReaderIndex();
			byte tag = in.readByte();
			// 搜索包的开始位置
			if (tag == 0x7E && in.isReadable(2)) {
				short len = in.readShort();
				byte[] tmp = new byte[len];
				for (int i = 0; i< len ; i++){
					if (!in.isReadable()) {
						in.resetReaderIndex(); // 没有找到结束包，等待下一次包
						return;
					}
					tmp[i] = in.readByte();
				}


				byte xor = tmp[len-1];
				byte end = GetCheckXor(tmp,0,len-1);
				if(end == xor) {
					GpsMessage message = new GpsMessage();
					message.readFromBytes(tmp);

					out.add(message);

				}

			}
		}
	}

	/**
	 * 获取校验和
	 *
	 * @param data
	 * @param pos
	 * @param len
	 * @return
	 */
	private byte GetCheckXor(byte[] data, int pos, int len) {
		byte A = 0;
		for (int i = pos; i < len; i++) {
			A ^= data[i];
		}
		return A;
	}
}
