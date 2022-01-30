package danger.system;

import danger.util.Logger;
import java.util.Hashtable;
import java.util.Map;

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
    public static Map<String, Boolean> mute = new Hashtable();
    
    public static void muteID(int id) {
        mute.put(id + "", Boolean.TRUE);
    }
    
    public static void unmuteID(int id) {
        mute.remove(id + "");
    }
    
    public static void unmuteAll() {
        mute.clear();
    }
    
    public static void play(int id) {
        if(mute.containsKey(id + "") == false) {
            Logger.wr("play: id " + id + " is muted");
        }
    }
    
    public static void list() {
        for(String id : mute.keySet()) {
            Logger.wr("list: id " + id + " is muted");            
        }
    }
}