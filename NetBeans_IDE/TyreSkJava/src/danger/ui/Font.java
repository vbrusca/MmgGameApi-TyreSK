package danger.ui;

import com.middlemindgames.TyreSkOrig.Tyre;
import danger.app.AppResources;
import danger.util.Logger;
import java.io.File;
import java.nio.file.Paths;
import net.middlemind.MmgGameApiJava.MmgBase.MmgFont;
import net.middlemind.MmgGameApiJava.MmgBase.MmgFont.FontType;

/*
 * Font.java
 * Victor G. Brusca 01/22/2022
 */
public class Font {

    public static String GLYPH_BACK = "B:";
    public static String GLYPH_LEFT_ARROW = "L:";
    public static String GLYPH_RIGHT_ARROW = "R:";
    public static String GLYPH_WHEEL = "A:";
    public static String GLYPH_RETURN = "A:";
    public static int FONT_SIZE_SMALL = 8;
    public static int FONT_SIZE_REGULAR = 10;
    public static int FONT_SIZE_LARGE = 12;
    public static int WIDTH_ADJ = 2;

    public static int TXT_WIDTH_NORMAL_S_X = 0;
    public static int TXT_WIDTH_NORMAL_S_Y = 0;
    public static int TXT_WIDTH_NORMAL_M_X = 0;
    public static int TXT_WIDTH_NORMAL_M_Y = 0;
    public static int TXT_WIDTH_NORMAL_L_X = 0;
    public static int TXT_WIDTH_NORMAL_L_Y = 0;

    public static int TXT_WIDTH_BOLD_S_X = 8;
    public static int TXT_WIDTH_BOLD_S_Y = 0;
    public static int TXT_WIDTH_BOLD_M_X = 0;
    public static int TXT_WIDTH_BOLD_M_Y = 0;
    public static int TXT_WIDTH_BOLD_L_X = 0;
    public static int TXT_WIDTH_BOLD_L_Y = 0;

    public static int TXT_WIDTH_ITALIC_S_X = 0;
    public static int TXT_WIDTH_ITALIC_S_Y = 0;
    public static int TXT_WIDTH_ITALIC_M_X = 0;
    public static int TXT_WIDTH_ITALIC_M_Y = 0;
    public static int TXT_WIDTH_ITALIC_L_X = 0;
    public static int TXT_WIDTH_ITALIC_L_Y = 0;

    public MmgFont mmgFont;

    public Font(MmgFont mf) {
        mmgFont = mf;
    }

    public Font() {
    }

