package danger.ui;

import danger.util.Logger;
import net.middlemind.MmgGameApiJava.MmgBase.MmgColor;

/*
 * Color.java
 * Victor G. Brusca 01/20/2022
 */
public class Color {
    public static int WHITE = java.awt.Color.decode("#FFFFFF").getRGB();
    public static int BLACK = java.awt.Color.decode("#000000").getRGB();
    public static int RED = java.awt.Color.decode("#FF0000").getRGB();
    public static int GRAY15 = java.awt.Color.decode("#262626").getRGB();
    public static int GRAY8 = java.awt.Color.decode("#141414").getRGB();    
    public static int GRAY0 = java.awt.Color.decode("#999999").getRGB();
    public static int ORANGE = java.awt.Color.decode("#FFA500").getRGB();
    public static int PURPLE = java.awt.Color.decode("#800080").getRGB();
    public static int BLUE = java.awt.Color.decode("#0000FF").getRGB();
    public static int LIGHT_BLUE = java.awt.Color.decode("#525CB8").getRGB();
    public static int JAVA_GRAY = java.awt.Color.decode("#EEEEEE").getRGB();
    public static int DARK_RED = java.awt.Color.decode("#B85C52").getRGB();
    public static int NIGHT = java.awt.Color.decode("#050505").getRGB();
    public static int DARK_BLUE = java.awt.Color.decode("#081260").getRGB();
    public static int DARK_BLUE2 = java.awt.Color.decode("#131D83").getRGB();
    public static int DARK_BLUE3 = java.awt.Color.decode("#182688").getRGB();    
    public static int DEAD_RED = java.awt.Color.decode("#B11919").getRGB();
    public static int TRANSPARENT = MmgColor.GetTransparent().GetColor().getRGB();
    
    public static MmgColor SkColor2MmgColor(int c) {
        if(c == WHITE || c == 16777215) {
            return MmgColor.GetWhite();
        } else if(c == BLACK || c == 0) {
            return MmgColor.GetBlack();
        } else if(c == RED) {
            return MmgColor.GetRed();
        } else if(c == GRAY15) {
            return MmgColor.GetDecodedColor("#262626");
        } else if(c == GRAY8) {
            return MmgColor.GetDecodedColor("#141414");
        } else if (c == GRAY0) {
            return MmgColor.GetDecodedColor("#999999");
        } else if(c == ORANGE) {
            return MmgColor.GetDecodedColor("#FFA500");
        } else if(c == PURPLE) {
            return MmgColor.GetDecodedColor("#800080");
        } else if(c == BLUE) {
            return MmgColor.GetBlue();
        } else if(c == LIGHT_BLUE) {
            return MmgColor.GetDecodedColor("#525CB8");
        } else if(c == JAVA_GRAY) {
            return MmgColor.GetDecodedColor("#EEEEEE");            
        } else if(c == DARK_RED) {
            return MmgColor.GetDecodedColor("#B85C52");
        } else if(c == NIGHT) {
            return MmgColor.GetDecodedColor("#050505");
        } else if(c == DARK_BLUE || c == -16248224) {
            return MmgColor.GetDecodedColor("#081260");
        } else if(c == DARK_BLUE2 || c == -15524477 || c == -15194448) {
            return MmgColor.GetDecodedColor("#131D83");
        } else if(c == DARK_BLUE3) {
            return MmgColor.GetDecodedColor("#182688");
        } else if (c == DEAD_RED) {
            return MmgColor.GetDecodedColor("#B11919");            
        } else if (c == TRANSPARENT) {
            return MmgColor.GetTransparent();
        } else {
            java.awt.Color c2 = new java.awt.Color(c);            
            Logger.wr("SkColor2MmgColor: Could not find matching color for value: " + c + " returning custom color, R:" + c2.getRed() + " G:" + c2.getGreen() + " B:" + c2.getBlue() + "");
            return new MmgColor(c2);
        }
    }
    
    public static String SkColor2Html(int c) {
        if(c == WHITE || c == 16777215) {
            return "#FFFFFF";
        } else if(c == BLACK || c == 0) {
            return "#000000";
        } else if(c == RED) {
            return "#FF0000";
        } else if(c == GRAY15) {
            return "#262626";
        } else if(c == GRAY8) {
            return "#141414";
        } else if (c == GRAY0) {
            return "#999999";
        } else if(c == ORANGE) {
            return "#FFA500";
        } else if(c == PURPLE) {
            return "#800080";
        } else if(c == BLUE) {
            return "#0000FF";
        } else if(c == LIGHT_BLUE) {
            return "#525CB8";
        } else if(c == JAVA_GRAY) {
            return "#EEEEEE";            
        } else if(c == DARK_RED) {
            return "#B85C52";
        } else if(c == NIGHT) {
            return "#050505";
        } else if(c == DARK_BLUE || c == -16248224) {
            return "#081260";
        } else if(c == DARK_BLUE2 || c == -15524477 || c == -15194448) {
            return "#131D83";
        } else if(c == DARK_BLUE3) {
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