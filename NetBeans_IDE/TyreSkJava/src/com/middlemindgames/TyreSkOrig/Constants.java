package com.middlemindgames.TyreSkOrig;

/*
 * Constants.java
 * Victor G. Brusca 01/16/2022
 * Created on October 24, 2004, 10:57 PM by Middlemind Games
 */
public class Constants {
    //console strings
    public static final int CONSTR_TORCH_WARNING = 129;
    public static final int CONSTR_ANTISLEEPINGEST = 130;
    public static final int CONSTR_CONTINUEMSG = 0;
    public static final int CONSTR_MAINMENUMSG = 1;
    public static final int CONSTR_CLOSEWIN = 2;

    //3-8
    public static final int CONSTR_STATISTICS = 9;
    public static final int CONSTR_NAME = 10;
    public static final int CONSTR_GOLD = 11;
    public static final int CONSTR_HP = 14;
    public static final int CONSTR_AP = 15;
    public static final int CONSTR_DP = 16;
    public static final int CONSTR_SPEED = 18;
    public static final int CONSTR_HITSTAKEN = 19;
    public static final int CONSTR_HITSGIVEN = 20;
    public static final int CONSTR_MISSES = 21;
    public static final int CONSTR_RUN = 22;
    public static final int CONSTR_ITEMS = 23;
    public static final int CONSTR_KILLS = 25;
    public static final int CONSTR_DEATHS = 26;
    public static final int CONSTR_INVENTORY = 27;
    public static final int CONSTR_INVENTORYEMPTY = 28;
    public static final int CONSTR_AMOUNT = 30;
    public static final int CONSTR_VALUE = 31;
    public static final int CONSTR_BUY = 33;
    public static final int CONSTR_SELL = 34;
    public static final int CONSTR_YOUDIED = 40;
    public static final int CONSTR_RETURNTOQUEST = 41;
    public static final int CONSTR_MAINMENU = 44;
    public static final int CONSTR_STARTNEW = 45;
    public static final int CONSTR_CONTINUE = 46;
    public static final int CONSTR_SETTINGS = 47;
    public static final int CONSTR_HELP = 48;
    public static final int CONSTR_PROLOGUE = 49;
    public static final int CONSTR_WOULDYOULIKETOSELL = 52;
    public static final int CONSTR_NOTENOUGHGOLD = 55;
    public static final int CONSTR_YOUHAVEBOUGHT = 56;
    public static final int CONSTR_SFOR = 57;       // (s) for
    public static final int CONSTR_GOLDPERIOD = 58; // gold.
    public static final int CONSTR_CANTSTORE = 59;
    public static final int CONSTR_S = 60;
    public static final int CONSTR_YOUHAVESOLD = 61;
    public static final int CONSTR_HASGAINED = 63;
    public static final int CONSTR_HPPERIOD = 64;
    public static final int CONSTR_NOWHAS = 65;
    public static final int CONSTR_HERBSHOPGREETING = 79;
    public static final int CONSTR_FOUNDNOTHING = 80;
    public static final int CONSTR_YES = 91;
    public static final int CONSTR_NO = 92;
    public static final int CONSTR_STARTNEWCONFIRM = 93;
    public static final int CONSTR_WOULDYOULIKETOBUY = 95;
    public static final int CONSTR_NOBODYTOTALK = 97;
    public static final int CONSTR_NOTHINGOFINTEREST = 98;
    public static final int CONSTR_POISONED = 99;
    public static final int CONSTR_DODGES = 100;
    public static final int CONSTR_STRHITSGIVEN = 103;
    public static final int CONSTR_STRHITSTAKEN = 104;
    public static final int CONSTR_PROLOGUETITLE = 105;
    public static final int CONSTR_JOURNEYBEGIN = 106;
    public static final int CONSTR_NAMECHAR = 107;
    public static final int CONSTR_DONE = 108;
    public static final int CONSTR_SOUNDS = 110;
    public static final int CONSTR_VIBRATION = 111;
    public static final int CONSTR_ON = 112;
    public static final int CONSTR_OFF = 113;
    public static final int CONSTR_AMBUSHED = 114;
    public static final int CONSTR_PREPARETOFIGHT = 115;
    public static final int CONSTR_CANTRUN = 116;
    public static final int CONSTR_BLOCKED = 117;
    public static final int CONSTR_MISSED = 118;
    public static final int CONSTR_TOLLKEEPERGREETING = 120;
    public static final int CONSTR_PAYTOLL = 121;
    public static final int CONSTR_DONTPAYTOLL = 122;
    public static final int CONSTR_PLAYERNOTPOISONED = 123;
    public static final int CONSTR_PLAYERCUREPOISON = 124;
    public static final int CONSTR_CANTSELLQUESTITEM = 125;
    public static final int CONSTR_HPRESTORED = 126;
    public static final int CONSTR_SAVEFORCOMBAT = 127;
    public static final int CONSTR_NOTHINGHAPPENS = 128;
    public static final int CONSTR_STATSHEADER1 = 131;
    public static final int CONSTR_STATSHEADER2 = 132;
    public static final int CONSTR_STATSHEADER3 = 133;
    public static final int CONSTR_TREASUREFOUND = 134;

