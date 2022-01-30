package danger.app;

import danger.ui.Bitmap;

/*
 * AppResources.java
 * Victor G. Brusca 01/20/2022
 */
public class AppResources {
    public static String ID_TYRE_BG_CLEAR_str = "bgclear.png";
    public static int ID_TYRE_BG_CLEAR = 513;
    public static int ID_TYRE_BG_CLEAR_APP = 512;    
    
    public static int ID_APP_FONT_LIGHT = 511;
    public static String ID_APP_FONT_LIGHT_str = "MiniSet2.ttf";
    public static int ID_APP_FONT_REGULAR = 510;
    public static String ID_APP_FONT_REGULAR_str = "MiniSet2.ttf";
    public static int ID_APP_FONT_BOLD = 509;
    public static String ID_APP_FONT_BOLD_str = "MiniSet2.ttf";
    public static int ID_APP_FONT_PATH = 508;
    public static String ID_APP_FONT_PATH_str = "../cfg/fonts/";
    
    public static int ID_LOCALE = 507;
    public static String ID_LOCALE_str = "en_US";
    public static int ID_TYRE_DAT_ZIP = 257;
    public static String ID_TYRE_DAT_ZIP_str = "chapter2.zip";
    public static int ID_TYRE_DAT_BIN = 258;
    public static String ID_TYRE_DAT_BIN_str = "chapter2.bin";
    public static int ID_BIN_RES_TYPE = 25;
    public static String ID_BIN_RES_TYPE_str = "bin_type";
    public static int ID_RESOURCE_PATH = 506;
    public static String ID_RESOURCE_PATH_str = "../cfg/sk/assets/default/color/";
    public static int ID_APP_NAME = 500;
    public static String ID_APP_NAME_str = "Tyre";
    public static int ID_APP_CLASS = 501;
    public static String ID_APP_CLASS_str = "com.middlemindgames.Tyre.Tyre";
    public static int ID_CHOOSER_CATEGORY = 502;
    public static String ID_CHOOSER_CATEGORY_str = "games";
    public static int ID_SMALL_ICON = 503;
    public static String ID_SMALL_ICON_str = "SmallIcon.png";
    public static Bitmap ID_SMALL_ICON_bmp = null;
    public static int ID_LARGE_ICON = 504;
    public static String ID_LARGE_ICON_str = "LargeIcon.png";
    public static Bitmap ID_LARGE_ICON_bmp = null;
    public static int ID_SPLASH_SCREEN = 505;
    public static String ID_SPLASH_SCREEN_str = "SplashScreen.png";
    public static Bitmap ID_SPLASH_SCREEN_bmp = null;
    public static String mmglogo_str = "logo2.png";
    public static Bitmap mmglogo_bmp = null;
    public static String aboutlogo_str = "logo2.png";
    public static Bitmap aboutlogo_bmp = null;
    public static String SPEED_MULTIPLIER_str = "1.0";
    public static String PLAYER_SPEED_str = "4";
    public static String SCREEN_WIDTH_str = "240";
    public static String SCREEN_HEIGHT_str = "136";
    public static String RENDER_ADJACENT_ROOMS_str = "0";
    public static String ROOM_WIDTH_str = "240";
    public static String ROOM_HEIGHT_str = "136";
    public static String GUTTER_X_str = "0";
    public static String GUTTER_Y_str = "0";
    public static String FONT_GROUP_str = "0";
    public static String MESSAGES_WIDTH_str = "250";
    public static String MESSAGE_FONT_HEIGHT_str = "10";
    public static String MAINMENU_HUD_WIDTH_str = "210";
    public static String MAINMENU_HUD_HEIGHT_str = "106";

    //help page offsets
    public static String HELP_PAGE_OFFSET1_str = "15";
    public static String HELP_PAGE_OFFSET2_str = "66";
    public static String HELP_PAGE_OFFSET3_str = "60";
    public static String HELP_PAGE_OFFSET4_str = "152";

