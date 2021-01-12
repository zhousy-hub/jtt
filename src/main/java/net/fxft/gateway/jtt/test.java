package net.fxft.gateway.jtt;

import net.fxft.gateway.jtt.util.ByteUtil;

import java.io.OutputStream;
import java.net.Socket;

public class test {

    public static void main(String[] args) throws Exception
    {
        byte[] test = ByteUtil.hexStrToBytes("7E 00 3B 0D AD 0A C3 F6 41 43 38 39 32 35 BB C6 07 E1 08 01 02 3B 03 B0 07 15 C1 C2 01 91 04 92 00 00 00 1E 00 00 00 3F 00 00 EB 06 00 00 00 00 00 03 00 03 00 00 00 00 00 00 00 00 63 3B");
        boolean start = true;
        Socket conn = new Socket("47.110.60.83", 6135);
        OutputStream os = conn.getOutputStream();

        int len = -1;
        byte[] block = new byte[512];
        while (start)
        {
            try{
                os.write(test, 0, test.length);
                os.flush();
                //Thread.sleep(10);
                System.out.println("sending...");
            }
            catch (Exception e)
            {
                System.out.println("e = " + e);
            }

        }
        os.close();

        conn.close();
    }

}
