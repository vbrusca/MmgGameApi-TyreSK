using com.middlemindgames.TyreSkOrig;
using danger.ui;
using danger.util;
using System;
using System.Collections.Generic;
using System.IO;

namespace danger.app {
    /*
     * Application.java
     * Victor G. Brusca 01/20/2022
     */
    public class Application {
        public List<Event> events;

        public Application() {
            events = new List<Event>();
        }

        public String getString(int id) {
            if (id == AppResources.ID_APP_CLASS) {
                return AppResources.ID_APP_CLASS_str;
            } else if (id == AppResources.ID_APP_NAME) {
                return AppResources.ID_APP_NAME_str;
            } else if (id == AppResources.ID_CHOOSER_CATEGORY) {
                return AppResources.ID_CHOOSER_CATEGORY_str;
            } else if (id == AppResources.ID_SMALL_ICON) {
                return AppResources.ID_SMALL_ICON_str;
            } else if (id == AppResources.ID_LARGE_ICON) {
                return AppResources.ID_LARGE_ICON_str;
            } else if (id == AppResources.ID_SPLASH_SCREEN) {
                return AppResources.ID_SPLASH_SCREEN_str;
            } else if (id == AppResources.mmglogo) {
                return AppResources.mmglogo_str;
            } else if (id == AppResources.aboutlogo) {
                return AppResources.aboutlogo_str;
            } else if (id == AppResources.SPEED_MULTIPLIER) {
                return AppResources.SPEED_MULTIPLIER_str;
            } else if (id == AppResources.PLAYER_SPEED) {
                return AppResources.PLAYER_SPEED_str;
            } else if (id == AppResources.SCREEN_WIDTH) {
                return AppResources.SCREEN_WIDTH_str;
            } else if (id == AppResources.SCREEN_HEIGHT) {
                return AppResources.SCREEN_HEIGHT_str;
            } else if (id == AppResources.RENDER_ADJACENT_ROOMS) {
                return AppResources.RENDER_ADJACENT_ROOMS_str;
            } else if (id == AppResources.ROOM_WIDTH) {
                return AppResources.ROOM_WIDTH_str;
            } else if (id == AppResources.ROOM_HEIGHT) {
                return AppResources.ROOM_HEIGHT_str;
            } else if (id == AppResources.GUTTER_X) {
                return AppResources.GUTTER_X_str;
            } else if (id == AppResources.GUTTER_Y) {
                return AppResources.GUTTER_Y_str;
            } else if (id == AppResources.FONT_GROUP) {
                return AppResources.FONT_GROUP_str;
            } else if (id == AppResources.MESSAGES_WIDTH) {
                return AppResources.MESSAGES_WIDTH_str;
            } else if (id == AppResources.MESSAGE_FONT_HEIGHT) {
                return AppResources.MESSAGE_FONT_HEIGHT_str;
            } else if (id == AppResources.MAINMENU_HUD_WIDTH) {
                return AppResources.MAINMENU_HUD_WIDTH_str;
            } else if (id == AppResources.MAINMENU_HUD_HEIGHT) {
                return AppResources.MAINMENU_HUD_HEIGHT_str;
            } else if (id == AppResources.HELP_PAGE_OFFSET1) {
                return AppResources.HELP_PAGE_OFFSET1_str;
            } else if (id == AppResources.HELP_PAGE_OFFSET2) {
                return AppResources.HELP_PAGE_OFFSET2_str;
            } else if (id == AppResources.HELP_PAGE_OFFSET3) {
                return AppResources.HELP_PAGE_OFFSET3_str;
            } else if (id == AppResources.HELP_PAGE_OFFSET4) {
                return AppResources.HELP_PAGE_OFFSET4_str;
            } else if (id == AppResources.ID_RESOURCE_PATH) {
                return AppResources.ID_RESOURCE_PATH_str;
            } else if (id == AppResources.ID_TYRE_DAT_ZIP) {
                return AppResources.ID_TYRE_DAT_ZIP_str;
            } else if (id == AppResources.ID_TYRE_DAT_BIN) {
                return AppResources.ID_TYRE_DAT_BIN_str;
            } else if (id == AppResources.ID_BIN_RES_TYPE) {
                return AppResources.ID_BIN_RES_TYPE_str;
            } else if (id == AppResources.ID_LOCALE) {
                return AppResources.ID_LOCALE_str;
            } else if (id == AppResources.ID_APP_FONT_BOLD) {
                return AppResources.ID_APP_FONT_BOLD_str;
            } else if (id == AppResources.ID_APP_FONT_REGULAR) {
                return AppResources.ID_APP_FONT_REGULAR_str;
            } else if (id == AppResources.ID_APP_FONT_LIGHT) {
                return AppResources.ID_APP_FONT_LIGHT_str;
            } else if (id == AppResources.ID_APP_FONT_PATH) {
                return AppResources.ID_APP_FONT_PATH_str;
            } else if (id == AppResources.ID_TYRE_BG_CLEAR) {
                return AppResources.ID_TYRE_BG_CLEAR_str;
            }
            Logger.wr("getString: Could not find id: " + id);
            return null;
        }

