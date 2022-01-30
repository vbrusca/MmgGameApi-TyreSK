using System;

namespace danger.util {
    /*
     * Logger.java
     * Victor G. Brusca 01/30/2022
     */
    public class Logger {
        public static bool LOGGING_ON = true;

        public static void wr(String s) {
            System.Diagnostics.Debug.WriteLine(s);
            Console.WriteLine(s);
        }
    }
}