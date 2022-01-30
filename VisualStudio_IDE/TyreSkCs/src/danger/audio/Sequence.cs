using net.middlemind.MmgGameApiCs.MmgBase;
using System;
using System.Collections.Generic;
using System.Text;

namespace danger.audio {
    /*
     * Sequence.java
     * Victor G. Brusca 01/22/2022
     */
    public class Sequence {
        public int loops;
        public MmgSound snd;
        public ToneFilter tf;
        public bool paused;
        public bool stopped;
        public bool started;

        public Sequence() {
            snd = null;
            paused = false;
            stopped = true;
            started = false;
        }

        public void destroy() {
            snd = null;
        }

        public bool loadFromFile(MmgSound s) {
            snd = s;
            return true;
        }

        public bool loadFromFile(byte[] b) {
            snd = MmgHelper.GetBinarySound(b);
            return true;
        }

        public void start(ToneFilter toneFilter) {
            snd.Play(loops, 0.0f);
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

        public bool isPaused() {
            return paused;
        }

        public bool isStarted() {
            return started;
        }

        public bool isStopped() {
            return stopped;
        }
    }
}