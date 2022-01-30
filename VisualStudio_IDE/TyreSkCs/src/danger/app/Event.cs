using System;
using System.Collections.Generic;
using System.Text;

namespace danger.app {
    /*
     * Event.java
     * Victor G. Brusca 01/15/2022
     */
    public class Event {
        public const int EVENT_TIMER = 0;
        public const int DEVICE_BUTTON_MENU = 1;
        public const int DEVICE_BUTTON_JUMP = 2;
        public const int DEVICE_BUTTON_CANCEL = 3;
        public const int DEVICE_BUTTON_BACK = 4;
        public const int DEVICE_WHEEL_PAGE_DOWN = 5;
        public const int DEVICE_WHEEL = 6;
        public const int DEVICE_ARROW_DOWN = 7;
        public const int DEVICE_ARROW_RIGHT = 8;
        public const int DEVICE_ARROW_UP = 9;
        public const int DEVICE_ARROW_LEFT = 10;
        public const int DEVICE_WHEEL_PAGE_UP = 11;
        public const int DEVICE_WHEEL_BUTTON = 12;

        public int type;

        public Event() {
        }

        public Event(int Type) {
            type = Type;
        }

        public int getType() {
            return type;
        }

        public void setType(int Type) {
            type = Type;
        }
    }
}