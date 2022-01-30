using System;
using System.Collections.Generic;
using System.Text;

namespace com.middlemindgames.dat {
    /*
     * tile.java
     * Victor G. Brusca 01/16/2022
     * Created on June 1, 2005, 10:57 PM by Middlemind Games
     */
    public class tile {
        public int imageIndex;
        public bool canWalk;

        public tile() {
        }

        public void setCanWalk(int CW) {
            if (CW == 0) {
                canWalk = false;
            } else {
                canWalk = true;
            }
        }
    }
}
