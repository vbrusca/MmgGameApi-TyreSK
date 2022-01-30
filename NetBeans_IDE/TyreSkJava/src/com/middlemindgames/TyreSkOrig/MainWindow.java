package com.middlemindgames.TyreSkOrig;

import danger.util.Logger;
import danger.system.Meta;
import danger.app.Timer;
import danger.ui.Color;
import danger.app.*;
import danger.ui.*;
import java.util.Random;
import com.middlemindgames.dat.*;
import danger.system.Pasteboard;

/*
 * MainWindow.java
 * Victor G. Brusca 01/16/2022
 * Created on June 1, 2005, 10:57 PM by Middlemind Games
 */
@SuppressWarnings({"FieldMayBeFinal", "UnusedAssignment", "CallToPrintStackTrace"})
public class MainWindow extends ScreenWindow {
    public static boolean SHOW_FONT_DEMO = false;    
    public static boolean PROLOGUE_FONT_BOLD = false;
    
    private final byte[] cheatCodes = {'F', 'u', 't', 'c', 'h', 'a',
        'C', 'a', 'e', 'r', 'i', 'e',
        'M', 'm', 'G', 'a', 'm', 'e',
        'J', 'e', 's', 'L', 'e', 'e',};
    private int[] cheatCodeInd = {0, 0, 0, 0};    
    
    //Console Bitmaps reference constants
    //helps smooth transitions caused by drawing all the window bitmaps
    public static final int CURSOR_INDEX = 0;
    public static final int NSLASH1_INDEX = 1;
    public static final int NSLASH2_INDEX = 2;
    public static final int SSLASH_INDEX = 3;

    //space in consoleBitmaps for stored images
    public static final int CONSOLEBITMAPS_HUD_TILES_START_INDEX = 4;
    public static final int CONSOLEBITMAPS_BATTLEHUD_TILES_START_INDEX = 9;
    public static final int HUDSELECTOR_INDEX = 14;

    //dynamic consoleBitmaps index constants
    //this index should be done away with after we add placeholders
    public static final int CONSOLEBITMAPS_NUM_DYNAMIC_IMAGES = 11;
    public static final int CONSOLEBITMAPS_DYNAMIC_START_INDEX = 15;

    public static final int DRAWMAPMSG_INDEX = CONSOLEBITMAPS_DYNAMIC_START_INDEX + 0;
    public static final int GAMEHUD_INDEX = CONSOLEBITMAPS_DYNAMIC_START_INDEX + 1;
    public static final int ENEMYTARGET_INDEX = CONSOLEBITMAPS_DYNAMIC_START_INDEX + 2;
    public static final int MSGBOX_INDEX = CONSOLEBITMAPS_DYNAMIC_START_INDEX + 3;
    public static final int MSGCONTENT_INDEX = CONSOLEBITMAPS_DYNAMIC_START_INDEX + 4;
    public static final int ITEMGET_INDEX = CONSOLEBITMAPS_DYNAMIC_START_INDEX + 5;
    public static final int HERBSHOPQUANTITY_INDEX = CONSOLEBITMAPS_DYNAMIC_START_INDEX + 6;
    public static final int MAINMENUBOX_INDEX = CONSOLEBITMAPS_DYNAMIC_START_INDEX + 7;
    public static final int BATTLEHUD_INDEX = CONSOLEBITMAPS_DYNAMIC_START_INDEX + 8;
    public static final int CONVOCONTENT_INDEX = CONSOLEBITMAPS_DYNAMIC_START_INDEX + 9;
    public static final int HERBSHOPMENU_INDEX = CONSOLEBITMAPS_DYNAMIC_START_INDEX + 10;
    ////////////////////////////////////////////////////////////////
    
    //These variables are ready from the resource txt file since they are dynamic
    public static int ROOM_WIDTH = 256;
    public static int ROOM_HEIGHT = 160;
    public static int SCREEN_WIDTH = 240;
    public static int SCREEN_HEIGHT = 136;
    public static int RENDER_X = 0;
    public static int RENDER_Y = 0;
    public static int GUTTER_X = 0;
    public static int GUTTER_Y = 0;
    public static int RENDER_ADJACENT_ROOMS = 0;
    public static int HELP_PAGE_OFFSET1 = 15;
    public static int HELP_PAGE_OFFSET2 = 66;
    public static int HELP_PAGE_OFFSET3 = 78;
    public static int HELP_PAGE_OFFSET4 = 170;
    public static int MESSAGE_FONT_HEIGHT = 10;
    public static int MAINMENU_HUD_WIDTH = 210;
    public static int MAINMENU_HUD_HEIGHT = 106;
    public static int MAINMENU_HUD_XPOS = 15;
    public static int MAINMENU_HUD_YPOS = 15;
    ////////////////////////////////////////////////////////////////

    public static final int LRGMESSAGE_POSX = 40;
    public static final int LRGMESSAGE_WIDTH = 160;
    public static final int LRGMESSAGE_POSY = 7;
    public static final int LRGMESSAGE_HEIGHT = 120;
    public static final int LRGMESSAGECONTENT_POSX = 43;
    public static final int LRGMESSAGECONTENT_VIEWABLE_WIDTH = 152;
    public static final int LRGMESSAGECONTENT_POSY = 13;
    public static final int LRGMESSAGECONTENT_VIEWABLE_HEIGHT = 108;
    //////////////////////////////////////////////////////////////
    
    //dynamic based on MAINMENU_HUD_HEIGHT
    public static int PROLOGUECONTENT_VIEWABLE_HEIGHT = 108;
    public static int LRGMESSAGECONTENT_PROLOGUE_WIDTH = 200;
    public static int PROLOGUEMESSAGES_WIDTH = 196;
    //////////////////////////////////////////////////////////////

    //in player stats popup, this is the relative xpos of the stat numbers
    public static final int STAT_XPOS = 105;
    public static final int LRGMESSAGECONTENT_ROW_HEIGHT = 12;
    public static final int LRGMESSAGESCROLLBAR_POSX = 195;
    public static final int LRGMESSAGESCROLLBAR_POSY = 13;
    public static final int LRGMESSAGESCROLLBAR_WIDTH = 2;
    public static final int LRGMESSAGESCROLLBAR_HEIGHT = 108;
    public static final int STORYMESSAGES_WIDTH = 148;

    //conversation constants
    public static final int CONVERSATIONBOX_WIDTH = 240;
    public static final int CONVERSATIONBOX_HEIGHT = 64;
    public static final int CONVERSATIONMSG_VIEWABLE_WIDTH = 202;
    public static final int CONVERSATIONMSG_VIEWABLE_HEIGHT = 58;
    public static final int CONVERSATIONMSG_ROW_HEIGHT = 12;
    public static final int DIALOGMESSAGES_WIDTH = 194;

    //item inventory
    public static final int ITEMINVENTORY_TITLE_POSX = 55;
    public static final int LRGMESSAGEITEMCONTENT_ROW_HEIGHT = 42;
    public static final int ITEMVIEW_ITEM_ICON_WIDTH = 48;
    public static final int ITEMVIEW_ITEM_ICON_HEIGHT = 42;
    public static final int ITEMVIEW_ITEMS_PER_ROW = 3;
    public static int item_u, item_v;
    public static int item_maxu, item_maxv;
    public static int itemcur_x, itemcur_y;
    public static int selItem;

    //boolean indicating if in a current multi-part conversation or not
    public static boolean inConv;
    //boolean indicating if the player is currently speaking in the multipart conversation
    public static boolean inConvPlayer;
    //boolean indicating that the current speaker should stay as is (continued comment from last screen)
    public static boolean inConvStayOnCurrent;
    //int with the position of the last
    public static int inConvPos;
    //int with the position of the last
    public static int inConvPosTotal;
    //int with NPC index of current conversation
    public static int inConvNpcIndex;
    //int with story mode (0 story, 1 conversation)
    public static int storyType;
    //int with NPC index of current conversation
    public static int NpcImageOverride;

    public static final int TREEFOLK_IMAGEID = 242;
    public static final int MAGICAL_WARP_FLAG_ID = 436;

    //positions the item selection information
    //redefined above by consoleBitmap constants
    public static final int ITEMSELECT_POSX = 53;
    public static int MESSAGES_WIDTH = 228;
    public static final int CONSOLESCROLLIN_TIME = 7;
    public static final int CONSOLESCROLLOUT_TIME = 6;
    public static int DEATHSCROLL_TIME = 46;
    public static final int CONSOLESCROLLIN_INCREMENT = 10;
    public static final int CONSOLESCROLLOUT_INCREMENT = 13;
    
    //2.3 PORT
    public static final int DEF_ENEMYWAIT_TIME = 60;
    public static final int DEF_PLAYERWAIT_TIME = 60;
    public static final int SUPERCHARGE_TIME = 10;

    //moved to character class
    public static int[] ENEMYWAIT_TIME = {DEF_ENEMYWAIT_TIME, DEF_ENEMYWAIT_TIME, DEF_ENEMYWAIT_TIME};
    public static int battleSong = Constants.MUSIC_BATTLE;

    public static final int DISPLAYATTACK_TIME = 13;
    public static final int MAX_NAME_SIZE = 15;
    public static String characterName = "";
    public static final int ENTER_NAME_CURSORY = 76;

    //used to format enemy name text
    public static Font ft;
    //used to format battle message text
    public static Font bft;
    //default system font
    public static Font sf;
    //small outline for enemy name
    public static Font bof;

    //main timer variable
    public static int count;
    public static int tmpcount;
    
    public static int i;
    public static int j;
    public static int k;
    public static int l;
    public static int m;
    public static int o;
    public static int p;
    public static String tmpStr, tmpStr2;
    public static String[] tmpStrs;
    public static int tmpInt;
    public static int tmpInt2;
    public static boolean tmpBool;
    public static boolean itemTest;
    public static boolean returnToMenu;
    
    //used to preserve passive screen state
    public static boolean skipMenuPrep;
    
    //added by sj
    public static int ind;
    public static int seli;
    public static int helpPageIndex;

    //temp values
    public static int tx, ty;
    public static int u;
    public static int v;

    //constants defining HP container indices
    private static final int[] HPICON_XOFF = {5, 18, 31, 5, 18, 31, 5, 18, 31};
    private static final int[] HPICON_YOFF = {8, 8, 8, 18, 18, 18, 28, 28, 28};
    private static final int HPICON_NUM_PER_ROW = 3;
    private static final int HPICON_NUM_ROWS = 3;
    private static final int HPICON_HP_PER_CONTAINER = 4;
    private static final int HPICON_NUM_CONTAINERS = 9;

    //consoleString constants
    public static final int PROLOGUE_STRING_INDEX = 5;
    public static final int EPILOGUE_STRING_INDEX = 533;

    //item found containers and quantiy
    public static int[] itemfound_type = new int[3];
    public static int[] itemfound_qty = new int[3];
    public static int itemfound_i = 0;
    public static int itemfound_t = 0;
    private static int tollkeeper_toll = 0;
    private static int tollkeeper_flag = -1;
    public static int prevGameState;
    public static int gameState;

    //0 = run
    //1 = pause
    //2 = fight scene
    //3 = equip scene
    //4 = transition top
    //5 = transition right
    //6 = transition bottom
    //7 = transition left
    //8 = loading screen
    //9 = draw room
    //--10 = room link pause
    //10 = main menu
    public static int gameSubState;
    //0 = normal
    //1 = menu

    //0 = buy/sell
    //1 = buy choose
    //2 = sell choose
    //3 = quantity
    //4 = verify
    public static int herbShopSubState;

    //battle mode variables
    public static int battleModeState;
    //0 = battle start
    //1 = enemy has turn
    //2 = player has turn
    //3 = item menu is up
    //4 = spell menu is up
    //5 = battle has ended

    public static int mainMenuSubState;
    //0 main menu
    //1 start new game verification
    //2 settings
    //3 help
    //4 prologue
    //5 import game state
    //6 import game state verification

    public static int timestamp;
    public static boolean attackSuccess;
    public static int successTime;

    //used in panel scroll in scroll out animation
    public static int displayPanelTime;
    //the last x coord of the panel
    public static int displayPanelX;

    //items menu variables
    public static int itemCursorPos;
    //public static boolean noItems;
    public static int numItems;

    public static int whichEnemy;
    public static enemy[] enemies;
    public static int numEnemies;
    public static int cursorPosY;
    public static int cursorIndX, cursorIndY;

    //battle state
    public static int[][] enemy_index = new int[3][2];
    public static int[][] enemy_cooldown = new int[3][2];
    public static int[] enemy_isdead = new int[3];
    public static int[] enemy_hp = new int[3];

    public static boolean selectEnemy;
    public static int selectEnemyPosX;
    public static int selectedEnemyPos;

    //attacking enemy (-1 if none are attacking)
    public static int lastActiveEnemy;
    public static int lastActiveEnemyTick;
    public static boolean superCharge;
    public static int splashEnemyCount;

    //0 = miss
    //1 = glancing blow
    //2 = normal attack
    //3 = super attack
    // player uses their own attackType so slashes don't conflict
    public static int playerAttackType;
    public static int[] attackType = new int[3];
    public static int currentConvNpcIndex;
    public static boolean currentConvNpcCanWalk;
    public static int currentConvNpcDir;
    public static boolean currentConvNpcSet = false;

    //used to store action messages that need to be displayed
    public static message[] messages;

    //used to store a story message string that needs to be broken up
    public static int lrgMessageOffsetY;
    public static int lrgMessageScrollOffsetY;
    public static int tmplrgMessageOffsetY;
    public static int tmplrgMessageScrollOffsetY;
    public static int tmpitem_maxu, tmpitem_maxv;
    public static int tmpitemcur_x, tmpitemcur_y;
    public static int tmpselItem, tmpitemCursorPos;
    public static Random rand;
    public static boolean resort;
    public static boolean consumedDown;
    public static boolean consumedUp;
    public static int transRight;
    public static int transTop;
    public static int transBottom;
    public static int transLeft;
    public static int gold;
    public static Pen pen;
    public static float valA;
    public static float valB;
    //used for scrolling in and out the control panel
    public static int nextBattleModeState;
    //0 = no item
    //1 = single enemy item
    //2 = group enemy item
    public static int usingItemType;
    public static int selectedItemIndex;
    public static String selectedItemName;
    public static boolean datKilled;

    //new variables for state preservation
    public static int lastStringUsed;
    public static int lastStringArrayUsed;
    public static int lastNpcIndex;
    public static conversation focus;
    
    //herterods myths grouped
    public static final int[] ITEM_SORTORDER = {Constants.ITEM_HEALINGHERB, Constants.ITEM_HEALINGSALVE, Constants.ITEM_RESTOREPOTION, Constants.ITEM_HERBALANTIDOTE, Constants.ITEM_HERBALANTISLEEP, Constants.ITEM_TORCH, Constants.ITEM_BLIZZARD_STAFF, Constants.ITEM_BALEFIREORB, Constants.ITEM_BALEFIREINSTRUCTIONS, Constants.ITEM_FERNSLETTER, Constants.ITEM_PILEOFGOLD, Constants.ITEM_MADELINESLIST, Constants.ITEM_BAGOFSTUFF, Constants.ITEM_INJUREDSOLDIER, Constants.ITEM_COMPASS, Constants.ITEM_MAP, Constants.ITEM_KALINSMESSAGE, Constants.ITEM_SAPLING, Constants.ITEM_MAJIKWERKSCERTIFICATE, Constants.ITEM_STRANGELETTER, Constants.ITEM_CHRISTOFFS_LETTER, Constants.ITEM_ANTIQUE_SWORD, Constants.ITEM_SMUGGLERS_CODE, Constants.ITEM_BOFFENS_DIRECTIONS, Constants.ITEM_KRYSYSTREASURE, Constants.ITEM_JIMMYSTREASURE, Constants.ITEM_TORNPAGE, Constants.ITEM_WIZZARDOSNOTEWRONG, Constants.ITEM_WIZZARDOSNOTERIGHT, Constants.ITEM_MAGICWERKS_DOOR_PASS, Constants.ITEM_TOLLDIRECTIONS, Constants.ITEM_DELIVERYLETTER, Constants.ITEM_HERTERODSMYTHS, Constants.ITEM_HERTERODS_MYTHS_P17, Constants.ITEM_HERTERODS_MYTHS_P23, Constants.ITEM_HERTERODS_MYTHS_P80, Constants.ITEM_HERTERODS_MYTHS_P145, Constants.ITEM_CARRIESTREASURE, Constants.ITEM_STEVES_NOTE, Constants.ITEM_MW_LOG_ENTRY, Constants.ITEM_MAGICAL_WARP, Constants.ITEM_SAPLING_LIFEFORCE, Constants.ITEM_HUGHS_TREASURENOTE};
    public static final int[] shopItems = {Constants.ITEM_HEALINGHERB, Constants.ITEM_HEALINGSALVE, Constants.ITEM_RESTOREPOTION, Constants.ITEM_HERBALANTIDOTE, Constants.ITEM_HERBALANTISLEEP, Constants.ITEM_TORCH};//, 6, 7, 8, 9 };
    public static int minQuantity;
    public static int maxQuantity;
    public static int currentQuantity;
    public static int cost;
    public static boolean buy;
    public static boolean isPoisoned;
    public static boolean isEntranced;
    public static boolean isStunned;
    public static int isEntrancedCount;
    public static int isStunnedCount;
    public static int antisleepCount;
    public static int[] dropItems = {Constants.ITEM_HEALINGHERB, Constants.ITEM_HEALINGHERB, Constants.ITEM_HEALINGHERB, Constants.ITEM_HERBALANTIDOTE, Constants.ITEM_HERBALANTIDOTE, Constants.ITEM_HEALINGHERB, Constants.ITEM_HEALINGHERB, Constants.ITEM_HEALINGHERB, Constants.ITEM_HERBALANTISLEEP, Constants.ITEM_HERBALANTISLEEP, Constants.ITEM_HEALINGSALVE, Constants.ITEM_HEALINGSALVE, Constants.ITEM_HEALINGSALVE, Constants.ITEM_HEALINGHERB, Constants.ITEM_TORCH, Constants.ITEM_TORCH};

    public static boolean hasSound;
    public static boolean hasVibration;
    public static boolean isBoss;
    public static boolean finishItemOverride;
    public static int currentBossId;
    public static int currentBossBattleId;
    public static int currentBossPos;
    public static int battleFlagIndex;
    public static int battleFlagIndex1;
    public static int battleFlagIndex2;
    public static int battleSearchIndex;

    //floating hearts on-screen display to show damage/heal points
    public static final int MAX_FLOATINGHEARTS = 6;
    public static final int FLOATINGHEARTS_FLOATTIME = 40;
    public static int[] fh_x = new int[MAX_FLOATINGHEARTS];
    public static int[] fh_y = new int[MAX_FLOATINGHEARTS];
    public static int[] fh_c = new int[MAX_FLOATINGHEARTS];
    public static int[] fh_i = new int[MAX_FLOATINGHEARTS];
    public static int[] fh_d = new int[MAX_FLOATINGHEARTS];
    public static int[] fh_t = new int[MAX_FLOATINGHEARTS];

