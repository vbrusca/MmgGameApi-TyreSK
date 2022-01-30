package danger.ui;

import danger.util.Logger;
import java.util.Hashtable;
import net.middlemind.MmgGameApiJava.MmgBase.Mmg9Slice;
import net.middlemind.MmgGameApiJava.MmgBase.MmgBmp;
import net.middlemind.MmgGameApiJava.MmgBase.MmgColor;
import net.middlemind.MmgGameApiJava.MmgBase.MmgFont;
import net.middlemind.MmgGameApiJava.MmgBase.MmgHelper;
import net.middlemind.MmgGameApiJava.MmgBase.MmgPen;
import net.middlemind.MmgGameApiJava.MmgBase.MmgRect;
import net.middlemind.MmgGameApiJava.MmgBase.MmgVector2;

/*
 * Pen.java
 * Victor G. Brusca 01/20/2022
 */
public class Pen {

    public static int TXT_OFF_NORMAL_S_X = 0;
    public static int TXT_OFF_NORMAL_S_Y = 0;
    public static int TXT_OFF_NORMAL_M_X = 0;
    public static int TXT_OFF_NORMAL_M_Y = 0;
    public static int TXT_OFF_NORMAL_L_X = 0;
    public static int TXT_OFF_NORMAL_L_Y = 0;

    public static int TXT_OFF_BOLD_S_X = 6;
    public static int TXT_OFF_BOLD_S_Y = 0;
    public static int TXT_OFF_BOLD_M_X = 0;
    public static int TXT_OFF_BOLD_M_Y = 0;
    public static int TXT_OFF_BOLD_L_X = 0;
    public static int TXT_OFF_BOLD_L_Y = 0;

    public static int TXT_OFF_ITALIC_S_X = 0;
    public static int TXT_OFF_ITALIC_S_Y = 0;
    public static int TXT_OFF_ITALIC_M_X = 0;
    public static int TXT_OFF_ITALIC_M_Y = 0;
    public static int TXT_OFF_ITALIC_L_X = 0;
    public static int TXT_OFF_ITALIC_L_Y = 0;

    public enum FillRectMode {
        RECT,
        BMP,
        NINE_SLICE
    }

    public FillRectMode fillRectMode = FillRectMode.BMP;
    public int color;
    public int outlineColor;
    public Font ft;
    public MmgFont mmgFt;
    public MmgPen mmgPen;
    public static MmgBmp bground;
    public Mmg9Slice slc;
    public static Hashtable<Integer, MmgBmp> tiles;
    public static Hashtable<String, Mmg9Slice> slices;
    private java.awt.Color tmpc;

    public Pen() {
        Init();
    }

    public Pen(MmgPen pen) {
        mmgPen = pen;
        Init();
    }

