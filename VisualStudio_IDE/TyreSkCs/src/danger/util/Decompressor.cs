using net.jbcg.Compression;
using System;

namespace danger.util {
    /*
     * Decompressor.java
     * Victor G. Brusca 01/30/2022
     */
    public class Decompressor {        
        public byte[] input;
        public bool started = false;
        public bool stopped = false;

        public void reset() {
            input = null;
            started = false;
            stopped = false;
        }

        public void setInput(byte[] b, int off, int len) {
            //Logger.wr("setInput: Off:" + off + " Len:" + len + " b.length:" + b.Length);
            input = new byte[len - 4];
            Array.Copy(b, off, input, 0, len - 4);
            //Logger.wr("setInput: input.length: " + input.Length);
        }

        public int inflate(out byte[] b, int off, int len) {
            started = true;
            stopped = false;

            try {
                //Logger.wr("=========================================================");
                //Logger.wr("Decompressor: Input Len:" + input.Length);
                ZipSimple zippy = new ZipSimple();
                b = zippy.Decompress(input);
                int length = b.Length;
                //Logger.wr("Decompressor: Output Len:" + length);
                end();
                return length;
            } catch (Exception ex) {
                Logger.wr(ex.Message);
                Logger.wr(ex.StackTrace);
            }

            b = null;
            return -1;
        }

        public void end() {
            stopped = true;
        }

        public bool finished() {
            if (started && stopped) {
                return true;
            } else {
                return false;
            }
        }
    }
}