    //enemy damage (drop down)
    private static final int[] fh_xmap = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1};
    private static final int[] fh_ymap = {8, 8, 8, 8, 8, 8, 8, 8, 8, 5, -4, -4, -4, 4, 4, 4, -2, -2, 2, 2};

    //heal (float up)
    private static final int[] fh2_xmap = {-1, 0, -1, 0, -2, 0, -2, 0, -1, 0, -1, 0, 0, 0, 1, 1, 2, 2, 1, 1, 0, 0, -1, -1, -2, -2, -1, -1, 0, 0, 1, 1, 2, 2, 1, 1};
    private static final int[] fh2_ymap = {0, 0, 0, 0, -1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0};

    //player damage (drop down)
    private static final int[] fh3_xmap = {0, 0, 0, 0, 0, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1};
    private static final int[] fh3_ymap = {8, 8, 8, 8, 1, -4, -4, -4, 4, 4, 4, -2, -2, 2, 2};

    public static final int MAX_STATUSMSGS = 2;
    public static final int STATUSMSGS_DISPLAYTIME = 15;
    public static int[] smsg_x = new int[MAX_STATUSMSGS];
    public static int[] smsg_y = new int[MAX_STATUSMSGS];
    public static int[] smsg_c = new int[MAX_STATUSMSGS];
    public static int[] smsg_i = new int[MAX_STATUSMSGS];
    public static String[] smsg = new String[MAX_STATUSMSGS];
    public static boolean allTargetsSelected = false;

    private static final int[] ENEMY_BATTLEMATRIX = {
        -1, 0, 0, 0, 0, 0, 0,
        -1, -1, -1, -1, -1, -1, -1,
        -1, -2, -3, -3, -3, -4, -4,
        -2, -2, -3, -3, -4, -5, -5};

    private static final int[] PLAYER_AP = {1, 2, 3, 3};
    private static final int[] PLAYER_BATTLEMATRIX = {
        0, 0, 0, 0,
        0, 0, 0, 0,
        0, -1, -1, 0,
        0, -1, -1, 0,
        0, -1, -1, -1,
        -1, -1, -2, -1,
        -1, -2, -2, -1};

    public int[][] tmpItems;
    private boolean expectingFlagIndex = false;
    private String expectedFlagIndex = "";
    private boolean expectingRoomIndex = false;
    private String expectedRoomIndex = "";
    private boolean[] loadFlags = null;
    private byte[] loadGS = null;
    private int[][] loadItems = null;
    private String loadName = null;
    private boolean renderLayerOneFlag, renderLayerTwoFlag;
    private String title;
    
    public MainWindow() {
        setTitle(Tyre.cApp.getString(AppResources.ID_APP_NAME));
        hasSound = true;
        hasVibration = true;
        isPoisoned = false;
        isEntranced = false;
        isStunned = false;
        usingItemType = 0;
        selectEnemy = false;
        lastActiveEnemy = -1;
        lastActiveEnemyTick = 0;
        whichEnemy = 0;
        cursorPosY = 0;
        cursorIndX = cursorIndY = 0;
        selectEnemyPosX = 0;
        selectedEnemyPos = 0;
        transRight = 0;
        transTop = 0;
        transBottom = 0;
        transLeft = 0;
        gameState = 0;
        battleModeState = 0;
        gameSubState = 0;
        prevGameState = 0;
        herbShopSubState = 0;
        mainMenuSubState = 0;
        attackSuccess = false;
        resort = false;
        rand = new Random();

        try {
            bft = Font.findFont("BoldSmall");
            ft = Font.findFont("BoldMedium");
            sf = Font.findFont("BoldSmall");
            bof = Font.findFont("BoldMedium");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        count = 0;
        gold = 0;
        allTargetsSelected = false;

        //read constants defined in resource file
        ROOM_WIDTH = Integer.valueOf(Tyre.cApp.getString(Resources.ROOM_WIDTH).trim()).intValue();
        ROOM_HEIGHT = Integer.valueOf(Tyre.cApp.getString(Resources.ROOM_HEIGHT).trim()).intValue();
        SCREEN_WIDTH = Integer.valueOf(Tyre.cApp.getString(Resources.SCREEN_WIDTH).trim()).intValue();
        SCREEN_HEIGHT = Integer.valueOf(Tyre.cApp.getString(Resources.SCREEN_HEIGHT).trim()).intValue();
        GUTTER_X = Integer.valueOf(Tyre.cApp.getString(Resources.GUTTER_X).trim()).intValue();
        GUTTER_Y = Integer.valueOf(Tyre.cApp.getString(Resources.GUTTER_Y).trim()).intValue();
        RENDER_X = (SCREEN_WIDTH - ROOM_WIDTH) / 2;
        RENDER_Y = (SCREEN_HEIGHT - ROOM_HEIGHT) / 2;
        DEATHSCROLL_TIME = (SCREEN_HEIGHT / 3) + 1;
        RENDER_ADJACENT_ROOMS = Integer.valueOf(Tyre.cApp.getString(Resources.RENDER_ADJACENT_ROOMS).trim()).intValue();
        MAINMENU_HUD_WIDTH = Integer.valueOf(Tyre.cApp.getString(Resources.MAINMENU_HUD_WIDTH).trim()).intValue();
        MAINMENU_HUD_HEIGHT = Integer.valueOf(Tyre.cApp.getString(Resources.MAINMENU_HUD_HEIGHT).trim()).intValue();
        
        //Mainmenu positions are based on the size
        MAINMENU_HUD_XPOS = (SCREEN_WIDTH - MAINMENU_HUD_WIDTH) / 2;
        MAINMENU_HUD_YPOS = (SCREEN_HEIGHT - MAINMENU_HUD_HEIGHT) / 2;

        HELP_PAGE_OFFSET1 = Integer.valueOf(Tyre.cApp.getString(Resources.HELP_PAGE_OFFSET1).trim()).intValue();
        HELP_PAGE_OFFSET2 = Integer.valueOf(Tyre.cApp.getString(Resources.HELP_PAGE_OFFSET2).trim()).intValue();
        HELP_PAGE_OFFSET3 = Integer.valueOf(Tyre.cApp.getString(Resources.HELP_PAGE_OFFSET3).trim()).intValue();
        HELP_PAGE_OFFSET4 = Integer.valueOf(Tyre.cApp.getString(Resources.HELP_PAGE_OFFSET4).trim()).intValue();

        PROLOGUECONTENT_VIEWABLE_HEIGHT = MAINMENU_HUD_HEIGHT + 2;
        LRGMESSAGECONTENT_PROLOGUE_WIDTH = MAINMENU_HUD_WIDTH - 10;
        PROLOGUEMESSAGES_WIDTH = LRGMESSAGECONTENT_PROLOGUE_WIDTH - 4;

        MESSAGE_FONT_HEIGHT = Integer.valueOf(Tyre.cApp.getString(Resources.MESSAGE_FONT_HEIGHT).trim()).intValue();
        //HUD is room width - 12 (3 * 2 for border, 3 * 2 + cellpadding)
        //MESSAGES_WIDTH = ROOM_WIDTH - 12;
        MESSAGES_WIDTH = Integer.valueOf(Tyre.cApp.getString(Resources.MESSAGES_WIDTH).trim()).intValue();
        renderLayerOneFlag = renderLayerTwoFlag = false;
        skipMenuPrep = false;
    }

    public final void setTitle(String s) {
        title = s;
    }
    
    public void changeState(int state) {
        changeState(state, -1, -1);
    }

    public void changeState(int state, int subState) {
        changeState(state, subState, -1);
    }

    public void changeState(int state, int subState, int subSubState) {
        Logger.wr("changeState: state:" + state + " subState:" + subState + " subSubState:" + subSubState);
        if (gameState != 8 && gameState != 10 && gameState != 1 && gameState != 11) {
            prevGameState = gameState;
        }
        gameState = state;
        switch (state) {
            case 0:
                //playing
                //reset adjacent room render
                resetBuildFlags();
                if (subState != -1) {
                    gameSubState = subState;
                }

                switch (gameSubState) {
                    case 0:
                        //resort
                        break;
                    case 1:
                        //action menu
                        cursorPosY = 65;
                        break;
                    case 2:
                        //map messages
                        break;
                    case 3:
                        //statistics
                        break;
                    case 4:
                        //inventory
                        break;
                    case 5:
                        //story messages
                        break;
                    case 6:
                        //herb shop
                        cursorPosY = 45;
                        break;
                    case 7:
                        //inn
                        break;
                    /*
                    case 8:
                        //spell shop
                        break;
                    case 9:
                        //spell inventory
                        break;
                    */
                    case 10:
                        //item receipt
                        if (hasVibration) {
                            danger.audio.AudioManager.vibrate(100);
                        }
                        PlaySystemSound(Meta.MENU_ACTIVATION);
                        break;
                    case 11:
                        //toll keeper
                        break;
                }
                break;
            case 1:
                //pause
                break;
            case 2:
                //room link
                break;
            case 3:
                //battle
                if (subState != -1) {
                    battleModeState = subState;
                }
                switch (battleModeState) {
                    case 0:
                        //opening message
                        break;
                    case 1:
                        //enemy's turn
                        break;
                    case 2:
                        //player's turn
                        break;
                    case 3:
                        //item menu up
                        break;
                    /*
                    case 4:
                        //spell menu up
                        break;
                    */
                    case 5:
                        //battle has ended
                        timestamp = 0;
                        break;
                    case 6:
                        //experience message
                        break;
                    case 7:
                        //inventory
                        break;
                    case 8:
                        //scroll in panel
                        displayPanelTime = 0;
                        displayPanelX = 240;
                        break;
                    case 9:
                        //scroll out panel
                        displayPanelTime = 0;
                        displayPanelX = 180;
                        break;
                    case 10:
                        //player has died
                        timestamp = 0;
                        break;
                    case 11:
                        //do you want to continue
                        if (hasVibration) {
                            danger.audio.AudioManager.vibrate(250);
                            danger.audio.AudioManager.vibrate(100);
                            danger.audio.AudioManager.vibrate(50);
                        }
                        break;
                    case 12:
                        //game won
                        timestamp = 0;
                        tmpcount = 0;
                        break;
                }
                break;
            case 4:
                //trans top
                break;
            case 5:
                //trans right
                break;
            case 6:
                //trans bottom
                break;
            case 7:
                //trans left
                break;
            case 8:
                //loading
                break;
            case 9:
                //building room
                break;
            case 10:
                //main menu first load
                if (subState != -1) {
                    mainMenuSubState = subState;
                }
                if (skipMenuPrep) {
                    skipMenuPrep = false;
                    //make preps that are required for data loading, just not resetting states
                    switch (mainMenuSubState) {
                        case 5:
                            setupSelectorChar();
                            break;
                    }
                } else {
                    prepMainMenuSubState();
                }
                break;
        }
    }

    private void prepMainMenuSubState() {
        switch (mainMenuSubState) {
            case 0:
                //main menu
                if (Tyre.gameStart) {
                    cursorPosY = 51;
                } else {
                    cursorPosY = 39;
                }
                break;
            case 1:
                //new game verify
                cursorPosY = 51;
                break;
            case 2:
                //settings
                cursorPosY = 39;
                if (hasSound) {
                    o = 130;
                } else {
                    o = 155;
                }
                if (hasVibration) {
                    m = 130;
                } else {
                    m = 155;
                }
                break;
            case 3:
                //help
                helpPageIndex = 0;
                break;
            case 4:
                //prologue
                lrgMessageOffsetY = 0;
                timestamp = count;
                break;
            case 5:
                //player name prompt
                cursorPosY = ENTER_NAME_CURSORY;
                cursorIndY = 0;
                cursorIndX = 0;
                setupSelectorChar();
                tmpStr = ResourceContainer.strings[Constants.MALE_PLAYER_NAME_INDEX];
                break;
            case 7:
                cursorPosY = 51;
                break;
        }
    }

    private void setupSelectorChar() {
        Tyre.charselector = new character();
        Tyre.charselector.loadCharData(cursorIndX);
        Tyre.charselector.walkStart = count;
        Tyre.charselector.walk = false;
        Tyre.charselector.posX = 110;
        Tyre.charselector.posY = 38;
        Tyre.charselector.dir = 0;
    }

    private void drawLayerOneObjects(Pen inPen) {
        if (RENDER_ADJACENT_ROOMS == 1) {
            inPen.drawBitmap(RENDER_X, RENDER_Y, Tyre.currentRoom);
        } else {
            inPen.drawBitmap(RENDER_X, RENDER_Y, Tyre.currentRoom, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        }
        if (renderLayerOneFlag && RENDER_ADJACENT_ROOMS == 1) {
            drawAdjLayerOneObjects(inPen);
            renderLayerOneFlag = false;
        }
        drawLayerOneFlaggedObjects(inPen, true, RENDER_X, RENDER_Y);
    }

    public final void drawLayerOneFlaggedObjects(Pen inPen, boolean drawPlayer) {
        drawLayerOneFlaggedObjects(inPen, drawPlayer, 0, 0);
    }

    //draw at RENDER_X, RENDER_Y origin    
    public final void drawLayerOneFlaggedObjects(Pen inPen, boolean drawPlayer, int x_off, int y_off) {
        if (Tyre.hasCanWalkDynamic) {
            for (i = 0; i < Tyre.canWalkDynamic.length; i++) {
                switch (Tyre.canWalkDynamic[i].type) {
                    case 12:
                        if (Tyre.canWalkDynamic[i].flagIndex == -1
                                || (Tyre.canWalkDynamic[i].flagDir == 0 && ResourceContainer.flags[Tyre.canWalkDynamic[i].flagIndex] == true)
                                || (Tyre.canWalkDynamic[i].flagDir == 1 && ResourceContainer.flags[Tyre.canWalkDynamic[i].flagIndex] == false)) {
                            inPen.drawBitmap(Tyre.canWalkDynamic[i].posX + x_off, Tyre.canWalkDynamic[i].posY + y_off, Tyre.bitmaps[Tyre.canWalkDynamic[i].imageIndex]);
                        }
                        break;
                }
            }
        }
        
        for (i = 0; i < Tyre.rObjects.length; i++) {
            switch (Tyre.roomObjects[Tyre.rObjects[i]].type) {
                case 0:
                    //link table object
                    if (Tyre.roomObjects[Tyre.rObjects[i]].flagIndex == -1
                            || (Tyre.roomObjects[Tyre.rObjects[i]].flagDir == 0 && ResourceContainer.flags[Tyre.roomObjects[Tyre.rObjects[i]].flagIndex] == true)
                            || (Tyre.roomObjects[Tyre.rObjects[i]].flagDir == 1 && ResourceContainer.flags[Tyre.roomObjects[Tyre.rObjects[i]].flagIndex] == false)) {
                        inPen.drawBitmap(Tyre.roomObjects[Tyre.rObjects[i]].posX + x_off, Tyre.roomObjects[Tyre.rObjects[i]].posY + y_off, Tyre.bitmaps[Tyre.roomObjects[Tyre.rObjects[i]].imageIndex]);
                    }
                    break;
                case 1:
                    //can't walk over
                    if (Tyre.roomObjects[Tyre.rObjects[i]].flagIndex == -1
                            || (Tyre.roomObjects[Tyre.rObjects[i]].flagDir == 0 && ResourceContainer.flags[Tyre.roomObjects[Tyre.rObjects[i]].flagIndex] == true)
                            || (Tyre.roomObjects[Tyre.rObjects[i]].flagDir == 1 && ResourceContainer.flags[Tyre.roomObjects[Tyre.rObjects[i]].flagIndex] == false)) {
                        inPen.drawBitmap(Tyre.roomObjects[Tyre.rObjects[i]].posX + x_off, Tyre.roomObjects[Tyre.rObjects[i]].posY + y_off, Tyre.bitmaps[Tyre.roomObjects[Tyre.rObjects[i]].imageIndex]);
                    }
                    break;
                case 2:
                    //can walk over
                    break;
                case 3:
                    //characters
                    if (drawPlayer) {
                        Tyre.player.paint(inPen, x_off, y_off);
                    }
                    break;
                case 4:
                    //can walk over
                    break;
                case 5:
                    //enemy link object
                    //used to be X,Y linked object
                    if (Tyre.roomObjects[Tyre.rObjects[i]].flagIndex == -1
                            || (Tyre.roomObjects[Tyre.rObjects[i]].flagDir == 0 && ResourceContainer.flags[Tyre.roomObjects[Tyre.rObjects[i]].flagIndex] == true)
                            || (Tyre.roomObjects[Tyre.rObjects[i]].flagDir == 1 && ResourceContainer.flags[Tyre.roomObjects[Tyre.rObjects[i]].flagIndex] == false)) {
                        inPen.drawBitmap(Tyre.roomObjects[Tyre.rObjects[i]].posX + x_off, Tyre.roomObjects[Tyre.rObjects[i]].posY + y_off, Tyre.bitmaps[Tyre.roomObjects[Tyre.rObjects[i]].imageIndex]);
                    }
                    break;
                case 6:
                    //npcs
                    if (Tyre.roomObjects[Tyre.rObjects[i]].flagIndex == -1
                            || (Tyre.roomObjects[Tyre.rObjects[i]].flagDir == 0 && ResourceContainer.flags[Tyre.roomObjects[Tyre.rObjects[i]].flagIndex] == true)
                            || (Tyre.roomObjects[Tyre.rObjects[i]].flagDir == 1 && ResourceContainer.flags[Tyre.roomObjects[Tyre.rObjects[i]].flagIndex] == false)) {

                        Tyre.roomChars[Tyre.roomObjects[Tyre.rObjects[i]].npcIndex].paint(inPen, x_off, y_off);
                    }
                    break;
            }
        }
    }

    private void drawAdjLayerOneObjects(Pen inPen) {
        //test adjacent room rendering
        if (Tyre.adjRoom[0] != null) {
            inPen.drawBitmap(RENDER_X + ROOM_WIDTH, RENDER_Y, Tyre.adjRoom[0]);
        }
        if (Tyre.adjRoom[1] != null) {
            inPen.drawBitmap(RENDER_X + ROOM_WIDTH, RENDER_Y + ROOM_HEIGHT, Tyre.adjRoom[1]);
        }
        if (Tyre.adjRoom[2] != null) {
            inPen.drawBitmap(RENDER_X, RENDER_Y + ROOM_HEIGHT, Tyre.adjRoom[2]);
        }
        //bottom left
        if (Tyre.adjRoom[3] != null) {
            inPen.drawBitmap(RENDER_X - ROOM_WIDTH, RENDER_Y + ROOM_HEIGHT, Tyre.adjRoom[3]);
        }
        //left
        if (Tyre.adjRoom[4] != null) {
            inPen.drawBitmap(RENDER_X - ROOM_WIDTH, RENDER_Y, Tyre.adjRoom[4]);
        }
        //top left
        if (Tyre.adjRoom[5] != null) {
            inPen.drawBitmap(RENDER_X - ROOM_WIDTH, RENDER_Y - ROOM_HEIGHT, Tyre.adjRoom[5]);
        }
        //top
        if (Tyre.adjRoom[6] != null) {
            inPen.drawBitmap(RENDER_X, RENDER_Y - ROOM_HEIGHT, Tyre.adjRoom[6]);
        }
        //top right
        if (Tyre.adjRoom[7] != null) {
            inPen.drawBitmap(ROOM_WIDTH + RENDER_X, RENDER_Y - ROOM_HEIGHT, Tyre.adjRoom[7]);
        }
    }

    //draws all layer two objects to the given pen
    private void drawLayerTwoObjects(Pen inPen) {
        inPen.mmgPen.SetAdvRenderHints();
        if (RENDER_ADJACENT_ROOMS == 1) {
            inPen.drawBitmap(RENDER_X, RENDER_Y, Tyre.layerTwo);
        } else {
            inPen.drawBitmap(RENDER_X, RENDER_Y, Tyre.layerTwo, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        }
        if (renderLayerTwoFlag && RENDER_ADJACENT_ROOMS == 1) {
            drawAdjLayerTwoObjects(inPen);
            renderLayerTwoFlag = false;
        }
    }

    private void drawAdjLayerTwoObjects(Pen inPen) {
        //test adjacent room rendering
        //right
        if (Tyre.adjLayerTwo[0] != null) {
            inPen.drawBitmap(RENDER_X + ROOM_WIDTH, RENDER_Y, Tyre.adjLayerTwo[0]);
        }
        //bottom right
        if (Tyre.adjLayerTwo[1] != null) {
            inPen.drawBitmap(RENDER_X + ROOM_WIDTH, RENDER_Y + ROOM_HEIGHT, Tyre.adjLayerTwo[1]);
        }
        //bottom
        if (Tyre.adjLayerTwo[2] != null) {
            inPen.drawBitmap(RENDER_X, RENDER_Y + ROOM_HEIGHT, Tyre.adjLayerTwo[2]);
        }
        //bottom left
        if (Tyre.adjLayerTwo[3] != null) {
            inPen.drawBitmap(RENDER_X - ROOM_WIDTH, RENDER_Y + ROOM_HEIGHT, Tyre.adjLayerTwo[3]);
        }
        //left
        if (Tyre.adjLayerTwo[4] != null) {
            inPen.drawBitmap(RENDER_X - ROOM_WIDTH, RENDER_Y, Tyre.adjLayerTwo[4]);
        }
        //top left
        if (Tyre.adjLayerTwo[5] != null) {
            inPen.drawBitmap(RENDER_X - ROOM_WIDTH, RENDER_Y - ROOM_HEIGHT, Tyre.adjLayerTwo[5]);
        }
        //top
        if (Tyre.adjLayerTwo[6] != null) {
            inPen.drawBitmap(RENDER_X, RENDER_Y - ROOM_HEIGHT, Tyre.adjLayerTwo[6]);
        }
        //top right
        if (Tyre.adjLayerTwo[7] != null) {
            inPen.drawBitmap(RENDER_X + ROOM_WIDTH, RENDER_Y - ROOM_HEIGHT, Tyre.adjLayerTwo[7]);
        }
    }

    private void drawPlayerActionMenu(Pen inPen) {
        inPen.drawBitmap(RENDER_X + GUTTER_X, RENDER_Y + GUTTER_Y + 85, Tyre.consoleBitmaps[GAMEHUD_INDEX]);
        inPen.drawBitmap(RENDER_X + GUTTER_X + (seli * 48), RENDER_Y + GUTTER_Y + 87, Tyre.consoleBitmaps[HUDSELECTOR_INDEX]);
    }

    //draw map messages
    private void drawMapMessagesConsole(Pen inPen) {
        inPen.drawBitmap(RENDER_X, RENDER_Y + GUTTER_Y + 87, Tyre.consoleBitmaps[DRAWMAPMSG_INDEX]);
    }

    private void drawPlayerStats(Pen inPen) {
        //stats
        inPen.drawBitmap(RENDER_X + GUTTER_X + LRGMESSAGE_POSX, RENDER_Y + GUTTER_Y + LRGMESSAGE_POSY, Tyre.consoleBitmaps[MSGBOX_INDEX]);
        inPen.drawBitmap(RENDER_X + GUTTER_X + LRGMESSAGECONTENT_POSX, RENDER_Y + GUTTER_Y + LRGMESSAGECONTENT_POSY, Tyre.consoleBitmaps[MSGCONTENT_INDEX], 0, lrgMessageOffsetY, LRGMESSAGECONTENT_VIEWABLE_WIDTH, lrgMessageOffsetY + LRGMESSAGECONTENT_VIEWABLE_HEIGHT);
        drawScrollBar(inPen);
    }

    private void drawScrollBar(Pen inPen) {
        if (Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight() > LRGMESSAGECONTENT_VIEWABLE_HEIGHT) {
            inPen.setColor(Color.GRAY15);
            inPen.fillRect(RENDER_X + GUTTER_X + LRGMESSAGESCROLLBAR_POSX, RENDER_Y + GUTTER_Y + LRGMESSAGESCROLLBAR_POSY, RENDER_X + GUTTER_X + LRGMESSAGESCROLLBAR_POSX + LRGMESSAGESCROLLBAR_WIDTH, RENDER_Y + GUTTER_Y + LRGMESSAGESCROLLBAR_POSY + LRGMESSAGESCROLLBAR_HEIGHT);
            inPen.setColor(Color.RED);
            tmpInt = LRGMESSAGESCROLLBAR_POSY + lrgMessageScrollOffsetY + ((LRGMESSAGESCROLLBAR_HEIGHT * 100) / Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight());
            if (tmpInt > (LRGMESSAGESCROLLBAR_POSY + LRGMESSAGESCROLLBAR_HEIGHT)) {
                tmpInt = (LRGMESSAGESCROLLBAR_POSY + LRGMESSAGESCROLLBAR_HEIGHT);
            }
            inPen.fillRect(RENDER_X + GUTTER_X + LRGMESSAGESCROLLBAR_POSX, RENDER_Y + GUTTER_Y + LRGMESSAGESCROLLBAR_POSY + lrgMessageScrollOffsetY, RENDER_X + GUTTER_X + LRGMESSAGESCROLLBAR_POSX + LRGMESSAGESCROLLBAR_WIDTH, RENDER_Y + GUTTER_Y + tmpInt);
        } else {
            inPen.setColor(Color.RED);
            inPen.fillRect(RENDER_X + GUTTER_X + LRGMESSAGESCROLLBAR_POSX, RENDER_Y + GUTTER_Y + LRGMESSAGESCROLLBAR_POSY, RENDER_X + GUTTER_X + LRGMESSAGESCROLLBAR_POSX + LRGMESSAGESCROLLBAR_WIDTH, RENDER_Y + GUTTER_Y + LRGMESSAGESCROLLBAR_POSY + LRGMESSAGESCROLLBAR_HEIGHT);
        }
    }

    private void drawInventory(Pen inPen) {
        inPen.drawBitmap(RENDER_X + GUTTER_X + LRGMESSAGE_POSX, RENDER_Y + GUTTER_Y + LRGMESSAGE_POSY, Tyre.consoleBitmaps[MSGBOX_INDEX]);

        if (LRGMESSAGECONTENT_VIEWABLE_HEIGHT > Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight()) {
            inPen.drawBitmap(RENDER_X + GUTTER_X + LRGMESSAGECONTENT_POSX, RENDER_Y + GUTTER_Y + LRGMESSAGECONTENT_POSY, Tyre.consoleBitmaps[MSGCONTENT_INDEX]);
        } else {
            inPen.drawBitmap(RENDER_X + GUTTER_X + LRGMESSAGECONTENT_POSX, RENDER_Y + GUTTER_Y + LRGMESSAGECONTENT_POSY, Tyre.consoleBitmaps[MSGCONTENT_INDEX], 0, lrgMessageOffsetY, LRGMESSAGECONTENT_VIEWABLE_WIDTH, lrgMessageOffsetY + LRGMESSAGECONTENT_VIEWABLE_HEIGHT);
        }
        drawScrollBar(inPen);
        if (numItems > 0) {
            tx = LRGMESSAGECONTENT_POSX + (4 + (itemcur_x * ITEMVIEW_ITEM_ICON_WIDTH));
            ty = LRGMESSAGECONTENT_POSY + ((itemcur_y * LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + 18);
            inPen.drawRect(RENDER_X + GUTTER_X + tx, RENDER_Y + GUTTER_Y + ty, RENDER_X + GUTTER_X + tx + ITEMVIEW_ITEM_ICON_WIDTH, RENDER_Y + GUTTER_Y + ty + LRGMESSAGEITEMCONTENT_ROW_HEIGHT);

            //todo move this to a redraw function and make title static
            ty = (lrgMessageOffsetY / LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + itemcur_y;
            tx = ((ty * ITEMVIEW_ITEMS_PER_ROW) + itemcur_x);

            //Herb shop purchase screen
            if (gameSubState == 6 && herbShopSubState == 1) {
                selItem = shopItems[tx];
            } else {
                selItem = Tyre.player.items[tx][0];
            }

            inPen.setColor(0x000000);
            inPen.fillRect(RENDER_X + GUTTER_X + LRGMESSAGE_POSX + 4, RENDER_Y + GUTTER_Y + LRGMESSAGE_POSY + 4, RENDER_X + GUTTER_X + LRGMESSAGE_POSX + LRGMESSAGE_WIDTH - 6, RENDER_Y + GUTTER_Y + LRGMESSAGE_POSY + 20);
            inPen.setFont(sf);
            inPen.setColor(-15524477);
            inPen.drawLine(RENDER_X + GUTTER_X + LRGMESSAGE_POSX + 4 + 10, RENDER_Y + GUTTER_Y + LRGMESSAGE_POSY + 20, RENDER_X + GUTTER_X + LRGMESSAGE_POSX + LRGMESSAGE_WIDTH - 6 - 10, RENDER_Y + GUTTER_Y + LRGMESSAGE_POSY + 20);
            inPen.setColor(0xffffff);
            tmpStr = ResourceContainer.strings[ResourceContainer.items[selItem].stringIndex];
            inPen.drawText((SCREEN_WIDTH - sf.getWidth(tmpStr)) / 2, RENDER_Y + GUTTER_Y + LRGMESSAGE_POSY + LRGMESSAGECONTENT_ROW_HEIGHT + 3, tmpStr);
        }
    }

    private void drawStoryMessage(Pen inPen) {
        switch (storyType) {
            //story window
            case 0:
                inPen.drawBitmap(RENDER_X + GUTTER_X + LRGMESSAGE_POSX, RENDER_Y + GUTTER_Y + LRGMESSAGE_POSY, Tyre.consoleBitmaps[MSGBOX_INDEX]);
                inPen.drawBitmap(RENDER_X + GUTTER_X + LRGMESSAGECONTENT_POSX, RENDER_Y + GUTTER_Y + LRGMESSAGECONTENT_POSY, Tyre.consoleBitmaps[MSGCONTENT_INDEX], 0, lrgMessageOffsetY, LRGMESSAGECONTENT_VIEWABLE_WIDTH, lrgMessageOffsetY + LRGMESSAGECONTENT_VIEWABLE_HEIGHT);
                drawScrollBar(inPen);
                break;
            //npc conversation
            case 1:
                //NPC Talking - draw on top
                if (!inConvPlayer) {
                    inPen.drawBitmap(RENDER_X + GUTTER_X, RENDER_Y, Tyre.consoleBitmaps[CONVOCONTENT_INDEX]);
                    inPen.drawBitmap(RENDER_X + GUTTER_X + 35, RENDER_Y + 3, Tyre.consoleBitmaps[MSGCONTENT_INDEX], 0, lrgMessageOffsetY, CONVERSATIONMSG_VIEWABLE_WIDTH, lrgMessageOffsetY + CONVERSATIONMSG_VIEWABLE_HEIGHT);
                    if (NpcImageOverride != -1) {
                        inPen.drawBitmap(RENDER_X + GUTTER_X + 8, RENDER_Y + 10, Tyre.bitmaps[NpcImageOverride]);
                    } else {
                        inPen.drawBitmap(RENDER_X + GUTTER_X + 8, RENDER_Y + 10, Tyre.bitmaps[Tyre.roomChars[inConvNpcIndex].fc], 0, 0, Tyre.bitmaps[Tyre.roomChars[inConvNpcIndex].fc].getWidth(), (Tyre.bitmaps[Tyre.roomChars[inConvNpcIndex].fc].getHeight() * 70 / 100));
                    }
                } 
                //Player Talking - draw on bottom
                else {
                    inPen.drawBitmap(RENDER_X + GUTTER_X, RENDER_Y + ROOM_HEIGHT - 64, Tyre.consoleBitmaps[CONVOCONTENT_INDEX]);
                    inPen.drawBitmap(RENDER_X + GUTTER_X + 3, RENDER_Y + ROOM_HEIGHT - 61, Tyre.consoleBitmaps[MSGCONTENT_INDEX], 0, lrgMessageOffsetY, CONVERSATIONMSG_VIEWABLE_WIDTH, lrgMessageOffsetY + CONVERSATIONMSG_VIEWABLE_HEIGHT);
                    inPen.drawBitmap(RENDER_X + GUTTER_X + 210, RENDER_Y + ROOM_HEIGHT - 54, Tyre.bitmaps[Tyre.player.fc], 0, 0, Tyre.bitmaps[Tyre.player.fc].getWidth(), (Tyre.bitmaps[Tyre.player.fc].getHeight() * 70 / 100));
                }
                break;
        }
    }

    public void formatPlayerStats() {
        //display item contents
        lrgMessageOffsetY = 0;
        lrgMessageScrollOffsetY = 0;
        Tyre.consoleBitmaps[MSGCONTENT_INDEX] = new Bitmap(LRGMESSAGECONTENT_VIEWABLE_WIDTH, (28 * LRGMESSAGECONTENT_ROW_HEIGHT));
        pen = Tyre.consoleBitmaps[MSGCONTENT_INDEX].createPen();
        o = 2;

        pen.setColor(-16448251);
        pen.fillRect(0, 0, LRGMESSAGECONTENT_VIEWABLE_WIDTH, (28 * LRGMESSAGECONTENT_ROW_HEIGHT));
        pen.setFont(sf);
        pen.setColor(Color.WHITE);
        pen.drawText(40, LRGMESSAGECONTENT_ROW_HEIGHT, ResourceContainer.consoleStrings[Constants.CONSTR_STATISTICS]);

        if (isPoisoned) {
            pen.drawText(2, (o * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_NAME] + ResourceContainer.strings[Tyre.player.stringIndex] + " (" + ResourceContainer.consoleStrings[Constants.CONSTR_POISONED] + ")");
        } else {
            pen.drawText(2, (o * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_NAME] + ResourceContainer.strings[Tyre.player.stringIndex]);
        }
        o++;

        //health status
        ///////////////////////////////////////////////////////
        pen.drawText(2, (o * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_STATSHEADER1]);
        int ty = (o * LRGMESSAGECONTENT_ROW_HEIGHT) + 6;
        o++;

        //draw slot border
        pen.drawRect(1, ty - 1, 3 + ITEMVIEW_ITEM_ICON_WIDTH, ty + 1 + ITEMVIEW_ITEM_ICON_HEIGHT);
        pen.drawRect(50, ty - 1, 52 + ITEMVIEW_ITEM_ICON_WIDTH, ty + 1 + ITEMVIEW_ITEM_ICON_HEIGHT);
        pen.drawRect(99, ty - 1, 101 + ITEMVIEW_ITEM_ICON_WIDTH, ty + 1 + ITEMVIEW_ITEM_ICON_HEIGHT);
        pen.drawBitmap(2, ty, Tyre.consoleBitmaps[CONSOLEBITMAPS_BATTLEHUD_TILES_START_INDEX + 2]);
        pen.drawBitmap(51, ty, Tyre.consoleBitmaps[CONSOLEBITMAPS_BATTLEHUD_TILES_START_INDEX + 1]);
        pen.drawBitmap(100, ty, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_GOLD].imageIndex]);

        formatPlayerHitsIcon(pen, 2, ty - 1, (Tyre.player.hp - Tyre.player.damage));
        formatPlayerStatusIcon(pen, 52, ty - 1);

        pen.setFont(ft);
        pen.setColor(0x000000);
        pen.drawText(108, ty + 36, "" + Tyre.player.goldCoins);
        pen.setFont(sf);
        o++;
        o++;
        o++;
        o++;

        //WEAPON AND ARMOR
        ///////////////////////////////////////
        pen.setColor(0xffffff);
        pen.drawText(2, (o * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_STATSHEADER2]);
        ty = (o * LRGMESSAGECONTENT_ROW_HEIGHT) + 6;
        o++;

        //draw slot border
        pen.drawRect(1, ty - 1, 3 + ITEMVIEW_ITEM_ICON_WIDTH, ty + 1 + ITEMVIEW_ITEM_ICON_HEIGHT);
        pen.drawRect(50, ty - 1, 52 + ITEMVIEW_ITEM_ICON_WIDTH, ty + 1 + ITEMVIEW_ITEM_ICON_HEIGHT);

        if (Tyre.player.weapon > -1) {
            pen.drawBitmap(2, ty, Tyre.bitmaps[ResourceContainer.items[Tyre.player.weapon].imageIndex]);
        }
        if (Tyre.player.armor > -1) {
            pen.drawBitmap(51, ty, Tyre.bitmaps[ResourceContainer.items[Tyre.player.armor].imageIndex]);
        }
        o++;
        o++;
        o++;
        o++;

        //ORBS OF PROTECTION
        ///////////////////////////////////////
        pen.setColor(0xffffff);
        pen.drawText(2, (o * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_STATSHEADER3]);
        ty = (o * LRGMESSAGECONTENT_ROW_HEIGHT) + 6;
        o++;

        //draw slot border
        pen.drawRect(1, ty - 1, 3 + ITEMVIEW_ITEM_ICON_WIDTH, ty + 1 + ITEMVIEW_ITEM_ICON_HEIGHT);
        pen.drawRect(50, ty - 1, 52 + ITEMVIEW_ITEM_ICON_WIDTH, ty + 1 + ITEMVIEW_ITEM_ICON_HEIGHT);
        pen.drawRect(99, ty - 1, 101 + ITEMVIEW_ITEM_ICON_WIDTH, ty + 1 + ITEMVIEW_ITEM_ICON_HEIGHT);

        if (Tyre.player.antipoison) {
            pen.drawBitmap(2, ty, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_ANTIPOISONORB].imageIndex]);
        }
        if (Tyre.player.antistun) {
            pen.drawBitmap(51, ty, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_ANTISTUNORB].imageIndex]);
        }
        if (Tyre.player.antisleeporb) {
            pen.drawBitmap(100, ty, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_ANTISLEEPORB].imageIndex]);
        }
        o++;
        o++;
        o++;
        o++;

        pen.drawText(2, (o * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_MISSES]);
        pen.drawText(STAT_XPOS, (o * LRGMESSAGECONTENT_ROW_HEIGHT), "" + Tyre.player.totalMisses);
        o++;

        pen.drawText(2, (o * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_DODGES]);
        pen.drawText(STAT_XPOS, (o * LRGMESSAGECONTENT_ROW_HEIGHT), "" + Tyre.player.totalDodges);
        o++;

        pen.drawText(2, (o * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_HITSGIVEN]);
        pen.drawText(STAT_XPOS, (o * LRGMESSAGECONTENT_ROW_HEIGHT), "" + Tyre.player.totalHitsGiven);
        o++;

        pen.drawText(2, (o * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_HITSTAKEN]);
        pen.drawText(STAT_XPOS, (o * LRGMESSAGECONTENT_ROW_HEIGHT), "" + Tyre.player.totalHitsTaken);
        o++;

        pen.drawText(2, (o * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_STRHITSGIVEN]);
        pen.drawText(STAT_XPOS, (o * LRGMESSAGECONTENT_ROW_HEIGHT), "" + Tyre.player.totalSuperAttackGiven);
        o++;

        pen.drawText(2, (o * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_RUN]);
        pen.drawText(STAT_XPOS, (o * LRGMESSAGECONTENT_ROW_HEIGHT), "" + Tyre.player.totalRun);
        o++;

        pen.drawText(2, (o * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_ITEMS]);
        pen.drawText(STAT_XPOS, (o * LRGMESSAGECONTENT_ROW_HEIGHT), "" + Tyre.player.totalItems);
        o++;

        //show equipment icons
        pen.drawText(2, (o * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_KILLS]);
        pen.drawText(STAT_XPOS, (o * LRGMESSAGECONTENT_ROW_HEIGHT), "" + Tyre.player.totalKills);
        o++;

        pen.drawText(2, (o * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_DEATHS]);
        pen.drawText(STAT_XPOS, (o * LRGMESSAGECONTENT_ROW_HEIGHT), "" + Tyre.player.totalDeaths);
        o++;

        pen.drawText(2, (o * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN]);
    }

    public void generateShopInventory() {

    }

    public void sortItemInventory() {
        tmpItems = new int[Constants.ITEM_INVENTORY_SIZE][2];
        tmpInt = 0;
        
        for (tmpInt2 = 0; tmpInt2 < Constants.ITEM_INVENTORY_SIZE; tmpInt2++) {
            if (tmpInt2 >= ITEM_SORTORDER.length) {
                break;
            }
            tx = Tyre.player.inInventory(ITEM_SORTORDER[tmpInt2]);
            if (tx > -1) {
                tmpItems[tmpInt][0] = Tyre.player.items[tx][0];
                tmpItems[tmpInt][1] = Tyre.player.items[tx][1];
                tmpInt++;
            }
        }
        for (tmpInt2 = tmpInt; tmpInt2 < Constants.ITEM_INVENTORY_SIZE; tmpInt2++) {
            tmpItems[tmpInt2][0] = -2;
            tmpItems[tmpInt2][1] = -2;
        }
        for (tmpInt = 0; tmpInt < Constants.ITEM_INVENTORY_SIZE; tmpInt++) {
            Tyre.player.items[tmpInt][0] = tmpItems[tmpInt][0];
            Tyre.player.items[tmpInt][1] = tmpItems[tmpInt][1];
        }
    }

    public void formatItemInventory() {
        int titlePosX = 45;
        //display item contents
        lrgMessageOffsetY = 0;
        lrgMessageScrollOffsetY = 0;
        itemCursorPos = (2 * LRGMESSAGECONTENT_ROW_HEIGHT);

        k = 0;
        item_u = item_v = 0;
        itemcur_x = itemcur_y = 0;

        //inventory size = 25
        for (i = 0; i < ITEM_SORTORDER.length; i++) {
            if (Tyre.player.items[i][0] > -1) {
                k++;
                item_u++;
                if (item_u >= ITEMVIEW_ITEMS_PER_ROW) {
                    item_v++;
                    item_u = 0;
                }

            } else {
                i = Constants.ITEM_INVENTORY_SIZE;
            }
        }
        numItems = k;

        //if u is 0 then last item pushed onto a new line, deduct from max_v
        if (item_u > 0) {
            item_maxv = item_v;
        } else {
            item_maxv = item_v - 1;
        }
        if (item_v > 0) {
            item_maxu = ITEMVIEW_ITEMS_PER_ROW - 1;
        } else {
            item_maxu = item_u;
        }

        k = item_maxv;
        Tyre.consoleBitmaps[MSGCONTENT_INDEX] = new Bitmap(LRGMESSAGECONTENT_VIEWABLE_WIDTH, ((k + 1) * LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + (LRGMESSAGECONTENT_ROW_HEIGHT + 12));
        pen = Tyre.consoleBitmaps[MSGCONTENT_INDEX].createPen();
        pen.setColor(-16448251);
        pen.fillRect(0, 0, LRGMESSAGECONTENT_VIEWABLE_WIDTH, ((k + 1) * LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + (LRGMESSAGECONTENT_ROW_HEIGHT * 1) + 12);
        pen.setColor(Color.WHITE);
        pen.setFont(sf);

        //window title
        if (Tyre.player.items[0][0] < 0) {
            pen.drawText(2, (LRGMESSAGECONTENT_ROW_HEIGHT * 2), ResourceContainer.consoleStrings[Constants.CONSTR_INVENTORYEMPTY]);
            pen.drawText(2, (LRGMESSAGECONTENT_ROW_HEIGHT * 4), ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN]);
        } else {
            updateItemInventoryTarget();
            item_u = item_v = itemcur_x = itemcur_y = 0;

            for (i = 0; i < Constants.ITEM_INVENTORY_SIZE; i++) {
                //end the loop if you find a null item
                if (Tyre.player.items[i][0] < 0) {
                    i = Constants.ITEM_INVENTORY_SIZE;
                } else {
                    //item icon
                    j = Tyre.player.items[i][0];
                    pen.drawBitmap(4 + (item_u * ITEMVIEW_ITEM_ICON_WIDTH), (item_v * LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + 18, Tyre.bitmaps[ResourceContainer.items[j].imageIndex]);
                    if (Tyre.player.items[i][1] > 1) {
                        pen.setFont(ft);
                        pen.setColor(0x000000);
                        pen.drawText((item_u * ITEMVIEW_ITEM_ICON_WIDTH) + 8, (item_v * LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + 54, "" + Tyre.player.items[i][1]);
                        pen.setFont(sf);
                        pen.setColor(0xffffff);
                    }

                    item_u++;
                    if (item_u >= ITEMVIEW_ITEMS_PER_ROW) {
                        item_v++;
                        item_u = 0;
                    }
                }
            }
        }
    }

    public void formatShopBuyInventory() {
        int titlePosX = 0;
        String title = "Buy Item";
        String endingMessage = "";
        //display item contents
        lrgMessageOffsetY = 0;
        lrgMessageScrollOffsetY = 0;
        itemCursorPos = (2 * LRGMESSAGECONTENT_ROW_HEIGHT);

        k = 0;
        item_u = item_v = 0;
        itemcur_x = itemcur_y = 0;

        for (i = 0; i < shopItems.length; i++) {
            if (shopItems[i] != -1) {
                k++;
                item_u++;
                if (item_u >= ITEMVIEW_ITEMS_PER_ROW) {
                    item_v++;
                    item_u = 0;
                }
            } else {
                i = shopItems.length;
            }
        }
        numItems = k;
        //if u is 0 then last item pushed onto a new line, deduct from max_v
        if (item_u > 0) {
            item_maxv = item_v;
        } else {
            item_maxv = item_v - 1;
        }
        if (item_v > 0) {
            item_maxu = ITEMVIEW_ITEMS_PER_ROW - 1;
        } else {
            item_maxu = item_u;
        }

        //create temp image to store inventory contents
        Tyre.consoleBitmaps[MSGCONTENT_INDEX] = new Bitmap(LRGMESSAGECONTENT_VIEWABLE_WIDTH, ((item_maxv + 1) * LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + (LRGMESSAGECONTENT_ROW_HEIGHT + 12));
        pen = Tyre.consoleBitmaps[MSGCONTENT_INDEX].createPen();
        pen.setColor(-16448251);

        pen.fillRect(0, 0, LRGMESSAGECONTENT_VIEWABLE_WIDTH, ((item_maxv + 1) * LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + (LRGMESSAGECONTENT_ROW_HEIGHT * 1) + 12);
        pen.setColor(Color.WHITE);
        pen.setFont(sf);

        //window title
        updateItemInventoryTarget();
        k = 2;
        item_u = item_v = itemcur_x = itemcur_y = 0;

        for (i = 0; i < shopItems.length; i++, k++) {
            //end the loop if you find a null item
            if (shopItems[i] == -1) {
                i = shopItems.length;
            } else {
                //item icon
                j = shopItems[i];
                pen.drawBitmap(4 + (item_u * ITEMVIEW_ITEM_ICON_WIDTH), (item_v * LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + 18, Tyre.bitmaps[ResourceContainer.items[j].imageIndex]);
                pen.setFont(ft);
                pen.setTextOutlineColor(0xffffff);
                pen.setColor(Color.ORANGE);
                pen.drawText((item_u * ITEMVIEW_ITEM_ICON_WIDTH) + 8, (item_v * LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + 54, "" + ResourceContainer.items[j].cost);
                pen.setFont(sf);
                pen.setColor(0xffffff);
                item_u++;
                if (item_u >= ITEMVIEW_ITEMS_PER_ROW) {
                    item_v++;
                    item_u = 0;
                }
            }
        }
    }

    public void formatStoryMessage(String storyMessage) {
        lrgMessageOffsetY = 0;
        lrgMessageScrollOffsetY = 0;
        m = 60;

        //use 'k' as height of content
        k = m * LRGMESSAGECONTENT_ROW_HEIGHT;
        if (k < LRGMESSAGECONTENT_VIEWABLE_HEIGHT) {
            k = LRGMESSAGECONTENT_VIEWABLE_HEIGHT;
        }

        Bitmap tmpBmp = new Bitmap(LRGMESSAGECONTENT_VIEWABLE_WIDTH, k);
        pen = tmpBmp.createPen();
        pen.setColor(-16448251);
        pen.fillRect(0, 0, LRGMESSAGECONTENT_VIEWABLE_WIDTH, k);
        pen.setFont(sf);
        pen.setColor(Color.WHITE);

        int widthAdj = -5;
        int stryX = 2;
        
        //sj
        k = 1;
        while (storyMessage.length() > 0) {
            j = sf.getWidth(storyMessage);
            tmpStr = storyMessage;
            m = storyMessage.length();
            while (j > STORYMESSAGES_WIDTH + widthAdj) {
                m = tmpStr.lastIndexOf(' ', m - 1);
                if (m != -1) {
                    j = sf.getWidth(storyMessage, 0, m);
                } else {
                    j = sf.getWidth(storyMessage);
                    break;
                }
            }

            tmpStr = storyMessage.substring(0, m);
            tmpcount = tmpStr.indexOf("[b]");
            tmpInt2 = 0;
            if (tmpcount != -1) {
                pen.drawText(stryX, k * LRGMESSAGECONTENT_ROW_HEIGHT, tmpStr.substring(0, tmpcount).trim());
                k++;
                tmpInt2 = tmpcount + 3;
                //added
                m = tmpInt2;
                if (m == storyMessage.length()) {
                    storyMessage = "";
                } else {
                    storyMessage = storyMessage.substring(tmpInt2).trim();
                }
            } else {
                if (m == storyMessage.length()) {
                    storyMessage = "";
                } else {
                    storyMessage = storyMessage.substring(m + 1).trim();
                }
                pen.drawText(stryX, k * LRGMESSAGECONTENT_ROW_HEIGHT, tmpStr.trim());
                k++;
            }
            //check if j goes over limit, if so push into new conversation screen
        }
        //end sj

        pen.drawText(stryX, (k * LRGMESSAGECONTENT_ROW_HEIGHT), ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN]);

        //copy cropped temp bitmap 
        //use 'k' as height of content
        m = k * LRGMESSAGECONTENT_ROW_HEIGHT;
        if (m < LRGMESSAGECONTENT_VIEWABLE_HEIGHT) {
            m = LRGMESSAGECONTENT_VIEWABLE_HEIGHT;
        }
        
        //used for auto-scrolling
        o = m;
        Tyre.consoleBitmaps[MSGCONTENT_INDEX] = new Bitmap(LRGMESSAGECONTENT_VIEWABLE_WIDTH, m);
        Bitmap.copyBits(Tyre.consoleBitmaps[MSGCONTENT_INDEX], new Rect(0, 0, LRGMESSAGECONTENT_VIEWABLE_WIDTH, m), tmpBmp, new Rect(0, 0, LRGMESSAGECONTENT_VIEWABLE_WIDTH, m));
        tmpBmp = null;
        j = 0;
        m = 0;
    }

    public void formatDialogueMessage(String titleMessage, String storyMessage) {
        storyMessage = titleMessage + storyMessage;
        String orig = storyMessage;

        lrgMessageOffsetY = 0;
        lrgMessageScrollOffsetY = 0;
        j = sf.getWidth(storyMessage);

        if (j >= DIALOGMESSAGES_WIDTH) {
            //find out how many rows storyMessage will take up
            m = j / DIALOGMESSAGES_WIDTH;
            if (j % DIALOGMESSAGES_WIDTH != 0) {
                m++;
            }
        }

        //add 5 extra rows in case the splitting on ' ' increases row count
        if (m <= 4) {
            m = 9;
        } else {
            m += 5;
        }

        //TODO SJ
        Tyre.consoleBitmaps[MSGCONTENT_INDEX] = new Bitmap(CONVERSATIONMSG_VIEWABLE_WIDTH, CONVERSATIONMSG_VIEWABLE_HEIGHT);
        pen = Tyre.consoleBitmaps[MSGCONTENT_INDEX].createPen();
        pen.setColor(-16448251);
        pen.fillRect(0, 0, CONVERSATIONMSG_VIEWABLE_WIDTH, CONVERSATIONMSG_VIEWABLE_HEIGHT);

        pen.setColor(Color.WHITE);
        pen.setFont(sf);
        //sj
        k = 1;
        i = 0;

        while (storyMessage.length() > 0) {
            j = sf.getWidth(storyMessage);
            tmpStr = storyMessage;
            m = storyMessage.length();

            while (j > DIALOGMESSAGES_WIDTH) {
                m = tmpStr.lastIndexOf(' ', m - 1);
                if (m != -1) {
                    j = sf.getWidth(storyMessage, 0, m);
                } else {
                    j = sf.getWidth(storyMessage);
                    break;
                }
            }
            //include +1 to account for space that was used to divide the line
            if (m < storyMessage.length()) {
                i += (m + 1);
            }

            tmpStr = storyMessage.substring(0, m);
            if (m == storyMessage.length()) {
                storyMessage = "";
            } else {
                storyMessage = storyMessage.substring(m + 1);
            }

            pen.drawText(4, k * CONVERSATIONMSG_ROW_HEIGHT, tmpStr);
            k++;
            //check if j goes over limit, if so push into new conversation screen
            if (k > 3) {
                if (storyMessage.length() > 0) {
                    inConv = true;
                    inConvStayOnCurrent = true;
                    inConvPos = i - titleMessage.length();
                } else {
                    inConvPosTotal = inConvPos;
                    inConvPos = 0;
                }
                break;
            } else {
                //inConvPosTotal = inConvPos;
            }
        }

        if (k > 3) {
            inConvPosTotal += inConvPos;
        } else {
            inConvPosTotal = inConvPos;
        }
        //end sj

        if (inConv) {
            pen.drawText(120, (4 * CONVERSATIONMSG_ROW_HEIGHT) + 6, ResourceContainer.consoleStrings[Constants.CONSTR_CONTINUEMSG]);
            pen.drawText(4, (4 * CONVERSATIONMSG_ROW_HEIGHT) + 6, ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN]);
        } else {
            pen.drawText(4, (4 * CONVERSATIONMSG_ROW_HEIGHT) + 6, ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN]);
        }
        j = 0;
        m = 0;
    }

    public void formatPrologueMessage(String storyMessage) {
        lrgMessageOffsetY = 0;
        lrgMessageScrollOffsetY = 0;
        m = 60;

        //use 'k' as height of content
        k = m * LRGMESSAGECONTENT_ROW_HEIGHT;
        if (k < PROLOGUECONTENT_VIEWABLE_HEIGHT) {
            k = PROLOGUECONTENT_VIEWABLE_HEIGHT;
        }

        Font pFt;        
        if(PROLOGUE_FONT_BOLD == true) {
            pFt = bft;
        } else {
            pFt = sf;
        }
        
        Bitmap tmpBmp = new Bitmap(LRGMESSAGECONTENT_PROLOGUE_WIDTH, k);
        pen = tmpBmp.createPen();
        pen.setColor(-16448251);
        pen.fillRect(0, 0, LRGMESSAGECONTENT_PROLOGUE_WIDTH, k);
        //pen.setFont(bft);
        pen.setFont(pFt);
        pen.setColor(Color.WHITE);
        tmpStr = "";
        k = 3;
        
        int widthAdj = -5;
        int stryX = 2;        
        
        while (storyMessage.length() > 0) {
            //j = bft.getWidth(storyMessage);
            j = pFt.getWidth(storyMessage);
            tmpStr = storyMessage;
            m = storyMessage.length();
            while (j > PROLOGUEMESSAGES_WIDTH + widthAdj) {
                m = tmpStr.lastIndexOf(' ', m - 1);
                if (m != -1) {
                    j = pFt.getWidth(storyMessage, 0, m);
                } else {
                    j = pFt.getWidth(storyMessage);
                    break;
                }
            }

            //VGB 01/18/2022
            if(m == -1) {
                break;
            }
            
            tmpStr = storyMessage.substring(0, m);
            tmpcount = tmpStr.indexOf("[b]");
            tmpInt2 = 0;
            if (tmpcount != -1) {
                pen.drawText(stryX, k * LRGMESSAGECONTENT_ROW_HEIGHT, tmpStr.substring(0, tmpcount).trim());
                k++;
                tmpInt2 = tmpcount + 3;
                //added
                m = tmpInt2;
                if (m == storyMessage.length()) {
                    storyMessage = "";
                } else {
                    storyMessage = storyMessage.substring(tmpInt2).trim();
                }
            } else {
                if (m == storyMessage.length()) {
                    storyMessage = "";
                } else {
                    storyMessage = storyMessage.substring(m + 1).trim();
                }
                pen.drawText(stryX, k * LRGMESSAGECONTENT_ROW_HEIGHT, tmpStr.trim());
                k++;
            }
        }

        m = k * LRGMESSAGECONTENT_ROW_HEIGHT;
        if (m < PROLOGUECONTENT_VIEWABLE_HEIGHT) {
            m = PROLOGUECONTENT_VIEWABLE_HEIGHT;
        }

        //used for auto-scrolling
        o = m + 90;
        Tyre.consoleBitmaps[MSGCONTENT_INDEX] = new Bitmap(LRGMESSAGECONTENT_PROLOGUE_WIDTH, m);
        Bitmap.copyBits(Tyre.consoleBitmaps[MSGCONTENT_INDEX], new Rect(0, 0, LRGMESSAGECONTENT_PROLOGUE_WIDTH, m), tmpBmp, new Rect(0, 0, LRGMESSAGECONTENT_PROLOGUE_WIDTH, m));
        tmpBmp = null;
        j = 0;
        m = 0;
    }

    private void processGameSubState(Pen inPen) {
        switch (gameSubState) {
            case 0:
                //resort room objects, Y based
                if (resort) {
                    Tyre.cApp.sortRoomObjects();
                    resort = false;
                }
                break;
            case 1:
                //player action menu
                drawPlayerActionMenu(inPen);
                break;
            case 2:
                //map messages
                drawMapMessagesConsole(inPen);
                drawBattleModeMessages(inPen);
                break;
            case 3:
                //statistics
                drawPlayerStats(inPen);
                break;
            case 4:
                //item inventory
                drawInventory(inPen);
                break;
            case 5:
                //story message
                drawStoryMessage(inPen);
                break;
            case 6:
                //herb shop
                processHerbShopSubState(inPen);
                break;
            case 7:
                //inn
                break;
            /*
            case 8:
                //spell shop
                break;
            case 9:
                //spell inventory
                drawInventory(inPen);
                break;
            */
            case 10:
                //item receipt dialog
                drawItemReceive(inPen);
                break;
            case 11:
                //toll keeper
                processTollKeeperSubState(inPen);
                break;
        }
    }

    private void processHerbShopSubState(Pen inPen) {
        switch (herbShopSubState) {
            //buy/sell
            case 0:
                drawMapMessagesConsole(inPen);
                drawHerbShopBuySell(inPen);
                drawBattleModeMessages(inPen);
                break;
            //buy
            case 1:
                drawInventory(inPen);
                break;
            //sell
            case 2:
                drawInventory(inPen);
                break;
            case 3:
                //adjust quantity
                drawMapMessagesConsole(inPen);
                drawHerbShopQuantity(inPen);
                drawBattleModeMessages(inPen);
                break;
            case 4:
                //verify transaction
                drawMapMessagesConsole(inPen);
                drawBattleModeMessages(inPen);
                break;
        }
    }

    private void processTollKeeperSubState(Pen inPen) {
        switch (herbShopSubState) {
            //pay/don't pay
            case 0:
                //greeting message
                drawMapMessagesConsole(inPen);
                drawTollKeeperYesNo(inPen);
                drawBattleModeMessages(inPen);
                break;
            case 1:
                //after greeting
                drawMapMessagesConsole(inPen);
                drawBattleModeMessages(inPen);
                break;
        }
    }

    private void drawItemReceive(Pen inPen) {
        inPen.drawBitmap(RENDER_X + GUTTER_X + 60, RENDER_Y + 20, Tyre.consoleBitmaps[ITEMGET_INDEX]);

        //HARDCODE - Life Container
        if (itemfound_type[itemfound_i] == Constants.ITEM_LIFECONTAINER) {
            inPen.drawBitmap(RENDER_X + GUTTER_X + 96, RENDER_Y + 39, Tyre.consoleBitmaps[CONSOLEBITMAPS_BATTLEHUD_TILES_START_INDEX + 2]);
            formatPlayerHitsIcon(inPen, RENDER_X + GUTTER_X + 96, RENDER_Y + 38, (Tyre.player.hp - Tyre.player.damage));
        } else {
            inPen.drawBitmap(RENDER_X + GUTTER_X + 96, RENDER_Y + 39, Tyre.bitmaps[ResourceContainer.items[itemfound_type[itemfound_i]].imageIndex]);
        }
        inPen.setColor(0xffffff);
        inPen.setFont(sf);
        tx = (SCREEN_WIDTH - sf.getWidth(ResourceContainer.consoleStrings[Constants.CONSTR_TREASUREFOUND])) / 2;
        inPen.drawText(tx, RENDER_Y + 36, ResourceContainer.consoleStrings[Constants.CONSTR_TREASUREFOUND]);

        //continue msg
        //Move over 4 pixels
        inPen.drawText(RENDER_X + GUTTER_X + 106, RENDER_Y + 108, ResourceContainer.consoleStrings[Constants.CONSTR_CONTINUEMSG]);

        //HARDCODE
        tmpStr = ResourceContainer.strings[ResourceContainer.items[itemfound_type[itemfound_i]].stringIndex];
        tx = (SCREEN_WIDTH - sf.getWidth(tmpStr)) / 2;
        inPen.drawText(tx, RENDER_Y + 91, tmpStr);
        
        if (itemfound_qty[itemfound_i] > 1 || itemfound_type[itemfound_i] == Constants.ITEM_GOLD) {
            inPen.setFont(ft);
            inPen.setColor(0x000000);
            inPen.drawText(tx + 8, RENDER_Y + 75, "" + itemfound_qty[itemfound_i]);
        }
    }

    private void drawHerbShopQuantity(Pen inPen) {
        inPen.drawBitmap(RENDER_X + GUTTER_X + 5, RENDER_Y + GUTTER_Y + 25, Tyre.consoleBitmaps[HERBSHOPQUANTITY_INDEX]);
        inPen.setFont(sf);
        inPen.setColor(Color.WHITE);
        inPen.drawText(RENDER_X + GUTTER_X + 11, RENDER_Y + GUTTER_Y + 42, ResourceContainer.consoleStrings[Constants.CONSTR_AMOUNT] + currentQuantity);
        inPen.drawText(RENDER_X + GUTTER_X + 11, RENDER_Y + GUTTER_Y + 54, ResourceContainer.consoleStrings[Constants.CONSTR_VALUE] + cost * currentQuantity);
        inPen.drawText(RENDER_X + GUTTER_X + 11, RENDER_Y + GUTTER_Y + 66, ResourceContainer.consoleStrings[Constants.CONSTR_GOLD] + Tyre.player.goldCoins);
    }

    private void drawHerbShopBuySell(Pen inPen) {
        inPen.drawBitmap(RENDER_X + GUTTER_X + 5, RENDER_Y + GUTTER_Y + 35, Tyre.consoleBitmaps[HERBSHOPMENU_INDEX]);
        inPen.setFont(sf);
        inPen.setColor(Color.WHITE);
        inPen.drawText(RENDER_X + GUTTER_X + 30, RENDER_Y + GUTTER_Y + 52, ResourceContainer.consoleStrings[Constants.CONSTR_BUY]);
        inPen.drawText(RENDER_X + GUTTER_X + 30, RENDER_Y + GUTTER_Y + 64, ResourceContainer.consoleStrings[Constants.CONSTR_SELL]);
        inPen.drawBitmap(RENDER_X + GUTTER_X + 10, RENDER_Y + GUTTER_Y + cursorPosY, Tyre.consoleBitmaps[CURSOR_INDEX]);
    }

    private void drawTollKeeperYesNo(Pen inPen) {
        inPen.drawBitmap(RENDER_X + GUTTER_X + 5, RENDER_Y + GUTTER_Y + 35, Tyre.consoleBitmaps[HERBSHOPMENU_INDEX]);
        inPen.setFont(sf);
        inPen.setColor(Color.WHITE);
        inPen.drawText(RENDER_X + GUTTER_X + 30, RENDER_Y + GUTTER_Y + 52, ResourceContainer.consoleStrings[Constants.CONSTR_YES]);
        inPen.drawText(RENDER_X + GUTTER_X + 30, RENDER_Y + GUTTER_Y + 64, ResourceContainer.consoleStrings[Constants.CONSTR_NO]);
        inPen.drawBitmap(RENDER_X + GUTTER_X + 10, RENDER_Y + GUTTER_Y + cursorPosY, Tyre.consoleBitmaps[CURSOR_INDEX]);
    }

    private void drawGamePauseScreen(Pen inPen) {
        renderLayerOneFlag = true;
        renderLayerTwoFlag = true;
        drawLayerOneObjects(inPen);
        drawLayerTwoObjects(inPen);
        inPen.setColor(255, 128, 0);
        inPen.drawRect(RENDER_X, RENDER_Y, RENDER_X + ROOM_WIDTH, RENDER_Y + ROOM_HEIGHT);
    }

    private void drawBattleModeBackground(Pen inPen) {
        if (attackSuccess && (count - successTime) <= 13) {
            inPen.setColor(Color.RED);
            attackSuccess = false;
        } else {
            inPen.setColor(-16448251);
        }
        
        //overuse renderLayerOneFlag to redraw world view under battle
        if (RENDER_ADJACENT_ROOMS == 1 && renderLayerOneFlag) {
            drawGamePauseScreen(inPen);
            renderLayerOneFlag = false;
        }
        
        //inPen.fillRect(0,0,240,136);
        inPen.fillRect(RENDER_X, RENDER_Y, RENDER_X + ROOM_WIDTH, RENDER_Y + 94);
        inPen.setColor(0);
        inPen.fillRect(RENDER_X, RENDER_Y + 94, RENDER_X + ROOM_WIDTH, RENDER_Y + ROOM_HEIGHT);
        if (RENDER_ADJACENT_ROOMS == 1) {
            Tyre.cApp.drawConsoleWindow(inPen, ROOM_WIDTH, ROOM_HEIGHT, RENDER_X, RENDER_Y);
        }
    }

    private void drawEnemy(Pen inPen, int enemyCount, int pos) {
        if (enemies == null) {
            return;
        }
        switch (enemyCount) {
            case 1:
                if (pos == lastActiveEnemy) {
                    //move to center
                    tmpInt = 2 + (pos * 80);
                    tmpInt2 = 15;
                } else {
                    tmpInt = 2 + (pos * 80);
                    tmpInt2 = 0;
                }

                if (enemies[pos].isDead) {
                    if (enemies[pos].shakeCount < 13) {
                        i = rand.nextInt(5);
                        k = rand.nextInt(5);
                        //TODO DRAWEN
                        inPen.drawBitmap(RENDER_X + GUTTER_X + 77 + k, RENDER_Y + GUTTER_Y + i + tmpInt2, Tyre.bitmaps[enemies[pos].imageIndex]);
                        enemies[pos].shakeCount++;
                    } else {
                        enemies[pos] = null;
                    }
                } else {
                    //TODO DRAWEN
                    inPen.drawBitmap(RENDER_X + GUTTER_X + 82, RENDER_Y + GUTTER_Y + 2 + tmpInt2, Tyre.bitmaps[enemies[pos].imageIndex]);
                    //enemy cooldown gauge
                    //TODO ENBAR
                    drawEnemyCooldownGauge(inPen, RENDER_X + GUTTER_X + 83, RENDER_Y + GUTTER_Y + 78 + tmpInt2, 74, 4, ((60 * enemies[pos].cooldown) / ENEMYWAIT_TIME[pos]));

                    if (battleModeState == 2) {
                        inPen.setFont(bof);
                        inPen.setColor(Color.RED);
                        inPen.drawText(RENDER_X + GUTTER_X + 86, RENDER_Y + GUTTER_Y + 72 + tmpInt2, ResourceContainer.strings[enemies[pos].stringIndex]);
                        inPen.drawText(RENDER_X + GUTTER_X + 86, RENDER_Y + GUTTER_Y + 14 + tmpInt2, "" + enemies[pos].lv);
                    }
                }
                break;
            default:
                if (pos == lastActiveEnemy) {
                    //move to center
                    tmpInt = 2 + (pos * 80);
                    i = 15;
                } else {
                    tmpInt = 2 + (pos * 80);
                    i = 0;
                }

                if (enemies[pos] == null) {
                    break;
                } else if (enemies[pos].isDead) {
                    if (enemies[pos].shakeCount < DISPLAYATTACK_TIME) {
                        i += rand.nextInt(5);
                        k = rand.nextInt(5);
                        //TODO DRAWEN
                        inPen.drawBitmap(RENDER_X + GUTTER_X + tmpInt - 3 + k, RENDER_Y + GUTTER_Y + i, Tyre.bitmaps[enemies[pos].imageIndex]);
                        enemies[pos].shakeCount++;
                    } else {
                        enemies[pos] = null;
                    }
                } else {
                    //TODO DRAWEN
                    inPen.drawBitmap(RENDER_X + GUTTER_X + tmpInt, RENDER_Y + GUTTER_Y + i + 2, Tyre.bitmaps[enemies[pos].imageIndex]);

                    //enemy cooldown gauge
                    //TODO ENBAR
                    drawEnemyCooldownGauge(inPen, RENDER_X + GUTTER_X + tmpInt + 1, RENDER_Y + GUTTER_Y + 78 + i, 74, 4, ((60 * enemies[pos].cooldown) / ENEMYWAIT_TIME[pos]));

                    //always show enemy name and target on front enemy if player cannot "select any"
                    if ((lastActiveEnemy == pos) && (pos == selectedEnemyPos) && selectEnemy) {
                        //enemy is in the front and selected
                        drawTargetOnEnemy(tmpInt, 17, pos, inPen, true);
                    } else if (allTargetsSelected) {
                        if (pos == lastActiveEnemy) {
                            drawTargetOnEnemy(tmpInt, 17, pos, inPen, true);
                        } else {
                            drawTargetOnEnemy(tmpInt, 2, pos, inPen, true);
                        }

                    } else if (selectedEnemyPos == pos && selectEnemy) {
                        drawTargetOnEnemy(tmpInt, 2, pos, inPen, true);
                    } else if (selectedEnemyPos == pos && selectEnemy && (lastActiveEnemy == -1)) {
                        drawTargetOnEnemy(tmpInt, 2, pos, inPen, true);
                    } else if (activeEnemyCount() == 1 && battleModeState == 2) {
                        if (pos == lastActiveEnemy) {
                            drawTargetOnEnemy(tmpInt, 17, pos, inPen, false);
                        } else {
                            drawTargetOnEnemy(tmpInt, 2, pos, inPen, false);
                        }
                    }
                }
                break;
        }
    }

    private void drawEnemies(Pen inPen) {
        if (enemies.length > 0) {
            for (j = 0; j < enemies.length; j++) {
                if (j == lastActiveEnemy) {
                    continue;
                }

                if (enemies[j] != null) {
                    drawEnemy(inPen, enemies.length, j);
                    if (enemies[j] == null) {
                        continue;
                    }
                    if (enemies[j].isHit && (count - enemies[j].hitTime) <= DISPLAYATTACK_TIME) {
                        if (enemies.length == 1) {
                            //TODO SLASH
                            inPen.drawBitmap(RENDER_X + GUTTER_X + 82, RENDER_Y + GUTTER_Y + 2, Tyre.consoleBitmaps[playerAttackType]);
                        } else {
                            //TODO SLASH
                            inPen.drawBitmap(RENDER_X + GUTTER_X + 2 + (j * 80), RENDER_Y + GUTTER_Y + 2, Tyre.consoleBitmaps[playerAttackType]);
                        }
                    } else {
                        enemies[j].isHit = false;
                    }
                }
            }
            if (lastActiveEnemy != -1) {
                if (enemies == null) {
                    return;
                }
                if (enemies.length < (lastActiveEnemy + 1)) {
                    return;
                }
                if (enemies[lastActiveEnemy] == null) {
                    return;
                }

                drawEnemy(inPen, enemies.length, lastActiveEnemy);
                //in case
                if (enemies[lastActiveEnemy] == null) {
                    return;
                }
                if (enemies[lastActiveEnemy].isHit && (count - enemies[lastActiveEnemy].hitTime) <= DISPLAYATTACK_TIME) {
                    if (enemies.length == 1) {
                        //TODO SLASH
                        inPen.drawBitmap(RENDER_X + GUTTER_X + 82, RENDER_Y + GUTTER_Y + 15 + 2, Tyre.consoleBitmaps[playerAttackType]);
                    } else {
                        //TODO SLASH
                        inPen.drawBitmap(RENDER_X + GUTTER_X + 2 + (lastActiveEnemy * 80), RENDER_Y + GUTTER_Y + 15 + 2, Tyre.consoleBitmaps[playerAttackType]);
                    }
                } else {
                    enemies[lastActiveEnemy].isHit = false;
                }
            }
        }
    }

    private void drawTargetOnEnemy(int x, int y, int pos, Pen inPen, boolean targeted) {
        //HACK - test slowdown
        /*
        if (false) {
            return;
        }
        */
        
        if (targeted) {
            inPen.drawBitmap(RENDER_X + GUTTER_X + x, RENDER_Y + GUTTER_Y + y, Tyre.consoleBitmaps[ENEMYTARGET_INDEX]);
        }
        inPen.setFont(bof);
        inPen.setColor(Color.RED);
        inPen.drawText(RENDER_X + GUTTER_X + x + 4, RENDER_Y + GUTTER_Y + y + 70, ResourceContainer.strings[enemies[pos].stringIndex]);
        inPen.drawText(RENDER_X + GUTTER_X + x + 4, RENDER_Y + GUTTER_Y + y + 12, "" + enemies[pos].lv);
    }

    private void drawBattleModeConsole(Pen inPen) {
        inPen.drawBitmap(RENDER_X + GUTTER_X + 0, RENDER_Y + GUTTER_Y + 91, Tyre.consoleBitmaps[BATTLEHUD_INDEX]);
        //format console hits
        formatPlayerHitsIcon(inPen, RENDER_X + GUTTER_X + (2 * 48), RENDER_Y + GUTTER_Y + 92, (Tyre.player.hp - Tyre.player.damage));
        //format console hits
        formatPlayerStatusIcon(inPen, RENDER_X + GUTTER_X + (1 * 48), RENDER_Y + GUTTER_Y + 92);
        //TODO PLAYERBAR
        drawPlayerCooldownGauge(inPen, RENDER_X + GUTTER_X + 70, RENDER_Y + GUTTER_Y + 91, 100, 5, ((Tyre.player.cooldown * 90) / Tyre.player.PLAYERWAIT_TIME));
        if (battleModeState == 2 && !(isEntranced || isStunned)) {
            inPen.drawBitmap(RENDER_X + GUTTER_X + (seli * 48), RENDER_Y + GUTTER_Y + 93, Tyre.consoleBitmaps[HUDSELECTOR_INDEX]);
        }
    }

    private void formatPlayerStatusIcon(Pen inPen, int x, int y) {
        inPen.setColor(0x000000);
        //poisoned
        if (!isPoisoned) {
            inPen.fillRect(x + 2, y + 4, x + 17, y + 23);
        }
        //dazed
        if (!isStunned) {
            inPen.fillRect(x + 17, y + 4, x + 31, y + 24);
        }
        //asleep
        if (!isEntranced) {
            inPen.fillRect(x + 31, y + 4, x + 45, y + 23);
        }
        //anti-poison
        if (!Tyre.player.antipoison) {
            inPen.fillRect(x + 2, y + 24, x + 17, y + 38);
        }
        //anti-dazed
        if (!Tyre.player.antistun) {
            inPen.fillRect(x + 17, y + 24, x + 31, y + 38);
        }
        //anti-asleep
        if (!Tyre.player.antisleep) {
            inPen.fillRect(x + 31, y + 24, x + 45, y + 38);
        }
        //container outlines
        inPen.setColor(0x999999);
        inPen.drawLine(x + 2, y + 22, x + 45, y + 22);
        inPen.drawLine(x + 16, y + 4, x + 16, y + 39);
        inPen.drawLine(x + 30, y + 4, x + 30, y + 39);
    }

    private void formatPlayerHitsIcon(Pen inPen, int x, int y, int hits) {
        //FOR NOW ASSUME 36 TOTAL HP (9 blocks x 4 HP per block)
        //1 QUARTER BLOCK = 1 HP
        inPen.setColor(0x000000);
        i = hits / HPICON_HP_PER_CONTAINER;
        j = hits % HPICON_HP_PER_CONTAINER;
        tmpInt = Tyre.player.hp / HPICON_HP_PER_CONTAINER;

        //i is now the number of containers to blackout
        for (v = 0; v < HPICON_NUM_ROWS; v++) {
            for (u = 0; u < HPICON_NUM_PER_ROW; u++) {
                k = (v * 3) + u;
                if ((j > 0) && (k == i)) {
                    //always used or it would be a full block
                    inPen.setColor(0x000000);
                    if (j < 4) {
                        tx = x + HPICON_XOFF[k] + 6;
                        ty = y + HPICON_YOFF[k] + 4;
                        inPen.fillRect(tx, ty, tx + 6, ty + 4);
                    }
                    if (j < 3) {
                        tx = x + HPICON_XOFF[k];
                        ty = y + HPICON_YOFF[k] + 4;
                        inPen.fillRect(tx, ty, tx + 6, ty + 4);
                    }
                    if (j < 2) {
                        tx = x + HPICON_XOFF[k] + 6;
                        ty = y + HPICON_YOFF[k];
                        inPen.fillRect(tx, ty, tx + 6, ty + 4);
                    }
                    //unused since partial implies j >= 1
                    if (j < 1) {
                        tx = x + HPICON_XOFF[k];
                        ty = y + HPICON_YOFF[k];
                        inPen.fillRect(tx, ty, tx + 6, ty + 4);
                    }
                } else if (i <= k) {
                    tx = x + HPICON_XOFF[k];
                    ty = y + HPICON_YOFF[k];
                    inPen.setColor(0x000000);
                    inPen.fillRect(tx, ty, tx + 12, ty + 8);
                    if (k < tmpInt) {
                        inPen.setColor(0x999999);
                        inPen.drawRect(tx, ty, tx + 12, ty + 8);
                    }
                }
            }
        }
    }

    private void drawPlayerCooldownGauge(Pen inPen, int x, int y, int w, int h, int cd) {
        inPen.setColor(Color.BLACK);
        inPen.fillRoundRect(x, y, x + (w), y + h, 2, 2);
        if (cd <= SUPERCHARGE_TIME && cd > 0) {
            inPen.setColor(Color.PURPLE);
            inPen.fillRoundRect(x, y, x + (w - cd), y + h, 2, 2);
        } else {
            if (superCharge) {
                inPen.setColor(Color.PURPLE);
            } else {
                inPen.setColor(Color.BLUE);
            }
            inPen.fillRoundRect(x, y, x + (w - cd), y + h, 2, 2);
        }
        inPen.setColor(Color.WHITE);
        inPen.drawRoundRect(x - 1, y, x + (w), y + h + 1, 2, 2);
        inPen.setColor(0x1824B5);
        inPen.drawRoundRect(x - 2, y - 1, x + (w) + 1, y + h + 2, 2, 2);
    }

    private void drawEnemyCooldownGauge(Pen inPen, int x, int y, int w, int h, int cd) {
        inPen.setColor(Color.BLACK);
        inPen.fillRect(x, y, x + (w), y + h);
        inPen.setColor(Color.RED);
        inPen.fillRect(x, y, x + (w - cd), y + h);
        inPen.setColor(Color.WHITE);
        inPen.drawRect(x - 1, y, x + (w), y + h + 1);
    }

    private void drawFloatingHearts(Pen inPen) {
        inPen.setFont(ft);
        for (i = 0; i < MAX_FLOATINGHEARTS; i++) {
            if (fh_i[i] > 0) {
                inPen.setColor(fh_c[i]);
                inPen.drawText(RENDER_X + GUTTER_X + fh_x[i], RENDER_Y + GUTTER_Y + fh_y[i], "" + fh_d[i]);
                fh_i[i]--;
                if (fh_i[i] <= 0) {
                    fh_i[i] = -1;
                } else {
                    j = (FLOATINGHEARTS_FLOATTIME - fh_i[i] - 1);
                    switch (fh_t[i]) {
                        case 0:
                            if (j < fh_ymap.length) {
                                fh_x[i] += fh_xmap[j];
                                fh_y[i] += fh_ymap[j];
                            }
                            break;
                        case 1:
                            if (j < fh_ymap.length) {
                                fh_x[i] += fh2_xmap[j];
                                fh_y[i] += fh2_ymap[j];
                            } else {
                                k = j % fh_ymap.length;
                                fh_x[i] += fh2_xmap[k];
                                fh_y[i] += fh2_ymap[k];
                            }
                            break;
                        case 2:
                            if (j < fh3_ymap.length) {
                                fh_x[i] += fh3_xmap[j];
                                fh_y[i] += fh3_ymap[j];
                            }
                            break;
                    }
                }
            }
        }
    }

    private void drawStatusMsgs(Pen inPen) {
        inPen.setFont(ft);
        for (i = 0; i < MAX_STATUSMSGS; i++) {
            if (smsg_i[i] > 0) {
                inPen.setColor(smsg_c[i]);
                inPen.drawText(RENDER_X + GUTTER_X + smsg_x[i], RENDER_Y + GUTTER_Y + smsg_y[i], "" + smsg[i]);
                smsg_i[i]--;
                if (smsg_i[i] <= 0) {
                    smsg_i[i] = -1;
                }
            }
        }
    }

    private void drawBattleOpeningMessage(Pen inPen) {
        seli = 2;
        //the battle has begun
        //the dark room on ludlo and houston
        if ((count - timestamp) >= 30) {
            //start battle immediately
            j = rand.nextInt(11);
            if (j % 2 == 0) {
                newStatusMsg(89, 97, 0xFF0000, ResourceContainer.consoleStrings[Constants.CONSTR_AMBUSHED], 30);
                //start battle
                changeState(3, 2);
                //player is ambushed
                Tyre.player.cooldown = Tyre.player.PLAYERWAIT_TIME;
                for (i = 0; i < activeEnemyCount(); i++) {
                    if (enemies[i] != null) {
                        int t = (int) (enemies[i].speed * Tyre.speedMultiplier);
                        enemies[i].cooldown = enemies[i].speed;
                        ENEMYWAIT_TIME[i] = enemies[i].speed;
                    }
                }

                for (i = 0; i < enemies.length; i++) {
                    if (enemies[i] != null) {
                    
                    } else {
                        
                    }
                }

                //TODOCOOL - set cooldown instead of enemy turn
                timestamp = count;
                whichEnemy = 0;
            } else {
                //player is ready to strike
                Tyre.player.cooldown = 0;
                for (i = 0; i < activeEnemyCount(); i++) {
                    if (enemies[i] != null) {
                        int t = (int) (enemies[i].speed * Tyre.speedMultiplier);
                        enemies[i].cooldown = enemies[i].speed;
                        ENEMYWAIT_TIME[i] = enemies[i].speed;
                    }
                }

                for (i = 0; i < enemies.length; i++) {
                    if (enemies[i] != null) {
                        
                    } else {
                    
                    }
                }
                //TODOCOOL - set cooldown instead of enemy turn
                //start battle
                changeState(3, 2);
            }
        }
    }

    public void prepBattle(int enm) {
        resetMessages();
        changeState(3, 0);
        //set prevGameState to also be 3 to prevent bug that exited battle if done is hit here
        prevGameState = 3;

        //CODE FROM changeState
        TyreAudio.startMusic(battleSong);
        timestamp = count;
        selectEnemy = false;
        cursorPosY = 83;
        selectEnemyPosX = 2;
        selectedEnemyPos = 0;
        //END OF CODE FROM changeState

        lastActiveEnemy = -1;
        clearFloatingHearts();
        clearStatusMsgs();
        superCharge = false;

        //was 30
        newStatusMsg(69, 97, 0xFF0000, ResourceContainer.consoleStrings[Constants.CONSTR_PREPARETOFIGHT], 25);
        //TODO - set enemy recharge meters based on player suprised, enemy surpised, normal encounter
    }

    private void drawBattleModeMessages(Pen inPen) {
        //draw battle messages
        for (i = 0; i < messages.length; i++) {
            if (messages[i].val != null) {
                inPen.setFont(sf);
                inPen.setColor(messages[i].color);
                inPen.drawText(RENDER_X + 6, RENDER_Y + GUTTER_Y + 100 + (i * MESSAGE_FONT_HEIGHT), messages[i] + "");
            }
        }
    }

    private int findNextActiveEnemy(int currentEnemy) {
        if (enemies.length == 3) {
            switch (currentEnemy) {
                case 0:
                    if (enemies[1] != null && !enemies[1].isDead) {
                        return 1;
                    } else if (enemies[2] != null && !enemies[2].isDead) {
                        return 2;
                    }
                    break;
                case 1:
                    if (enemies[2] != null && !enemies[2].isDead) {
                        return 2;
                    } else if (enemies[0] != null && !enemies[0].isDead) {
                        return 0;
                    }
                    break;
                case 2:
                    if (enemies[0] != null && !enemies[0].isDead) {
                        return 0;
                    } else if (enemies[1] != null && !enemies[1].isDead) {
                        return 1;
                    }
                    break;
            }
        } else if (enemies.length == 2) {
            switch (currentEnemy) {
                case 0:
                    if (enemies[1] != null && !enemies[1].isDead) {
                        return 1;
                    }
                    break;
                case 1:
                    if (enemies[0] != null && !enemies[0].isDead) {
                        return 0;
                    }
                    break;
            }
        }
        return 0;
    }

    private void processBattleModeState(Pen inPen) {
        if (lastActiveEnemyTick > 0) {
            lastActiveEnemyTick--;
            if (lastActiveEnemyTick == 0) {
                lastActiveEnemy = -1;
            }
        }
        
        //attacked by an enemy
        drawBattleModeBackground(inPen);
        inPen.setFont(sf);
        inPen.setColor(Color.WHITE);
        if (battleModeState != 5 && battleModeState != 12) {
            drawBattleModeConsole(inPen);
        }
        drawEnemies(inPen);

        switch (battleModeState) {
            case 0:
                drawBattleOpeningMessage(inPen);
                break;
            case 1:
                //enemy
                processPlayerCooldown();
                processEnemyCooldown();
                break;
            case 2:
                //players turn
                processPlayerCooldown();
                processEnemyCooldown();
                break;
            case 3:
                //single enemy attack
                processPlayerCooldown();
                processEnemyCooldown();
                break;
            /*
            case 4:
                //spell inventory
                break;
            */
            case 5:
                //ending
                if (timestamp >= 15) {
                    //replace back to normal state with item received
                    //end of battle, show items received dialog
                    if (!isBoss) {
                        superCharge = false;
                        Tyre.player.moveBattleStats();
                        TyreAudio.startMusic(TyreAudio.prevIndex);
                        if (playerAttackType == -1) {
                            changeState(0);
                        } else {
                            changeState(0, 10);
                        }
                    } else {
                        superCharge = false;
                        Tyre.player.moveBattleStats();

                        //check for final battle here
                        if (currentBossBattleId == Constants.BATTLE_FINAL) {
                            //go to end game sequence
                            //clear game hash and restart 
                            int tmp_weap = Tyre.player.weapon;
                            int tmp_armor = Tyre.player.armor;
                            int tmp_ap = Tyre.player.ap;
                            int tmp_dp = Tyre.player.dp;
                            boolean tmp_antisleeporb = Tyre.player.antisleeporb;
                            boolean tmp_antistun = Tyre.player.antistun;
                            boolean tmp_antipoison = Tyre.player.antipoison;
                            
                            //////////////////////
                            ResourceContainer.flags = new boolean[ResourceContainer.flags.length];
                            for (i = 0; i < ResourceContainer.flags.length; i++) {
                                ResourceContainer.flags[i] = false;
                            }

                            Tyre.player = new character();
                            Tyre.player.loadCharData(cursorIndX);
                            Tyre.player.equip_weapon(tmp_ap, tmp_weap);
                            Tyre.player.equip_armor(tmp_dp, tmp_armor);
                            Tyre.player.antisleep = tmp_antisleeporb;
                            Tyre.player.antisleeporb = tmp_antisleeporb;
                            Tyre.player.antistun = tmp_antistun;
                            Tyre.player.antipoison = tmp_antipoison;
                            Tyre.player.setRechargeSpeed();
                            isPoisoned = isEntranced = isStunned = false;
                            Tyre.currentRoomIdx = ResourceContainer.players[cursorIndX].startingRoom;

                            if (Tyre.gameStart) {
                                Tyre.gameStart = false;
                            }
                            if (Tyre.firstRun) {
                                Tyre.firstRun = false;
                            }
                            returnToMenu = false;
                            //////////////////////
                            changeState(gameState, 12);
                        } else {
                            TyreAudio.startMusic(TyreAudio.prevIndex);
                            if (battleSearchIndex != -1) {
                                itemfound_i = 0;
                                itemfound_t = 0;
                                changeState(0, 10);
                                handleSearchObject(battleSearchIndex);
                            } else {
                                //TODO - add standard battle spoils here
                                changeState(0, 10);
                            }
                        }
                    }
                    //todo override
                    prevGameState = 0;
                } else {
                    //TODO USE NEW DIALOG FOR ITEMGET
                    //tests
                    drawBattleModeConsole(inPen);
                    timestamp++;
                }
                break;
            case 6:
                //display experience message
                resetMessages();
                //TODO - move to function
                for (i = 0; i < 3; i++) {
                    itemfound_qty[i] = 0;
                    itemfound_type[i] = -1;
                }
                itemfound_t = 0;
                itemfound_i = 0;

                //calc xp
                k = 0;
                k -= Tyre.player.battleMisses;
                k += Tyre.player.battleDodges;
                k += (Tyre.player.battleKills * 2);
                k += Tyre.player.battleHitsGiven;
                k += Tyre.player.battleHitsTaken;
                k += (Tyre.player.battleSuperAttackGiven * 2);
                k -= Tyre.player.battleSuperAttackTaken;
                k += Tyre.player.battleItems;
                if (k <= 0) {
                    k = 1;
                }
                Tyre.player.ex += k;

                //attackType gets set to -1 when player runs
                k = 0;
                if (gold > 0 && playerAttackType != -1) {
                    itemfound_qty[k] = gold;
                    //TODO - make gold item index const
                    itemfound_type[k] = Constants.ITEM_GOLD;
                    itemfound_t++;
                    k++;
                    Tyre.player.goldCoins += gold;

                    if (rand.nextInt(100) < 15) {
                        //found heal potion
                        itemfound_type[k] = dropItems[rand.nextInt(dropItems.length)];
                        itemfound_qty[k] = 1;
                        itemfound_t++;
                        storeItem(itemfound_type[k], itemfound_qty[k]);
                        k++;
                    }
                    if (rand.nextInt(100) < 3) {
                        //found heal potion
                        itemfound_type[k] = dropItems[rand.nextInt(dropItems.length)];
                        itemfound_qty[k] = 1;
                        itemfound_t++;
                        storeItem(itemfound_type[k], itemfound_qty[k]);
                        k++;
                    }
                    //TODO - replace with item gotten
                }
                
                if (isBoss) {
                    if (battleFlagIndex != -1) {
                        Logger.wr("FLAGS: BOSS: Index:" + battleFlagIndex + " = " + (!ResourceContainer.flags[battleFlagIndex]) + "< battleFlagIndex = (!ResourceContainer.flags[battleFlagIndex]) >");
                        ResourceContainer.flags[battleFlagIndex] = (!ResourceContainer.flags[battleFlagIndex]);
                    }
                    if (battleFlagIndex1 != -1) {
                        Logger.wr("FLAGS: BOSS: Index:" + battleFlagIndex1 + " = " + (!ResourceContainer.flags[battleFlagIndex1]) + "< battleFlagIndex1 = (!ResourceContainer.flags[battleFlagIndex1]) >");
                        ResourceContainer.flags[battleFlagIndex1] = (!ResourceContainer.flags[battleFlagIndex1]);
                    }
                    if (battleFlagIndex2 != -1) {
                        Logger.wr("FLAGS: BOSS: Index:" + battleFlagIndex2 + " = " + (!ResourceContainer.flags[battleFlagIndex2]) + "< battleFlagIndex2 = (!ResourceContainer.flags[battleFlagIndex2]) >");
                        ResourceContainer.flags[battleFlagIndex2] = (!ResourceContainer.flags[battleFlagIndex2]);
                    }
                }
                changeState(3, 5);
                break;
            case 7:
                drawInventory(inPen);
                processPlayerCooldown();
                processEnemyCooldown();
                break;
            case 8:
                //scroll in control panel
                break;
            case 9:
                //scroll out control panel
                break;
            case 10:
                //player has died
                if (timestamp <= DEATHSCROLL_TIME) {
                    inPen.setColor(-5170919);
                    inPen.fillRect(0, 0, SCREEN_WIDTH, timestamp * 3);
                    timestamp++;
                } else {
                    inPen.setColor(-5170919);
                    inPen.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                    changeState(3, 11);
                }
                break;
            case 11:
                //ask player if he wishes to continue
                inPen.setColor(-5170919);
                inPen.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                inPen.setColor(Color.BLACK);
                inPen.setFont(ft);
                inPen.drawText(RENDER_X + GUTTER_X + 37, RENDER_Y + GUTTER_Y + 58, ResourceContainer.consoleStrings[Constants.CONSTR_YOUDIED]);
                inPen.drawText(RENDER_X + GUTTER_X + 32, RENDER_Y + GUTTER_Y + 70, ResourceContainer.consoleStrings[Constants.CONSTR_RETURNTOQUEST]);
                break;
            //game won transition screen
            case 12:
                if (tmpcount <= DEATHSCROLL_TIME) {
                    drawBattleModeConsole(inPen);
                    inPen.setColor(Color.BLACK);
                    inPen.fillRect(0, 0, SCREEN_WIDTH, tmpcount * 3);
                    tmpcount++;
                    timestamp++;
                } else {
                    formatPrologueMessage(ResourceContainer.strings[EPILOGUE_STRING_INDEX]);
                    changeState(10, 4);
                }
                break;
        }
        drawFloatingHearts(inPen);
        drawStatusMsgs(inPen);
    }

    private void processEnemyCooldown() {
        for (whichEnemy = 0; whichEnemy < enemies.length; whichEnemy++) {
            if (enemyIsActive(whichEnemy)) {
                if (enemies[whichEnemy].cooldown > 0) {
                    enemies[whichEnemy].cooldown--;
                    if (enemies[whichEnemy].cooldown == 0) {
                        enemies[whichEnemy].cooldown = ENEMYWAIT_TIME[whichEnemy];
                        enemyAttack(whichEnemy);
                    }
                }
            }
        }
    }

    private void processPlayerCooldown() {
        if (Tyre.player.cooldown > 0) {
            Tyre.player.cooldown--;
            if (Tyre.player.cooldown == 0) {
                //do nothing
            }
        }
    }

    public void paint(Pen inPen) {
        if (Tyre.suspended) {
            Logger.wr("SUSPEND: paint blocked");
            return;
        }
        
        //l1 = System.currentTimeMillis();
        switch (gameState) {
            case 0:
                //run
                resort = false;
                //draw this if framing
                drawLayerOneObjects(inPen);
                drawLayerTwoObjects(inPen);
                if (RENDER_ADJACENT_ROOMS == 1) {
                    inPen.setColor(255, 128, 0);
                    inPen.drawRect(RENDER_X, RENDER_Y, RENDER_X + ROOM_WIDTH, RENDER_Y + ROOM_HEIGHT);
                }
                processGameSubState(inPen);
                break;                
            case 1:
                //paused
                drawGamePauseScreen(inPen);
                break;
            case 2:
                //transition through link object
                transitionRoom();
                break;
            case 3:
                processBattleModeState(inPen);
                break;
            case 4:
                //transition top
                prepEnterRoom(0);
                transitionRoom(0);
                transTop = 0;
                break;
            case 5:
                //transition right
                prepEnterRoom(1);
                transitionRoom(1);
                transRight = 0;
                break;
            case 6:
                //transition bottom
                prepEnterRoom(2);
                transitionRoom(2);
                transBottom = 0;
                break;
            case 7:
                //transition left
                prepEnterRoom(3);
                transitionRoom(3);
                transLeft = 0;
                break;
            case 8:
                //loading screen
                inPen.setColor(Color.BLACK);
                inPen.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                tx = (SCREEN_WIDTH - Tyre.logoImage.getWidth()) / 2;
                ty = (SCREEN_HEIGHT - Tyre.logoImage.getHeight()) / 2;
                inPen.drawBitmap(tx, ty, Tyre.logoImage);
                inPen.setFont(ft);
                inPen.setColor(Color.WHITE);
                inPen.fillRect(RENDER_X + GUTTER_X + 72, RENDER_Y + GUTTER_Y + 114, RENDER_X + GUTTER_X + 174, RENDER_Y + GUTTER_Y + 126);
                inPen.setColor(82, 92, 184);

                if (null != Tyre.datLoader && null != loadDat.chapter) {
                    inPen.fillRect(RENDER_X + GUTTER_X + 73, RENDER_Y + GUTTER_Y + 115, RENDER_X + GUTTER_X + 73 + (((loadDat.pos * 100) / loadDat.chapter.length)), RENDER_Y + GUTTER_Y + 125);
                } else {
                    inPen.fillRect(RENDER_X + GUTTER_X + 73, RENDER_Y + GUTTER_Y + 115, RENDER_X + GUTTER_X + 173, RENDER_Y + GUTTER_Y + 125);
                }
                
                inPen.setColor(Color.WHITE);
                inPen.drawText(RENDER_X + GUTTER_X + 100, RENDER_Y + GUTTER_Y + 100, "Loading...");
                break;
            case 9:
                //wait while loading room
                inPen.setColor(Color.BLACK);
                inPen.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                break;
            case 10:
                //main menu, continue
                processMainMenuSubState(inPen);
                break;
            case 11:
                //MMG Splash
                inPen.setColor(0x000000);
                inPen.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                inPen.drawBitmap(RENDER_X + GUTTER_X + 70, RENDER_Y + GUTTER_Y + 43, Tyre.logoImage);
                tmpInt++;
                if (tmpInt > 75) {
                    tmpInt = 0;
                    changeState(10, 0);
                }
                break;
        }
    }

    private void prepEnterRoom(int side) {
        Tyre.player.draw = false;
        Tyre.player.walk = false;

        switch (side) {
            case 0:
                //top
                Tyre.player.posY = (ROOM_HEIGHT - Tyre.player.cbot.sHeight);
                break;
            case 1:
                //right
                Tyre.player.posX = 0;
                break;
            case 2:
                //bottom
                Tyre.player.posY = 0;
                break;
            case 3:
                //left
                Tyre.player.posX = (ROOM_WIDTH - Tyre.player.cbot.sWidth);
                break;
        }
    }

    private void processMainMenuSubState(Pen inPen) {
        switch (mainMenuSubState) {
            case 0:
                //main menu
                drawMainMenu(inPen);
                break;
            case 1:
                //new game verify
                drawNewGameVerify(inPen);
                break;
            case 2:
                //settings
                drawSettings(inPen);
                break;
            case 3:
                //help
                drawHelpScreen(inPen);
                break;
            case 4:
                //prologue
                drawPrologue(inPen);
                //slow down scroll speed
                if ((count - timestamp) >= 3) {
                    lrgMessageOffsetY++;
                    timestamp = count;
                }
                if (lrgMessageOffsetY + 92 >= (o - 5)) {
                    if (returnToMenu) {
                        changeState(10, 0);
                    } else {
                        Tyre.cApp.buildRoom(true);
                    }
                }
                break;
            case 5:
                //player name prompt
                drawEnterName(inPen);
                break;
            case 6:
                drawSettingsImport(inPen);
                break;
            case 7:
                drawSettingsImportVerify(inPen);
                break;
            case 8:
                //draw import success
                drawSettingsImportResult(inPen);
                break;
            case 9:
                break;
        }
    }

    private void drawEnterName(Pen inPen) {
        drawMainMenuBg(inPen, false);
        inPen.setColor(82, 92, 184);
        //tmpInt holds the value cursorPosY used to have
        tmpInt = ENTER_NAME_CURSORY + (cursorIndY * 12);
        inPen.fillRect(RENDER_X + GUTTER_X + 50, RENDER_Y + GUTTER_Y + tmpInt + 3, RENDER_X + GUTTER_X + 180, RENDER_Y + GUTTER_Y + tmpInt + 13);
        inPen.setFont(ft);
        inPen.setColor(Color.WHITE);

        //character image
        Tyre.charselector.paint(inPen, RENDER_X + GUTTER_X, RENDER_Y + GUTTER_Y);
        if (u > 0) {
            inPen.drawText(RENDER_X + GUTTER_X + v, RENDER_Y + GUTTER_Y + 60, tmpStr2);
        } else {
            if (u < -10) {
                u = 11;
            }
        }
        u--;

        //Name Your Character
        inPen.drawText(RENDER_X + GUTTER_X + 65, RENDER_Y + GUTTER_Y + 31, ResourceContainer.consoleStrings[Constants.CONSTR_NAMECHAR]);
        inPen.setFont(bft);
        inPen.setColor(Color.WHITE);
        o = bft.getWidth(tmpStr);
        m = (SCREEN_WIDTH - o) / 2;
        inPen.drawText(m, RENDER_Y + GUTTER_Y + ENTER_NAME_CURSORY + 12, tmpStr);

        if (cursorIndY == 0) {
            if (k > 0) {
                inPen.drawText(m + o, RENDER_Y + GUTTER_Y + ENTER_NAME_CURSORY + 11, "_");
            }

            if (k > 0) {
                k++;
                if (k % 10 == 0) {
                    k = -1;
                }
            } else {
                k--;
                if (k % 10 == 0) {
                    k = 1;
                }
            }
        }
        
        inPen.drawText(RENDER_X + GUTTER_X + 100, RENDER_Y + GUTTER_Y + ENTER_NAME_CURSORY + 23, ResourceContainer.consoleStrings[Constants.CONSTR_DONE] + " " + Font.GLYPH_BACK);
        inPen.drawBitmap(RENDER_X + GUTTER_X + 47, RENDER_Y + GUTTER_Y + tmpInt, Tyre.getBitmap(AppResources.ID_SMALL_ICON));
        inPen.drawBitmap(RENDER_X + GUTTER_X + 175, RENDER_Y + GUTTER_Y + tmpInt, Tyre.getBitmap(AppResources.ID_SMALL_ICON));
    }

    private void drawHelpScreenPage(Pen inPen, String[] tmpStrs) {
        drawCenteredText(RENDER_Y + GUTTER_Y + 31, tmpStrs[0], inPen, bof);
        inPen.setFont(sf);
        inPen.setColor(Color.WHITE);

        tx = MAINMENU_HUD_XPOS + HELP_PAGE_OFFSET1;
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 42, tmpStrs[1]);
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 52, tmpStrs[2]);
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 62, tmpStrs[3]);
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 72, tmpStrs[4]);
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 82, tmpStrs[5]);
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 92, tmpStrs[6]);
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 102, tmpStrs[7]);
    }

    private void drawHelpScreenPage(Pen inPen, String[] tmpStrs, Bitmap img) {
        drawCenteredText(RENDER_Y + GUTTER_Y + 31, tmpStrs[0], inPen, bof);
        tx = MAINMENU_HUD_XPOS + HELP_PAGE_OFFSET2 - 48;
        inPen.drawBitmap(tx, RENDER_Y + GUTTER_Y + 32, img);
        inPen.setFont(sf);
        inPen.setColor(Color.WHITE);
        
        tx = MAINMENU_HUD_XPOS + HELP_PAGE_OFFSET2;
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 42, tmpStrs[1]);
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 52, tmpStrs[2]);
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 62, tmpStrs[3]);
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 72, tmpStrs[4]);

        tx = MAINMENU_HUD_XPOS + HELP_PAGE_OFFSET1;
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 82, tmpStrs[5]);
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 92, tmpStrs[6]);
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 102, tmpStrs[7]);
    }

    private void drawHelpScreenPage(Pen inPen, String[] tmpStrs, Bitmap img1, Bitmap img2) {
        drawCenteredText(RENDER_Y + GUTTER_Y + 31, tmpStrs[0], inPen, bof);
        inPen.drawBitmap(RENDER_X + GUTTER_X + 25, RENDER_Y + GUTTER_Y + 32, img1);
        inPen.drawBitmap(RENDER_X + GUTTER_X + 170, RENDER_Y + GUTTER_Y + 62, img2);
        inPen.setFont(sf);
        inPen.setColor(Color.WHITE);

        tx = MAINMENU_HUD_XPOS + HELP_PAGE_OFFSET3;
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 35, tmpStrs[1]);
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 45, tmpStrs[2]);
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 55, tmpStrs[3]);
        inPen.drawText(tx, RENDER_Y + GUTTER_Y + 65, tmpStrs[4]);

        tx = MAINMENU_HUD_XPOS + HELP_PAGE_OFFSET4;
        drawTextR(inPen, tx, RENDER_Y + GUTTER_Y + 80, tmpStrs[5]);
        drawTextR(inPen, tx, RENDER_Y + GUTTER_Y + 90, tmpStrs[6]);
        drawTextR(inPen, tx, RENDER_Y + GUTTER_Y + 100, tmpStrs[7]);
        drawTextR(inPen, tx, RENDER_Y + GUTTER_Y + 110, tmpStrs[8]);
    }

    private void drawTextR(Pen inPen, int x, int y, String str) {
        inPen.drawText(x - inPen.getFont().getWidth(str), y, str);
    }

    private void drawHelpScreen(Pen inPen) {
        drawMainMenuBg(inPen, false);
        inPen.setFont(ft);
        inPen.setColor(Color.WHITE);

        //Settings
        if (helpPageIndex > 0) {
            inPen.drawText(RENDER_X + GUTTER_X + 30, RENDER_Y + GUTTER_Y + 32, "" + Font.GLYPH_LEFT_ARROW);
        }
        if (helpPageIndex < Constants.TOTAL_HELP_PAGE_INDEXES) {
            inPen.drawText(RENDER_X + GUTTER_X + 205, RENDER_Y + GUTTER_Y + 32, "" + Font.GLYPH_RIGHT_ARROW);
        }

        switch (helpPageIndex) {

            case 0:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P1);
                drawHelpScreenPage(inPen, tmpStrs);
                break;

            case 1:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P2);
                drawHelpScreenPage(inPen, tmpStrs);
                break;

            case 2:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P3);
                drawHelpScreenPage(inPen, tmpStrs);
                break;

            case 3:
                // Charge Bar - custom page
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P4);
                drawCenteredText(RENDER_Y + GUTTER_Y + 31, tmpStrs[0], inPen, bof);

                inPen.setFont(sf);
                inPen.setColor(Color.WHITE);
                tx = MAINMENU_HUD_XPOS + HELP_PAGE_OFFSET1;

                inPen.drawText(tx, RENDER_Y + GUTTER_Y + 42, tmpStrs[1]);
                inPen.drawText(tx, RENDER_Y + GUTTER_Y + 52, tmpStrs[2]);
                inPen.drawText(tx, RENDER_Y + GUTTER_Y + 62, tmpStrs[3]);
                inPen.drawText(tx, RENDER_Y + GUTTER_Y + 72, tmpStrs[4]);
                inPen.drawText(tx, RENDER_Y + GUTTER_Y + 82, tmpStrs[5]);

                tx = (SCREEN_WIDTH - 100) / 2;
                drawPlayerCooldownGauge(inPen, tx, RENDER_Y + GUTTER_Y + 83, 100, 5, ((40 * 90) / DEF_PLAYERWAIT_TIME));
                drawPlayerCooldownGauge(inPen, tx, RENDER_Y + GUTTER_Y + 92, 100, 5, ((4 * 90) / DEF_PLAYERWAIT_TIME));
                inPen.setColor(Color.WHITE);
                break;

            case 4:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P5);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.consoleBitmaps[(CONSOLEBITMAPS_HUD_TILES_START_INDEX + 0)]);
                break;

            case 5:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P6);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.consoleBitmaps[(CONSOLEBITMAPS_HUD_TILES_START_INDEX + 1)]);
                break;

            case 6:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P7);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.consoleBitmaps[(CONSOLEBITMAPS_HUD_TILES_START_INDEX + 2)]);
                break;

            case 7:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P8);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.consoleBitmaps[(CONSOLEBITMAPS_HUD_TILES_START_INDEX + 3)]);
                break;

            case 8:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P9);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.consoleBitmaps[(CONSOLEBITMAPS_HUD_TILES_START_INDEX + 4)]);
                break;

            case 9:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P10);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.consoleBitmaps[(CONSOLEBITMAPS_BATTLEHUD_TILES_START_INDEX + 0)]);
                break;

            case 10:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P11);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.consoleBitmaps[(CONSOLEBITMAPS_BATTLEHUD_TILES_START_INDEX + 1)]);
                break;

            case 11:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P12);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.consoleBitmaps[(CONSOLEBITMAPS_BATTLEHUD_TILES_START_INDEX + 2)]);
                break;

            case 12:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P13);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.consoleBitmaps[(CONSOLEBITMAPS_BATTLEHUD_TILES_START_INDEX + 4)]);
                break;

            case 13:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P14);
                drawHelpScreenPage(inPen, tmpStrs);
                break;

            case 14:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P15);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_HEALINGHERB].imageIndex], Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_HEALINGSALVE].imageIndex]);
                break;

            case 15:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P16);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_RESTOREPOTION].imageIndex], Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_TORCH].imageIndex]);
                break;

            case 16:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P17);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_HERBALANTIDOTE].imageIndex], Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_HERBALANTISLEEP].imageIndex]);
                break;

            case 17:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P18);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_SWORD1].imageIndex], Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_SWORD2].imageIndex]);
                break;

            case 18:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P19);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_SWORD3].imageIndex], Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_SWORD4].imageIndex]);
                break;

            case 19:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P20);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_ARMOR1].imageIndex], Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_ARMOR2].imageIndex]);
                break;

            case 20:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P21);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_ARMOR3].imageIndex], Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_ARMOR4].imageIndex]);
                break;

            case 21:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P22);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_COMPASS].imageIndex], Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_MAP].imageIndex]);
                break;

            case 22:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P23);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_HERTERODSMYTHS].imageIndex], Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_MAGICAL_WARP].imageIndex]);
                break;

            case 23:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P24);
                drawHelpScreenPage(inPen, tmpStrs, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_BALEFIREORB].imageIndex], Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_BLIZZARD_STAFF].imageIndex]);
                break;

            case 24:
                //ORBS - custom page
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P25);
                drawCenteredText(RENDER_Y + GUTTER_Y + 31, tmpStrs[0], inPen, bof);

                //draw slot border
                ty = RENDER_Y + GUTTER_Y + 54;
                inPen.setColor(Color.WHITE);
                inPen.drawBitmap(RENDER_X + GUTTER_X + 41, ty, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_ANTIPOISONORB].imageIndex]);
                inPen.drawBitmap(RENDER_X + GUTTER_X + 95, ty, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_ANTISTUNORB].imageIndex]);
                inPen.drawBitmap(RENDER_X + GUTTER_X + 149, ty, Tyre.bitmaps[ResourceContainer.items[Constants.ITEM_ANTISLEEPORB].imageIndex]);
                inPen.setFont(sf);
                inPen.drawText(RENDER_X + GUTTER_X + 43, RENDER_Y + GUTTER_Y + 102, tmpStrs[1]);
                inPen.drawText(RENDER_X + GUTTER_X + 101, RENDER_Y + GUTTER_Y + 102, tmpStrs[2]);
                inPen.drawText(RENDER_X + GUTTER_X + 154, RENDER_Y + GUTTER_Y + 102, tmpStrs[3]);

                drawCenteredText(RENDER_Y + GUTTER_Y + 42, tmpStrs[4], inPen, sf);
                drawCenteredText(RENDER_Y + GUTTER_Y + 52, tmpStrs[5], inPen, sf);
                break;

            case 25:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P26);
                drawHelpScreenPage(inPen, tmpStrs);
                break;

            case 26:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P27);
                drawHelpScreenPage(inPen, tmpStrs);
                break;

            case 27:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P28);
                drawHelpScreenPage(inPen, tmpStrs);
                break;

            case 28:
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P29);
                drawHelpScreenPage(inPen, tmpStrs);
                break;

            case 29:
                //CREDITS - custom page
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P30);
                drawCenteredText(RENDER_Y + GUTTER_Y + 31, tmpStrs[0], inPen, bof);

                inPen.setFont(sf);
                inPen.setColor(Color.WHITE);

                drawCenteredText(RENDER_Y + GUTTER_Y + 42, tmpStrs[1], inPen, sf);
                drawCenteredText(RENDER_Y + GUTTER_Y + 57, tmpStrs[2], inPen, sf);
                drawCenteredText(RENDER_Y + GUTTER_Y + 67, tmpStrs[3], inPen, sf);
                drawCenteredText(RENDER_Y + GUTTER_Y + 77, tmpStrs[4], inPen, sf);
                drawCenteredText(RENDER_Y + GUTTER_Y + 87, tmpStrs[5], inPen, sf);
                drawCenteredText(RENDER_Y + GUTTER_Y + 97, tmpStrs[6], inPen, sf);
                break;

            case 30:
                //COMPANY INFO - custom page
                tmpStrs = Tyre.cApp.getStringArray(Resources.HELP_STRINGS_P31);
                inPen.setFont(sf);
                inPen.setColor(Color.WHITE);

                //TODO LOGO
                tx = (SCREEN_WIDTH - Tyre.aboutlogoImage.getWidth()) / 2;
                inPen.drawBitmap(tx, RENDER_Y + GUTTER_Y + 56, Tyre.aboutlogoImage);
                
                drawCenteredText(RENDER_Y + GUTTER_Y + 37, tmpStrs[0], inPen, sf);
                drawCenteredText(RENDER_Y + GUTTER_Y + 47, tmpStrs[1], inPen, sf);
                drawCenteredText(RENDER_Y + GUTTER_Y + 90, tmpStrs[2], inPen, sf);
                drawCenteredText(RENDER_Y + GUTTER_Y + 100, tmpStrs[3], inPen, sf);
                break;

        }

        inPen.setFont(sf);
        inPen.drawText(RENDER_X + GUTTER_X + 135, RENDER_Y + GUTTER_Y + (9 * CONVERSATIONMSG_ROW_HEIGHT) + 6, ResourceContainer.consoleStrings[Constants.CONSTR_CONTINUEMSG]);
        inPen.drawText(RENDER_X + GUTTER_X + 30, RENDER_Y + GUTTER_Y + (9 * CONVERSATIONMSG_ROW_HEIGHT) + 6, ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN]);
    }

    private void drawSettingsImport(Pen inPen) {
        this.drawMainMenuBg(inPen, false);
        inPen.setFont(ft);
        inPen.setColor(Color.WHITE);

        //Import Game State
        inPen.drawText(RENDER_X + GUTTER_X + 68, RENDER_Y + GUTTER_Y + 31, "Import Game State");
        inPen.setFont(bft);

        //text
        inPen.setColor(Color.WHITE);
        inPen.drawText(RENDER_X + GUTTER_X + 28, RENDER_Y + GUTTER_Y + 50, "This screen determines if you have");
        inPen.drawText(RENDER_X + GUTTER_X + 32, RENDER_Y + GUTTER_Y + 62, "valid game data on your clip board.");

        //----------------------------------------------------------------------------
        if (tmpStr != null) {
            if (tmpStr.toUpperCase().indexOf("+=+=+TYRE GAME STATE START+=+=+") != -1 && tmpStr.toUpperCase().indexOf("+=+=+TYRE GAME STATE END+=+=+") != -1) {
                if (Tyre.cApp.getString(Resources.PLAYER_SPEED).trim() == "4") {
                    if (tmpStr.toUpperCase().indexOf("131:114:06:06") != -1) {
                        tmpBool = true;
                    } else {
                        tmpBool = false;
                    }
                } else {
                    if (tmpStr.toUpperCase().indexOf("121:64:19:80") != -1) {
                        tmpBool = true;
                    } else {
                        tmpBool = false;
                    }
                }
            } else {
                tmpBool = false;
            }
        } else {
            tmpBool = false;
        }

        if (tmpBool) {
            inPen.drawText(RENDER_X + GUTTER_X + 48, RENDER_Y + GUTTER_Y + 80, "** Game data detected ! **");
            inPen.drawText(RENDER_X + GUTTER_X + 35, RENDER_Y + GUTTER_Y + 98, "Press " + Font.GLYPH_WHEEL + " or " + Font.GLYPH_RETURN + " to load the data.");
        } else {
            inPen.drawText(RENDER_X + GUTTER_X + 41, RENDER_Y + GUTTER_Y + 80, "** No game data detected. **");
            inPen.drawText(RENDER_X + GUTTER_X + 24, RENDER_Y + GUTTER_Y + 98, "Press " + Font.GLYPH_BACK + " to return to the main menu.");
        }
    }

    private void drawSettingsImportResult(Pen inPen) {
        this.drawMainMenuBg(inPen, false);
        inPen.setFont(ft);
        inPen.setColor(Color.WHITE);

        //Import Game State
        inPen.drawText(RENDER_X + GUTTER_X + 49, RENDER_Y + GUTTER_Y + 31, "Import Game State Result");
        inPen.setFont(bft);

        //text
        inPen.setColor(Color.WHITE);
        if (tmpInt2 == 1) {
            //success
            inPen.drawText(RENDER_X + GUTTER_X + 46, RENDER_Y + GUTTER_Y + 50, "Data imported successfully!");
            inPen.drawText(RENDER_X + GUTTER_X + 24, RENDER_Y + GUTTER_Y + 62, "Press " + Font.GLYPH_BACK + " to return to the main menu.");
        } else {
            //fail
            inPen.drawText(RENDER_X + GUTTER_X + 70, RENDER_Y + GUTTER_Y + 50, "Data import failed.");
            inPen.drawText(RENDER_X + GUTTER_X + 24, RENDER_Y + GUTTER_Y + 62, "Press " + Font.GLYPH_BACK + " to return to the main menu.");
        }
    }

    private void drawSettings(Pen inPen) {
        drawMainMenuBg(inPen);
        inPen.setColor(82, 92, 184);
        inPen.fillRect(RENDER_X + GUTTER_X + 40, RENDER_Y + GUTTER_Y + cursorPosY + 3, RENDER_X + GUTTER_X + 190, RENDER_Y + GUTTER_Y + cursorPosY + 13);
        inPen.setFont(ft);
        inPen.setColor(Color.WHITE);
        
        //Settings
        inPen.drawText(RENDER_X + GUTTER_X + 96, RENDER_Y + GUTTER_Y + 31, ResourceContainer.consoleStrings[Constants.CONSTR_SETTINGS]);
        inPen.setFont(bft);
        inPen.setColor(Color.WHITE);
        //sounds
        inPen.drawText(RENDER_X + GUTTER_X + 60, RENDER_Y + GUTTER_Y + 50, ResourceContainer.consoleStrings[Constants.CONSTR_SOUNDS]);
        //vibration
        inPen.drawText(RENDER_X + GUTTER_X + 60, RENDER_Y + GUTTER_Y + 62, ResourceContainer.consoleStrings[Constants.CONSTR_VIBRATION]);
        inPen.setFont(sf);
        //on
        inPen.drawText(RENDER_X + GUTTER_X + 135, RENDER_Y + GUTTER_Y + 50, ResourceContainer.consoleStrings[Constants.CONSTR_ON]);
        //off
        inPen.drawText(RENDER_X + GUTTER_X + 160, RENDER_Y + GUTTER_Y + 50, ResourceContainer.consoleStrings[Constants.CONSTR_OFF]);
        inPen.drawText(RENDER_X + GUTTER_X + 135, RENDER_Y + GUTTER_Y + 62, ResourceContainer.consoleStrings[Constants.CONSTR_ON]);
        inPen.drawText(RENDER_X + GUTTER_X + 160, RENDER_Y + GUTTER_Y + 62, ResourceContainer.consoleStrings[Constants.CONSTR_OFF]);

        inPen.setFont(bft);
        inPen.drawText(RENDER_X + GUTTER_X + o, RENDER_Y + GUTTER_Y + 50, ">");
        inPen.drawText(RENDER_X + GUTTER_X + m, RENDER_Y + GUTTER_Y + 62, ">");
        inPen.drawText(RENDER_X + GUTTER_X + 70, RENDER_Y + GUTTER_Y + 74, "Export Game State");
        inPen.drawText(RENDER_X + GUTTER_X + 70, RENDER_Y + GUTTER_Y + 86, "Import Game State");
        inPen.drawText(RENDER_X + GUTTER_X + 100, RENDER_Y + GUTTER_Y + 98, ResourceContainer.consoleStrings[Constants.CONSTR_DONE] + " " + Font.GLYPH_BACK);
        //selector icons
        inPen.drawBitmap(RENDER_X + GUTTER_X + 37, RENDER_Y + GUTTER_Y + cursorPosY, Tyre.getBitmap(AppResources.ID_SMALL_ICON));
        inPen.drawBitmap(RENDER_X + GUTTER_X + 185, RENDER_Y + GUTTER_Y + cursorPosY, Tyre.getBitmap(AppResources.ID_SMALL_ICON));
    }

    private void drawNewGameVerify(Pen inPen) {
        drawMainMenuBg(inPen);
        inPen.setFont(ft);
        inPen.setColor(Color.WHITE);
        inPen.drawText(RENDER_X + GUTTER_X + 71, RENDER_Y + GUTTER_Y + 31, ResourceContainer.consoleStrings[Constants.CONSTR_STARTNEWCONFIRM]);
        inPen.setFont(bft);
        inPen.setColor(Color.WHITE);
        inPen.drawText(RENDER_X + GUTTER_X + 110, RENDER_Y + GUTTER_Y + 50, ResourceContainer.consoleStrings[Constants.CONSTR_YES]);
        inPen.drawText(RENDER_X + GUTTER_X + 112, RENDER_Y + GUTTER_Y + 62, ResourceContainer.consoleStrings[Constants.CONSTR_NO]);
        drawMainMenuFg(inPen);
    }

    private void drawSettingsImportVerify(Pen inPen) {
        drawMainMenuBg(inPen);
        inPen.setFont(ft);
        inPen.setColor(Color.WHITE);
        inPen.drawText(RENDER_X + GUTTER_Y + 53, RENDER_Y + GUTTER_Y + 31, "Overwrite current game?");
        inPen.setFont(bft);
        inPen.setColor(Color.WHITE);
        inPen.drawText(RENDER_X + GUTTER_X + 110, RENDER_Y + GUTTER_Y + 50, ResourceContainer.consoleStrings[Constants.CONSTR_YES]);
        inPen.drawText(RENDER_X + GUTTER_X + 112, RENDER_Y + GUTTER_Y + 62, ResourceContainer.consoleStrings[Constants.CONSTR_NO]);
        drawMainMenuFg(inPen);
    }

    private void drawPrologue(Pen inPen) {
        inPen.setColor(82, 92, 184);
        inPen.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        inPen.drawBitmap(MAINMENU_HUD_XPOS, MAINMENU_HUD_YPOS, Tyre.consoleBitmaps[MAINMENUBOX_INDEX]);
        ty = (PROLOGUECONTENT_VIEWABLE_HEIGHT - 12);
        if (Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight() - lrgMessageOffsetY < ty) {
            ty = Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight() - lrgMessageOffsetY;
        }
        inPen.drawBitmap(MAINMENU_HUD_XPOS + 5, MAINMENU_HUD_YPOS + 5, Tyre.consoleBitmaps[MSGCONTENT_INDEX], 0, lrgMessageOffsetY, LRGMESSAGECONTENT_PROLOGUE_WIDTH, lrgMessageOffsetY + ty);
    }

    private void drawMainMenuBg(Pen inPen) {
        drawMainMenuBg(inPen, true);
    }

    private void drawMainMenuBg(Pen inPen, boolean drawBox) {
        inPen.setColor(82, 92, 184);
        inPen.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        inPen.drawBitmap(MAINMENU_HUD_XPOS, MAINMENU_HUD_YPOS, Tyre.consoleBitmaps[MAINMENUBOX_INDEX]);
        
        //VGB 01/18/2022
        //TEST
        //Pen p = Tyre.consoleBitmaps[MAINMENUBOX_INDEX].createPen();
        //p.setFont(Font.findSystemFontLg());
        //p.drawText(20, 20, "AAAAAA");
        //inPen.drawBitmap(20, 20, Tyre.cApp.getBitmap(AppResources.ID_SMALL_ICON).rotateBy(90));
        
        if (!drawBox) {
            return;
        }
        //menu item selection bar
        inPen.setColor(82, 92, 184);
        inPen.fillRect(RENDER_X + GUTTER_X + 55, RENDER_Y + GUTTER_Y + cursorPosY + 3, RENDER_X + GUTTER_X + 175, RENDER_Y + GUTTER_Y + cursorPosY + 13);
    }

    private void drawMainMenuFg(Pen inPen) {
        inPen.drawBitmap(RENDER_X + GUTTER_X + 52, RENDER_Y + GUTTER_Y + cursorPosY, Tyre.getBitmap(AppResources.ID_SMALL_ICON));
        inPen.drawBitmap(RENDER_X + GUTTER_X + 170, RENDER_Y + GUTTER_Y + cursorPosY, Tyre.getBitmap(AppResources.ID_SMALL_ICON));
    }

    private void drawMainMenu(Pen inPen) {
        drawMainMenuBg(inPen);
        
        if (SHOW_FONT_DEMO == true) {
            inPen.setColor(Color.WHITE);
            inPen.setFont(ft);
            inPen.drawText(10, 10, "ft: ABC");

            inPen.setFont(bft);
            inPen.drawText(10, 20, "bft: ABC");

            inPen.setFont(sf);
            inPen.drawText(10, 30, "sf: ABC");

            inPen.setFont(bof);
            inPen.drawText(10, 40, "bof: ABC");
        }
        
        inPen.setFont(ft);
        inPen.setColor(Color.WHITE);
        inPen.drawText(RENDER_X + GUTTER_X + 94, RENDER_Y + GUTTER_Y + 31, ResourceContainer.consoleStrings[Constants.CONSTR_MAINMENU]);
        inPen.setFont(bft);
        inPen.setColor(Color.WHITE);
        if (Tyre.gameStart) {
            inPen.setColor(Color.RED);
            inPen.drawText(RENDER_X + GUTTER_X + 97, RENDER_Y + GUTTER_Y + 50, ResourceContainer.consoleStrings[Constants.CONSTR_CONTINUE]);
            inPen.setColor(Color.WHITE);
        } else {
            inPen.drawText(RENDER_X + GUTTER_X + 97, RENDER_Y + GUTTER_Y + 50, ResourceContainer.consoleStrings[Constants.CONSTR_CONTINUE]);
        }
        inPen.drawText(RENDER_X + GUTTER_X + 76, RENDER_Y + GUTTER_Y + 62, ResourceContainer.consoleStrings[Constants.CONSTR_STARTNEW]);
        inPen.drawText(RENDER_X + GUTTER_X + 97, RENDER_Y + GUTTER_Y + 74, ResourceContainer.consoleStrings[Constants.CONSTR_SETTINGS]);
        inPen.drawText(RENDER_X + GUTTER_X + 88, RENDER_Y + GUTTER_Y + 86, ResourceContainer.consoleStrings[Constants.CONSTR_HELP]);
        inPen.drawText(RENDER_X + GUTTER_X + 97, RENDER_Y + GUTTER_Y + 98, ResourceContainer.consoleStrings[Constants.CONSTR_PROLOGUE]);
        drawMainMenuFg(inPen);
    }

    public void formatBattleMessage(String s, int c) {
        s = s.trim();
        j = bft.getWidth(s);
        if (j >= MESSAGES_WIDTH) {
            //find out how many rows s will take up
            k = j / MESSAGES_WIDTH;
            if (j % MESSAGES_WIDTH != 0) {
                k++;
            }

            if (k > messages.length) {
                k = (messages.length - 1);
            }

            //shift down k rows
            for (i = 0; i < k; i++) {
                messages[3].val = messages[2].val;
                messages[3].color = messages[2].color;
                messages[2].val = messages[1].val;
                messages[2].color = messages[1].color;
                messages[1].val = messages[0].val;
                messages[1].color = messages[0].color;
                messages[0].val = "";
                messages[0].color = c;
            }
            messages[0].val = s;
            messages[0].color = c;

            for (i = 0; i < k; i++) {
                if (messages[i + 1].val == null) {
                    messages[i + 1].val = "";
                }
                while (bft.getWidth(messages[i].val) > MESSAGES_WIDTH) {
                    messages[i + 1].val = messages[i].val.substring(messages[i].val.lastIndexOf(" ")).trim() + " " + messages[i + 1];
                    messages[i].val = messages[i].val.substring(0, messages[i].val.lastIndexOf(" "));
                }
            }
        } else {
            //the message is only one row no splitting is necessary
            messages[3].val = messages[2].val;
            messages[3].color = messages[2].color;
            messages[2].val = messages[1].val;
            messages[2].color = messages[1].color;
            messages[1].val = messages[0].val;
            messages[1].color = messages[0].color;
            messages[0].val = s;
            messages[0].color = c;
        }
    }

    //transitionRoom by walking from an adjacent room - only rebuild as necessary
    public void transitionRoom(int side) {
        //reset adjacent room render
        resetBuildFlags();
        //if building adjacent rooms check to see if the images can be shifted without a full rebuild
        if (RENDER_ADJACENT_ROOMS == 1) {
            Tyre.adjustAdjacentRooms(side);
        }
        resort = false;
        Tyre.cApp.buildRoom(true);
    }

    //transitionRoom without walking from an adjacent room (jump link), force full rebuild
    public void transitionRoom() {
        //reset adjacent room render
        resetBuildFlags();
        resort = false;
        Tyre.cApp.buildRoom(true);
    }

    public void ExportGameState() {
        String to = "";
        String subject = "Tyre Game State Export";
        String body = "Copy the following piece of text onto the\n";
        body += "clip board. Then open 'Tyre' and locate\n";
        body += "'Import Game State' window.  This window\n";
        body += "is contained in the 'Settings' section of\n";
        body += "the main menu.  Be sure to include the\n";
        body += "entire start and end tags. The game will\n";
        body += "detect the game data on the clip board\n";
        body += "and allow you to import the data.\n\n" + EncodeGameState();

        /*
        IPCMessage ipc = new IPCMessage();
        ipc.addItem("action", "send");
        ipc.addItem("subject", subject);
        ipc.addItem("body", body);
        
        //to, cc and bcc can be String[]'s as well
        ipc.addItem("to", to);
        //ipc.addItem("from" , to);
        //ipc.addItem("cc" , cc);
        //ipc.addItem("bcc" , bcc);
        Registrar.sendMessage("Email", ipc, null);
        */
        
        Logger.wr("Action: send");
        Logger.wr("Subject: " + subject);
        Logger.wr("Body: " + body);
    }

    public boolean DecodeGameState(String data) {
        try {
            data = data.trim();
            if (!data.substring(0, 32).toUpperCase().trim().equals("+=+=+TYRE GAME STATE START+=+=+")) {
                return false;
            }
            
            char[] chr = data.toCharArray();
            int[] d1 = new int[124];
            String tmp = "";
            char test;
            int count = 0;
            int found = 0;
            
            for (int i = 32; i < chr.length; i++) {
                test = chr[i];
                if (test == ':' || test == '\n' || test == '\r') {
                    if (count < d1.length) {
                        d1[count] = Integer.parseInt(tmp.trim());
                        found++;
                    } else {
                        return false;
                    }
                    tmp = "";
                    count++;
                } else if (test == '+') {
                    i = chr.length;
                    break;
                } else {
                    tmp += test + "";
                }
            }
            data = null;
            chr = null;

            if (found != 124) {
                return false;
            }

            //flag section first 18 ints
            byte[] tmpB = new byte[4];
            boolean[] tmpFlgs = new boolean[640];
            count = 0;
            int offset = 0;

            for (int i = offset; i < 20; i++) {
                tmpB = Tyre.toByteArray(d1[i], tmpB);
                for (int j = 3; j >= 0; j--) {
                    if ((tmpB[j] & 0x80) > 0) {
                        tmpFlgs[count] = true;
                        count++;
                    } else {
                        tmpFlgs[count] = false;
                        count++;
                    }

                    if ((tmpB[j] & 0x40) > 0) {
                        tmpFlgs[count] = true;
                        count++;
                    } else {
                        tmpFlgs[count] = false;
                        count++;
                    }

                    if ((tmpB[j] & 0x20) > 0) {
                        tmpFlgs[count] = true;
                        count++;
                    } else {
                        tmpFlgs[count] = false;
                        count++;
                    }

                    if ((tmpB[j] & 0x10) > 0) {
                        tmpFlgs[count] = true;
                        count++;
                    } else {
                        tmpFlgs[count] = false;
                        count++;
                    }

                    if ((tmpB[j] & 0x08) > 0) {
                        tmpFlgs[count] = true;
                        count++;
                    } else {
                        tmpFlgs[count] = false;
                        count++;
                    }

                    if ((tmpB[j] & 0x04) > 0) {
                        tmpFlgs[count] = true;
                        count++;
                    } else {
                        tmpFlgs[count] = false;
                        count++;
                    }

                    if ((tmpB[j] & 0x02) > 0) {
                        tmpFlgs[count] = true;
                        count++;
                    } else {
                        tmpFlgs[count] = false;
                        count++;
                    }

                    if ((tmpB[j] & 0x01) > 0) {
                        tmpFlgs[count] = true;
                        count++;
                    } else {
                        tmpFlgs[count] = false;
                        count++;
                    }
                }
            }
            offset = 20;

            loadFlags = new boolean[ResourceContainer.flags.length];
            for (int i = 0; i < loadFlags.length; i++) {
                loadFlags[i] = tmpFlgs[i];
            }
            tmpFlgs = null;

            //game state section
            //flag section next 64 ints
            int[] gsTmp = new int[64];
            for (int i = offset; i < (offset + 64); i++) {
                gsTmp[(i - offset)] = d1[i];
            }

            byte[] tmpD = new byte[256];
            tmpB = new byte[4];
            count = 0;
            for (int i = 0; i < gsTmp.length; i++) {
                tmpB = Tyre.toByteArray(gsTmp[i], tmpB);
                for (int j = 3; j >= 0; j--) {
                    tmpD[count] = tmpB[j];
                    count++;
                }
            }

            byte[] ser = Tyre.cApp.serializeGameData();
            loadGS = new byte[ser.length];
            for (int i = 0; i < ser.length; i++) {
                loadGS[i] = tmpD[i];
            }
            tmpD = null;
            offset += 64;

            //game state section
            //items section next 32 ints
            gsTmp = new int[32];
            for (int i = offset; i < (offset + 32); i++) {
                gsTmp[(i - offset)] = d1[i];
            }

            tmpD = new byte[128];
            tmpB = new byte[4];
            count = 0;
            for (int i = 0; i < gsTmp.length; i++) {
                tmpB = Tyre.toByteArray(gsTmp[i], tmpB);
                for (int j = 3; j >= 0; j--) {
                    tmpD[count] = tmpB[j];
                    count++;
                }
            }

            int[][] itmTmp = new int[50][2];
            count = 0;
            for (int i = 0; i < 100; i += 2) {
                itmTmp[count][0] = tmpD[i];
                //-1 and -2 are reserved values so check for them before masking
                if ((int) tmpD[i + 1] == -1 || (int) tmpD[i + 1] == -2) {
                    itmTmp[count][1] = (int) tmpD[i + 1];
                } else {
                    itmTmp[count][1] = (int) (0x000000FF & ((int) tmpD[i + 1]));
                }
                count++;
            }
            loadItems = itmTmp;

            itmTmp = null;
            offset += 32;

            //name section
            //items section next 4 ints
            gsTmp = new int[4];
            for (int i = (offset); i < (offset + 4); i++) {
                gsTmp[(i - offset)] = d1[i];
            }

            tmpD = new byte[16];
            tmpB = new byte[4];
            count = 0;
            for (int i = 0; i < gsTmp.length; i++) {
                tmpB = Tyre.toByteArray(gsTmp[i], tmpB);
                for (int j = 3; j >= 0; j--) {
                    tmpD[count] = tmpB[j];
                    count++;
                }
            }

            String tmpName = "";
            for (int i = 0; i < tmpD.length; i++) {
                tmpName += "" + ((char) tmpD[i]);
            }
            loadName = tmpName.trim();
            tmpName = null;
            d1 = null;
            
        } catch (Exception e) {
            e.printStackTrace();
            loadFlags = null;
            loadGS = null;
            loadItems = null;
            loadName = null;
            return false;
        }
        return true;
    }

    public String EncodeGameState() {
        String msg = "+=+=+TYRE GAME STATE START+=+=+\n";
        byte[] b = new byte[80];
        int count = 0;
        byte tmp = 0;
        for (int i = 0; i < 640; i++) {
            if (i == 0 || i % 8 == 0) {
                b[i / 8] = 0;
            }
            if (i < ResourceContainer.flags.length) {
                if (ResourceContainer.flags[i]) {
                    tmp = 1;
                } else {
                    tmp = 0;
                }
            } else {
                tmp = 0;
            }

            switch (count) {
                case 0:
                    if (tmp == 1) {
                        b[i / 8] += 128;
                    }
                    break;
                case 1:
                    if (tmp == 1) {
                        b[i / 8] += 64;
                    }
                    break;
                case 2:
                    if (tmp == 1) {
                        b[i / 8] += 32;
                    }
                    break;
                case 3:
                    if (tmp == 1) {
                        b[i / 8] += 16;
                    }
                    break;
                case 4:
                    if (tmp == 1) {
                        b[i / 8] += 8;
                    }
                    break;
                case 5:
                    if (tmp == 1) {
                        b[i / 8] += 4;
                    }
                    break;
                case 6:
                    if (tmp == 1) {
                        b[i / 8] += 2;
                    }
                    break;
                case 7:
                    if (tmp == 1) {
                        b[i / 8] += 1;
                    }
                    break;
            }

            count++;
            if (count == 8) {
                count = 0;
            }
        }

        int[] conv = new int[20];
        count = 0;
        for (int i = 0; i < conv.length; i++) {
            conv[i] = Tyre.GetIntDataFromBytes(b, i * 4);
            msg += Integer.toString(conv[i]);
            count++;
            if (count == 4) {
                count = 0;
                msg += "\n";
            } else {
                msg += ":";
            }
        }
        //msg += "\n";

        b = new byte[256];
        byte[] ser = Tyre.cApp.serializeGameData();
        for (int i = 0; i < b.length; i++) {
            if (i < ser.length) {
                b[i] = ser[i];
            } else {
                b[i] = 0;
            }
        }
        conv = new int[64];
        count = 0;
        for (int i = 0; i < conv.length; i++) {
            conv[i] = Tyre.GetIntDataFromBytes(b, i * 4);
            msg += Integer.toString(conv[i]);
            count++;
            if (count == 4) {
                count = 0;
                msg += "\n";
            } else {
                msg += ":";
            }
        }

        b = new byte[128];
        count = 0;
        for (int i = 0; i < Tyre.player.items.length; i++) {
            b[count] = (byte) Tyre.player.items[i][0];
            b[count + 1] = (byte) Tyre.player.items[i][1];
            count += 2;
        }

        conv = new int[32];
        count = 0;
        for (int i = 0; i < conv.length; i++) {
            conv[i] = Tyre.GetIntDataFromBytes(b, i * 4);
            msg += Integer.toString(conv[i]);
            count++;
            if (count == 4) {
                count = 0;
                msg += "\n";
            } else {
                msg += ":";
            }
        }

        b = new byte[16];
        char[] chr = ResourceContainer.strings[Constants.PLAYER_NAME_INDEX].toCharArray();
        for (int i = 0; i < b.length; i++) {
            if (i < chr.length) {
                b[i] = (byte) chr[i];
            }
        }

        conv = new int[4];
        count = 0;
        for (int i = 0; i < conv.length; i++) {
            conv[i] = Tyre.GetIntDataFromBytes(b, i * 4);
            msg += Integer.toString(conv[i]);
            count++;
            if (count == 4) {
                count = 0;
                msg += "\n";
            } else {
                msg += ":";
            }
        }

        if (Tyre.cApp.getString(Resources.PLAYER_SPEED).trim() == "4") {
            msg += "131:114:06:06\n";
        } else {
            msg += "121:64:19:80\n";
        }

        msg += "+=+=+TYRE GAME STATE END+=+=+\n";
        return msg;
    }

    public void TimerTick() {
        if (Tyre.suspended) {
            Logger.wr("SUSPEND: TimerTick blocked");
            return;
        }

        if (count > 100000) {
            count = 1;
            try {
                int i = 0;
                if (Tyre.roomChars != null) {
                    for (; i < Tyre.roomChars.length; i++) {
                        if (Tyre.roomChars[i] != null) {
                            Tyre.roomChars[i].walkStart = 0;
                        }
                    }
                }
                if (enemies != null) {
                    for (i = 0; i < enemies.length; i++) {
                        if (enemies[i] != null) {
                            enemies[i].hitTime = 0;
                        }
                    }
                }
                Tyre.player.walkStart = 0;
                timestamp = 0;
                successTime = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            count++;
        }
        invalidate();
    }

    public void invalidate() {
        
    }
    
    private void handleEventWidgetDownCancel() {
        handleEventWidgetDownBack();
    }

    private void handleEventWidgetDownBack() {
        switch (gameState) {
            case 0:
                switch (gameSubState) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                        PlaySystemSound(Meta.MENU_DISMISS);
                        consumedDown = true;
                        break;
                }
                break;
            case 3:
                switch (battleModeState) {
                    case 2:
                    case 7:
                    case 10:
                    case 11:
                        PlaySystemSound(Meta.MENU_DISMISS);
                        consumedDown = true;
                        break;
                }
                break;
        }
        if (!consumedDown) {
            consumedDown = true;
        }
    }

    private void scrollDown() {
        if ((lrgMessageOffsetY + LRGMESSAGECONTENT_ROW_HEIGHT + LRGMESSAGECONTENT_VIEWABLE_HEIGHT) <= Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight()) {
            lrgMessageOffsetY += LRGMESSAGECONTENT_ROW_HEIGHT;
            valA = LRGMESSAGECONTENT_ROW_HEIGHT;
            valB = Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight();
            lrgMessageScrollOffsetY += (int) (((valA / valB) * LRGMESSAGESCROLLBAR_HEIGHT) + 1);
            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
        } else {
            PlaySystemSound(Meta.SCROLL_LIMIT);
        }
    }

    private boolean scrollDownBool() {
        if ((lrgMessageOffsetY + LRGMESSAGECONTENT_ROW_HEIGHT + LRGMESSAGECONTENT_VIEWABLE_HEIGHT) <= Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight()) {
            lrgMessageOffsetY += LRGMESSAGECONTENT_ROW_HEIGHT;
            lrgMessageScrollOffsetY += (int) ((((float) LRGMESSAGECONTENT_ROW_HEIGHT / (float) Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight()) * LRGMESSAGESCROLLBAR_HEIGHT) + 1);
            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
            return true;
        } else {
            PlaySystemSound(Meta.SCROLL_LIMIT);
            return false;
        }
    }

    private void scrollItemDown() {
        if ((lrgMessageOffsetY + LRGMESSAGEITEMCONTENT_ROW_HEIGHT + LRGMESSAGECONTENT_VIEWABLE_HEIGHT) <= Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight() + 18) {
            lrgMessageOffsetY += LRGMESSAGEITEMCONTENT_ROW_HEIGHT;
            valA = LRGMESSAGEITEMCONTENT_ROW_HEIGHT;
            valB = Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight();
            lrgMessageScrollOffsetY += (int) (((valA * LRGMESSAGESCROLLBAR_HEIGHT) / valB) + 1);
        }
    }

    private boolean scrollItemDownBool() {
        if ((lrgMessageOffsetY + LRGMESSAGEITEMCONTENT_ROW_HEIGHT + LRGMESSAGECONTENT_VIEWABLE_HEIGHT) <= Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight() + 18) {
            lrgMessageOffsetY += LRGMESSAGEITEMCONTENT_ROW_HEIGHT;
            valA = LRGMESSAGEITEMCONTENT_ROW_HEIGHT;
            valB = Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight();
            lrgMessageScrollOffsetY += (int) (((valA * LRGMESSAGESCROLLBAR_HEIGHT) / valB) + 1);
            return true;
        } else {
            return false;
        }
    }

    private void updateItemInventoryTarget() {
        //TODO - updateItemInventory bypassed
        //not item shop
        if (gameSubState != 6) {
            ty = (lrgMessageOffsetY / LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + itemcur_y;
            tx = ((ty * ITEMVIEW_ITEMS_PER_ROW) + itemcur_x);
            selItem = Tyre.player.items[tx][0];
            
            //heal player hp (0), restore player hp (11), readable (12)
            //not battle mode  (anything other than ARMOR, SWORD, OTHER
            if (gameState != 3) {
                if (!(ResourceContainer.items[selItem].type == Constants.ITEMTYPE_OTHER)) {
                    tmpBool = true;
                } else {
                    tmpBool = false;
                }
            }
            
            //battle mode
            else {
                //battle mode - all items can be used except readable
                if (ResourceContainer.items[selItem].type == Constants.ITEMTYPE_READABLE || ResourceContainer.items[selItem].type == Constants.ITEMTYPE_OTHER) {
                    tmpBool = false;
                } else {
                    tmpBool = true;
                }
            }
        } else {
            //in item shop
            ty = (lrgMessageOffsetY / LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + itemcur_y;
            tx = ((ty * ITEMVIEW_ITEMS_PER_ROW) + itemcur_x);
            if (gameSubState == 6 && herbShopSubState == 1) {
                selItem = shopItems[tx];
            } else {
                selItem = Tyre.player.items[tx][0];
            }
            tmpBool = true;
        }
    }

    private void scrollItemInventoryRight() {
        //the max width depends on the row
        ty = (lrgMessageOffsetY / LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + itemcur_y;
        //bottom row, check how many items are on it
        if (ty == item_maxv) {
            if (gameSubState == 6 && herbShopSubState == 1) {
                k = shopItems.length % ITEMVIEW_ITEMS_PER_ROW;
            } else {
                k = currentItemCount() % ITEMVIEW_ITEMS_PER_ROW;
            }
            if ((itemcur_x < item_maxu) && ((k == 0) || (itemcur_x < k - 1))) {
                itemcur_x++;
                updateItemInventoryTarget();
                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
            } else {
                PlaySystemSound(Meta.SCROLL_LIMIT);
            }
        } else {
            if (itemcur_x < item_maxu) {
                itemcur_x++;
                updateItemInventoryTarget();
                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
            } else {
                PlaySystemSound(Meta.SCROLL_LIMIT);
            }
        }
    }

    private void scrollItemInventoryLeft() {
        if (itemcur_x > 0) {
            itemcur_x--;
            updateItemInventoryTarget();
            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
        } else {
            PlaySystemSound(Meta.SCROLL_LIMIT);
        }
    }

    private void scrollItemInventoryDown() {
        if (((lrgMessageOffsetY + itemCursorPos) / LRGMESSAGEITEMCONTENT_ROW_HEIGHT) < item_maxv) {
            //this is the second to last row, check how many items are on the bottom row
            if (((lrgMessageOffsetY + itemCursorPos) / LRGMESSAGEITEMCONTENT_ROW_HEIGHT) == (item_maxv - 1)) {
                //2nd to bottom row, check that xpos in bottom row exists otherwise block scroll down
                if (gameSubState == 6 && herbShopSubState == 1) {
                    k = shopItems.length % ITEMVIEW_ITEMS_PER_ROW;
                } else {
                    k = currentItemCount() % ITEMVIEW_ITEMS_PER_ROW;
                }
                if ((k == 0) || (itemcur_x < k)) {
                    itemCursorPos += LRGMESSAGEITEMCONTENT_ROW_HEIGHT;
                    itemcur_y++;
                    updateItemInventoryTarget();
                    PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                } else {
                    PlaySystemSound(Meta.SCROLL_LIMIT);
                }
            } else {
                itemCursorPos += LRGMESSAGEITEMCONTENT_ROW_HEIGHT;
                itemcur_y++;
                updateItemInventoryTarget();
                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
            }
        } else {
            if (scrollItemDownBool()) {
                itemCursorPos -= LRGMESSAGEITEMCONTENT_ROW_HEIGHT;
                itemcur_y--;
                updateItemInventoryTarget();
                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
            } else {
                PlaySystemSound(Meta.SCROLL_LIMIT);
            }
        }
        if ((itemCursorPos + (3 * LRGMESSAGEITEMCONTENT_ROW_HEIGHT)) >= (LRGMESSAGECONTENT_POSY + LRGMESSAGECONTENT_VIEWABLE_HEIGHT) && lrgMessageOffsetY < (Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight() - LRGMESSAGECONTENT_VIEWABLE_HEIGHT)) {
            scrollItemDown();
            itemCursorPos -= LRGMESSAGEITEMCONTENT_ROW_HEIGHT;
            itemcur_y--;
            updateItemInventoryTarget();
            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
        } else {
            PlaySystemSound(Meta.SCROLL_LIMIT);
        }
    }

    private void scrollItemInventoryUp() {
        if ((((lrgMessageOffsetY + itemCursorPos) / LRGMESSAGEITEMCONTENT_ROW_HEIGHT)) > 0) {
            itemCursorPos -= LRGMESSAGEITEMCONTENT_ROW_HEIGHT;
            itemcur_y--;
            updateItemInventoryTarget();
            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
        } else {
            if (scrollItemUpBool()) {
                itemCursorPos += LRGMESSAGEITEMCONTENT_ROW_HEIGHT;
                itemcur_y++;
                updateItemInventoryTarget();
                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
            } else {
                PlaySystemSound(Meta.SCROLL_LIMIT);
            }
        }
        if ((itemCursorPos - (3 * LRGMESSAGEITEMCONTENT_ROW_HEIGHT)) < (LRGMESSAGECONTENT_POSY) && lrgMessageOffsetY > 0) {
            scrollItemUp();
            itemCursorPos += LRGMESSAGEITEMCONTENT_ROW_HEIGHT;
            itemcur_y++;
            updateItemInventoryTarget();
            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
        } else {
            PlaySystemSound(Meta.SCROLL_LIMIT);
        }
    }

    private void handleEventWidgetDownDown() {
        switch (gameState) {
            //normal game loop
            case 0:
                switch (gameSubState) {
                    //normal sub state
                    case 0:
                        if (transBottom < 1) {
                            Tyre.player.walk = true;
                            Tyre.player.dir = 0;
                            if (!Tyre.player.walk) {
                                Tyre.player.walkStart = count;
                            }
                        }
                        break;
                    case 3:
                        //statistics
                        scrollDown();
                        break;
                    case 4:
                        //inventory
                        scrollItemInventoryDown();
                        break;
                    case 5:
                        //story message
                        scrollDown();
                        break;
                    case 6:
                        //herb shop substate
                        switch (herbShopSubState) {
                            //buy/sell menu
                            case 0:
                                switch (cursorPosY) {
                                    case 45:
                                        cursorPosY += 10;
                                        PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                        break;
                                    default:
                                        PlaySystemSound(Meta.SCROLL_LIMIT);
                                        break;
                                }
                                break;
                            case 1:
                                scrollItemInventoryDown();
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            case 2:
                                scrollItemInventoryDown();
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            case 3:
                                if (currentQuantity > minQuantity) {
                                    currentQuantity--;
                                    PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                } else {
                                    PlaySystemSound(Meta.SCROLL_LIMIT);
                                }
                                break;
                        }
                        break;
                    case 7:
                        //inn
                        break;
                    /*
                    case 8:
                        //spell shop
                        break;
                    case 9:
                        //spell inventory
                        break;
                    */
                    case 11:
                        //toll keeper substate
                        switch (herbShopSubState) {
                            //buy/sell menu
                            case 0:
                                switch (cursorPosY) {
                                    case 45:
                                        cursorPosY += 10;
                                        PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                        break;
                                    default:
                                        PlaySystemSound(Meta.SCROLL_LIMIT);
                                        break;
                                }
                                break;
                        }
                        break;
                }
                consumedDown = true;
                break;
            //battle mode state
            case 3:
                switch (battleModeState) {
                    //player's turn
                    case 2:
                        if (selectEnemy && Tyre.player.canSelectAll) {
                            allTargetsSelected = false;
                        }
                        break;
                    /*
                    case 4:
                        //spell inventory
                    */
                    case 7:
                        //item inventory
                        scrollItemInventoryDown();
                        break;
                }
                consumedDown = true;
                break;
            case 10:
                switch (mainMenuSubState) {
                    case 0:
                        //main menu
                        switch (cursorPosY) {
                            case 39:
                                cursorPosY += 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            case 51:
                                cursorPosY += 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            case 63:
                                cursorPosY += 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            case 75:
                                cursorPosY += 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            default:
                                PlaySystemSound(Meta.SCROLL_LIMIT);
                                break;
                        }
                        break;
                    case 1:
                        //new game verify
                        switch (cursorPosY) {
                            case 39:
                                cursorPosY += 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            default:
                                PlaySystemSound(Meta.SCROLL_LIMIT);
                                break;
                        }
                        break;
                    case 2:
                        //settings
                        switch (cursorPosY) {
                            case 39:
                                cursorPosY += 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            case 51:
                                cursorPosY += 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            case 63:
                                cursorPosY += 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            default:
                                PlaySystemSound(Meta.SCROLL_LIMIT);
                                break;
                        }
                        break;
                    case 5:
                        //name character
                        break;
                    case 7:
                        //import verify
                        switch (cursorPosY) {
                            case 39:
                                cursorPosY += 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            default:
                                PlaySystemSound(Meta.SCROLL_LIMIT);
                                break;
                        }
                        break;
                }
                consumedDown = true;
                break;
        }
    }

    private void handleEventWidgetDownRight() {
        switch (gameState) {
            //normal game state
            case 0:
                switch (gameSubState) {
                    //normal sub state
                    case 0:
                        if (transRight < 1) {
                            Tyre.player.walk = true;
                            Tyre.player.dir = 1;
                            if (!Tyre.player.walk) {
                                Tyre.player.walkStart = count;
                            }
                        }
                        break;
                    //menu substate
                    case 1:
                        if (seli < 4) {
                            seli++;
                            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                        } else {
                            PlaySystemSound(Meta.SCROLL_LIMIT);
                        }
                        break;
                    case 4:
                        //inventory
                        scrollItemInventoryRight();
                        break;
                    case 6:
                        //herb shop
                        switch (herbShopSubState) {
                            //buy
                            case 1:
                                scrollItemInventoryRight();
                                break;
                            //sell
                            case 2:
                                scrollItemInventoryRight();
                                break;
                        }
                        break;
                }
                consumedDown = true;
                break;
            //battle mode
            case 3:
                switch (battleModeState) {
                    case 2:
                        if (selectEnemy) {
                            switch (selectEnemyPosX) {
                                case 2:
                                    if (enemies.length >= 2) {
                                        if (enemies[1] != null && enemies[1].isDead != true) {
                                            selectEnemyPosX += 80;
                                            selectedEnemyPos++;
                                            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                        } else if (enemies[2] != null && enemies[2].isDead != true) {
                                            selectEnemyPosX += 160;
                                            selectedEnemyPos += 2;
                                            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                        } else {
                                            PlaySystemSound(Meta.SCROLL_LIMIT);
                                        }
                                    } else {
                                        PlaySystemSound(Meta.SCROLL_LIMIT);
                                    }
                                    break;
                                case 82:
                                    if (enemies.length == 3) {
                                        if (enemies[2] != null) {
                                            selectEnemyPosX += 80;
                                            selectedEnemyPos++;
                                            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                        } else {
                                            PlaySystemSound(Meta.SCROLL_LIMIT);
                                        }
                                    } else {
                                        PlaySystemSound(Meta.SCROLL_LIMIT);
                                    }
                                    break;
                            }
                        } else {
                            //player's turn, not selecting enemy so move cursor
                            if ((battleModeState == 2) && (seli < 4)) {
                                seli++;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                            } else {
                                PlaySystemSound(Meta.SCROLL_LIMIT);
                            }
                        }
                        break;
                    case 7:
                        //inventory
                        scrollItemInventoryRight();
                        break;
                }
                consumedDown = true;
                break;
            case 10:
                switch (mainMenuSubState) {
                    case 3:
                        //help
                        if (helpPageIndex < Constants.TOTAL_HELP_PAGE_INDEXES) {
                            helpPageIndex++;
                            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                        } else {
                            PlaySystemSound(Meta.SCROLL_LIMIT);
                        }
                        break;
                    //name your char
                    case 5:
                        if (cursorIndX < ResourceContainer.players.length - 1) {
                            cursorIndX++;
                            tmpStr2 = "L:"; //"uF00B";
                            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                        } else {
                            PlaySystemSound(Meta.SCROLL_LIMIT);
                        }
                        Tyre.charselector.loadCharData(cursorIndX);
                        if (tmpStr.equals(ResourceContainer.strings[Constants.MALE_PLAYER_NAME_INDEX])) {
                            ResourceContainer.strings[Constants.PLAYER_NAME_INDEX] = ResourceContainer.strings[Constants.FEMALE_PLAYER_NAME_INDEX];
                            tmpStr = ResourceContainer.strings[Constants.PLAYER_NAME_INDEX];
                        }
                        break;
                    //settings
                    case 2:
                        switch (cursorPosY) {
                            case 39:
                                if (o == 130) {
                                    o = 155;
                                    PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                } else {
                                    PlaySystemSound(Meta.SCROLL_LIMIT);
                                }
                                break;
                            case 51:
                                if (m == 130) {
                                    m = 155;
                                    PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                } else {
                                    PlaySystemSound(Meta.SCROLL_LIMIT);
                                }
                                break;
                            default:
                                PlaySystemSound(Meta.SCROLL_LIMIT);
                                break;
                        }
                        break;
                }
                break;
        }
    }

    private void scrollUp() {
        if ((lrgMessageOffsetY - LRGMESSAGECONTENT_ROW_HEIGHT) >= 0) {
            lrgMessageOffsetY -= LRGMESSAGECONTENT_ROW_HEIGHT;
            lrgMessageScrollOffsetY -= (int) ((((float) LRGMESSAGECONTENT_ROW_HEIGHT / (float) Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight()) * LRGMESSAGESCROLLBAR_HEIGHT) + 1);
            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
        } else {
            PlaySystemSound(Meta.SCROLL_LIMIT);
        }
    }

    private boolean scrollUpBool() {
        if ((lrgMessageOffsetY - LRGMESSAGECONTENT_ROW_HEIGHT) >= 0) {
            lrgMessageOffsetY -= LRGMESSAGECONTENT_ROW_HEIGHT;
            lrgMessageScrollOffsetY -= (int) ((((float) LRGMESSAGECONTENT_ROW_HEIGHT / (float) Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight()) * LRGMESSAGESCROLLBAR_HEIGHT) + 1);
            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
            return true;
        } else {
            PlaySystemSound(Meta.SCROLL_LIMIT);
            return false;
        }
    }

    private void scrollItemUp() {
        if ((lrgMessageOffsetY - LRGMESSAGEITEMCONTENT_ROW_HEIGHT) >= 0) {
            lrgMessageOffsetY -= LRGMESSAGEITEMCONTENT_ROW_HEIGHT;
            lrgMessageScrollOffsetY -= (int) ((((float) LRGMESSAGEITEMCONTENT_ROW_HEIGHT / (float) Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight()) * LRGMESSAGESCROLLBAR_HEIGHT) + 1);
        }
    }

    private boolean scrollItemUpBool() {
        if ((lrgMessageOffsetY - LRGMESSAGEITEMCONTENT_ROW_HEIGHT) >= 0) {
            lrgMessageOffsetY -= LRGMESSAGEITEMCONTENT_ROW_HEIGHT;
            lrgMessageScrollOffsetY -= (int) ((((float) LRGMESSAGEITEMCONTENT_ROW_HEIGHT / (float) Tyre.consoleBitmaps[MSGCONTENT_INDEX].getHeight()) * LRGMESSAGESCROLLBAR_HEIGHT) + 1);
            return true;
        } else {
            return false;
        }
    }

    private void handleEventWidgetDownUp() {
        switch (gameState) {
            case 0:
                switch (gameSubState) {
                    case 0:
                        if (transTop < 1) {
                            Tyre.player.walk = true;
                            Tyre.player.dir = 2;
                            if (!Tyre.player.walk) {
                                Tyre.player.walkStart = count;
                            }
                        }
                        break;
                    case 3:
                        //statistics
                        scrollUp();
                        break;
                    case 4:
                        //item inventory
                        scrollItemInventoryUp();
                        break;
                    case 5:
                        //story message
                        scrollUp();
                        break;
                    case 6:
                        //herb shop substate
                        switch (herbShopSubState) {
                            //buy/sell menu
                            case 0:
                                switch (cursorPosY) {
                                    case 55:
                                        cursorPosY -= 10;
                                        PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                        break;
                                    default:
                                        PlaySystemSound(Meta.SCROLL_LIMIT);
                                        break;
                                }
                                break;
                            case 1:
                                scrollItemInventoryUp();
                                break;
                            case 2:
                                scrollItemInventoryUp();
                                break;
                            case 3:
                                if (currentQuantity < maxQuantity) {
                                    currentQuantity++;
                                    PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                } else {
                                    PlaySystemSound(Meta.SCROLL_LIMIT);
                                }
                                break;
                        }
                        break;
                    case 7:
                        //inn
                        break;
                    /*
                    case 8:
                        //spell shop
                        break;
                    case 9:
                        //spell inventory
                        break;
                    */
                    case 11:
                        //toll keeper substate
                        switch (herbShopSubState) {
                            //buy/sell menu
                            case 0:
                                switch (cursorPosY) {
                                    case 55:
                                        cursorPosY -= 10;
                                        PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                        break;
                                    default:
                                        PlaySystemSound(Meta.SCROLL_LIMIT);
                                        break;
                                }
                                break;
                        }
                        break;
                }
                consumedUp = true;
                break;
            case 3:
                switch (battleModeState) {
                    case 2:
                        if (selectEnemy && Tyre.player.canSelectAll) {
                            allTargetsSelected = true;
                            splashEnemyCount = activeEnemyCount();
                        }
                        break;
                    /*
                    case 4:
                        //spell inventory
                    */
                    case 7:
                        //item inventory
                        scrollItemInventoryUp();
                        break;
                }
                consumedUp = true;
                break;
            case 10:
                switch (mainMenuSubState) {
                    case 0:
                        //main menu
                        switch (cursorPosY) {
                            case 51:
                                if (!Tyre.gameStart) {
                                    cursorPosY -= 12;
                                }
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            case 63:
                                cursorPosY -= 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            case 75:
                                cursorPosY -= 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            case 87:
                                cursorPosY -= 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            default:
                                PlaySystemSound(Meta.SCROLL_LIMIT);
                                break;
                        }
                        break;
                    case 1:
                        //new game verify
                        switch (cursorPosY) {
                            case 51:
                                cursorPosY -= 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            default:
                                PlaySystemSound(Meta.SCROLL_LIMIT);
                                break;
                        }
                        break;
                    case 2:
                        //settings
                        switch (cursorPosY) {
                            case 51:
                                cursorPosY -= 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            case 63:
                                cursorPosY -= 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            case 75:
                                cursorPosY -= 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            default:
                                PlaySystemSound(Meta.SCROLL_LIMIT);
                                break;
                        }
                        break;
                    case 5:
                        //name character
                        switch (cursorIndY) {
                            case 1:
                                cursorIndY--;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            default:
                                PlaySystemSound(Meta.SCROLL_LIMIT);
                                break;
                        }
                        break;
                    case 7:
                        //import verify
                        switch (cursorPosY) {
                            case 51:
                                cursorPosY -= 12;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                break;
                            default:
                                PlaySystemSound(Meta.SCROLL_LIMIT);
                                break;
                        }
                        break;
                }
                consumedDown = true;
                break;
        }
    }

    public void PlaySystemSound(int id) {
        if (hasSound) {
            Meta.unmuteID(id);
            Meta.play(id);
            Meta.muteID(id);
        }
    }

    private void handleEventWidgetDownLeft() {
        switch (gameState) {
            case 0:
                switch (gameSubState) {
                    case 0:
                        if (transLeft < 1) {
                            Tyre.player.walk = true;
                            Tyre.player.dir = 3;
                            if (!Tyre.player.walk) {
                                Tyre.player.walkStart = count;
                            }
                        }
                        break;
                    case 1:
                        //todoleft
                        if (seli > 0) {
                            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                            seli--;
                        } else {
                            PlaySystemSound(Meta.SCROLL_LIMIT);
                        }
                        break;
                    case 4:
                        //inventory
                        scrollItemInventoryLeft();
                        break;
                    case 6:
                        //herb shop
                        switch (herbShopSubState) {
                            //buy
                            case 1:
                                scrollItemInventoryLeft();
                                break;
                            //sell
                            case 2:
                                scrollItemInventoryLeft();
                                break;
                            default:
                                break;
                        }
                        break;
                }
                consumedDown = true;
                break;
            case 3:
                switch (battleModeState) {
                    case 2:
                        if (selectEnemy) {
                            switch (selectEnemyPosX) {
                                case 82:
                                    if (enemies[0] != null && enemies[0].isDead != true) {
                                        selectEnemyPosX -= 80;
                                        selectedEnemyPos--;
                                        PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                    }
                                    break;
                                case 162:
                                    if (enemies[1] != null && enemies[1].isDead != true) {
                                        selectEnemyPosX -= 80;
                                        selectedEnemyPos--;
                                        PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                    } else if (enemies[0] != null && enemies[0].isDead != true) {
                                        selectEnemyPosX -= 160;
                                        selectedEnemyPos -= 2;
                                        PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                    }
                                    break;
                                default:
                                    PlaySystemSound(Meta.SCROLL_LIMIT);
                                    break;
                            };
                        } else {
                            //player's turn, not selecting enemy so move cursor
                            if ((battleModeState == 2) && (seli > 0)) {
                                seli--;
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                            } else {
                                PlaySystemSound(Meta.SCROLL_LIMIT);
                            }
                        }
                        break;
                    case 7:
                        //inventory
                        scrollItemInventoryLeft();
                        break;
                }
                consumedDown = true;
                break;
            case 10:
                switch (mainMenuSubState) {
                    case 3:
                        //help
                        if (helpPageIndex > 0) {
                            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                            helpPageIndex--;
                        } else {
                            PlaySystemSound(Meta.SCROLL_LIMIT);
                        }
                        break;
                    case 5:
                        if (cursorIndX > 0) {
                            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                            cursorIndX--;
                            tmpStr2 = "R:"; //"uF00C";
                        } else {
                            PlaySystemSound(Meta.SCROLL_LIMIT);
                        }
                        Tyre.charselector.loadCharData(cursorIndX);
                        if (tmpStr.equals(ResourceContainer.strings[Constants.FEMALE_PLAYER_NAME_INDEX])) {
                            ResourceContainer.strings[Constants.PLAYER_NAME_INDEX] = ResourceContainer.strings[Constants.MALE_PLAYER_NAME_INDEX];
                            tmpStr = ResourceContainer.strings[Constants.PLAYER_NAME_INDEX];
                        }
                        break;
                    //settings
                    case 2:
                        switch (cursorPosY) {
                            case 39:
                                if (o == 155) {
                                    o = 130;
                                    PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                }
                                break;
                            case 51:
                                if (m == 155) {
                                    m = 130;
                                    PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                                }
                                break;
                            default:
                                PlaySystemSound(Meta.SCROLL_LIMIT);
                                break;
                        }
                        break;
                }
                break;
        }
    }

    private void handleEventWidgetDownWheelClick() {
        switch (gameState) {
            case 0:
                switch (gameSubState) {
                    case 0:
                        changeState(0, 1);
                        seli = 2;
                        PlaySystemSound(Meta.MENU_ACTIVATION);
                        break;
                    case 1:
                        switch (seli) {
                            case 0:
                                //read
                                handleRead();
                                PlaySystemSound(Meta.MENU_ACTIVATION);
                                break;
                            case 1:
                                //talk
                                //new conversation so reset multipart flags
                                handleTalk();
                                PlaySystemSound(Meta.MENU_ACTIVATION);
                                break;
                            case 2:
                                //item
                                formatItemInventory();
                                gameSubState = 4;
                                PlaySystemSound(Meta.MENU_ACTIVATION);
                                break;
                            case 3:
                                //stats
                                formatPlayerStats();
                                gameSubState = 3;
                                PlaySystemSound(Meta.MENU_ACTIVATION);
                                break;
                            case 4:
                                //search clicked
                                handleSearch();
                                PlaySystemSound(Meta.MENU_ACTIVATION);
                                break;
                        }
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        //use item
                        if (numItems > 0) {
                            ty = (lrgMessageOffsetY / LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + itemcur_y;
                            j = ((ty * ITEMVIEW_ITEMS_PER_ROW) + itemcur_x);
                            if (tmpBool) {
                                //run item routine
                                playerItem(ResourceContainer.items[Tyre.player.items[j][0]], j, "", "", -1, true, Color.WHITE, -1);
                                PlaySystemSound(Meta.BEEP_COMMAND_ACCEPTED);
                            } else {
                                //sound buzzer
                                if (hasVibration) {
                                    danger.audio.AudioManager.vibrate(100);
                                }
                                PlaySystemSound(Meta.BEEP_COMMAND_REJECTED);
                            }
                        }
                        break;
                    case 5:
                        //story message
                        //check if in conversation already
                        //talk
                        if (inConv) {
                            handleTalkContinue();
                        }
                        break;
                    case 6:
                        //herb shop
                        switch (herbShopSubState) {
                            //buy/sell
                            case 0:
                                switch (cursorPosY) {
                                    //buy
                                    case 45:
                                        //gen buy list
                                        generateShopInventory();
                                        formatShopBuyInventory();
                                        herbShopSubState = 1;
                                        buy = true;
                                        PlaySystemSound(Meta.MENU_ACTIVATION);
                                        break;
                                    //sell
                                    case 55:
                                        //choose sell item
                                        formatItemInventory();
                                        herbShopSubState = 2;
                                        buy = false;
                                        PlaySystemSound(Meta.MENU_ACTIVATION);
                                        break;
                                }
                                break;
                            case 1:
                                handleBuySelection();
                                PlaySystemSound(Meta.MENU_ACTIVATION);
                                break;
                            case 2:
                                handleSellSelection();
                                PlaySystemSound(Meta.MENU_ACTIVATION);
                                break;
                            case 3:
                                handleVerifyTransaction();
                                herbShopSubState = 4;
                                break;
                            case 4:
                                break;
                        }
                        break;
                    case 7:
                        //inn
                        break;
                    /*
                    case 8:
                        //spell shop
                        break;
                    case 9:
                        //use spell
                        break;
                    */
                    //item found dialog
                    case 10:
                        if (itemfound_i < itemfound_t - 1) {
                            itemfound_i++;
                            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                        } else {
                            gameSubState = 0;
                            PlaySystemSound(Meta.MENU_DISMISS);
                        }
                        break;
                    case 11:
                        //toll keeper
                        switch (herbShopSubState) {
                            //buy/sell
                            case 0:
                                switch (cursorPosY) {
                                    //buy
                                    case 45:
                                        if (Tyre.player.goldCoins < tollkeeper_toll) {
                                            resetMessages();
                                            formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], Color.WHITE);
                                            formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_NOTENOUGHGOLD], Color.WHITE);
                                            herbShopSubState = 1;
                                            PlaySystemSound(Meta.BEEP_COMMAND_REJECTED);
                                        } 
                                        //toll not paid
                                        else {
                                            resetMessages();
                                            formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], Color.WHITE);
                                            formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_PAYTOLL], Color.WHITE);
                                            //pay toll and set tollkeeper flag
                                            Tyre.player.goldCoins -= tollkeeper_toll;
                                            if (tollkeeper_flag != -1) {
                                                Logger.wr("FLAGS: TOLL: Index:" + tollkeeper_flag + " = " + true + "< tollkeeper_flag = true >");
                                                ResourceContainer.flags[tollkeeper_flag] = true;
                                            }
                                            herbShopSubState = 1;
                                            PlaySystemSound(Meta.BEEP_COMMAND_ACCEPTED);
                                        } 
                                        //toll paid
                                        break;
                                    //sell
                                    case 55:
                                        resetMessages();
                                        formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], Color.WHITE);
                                        formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_DONTPAYTOLL], Color.WHITE);
                                        herbShopSubState = 1;
                                        break;
                                }
                                break;
                        }
                        //toll keeper
                        break;
                }
                break;
            case 3:
                //STUNNED or ENTRANCED
                if (isEntranced || isStunned) {
                    return;
                }
                switch (battleModeState) {
                    case 2:
                        if (!selectEnemy) {
                            switch (seli) {
                                case 0:
                                    //attack
                                    //check for premature hit during recharge, also set superCharge state
                                    if (!checkActionDuringCharge()) {
                                        break;
                                    }
                                    if (activeEnemyCount() > 1) {
                                        switch (selectEnemyPosX) {
                                            case 2:
                                                if (!enemyIsActive(0)) {
                                                    adjustTarget();
                                                }
                                                break;
                                            case 82:
                                                if (!enemyIsActive(1)) {
                                                    adjustTarget();
                                                }
                                                break;
                                            case 162:
                                                if (!enemyIsActive(2)) {
                                                    adjustTarget();
                                                }
                                                break;
                                        }
                                        //no enemy select, select now
                                        nextBattleModeState = 2;
                                        selectEnemy = true;
                                        usingItemType = 0;
                                    } else {
                                        //code from single enemy attack code block
                                        playerAttack(findFirstActiveEnemy());
                                    }
                                    break;
                                case 3:
                                    //item
                                    formatItemInventory();
                                    battleModeState = 7;
                                    break;
                                case 4:
                                    //check for premature hit during recharge, also set superCharge state
                                    if (!checkActionDuringCharge()) {
                                        break;
                                    }
                                    //run
                                    playerRun();
                                    break;
                            }
                        } else {
                            //0 = normal attack
                            //1 = now use item
                            switch (usingItemType) {
                                case 0:
                                    if (allTargetsSelected) {
                                        playerAttackAll();
                                        allTargetsSelected = false;
                                    }
                                    //disable canselectany
                                    else {
                                        playerAttack(selectedEnemyPos);
                                    }
                                    break;
                                    
                                case 1:
                                    //TODO - CHECK THIS SECTION FOR BALE FIRE ORB - BLIZZARD QUEEN
                                    switch (selectEnemyPosX) {
                                        case 2:
                                            playerItem(ResourceContainer.items[Tyre.player.items[selectedItemIndex][0]], selectedItemIndex, ResourceContainer.strings[ResourceContainer.items[Tyre.player.items[selectedItemIndex][0]].stringIndex], ResourceContainer.strings[enemies[0].stringIndex], 0, false, Color.GRAY8, 0);
                                            break;
                                        case 82:
                                            playerItem(ResourceContainer.items[Tyre.player.items[selectedItemIndex][0]], selectedItemIndex, ResourceContainer.strings[ResourceContainer.items[Tyre.player.items[selectedItemIndex][0]].stringIndex], ResourceContainer.strings[enemies[1].stringIndex], 0, false, Color.GRAY8, 1);
                                            break;
                                        case 162:
                                            playerItem(ResourceContainer.items[Tyre.player.items[selectedItemIndex][0]], selectedItemIndex, ResourceContainer.strings[ResourceContainer.items[Tyre.player.items[selectedItemIndex][0]].stringIndex], ResourceContainer.strings[enemies[2].stringIndex], 0, false, Color.GRAY8, 2);
                                            break;
                                    }
                                    break;
                            }
                        }
                        break;
                    case 7:
                        //use item
                        if (numItems > 0) {
                            ty = (lrgMessageOffsetY / LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + itemcur_y;
                            j = ((ty * ITEMVIEW_ITEMS_PER_ROW) + itemcur_x);

                            if (tmpBool) {
                                //run item routine
                                playerItem(ResourceContainer.items[Tyre.player.items[j][0]], j, "", "", -1, true, Color.WHITE, -1);
                            } else {
                                //sound buzzer
                                if (hasVibration) {
                                    danger.audio.AudioManager.vibrate(100);
                                }
                                PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                            }
                        }
                        break;
                }
                break;
            case 10:
                switch (mainMenuSubState) {
                    case 0:
                        switch (cursorPosY) {
                            case 39:
                                //continue
                                if (!Tyre.gameStart) {
                                    continueGame();
                                }
                                break;
                            case 51:
                                //start new game
                                changeState(10, 1);
                                break;
                            case 63:
                                //settings
                                changeState(10, 2);
                                break;
                            case 75:
                                //help
                                changeState(10, 3);
                                break;
                            case 87:
                                //prologue
                                returnToMenu = true;
                                this.formatPrologueMessage(ResourceContainer.strings[PROLOGUE_STRING_INDEX]);
                                changeState(10, 4);
                                break;
                        }
                        break;
                    case 1:
                        //new game verify
                        switch (cursorPosY) {
                            case 39:
                                u = 10;
                                v = 140;
                                tmpStr2 = "R:"; //"uF00C";
                                changeState(10, 5);
                                break;
                            case 51:
                                changeState(10, 0);
                                break;
                        }
                        break;
                    case 2:
                        //settings
                        switch (cursorPosY) {
                            case 39:
                                if (o == 155) {
                                    o = 130;
                                } else {
                                    o = 155;
                                }
                                break;
                            case 51:
                                if (m == 155) {
                                    m = 130;
                                } else {
                                    m = 155;
                                }
                                break;
                            case 63:
                                ExportGameState();
                                break;
                            case 75:
                                //change state to import game state screen
                                tmpStr = Pasteboard.getString();
                                changeState(10, 6);
                                break;
                        }
                        break;
                    case 3:
                        //help
                        if (helpPageIndex < Constants.TOTAL_HELP_PAGE_INDEXES) {
                            helpPageIndex++;
                            PlaySystemSound(Meta.FEEDBACK_KEY_ONCE);
                        } else {
                            changeState(10, 0);
                        }
                        break;
                    case 5:
                        switch (cursorPosY) {
                            case 63:
                                processEnterName(tmpStr);
                                break;
                        }
                        break;
                    case 6:
                        //import game data
                        if (tmpBool) {
                            changeState(10, 7);
                        }
                        break;
                    case 7:
                        //import game data verification
                        switch (cursorPosY) {
                            case 39:
                                //load data
                                //u = 10;
                                //v = 140;
                                if (DecodeGameState(tmpStr)) {
                                    tmpInt2 = 1;
                                    ResourceContainer.flags = loadFlags;
                                    Tyre.player.items = loadItems;
                                    ResourceContainer.strings[Constants.PLAYER_NAME_INDEX] = loadName;
                                    characterName = loadName;
                                    Tyre.cApp.storeSettings();
                                    Tyre.cApp.readSerializedGameData(loadGS);
                                    Tyre.theSettings.setSerializedGameData(loadGS);
                                    Tyre.cApp.storeSerializedInvData();
                                    //exit battle if exported during it
                                    if (prevGameState == 3) {
                                        prevGameState = 0;
                                    }
                                    changeState(10, 8);
                                } else {
                                    tmpInt2 = 0;
                                    changeState(10, 8);
                                }
                                break;
                            case 51:
                                changeState(10, 0);
                                break;
                        }
                        break;
                }
                break;
        }
        consumedDown = true;
    }

    private void processEnterName(String str) {
        startNewGame();
        if (str.length() > 0) {
            ResourceContainer.strings[Constants.PLAYER_NAME_INDEX] = str;
        } else if (cursorIndX == 0) {
            ResourceContainer.strings[Constants.PLAYER_NAME_INDEX] = ResourceContainer.strings[Constants.MALE_PLAYER_NAME_INDEX];
        } else {
            ResourceContainer.strings[Constants.PLAYER_NAME_INDEX] = ResourceContainer.strings[Constants.FEMALE_PLAYER_NAME_INDEX];
        }
        tmpStr = ResourceContainer.strings[Constants.PLAYER_NAME_INDEX];
        characterName = tmpStr;
    }

    private void processSettings() {
        if (o == 130) {
            hasSound = true;
            TyreAudio.startMusic(Constants.MUSIC_MENU);
        } else {
            hasSound = false;
            TyreAudio.stopMusic();
        }
        if (m == 130) {
            hasVibration = true;
        } else {
            hasVibration = false;
        }
        //this has been commented out since it only saved player name & flags
        //use storeSerializedGameData instead 
        //if values are both set to default bypass storing it
        //if player is in game and changes the settings back to default it will not clear 
        if ((m != 130 || o != 130) || (Tyre.gameStart == false)) {
            Logger.wr("Settings are not default or game is in progress - storing settings m:o:gs " + m + ":" + o + ":" + Tyre.gameStart);
            Tyre.cApp.storeSerializedGameData();
        } else {
            Logger.wr("Settings are default and game is not progress - bypass storing settings m:o:gs " + m + ":" + o + ":" + Tyre.gameStart);
        }
        changeState(10, 0);
    }

    private void startNewGame() {
        ResourceContainer.flags = new boolean[ResourceContainer.flags.length];
        for (i = 0; i < ResourceContainer.flags.length; i++) {
            ResourceContainer.flags[i] = false;
        }

        Tyre.player = new character();
        Tyre.player.loadCharData(cursorIndX);
        Tyre.player.equip_armor(0, -2);
        Tyre.player.equip_weapon(0, -2);
        Tyre.player.setRechargeSpeed();

        isPoisoned = isEntranced = isStunned = false;
        resetBuildFlags();
        Tyre.currentRoomIdx = ResourceContainer.players[cursorIndX].startingRoom;
        if (Tyre.gameStart) {
            Tyre.gameStart = false;
        }
        if (Tyre.firstRun) {
            Tyre.firstRun = false;
        }
        if (Tyre.firstContinue) {
            Tyre.firstContinue = false;
        }
        
        //show prologue
        returnToMenu = false;
        formatPrologueMessage(ResourceContainer.strings[PROLOGUE_STRING_INDEX]);
        changeState(10, 4);
    }

    private void continueGame() {
        //TODO FLUSH BATTLE STATS, CLEAR CHARGEBAR AND SET HP BACK TO NORMAL IF HP=0
        if (Tyre.player.damage >= Tyre.player.hp) {
            resetPlayerHP();
        }

        Tyre.cApp.buildRoom(true);
        if (Tyre.firstRun) {
            Tyre.firstRun = false;
            changeState(0, 0);
        } else {
            //overworld simple message (formatBattleMessage)
            if (prevGameState == 0 && gameSubState == 2) {
                //close existing message
                gameSubState = 0;
            }
            //catch overworld player stats mode
            else if (prevGameState == 0 && gameSubState == 3) {
                formatPlayerStats();
                lrgMessageOffsetY = tmplrgMessageOffsetY;
                lrgMessageScrollOffsetY = tmplrgMessageScrollOffsetY;

            } 
            //catch overworld inventory mode
            else if (prevGameState == 0 && gameSubState == 4) {
                //item
                restoreInventory();
            }
            //catch overworld message popup or conversation
            else if (prevGameState == 0 && gameSubState == 5) {
                //non-conversation
                if (storyType == 0) {
                    //strings
                    if (lastStringArrayUsed == 0) {
                        formatStoryMessage(ResourceContainer.strings[lastStringUsed]);
                    } else {
                        formatStoryMessage(ResourceContainer.consoleStrings[lastStringUsed]);
                    }
                    lrgMessageOffsetY = tmplrgMessageOffsetY;
                    lrgMessageScrollOffsetY = tmplrgMessageScrollOffsetY;

                } else {
                    //close existing convo
                    gameSubState = 0;
                    //uncomment to attempt to restore conversation state
                }
            } 
            //item shop
            else if (prevGameState == 0 && gameSubState == 6) {
                //close existing convo
                gameSubState = 0;
            }
            //items found or battle spoils won
            else if (prevGameState == 0 && gameSubState == 10) {
                //this is alreay handled since itemfound variables are in state datastore
            }
            //tollkeeper dialog
            else if (prevGameState == 0 && gameSubState == 11) {
                //close existing convo
                gameSubState = 0;
            }
            //battle mode, rebuild enemy array and restore battle state
            else if (prevGameState == 3) {
                TyreAudio.startMusic(battleSong);
                if (Tyre.firstContinue) {
                    Logger.wr("calling restoreBattle");
                    restoreBattle();
                    Tyre.firstContinue = false;
                } else {
                    Logger.wr("bypassing restoreBattle");
                }
                renderLayerOneFlag = true;
                if (battleModeState == 7) {
                    //item
                    restoreInventory();
                }
            }

            changeState(prevGameState);
            //todo override
            if (gameState == 3) {
                //in battle override
                Logger.wr("BATTLE OVERRIDE - changing prevGameState " + prevGameState + " -> 3");
                prevGameState = 3;
            }
        }
    }

    protected void onActivate() {
        switch (gameState) {
            case 8:
                if (Tyre.datLoader == null || datKilled == true) {
                    datKilled = false;
                    changeState(8);
                    Tyre.cApp.loadData();
                }
                break;
            case 10:
                break;
            default:
                changeState(prevGameState);
                break;
        }
        TyreAudio.resume();
        resetBuildFlags();
        startTimer();
    }

    public void startTimer() {
        if (Tyre.mTimer != null) {
            Tyre.mTimer.start();
        } else {
            Tyre.mTimer = new Timer(30, true, this);
            Tyre.mTimer.start();
        }
    }

    public void stopTimer() {
        if (Tyre.mTimer != null) {
            Tyre.mTimer.stop();
        }
    }

    protected void onDeactivate() {
        switch (gameState) {
            case 8:
                if (datKilled == false) {
                    datKilled = true;
                    Tyre.cApp.killDatLoad();
                }
                break;                
            case 10:
                break;                
            case 11:
                break;                
            default:
                renderLayerOneFlag = renderLayerTwoFlag = true;
                changeState(1);
                break;
        }
        TyreAudio.pause();
        stopTimer();
        invalidate();
    }

    public boolean eventKeyDown(char c, Event event) {
        switch (event.type) {
            case -6:
                if ((int) c == 13 || (int) c == 10) {
                    handleEventWidgetDownWheelClick();
                } else {
                    if ((gameState == 10 || gameState == 11) && mainMenuSubState == 5) {
                        if (((int) c == 8) && tmpStr.length() > 0) {
                            tmpStr = tmpStr.substring(0, tmpStr.length() - 1);
                        } else if (tmpStr.length() < 10) {
                            j = (int) c;
                            if ((j >= 65 && j <= 90) || (j >= 97 && j <= 122)) {
                                tmpStr += c;
                            }
                            //not valid name letter
                            else {
                                if (hasVibration) {
                                    danger.audio.AudioManager.vibrate(100);
                                }
                                if (hasSound) {
                                    Meta.unmuteID(Meta.BEEP_COMMAND_REJECTED);
                                    Meta.play(Meta.BEEP_COMMAND_REJECTED);
                                    Meta.muteID(Meta.BEEP_COMMAND_REJECTED);
                                }
                            }
                        }
                        //past maximum letters
                        else {
                            if (hasVibration) {
                                danger.audio.AudioManager.vibrate(100);
                            }
                            if (hasSound) {
                                Meta.unmuteID(Meta.BEEP_COMMAND_REJECTED);
                                Meta.play(Meta.BEEP_COMMAND_REJECTED);
                                Meta.muteID(Meta.BEEP_COMMAND_REJECTED);
                            }
                        }
                    } else {
                        checkCheatCodes(c);
                    }
                }
                break;
        }

        if (expectingFlagIndex && java.lang.Character.isDigit(c)) {
            expectedFlagIndex += java.lang.Character.toString(c);
            return true;
        }

        if (expectingRoomIndex && java.lang.Character.isDigit(c)) {
            expectedRoomIndex += java.lang.Character.toString(c);
            return true;
        }
        
        /*
        if(c == 'D') {
            System.out.println("CLEARING DS ");
            Tyre.cApp.theSettings.clearAllSettingsDB();
        }
        */
        
        if (c == '=') {
            //set room index and player position
            if (ResourceContainer.flags[MAGICAL_WARP_FLAG_ID]) {
                if (expectingRoomIndex) {
                    try {
                        int index = Integer.parseInt(expectedRoomIndex);
                        int t2 = -1;
                        switch (index) {
                            case 1:
                                t2 = 184;
                                Tyre.player.posX = 32;
                                Tyre.player.posY = 24;
                                break;
                            case 2:
                                t2 = 106;
                                Tyre.player.posX = 64;
                                Tyre.player.posY = 72;
                                break;
                            case 3:
                                t2 = 82;
                                Tyre.player.posX = 200;
                                Tyre.player.posY = 24;
                                break;
                            case 4:
                                t2 = 72;
                                Tyre.player.posX = 40;
                                Tyre.player.posY = 24;
                                break;
                            case 5:
                                t2 = 207;
                                Tyre.player.posX = 24;
                                Tyre.player.posY = 40;
                                break;
                            case 6:
                                //tomb
                                t2 = 332;
                                Tyre.player.posX = 160;
                                Tyre.player.posY = 32;
                                break;
                            case 7:
                                //majik werks island
                                t2 = 51;
                                Tyre.player.posX = 192;
                                Tyre.player.posY = 32;
                                break;
                            case 8:
                                //hidden island
                                t2 = 95;
                                Tyre.player.posX = 160;
                                Tyre.player.posY = 64;
                                break;
                            case 102:
                                //Majikwerks Building 102
                                t2 = 8;
                                Tyre.player.posX = 96;
                                Tyre.player.posY = 72;
                                break;
                            case 103:
                                //Majikwerks Building 103
                                t2 = 9;
                                Tyre.player.posX = 96;
                                Tyre.player.posY = 72;
                                break;
                            case 666:
                                //final battle
                                t2 = 124;
                                Tyre.player.posX = 192;
                                Tyre.player.posY = 64;
                                break;
                            case 91:
                                //item shop room50
                                t2 = 228;
                                Tyre.player.posX = 112;
                                Tyre.player.posY = 80;
                                break;
                            case 92:
                                //item shop room60
                                t2 = 446;
                                Tyre.player.posX = 112;
                                Tyre.player.posY = 80;
                                break;
                            case 93:
                                //item shop room70
                                t2 = 317;
                                Tyre.player.posX = 112;
                                Tyre.player.posY = 80;
                                break;
                            case 94:
                                //item shop room196
                                t2 = 242;
                                Tyre.player.posX = 112;
                                Tyre.player.posY = 80;
                                break;
                        }
                        if (t2 != -1) {
                            Tyre.currentRoomIdx = t2;
                            transitionRoom();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    expectedRoomIndex = "";
                }
                expectingRoomIndex = !expectingRoomIndex;
            }
        }
        return true;
    }

    public boolean eventKeyUp(char c, Event event) {
        switch (event.type) {
            case -5:
                if ((int) c == 13 || (int) c == 10) {
                }
                break;
        }
        return true;
    }

    @Override
    public boolean eventWidgetDown(int inWhichWidget, Event inEvent) {
        consumedDown = false;
        switch (inWhichWidget) {
            case Event.DEVICE_BUTTON_MENU:
                break;
            case Event.DEVICE_BUTTON_JUMP:
                switch (gameState) {
                    case 8:
                        datKilled = true;
                        Tyre.cApp.killDatLoad();
                        break;
                }
                break;
            case Event.DEVICE_BUTTON_CANCEL:
                handleEventWidgetDownCancel();
                break;
            case Event.DEVICE_BUTTON_BACK:
                handleEventWidgetDownBack();
                break;
            case Event.DEVICE_WHEEL_PAGE_DOWN:
            case Event.DEVICE_WHEEL:
                //handle scroll down with wheel
                if (gameState == 0 && gameSubState == 0) {
                    break;
                }
            case Event.DEVICE_ARROW_DOWN:
                //directional down
                handleEventWidgetDownDown();
                break;
            case Event.DEVICE_ARROW_RIGHT:
                //directional right
                handleEventWidgetDownRight();
                break;
            case Event.DEVICE_ARROW_UP:
                //directional up
                handleEventWidgetDownUp();
                break;
            case Event.DEVICE_ARROW_LEFT:
                //directional left
                handleEventWidgetDownLeft();
                break;
        }
        if (consumedDown) {
            return true;
        } else {
            aboutToShowMenu();
            return super.eventWidgetDown(inWhichWidget, inEvent);
        }
    }
    
    public void handleSellSelection() {
        ty = (lrgMessageOffsetY / LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + itemcur_y;
        j = ((ty * ITEMVIEW_ITEMS_PER_ROW) + itemcur_x);
        selectedItemIndex = j;
        selectedItemName = ResourceContainer.strings[ResourceContainer.items[Tyre.player.items[selectedItemIndex][0]].stringIndex];
        resetMessages();
        cost = ResourceContainer.items[Tyre.player.items[selectedItemIndex][0]].cost;

        if (cost == 0) {
            formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_MAINMENUMSG], Color.WHITE);
            formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CANTSELLQUESTITEM], Color.WHITE);
            herbShopSubState = 4;
        } else {
            formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_MAINMENUMSG] + "  " + ResourceContainer.consoleStrings[Constants.CONSTR_CONTINUEMSG], Color.WHITE);
            formatBattleMessage(selectedItemName + ResourceContainer.consoleStrings[Constants.CONSTR_WOULDYOULIKETOSELL], Color.WHITE);
            maxQuantity = Tyre.player.items[selectedItemIndex][1];
            minQuantity = 1;
            currentQuantity = 1;
            herbShopSubState = 3;
        }
    }

    public void handleVerifyTransaction() {
        resetMessages();
        if (buy) {
            if (Tyre.player.goldCoins < (currentQuantity * cost)) {
                formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_MAINMENUMSG], Color.WHITE);
                formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_NOTENOUGHGOLD], Color.WHITE);
                PlaySystemSound(Meta.BEEP_COMMAND_REJECTED);
            } else {
                if (checkBuyItem(shopItems[selectedItemIndex], currentQuantity)) {
                    storeItem(shopItems[selectedItemIndex], currentQuantity);
                    //display success message
                    Tyre.player.goldCoins -= (currentQuantity * cost);
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_MAINMENUMSG], Color.WHITE);
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_YOUHAVEBOUGHT] + currentQuantity + " " + selectedItemName + ResourceContainer.consoleStrings[Constants.CONSTR_SFOR] + (currentQuantity * cost) + ResourceContainer.consoleStrings[Constants.CONSTR_GOLDPERIOD], Color.WHITE);
                    PlaySystemSound(Meta.BEEP_COMMAND_ACCEPTED);
                } else {
                    //display failure message
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_MAINMENUMSG], Color.WHITE);
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CANTSTORE] + selectedItemName + ResourceContainer.consoleStrings[Constants.CONSTR_S], Color.WHITE);
                    PlaySystemSound(Meta.BEEP_COMMAND_REJECTED);
                }
            }
        } else {
            //sell
            Tyre.player.goldCoins += (currentQuantity * cost);
            Tyre.player.items[selectedItemIndex][1] -= currentQuantity;
            if (Tyre.player.items[selectedItemIndex][1] == 0) {
                adjustInventory(selectedItemIndex);
            }
            formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_MAINMENUMSG], Color.WHITE);
            formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_YOUHAVESOLD] + currentQuantity + " " + selectedItemName + ResourceContainer.consoleStrings[Constants.CONSTR_SFOR] + (currentQuantity * cost) + ResourceContainer.consoleStrings[Constants.CONSTR_GOLDPERIOD], Color.WHITE);
            PlaySystemSound(Meta.BEEP_COMMAND_ACCEPTED);
        }
        
        //clean up
        selectedItemIndex = 0;
        selectedItemName = "";
        cost = 0;
        currentQuantity = 0;
        minQuantity = 0;
        maxQuantity = 0;
    }

    //inventory size = 25
    public void adjustInventory(int m) {
        for (j = (m + 1); j < Constants.ITEM_INVENTORY_SIZE; j++) {
            Tyre.player.items[(j - 1)][0] = Tyre.player.items[j][0];
            Tyre.player.items[(j - 1)][1] = Tyre.player.items[j][1];
            if (j == (Constants.ITEM_INVENTORY_SIZE - 1)) {
                Tyre.player.items[j][0] = -1;
                Tyre.player.items[j][1] = -1;
            }
        }
    }

    public int currentItemCount() {
        k = 0;
        for (j = 0; j < Constants.ITEM_INVENTORY_SIZE; j++) {
            if (Tyre.player.items[j][1] > 0) {
                k++;
            }
        }
        return k;
    }

    public void handleBuySelection() {
        ty = (lrgMessageOffsetY / LRGMESSAGEITEMCONTENT_ROW_HEIGHT) + itemcur_y;
        j = ((ty * ITEMVIEW_ITEMS_PER_ROW) + itemcur_x);
        selectedItemIndex = j;
        selectedItemName = ResourceContainer.strings[ResourceContainer.items[shopItems[selectedItemIndex]].stringIndex];

        resetMessages();
        formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_MAINMENUMSG] + "  " + ResourceContainer.consoleStrings[Constants.CONSTR_CONTINUEMSG], Color.WHITE);
        formatBattleMessage(selectedItemName + ResourceContainer.consoleStrings[Constants.CONSTR_WOULDYOULIKETOBUY], Color.WHITE);

        maxQuantity = Constants.MAX_ITEM_QTY;
        minQuantity = 1;
        currentQuantity = 1;
        cost = ResourceContainer.items[shopItems[selectedItemIndex]].cost;
        herbShopSubState = 3;
    }

    public void handleItemRequest(item itm, String from, String to, int inventoryIndex, boolean inResetMessages, int color, int enm) {
        if (inResetMessages) {
            resetMessages();
        }
        finishItemOverride = false;

        switch (itm.type) {
            case 0:
                //player hp
                k = calcDiceRoll(itm.ap, 0, 0);
                effectHealPlayerHP(-k);
                if (inResetMessages) {
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], color);
                    formatBattleMessage(ResourceContainer.strings[Constants.PLAYER_NAME_INDEX] + ResourceContainer.consoleStrings[Constants.CONSTR_HASGAINED] + k + ResourceContainer.consoleStrings[Constants.CONSTR_HPPERIOD] + " " + ResourceContainer.strings[Constants.PLAYER_NAME_INDEX] + ResourceContainer.consoleStrings[Constants.CONSTR_NOWHAS] + (Tyre.player.hp - Tyre.player.damage) + "/" + Tyre.player.hp + ".", color);
                } else {
                    formatBattleMessage(ResourceContainer.strings[Constants.PLAYER_NAME_INDEX] + ResourceContainer.consoleStrings[Constants.CONSTR_HASGAINED] + k + ResourceContainer.consoleStrings[Constants.CONSTR_HPPERIOD] + " " + ResourceContainer.strings[Constants.PLAYER_NAME_INDEX] + ResourceContainer.consoleStrings[Constants.CONSTR_NOWHAS] + (Tyre.player.hp - Tyre.player.damage) + "/" + Tyre.player.hp + ".", color);
                }
                PlaySystemSound(Meta.CHARGER_DISCONNECTED);
                break;
            case 11:
                //player hp full restore
                k = Tyre.player.damage;
                effectHealPlayerHP(-k);
                if (inResetMessages) {
                    resetMessages();
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], color);
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_HPRESTORED] + ResourceContainer.strings[Constants.PLAYER_NAME_INDEX] + ResourceContainer.consoleStrings[Constants.CONSTR_NOWHAS] + (Tyre.player.hp - Tyre.player.damage) + "/" + Tyre.player.hp + ".", color);
                } else {
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_HPRESTORED] + ResourceContainer.strings[Constants.PLAYER_NAME_INDEX] + ResourceContainer.consoleStrings[Constants.CONSTR_NOWHAS] + (Tyre.player.hp - Tyre.player.damage) + "/" + Tyre.player.hp + ".", color);
                }
                PlaySystemSound(Meta.CHARGER_DISCONNECTED);
                break;
            case 9:
                //poison antidote
                if (isPoisoned) {
                    resetMessages();
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], color);
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_PLAYERCUREPOISON], color);
                    isPoisoned = false;
                    Tyre.player.setRechargeSpeed();
                } else {
                    resetMessages();
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], color);
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_PLAYERNOTPOISONED], color);
                    return;
                }
                PlaySystemSound(Meta.CHARGER_DISCONNECTED);
                break;
            case 10:
                //herbal antisleep
                if (gameState != 3) {
                    resetMessages();
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], color);
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_ANTISLEEPINGEST], color);
                    Tyre.player.antisleep = true;
                    antisleepCount = 3;
                } else {
                    Tyre.player.antisleep = true;
                    antisleepCount = 3;
                }
                PlaySystemSound(Meta.CHARGER_DISCONNECTED);
                break;
            case 12:
                //readable
                storyType = 0;
                lastStringUsed = itm.readStringsIndex;
                lastStringArrayUsed = 0;
                formatStoryMessage(ResourceContainer.strings[itm.readStringsIndex]);
                if (itm.flagsIndex > -1) {
                    Logger.wr("FLAGS: READABLE: Index:" + itm.flagsIndex + " = " + true + "< itm.flagsIndex = true >");
                    ResourceContainer.flags[itm.flagsIndex] = true;
                }
                gameState = 0;
                gameSubState = 5;
                
                //return now so item is not consumed
                PlaySystemSound(Meta.MENU_ACTIVATION);
                return;
            case Constants.ITEMTYPE_SPECIALCOMBAT:
                //special combat item
                if (gameState != 3) {
                    resetMessages();
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], color);
                    formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_SAVEFORCOMBAT], color);
                } else {
                    //check here for specific item type and its unique usage case
                    if ((Tyre.player.items[inventoryIndex][0] == Constants.ITEM_BALEFIREORB) && isBoss && (currentBossId == Constants.BOSS_BLIZZARDQUEEN) && enemies[currentBossPos].damage >= enemies[currentBossPos].hp) {
                        enemies[currentBossPos].isDead = true;
                        adjustTarget();
                        Tyre.player.battleKills++;
                        effectEnemyHP(enemies[currentBossPos], 9999);
                        //todo discard
                        discardItem(Tyre.player.inInventory(Constants.ITEM_BALEFIREORB), 1);
                        finishItemOverride = true;
                    } else if ((Tyre.player.items[inventoryIndex][0] == Constants.ITEM_BLIZZARD_STAFF) && isBoss && (currentBossBattleId == Constants.BATTLE_FINAL)) {
                        for (int i = 0; i < ENEMYWAIT_TIME.length; i++) {
                            ENEMYWAIT_TIME[i] = (int) (Constants.FINALBOSS_SLOWSPEED * Tyre.speedMultiplier);
                        }
                    } else {
                        newStatusMsg(76, 97, 0xFF0000, ResourceContainer.consoleStrings[Constants.CONSTR_NOTHINGHAPPENS], 30);
                    }
                }
                //return now so item is not consumed
                return;
        }

        //deduct one usage and shift up items
        discardItem(inventoryIndex, 1);
    }

    public void startBattle(int batlIdx, int flagIndex, int flagIndex1, int flagIndex2, int searchId) {
        isBoss = true;
        battleSong = Constants.MUSIC_BOSSMODE;
        isEntrancedCount = isStunnedCount = 0;

        //bug fix, clear boss battle status before each battle
        currentBossId = -1;
        currentBossBattleId = -1;
        currentBossPos = -1;
        battleFlagIndex = flagIndex;
        battleFlagIndex1 = flagIndex1;
        battleFlagIndex2 = flagIndex2;
        battleSearchIndex = searchId;
        p = 0;
        
        if (ResourceContainer.battles[batlIdx].enemyId1 != 255) {
            p++;
        }
        if (ResourceContainer.battles[batlIdx].bossId != 255) {
            p++;
        }
        if (ResourceContainer.battles[batlIdx].enemyId2 != 255) {
            p++;
        }
        if (ResourceContainer.battles[batlIdx].enemyId3 != 255) {
            p++;
        }
        tmpInt = 0;

        //prep-final battle
        if (batlIdx == Constants.BATTLE_FINAL) {
            enemies = new enemy[3];
            enemies[tmpInt] = Tyre.cApp.copyEnemy(ResourceContainer.bosses[Constants.BATTLE_FINAL_BOSS1]);
            enemy_index[tmpInt][0] = -1;
            enemy_index[tmpInt][1] = Constants.BATTLE_FINAL_BOSS1;
            prepareBattleGold(tmpInt);
            tmpInt++;

            enemies[tmpInt] = Tyre.cApp.copyEnemy(ResourceContainer.bosses[Constants.BATTLE_FINAL_BOSS2]);
            enemy_index[tmpInt][0] = -1;
            enemy_index[tmpInt][1] = Constants.BATTLE_FINAL_BOSS2;
            currentBossId = Constants.BATTLE_FINAL_BOSS2;
            currentBossBattleId = batlIdx;
            currentBossPos = tmpInt;
            
            //HACK (test boss mode)
            //enemies[tmpInt].hp = 2;
            
            prepareBattleGold(tmpInt);
            tmpInt++;
            enemies[tmpInt] = Tyre.cApp.copyEnemy(ResourceContainer.bosses[Constants.BATTLE_FINAL_BOSS3]);
            enemy_index[tmpInt][0] = -1;
            enemy_index[tmpInt][1] = Constants.BATTLE_FINAL_BOSS3;
            prepareBattleGold(tmpInt);
            tmpInt++;
            numEnemies = 3;
            prepBattle(p);
            return;
        }

        if (p == 4) {
            p = 3;
        }
        numEnemies = p;
        enemies = new enemy[p];

        if (ResourceContainer.battles[batlIdx].enemyId1 != 255) {
            enemies[tmpInt] = Tyre.cApp.copyEnemy(ResourceContainer.enemies[ResourceContainer.battles[batlIdx].enemyDim1][ResourceContainer.battles[batlIdx].enemyId1]);
            enemy_index[tmpInt][0] = ResourceContainer.battles[batlIdx].enemyDim1;
            enemy_index[tmpInt][1] = ResourceContainer.battles[batlIdx].enemyId1;
            prepareBattleGold(tmpInt);
            tmpInt++;
        }
        if (ResourceContainer.battles[batlIdx].bossId != 255) {
            enemies[tmpInt] = Tyre.cApp.copyEnemy(ResourceContainer.bosses[ResourceContainer.battles[batlIdx].bossId]);
            enemy_index[tmpInt][0] = -1;
            enemy_index[tmpInt][1] = ResourceContainer.battles[batlIdx].bossId;
            currentBossId = ResourceContainer.battles[batlIdx].bossId;
            currentBossBattleId = batlIdx;
            currentBossPos = tmpInt;
            
            //HACK (test boss mode)
            //enemies[tmpInt].hp = 2;
            
            prepareBattleGold(tmpInt);
            tmpInt++;
        } else {
            currentBossId = -1;
        }

        if (ResourceContainer.battles[batlIdx].enemyId2 != 255) {
            enemies[tmpInt] = Tyre.cApp.copyEnemy(ResourceContainer.enemies[ResourceContainer.battles[batlIdx].enemyDim2][ResourceContainer.battles[batlIdx].enemyId2]);
            enemy_index[tmpInt][0] = ResourceContainer.battles[batlIdx].enemyDim2;
            enemy_index[tmpInt][1] = ResourceContainer.battles[batlIdx].enemyId2;
            prepareBattleGold(tmpInt);
            tmpInt++;
        }
        if (ResourceContainer.battles[batlIdx].enemyId3 != 255) {
            enemies[tmpInt] = Tyre.cApp.copyEnemy(ResourceContainer.enemies[ResourceContainer.battles[batlIdx].enemyDim3][ResourceContainer.battles[batlIdx].enemyId3]);
            enemy_index[tmpInt][0] = ResourceContainer.battles[batlIdx].enemyDim3;
            enemy_index[tmpInt][1] = ResourceContainer.battles[batlIdx].enemyId3;
            prepareBattleGold(tmpInt);
            tmpInt++;
        }
        prepBattle(p);
    }

    public void restoreInventory() {
        formatItemInventory();
        lrgMessageOffsetY = tmplrgMessageOffsetY;
        lrgMessageScrollOffsetY = tmplrgMessageScrollOffsetY;
        itemcur_x = tmpitemcur_x;
        itemcur_y = tmpitemcur_y;
        item_maxu = tmpitem_maxu;
        item_maxv = tmpitem_maxv;
        selItem = tmpselItem;
        itemCursorPos = tmpitemCursorPos;
        updateItemInventoryTarget();
    }

    public static void restoreBattle() {
        enemies = new enemy[numEnemies];
        for (tmpInt = 0; tmpInt < numEnemies; tmpInt++) {
            if (enemy_index[tmpInt][0] == -1) {
                //boss
                enemies[tmpInt] = Tyre.cApp.copyEnemy(ResourceContainer.bosses[(enemy_index[tmpInt][1])]);
            } else {
                enemies[tmpInt] = Tyre.cApp.copyEnemy(ResourceContainer.enemies[(enemy_index[tmpInt][0])][(enemy_index[tmpInt][1])]);
            }
            enemies[tmpInt].cooldown = enemy_cooldown[tmpInt][0];
            ENEMYWAIT_TIME[tmpInt] = enemy_cooldown[tmpInt][1];
            enemies[tmpInt].hp = enemy_hp[tmpInt];

            if (Tyre.intToBoolean(enemy_isdead[tmpInt])) {
                enemies[tmpInt] = null;
            }
        }
        timestamp = count;
    }

    public static void prepareBattleGold(int index) {
        if (!isBoss) {
            if (enemies[index].goldCoinsMax != 0) {
                gold += rand.nextInt(enemies[index].goldCoinsMax);
                gold++;
            }
        } else {
            gold += enemies[index].goldCoinsMax;
        }
    }

    public void resetMessages() {
        messages = new message[4];
        for (i = 0; i < 4; i++) {
            messages[i] = new message();
        }
    }

    public boolean enemyIsActive(int i) {
        try {
            if (enemies[i] == null) {
                return false;
            } else if (enemies[i] != null && !enemies[i].isDead) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int activeEnemyCount() {
        l = 0;
        for (m = 0; m < enemies.length; m++) {
            if (enemies[m] != null && !enemies[m].isDead) {
                l++;
            }
        }
        return l;
    }

    public int findFirstActiveEnemy() {
        for (k = 0; k < enemies.length; k++) {
            if (enemies[k] != null && !enemies[k].isDead) {
                break;
            }
        }
        return k;
    }

    public int findWeakestActiveEnemy() {
        l = -1;
        m = 0;
        for (k = 0; k < enemies.length; k++) {
            if (enemies[k] != null && !enemies[k].isDead) {
                if ((l == -1) || ((enemies[k].hp - enemies[k].damage) < l)) {
                    l = (enemies[k].hp - enemies[k].damage);
                    m = k;
                }
            }
        }
        return m;
    }

    public int findFastestActiveEnemy() {
        l = 0;
        m = 0;
        for (k = 0; k < enemies.length; k++) {
            if (enemies[k] != null && !enemies[k].isDead) {
                if (enemies[k].speed > l) {
                    l = enemies[k].speed;
                    m = k;
                }
            }
        }
        return m;
    }

    private void effectEnemyHP(enemy enm, int amount) {
        effectEnemyHP(enm, amount, 0x0000ff, 0, amount);
    }

    private void effectHealEnemyHP(enemy enm, int amount) {
        effectEnemyHP(enm, amount, 0x00cc00, 1, -amount);
    }

    private void effectEnemyHP(enemy enm, int amount, int col, int type, int display_amount) {
        //CHECK FOR 0 HP TO USE SPECIAL COMBAT ITEM USAGE
        //enemy already has 0 HP - set dmg specifically to 0 as clue to player
        if (enm.damage >= enm.hp) {
            amount = 0;
            display_amount = 0;
        } else if ((enm.hp - (enm.damage + amount)) < 0) {
            enm.damage = enm.hp;
        } else if (enm.damage + amount < 0) {
            enm.damage = 0;
        } else {
            enm.damage += amount;
        }
        enm.isHit = true;
        enm.hitTime = count;
        if ((enm.hp - enm.damage) <= 0) {
            /*
            if (isBoss && enm == enemies[currentBossPos] && currentBossId == BOSS_FINALBOSS) {
                //HACK (test end game)
                //if (true) {
                enm.isDead = true;
                adjustTarget();
                Tyre.player.battleKills++;
                changeState(gameState, 12);
                
                //timestamp = 0;
                //formatEndGameMessage("Your sword strikes a fatal blow! The game is over. You have won the game. Vic will now tell you what you have won. Congratulations.");
                //changeState(10, 4);
            } else 
            */
            //if (isBoss && enm == enemies[currentBossPos] && currentBossId == BOSS_BLIZZARDQUEEN) {
            //check if battle is against blizzard queen. If so don't kill her without the orb
            
            if (currentBossId == Constants.BOSS_BLIZZARDQUEEN) {
                if (isBoss && enm == enemies[currentBossPos]) {
                    enm.damage = enm.hp;
                }
            } else {
                enm.isDead = true;
                adjustTarget();
                Tyre.player.battleKills++;
            }
        }

        if (enemies.length == 1) {
            newFloatingHeart(82 + 33, 55, col, display_amount, type);
        } else {
            for (j = 0; j < enemies.length; j++) {
                if (enm == enemies[j]) {
                    newFloatingHeart((j * 80) + 33, 55, col, display_amount, type);
                }
            }
        }
    }

    private void effectPlayerHP(int amount) {
        effectPlayerHP(amount, 0xff0000, amount, -1);
    }

    private void effectHealPlayerHP(int amount) {
        effectPlayerHP(amount, 0x00cc00, -amount, 2);
    }

    private void effectPlayerHP(int amount, int col, int display_amount, int type) {
        if ((Tyre.player.hp - (Tyre.player.damage + amount)) < 0) {
            Tyre.player.damage = Tyre.player.hp;
        } else if (Tyre.player.damage + amount < 0) {
            Tyre.player.damage = 0;
        } else {
            Tyre.player.damage += amount;
        }
        //disable
        if (type > -1) {
            newFloatingHeart(82 + 36, 100, col, display_amount, type);
        }
    }

    private void effectPlayerMP(int amount) {
        if ((Tyre.player.mp - (Tyre.player.mpUsage + amount)) < 0) {
            Tyre.player.mpUsage = Tyre.player.mp;
        } else if (Tyre.player.mpUsage + amount < 0) {
            Tyre.player.mpUsage = 0;
        } else {
            Tyre.player.mpUsage += amount;
        }
    }

    private void effectPlayerSpeed(int amount) {
        Tyre.player.speedBonus += amount;
    }

    private void effectPlayerAP(int amount) {
        Tyre.player.attackBonus += amount;
    }

    private void effectPlayerDP(int amount) {
        Tyre.player.defenseBonus += amount;
    }

    private void playerItem(item itm, int inventoryIndex, String from, String to, int target, boolean resetMessages, int color, int enm) {
        Tyre.player.battleItems++;
        switch (target) {
            case -1:
                //player items
                handleItemRequest(itm, from, to, inventoryIndex, resetMessages, color, -1);
                if (finishItemOverride) {
                    playerFinishAttack();
                } else {
                    playerFinishItem();
                }
                break;
            default:
                //enemy items
                handleItemRequest(itm, from, to, inventoryIndex, resetMessages, color, enm);
                playerFinishAttack();
                break;
        }
    }

    private void playerAttackAll() {
        for (int enm = 0; enm < enemies.length; enm++) {
            if (enemies[enm] == null || enemies[enm].isDead) {
                continue;
            }
            playerAttackSlot(enm);
            //test if the battle is over
            playerFinishAttack();
        }
    }

    private void playerAttack(int enm) {
        playerAttackSlot(enm);
        //test if the battle is over
        playerFinishAttack();
    }

    private void playerAttackSlot(int enm) {
        //calculate attack amount
        playerAttackType = calcAttackType(Tyre.player.dp, 0, enemies[enm].dp, 0);
        performPlayerAttack(playerAttackType, Tyre.player, ResourceContainer.strings[enemies[enm].stringIndex], ResourceContainer.strings[Constants.PLAYER_NAME_INDEX], enm);
        if (superCharge) {
            playerAttackType = 3;
        }
        //determine type of attack image
        if (playerAttackType == 1) {
        } else if (playerAttackType == 2) {
            j = rand.nextInt(2);
            if (j == 0) {
                playerAttackType = NSLASH2_INDEX;
            } else {
                playerAttackType = NSLASH1_INDEX;
            }
        } else if (playerAttackType == 3) {
            playerAttackType = SSLASH_INDEX;
        }
    }

    private void playerFinishItem() {
        if (gameState == 3) {
            //in battle mode
            //enemies turn now
            timestamp = count;
            Tyre.player.cooldown = Tyre.player.PLAYERWAIT_TIME;
            changeState(gameState, 2);
        } else {
            //normal mode
            if (gameSubState != 5) {
                gameSubState = 2;
            }
        }
    }

    private void playerFinishAttack() {
        //clear super charge status
        superCharge = false;
        if (activeEnemyCount() == 0) {
            //display battle end message
            battleModeState = 6;
            timestamp = count;
        } else {
            selectEnemy = false;
            Tyre.player.cooldown = Tyre.player.PLAYERWAIT_TIME;
            timestamp = count;
        }
    }

    private void playerRun() {
        j = this.findFastestActiveEnemy();
        if (isEntranced || isStunned) {
            Tyre.player.cooldown = Tyre.player.PLAYERWAIT_TIME;
            timestamp = count;
            return;
        }
        if (isBoss) {
            Tyre.player.cooldown = Tyre.player.PLAYERWAIT_TIME;
            timestamp = count;
            //blocked run
            newStatusMsg(194, 116, 0xFF0000, ResourceContainer.consoleStrings[Constants.CONSTR_CANTRUN], STATUSMSGS_DISPLAYTIME);
            superCharge = false;
            PlaySystemSound(Meta.BEEP_COMMAND_REJECTED);
            return;
        }
        if (superCharge) {
            tx = 40;
        } else {
            tx = 25;
        }
        if (rand.nextInt(100) < tx) {
            Tyre.player.battleRun++;
            playerAttackType = -1;
            battleModeState = 6;
            timestamp = count;
        } else {
            //TODO add text when player attempts to run away
            superCharge = false;
            Tyre.player.cooldown = Tyre.player.PLAYERWAIT_TIME;
            //TODOCOOL - set cooldown instead of enemy turn
            timestamp = count;
            //blocked run
            newStatusMsg(194, 116, 0xFF0000, ResourceContainer.consoleStrings[Constants.CONSTR_BLOCKED], STATUSMSGS_DISPLAYTIME);
            PlaySystemSound(Meta.BEEP_COMMAND_REJECTED);
        }
    }

    public void adjustTarget() {
        if (enemies.length > 1) {
            if (enemies.length == 3) {
                switch (selectEnemyPosX) {
                    case 2:
                        if (enemies[1] != null && !enemies[1].isDead) {
                            selectEnemyPosX += 80;
                            selectedEnemyPos++;
                        } else if (enemies[2] != null && !enemies[2].isDead) {
                            selectEnemyPosX += 160;
                            selectedEnemyPos += 2;
                        }
                        break;
                    case 82:
                        if (enemies[2] != null && !enemies[2].isDead) {
                            selectEnemyPosX += 80;
                            selectedEnemyPos++;
                        } else if (enemies[0] != null && !enemies[0].isDead) {
                            selectEnemyPosX -= 80;
                            selectedEnemyPos--;
                        }
                        break;
                    case 162:
                        if (enemies[0] != null && !enemies[0].isDead) {
                            selectEnemyPosX -= 160;
                            selectedEnemyPos -= 2;
                        } else if (enemies[1] != null && !enemies[1].isDead) {
                            selectEnemyPosX -= 80;
                            selectedEnemyPos--;
                        }
                        break;
                }
            } else if (enemies.length == 2) {
                switch (selectEnemyPosX) {
                    case 2:
                        if (enemies[1] != null && !enemies[1].isDead) {
                            selectEnemyPosX += 80;
                            selectedEnemyPos++;
                        }
                        break;
                    case 82:
                        if (enemies[0] != null && !enemies[0].isDead) {
                            selectEnemyPosX -= 80;
                            selectedEnemyPos--;
                        }
                        break;
                }
            }
        }
    }

    private void performPlayerAttack(int playerAttackType, charCore obj, String to, String from, int enm) {
        //HACK (test miss)
        //playerAttackType = 0;
        
        switch (playerAttackType) {
            case 0:
                //miss
                newStatusMsg(4, 116, 0xFF0000, ResourceContainer.consoleStrings[Constants.CONSTR_MISSED], STATUSMSGS_DISPLAYTIME);
                if (hasVibration) {
                    danger.audio.AudioManager.vibrate(100);
                }
                if (hasSound) {
                    Meta.unmuteID(Meta.BEEP_COMMAND_REJECTED);
                    Meta.play(Meta.BEEP_COMMAND_REJECTED);
                    Meta.muteID(Meta.BEEP_COMMAND_REJECTED);
                }
                break;
            case 2:
                //normal hit
                if (allTargetsSelected) {
                    //use sword 1 level below
                    o = getPlayerBattleMatrixResult((obj.ap - 1), enemies[enm].dp);
                } else {
                    o = getPlayerBattleMatrixResult(obj.ap, enemies[enm].dp);
                }

                if (superCharge) {
                    o *= 2;
                }
                break;
        }

        switch (playerAttackType) {
            case 0:
                Tyre.player.battleMisses++;
                break;
            case 2:
                if (superCharge) {
                    Tyre.player.battleHitsGiven++;
                    Tyre.player.battleSuperAttackGiven++;
                    if (hasSound) {
                        Meta.unmuteID(Meta.DELETE_TRASH);
                        Meta.play(Meta.DELETE_TRASH);
                        Meta.muteID(Meta.DELETE_TRASH);
                    }
                } else {
                    if (hasSound) {
                        Meta.unmuteID(Meta.BEEP_COMMAND_ACCEPTED);
                        Meta.play(Meta.BEEP_COMMAND_ACCEPTED);
                        Meta.muteID(Meta.BEEP_COMMAND_ACCEPTED);
                    }
                    Tyre.player.battleHitsGiven++;
                }
                break;
            //case 3:
                //reserve for supercharge
                //Tyre.player.battleSuperAttackGiven++;
                //break;
        }
        if (playerAttackType > 0) {
            effectEnemyHP(enemies[enm], o);
        }
    }

    private void performEnemyAttack(int currentAttackType, charCore obj, String to, String from, int src) {
        //HACK (test missed)
        //currentAttackType = 0;
        
        switch (currentAttackType) {
            case 0:
                //miss
                PlaySystemSound(Meta.BEEP_ACTION_FAIL);
                break;
            case 2:
                //normal hit
                //use battle matrix here
                o = getEnemyBattleMatrixResult(enemies[src].ap, obj.dp);
                PlaySystemSound(Meta.BEEP_ACTION_SUCCESS);
                break;
        }

        switch (currentAttackType) {
            case 0:
                Tyre.player.battleDodges++;
                break;
            //case 2:
            default:
                Tyre.player.battleHitsTaken++;
                break;
        }
        if (currentAttackType > 0) {
            effectPlayerHP(o);
        }
    }

    public void enemyAttack(int src) {
        lastActiveEnemy = src;
        lastActiveEnemyTick = 20;

        attackType[src] = 0;
        if (!enemyIsActive(src)) {
            //enemy not active, bypass and move cpanel back in (disabled)
            return;
        }

        //HACK (test strong self-heal)
        //switch (21) {        
        switch (enemies[src].aiType) {
            //HEAL AND POISON (OVERLOADED SPECIALIST)
            case 9:
                //self-heal (smart)
                if (rand.nextInt(100) < 18 && enemies[src].damage > 0) {
                    //HEALING ALL ALLYS
                    o = 5;
                    for (tmpInt = 0; tmpInt < enemies.length; tmpInt++) {
                        if (enemies[tmpInt] != null && !enemies[tmpInt].isDead) {
                            effectHealEnemyHP(enemies[tmpInt], -o);
                        }
                    }
                } else {
                    attackType[src] = calcAttackType(enemies[src].dp, 0, Tyre.player.dp, 0);
                    if (!isPoisoned && attackType[src] > 0 && rand.nextInt(100) < 18) {
                        poisonPlayer();
                    }
                    performEnemyAttack(attackType[src], Tyre.player, ResourceContainer.strings[Constants.PLAYER_NAME_INDEX], ResourceContainer.strings[enemies[src].stringIndex], src);
                }
                break;
            //ENTRANCING ATTACKS
            case 10:
                //entrancing (weak)
                if (rand.nextInt(100) < 10) {
                    entrancePlayer(3);
                } else {
                    attackType[src] = calcAttackType(enemies[src].dp, 0, Tyre.player.dp, 0);
                    performEnemyAttack(attackType[src], Tyre.player, ResourceContainer.strings[Constants.PLAYER_NAME_INDEX], ResourceContainer.strings[enemies[src].stringIndex], src);
                }
                break;
            case 11:
                //entrancing (strong)
                if (rand.nextInt(100) < 14) {
                    //entrance for 5 (4 + 1) frames since current action will be included
                    entrancePlayer(4);
                } else {
                    attackType[src] = calcAttackType(enemies[src].dp, 0, Tyre.player.dp, 0);
                    performEnemyAttack(attackType[src], Tyre.player, ResourceContainer.strings[Constants.PLAYER_NAME_INDEX], ResourceContainer.strings[enemies[src].stringIndex], src);
                }
                break;
            case 12:
                //entrancing (smart)
                if (rand.nextInt(100) < 18 && !isEntranced) {
                    //entrance for 5 (4 + 1) frames since current action will be included
                    entrancePlayer(4);
                } else {
                    attackType[src] = calcAttackType(enemies[src].dp, 0, Tyre.player.dp, 0);
                    performEnemyAttack(attackType[src], Tyre.player, ResourceContainer.strings[Constants.PLAYER_NAME_INDEX], ResourceContainer.strings[enemies[src].stringIndex], src);
                }
                break;
            //POISON ATTACKS
            case 13:
                //poison (weak)
                attackType[src] = calcAttackType(enemies[src].dp, 0, Tyre.player.dp, 0);
                if (attackType[src] > 0 && rand.nextInt(100) < 10) {
                    poisonPlayer();
                }
                performEnemyAttack(attackType[src], Tyre.player, ResourceContainer.strings[Constants.PLAYER_NAME_INDEX], ResourceContainer.strings[enemies[src].stringIndex], src);
                break;
            case 14:
                //poison (strong)
                attackType[src] = calcAttackType(enemies[src].dp, 0, Tyre.player.dp, 0);
                if (attackType[src] > 0 && rand.nextInt(100) < 14) {
                    poisonPlayer();
                }
                performEnemyAttack(attackType[src], Tyre.player, ResourceContainer.strings[Constants.PLAYER_NAME_INDEX], ResourceContainer.strings[enemies[src].stringIndex], src);
                break;
            case 15:
                //poison (smart)
                attackType[src] = calcAttackType(enemies[src].dp, 0, Tyre.player.dp, 0);
                if (!isPoisoned && attackType[src] > 0 && rand.nextInt(100) < 18) {
                    poisonPlayer();
                }
                performEnemyAttack(attackType[src], Tyre.player, ResourceContainer.strings[Constants.PLAYER_NAME_INDEX], ResourceContainer.strings[enemies[src].stringIndex], src);
                break;
            //SELF HEAL
            case 16:
                //self-heal (weak)
                if (rand.nextInt(100) < 10) {
                    o = 2;
                    effectHealEnemyHP(enemies[src], -o);
                } else {
                    attackType[src] = calcAttackType(enemies[src].dp, 0, Tyre.player.dp, 0);
                    performEnemyAttack(attackType[src], Tyre.player, ResourceContainer.strings[Constants.PLAYER_NAME_INDEX], ResourceContainer.strings[enemies[src].stringIndex], src);
                }
                break;
            case 17:
                //self-heal (strong)
                if (rand.nextInt(100) < 14 && enemies[src].damage > 0) {
                    o = 3;
                    effectHealEnemyHP(enemies[src], -o);
                } else {
                    attackType[src] = calcAttackType(enemies[src].dp, 0, Tyre.player.dp, 0);
                    performEnemyAttack(attackType[src], Tyre.player, ResourceContainer.strings[Constants.PLAYER_NAME_INDEX], ResourceContainer.strings[enemies[src].stringIndex], src);
                }
                break;
            case 18:
                //self-heal (smart)
                if (rand.nextInt(100) < 18 && enemies[src].damage > 0) {
                    o = 5;
                    effectHealEnemyHP(enemies[src], -o);
                } else {
                    attackType[src] = calcAttackType(enemies[src].dp, 0, Tyre.player.dp, 0);
                    performEnemyAttack(attackType[src], Tyre.player, ResourceContainer.strings[Constants.PLAYER_NAME_INDEX], ResourceContainer.strings[enemies[src].stringIndex], src);
                }
                break;
            //ALLY HEAL
            case 19:
                //ally-heal (weak)
                if (rand.nextInt(100) < 10) {
                    o = 2;
                    k = findWeakestActiveEnemy();
                    effectHealEnemyHP(enemies[k], -o);
                } else {
                    attackType[src] = calcAttackType(enemies[src].dp, 0, Tyre.player.dp, 0);
                    performEnemyAttack(attackType[src], Tyre.player, ResourceContainer.strings[Constants.PLAYER_NAME_INDEX], ResourceContainer.strings[enemies[src].stringIndex], src);
                }
                break;
            case 20:
                //ally-heal (strong)
                if (rand.nextInt(100) < 14) {
                    o = 3;
                    k = findWeakestActiveEnemy();
                    effectHealEnemyHP(enemies[k], -o);
                } else {
                    attackType[src] = calcAttackType(enemies[src].dp, 0, Tyre.player.dp, 0);
                    performEnemyAttack(attackType[src], Tyre.player, ResourceContainer.strings[Constants.PLAYER_NAME_INDEX], ResourceContainer.strings[enemies[src].stringIndex], src);
                }
                break;
            case 21:
                //ally-heal (smart)
                if (rand.nextInt(100) < 18 && enemies[findWeakestActiveEnemy()].damage > 0) {
                    o = 5;
                    for (tmpInt = 0; tmpInt < enemies.length; tmpInt++) {
                        if (enemies[tmpInt] != null && !enemies[tmpInt].isDead) {
                            effectHealEnemyHP(enemies[tmpInt], -o);
                        }
                    }
                } else {
                    attackType[src] = calcAttackType(enemies[src].dp, 0, Tyre.player.dp, 0);
                    performEnemyAttack(attackType[src], Tyre.player, ResourceContainer.strings[Constants.PLAYER_NAME_INDEX], ResourceContainer.strings[enemies[src].stringIndex], src);
                }
                break;
            default:
                attackType[src] = calcAttackType(enemies[src].dp, 0, Tyre.player.dp, 0);
                performEnemyAttack(attackType[src], Tyre.player, ResourceContainer.strings[Constants.PLAYER_NAME_INDEX], ResourceContainer.strings[enemies[src].stringIndex], src);
                if (rand.nextInt(100) < 5 && !isStunned) {
                    //stun for 1 frame since current action will be included
                    stunPlayer(2);
                }
                break;
        }

        if (attackType[src] > 0) {
            if (attackType[src] == 3) {
                //TODO - ENEMY CURRENTLY HAS NO CRITICAL STRIKE CAPABILITY
            }
            successTime = count;
            attackSuccess = true;
        } else {
            attackSuccess = false;
        }

        //in case the last enemy ran away
        if ((Tyre.player.hp - Tyre.player.damage) <= 0) {
            //player has died
            if (Tyre.player.goldCoins > 1) {
                //Tyre.player.goldCoins = (Tyre.player.goldCoins/2);
                //lose 1/4 of gold instead of 1/2 gold
                Tyre.player.goldCoins -= (Tyre.player.goldCoins / 4);
            }
            Tyre.player.totalDeaths++;
            superCharge = false;
            allTargetsSelected = false;
            Tyre.player.moveBattleStats();

            //Tyre.player.resetBattleStats();
            //set life cells to 2
            //Tyre.player.damage = Tyre.player.hp - 8;
            changeState(3, 10);
        } else if (activeEnemyCount() == 0) {
            changeState(3, 6);
        }

        //process player afflictions since enemy made an action
        if (isEntranced) {
            isEntrancedCount--;
            if (isEntrancedCount <= 0) {
                isEntrancedCount = 0;
                isEntranced = false;
            }
        }
        if (isStunned) {
            isStunnedCount--;
            if (isStunnedCount <= 0) {
                isStunnedCount = 0;
                isStunned = false;
            }
        }
        //anti-sleep countdown based on sleep attempts against player
    }

    public void scrollInPlayerControlPanel() {
        //scroll in player control panel
        changeState(3, 8);
    }

    public void scrollOutPlayerControlPanel() {
        //scroll out player control panel
        changeState(3, 9);
    }

    private int calcAttackType(int ap, int apBonus, int dp, int dpBonus) {
        //HACK (test miss)
        //if (true) { return 0; }

        j = rand.nextInt(100);
        if (j < 10) {
            //miss
            return 0;
        } else {
            //normal
            return 2;
        }
    }

    private boolean calcRunSuccess(int aSpd, int aSpdBonus, int dSpd, int dSpdBonus) {
        j = (rand.nextInt(7) + aSpd + aSpdBonus);
        k = (rand.nextInt(4) + dSpd + dSpdBonus);
        if (k > j) {
            //run away successful
            return true;
        } else {
            return false;
        }
    }

    private int calcDiceRoll(int ap, int timesBonus, int attackBonus) {
        tmpInt = 0;
        //roll the proper number of times
        for (j = 0; j < ResourceContainer.attackRolls[ap].times + timesBonus; j++) {
            tmpInt += rand.nextInt(ResourceContainer.attackRolls[ap].max) + attackBonus;
            tmpInt++;
        }
        if (tmpInt < ResourceContainer.attackRolls[ap].min) {
            tmpInt = ResourceContainer.attackRolls[ap].min;
        }
        return tmpInt;
    }

    private void resetNpcInfo() {
        Tyre.roomChars[currentConvNpcIndex].canWalk = currentConvNpcCanWalk;
        Tyre.roomChars[currentConvNpcIndex].dir = currentConvNpcDir;
        currentConvNpcSet = false;
    }

    private void handleEventWidgetUpCancel() {
        //settings screen - return to main menu without committing changes
        if (gameState == 10 && mainMenuSubState == 2) {
            changeState(10, 0);
        }
        //enter player name - return to are you sure screen (back up one screen)
        else if (gameState == 10 && mainMenuSubState == 5) {
            changeState(10, 1);
        } else {
            handleEventWidgetUpBack();
        }
    }

    private void handleEventWidgetUpBack() {
        switch (gameState) {
            case 0:
                switch (gameSubState) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 9:
                    //case 10:
                        gameSubState = 0;
                        consumedUp = true;
                        break;
                    case 5:
                        //end of conversation
                        gameSubState = 0;
                        consumedUp = true;
                        if (storyType == 0) {
                            break;
                        }
                        if (currentConvNpcSet) {
                            resetNpcInfo();
                        }
                        //check for end of conversation (inConv is false)
                        if (!inConv) {
                            //clear flag TODO - MOVE TO END OF CONVO
                            if (focus.flagIndex != -1) {
                                if (focus.selfTriggered && !ResourceContainer.flags[focus.flagIndex]) {
                                    Logger.wr("FLAGS: CONVO: Index:" + focus.flagIndex + " = " + true + "< focus.flagIndex = true >");
                                    ResourceContainer.flags[focus.flagIndex] = true;
                                }
                            }
                            if (focus.flagIndex1 != -1) {
                                Logger.wr("FLAGS: CONVO: Index:" + focus.flagIndex1 + " = " + true + "< focus.flagIndex1 = true >");
                                ResourceContainer.flags[focus.flagIndex1] = true;
                            }
                            //hack
                            if (focus.searchIndex > -1) {
                                //items taken thru convo
                                if (focus.takeItem) {
                                    /*
                                    if (ResourceContainer.searches[focus.searchIndex].item1 > -1) {
                                        discardItem(Tyre.player.inInventory(ResourceContainer.searches[focus.searchIndex].item1), ResourceContainer.searches[focus.searchIndex].quantity1);
                                    }
                                    if (ResourceContainer.searches[focus.searchIndex].item2 > -1) {
                                        discardItem(Tyre.player.inInventory(ResourceContainer.searches[focus.searchIndex].item2), ResourceContainer.searches[focus.searchIndex].quantity2);
                                    }
                                    if (ResourceContainer.searches[focus.searchIndex].item3 > -1) {
                                        discardItem(Tyre.player.inInventory(ResourceContainer.searches[focus.searchIndex].item3), ResourceContainer.searches[focus.searchIndex].quantity3);
                                    }
                                    ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex] = true;
                                     */

                                    if (ResourceContainer.searches[focus.searchIndex].flagIndex == -1 || (ResourceContainer.searches[focus.searchIndex].flagDir == 0 && ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex] == true) || (ResourceContainer.searches[focus.searchIndex].flagDir == 1 && ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex] == false)) {
                                        if (ResourceContainer.searches[focus.searchIndex].item1 > -1) {
                                            discardItem(Tyre.player.inInventory(ResourceContainer.searches[focus.searchIndex].item1), ResourceContainer.searches[focus.searchIndex].quantity1);
                                        }
                                        if (ResourceContainer.searches[focus.searchIndex].item2 > -1) {
                                            discardItem(Tyre.player.inInventory(ResourceContainer.searches[focus.searchIndex].item2), ResourceContainer.searches[focus.searchIndex].quantity2);
                                        }
                                        if (ResourceContainer.searches[focus.searchIndex].item3 > -1) {
                                            discardItem(Tyre.player.inInventory(ResourceContainer.searches[focus.searchIndex].item3), ResourceContainer.searches[focus.searchIndex].quantity3);
                                        }
                                        if (ResourceContainer.searches[focus.searchIndex].flagIndex > -1) {
                                            Logger.wr("FLAGS: SEARCH: Index:" + ResourceContainer.searches[focus.searchIndex].flagIndex + " = " + (!ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex]) + "< focus.searchIndex].flagIndex = (!ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex]) >");
                                            ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex] = (!ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex]);
                                        }
                                        if (ResourceContainer.searches[focus.searchIndex].flagIndex2 > -1) {
                                            Logger.wr("FLAGS: SEARCH: Index:" + ResourceContainer.searches[focus.searchIndex].flagIndex2 + " = " + (!ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex2]) + "< focus.searchIndex].flagIndex2 = (!ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex2]) >");
                                            ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex2] = (!ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex2]);
                                        }
                                        if (ResourceContainer.searches[focus.searchIndex].flagIndex3 > -1) {
                                            Logger.wr("FLAGS: SEARCH: Index:" + ResourceContainer.searches[focus.searchIndex].flagIndex3 + " = " + (!ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex3]) + "< focus.searchIndex].flagIndex3 = (!ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex3]) >");
                                            ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex3] = (!ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex3]);
                                        }
                                        if (ResourceContainer.searches[focus.searchIndex].flagIndex4 > -1) {
                                            Logger.wr("FLAGS: SEARCH: Index:" + ResourceContainer.searches[focus.searchIndex].flagIndex4 + " = " + (!ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex4]) + "< focus.searchIndex].flagIndex4 = (!ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex4]) >");
                                            ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex4] = (!ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex4]);
                                        }
                                        if (ResourceContainer.searches[focus.searchIndex].flagIndex5 > -1) {
                                            Logger.wr("FLAGS: SEARCH: Index:" + ResourceContainer.searches[focus.searchIndex].flagIndex5 + " = " + (!ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex5]) + "< focus.searchIndex].flagIndex5 = (!ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex5]) >");
                                            ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex5] = (!ResourceContainer.flags[ResourceContainer.searches[focus.searchIndex].flagIndex5]);
                                        }
                                    }
                                }
                                //items received thru convo
                                else {
                                    itemfound_i = 0;
                                    itemfound_t = 0;
                                    changeState(gameState, 10);
                                    handleSearchObject(focus.searchIndex);
                                }
                            } else if (focus.battlesIndex != 255) {
                                //start enemy battle after convo
                                startBattle(focus.battlesIndex, ResourceContainer.battles[focus.battlesIndex].flagIndex, ResourceContainer.battles[focus.battlesIndex].flagIndex1, ResourceContainer.battles[focus.battlesIndex].flagIndex2, ResourceContainer.battles[focus.battlesIndex].searchId);
                            }
                        }
                        break;
                    case 6:
                        if (herbShopSubState != 0) {
                            handleHerbShopGreeting();
                            herbShopSubState = 0;
                        } else {
                            gameSubState = 0;
                        }
                        consumedUp = true;
                        break;
                    case 7:
                        //inn
                        break;
                    /*
                    case 8:
                        //spell shop
                        break;
                    */
                    case 10:
                        //item found
                        if (itemfound_i >= itemfound_t - 1) {
                            gameSubState = 0;
                            consumedUp = true;
                        }
                        break;
                    //toll keeper
                    case 11:
                        resetNpcInfo();
                        gameSubState = 0;
                        consumedUp = true;
                        break;
                    default:
                        Tyre.cApp.returnToLauncher();
                        consumedUp = true;
                        break;
                }
                break;
            case 3:
                switch (battleModeState) {
                    case 2:
                        //player's turn
                        consumedUp = true;
                        if (selectEnemy) {
                            selectEnemy = false;
                            battleModeState = 2;
                        } else {
                            Tyre.cApp.returnToLauncher();
                        }
                        break;
                    /*
                    case 4:
                        //spell inventory
                        break;
                    */
                    case 7:
                        //item inventory
                        battleModeState = 2;
                        consumedUp = true;
                        break;
                    case 10:
                        //skip red curtain
                        //this is the continue game after death
                        resetPlayerHP();
                        TyreAudio.startMusic(TyreAudio.prevIndex);
                        changeState(0, 0);
                        consumedUp = true;
                        break;
                    case 11:
                        //this is the continue game after death
                        resetPlayerHP();
                        TyreAudio.startMusic(TyreAudio.prevIndex);
                        changeState(0, 0);
                        consumedUp = true;
                        break;
                    default:
                        consumedUp = true;
                        Tyre.cApp.returnToLauncher();
                        break;
                }
                break;
            case 10:
                switch (mainMenuSubState) {
                    case 0:
                        //10.17.07 - done button should always go to launcher from main menu
                        Tyre.cApp.returnToLauncher();
                        /*
                        if(Tyre.gameStart) {
                            Tyre.cApp.returnToLauncher();
                        }else{
                            continueGame();
                        }
                        */
                        break;
                    case 1:
                        changeState(10, 0);
                        break;
                    case 2:
                        processSettings();
                        changeState(10, 0);
                        break;
                    case 3:
                        changeState(10, 0);
                        break;
                    case 4:
                        if (returnToMenu) {
                            changeState(10, 0);
                        } else {
                            Tyre.cApp.buildRoom(true);
                        }
                        break;
                    case 5:
                        processEnterName(tmpStr);
                        break;
                    case 6:
                    case 8:
                        //settings import screen
                        changeState(10, 0);
                        break;
                    case 7:
                        //import verification screen
                        changeState(10, 6);
                        break;
                }
                break;
            default:
                Tyre.cApp.returnToLauncher();
                consumedUp = true;
                break;
        }
    }

    @Override
    public boolean eventWidgetUp(int inWhichWidget, Event inEvent) {
        consumedUp = false;
        switch (inWhichWidget) {
            /*
            case Event.EVENT_PASTE :
                consumedUp = true;
                if(gameState == 10 && mainMenuSubState == 6) {
                    tmpStr = Pasteboard.getString();
                }
                break;
            */
            case Event.DEVICE_BUTTON_CANCEL:
                handleEventWidgetUpCancel();
                break;
            case Event.DEVICE_BUTTON_BACK:
                handleEventWidgetUpBack();
                break;
            case Event.DEVICE_WHEEL_PAGE_UP:
            case Event.DEVICE_WHEEL:
                if (!(gameState == 0 && gameSubState == 0)) {
                    handleEventWidgetDownUp();
                }
                break;
            case Event.DEVICE_WHEEL_BUTTON:
                //wheel click
                handleEventWidgetDownWheelClick();
                consumedUp = true;
                break;
            /*               
            case Event.DEVICE_WHEEL_BUTTON:
                consumedUp = true;
                break;
            */
            case Event.DEVICE_ARROW_LEFT:
                transTop = 0;
                transRight = 0;
                transBottom = 0;
                if (Tyre.player == null) {
                    consumedUp = true;
                    break;
                }
                if (Tyre.player.walk) {
                    Tyre.player.walk = false;
                }
                consumedUp = true;
                break;
            case Event.DEVICE_ARROW_UP:
                transLeft = 0;
                transRight = 0;
                transBottom = 0;
                if (Tyre.player == null) {
                    consumedUp = true;
                    break;
                }
                if (Tyre.player.walk) {
                    Tyre.player.walk = false;
                }
                consumedUp = true;
                break;
            case Event.DEVICE_ARROW_RIGHT:
                transTop = 0;
                transLeft = 0;
                transBottom = 0;
                if (Tyre.player == null) {
                    consumedUp = true;
                    break;
                }
                if (Tyre.player.walk) {
                    Tyre.player.walk = false;
                }
                consumedUp = true;
                break;
            case Event.DEVICE_ARROW_DOWN:
                transTop = 0;
                transLeft = 0;
                transRight = 0;
                if (Tyre.player == null) {
                    consumedUp = true;
                    break;
                }
                if (Tyre.player.walk) {
                    Tyre.player.walk = false;
                }
                consumedUp = true;
                break;
            case Event.DEVICE_BUTTON_MENU:
                switch (gameState) {
                    case 8:
                        consumedUp = true;
                        break;
                    case 10:
                        // 10.17.07 - Disabled menu button from main menu screen
                        //continueGame();
                        //Tyre.buildRoom(true);
                        //changeState(prevGameState);
                        consumedUp = true;
                        break;
                    case 11:
                        consumedUp = true;
                        break;
                    default:
                        changeState(10, 0);
                        TyreAudio.startMusic(Constants.MUSIC_MENU);
                        break;
                }
                break;
            case Event.DEVICE_BUTTON_JUMP:
                //Tyre.cApp.returnToLauncher();
                break;
        }
        if (consumedUp) {
            return true;
        } else {
            return super.eventWidgetUp(inWhichWidget, inEvent);
        }
    }

    private void readObject(int deltaX, int deltaY) {
        for (p = 0; p < Tyre.roomObjects.length; p++) {
            switch (Tyre.roomObjects[p].type) {
                case 1:
                    if (Tyre.roomObjects[p].stringIndex != -1) {
                        if (Tyre.player.cbot.tileCollision(Tyre.roomObjects[p].posX, Tyre.roomObjects[p].posY, Tyre.bitmaps[Tyre.roomObjects[p].imageIndex].getWidth(), Tyre.bitmaps[Tyre.roomObjects[p].imageIndex].getHeight(), Tyre.player.posX + deltaX, Tyre.player.posY + deltaY, Tyre.player.posX, Tyre.player.posY)) {
                            tmpBool = true;
                            resetMessages();
                            formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], Color.WHITE);
                            formatBattleMessage(ResourceContainer.strings[Tyre.roomObjects[p].stringIndex], Color.WHITE);
                            k = Tyre.roomObjects.length;
                        }
                    }
                    break;
                //NPC
                case 6:
                    if (Tyre.roomObjects[p].stringIndex != -1) {
                        if (Tyre.player.cbot.collision(Tyre.roomChars[Tyre.roomObjects[p].npcIndex].posX, Tyre.roomChars[Tyre.roomObjects[p].npcIndex].posY, Tyre.roomChars[Tyre.roomObjects[p].npcIndex].width, Tyre.roomChars[Tyre.roomObjects[p].npcIndex].height, Tyre.player.posX + deltaX, Tyre.player.posY + deltaY, 4, Tyre.player.posX, Tyre.player.posY)) {
                            tmpBool = true;
                            resetMessages();
                            formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], Color.WHITE);
                            formatBattleMessage(ResourceContainer.strings[Tyre.roomObjects[p].stringIndex], Color.WHITE);
                            k = Tyre.roomObjects.length;
                        }
                    }
                    break;
            }
        }
    }

    private void npcChangeDir(int playerDir, int npcIndex) {
        if (!currentConvNpcSet) {
            currentConvNpcDir = Tyre.roomChars[npcIndex].dir;
            switch (playerDir) {
                case 0:
                    //down
                    Tyre.roomChars[npcIndex].dir = 2;
                    break;
                case 1:
                    //right
                    Tyre.roomChars[npcIndex].dir = 3;
                    break;
                case 2:
                    //up
                    Tyre.roomChars[npcIndex].dir = 0;
                    break;
                case 3:
                    //left
                    Tyre.roomChars[npcIndex].dir = 1;
                    break;
            }
            currentConvNpcIndex = npcIndex;
            currentConvNpcCanWalk = Tyre.roomChars[npcIndex].canWalk;
            Tyre.roomChars[npcIndex].canWalk = false;
            Tyre.roomChars[npcIndex].setWalk(false);
            currentConvNpcSet = true;
        }
    }

    private void talkObject(int deltaX, int deltaY) {
        for (p = 0; p < Tyre.roomObjects.length; p++) {
            switch (Tyre.roomObjects[p].type) {
                case 6:
                    /*
                    if(Tyre.roomChars[Tyre.roomObjects[p].npcIndex].npcType == 0) {
                        //gameSubState = 6;
                        changeState(gameState, 6);
                        return;
                    }
                    */
                    if (Tyre.roomObjects[p].flagIndex != -1) {
                        if ((Tyre.roomObjects[p].flagDir == 0 && ResourceContainer.flags[Tyre.roomObjects[p].flagIndex] == true) || (Tyre.roomObjects[p].flagDir == 1 && ResourceContainer.flags[Tyre.roomObjects[p].flagIndex] == false)) {
                            //do nothing
                        } else {
                            break;
                        }
                    }
                    if (Tyre.roomObjects[p].conversationIndex != -1 || Tyre.roomChars[Tyre.roomObjects[p].npcIndex].npcType == Constants.NPCTYPE_SHOPKEEPER || Tyre.roomChars[Tyre.roomObjects[p].npcIndex].npcType == Constants.NPCTYPE_TOLLKEEPER) {
                        if (Tyre.player.cbot.collision(Tyre.roomChars[Tyre.roomObjects[p].npcIndex].posX, Tyre.roomChars[Tyre.roomObjects[p].npcIndex].posY, Tyre.roomChars[Tyre.roomObjects[p].npcIndex].width, Tyre.roomChars[Tyre.roomObjects[p].npcIndex].height, Tyre.player.posX + deltaX, Tyre.player.posY + deltaY, 4, Tyre.player.posX, Tyre.player.posY)) {
                            switch (Tyre.roomChars[Tyre.roomObjects[p].npcIndex].npcType) {
                                //check for shop keeper
                                case Constants.NPCTYPE_SHOPKEEPER:
                                    //npcChangeDir(Tyre.player.dir, Tyre.roomObjects[p].npcIndex);
                                    changeState(gameState, 6);
                                    return;
                                //check for healer
                                case Constants.NPCTYPE_HEALER:
                                    npcChangeDir(Tyre.player.dir, Tyre.roomObjects[p].npcIndex);
                                    //heal all damage and cure poison
                                    Tyre.player.damage = 0;
                                    isPoisoned = false;
                                    Tyre.player.setRechargeSpeed();
                                    break;
                                //check for toll keeper
                                case Constants.NPCTYPE_TOLLKEEPER:
                                    npcChangeDir(Tyre.player.dir, Tyre.roomObjects[p].npcIndex);
                                    focus = ResourceContainer.conversations[Tyre.roomObjects[p].conversationIndex];
                                    tollkeeper_flag = focus.flagIndex;
                                    switch (Tyre.currentRoomIdx) {
                                        case 79:
                                            tollkeeper_toll = 250;
                                            break;
                                        case 82:
                                            tollkeeper_toll = 400;
                                            break;
                                        default:
                                            tollkeeper_toll = 800;
                                            break;
                                    }
                                    changeState(gameState, 11);
                                    return;
                                //check for tree folk
                                case Constants.NPCTYPE_TREEFOLK:
                                    NpcImageOverride = TREEFOLK_IMAGEID;
                                    break;
                                case Constants.NPCTYPE_PERSON:
                                    npcChangeDir(Tyre.player.dir, Tyre.roomObjects[p].npcIndex);
                                    break;
                            }//switch

                            tmpBool = true;
                            inConvNpcIndex = Tyre.roomObjects[p].npcIndex;
                            lastStringUsed = Tyre.roomObjects[p].conversationIndex;
                            lastStringArrayUsed = 2;
                            focus = ResourceContainer.conversations[Tyre.roomObjects[p].conversationIndex];

                            if (focus.flagIndex != -1) {
                                while (focus.flagIndex != -1 && ResourceContainer.flags[focus.flagIndex] == true && focus.conversationIndex != -1) {
                                    //focus = Tyre.conversations[focus.conversationIndex];
                                    lastStringUsed = focus.conversationIndex;
                                    lastStringArrayUsed = 2;
                                    focus = ResourceContainer.conversations[focus.conversationIndex];
                                }
                            }

                            //already in a conversation - advance to next part
                            if (inConv) {
                                m = ResourceContainer.strings[focus.stringIdNpc].indexOf("[s]", inConvPosTotal);
                                //this is final part of a multipart conversation
                                if (m == -1) {
                                    inConv = false;
                                    if (inConvStayOnCurrent) {
                                        inConvStayOnCurrent = false;
                                    } else {
                                        inConvPlayer = !inConvPlayer;
                                    }
                                    lastNpcIndex = Tyre.roomChars[Tyre.roomObjects[p].npcIndex].stringIndex;

                                    if (inConvPlayer) {
                                        tmpStr2 = ResourceContainer.strings[Tyre.player.stringIndex] + ": ";
                                        tmpStr = ResourceContainer.strings[focus.stringIdNpc].substring(inConvPosTotal);
                                    } else {
                                        tmpStr2 = ResourceContainer.strings[Tyre.roomChars[Tyre.roomObjects[p].npcIndex].stringIndex] + ": ";
                                        tmpStr = ResourceContainer.strings[focus.stringIdNpc].substring(inConvPosTotal);
                                    }
                                    inConvPos = 0;
                                }
                                //there is more to this multipart conversation
                                else {
                                    inConv = true;
                                    if (inConvStayOnCurrent) {
                                        inConvStayOnCurrent = false;
                                    } else {
                                        inConvPlayer = !inConvPlayer;
                                    }
                                    lastNpcIndex = Tyre.roomChars[Tyre.roomObjects[p].npcIndex].stringIndex;

                                    if (inConvPlayer) {
                                        tmpStr2 = ResourceContainer.strings[Tyre.player.stringIndex] + ": ";
                                        tmpStr = ResourceContainer.strings[focus.stringIdNpc].substring(inConvPosTotal, m);
                                    } else {
                                        tmpStr2 = ResourceContainer.strings[Tyre.roomChars[Tyre.roomObjects[p].npcIndex].stringIndex] + ": ";
                                        tmpStr = ResourceContainer.strings[focus.stringIdNpc].substring(inConvPosTotal, m);
                                    }
                                    inConvPos = m + 3;
                                }
                            } else {
                                inConvPosTotal = 0;
                                inConvPlayer = false;
                                m = ResourceContainer.strings[focus.stringIdNpc].indexOf("[s]");
                                //this is not a multipart conversation
                                if (m == -1) {
                                    lastNpcIndex = Tyre.roomChars[Tyre.roomObjects[p].npcIndex].stringIndex;
                                    tmpStr2 = ResourceContainer.strings[Tyre.roomChars[Tyre.roomObjects[p].npcIndex].stringIndex] + ": ";
                                    tmpStr = ResourceContainer.strings[focus.stringIdNpc];
                                }
                                //this is a multipart conversation
                                else {
                                    //check for player start
                                    //player first conversation
                                    if (m == 0) {
                                        inConvPos = m + 6;
                                        //start of conversation means NPC talks first (unless special char is first)
                                        inConvPlayer = true;
                                        tmpStr2 = ResourceContainer.strings[Tyre.player.stringIndex] + ": ";
                                        m = ResourceContainer.strings[focus.stringIdNpc].indexOf("[s]", inConvPos);
                                        tmpStr = ResourceContainer.strings[focus.stringIdNpc].substring(3, m);
                                        inConvPos = m + 3;
                                    }
                                    //NPC first conversation
                                    else {
                                        inConvPos = m + 3;
                                        //start of conversation means NPC talks first (unless special char is first)
                                        inConvPlayer = false;
                                        lastNpcIndex = Tyre.roomChars[Tyre.roomObjects[p].npcIndex].stringIndex;
                                        tmpStr2 = ResourceContainer.strings[Tyre.roomChars[Tyre.roomObjects[p].npcIndex].stringIndex] + ": ";
                                        tmpStr = ResourceContainer.strings[focus.stringIdNpc].substring(0, m);
                                    }
                                    inConvStayOnCurrent = false;
                                    inConv = true;
                                }
                            }
                            formatDialogueMessage(tmpStr2, tmpStr);
                            tmpStr = "";
                            p = Tyre.roomObjects.length;
                        }
                    }
                    break;
            }
        }
    }

    public void handleRead() {
        resetMessages();
        tmpBool = false;
        switch (Tyre.player.dir) {
            case 0:
                //down
                readObject(0, 8);
                break;
            case 1:
                //right
                readObject(8, 0);
                break;
            case 2:
                //up
                readObject(0, -8);
                break;
            case 3:
                //left
                readObject(-8, -0);
                break;
        }
        gameSubState = 2;
        if (!tmpBool) {
            formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], Color.WHITE);
            formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_NOTHINGOFINTEREST], Color.WHITE);
        }
    }

    public void handleTalkContinue() {
        tmpBool = false;
        switch (Tyre.player.dir) {
            case 0:
                //down
                talkObject(0, 8);
                break;
            case 1:
                //right
                talkObject(8, 0);
                break;
            case 2:
                //up
                talkObject(0, -8);
                break;
            case 3:
                //left
                talkObject(-8, -0);
                break;
        }
    }

    public void handleTalk() {
        tmpBool = false;
        //new conversation so reset multipart conversation flags
        inConv = false;
        inConvPos = 0;
        storyType = 1;
        NpcImageOverride = -1;
        switch (Tyre.player.dir) {
            case 0:
                //down
                talkObject(0, 8);
                break;
            case 1:
                //right
                talkObject(8, 0);
                break;
            case 2:
                //up
                talkObject(0, -8);
                break;
            case 3:
                //left
                talkObject(-8, 0);
                break;
        }

        if (gameSubState == 6) {
            handleHerbShopGreeting();
        } else if (gameSubState == 11) {
            handleTollKeeperGreeting();
        } else {
            resetMessages();
            if (!tmpBool) {
                formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], Color.WHITE);
                formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_NOBODYTOTALK], Color.WHITE);
                gameSubState = 2;
            } else {
                gameSubState = 5;
            }
        }
    }

    public void handleHerbShopGreeting() {
        resetMessages();
        cursorPosY = 45;
        herbShopSubState = 0;
        formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], Color.WHITE);
        formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_HERBSHOPGREETING], Color.WHITE);
    }

    public void handleTollKeeperGreeting() {
        resetMessages();
        cursorPosY = 45;
        herbShopSubState = 0;
        formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], Color.WHITE);
        formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_TOLLKEEPERGREETING] + " " + tollkeeper_toll + " " + ResourceContainer.consoleStrings[Constants.CONSTR_GOLDPERIOD], Color.WHITE);
    }

    public void handleSearch() {
        resetMessages();
        tmpBool = false;
        switch (Tyre.player.dir) {
            case 0:
                //down
                searchObject(0, 12);
                break;
            case 1:
                //right
                searchObject(12, 0);
                break;
            case 2:
                //up
                searchObject(0, -12);
                break;
            case 3:
                //left
                searchObject(-12, 0);
                break;
        }
        
        //DISABLED, use item found dialog
        //gameSubState = 2;
        if (!tmpBool) {
            gameSubState = 2;
            formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_CLOSEWIN], Color.WHITE);
            formatBattleMessage(ResourceContainer.consoleStrings[Constants.CONSTR_FOUNDNOTHING], Color.WHITE);
        }
    }

    //since purchasing an item is not valid when the qty is above MAX_ITEM_QTY this function checks if the item and qty is buyable
    //by contrast, storeItem caps the amount at MAX_ITEM_QTY if its above
    private boolean checkBuyItem(int itemId, int quantity) {
        //no items in inventory yet
        if (Tyre.player.items[0][0] < 0) {
            return true;
        } else {
            //search for a match
            for (int z = 0; z < Constants.ITEM_INVENTORY_SIZE; z++) {
                if (Tyre.player.items[z][0] == itemId) {
                    //cap items at 250
                    if (Tyre.player.items[z][1] + quantity > Constants.MAX_ITEM_QTY) {
                        //new qty + existing qty exceeds max
                        return false;
                    } else {
                        //new qty + existing qty below max
                        return true;
                    }
                }
            }
        }
        //item not in inventory yet
        return true;
    }

    private boolean storeItem(int itemId, int quantity) {
        //TODO CHECK FOR ITEM TYPE
        switch (itemId) {
            case Constants.ITEM_GOLD:
                Tyre.player.goldCoins += quantity;
                //cap gold at 9999
                if (Tyre.player.goldCoins > 9999) {
                    Tyre.player.goldCoins = 9999;
                }
                return true;
            case Constants.ITEM_ANTISTUNORB:
                Tyre.player.antistun = true;
                return true;
            case Constants.ITEM_ANTIPOISONORB:
                Tyre.player.antipoison = true;
                return true;
            case Constants.ITEM_ANTISLEEPORB:
                Tyre.player.antisleep = true;
                Tyre.player.antisleeporb = true;
                return true;
            case Constants.ITEM_SWORD1:
                Tyre.player.equip_weapon(0, Constants.ITEM_SWORD1);
                if (!isPoisoned) {
                    Tyre.player.setRechargeSpeed();
                }
                return true;
            case Constants.ITEM_SWORD2:
                Tyre.player.equip_weapon(1, Constants.ITEM_SWORD2);
                if (!isPoisoned) {
                    Tyre.player.setRechargeSpeed();
                }
                return true;
            case Constants.ITEM_SWORD3:
                Tyre.player.equip_weapon(2, Constants.ITEM_SWORD3);
                if (!isPoisoned) {
                    Tyre.player.setRechargeSpeed();
                }
                return true;
            case Constants.ITEM_SWORD4:
                Tyre.player.equip_weapon(3, Constants.ITEM_SWORD4);
                if (!isPoisoned) {
                    Tyre.player.setRechargeSpeed();
                }
                return true;
            case Constants.ITEM_ARMOR1:
                Tyre.player.equip_armor(0, Constants.ITEM_ARMOR1);
                return true;
            case Constants.ITEM_ARMOR2:
                Tyre.player.equip_armor(1, Constants.ITEM_ARMOR2);
                return true;
            case Constants.ITEM_ARMOR3:
                Tyre.player.equip_armor(2, Constants.ITEM_ARMOR3);
                return true;
            case Constants.ITEM_ARMOR4:
                Tyre.player.equip_armor(3, Constants.ITEM_ARMOR4);
                return true;
            case Constants.ITEM_LIFECONTAINER:
                //Life container - increase max hp and restore health
                Tyre.player.hp += 4;
                Tyre.player.damage = 0;
                return true;
        }

        if (Tyre.player.items[0][0] < 0) {
            //no items have been entered so ad one
            Tyre.player.items[0][0] = itemId;
            Tyre.player.items[0][1] = quantity;
            return true;
        } else {
            //search for a match
            for (int z = 0; z < Constants.ITEM_INVENTORY_SIZE; z++) {
                if (Tyre.player.items[z][0] == itemId) {
                    Tyre.player.items[z][1] += quantity;
                    //cap items at MAX_ITEM_QTY
                    if (Tyre.player.items[z][1] > Constants.MAX_ITEM_QTY) {
                        Tyre.player.items[z][1] = Constants.MAX_ITEM_QTY;
                    }
                    return true;
                }
            }
            //find next available item spot
            for (int z = 0; z < Constants.ITEM_INVENTORY_SIZE; z++) {
                if (Tyre.player.items[z][0] < 0) {
                    Tyre.player.items[z] = new int[2];
                    Tyre.player.items[z][0] = itemId;
                    Tyre.player.items[z][1] = quantity;
                    sortItemInventory();
                    return true;
                }
            }
        }
        return false;
    }

    private void searchObject(int deltaX, int deltaY) {
        /*
        //hardcode to test
        itemfound_i = 0;
        itemfound_t = 0;
        changeState(gameState, 10);
        handleSearchObject(0);
        */
        
        for (p = 0; p < Tyre.roomObjects.length; p++) {
            /*
            if (Tyre.roomObjects[p].searchItem != -1 ) {
            } else {
            }
            */
            
            if (Tyre.roomObjects[p].searchItem != -1) {
                if (ResourceContainer.searches[Tyre.roomObjects[p].searchItem].flagIndex == -1 || (ResourceContainer.searches[Tyre.roomObjects[p].searchItem].flagDir == 0 && ResourceContainer.flags[ResourceContainer.searches[Tyre.roomObjects[p].searchItem].flagIndex] == true)
                        || (ResourceContainer.searches[Tyre.roomObjects[p].searchItem].flagDir == 1 && ResourceContainer.flags[ResourceContainer.searches[Tyre.roomObjects[p].searchItem].flagIndex] == false)) {
                    if (Tyre.player.cbot.tileCollision(Tyre.roomObjects[p].posX, Tyre.roomObjects[p].posY, 16, 16, Tyre.player.posX + deltaX, Tyre.player.posY + deltaY, Tyre.player.posX, Tyre.player.posY)) {
                        itemfound_i = 0;
                        itemfound_t = 0;
                        changeState(gameState, 10);
                        handleSearchObject(Tyre.roomObjects[p].searchItem);
                        p = Tyre.roomObjects.length;
                        break;
                    }
                }
            }
        }
    }

    public void handleSearchObject(int searchIndex) {
        /*
        for (i = 0; i < 3; i++) {
            itemfound_qty[i] = 0;
            itemfound_type[i] = -1;
        }
        */
        
        itemfound_type[0] = ResourceContainer.searches[searchIndex].item1;
        itemfound_type[1] = ResourceContainer.searches[searchIndex].item2;
        itemfound_type[2] = ResourceContainer.searches[searchIndex].item3;
        itemfound_qty[0] = ResourceContainer.searches[searchIndex].quantity1;
        itemfound_qty[1] = ResourceContainer.searches[searchIndex].quantity2;
        itemfound_qty[2] = ResourceContainer.searches[searchIndex].quantity3;

        //TODO - move quantity1.. and item1.. to arrays
        for (i = 0; i < 3; i++) {
            if (itemfound_qty[i] > 0) {
                itemfound_t++;
                tmpBool = true;
                if (storeItem(itemfound_type[i], itemfound_qty[i])) {
                    //if(ResourceContainer.searches[searchIndex].flagIndex > -1) {
                    //  ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex] = true;
                    //}
                }
            } else {
                itemfound_type[i] = -1;
            }
        }
        //for
        if (ResourceContainer.searches[searchIndex].flagIndex > -1) {
            Logger.wr("FLAGS: SEARCH: Index:" + ResourceContainer.searches[searchIndex].flagIndex + " = " + (!ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex]) + "< ResourceContainer.searches[searchIndex].flagIndex = (!ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex]) >");
            ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex] = (!ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex]);
        }
        if (ResourceContainer.searches[searchIndex].flagIndex2 > -1) {
            Logger.wr("FLAGS: SEARCH: Index:" + ResourceContainer.searches[searchIndex].flagIndex2 + " = " + (!ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex2]) + "< ResourceContainer.searches[searchIndex].flagIndex2 = (!ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex2]) >");
            ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex2] = (!ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex2]);
        }
        if (ResourceContainer.searches[searchIndex].flagIndex3 > -1) {
            Logger.wr("FLAGS: SEARCH: Index:" + ResourceContainer.searches[searchIndex].flagIndex3 + " = " + (!ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex3]) + "< ResourceContainer.searches[searchIndex].flagIndex3 = (!ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex3]) >");
            ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex3] = (!ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex3]);
        }
        if (ResourceContainer.searches[searchIndex].flagIndex4 > -1) {
            Logger.wr("FLAGS: SEARCH: Index:" + ResourceContainer.searches[searchIndex].flagIndex4 + " = " + (!ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex4]) + "< ResourceContainer.searches[searchIndex].flagIndex4 = (!ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex4]) >");
            ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex4] = (!ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex4]);
        }
        if (ResourceContainer.searches[searchIndex].flagIndex5 > -1) {
            Logger.wr("FLAGS: SEARCH: Index:" + ResourceContainer.searches[searchIndex].flagIndex5 + " = " + (!ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex5]) + "< ResourceContainer.searches[searchIndex].flagIndex5 = (!ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex5]) >");
            ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex5] = (!ResourceContainer.flags[ResourceContainer.searches[searchIndex].flagIndex5]);
        }
    }

    public static void clearFloatingHearts() {
        for (i = 0; i < MAX_FLOATINGHEARTS; i++) {
            fh_i[i] = 0;
        }
    }

    public static int newFloatingHeart(int x, int y, int col, int dam) {
        return newFloatingHeart(x, y, col, dam, 0);
    }

    public static int newFloatingHeart(int x, int y, int col, int dam, int type) {
        for (i = 0; i < MAX_FLOATINGHEARTS; i++) {
            if (fh_i[i] <= 0) {
                fh_x[i] = x;
                fh_y[i] = y;
                fh_c[i] = col;
                fh_d[i] = dam;
                fh_t[i] = type;
                fh_i[i] = FLOATINGHEARTS_FLOATTIME;
                return i;
            }
        }
        return -1;
    }

    public static void clearStatusMsgs() {
        for (i = 0; i < MAX_STATUSMSGS; i++) {
            smsg_i[i] = 0;
        }
    }

    public static int newStatusMsg(int x, int y, int col, String msg, int len) {
        for (i = 0; i < MAX_STATUSMSGS; i++) {
            if (smsg_i[i] <= 0) {
                smsg_x[i] = x;
                smsg_y[i] = y;
                smsg_c[i] = col;
                smsg_i[i] = len;
                smsg[i] = msg;
                return i;
            }
        }
        return -1;
    }

    public static int getPlayerBattleMatrixResult(int ap, int dl) {
        i = PLAYER_BATTLEMATRIX[(dl * 4) + ap];
        j = PLAYER_AP[ap];
        return (j + i);
    }

    public static int getEnemyBattleMatrixResult(int ap, int dl) {
        i = ENEMY_BATTLEMATRIX[(dl * 7) + ap];
        j = ResourceContainer.attackRolls[ap].max;
        return (j + i);
    }

    public int discardItem(int inventoryIndex, int quantity) {
        if (inventoryIndex == -1) {
            return -1;
        }
        //deduct one usage and shift up items
        if (Tyre.player.items[inventoryIndex][1] < quantity) {
            return -1;
        }
        Tyre.player.items[inventoryIndex][1] -= quantity;
        if (Tyre.player.items[inventoryIndex][1] == 0) {
            adjustInventory(inventoryIndex);
            return 0;
        }
        return Tyre.player.items[inventoryIndex][1];
    }

    public boolean checkActionDuringCharge() {
        tx = ((Tyre.player.cooldown * 90) / Tyre.player.PLAYERWAIT_TIME);
        //WITH SUPERCHARGE SUPPORT
        if (((Tyre.player.cooldown * 90) / Tyre.player.PLAYERWAIT_TIME) > SUPERCHARGE_TIME) {
            Tyre.player.cooldown += 10;
            if (Tyre.player.cooldown > Tyre.player.PLAYERWAIT_TIME) {
                Tyre.player.cooldown = Tyre.player.PLAYERWAIT_TIME;
            }
            return false;
        }
        //THIS IS A SUPERCHARGE HIT
        if (Tyre.player.cooldown > 0 && Tyre.player.cooldown < SUPERCHARGE_TIME) {
            superCharge = true;
        } else {
            superCharge = false;
        }
        return true;
    }

    public void entrancePlayer(int count) {
        if (Tyre.player.antisleep) {
            if (antisleepCount > 0) {
                antisleepCount--;
                //anti-sleep just wore off, make sure orb is not in possession before clearing
                if (antisleepCount <= 0) {
                    if (Tyre.player.antisleeporb == false && Tyre.player.antisleep == true) {
                        Tyre.player.antisleep = false;
                    }
                    antisleepCount = 0;
                }
            }
            return;
        }
        isEntranced = true;
        isEntrancedCount = count;
        battleModeState = 2;
        PlaySystemSound(Meta.FEEDBACK_HEADSET_BUTTON);
    }

    public void poisonPlayer() {
        if (Tyre.player.antipoison) {
            return;
        }
        isPoisoned = true;
        Tyre.player.PLAYERWAIT_TIME = DEF_PLAYERWAIT_TIME * 2;
        PlaySystemSound(Meta.FEEDBACK_HEADSET_BUTTON);
    }

    public void stunPlayer(int count) {
        if (Tyre.player.antistun) {
            return;
        }
        isStunned = true;
        isStunnedCount = count;
        battleModeState = 2;
        PlaySystemSound(Meta.FEEDBACK_HEADSET_BUTTON);
    }

    void drawCenteredText(int y, String str, Pen inPen, Font font) {
        inPen.drawText((SCREEN_WIDTH - font.getWidth(str)) / 2, y, str);
    }

    //resets the flags so that all adjacent rooms are rebuilt
    public void resetBuildFlags() {
        renderLayerOneFlag = renderLayerTwoFlag = true;
        for (i = 0; i < Tyre.adjBuildFlag.length; i++) {
            Tyre.adjBuildFlag[i] = true;
        }
    }

    public void resetPlayerHP() {
        //set life cells to 2
        Tyre.player.damage = Tyre.player.hp - 8;
    }

    public void checkCheatCodes(char c) {
        for (i = 0; i < cheatCodeInd.length; i++) {
            if (cheatCodes[((i * 6) + cheatCodeInd[i])] == c) {
                cheatCodeInd[i]++;
                if (cheatCodeInd[i] > 5) {
                    handleCheatCode(i);
                    cheatCodeInd[i] = 0;
                }
            } else {
                cheatCodeInd[i] = 0;
            }
        }
    }

    void handleCheatCode(int code) {
        switch (code) {
            //futcha  - give extra gold
            case 0:
                Tyre.player.goldCoins = Tyre.player.goldCoins = 9999;
                break;
            //heal all damages
            case 1:
                Tyre.player.damage = 0;
                isPoisoned = false;
                isEntranced = false;
                isStunned = false;
                isEntrancedCount = isStunnedCount = 0;
                Tyre.player.setRechargeSpeed();
                break;
            //give magic gear
            case 2:
                Tyre.player.equip_weapon(3, Constants.ITEM_SWORD4);
                if (!isPoisoned) {
                    Tyre.player.setRechargeSpeed();
                }
                Tyre.player.equip_armor(3, Constants.ITEM_ARMOR4);
                break;
            //enable warp
            case 3:
                Logger.wr("FLAGS: WARP: Index:" + MAGICAL_WARP_FLAG_ID + " = " + true + "< MAGICAL_WARP_FLAG_ID = true >");
                ResourceContainer.flags[MAGICAL_WARP_FLAG_ID] = true;
                break;
        }
        PlaySystemSound(Meta.CHARGER_DISCONNECTED);
    }
}