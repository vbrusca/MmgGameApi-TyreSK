using System;
using System.Collections.Generic;
using System.Text;

namespace danger.util {
    /*
     * ByteArray.java
     * Victor G. Brusca 01/30/2022
     */
    public class ByteArray {
        public static int readInt(byte[] b, int off) {
            return BitConverter.ToInt32(b, off);
        }
    }
}
