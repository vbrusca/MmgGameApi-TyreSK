using danger.util;
using net.middlemind.MmgGameApiCs.MmgBase;
using System;
using System.Collections.Generic;
using System.Text;

namespace danger.ui {
    /*
     * Color.java
     * Victor G. Brusca 01/20/2022
     */
    public class Color {
        public static int WHITE = GetColorAsInt("#FFFFFF");
        public static int BLACK = GetColorAsInt("#000000");
        public static int RED = GetColorAsInt("#FF0000");
        public static int GRAY15 = GetColorAsInt("#262626");
        public static int GRAY8 = GetColorAsInt("#141414");
        public static int GRAY0 = GetColorAsInt("#999999");
        public static int ORANGE = GetColorAsInt("#FFA500");
        public static int PURPLE = GetColorAsInt("#800080");
        public static int BLUE = GetColorAsInt("#0000FF");
        public static int LIGHT_BLUE = GetColorAsInt("#525CB8");
        public static int JAVA_GRAY = GetColorAsInt("#EEEEEE");
        public static int DARK_RED = GetColorAsInt("#B85C52");
        public static int NIGHT = GetColorAsInt("#050505");
        public static int DARK_BLUE = GetColorAsInt("#081260");
        public static int DARK_BLUE2 = GetColorAsInt("#131D83");
        public static int DARK_BLUE3 = GetColorAsInt("#182688");
        public static int DEAD_RED = GetColorAsInt("#B11919");
        public static int TRANSPARENT = (int)MmgColor.GetTransparent().GetColor().PackedValue;
 
        public static int GetColorAsInt(string html) {
            System.Drawing.Color c = System.Drawing.ColorTranslator.FromHtml(html);
            return (int)(new Microsoft.Xna.Framework.Color(c.R, c.G, c.B, c.A).PackedValue);
        }

        public static int GetColorAsInt(int r, int g, int b) {
            return GetColorAsInt(r, g, b, 255);
        }

        public static int GetColorAsInt(int r, int g, int b, int a) {
            return (int)(new Microsoft.Xna.Framework.Color(r, g, b, a).PackedValue);
        }

        public static MmgColor GetColorFromInt(int ic) {            
            return new MmgColor(new Microsoft.Xna.Framework.Color((uint)ic));
        }

        public static MmgColor SkColor2MmgColor(int c) {
            if (c == WHITE || c == 16777215) {
                return MmgColor.GetWhite();
            } else if (c == BLACK || c == 0) {
                return MmgColor.GetBlack();
            } else if (c == RED) {
                return MmgColor.GetRed();
            } else if (c == GRAY15) {
                return MmgColor.GetDecodedColor("#262626");
            } else if (c == GRAY8) {
                return MmgColor.GetDecodedColor("#141414");
            } else if (c == GRAY0) {
                return MmgColor.GetDecodedColor("#999999");
            } else if (c == ORANGE) {
                return MmgColor.GetDecodedColor("#FFA500");
            } else if (c == PURPLE) {
                return MmgColor.GetDecodedColor("#800080");
            } else if (c == BLUE) {
                return MmgColor.GetBlue();
            } else if (c == LIGHT_BLUE) {
                return MmgColor.GetDecodedColor("#525CB8");
            } else if (c == JAVA_GRAY) {
                return MmgColor.GetDecodedColor("#EEEEEE");
            } else if (c == DARK_RED) {
                return MmgColor.GetDecodedColor("#B85C52");
            } else if (c == NIGHT) {
                return MmgColor.GetDecodedColor("#050505");
            } else if (c == DARK_BLUE || c == -16248224) {
                return MmgColor.GetDecodedColor("#081260");
            } else if (c == DARK_BLUE2 || c == -15524477 || c == -15194448) {
                return MmgColor.GetDecodedColor("#131D83");
            } else if (c == DARK_BLUE3) {
                return MmgColor.GetDecodedColor("#182688");
            } else if (c == DEAD_RED) {
                return MmgColor.GetDecodedColor("#B11919");
            } else if(c == TRANSPARENT) {
                return MmgColor.GetTransparent();
            } else {
                System.Drawing.Color c2 = System.Drawing.Color.FromArgb(c);
                Logger.wr("SkColor2MmgColor: Could not find matching color for value: " + c + " returning custom color, R:" + c2.R + " G:" + c2.G + " B:" + c2.B + " A:" + c2.A);
                return GetColorFromInt(c);
            }
        }

        public static String SkColor2Html(int c) {
            if (c == WHITE || c == 16777215) {
                return "#FFFFFF";
            } else if (c == BLACK || c == 0) {
                return "#000000";
            } else if (c == RED) {
                return "#FF0000";
            } else if (c == GRAY15) {
                return "#262626";
            } else if (c == GRAY8) {
                return "#141414";
            } else if (c == GRAY0) {
                return "#999999";
            } else if (c == ORANGE) {
                return "#FFA500";
            } else if (c == PURPLE) {
                return "#800080";
            } else if (c == BLUE) {
                return "#0000FF";
            } else if (c == LIGHT_BLUE) {
                return "#525CB8";
            } else if (c == JAVA_GRAY) {
                return "#EEEEEE";
            } else if (c == DARK_RED) {
                return "#B85C52";
            } else if (c == NIGHT) {
                return "#050505";
            } else if (c == DARK_BLUE || c == -16248224) {
                return "#081260";
            } else if (c == DARK_BLUE2 || c == -15524477 || c == -15194448) {
                return "#131D83";
            } else if (c == DARK_BLUE3) {
                return "#182688";
            } else if (c == DEAD_RED) {
                return "#B11919";
            } else if (c == TRANSPARENT) {
                return "transparent";
            } else {
                return "unknown";
            }
        }
    }
}