        //TODO: PORT
        public static Bitmap getBitmap(int id) {
            if (id == AppResources.ID_SMALL_ICON) {
                if (Tyre.idSmallIcon == null) {
                    return new Bitmap(Tyre.cApp.getString(AppResources.ID_RESOURCE_PATH), Tyre.cApp.getString(id));
                } else {
                    return Tyre.idSmallIcon;
                }
            } else if (id == AppResources.ID_LARGE_ICON) {
                if (Tyre.idLargeIcon == null) {
                    return new Bitmap(Tyre.cApp.getString(AppResources.ID_RESOURCE_PATH), Tyre.cApp.getString(id));
                } else {
                    return Tyre.idLargeIcon;
                }
            } else if (id == AppResources.ID_SPLASH_SCREEN) {
                if (Tyre.idSplashScreen == null) {
                    return new Bitmap(Tyre.cApp.getString(AppResources.ID_RESOURCE_PATH), Tyre.cApp.getString(id));
                } else {
                    return Tyre.idSplashScreen;
                }
            } else if (id == AppResources.mmglogo) {
                if (Tyre.logoImage == null) {
                    return new Bitmap(Tyre.cApp.getString(AppResources.ID_RESOURCE_PATH), Tyre.cApp.getString(id));
                } else {
                    return Tyre.logoImage;
                }
            } else if (id == AppResources.aboutlogo) {
                if (Tyre.aboutlogoImage == null) {
                    return new Bitmap(Tyre.cApp.getString(AppResources.ID_RESOURCE_PATH), Tyre.cApp.getString(id));
                } else {
                    return Tyre.aboutlogoImage;
                }
            }
            Logger.wr("getBitmap: Could not find id: " + id);
            return null;
        }

        public virtual bool receiveEvent(Event e) {
            return false;
        }

        public void cancelEvents(int evnt, int parent) {
            events.Clear();
        }

        public Resource getResource(int id, int type) {
            if (type == AppResources.ID_BIN_RES_TYPE) {
                if (id == AppResources.ID_TYRE_DAT_ZIP) {
                    try {
                        string baseDir = Tyre.cApp.getString(AppResources.ID_RESOURCE_PATH);
                        string fName = Tyre.cApp.getString(AppResources.ID_TYRE_DAT_ZIP);
                        byte[] b = File.ReadAllBytes(Path.Combine(baseDir, fName));
                        Resource ret = new Resource(b);
                        return ret;
                    } catch (IOException ex2) {
                        Logger.wr(ex2.Message);
                        Logger.wr(ex2.StackTrace);
                    }
                } else if (id == AppResources.ID_TYRE_DAT_BIN) {
                    try {
                        string baseDir = Tyre.cApp.getString(AppResources.ID_RESOURCE_PATH);
                        string fName = Tyre.cApp.getString(AppResources.ID_TYRE_DAT_BIN);
                        byte[] b = File.ReadAllBytes(Path.Combine(baseDir, fName));
                        Resource ret = new Resource(b);
                        return ret;
                    } catch (IOException ex2) {
                        Logger.wr(ex2.Message);
                        Logger.wr(ex2.StackTrace);
                    }
                } else if (id == AppResources.ID_TYRE_BG_CLEAR_APP) {
                    try {
                        string baseDir = Tyre.cApp.getString(AppResources.ID_RESOURCE_PATH);
                        string fName = Tyre.cApp.getString(AppResources.ID_TYRE_BG_CLEAR);
                        byte[] b = File.ReadAllBytes(Path.Combine(baseDir, fName));
                        Resource ret = new Resource(b);
                        return ret;
                    } catch (IOException ex2) {
                        Logger.wr(ex2.Message);
                        Logger.wr(ex2.StackTrace);
                    }
                }
            }
            Logger.wr("getResource: Could not find id: " + id + " or type: " + type);
            return null;
        }

