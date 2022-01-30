package danger.app;

import com.middlemindgames.TyreSkOrig.MainWindow;

/*
 * Timer.java
 * Victor G. Brusca 01/15/2022
 */
public class Timer {
    public int duration;
    public boolean active;
    public boolean paused;
    public Application application;
    public MainWindow mainWindow;
    
    public Timer(int Dur, boolean Act, Application App) {
        duration = Dur;
        active = Act;
        paused = true;
        application = App;
    }
    
    public Timer(int Dur, boolean Act, MainWindow Mw) {
        duration = Dur;
        active = Act;
        paused = true;
        mainWindow = Mw;
    }    
    
    public void start() {
        active = true;
        paused = false;
    }
    
    public void stop() {
        active = false;
        paused = true;
    }
    
    public void pause() {
        paused = true;
    }
    
    public void unpause() {
        paused = false;
    }
}