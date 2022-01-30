package danger.ui;

import danger.app.Event;
import danger.util.Logger;

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

    public boolean eventWidgetDown(int inWhichWidget, Event inEvent) {
        return false;
    }
    
    public boolean eventWidgetUp(int inWhichWidget, Event inEvent) {
        return false;
    }
}