        public string[] getStringArray(int id) {
            if (id == AppResources.HELP_STRINGS_P1) {                
                return AppResources.HELP_STRINGS_P1_sar;
            } else if (id == AppResources.HELP_STRINGS_P2) {
                return AppResources.HELP_STRINGS_P2_sar;
            } else if (id == AppResources.HELP_STRINGS_P3) {
                return AppResources.HELP_STRINGS_P3_sar;
            } else if (id == AppResources.HELP_STRINGS_P4) {
                return AppResources.HELP_STRINGS_P4_sar;
            } else if (id == AppResources.HELP_STRINGS_P5) {
                return AppResources.HELP_STRINGS_P5_sar;
            } else if (id == AppResources.HELP_STRINGS_P6) {
                return AppResources.HELP_STRINGS_P6_sar;
            } else if (id == AppResources.HELP_STRINGS_P7) {
                return AppResources.HELP_STRINGS_P7_sar;
            } else if (id == AppResources.HELP_STRINGS_P8) {
                return AppResources.HELP_STRINGS_P8_sar;
            } else if (id == AppResources.HELP_STRINGS_P9) {
                return AppResources.HELP_STRINGS_P9_sar;
            } else if (id == AppResources.HELP_STRINGS_P10) {
                return AppResources.HELP_STRINGS_P10_sar;
            } else if (id == AppResources.HELP_STRINGS_P11) {
                return AppResources.HELP_STRINGS_P11_sar;
            } else if (id == AppResources.HELP_STRINGS_P12) {
                return AppResources.HELP_STRINGS_P12_sar;
            } else if (id == AppResources.HELP_STRINGS_P13) {
                return AppResources.HELP_STRINGS_P13_sar;
            } else if (id == AppResources.HELP_STRINGS_P14) {
                return AppResources.HELP_STRINGS_P14_sar;
            } else if (id == AppResources.HELP_STRINGS_P15) {
                return AppResources.HELP_STRINGS_P15_sar;
            } else if (id == AppResources.HELP_STRINGS_P16) {
                return AppResources.HELP_STRINGS_P16_sar;
            } else if (id == AppResources.HELP_STRINGS_P17) {
                return AppResources.HELP_STRINGS_P17_sar;
            } else if (id == AppResources.HELP_STRINGS_P18) {
                return AppResources.HELP_STRINGS_P18_sar;
            } else if (id == AppResources.HELP_STRINGS_P19) {
                return AppResources.HELP_STRINGS_P19_sar;
            } else if (id == AppResources.HELP_STRINGS_P20) {
                return AppResources.HELP_STRINGS_P20_sar;
            } else if (id == AppResources.HELP_STRINGS_P21) {
                return AppResources.HELP_STRINGS_P21_sar;
            } else if (id == AppResources.HELP_STRINGS_P22) {
                return AppResources.HELP_STRINGS_P22_sar;
            } else if (id == AppResources.HELP_STRINGS_P23) {
                return AppResources.HELP_STRINGS_P23_sar;
            } else if (id == AppResources.HELP_STRINGS_P24) {
                return AppResources.HELP_STRINGS_P24_sar;
            } else if (id == AppResources.HELP_STRINGS_P25) {
                return AppResources.HELP_STRINGS_P25_sar;
            } else if (id == AppResources.HELP_STRINGS_P26) {
                return AppResources.HELP_STRINGS_P26_sar;
            } else if (id == AppResources.HELP_STRINGS_P27) {
                return AppResources.HELP_STRINGS_P27_sar;
            } else if (id == AppResources.HELP_STRINGS_P28) {
                return AppResources.HELP_STRINGS_P28_sar;
            } else if (id == AppResources.HELP_STRINGS_P29) {
                return AppResources.HELP_STRINGS_P29_sar;
            } else if (id == AppResources.HELP_STRINGS_P30) {
                return AppResources.HELP_STRINGS_P30_sar;
            } else if (id == AppResources.HELP_STRINGS_P31) {
                return AppResources.HELP_STRINGS_P31_sar;
            }
            Logger.wr("getStringArray: Could not find id: " + id);
            return null;
        }

        public void returnToLauncher() {
            Tyre.mWindow.changeState(10, 0);
        }
    }
}