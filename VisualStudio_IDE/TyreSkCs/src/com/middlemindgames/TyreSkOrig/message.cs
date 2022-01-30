using danger.ui;
using System;

namespace com.middlemindgames.TyreSkOrig {
    /*
     * message.java
     * Victor G. Brusca 01/16/2022
     * Created on January 21, 2006, 8:00 PM
     */
    public class message {
        public int color;
        public string val;

        public message() {
            color = Color.WHITE;
        }

        public override string ToString() {
            return val;
        }
    }
}