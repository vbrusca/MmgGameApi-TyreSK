package danger.ui;

import net.middlemind.MmgGameApiJava.MmgBase.MmgRect;

/*
 * Rect.java
 * Victor G. Brusca 01/20/2022
 */
public class Rect {
    public int x1;
    public int x2;
    public int y1;
    public int y2;
    public int w;
    public int h;
    public MmgRect mmgRect;
    
    public Rect(int w, int h) {
        Init(0, 0, w, h);
    }
    
    public Rect(int X1, int Y1, int X2, int Y2) {
        Init(X1, Y1, X2, Y2);
    }
    
    public final void Init(int X1, int Y1, int X2, int Y2) {
        x1 = X1;
        x2 = X2;
        y1 = Y1;
        y2 = Y2;
        if(x1 > x2) {
            w = x1 - x2;
        } else {
            w = x2 - x1;
        }
        
        if(y1 > y2) {
            h = y1 - y2;
        } else {
            h = y2 - y1;
        }
        
        mmgRect = new MmgRect(x1, y1, y2, x2);
    }
}