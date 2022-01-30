using com.middlemindgames.TyreSkOrig;
using danger.util;
using Microsoft.Xna.Framework.Graphics;
using net.middlemind.MmgGameApiCs.MmgBase;
using System;
using System.Collections.Generic;

namespace danger.ui {
    /*
     * Bitmap.java
     * Victor G. Brusca 01/22/2022
     */
    public class Bitmap {
        public static bool CACHE_PEN = true; //default=true
        public static bool CACHE_ROTATION = true; //default=true

        public static bool FORCE_CREATE_PEN_COPY_BITS = false; //default=false
        public int transparent = Color.TRANSPARENT;
        public MmgBmp mmgBmp;
        public Pen pen;
        public Dictionary<int, Bitmap> rotCache = new Dictionary<int, Bitmap>();

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

        public Bitmap(string baseDir, string file) {
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

        public Pen createPen() {
            if (CACHE_PEN && pen != null) {
                return pen;
            } else {
                GraphicsDevice graphics = mmgBmp.GetImage().GraphicsDevice;
                Pen p = new Pen();
                p.mmgPen = new MmgPen();
                p.mmgPen.SetGraphics(new SpriteBatch(graphics));
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
            if (CACHE_ROTATION && rotCache.ContainsKey(angle) == true) {
                return rotCache[angle];
            } else {
                Bitmap tBmp = new Bitmap(MmgBmpScaler.RotateMmgBmp(mmgBmp, angle, true));
                rotCache.Add(angle, tBmp);
                return tBmp;
            }
        }

        public static void copyBits(Bitmap bmp1, Rect r1, Bitmap bmp2, Rect r2) {
            //Logger.wr("copyBits: bmp1:" + bmp1.mmgBmp.name + " r1:" + r1.ToString() + " bmp2:" + bmp2.mmgBmp.name + " r2:" + r2.ToString());
            if (FORCE_CREATE_PEN_COPY_BITS == true) {
                Pen p = bmp1.createPen();
                p.mmgPen.DrawBmp(bmp2.mmgBmp, r2.mmgRect, r1.mmgRect);
            } else {
                bmp1.pen.mmgPen.GetGraphics().GraphicsDevice.SetRenderTarget((RenderTarget2D)bmp1.mmgBmp.GetTexture2D());
                bmp1.pen.mmgPen.GetGraphics().Begin(SpriteSortMode.Immediate, BlendState.NonPremultiplied);
                bmp1.pen.mmgPen.DrawBmp(bmp2.mmgBmp, r2.mmgRect, r1.mmgRect);
                bmp1.pen.mmgPen.GetGraphics().End();
                bmp1.pen.mmgPen.GetGraphics().GraphicsDevice.SetRenderTarget(null);
            }
        }

        public static void copyBits(Pen pen, Bitmap bmp1, Rect r1, Bitmap bmp2, Rect r2) {
            pen.mmgPen.DrawBmp(bmp2.mmgBmp, r2.mmgRect, r1.mmgRect);
        }
    }
}
