package danger.ui;

import com.middlemindgames.TyreSkOrig.MainWindow;
import java.awt.Graphics2D;
import java.util.Hashtable;
import java.util.Map;
import net.middlemind.MmgGameApiJava.MmgBase.MmgBmp;
import net.middlemind.MmgGameApiJava.MmgBase.MmgBmpScaler;
import net.middlemind.MmgGameApiJava.MmgBase.MmgColor;
import net.middlemind.MmgGameApiJava.MmgBase.MmgHelper;
import net.middlemind.MmgGameApiJava.MmgBase.MmgPen;

/*
 * Bitmap.java
 * Victor G. Brusca 01/22/2022
 */
public class Bitmap {
    public static boolean CACHE_PEN = true; //default=true
    public static boolean CACHE_ROTATION = true; //default=true
    
    public static boolean FORCE_CREATE_PEN_COPY_BITS = true; //default=false
    public int transparent = Color.TRANSPARENT;
    public MmgBmp mmgBmp;
    public Pen pen;
    public Map<Integer, Bitmap> rotCache = new Hashtable();
    
    public Bitmap(MmgBmp bmp) {
        mmgBmp = bmp;
        createPen();
    }
    
    public Bitmap(int w, int h) {
        mmgBmp = MmgHelper.CreateDrawableBmpSet(w, h, true).img;
        createPen();
    }
    
    public Bitmap(int w, int h, MmgColor c) {
        mmgBmp = MmgHelper.CreateDrawableBmpSet(w, h, true, c).img;
        createPen();
    }    
    
    public Bitmap(String baseDir, String file) {
        mmgBmp = MmgHelper.GetBasicCachedBmp(file);
        createPen();
    }
    
    public int getWidth() {
        return mmgBmp.w;
    }
    
    public int getHeight() {
        return mmgBmp.h;
    }
    
    public static Bitmap newFromPNGData(byte[] b) {
        return new Bitmap(MmgHelper.GetBinaryBmp(b));
    }
    
    public final Pen createPen() {
        if(CACHE_PEN && pen != null) {
            return pen;
        } else {
            Graphics2D graphics = (Graphics2D)mmgBmp.GetImage().getGraphics();
            Pen p = new Pen();
            p.mmgPen = new MmgPen();
            p.mmgPen.SetGraphics(graphics);
            p.mmgPen.SetAdvRenderHints();
            p.setColor(Color.WHITE);        
            p.setFont(MainWindow.ft);
            pen = p;
            return p;
        }
    }
    
    public void setTransparentColor(int c) {
        transparent = c;
    }
    
    public Bitmap rotateBy(int angle) {
        //Logger.wr("rotateBy: angle:" + angle);
        if(CACHE_ROTATION && rotCache.containsKey(angle) == true) {
            return rotCache.get(angle);
        } else {
            Bitmap tBmp = new Bitmap(MmgBmpScaler.RotateMmgBmp(mmgBmp, angle, true));
            rotCache.put(angle, tBmp);
            return tBmp;
        }
    }
    
    public static void copyBits(Bitmap bmp1, Rect r1, Bitmap bmp2, Rect r2) {
        //Logger.wr("copyBits: bmp1:" + bmp1.mmgBmp.name + " r1:" + r1.toString() + " bmp2:" + bmp2.mmgBmp.name + " r2:" + r2.toString());
        if(FORCE_CREATE_PEN_COPY_BITS == true) {
            Pen p = bmp1.createPen();
            p.mmgPen.DrawBmp(bmp2.mmgBmp, r2.mmgRect, r1.mmgRect);
        } else {
            bmp1.pen.mmgPen.DrawBmp(bmp2.mmgBmp, r2.mmgRect, r1.mmgRect);
        }
    }
    
    public static void copyBits(Pen pen, Bitmap bmp1, Rect r1, Bitmap bmp2, Rect r2) {
        pen.mmgPen.DrawBmp(bmp2.mmgBmp, r2.mmgRect, r1.mmgRect);
    }    
}