
namespace danger.app {
    /*
     * Resource.java
     * Victor G. Brusca 01/15/2022
     */
    public class Resource {
        public byte[] res;

        public Resource(byte[] data) {
            res = data;
        }

        public byte[] getBytes() {
            return res;
        }

        public byte[] getBytes(byte[] b, int off, int len) {
            byte[] br = new byte[b.Length];
            for (int i = off; i < len; i++) {
                b[i] = res[i];
                br[i] = res[i];
            }
            return br;
        }

        public int getSize() {
            return res.Length;
        }
    }
}