    //A: uF00A
    //L: uF00B
    //R: uF00C
    //U: uF004
    //D: uF00D
    //B: uF005    
    
    public static String[] HELP_STRINGS_P1_sar = {
        "Game Controls",
        "",
        "Move around using the D-Pad. Click",
        "A: to open your control panel. Select",
        "an icon using L: and R: and then click A:.",
        "",
        "",
        ""};

    public static String[] HELP_STRINGS_P2_sar = {
        "Combat",
        "",
        "You can fight, use items or try to run",
        "away. Any action resets the charge bar.",
        "You cannot attack or run when the bar is",
        "recharging but can use items. Hitting",
        "A: too soon moves the bar backwards.",
        ""};

    public static String[] HELP_STRINGS_P3_sar = {
        "Fighting Multiple Enemies",
        "",
        "If there are multiple enemies you must",
        "select your target using L: and R:. If you",
        "have the long sword, click U: to select",
        "all targets or D: to deselect. Striking",
        "multiple enemies inflicts less damage.",
        ""};

    public static String[] HELP_STRINGS_P4_sar = {
        "Super-Charge",
        "",
        "Before the bar is fully charged it turns",
        "purple and your damage and chance of",
        "successfully running are both doubled.",
        ""};

    public static String[] HELP_STRINGS_P5_sar = {
        "Examine",
        "",
        "Gives you a brief description",
        "of whatever you are facing.",
        "",
        "",
        "",
        ""};

    public static String[] HELP_STRINGS_P6_sar = {
        "Talk",
        "",
        "Talks to the character in",
        "front of you.",
        "",
        "",
        "",
        ""};

    public static String[] HELP_STRINGS_P7_sar = {
        "Inventory",
        "",
        "Shows your inventory. Use",
        "the D-Pad to select an item",
        "then click A: to use it.",
        "",
        "",
        ""};

    public static String[] HELP_STRINGS_P8_sar = {
        "Character Info",
        "",
        "Displays your sword, armor,",
        "gold, orbs, health and battle",
        "stats.",
        "",
        "",
        ""};

    public static String[] HELP_STRINGS_P9_sar = {
        "Search",
        "",
        "Searches the ground you are",
        "standing on. This may reveal",
        "hidden items.",
        "",
        "",
        ""};

    public static String[] HELP_STRINGS_P10_sar = {
        "Attack",
        "",
        "Click this icon to attack your",
        "enemy.",
        "",
        "",
        "",
        ""};

    public static String[] HELP_STRINGS_P11_sar = {
        "Health Status",
        "",
        "The top icons show current",
        "ailments. The bottom icons",
        "show your immunities.",
        "",
        "",
        ""};

    public static String[] HELP_STRINGS_P12_sar = {
        "Current Hit Points",
        "",
        "Each container represents four",
        "hit points. You cannot have",
        "more than nine containers.",
        "",
        "",
        ""};

    public static String[] HELP_STRINGS_P13_sar = {
        "Run",
        "",
        "Attempts to flee from battle.",
        "Your enemy can block you. You",
        "cannot run from some battles.",
        "",
        "",
        ""};

    public static String[] HELP_STRINGS_P14_sar = {
        "Battle Ailments",
        "",
        "There are three combat ailments.",
        "",
        "Poisoned:  Recharge time is slowed",
        "Stunned:  Cannot act for a round",
        "Entranced:  Asleep for several rounds",
        ""};

    public static String[] HELP_STRINGS_P15_sar = {
        "Items   1",
        "",
        "Healing Herb",
        "Heals six hit points",
        "",
        "Healing Salve",
        "Heals twelve hit points",
        "",
        ""};

    public static String[] HELP_STRINGS_P16_sar = {
        "Items   2",
        "",
        "Restore Potion",
        "Heals all lost hit points",
        "",
        "Torch",
        "Used to enter a dungeon or to",
        "take stairs inside a dungeon.",
        ""};

