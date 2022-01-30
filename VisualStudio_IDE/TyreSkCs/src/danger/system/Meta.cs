using danger.util;
using System;
using System.Collections.Generic;
using System.Text;

namespace danger.system {
    /*
     * Meta.java
     * Victor G. Brusca 01/22/2022
     */
    public class Meta {
        public static int FEEDBACK_KEY_ONCE = 0;
        public static int FEEDBACK_KEY_REPEAT = 1;
        public static int FEEDBACK_PAGE_DOWN = 2;
        public static int FEEDBACK_PAGE_UP = 3;
        public static int FEEDBACK_LEFT_SHOULDER_BUTTON = 4;
        public static int FEEDBACK_RIGHT_SHOULDER_BUTTON = 5;
        public static int SCROLL_WHEEL_UP = 6;
        public static int SCROLL_WHEEL_DOWN = 7;
        public static int BEEP_ACTION_FAIL = 8;
        public static int BEEP_ACTION_SUCCESS = 9;
        public static int BEEP_COMMAND_ACCEPTED = 10;
        public static int SCROLL_LIMIT = 11;
        public static int FEEDBACK_WHEEL_BUTTON = 12;
        public static int BEEP_COMMAND_REJECTED = 13;
        public static int MENU_ACTIVATION = 14;
        public static int MENU_DISMISS = 15;
        public static int CHARGER_DISCONNECTED = 16;
        public static int DELETE_TRASH = 17;
        public static int FEEDBACK_HEADSET_BUTTON = 18;
        public static Dictionary<string, bool> mute = new Dictionary<string, bool>();

        public static void muteID(int id) {
            mute.Add(id + "", true);
        }

        public static void unmuteID(int id) {
            mute.Remove(id + "");
        }

        public static void unmuteAll() {
            mute.Clear();
        }

        public static void play(int id) {
            if (mute.ContainsKey(id + "") == false) {
                Logger.wr("play: id " + id + " is muted");
            }
        }

        public static void list() {
            foreach (string id in mute.Keys) {
                Logger.wr("list: id " + id + " is muted");
            }
        }
    }
}