using danger.util;
using Microsoft.Xna.Framework.Graphics;
using net.middlemind.MmgGameApiCs.MmgBase;
using System;
using System.Collections.Generic;
using System.Text;
using static net.middlemind.MmgGameApiCs.MmgBase.MmgFont;

namespace danger.ui {
    /*
     * Font.java
     * Victor G. Brusca 01/22/2022
     */
    public class Font {
        public static string GLYPH_BACK = "B:";
        public static string GLYPH_LEFT_ARROW = "L:";
        public static string GLYPH_RIGHT_ARROW = "R:";
        public static string GLYPH_WHEEL = "A:";
        public static string GLYPH_RETURN = "A:";
        public static int FONT_SIZE_SMALL = 12;
        public static int FONT_SIZE_REGULAR = 14;
        public static int FONT_SIZE_LARGE = 16;
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
                SpriteFont ftmp = MmgScreenData.CONTENT_MANAGER.Load<SpriteFont>("DongleBold15");
                return new Font(new MmgFont(ftmp, FONT_SIZE_SMALL, FontType.BOLD, true));            
            } catch (Exception ex) {
                Logger.wr("findBoldSmallFont: Exception encountered");
                Logger.wr(ex.Message);
                Logger.wr(ex.StackTrace);
            }
            return null;
        }

        public static Font findBoldMediumFont() {
            try {
                SpriteFont ftmp = MmgScreenData.CONTENT_MANAGER.Load<SpriteFont>("DongleBold16");
                return new Font(new MmgFont(ftmp, FONT_SIZE_REGULAR, FontType.BOLD, true));
            } catch (Exception ex) {
                Logger.wr("findBoldMediumFont: Exception encountered");
                Logger.wr(ex.Message);
                Logger.wr(ex.StackTrace);
            }
            return null;
        }

        public static Font findBoldLargeFont() {
            try {
                SpriteFont ftmp = MmgScreenData.CONTENT_MANAGER.Load<SpriteFont>("DongleBold18");
                return new Font(new MmgFont(ftmp, FONT_SIZE_LARGE, FontType.BOLD, true));
            } catch (Exception ex) {
                Logger.wr("findBoldLargeFont: Exception encountered");
                Logger.wr(ex.Message);
                Logger.wr(ex.StackTrace);
            }
            return null;
        }

        public static Font findRegularSmallFont() {
            try {
                SpriteFont ftmp = MmgScreenData.CONTENT_MANAGER.Load<SpriteFont>("DongleRegular15");
                return new Font(new MmgFont(ftmp, FONT_SIZE_SMALL, FontType.NORMAL, true));
            } catch (Exception ex) {
                Logger.wr("findRegularSmallFont: Exception encountered");
                Logger.wr(ex.Message);
                Logger.wr(ex.StackTrace);
            }
            return null;
        }

        public static Font findRegularMediumFont() {
            try {
                SpriteFont ftmp = MmgScreenData.CONTENT_MANAGER.Load<SpriteFont>("DongleRegular16");
                return new Font(new MmgFont(ftmp, FONT_SIZE_REGULAR, FontType.NORMAL, true));
            } catch (Exception ex) {
                Logger.wr("findRegularMediumFont: Exception encountered");
                Logger.wr(ex.Message);
                Logger.wr(ex.StackTrace);
            }
            return null;
        }

        public static Font findRegularLargeFont() {
            try {
                SpriteFont ftmp = MmgScreenData.CONTENT_MANAGER.Load<SpriteFont>("DongleRegular18");
                return new Font(new MmgFont(ftmp, FONT_SIZE_LARGE, FontType.NORMAL, true));
            } catch (Exception ex) {
                Logger.wr("findRegularLargeFont: Exception encountered");
                Logger.wr(ex.Message);
                Logger.wr(ex.StackTrace);
            }
            return null;
        }

        public static Font findLightSmallFont() {
            try {
                SpriteFont ftmp = MmgScreenData.CONTENT_MANAGER.Load<SpriteFont>("DongleLight15");
                return new Font(new MmgFont(ftmp, FONT_SIZE_SMALL, FontType.ITALIC, true));
            } catch (Exception ex) {
                Logger.wr("findLightSmallFont: Exception encountered");
                Logger.wr(ex.Message);
                Logger.wr(ex.StackTrace);
            }
            return null;
        }

        public static Font findLightMediumFont() {
            try {
                SpriteFont ftmp = MmgScreenData.CONTENT_MANAGER.Load<SpriteFont>("DongleLight16");
                return new Font(new MmgFont(ftmp, FONT_SIZE_REGULAR, FontType.ITALIC, true));
            } catch (Exception ex) {
                Logger.wr("findLightMediumFont: Exception encountered");
                Logger.wr(ex.Message);
                Logger.wr(ex.StackTrace);
            }
            return null;
        }

        public static Font findLightLargeFont() {
            try {
                SpriteFont ftmp = MmgScreenData.CONTENT_MANAGER.Load<SpriteFont>("DongleLight18");
                return new Font(new MmgFont(ftmp, FONT_SIZE_LARGE, FontType.ITALIC, true));
            } catch (Exception ex) {
                Logger.wr("findLightLargeFont: Exception encountered");
                Logger.wr(ex.Message);
                Logger.wr(ex.StackTrace);
            }
            return null;
        }

        public static Font findFont(string fontName) {
            if (fontName != null) {
                if (fontName.Equals("BoldSmall")) {
                    return findBoldSmallFont();

                } else if (fontName.Equals("BoldMedium")) {
                    return findBoldMediumFont();

                } else if (fontName.Equals("BoldLarge")) {
                    return findBoldLargeFont();

                } else if (fontName.Equals("RegularSmall")) {
                    return findRegularSmallFont();

                } else if (fontName.Equals("RegularMedium")) {
                    return findRegularMediumFont();

                } else if (fontName.Equals("RegularLarge")) {
                    return findRegularLargeFont();

                } else if (fontName.Equals("LightSmall")) {
                    return findLightSmallFont();

                } else if (fontName.Equals("LightMedium")) {
                    return findLightMediumFont();

                } else if (fontName.Equals("LightLarge")) {
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
            return getWidth(s.Substring(off, len));
        }
    }
}
