
namespace com.middlemindgames.TyreSkOrig {
    /*
    * Constants.java
    * Victor G. Brusca 01/16/2022
    * Created on October 24, 2004, 10:57 PM by Middlemind Games
    */
    public class Constants {
        //console strings
        public static int CONSTR_TORCH_WARNING = 129;
        public static int CONSTR_ANTISLEEPINGEST = 130;
        public static int CONSTR_CONTINUEMSG = 0;
        public static int CONSTR_MAINMENUMSG = 1;
        public static int CONSTR_CLOSEWIN = 2;

        //3-8
        public static int CONSTR_STATISTICS = 9;
        public static int CONSTR_NAME = 10;
        public static int CONSTR_GOLD = 11;
        public static int CONSTR_HP = 14;
        public static int CONSTR_AP = 15;
        public static int CONSTR_DP = 16;
        public static int CONSTR_SPEED = 18;
        public static int CONSTR_HITSTAKEN = 19;
        public static int CONSTR_HITSGIVEN = 20;
        public static int CONSTR_MISSES = 21;
        public static int CONSTR_RUN = 22;
        public static int CONSTR_ITEMS = 23;
        public static int CONSTR_KILLS = 25;
        public static int CONSTR_DEATHS = 26;
        public static int CONSTR_INVENTORY = 27;
        public static int CONSTR_INVENTORYEMPTY = 28;
        public static int CONSTR_AMOUNT = 30;
        public static int CONSTR_VALUE = 31;
        public static int CONSTR_BUY = 33;
        public static int CONSTR_SELL = 34;
        public static int CONSTR_YOUDIED = 40;
        public static int CONSTR_RETURNTOQUEST = 41;
        public static int CONSTR_MAINMENU = 44;
        public static int CONSTR_STARTNEW = 45;
        public static int CONSTR_CONTINUE = 46;
        public static int CONSTR_SETTINGS = 47;
        public static int CONSTR_HELP = 48;
        public static int CONSTR_PROLOGUE = 49;
        public static int CONSTR_WOULDYOULIKETOSELL = 52;
        public static int CONSTR_NOTENOUGHGOLD = 55;
        public static int CONSTR_YOUHAVEBOUGHT = 56;
        public static int CONSTR_SFOR = 57;       // (s) for
        public static int CONSTR_GOLDPERIOD = 58; // gold.
        public static int CONSTR_CANTSTORE = 59;
        public static int CONSTR_S = 60;
        public static int CONSTR_YOUHAVESOLD = 61;
        public static int CONSTR_HASGAINED = 63;
        public static int CONSTR_HPPERIOD = 64;
        public static int CONSTR_NOWHAS = 65;
        public static int CONSTR_HERBSHOPGREETING = 79;
        public static int CONSTR_FOUNDNOTHING = 80;
        public static int CONSTR_YES = 91;
        public static int CONSTR_NO = 92;
        public static int CONSTR_STARTNEWCONFIRM = 93;
        public static int CONSTR_WOULDYOULIKETOBUY = 95;
        public static int CONSTR_NOBODYTOTALK = 97;
        public static int CONSTR_NOTHINGOFINTEREST = 98;
        public static int CONSTR_POISONED = 99;
        public static int CONSTR_DODGES = 100;
        public static int CONSTR_STRHITSGIVEN = 103;
        public static int CONSTR_STRHITSTAKEN = 104;
        public static int CONSTR_PROLOGUETITLE = 105;
        public static int CONSTR_JOURNEYBEGIN = 106;
        public static int CONSTR_NAMECHAR = 107;
        public static int CONSTR_DONE = 108;
        public static int CONSTR_SOUNDS = 110;
        public static int CONSTR_VIBRATION = 111;
        public static int CONSTR_ON = 112;
        public static int CONSTR_OFF = 113;
        public static int CONSTR_AMBUSHED = 114;
        public static int CONSTR_PREPARETOFIGHT = 115;
        public static int CONSTR_CANTRUN = 116;
        public static int CONSTR_BLOCKED = 117;
        public static int CONSTR_MISSED = 118;
        public static int CONSTR_TOLLKEEPERGREETING = 120;
        public static int CONSTR_PAYTOLL = 121;
        public static int CONSTR_DONTPAYTOLL = 122;
        public static int CONSTR_PLAYERNOTPOISONED = 123;
        public static int CONSTR_PLAYERCUREPOISON = 124;
        public static int CONSTR_CANTSELLQUESTITEM = 125;
        public static int CONSTR_HPRESTORED = 126;
        public static int CONSTR_SAVEFORCOMBAT = 127;
        public static int CONSTR_NOTHINGHAPPENS = 128;
        public static int CONSTR_STATSHEADER1 = 131;
        public static int CONSTR_STATSHEADER2 = 132;
        public static int CONSTR_STATSHEADER3 = 133;
        public static int CONSTR_TREASUREFOUND = 134;

