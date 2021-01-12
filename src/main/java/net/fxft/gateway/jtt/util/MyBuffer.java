package net.fxft.gateway.jtt.util;



import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ResourceLeakDetector;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 *  ByteBuffer缓冲区，用Netty实现
 *
 * @author linys
 * @create 2018-06-12
 * @since 1.0.0
 */
@Slf4j
public class MyBuffer {

    private final Logger log = LoggerFactory.getLogger(MyBuffer.class);

    protected ByteBuf buff;

    public MyBuffer() {
        buff = ByteBufAllocator.DEFAULT.ioBuffer(1536);
        //为了找到ByteBuff没有被释放的原因 (上线关闭)
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
    }

    public MyBuffer(byte[] bytes) {
        buff = ByteBufAllocator.DEFAULT.ioBuffer(bytes.length);
        buff.writeBytes(bytes);
        //为了找到ByteBuff没有被释放的原因 (上线关闭)
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
    }

    public MyBuffer(byte[] bytes, int start, int length) {
        buff = ByteBufAllocator.DEFAULT.ioBuffer(length);
        buff.writeBytes(bytes, start, length);
        //为了找到ByteBuff没有被释放的原因 (上线关闭)
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
    }

    public void clear() {
        buff.clear();
        buff.markWriterIndex();
        buff.markReaderIndex();
    }

    public void put(byte a) {
        buff.writeByte(a);
    }

    public void put(long a) {
        buff.writeLong(a);
    }

    public void put(short a) {
        buff.writeShort(a);
    }

    public void put(byte[] a) {
        buff.writeBytes(a);
    }

    public boolean hasRemain() {
        return buff.isReadable();
    }

    public void put(int a) {
        buff.writeInt(a);
    }

    public void put(String str) {
        // US-ASCII
        try {
            byte[] b = str.getBytes("gbk");
            buff.writeBytes(b);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getBcdString(int len) {
        byte[] bytes = this.gets(len);
        StringBuilder bcd = new StringBuilder();
        for (int m = 0; m < len; m++) {
            bcd.append(String.format("%02X", bytes[m]));
        }
        return bcd.toString();
    }

    public void putBcd(String str, int length) {
        byte[] b = BcDToBytes(str, length);
        buff.writeBytes(b);
    }

    public static byte[] BcDToBytes(String bcd, int len) {
        bcd = bcd == null ? "" : bcd;
        while (bcd.length() < len) {
            bcd = "0" + bcd;
        }
        return Tools.HexString2Bytes(bcd);
    }

    public static String BytesToBcd(byte[] bytes, int start, int len) {
        StringBuilder bcd = new StringBuilder();
        for (int m = 0; m < len; m++) {
            bcd.append(String.format("%02X", bytes[start + m]));
        }
        return bcd.toString();
    }

    public void put(String str, int len) {
        byte[] result = new byte[len];
        try {
            byte[] b = str.getBytes("gbk");
            System.arraycopy(b,0, result,0, b.length);
            for (int m = b.length; m < len; m++) {
                result[m] = 0; //不够位补0
            }
            buff.writeBytes(result);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
    }

    public byte get() {
        return buff.readByte();
    }

    public short getShort() {
        return buff.readShort();
    }

    public int getInt() {
        return buff.readInt();
    }

    public long getLong() {
        return buff.readLong();
    }

    public double getDouble() {
        return buff.readDouble();
    }

    public byte[] gets(int len) {
        byte[] data = new byte[len];
        buff.readBytes(data);
        return data;
    }

    // 将data字节型数据转换为0~255 (0xFF 即BYTE)。
    public short getUnsignedByte() {
        return buff.readUnsignedByte();
    }

    // 将data字节型数据转换为0~65535 (0xFFFF 即 WORD)。
    public int getUnsignedShort() {
        return buff.readUnsignedShort();
    }

    public long getUnsignedInt() {
        return buff.readUnsignedInt();
    }

    public String getString() {
        return buff.toString(Charset.forName("GBK"));
    }

    public String getString(int len) {
        return buff.toString(0, len, Charset.forName("GBK"));
    }

    /**
     * 转换成byte数组
     * @return
     */
    public byte[] toByteArray() {
        int pos = buff.writerIndex();
        byte[] data = new byte[pos];
        buff.readBytes(data);
        //再次调用重新从头读
        buff.resetReaderIndex();
        return data;
    }

    /**
     * 清空释放buff，在buff使用结束后调用
     * @return
     */
    public void release() {
        this.clear();
        //释放缓冲区内存
        ReferenceCountUtil.release(buff);
    }

    /**
     * 转换成byte数组并清空释放buff，在buff使用结束后调用
     * @return
     */
    public byte[] toByteArrayAndRelease() {
        int pos = buff.writerIndex();
        byte[] data = new byte[pos];
        buff.readBytes(data);
        this.clear();
        //释放缓冲区内存
        ReferenceCountUtil.release(buff);
        return data;
    }


}