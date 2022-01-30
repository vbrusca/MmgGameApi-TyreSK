
namespace com.middlemindgames.dat {
    /*
     * npcdata.java
     * Victor G. Brusca 01/16/2022
     * Created on Dec 8, 2006, 1:38 PM by Middlemind Games
     */

    public class npcdata {
        public int stringIndex;
        public int fc;
        public int fr;
        public int fl;

        public int bc;
        public int br;
        public int bl;

        public int rc;
        public int rr;
        public int rl;

        public int lc;
        public int lr;
        public int ll;

        public int posX;
        public int posY;
        public int dir;
        public bool canWalk;
        public int walkFreeze;
        public int toggle = 0;
        public int speed;
        public int height;
        public int width;
        public int npcType;

        public npcdata() {
            walkFreeze = 14;
            speed = 2;
            canWalk = false;
        }

        public void setCanWalk(int cw) {
            if (cw == 1) {
                canWalk = true;
            } else {
                canWalk = false;
            }
        }
    }
}