        //ITEM STR
        public const int ITEM_COMPASS = 0;
        public const int ITEM_RESTOREPOTION = 1;
        public const int ITEM_FERNSLETTER = 2;
        public const int ITEM_KALINSMESSAGE = 3;
        public const int ITEM_ANTIPOISONORB = 4;
        public const int ITEM_ANTISTUNORB = 5;
        public const int ITEM_BALEFIREORB = 6;
        public const int ITEM_ANTISLEEPORB = 7;
        public const int ITEM_HERBALANTIDOTE = 8;
        public const int ITEM_HERBALANTISLEEP = 9;
        public const int ITEM_HEALINGHERB = 10;
        public const int ITEM_TORCH = 11;
        public const int ITEM_GOLD = 12;
        public const int ITEM_HEALINGSALVE = 13;
        public const int ITEM_SWORD1 = 14;
        public const int ITEM_SWORD2 = 15;
        public const int ITEM_SWORD3 = 16;
        public const int ITEM_SWORD4 = 17;
        public const int ITEM_ARMOR1 = 18;
        public const int ITEM_ARMOR2 = 19;
        public const int ITEM_ARMOR3 = 20;
        public const int ITEM_ARMOR4 = 21;
        public const int ITEM_LIFECONTAINER = 22;
        public const int ITEM_PILEOFGOLD = 23;
        public const int ITEM_INJUREDSOLDIER = 24;
        public const int ITEM_MAP = 25;
        public const int ITEM_SAPLING = 26;
        public const int ITEM_MAJIKWERKSCERTIFICATE = 27;
        public const int ITEM_MADELINESLIST = 28;
        public const int ITEM_BAGOFSTUFF = 29;
        public const int ITEM_HERTERODSMYTHS = 30;
        public const int ITEM_STRANGELETTER = 31;
        public const int ITEM_CHRISTOFFS_LETTER = 32;
        public const int ITEM_ANTIQUE_SWORD = 33;
        public const int ITEM_HERTERODS_MYTHS_P80 = 34;
        public const int ITEM_SMUGGLERS_CODE = 35;
        public const int ITEM_BOFFENS_DIRECTIONS = 36;
        public const int ITEM_HERTERODS_MYTHS_P17 = 37;
        public const int ITEM_BALEFIREINSTRUCTIONS = 38;
        public const int ITEM_KRYSYSTREASURE = 39;
        public const int ITEM_JIMMYSTREASURE = 40;
        public const int ITEM_TORNPAGE = 41;
        public const int ITEM_WIZZARDOSNOTEWRONG = 42;
        public const int ITEM_WIZZARDOSNOTERIGHT = 43;
        public const int ITEM_MAGICWERKS_DOOR_PASS = 44;
        public const int ITEM_TOLLDIRECTIONS = 45;
        public const int ITEM_DELIVERYLETTER = 46;
        public const int ITEM_HERTERODS_MYTHS_P23 = 47;
        public const int ITEM_HERTERODS_MYTHS_P145 = 48;
        public const int ITEM_CARRIESTREASURE = 49;
        public const int ITEM_STEVES_NOTE = 50;
        public const int ITEM_MW_LOG_ENTRY = 51;
        public const int ITEM_MAGICAL_WARP = 52;
        public const int ITEM_SAPLING_LIFEFORCE = 53;
        public const int ITEM_BLIZZARD_STAFF = 54;
        public const int ITEM_HUGHS_TREASURENOTE = 55;
        public const int ITEM_INVENTORY_SIZE = 50;

        public const int ITEMTYPE_OTHER = 8;
        public const int ITEMTYPE_ANTISLEEP = 10;
        public const int ITEMTYPE_RESTOREPOTION = 11;
        public const int ITEMTYPE_READABLE = 12;
        public const int ITEMTYPE_SWORD = 14;
        public const int ITEMTYPE_ARMOR = 15;
        public const int ITEMTYPE_SPECIALCOMBAT = 16;

        //ROOM TYPES
        public const int ROOMTYPE_WILDERNESS = 0;
        public const int ROOMTYPE_INSIDE = 1;
        public const int ROOMTYPE_TUNNEL = 2;
        public const int ROOMTYPE_HOUSEMYSTERIOUS = 3;

        //NPC TYPES
        public const int NPCTYPE_SHOPKEEPER = 0;
        public const int NPCTYPE_PERSON = 1;
        public const int NPCTYPE_TOLLKEEPER = 3;
        public const int NPCTYPE_TREEFOLK = 4;
        public const int NPCTYPE_HEALER = 5;
        public static int PLAYER_NAME_INDEX = 4;
        public static int MALE_PLAYER_NAME_INDEX = 130;
        public static int FEMALE_PLAYER_NAME_INDEX = 91;
        public static int MUSIC_OUTSIDE = 2;
        public static int MUSIC_INSIDE = 1;
        public static int MUSIC_BATTLE = 0;
        public static int MUSIC_BOSSMODE = 5;
        public static int MUSIC_TUNNEL = 3;
        public static int MUSIC_MENU = 4;
        public static int BOSS_THIEF = 3;
        public static int BOSS_BLIZZARDQUEEN = 21;
        public static int BATTLE_FINAL = 76;
        public static int BATTLE_FINAL_BOSS1 = 27;
        public static int BATTLE_FINAL_BOSS2 = 25;
        public static int BATTLE_FINAL_BOSS3 = 24;
        public static int FINALBOSS_SLOWSPEED = 30;
        public static int TOTAL_HELP_PAGE_INDEXES = 30;
        public static int MAX_ITEM_QTY = 99;
    }
}