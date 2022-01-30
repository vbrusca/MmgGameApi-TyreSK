using danger.app;
using danger.util;

namespace danger.ui {
    /*
     * ScreenWindow.java
     * Victor G. Brusca 01/20/2022
     */
    public class ScreenWindow {
        public void show() {

        }

        public void aboutToShowMenu() {
            //Logger.wr("aboutToShowMenu");
        }

        public virtual bool eventWidgetDown(int inWhichWidget, Event inEvent) {
            return false;
        }

        public virtual bool eventWidgetUp(int inWhichWidget, Event inEvent) {
            return false;
        }
    }
}
