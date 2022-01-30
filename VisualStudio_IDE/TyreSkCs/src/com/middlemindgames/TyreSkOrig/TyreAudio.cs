using danger.audio;
using danger.system;
using danger.util;
using System;

namespace com.middlemindgames.TyreSkOrig {
    /*
     * TyreAudio.java
     * Victor G. Brusca 01/16/2022
     * Created on June 1, 2005, 10:57 PM by Middlemind Games
     */
    public class TyreAudio : Resources {
        public static int tmpIndex = -1;
        public static int index = -1;
        public static int prevIndex = 1;
        public static bool sAudioReady = false;
        public static bool sLoopStarted = false;

        public static void startMusic(int idx) {
            try {
                if (!MainWindow.hasSound) {
                    return;
                }

                //STOP CODE WAS HERE
                tmpIndex = index;
                if (idx != index) {
                    try {
                        prevIndex = index;
                        index = idx;
                        Tyre.sequences[idx].startWithFilter(ToneFilter.NONE);
                        sLoopStarted = true;
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }

                if (idx != tmpIndex && tmpIndex != -1) {
                    try {
                        Tyre.sequences[tmpIndex].stop();
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
        }

        public static void stopMusic() {
            try {
                Tyre.sequences[index].stop();
                index = -1;
                sLoopStarted = false;
            } catch (Exception e) {
                Logger.wr(e.Message);
                Logger.wr(e.StackTrace);
            }
        }

        public static void prepareAudio() {
            Meta.muteID(Meta.FEEDBACK_KEY_ONCE);
            Meta.muteID(Meta.FEEDBACK_KEY_REPEAT);
            Meta.muteID(Meta.FEEDBACK_PAGE_DOWN);
            Meta.muteID(Meta.FEEDBACK_PAGE_UP);
            Meta.muteID(Meta.FEEDBACK_LEFT_SHOULDER_BUTTON);
            Meta.muteID(Meta.FEEDBACK_RIGHT_SHOULDER_BUTTON);
            Meta.muteID(Meta.SCROLL_WHEEL_UP);
            Meta.muteID(Meta.SCROLL_WHEEL_DOWN);
            Meta.muteID(Meta.BEEP_ACTION_FAIL);
            Meta.muteID(Meta.BEEP_ACTION_SUCCESS);
            Meta.muteID(Meta.BEEP_COMMAND_ACCEPTED);
            Meta.muteID(Meta.SCROLL_LIMIT);
        }

        public static void pause() {
            if (sLoopStarted) {
                if (Tyre.sequences[index] != null) {
                    if (Tyre.sequences[index].isPaused() == false) {
                        Tyre.sequences[index].pause();
                    }
                }
            }
        }

        public static void resume() {
            if (sLoopStarted) {
                if (Tyre.sequences[index] != null) {
                    if (Tyre.sequences[index].isPaused()) {
                        Tyre.sequences[index].resume();
                    }
                }
            }
        }

        public static void cleanup() {
            if (Tyre.sequences != null) {
                for (int i = 0; i < Tyre.sequences.Length; i++) {
                    if (Tyre.sequences[i] != null) {
                        Tyre.sequences[i].stop();
                        Tyre.sequences[i].destroy();
                        Tyre.sequences[i] = null;
                    }
                }
            }

            Meta.unmuteID(Meta.FEEDBACK_KEY_ONCE);
            Meta.unmuteID(Meta.FEEDBACK_KEY_REPEAT);
            Meta.unmuteID(Meta.FEEDBACK_PAGE_DOWN);
            Meta.unmuteID(Meta.FEEDBACK_PAGE_UP);
            Meta.unmuteID(Meta.FEEDBACK_WHEEL_BUTTON);
            Meta.unmuteID(Meta.FEEDBACK_LEFT_SHOULDER_BUTTON);
            Meta.unmuteID(Meta.FEEDBACK_RIGHT_SHOULDER_BUTTON);
            Meta.unmuteID(Meta.SCROLL_WHEEL_UP);
            Meta.unmuteID(Meta.SCROLL_WHEEL_DOWN);
            Meta.unmuteID(Meta.BEEP_ACTION_FAIL);
            Meta.unmuteID(Meta.BEEP_ACTION_SUCCESS);
            Meta.unmuteID(Meta.BEEP_COMMAND_ACCEPTED);
            Meta.unmuteID(Meta.SCROLL_LIMIT);

            sAudioReady = false;
            sLoopStarted = false;
            index = -1;
        }
    }
}