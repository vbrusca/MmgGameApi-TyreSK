package danger.util;

import java.nio.ByteBuffer;

/*
 * ByteArray.java
 * Victor G. Brusca 01/30/2022
 */
public class ByteArray {
    public static int readInt(byte[] b, int off) {
        return ByteBuffer.wrap(new byte[] {b[off + 3], b[off + 2], b[off + 1], b[off + 0]}).getInt();
    }
}