    public static String[] HELP_STRINGS_P17_sar = {
        "Items   3",
        "",
        "Herbal Antidote",
        "Cures poison ailment",
        "",
        "Herbal Antisleep",
        "Prevents sleep ailment",
        "",
        ""};

    public static String[] HELP_STRINGS_P18_sar = {
        "Items   4",
        "",
        "Wooden Sword",
        "Inflicts minimal damage",
        "",
        "Broad Sword",
        "Inflicts moderate damage",
        "",
        ""};

    public static String[] HELP_STRINGS_P19_sar = {
        "Items   5",
        "",
        "Long Sword",
        "Able to hit multiple targets",
        "",
        "Magical Sword",
        "??",
        "",
        ""};

    public static String[] HELP_STRINGS_P20_sar = {
        "Items   6",
        "",
        "Leather Armor",
        "Low protection",
        "",
        "Chain Mail",
        "Moderate protection",
        "",
        ""};

    public static String[] HELP_STRINGS_P21_sar = {
        "Items   7",
        "",
        "Plate Mail",
        "Very good protection",
        "",
        "Magical Armor",
        "??",
        "",
        ""};

    public static String[] HELP_STRINGS_P22_sar = {
        "Items   8",
        "",
        "Compass",
        "Necessary for navigating",
        "difficult terrain.",
        "Map",
        "Necessary for navigating",
        "difficult terrain.",
        ""};

    public static String[] HELP_STRINGS_P23_sar = {
        "Items   9",
        "",
        "Herterods Myths",
        "This book provides information",
        "on the lore of Tyre",
        "Magic Warp Sheet",
        "??",
        "",
        ""};

    public static String[] HELP_STRINGS_P24_sar = {
        "Items   10",
        "",
        "Bale Fire Orb",
        "??",
        "",
        "Blizzard Staff",
        "??",
        "",
        ""};

    public static String[] HELP_STRINGS_P25_sar = {
        "Protection Orbs",
        "antipoison",
        "antistun",
        "antisleep",
        "",
        "Immunizes you from combat ailments."};

    public static String[] HELP_STRINGS_P26_sar = {
        "Exporting Game State",
        "",
        "You can email your current game state",
        "at any time. This allows you to safely",
        "store and backup saved games. To do this",
        "go to 'Settings' under the main menu and",
        "select 'Export Game State'. You can email",
        "the game state to any email address.",
        ""};

    public static String[] HELP_STRINGS_P27_sar = {
        "Importing Game State",
        "",
        "First copy the game code from the email",
        "to your clipboard, including the start",
        "and end lines containing +=+=+. Select",
        "'Import Game State' from the 'Settings'",
        "menu and if the game data is valid you",
        "can hit B: to load it.",
        ""};

    public static String[] HELP_STRINGS_P28_sar = {
        "Tips and Tricks  1",
        "",
        "Listen carefully to the people you meet.",
        "Some people tell you more a second time.",
        "Search your surroundings often.",
        "Trade your gold for items when you can.",
        "Healers restore health and cure ailments.",
        "",
        ""};

    public static String[] HELP_STRINGS_P29_sar = {
        "Tips and Tricks  2",
        "",
        "Always try to use super-charge attacks.",
        "Always carry a spare torch.",
        "3 life containers are hidden treasures.",
        "3 life containers are rewards for quests.",
        "Export your game state after big quests.",
        "",
        ""};

    public static String[] HELP_STRINGS_P30_sar = {
        "Credits",
        "(in alphabetical order)",
        "Victor G. Brusca",
        "Shaun Jensen",
        "Katia Pouleva",
        "Douglas Shuler",
        "Aaron Walz"};

    public static String[] HELP_STRINGS_P31_sar = {
        "Tyre",
        "\"Prelude to the Road to Tyre\"",
        "Copyright 2007 Middlemind Games  ver 1.1.7",
        "Game support: info@middlemindgames.com"};
}