    //ITEM STR
    public static final int ITEM_COMPASS = 0;
    public static final int ITEM_RESTOREPOTION = 1;
    public static final int ITEM_FERNSLETTER = 2;
    public static final int ITEM_KALINSMESSAGE = 3;
    public static final int ITEM_ANTIPOISONORB = 4;
    public static final int ITEM_ANTISTUNORB = 5;
    public static final int ITEM_BALEFIREORB = 6;
    public static final int ITEM_ANTISLEEPORB = 7;
    public static final int ITEM_HERBALANTIDOTE = 8;
    public static final int ITEM_HERBALANTISLEEP = 9;
    public static final int ITEM_HEALINGHERB = 10;
    public static final int ITEM_TORCH = 11;
    public static final int ITEM_GOLD = 12;
    public static final int ITEM_HEALINGSALVE = 13;
    public static final int ITEM_SWORD1 = 14;
    public static final int ITEM_SWORD2 = 15;
    public static final int ITEM_SWORD3 = 16;
    public static final int ITEM_SWORD4 = 17;
    public static final int ITEM_ARMOR1 = 18;
    public static final int ITEM_ARMOR2 = 19;
    public static final int ITEM_ARMOR3 = 20;
    public static final int ITEM_ARMOR4 = 21;
    public static final int ITEM_LIFECONTAINER = 22;
    public static final int ITEM_PILEOFGOLD = 23;
    public static final int ITEM_INJUREDSOLDIER = 24;
    public static final int ITEM_MAP = 25;
    public static final int ITEM_SAPLING = 26;
    public static final int ITEM_MAJIKWERKSCERTIFICATE = 27;
    public static final int ITEM_MADELINESLIST = 28;
    public static final int ITEM_BAGOFSTUFF = 29;
    public static final int ITEM_HERTERODSMYTHS = 30;
    public static final int ITEM_STRANGELETTER = 31;
    public static final int ITEM_CHRISTOFFS_LETTER = 32;
    public static final int ITEM_ANTIQUE_SWORD = 33;
    public static final int ITEM_HERTERODS_MYTHS_P80 = 34;
    public static final int ITEM_SMUGGLERS_CODE = 35;
    public static final int ITEM_BOFFENS_DIRECTIONS = 36;
    public static final int ITEM_HERTERODS_MYTHS_P17 = 37;
    public static final int ITEM_BALEFIREINSTRUCTIONS = 38;
    public static final int ITEM_KRYSYSTREASURE = 39;
    public static final int ITEM_JIMMYSTREASURE = 40;
    public static final int ITEM_TORNPAGE = 41;
    public static final int ITEM_WIZZARDOSNOTEWRONG = 42;
    public static final int ITEM_WIZZARDOSNOTERIGHT = 43;
    public static final int ITEM_MAGICWERKS_DOOR_PASS = 44;
    public static final int ITEM_TOLLDIRECTIONS = 45;
    public static final int ITEM_DELIVERYLETTER = 46;
    public static final int ITEM_HERTERODS_MYTHS_P23 = 47;
    public static final int ITEM_HERTERODS_MYTHS_P145 = 48;
    public static final int ITEM_CARRIESTREASURE = 49;
    public static final int ITEM_STEVES_NOTE = 50;
    public static final int ITEM_MW_LOG_ENTRY = 51;
    public static final int ITEM_MAGICAL_WARP = 52;
    public static final int ITEM_SAPLING_LIFEFORCE = 53;
    public static final int ITEM_BLIZZARD_STAFF = 54;
    public static final int ITEM_HUGHS_TREASURENOTE = 55;
    public static final int ITEM_INVENTORY_SIZE = 50;

    public static final int ITEMTYPE_OTHER = 8;
    public static final int ITEMTYPE_ANTISLEEP = 10;
    public static final int ITEMTYPE_RESTOREPOTION = 11;
    public static final int ITEMTYPE_READABLE = 12;
    public static final int ITEMTYPE_SWORD = 14;
    public static final int ITEMTYPE_ARMOR = 15;
    public static final int ITEMTYPE_SPECIALCOMBAT = 16;

    //ROOM TYPES
    public static final int ROOMTYPE_WILDERNESS = 0;
    public static final int ROOMTYPE_INSIDE = 1;
    public static final int ROOMTYPE_TUNNEL = 2;
    public static final int ROOMTYPE_HOUSEMYSTERIOUS = 3;

    //NPC TYPES
    public static final int NPCTYPE_SHOPKEEPER = 0;
    public static final int NPCTYPE_PERSON = 1;
    public static final int NPCTYPE_TOLLKEEPER = 3;
    public static final int NPCTYPE_TREEFOLK = 4;
    public static final int NPCTYPE_HEALER = 5;
    public static final int PLAYER_NAME_INDEX = 4;
    public static final int MALE_PLAYER_NAME_INDEX = 130;
    public static final int FEMALE_PLAYER_NAME_INDEX = 91;
    public static final int MUSIC_OUTSIDE = 2;
    public static final int MUSIC_INSIDE = 1;
    public static final int MUSIC_BATTLE = 0;
    public static final int MUSIC_BOSSMODE = 5;
    public static final int MUSIC_TUNNEL = 3;
    public static final int MUSIC_MENU = 4;
    public static final int BOSS_THIEF = 3;
    public static final int BOSS_BLIZZARDQUEEN = 21;
    public static final int BATTLE_FINAL = 76;
    public static final int BATTLE_FINAL_BOSS1 = 27;
    public static final int BATTLE_FINAL_BOSS2 = 25;
    public static final int BATTLE_FINAL_BOSS3 = 24;
    public static final int FINALBOSS_SLOWSPEED = 30;
    public static final int TOTAL_HELP_PAGE_INDEXES = 30;
    public static final int MAX_ITEM_QTY = 99;
}