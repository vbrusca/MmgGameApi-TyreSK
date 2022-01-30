using System;
using System.Collections.Generic;
using System.Text;

namespace danger.system {
    /*
     * Pasteboard.java
     * Victor G. Brusca 01/22/2022
     */
    public class Pasteboard {
        public static string val = "";

        public static void setString(string s) {
            val = s;
        }

        public static string getString() {
            return val;
        }
    }
}