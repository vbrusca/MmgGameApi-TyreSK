package danger.audio;

import danger.util.Logger;

/*
 * AudioManager.java
 * Victor G. Brusca 01/22/2022
 */
public class AudioManager {
    public static void vibrate(int ms) {
        Logger.wr("AudioManager: vibrate: " + ms + "ms");
    }
    
    public static Sequence createSequence(boolean b) throws AudioException {
        return new Sequence();
    }
}