    public void Init() {
        color = Color.WHITE;
        outlineColor = Color.WHITE;

        if (bground == null) {
            bground = MmgHelper.GetBasicCachedBmp("popup_window_base_24.png");
        }

        if (tiles == null) {
            tiles = new Hashtable<Integer, MmgBmp>();
            tiles.put(Color.BLACK, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetBlack()));
            tiles.put(Color.BLUE, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetBlue()));
            tiles.put(Color.WHITE, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetWhite()));
            tiles.put(16777215, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetWhite()));
            tiles.put(Color.RED, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetRed()));
            tiles.put(Color.GRAY15, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#262626")));
            tiles.put(Color.GRAY8, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#141414")));
            tiles.put(Color.GRAY0, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#999999")));
            tiles.put(10066329, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#999999")));
            tiles.put(Color.ORANGE, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#FFA500")));
            tiles.put(Color.PURPLE, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#800080")));
            tiles.put(Color.LIGHT_BLUE, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#525CB8")));
            tiles.put(Color.JAVA_GRAY, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#EEEEEE")));
            tiles.put(Color.DARK_RED, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#B85C52")));
            tiles.put(Color.NIGHT, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#050505")));
            tiles.put(Color.DARK_BLUE, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#081260")));
            tiles.put(-16248224, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#081260")));
            tiles.put(Color.DEAD_RED, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#B11919")));
            tiles.put(-5170919, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#B11919")));
            tiles.put(-16248224, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#081260")));
            tiles.put(Color.DARK_BLUE2, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#131D83")));
            tiles.put(-15524477, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#131D83")));
            tiles.put(-15194448, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#131D83")));
            tiles.put(Color.DARK_BLUE3, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetDecodedColor("#182688")));
            tiles.put(Color.TRANSPARENT, MmgHelper.CreateFilledBmp(1, 1, MmgColor.GetTransparent()));
        }

        if (slices == null) {
            slices = new Hashtable<String, Mmg9Slice>();
            slc = null;
            int w;
            int h;

            w = 240;
            h = 136;
            slc = new Mmg9Slice(MmgHelper.ScaleValue(4), bground, w, h);
            slc.SetPosition(new MmgVector2(0, 0));
            slc.SetWidth(w);
            slc.SetHeight(h);
            slices.put(w + "x" + h, slc);

            w = 102;
            h = 12;
            slc = new Mmg9Slice(MmgHelper.ScaleValue(4), bground, w, h);
            slc.SetPosition(new MmgVector2(0, 0));
            slc.SetWidth(w);
            slc.SetHeight(h);
            slices.put(w + "x" + h, slc);
        }
    }

    public Font getFont() {
        return ft;
    }

    public void setFont(Font f) {
        ft = f;
    }

    public void setTextOutlineColor(int c) {
        outlineColor = c;
    }

    public void setColor(int c) {
        //java.awt.Color c2 = new java.awt.Color(c);
        //Logger.wr("Pen: setColor: C:" + c + " R:" + c2.getRed() + " G:" + c2.getGreen() + " B:" + c2.getBlue() + " html:" + Color.SkColor2Html(c));
        color = c;
    }

    public void setColor(int r, int g, int b) {
        //Logger.wr("Pen: setColor called: R:" + r + " G:" + g + " B:" + b);
        setColor(new java.awt.Color(r, g, b).getRGB());
    }

    public void setColor(int r, int g, int b, int a) {
        //Logger.wr("Pen: setColor called: R:" + r + " G:" + g + " B:" + b);
        setColor(new java.awt.Color(r, g, b, a).getRGB());
    }    
    
    public MmgBmp getTile(int c) {
        if (tiles.containsKey(c)) {
            return tiles.get(c);
        } else {
            java.awt.Color c2 = new java.awt.Color(c);
            Logger.wr("getTile: tile missing for color: " + c + ", R: " + c2.getRed() + " G: " + c2.getGreen() + " B: " + c2.getBlue() + " A: " + c2.getAlpha());
            return tiles.get(Color.BLUE);
        }
    }

    public Mmg9Slice getSlice(int w, int h) {
        String key = w + "x" + h;
        if (slices.containsKey(key)) {
            return slices.get(key);
        } else {
            Logger.wr("getSlice: slice missing for width:" + w + " , height:" + h);
            return null;
        }
    }

    public void fillRect(int x1, int y1, int x2, int y2) {
        int w;
        int h;

        if (x2 > x1) {
            w = x2 - x1;
        } else {
            w = x1 - x2;
        }

        if (y2 > y1) {
            h = y2 - y1;
        } else {
            h = y1 - y2;
        }

        if (w == 0) {
            w = 1;
        }

        if (h == 0) {
            h = 1;
        }

        //Logger.wr("fillRect: x1:" + x1 + " y1:" + y1 + " x2:" + x2 + " y2:" + y2 + " w:" + w + " h:" + h);
        if (fillRectMode == FillRectMode.RECT) {
            mmgPen.DrawRect(x1, y1, w, h);
        } else if (fillRectMode == FillRectMode.BMP) {
            mmgPen.DrawBmp(getTile(color), new MmgRect(0, 0, 1, 1), new MmgRect(x1, y1, y1 + h, x1 + w));
        } else if (fillRectMode == FillRectMode.NINE_SLICE) {
            slc = getSlice(w, h);
            slc.SetPosition(x1, y1);
            slc.MmgDraw(mmgPen);
        }
    }

    public void fillRoundRect(int x1, int y1, int x2, int y2, int r1, int r2) {
        int w;
        int h;

        if (x2 > x1) {
            w = x2 - x1;
        } else {
            w = x1 - x2;
        }

        if (y2 > y1) {
            h = y2 - y1;
        } else {
            h = y1 - y2;
        }

        if (w == 0) {
            w = 1;
        }

        if (h == 0) {
            h = 1;
        }

        //Logger.wr("fillRoundRect: x1:" + x1 + " y1:" + y1 + " x2:" + x2 + " y2:" + y2 + " w:" + w + " h:" + h);
        if (fillRectMode == FillRectMode.RECT) {
            mmgPen.DrawRect(x1, y1, w, h);
        } else if (fillRectMode == FillRectMode.BMP) {
            mmgPen.DrawBmp(getTile(color), new MmgRect(0, 0, 1, 1), new MmgRect(x1, y1, y1 + h, x1 + w));
        } else if (fillRectMode == FillRectMode.NINE_SLICE) {
            slc = getSlice(w, h);
            slc.SetPosition(x1, y1);
            slc.MmgDraw(mmgPen);
        }
    }

    public void drawRoundRect(int x1, int y1, int x2, int y2, int r1, int r2) {
        //Logger.wr("drawRoundRect: x1:" + x1 + " y1:" + y1 + " x2:" + x2 + " y2:" + y2);
        int w;
        int h;

        if (x2 > x1) {
            w = x2 - x1;
        } else {
            w = x1 - x2;
        }

        if (y2 > y1) {
            h = y2 - y1;
        } else {
            h = y1 - y2;
        }

        if (w == 0) {
            w = 1;
        }

        if (h == 0) {
            h = 1;
        }

        tmpc = mmgPen.GetGraphics().getColor();
        mmgPen.GetGraphics().setColor(Color.SkColor2MmgColor(color).GetColor());
        mmgPen.DrawRect(x1, y1, w, h);
        mmgPen.SetGraphicsColor(tmpc);
    }

    public void drawRect(int x1, int y1, int x2, int y2) {
        //Logger.wr("drawRect: x1:" + x1 + " y1:" + y1 + " x2:" + x2 + " y2:" + y2);
        int w;
        int h;

        if (x2 > x1) {
            w = x2 - x1;
        } else {
            w = x1 - x2;
        }

        if (y2 > y1) {
            h = y2 - y1;
        } else {
            h = y1 - y2;
        }

        if (w == 0) {
            w = 1;
        }

        if (h == 0) {
            h = 1;
        }

        tmpc = mmgPen.GetGraphics().getColor();
        mmgPen.GetGraphics().setColor(Color.SkColor2MmgColor(color).GetColor());
        mmgPen.DrawRect(x1, y1, w, h);
        mmgPen.SetGraphicsColor(tmpc);        
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        //Logger.wr("drawLine: x1:" + x1 + " y1:" + y1 + " x2:" + x2 + " y2:" + y2);
        mmgPen.DrawBmp(getTile(color), new MmgRect(0, 0, 1, 1), new MmgRect(x1, y1, y2, x2));
    }

    public void drawBitmap(int x, int y, Bitmap bmp) {
        //Logger.wr("drawBitmap: x:" + x + " y:" + y);
        mmgPen.DrawBmp(bmp.mmgBmp, x, y);
    }

    public void drawBitmap(int x1, int y1, Bitmap bmp, int x2, int y2, int x3, int y3) {
        int w;
        int h;

        if (x3 > x2) {
            w = x3 - x2;
        } else {
            w = x2 - x3;
        }

        if (y3 > y2) {
            h = y3 - y2;
        } else {
            h = y2 - y3;
        }

        if (w == 0) {
            w = 1;
        }

        if (h == 0) {
            h = 1;
        }

        //Logger.wr("drawBitmap from-to: x1:" + x1 + " y1:" + y1 + " x2:" + x2 + " y2:" + y2 + " x3:" + x3 + " y3:" + y3 + " w:" + w + " h:" + h);
        mmgPen.DrawBmp(bmp.mmgBmp, new MmgRect(x2, y2, (y2 + h), (x2 + w)), new MmgRect(x1, y1, (y1 + h), (x1 + w)));
    }

    public void drawText(int x, int y, String s) {
        //Logger.wr("drawText: x:" + x + " y:" + y);
        ft.mmgFont.SetText(s);
        MmgColor c = ft.mmgFont.GetMmgColor();
        ft.mmgFont.SetMmgColor(Color.SkColor2MmgColor(color));

        if (ft.mmgFont.GetFontType() == MmgFont.FontType.BOLD) {
            if (ft.mmgFont.GetFontSize() == Font.FONT_SIZE_SMALL) {
                mmgPen.DrawText(ft.mmgFont, x + TXT_OFF_BOLD_S_X, y + TXT_OFF_BOLD_S_Y);
            } else if (ft.mmgFont.GetFontSize() == Font.FONT_SIZE_REGULAR) {
                mmgPen.DrawText(ft.mmgFont, x + TXT_OFF_BOLD_M_X, y + TXT_OFF_BOLD_M_Y);
            } else if (ft.mmgFont.GetFontSize() == Font.FONT_SIZE_LARGE) {
                mmgPen.DrawText(ft.mmgFont, x + TXT_OFF_BOLD_L_X, y + TXT_OFF_BOLD_L_Y);
            }

        } else if (ft.mmgFont.GetFontType() == MmgFont.FontType.NORMAL) {
            if (ft.mmgFont.GetFontSize() == Font.FONT_SIZE_SMALL) {
                mmgPen.DrawText(ft.mmgFont, x + TXT_OFF_NORMAL_S_X, y + TXT_OFF_NORMAL_S_Y);
            } else if (ft.mmgFont.GetFontSize() == Font.FONT_SIZE_REGULAR) {
                mmgPen.DrawText(ft.mmgFont, x + TXT_OFF_NORMAL_M_X, y + TXT_OFF_NORMAL_M_Y);
            } else if (ft.mmgFont.GetFontSize() == Font.FONT_SIZE_LARGE) {
                mmgPen.DrawText(ft.mmgFont, x + TXT_OFF_NORMAL_L_X, y + TXT_OFF_NORMAL_L_Y);
            }

        } else if (ft.mmgFont.GetFontType() == MmgFont.FontType.ITALIC) {
            if (ft.mmgFont.GetFontSize() == Font.FONT_SIZE_SMALL) {
                mmgPen.DrawText(ft.mmgFont, x + TXT_OFF_ITALIC_S_X, y + TXT_OFF_ITALIC_S_Y);
            } else if (ft.mmgFont.GetFontSize() == Font.FONT_SIZE_REGULAR) {
                mmgPen.DrawText(ft.mmgFont, x + TXT_OFF_ITALIC_M_X, y + TXT_OFF_ITALIC_M_Y);
            } else if (ft.mmgFont.GetFontSize() == Font.FONT_SIZE_LARGE) {
                mmgPen.DrawText(ft.mmgFont, x + TXT_OFF_ITALIC_L_X, y + TXT_OFF_ITALIC_L_Y);
            }
        }
        ft.mmgFont.SetMmgColor(c);
    }
}
