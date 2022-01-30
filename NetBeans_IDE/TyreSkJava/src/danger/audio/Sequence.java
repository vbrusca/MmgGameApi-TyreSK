package danger.audio;

import net.middlemind.MmgGameApiJava.MmgBase.MmgHelper;
import net.middlemind.MmgGameApiJava.MmgBase.MmgSound;

/*
 * Sequence.java
 * Victor G. Brusca 01/22/2022
 */
public class Sequence {
    public int loops;
    public MmgSound snd;
    public ToneFilter tf;
    public boolean paused;
    public boolean stopped;
    public boolean started;    
    
    public Sequence() {
        snd = null;
        paused = false;
        stopped = true;
        started = false;
    }
    
    public void destroy() {
        snd = null;
    }
    
    public boolean loadFromFile(byte[] b) {
        snd = MmgHelper.GetBinarySound(b);
        return true;
    }
    
    public void start(ToneFilter toneFilter) {
        snd.Play(loops, -1.0f);
        tf = toneFilter;
        started = true;
        stopped = false;
        paused = false;
    }
    
    public void startWithFilter(ToneFilter toneFilter) {
        start(toneFilter);
    }    
    
    public void setLoops(int l) {        
        loops = l;
    }
    
    public void stop() {
        snd.Stop();
        started = true;
        paused = false;
        stopped = true;        
    }
    
    public void resume() {
        snd.Play();
        paused = false;
    }

    public void pause() {
        snd.Stop();
        paused = true;
    }
    
    public boolean isPaused() {
        return paused;
    }
    
    public boolean isStarted() {
        return started;
    }
    
    public boolean isStopped() {
        return stopped;
    }
}