    public static Font findBoldSmallFont() {
        try {
            String fntPath = Paths.get(Tyre.cApp.getString(AppResources.ID_APP_FONT_PATH), Tyre.cApp.getString(AppResources.ID_APP_FONT_BOLD)).toString();
            java.awt.Font tmpFnt = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(fntPath));
            tmpFnt = tmpFnt.deriveFont((float) FONT_SIZE_SMALL);
            return new Font(new MmgFont((java.awt.Font) tmpFnt, FONT_SIZE_SMALL, FontType.BOLD));
        } catch (Exception ex) {
            Logger.wr("findBoldSmallFont: Exception encountered");
            ex.printStackTrace();
        }
        return null;
    }

    public static Font findBoldMediumFont() {
        try {
            String fntPath = Paths.get(Tyre.cApp.getString(AppResources.ID_APP_FONT_PATH), Tyre.cApp.getString(AppResources.ID_APP_FONT_BOLD)).toString();
            java.awt.Font tmpFnt = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(fntPath));
            tmpFnt = tmpFnt.deriveFont((float) FONT_SIZE_REGULAR);
            return new Font(new MmgFont((java.awt.Font) tmpFnt, FONT_SIZE_REGULAR, FontType.BOLD));
        } catch (Exception ex) {
            Logger.wr("findBoldMediumFont: Exception encountered");
            ex.printStackTrace();
        }
        return null;
    }

    public static Font findBoldLargeFont() {
        try {
            String fntPath = Paths.get(Tyre.cApp.getString(AppResources.ID_APP_FONT_PATH), Tyre.cApp.getString(AppResources.ID_APP_FONT_BOLD)).toString();
            java.awt.Font tmpFnt = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(fntPath));
            tmpFnt = tmpFnt.deriveFont((float) FONT_SIZE_LARGE);
            return new Font(new MmgFont((java.awt.Font) tmpFnt, FONT_SIZE_LARGE, FontType.BOLD));
        } catch (Exception ex) {
            Logger.wr("findBoldLargeFont: Exception encountered");
            ex.printStackTrace();
        }
        return null;
    }

    public static Font findRegularSmallFont() {
        try {
            String fntPath = Paths.get(Tyre.cApp.getString(AppResources.ID_APP_FONT_PATH), Tyre.cApp.getString(AppResources.ID_APP_FONT_REGULAR)).toString();
            java.awt.Font tmpFnt = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(fntPath));
            tmpFnt = tmpFnt.deriveFont((float) FONT_SIZE_SMALL);
            return new Font(new MmgFont((java.awt.Font) tmpFnt, FONT_SIZE_SMALL, FontType.NORMAL));
        } catch (Exception ex) {
            Logger.wr("findRegularSmallFont: Exception encountered");
            ex.printStackTrace();
        }
        return null;
    }

    public static Font findRegularMediumFont() {
        try {
            String fntPath = Paths.get(Tyre.cApp.getString(AppResources.ID_APP_FONT_PATH), Tyre.cApp.getString(AppResources.ID_APP_FONT_REGULAR)).toString();
            java.awt.Font tmpFnt = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(fntPath));
            tmpFnt = tmpFnt.deriveFont((float) FONT_SIZE_REGULAR);
            return new Font(new MmgFont((java.awt.Font) tmpFnt, FONT_SIZE_REGULAR, FontType.NORMAL));
        } catch (Exception ex) {
            Logger.wr("findRegularMediumFont: Exception encountered");
            ex.printStackTrace();
        }
        return null;
    }

    public static Font findRegularLargeFont() {
        try {
            String fntPath = Paths.get(Tyre.cApp.getString(AppResources.ID_APP_FONT_PATH), Tyre.cApp.getString(AppResources.ID_APP_FONT_REGULAR)).toString();
            java.awt.Font tmpFnt = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(fntPath));
            tmpFnt = tmpFnt.deriveFont((float) FONT_SIZE_LARGE);
            return new Font(new MmgFont((java.awt.Font) tmpFnt, FONT_SIZE_LARGE, FontType.NORMAL));
        } catch (Exception ex) {
            Logger.wr("findRegularLargeFont: Exception encountered");
            ex.printStackTrace();
        }
        return null;
    }

    public static Font findLightSmallFont() {
        try {
            String fntPath = Paths.get(Tyre.cApp.getString(AppResources.ID_APP_FONT_PATH), Tyre.cApp.getString(AppResources.ID_APP_FONT_LIGHT)).toString();
            java.awt.Font tmpFnt = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(fntPath));
            tmpFnt = tmpFnt.deriveFont((float) FONT_SIZE_SMALL);
            return new Font(new MmgFont((java.awt.Font) tmpFnt, FONT_SIZE_SMALL, FontType.ITALIC));
        } catch (Exception ex) {
            Logger.wr("findLightSmallFont: Exception encountered");
            ex.printStackTrace();
        }
        return null;
    }

    public static Font findLightMediumFont() {
        try {
            String fntPath = Paths.get(Tyre.cApp.getString(AppResources.ID_APP_FONT_PATH), Tyre.cApp.getString(AppResources.ID_APP_FONT_LIGHT)).toString();
            java.awt.Font tmpFnt = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(fntPath));
            tmpFnt = tmpFnt.deriveFont((float) FONT_SIZE_REGULAR);
            return new Font(new MmgFont((java.awt.Font) tmpFnt, FONT_SIZE_REGULAR, FontType.ITALIC));
        } catch (Exception ex) {
            Logger.wr("findLightMediumFont: Exception encountered");
            ex.printStackTrace();
        }
        return null;
    }

    public static Font findLightLargeFont() {
        try {
            String fntPath = Paths.get(Tyre.cApp.getString(AppResources.ID_APP_FONT_PATH), Tyre.cApp.getString(AppResources.ID_APP_FONT_LIGHT)).toString();
            java.awt.Font tmpFnt = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File(fntPath));
            tmpFnt = tmpFnt.deriveFont((float) FONT_SIZE_LARGE);
            return new Font(new MmgFont((java.awt.Font) tmpFnt, FONT_SIZE_LARGE, FontType.ITALIC));
        } catch (Exception ex) {
            Logger.wr("findLightLargeFont: Exception encountered");
            ex.printStackTrace();
        }
        return null;
    }

    public static Font findFont(String fontName) throws Exception {
        if (fontName != null) {
            if (fontName.equals("BoldSmall")) {
                return findBoldSmallFont();

            } else if (fontName.equals("BoldMedium")) {
                return findBoldMediumFont();

            } else if (fontName.equals("BoldLarge")) {
                return findBoldLargeFont();

            } else if (fontName.equals("RegularSmall")) {
                return findRegularSmallFont();

            } else if (fontName.equals("RegularMedium")) {
                return findRegularMediumFont();

            } else if (fontName.equals("RegularLarge")) {
                return findRegularLargeFont();

            } else if (fontName.equals("LightSmall")) {
                return findLightSmallFont();

            } else if (fontName.equals("LightMedium")) {
                return findLightMediumFont();

            } else if (fontName.equals("LightLarge")) {
                return findLightLargeFont();

            }
        }
        Logger.wr("findFont: Could not find font for name: " + fontName);
        throw new Exception("Missing Font: " + fontName);
    }

    public int getWidth(String s) {
            MmgFont tmpF = mmgFont.CloneTyped();
            tmpF.SetText(s);
            if (tmpF.GetFontType() == FontType.BOLD) {
                if (tmpF.GetFontSize() == FONT_SIZE_SMALL) {
                    return tmpF.GetWidth() + TXT_WIDTH_BOLD_S_X;
                } else if (tmpF.GetFontSize() == FONT_SIZE_REGULAR) {
                    return tmpF.GetWidth() + TXT_WIDTH_BOLD_M_X;
                } else if (tmpF.GetFontSize() == FONT_SIZE_LARGE) {
                    return tmpF.GetWidth() + TXT_WIDTH_BOLD_L_X;
                }
            } else if (tmpF.GetFontType() == FontType.ITALIC) {
                if (tmpF.GetFontSize() == FONT_SIZE_SMALL) {
                    return tmpF.GetWidth() + TXT_WIDTH_ITALIC_S_X;
                } else if (tmpF.GetFontSize() == FONT_SIZE_REGULAR) {
                    return tmpF.GetWidth() + TXT_WIDTH_ITALIC_M_X;
                } else if (tmpF.GetFontSize() == FONT_SIZE_LARGE) {
                    return tmpF.GetWidth() + TXT_WIDTH_ITALIC_L_X;
                }
            } else if (tmpF.GetFontType() == FontType.NORMAL) {
                if (tmpF.GetFontSize() == FONT_SIZE_SMALL) {
                    return tmpF.GetWidth() + TXT_WIDTH_NORMAL_S_X;
                } else if (tmpF.GetFontSize() == FONT_SIZE_REGULAR) {
                    return tmpF.GetWidth() + TXT_WIDTH_NORMAL_M_X;
                } else if (tmpF.GetFontSize() == FONT_SIZE_LARGE) {
                    return tmpF.GetWidth() + TXT_WIDTH_NORMAL_L_X;
                }
            }
            return tmpF.GetWidth() + WIDTH_ADJ;
    }

    public int getWidth(String s, int off, int len) {
        return getWidth(s.substring(off, off + len));
    }
}
