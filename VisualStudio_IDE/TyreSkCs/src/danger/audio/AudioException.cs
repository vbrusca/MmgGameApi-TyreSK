using System;
using System.Collections.Generic;
using System.Text;

namespace danger.audio {
    /*
     * AudioException.java
     * Victor G. Brusca 01/22/2022
     */
    public class AudioException : Exception {
        public AudioException(string msg) : base(msg) {
        }
    }
}
