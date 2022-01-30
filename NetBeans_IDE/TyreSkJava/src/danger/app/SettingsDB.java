package danger.app;

import com.middlemindgames.TyreSkOrig.Constants;
import com.middlemindgames.TyreSkOrig.MainWindow;
import com.middlemindgames.TyreSkOrig.Tyre;
import danger.util.KeyValuePair;
import danger.util.Logger;
import java.util.Hashtable;
import java.util.Map;
import net.middlemind.MmgGameApiJava.MmgBase.MmgCfgFileEntry;
import net.middlemind.MmgGameApiJava.MmgBase.MmgCfgFileEntry.CfgEntryType;
import net.middlemind.MmgGameApiJava.MmgBase.MmgHelper;
import net.middlemind.MmgGameApiJava.MmgCore.GameSettings;

/*
 * SettingsDB.java
 * Victor G. Brusca 01/15/2022
 */
@SuppressWarnings({"OverridableMethodCallInConstructor", "UnusedAssignment"})
public class SettingsDB {
    public static String COL_DELIM = "`";
    public static String ROW_DELIM = "~";
    public static String KEY_VAL_DELIM = "!";
    
    public String appName;
    public boolean autoSync;
    public Application autoSyncNotifyee;
    public Map<String, byte[]> dataBin;
    public Map<String, String> dataStr;
    public Hashtable<String, MmgCfgFileEntry> settings;
    
    public SettingsDB() {
        appName = "Tyre";
        autoSync = false;
        autoSyncNotifyee = null;
        dataBin = new Hashtable();
        dataStr = new Hashtable();
        deserializeDataAll();
    }
    
    public SettingsDB(String AppName, boolean AutoSync) {
        appName = AppName;
        autoSync = AutoSync;
        autoSyncNotifyee = null;
        dataBin = new Hashtable();
        dataStr = new Hashtable();
        deserializeDataAll();        
    }
    
    public void serializeDataAll() {
        MmgCfgFileEntry entry;        
        entry = new MmgCfgFileEntry();
        entry.name = "dataBin";
        entry.cfgType = CfgEntryType.TYPE_STRING;
        entry.str = serializeDataBin();
        if(settings.containsKey(entry.name) == true) {
            settings.remove(entry.name);
        }
        settings.put(entry.name, entry);
        
        entry = new MmgCfgFileEntry();
        entry.name = "dataStr";
        entry.cfgType = CfgEntryType.TYPE_STRING;
        entry.str = serializeDataString();
        if(settings.containsKey(entry.name) == true) {
            settings.remove(entry.name);
        }
        settings.put(entry.name, entry);   
        
        MmgHelper.WriteClassConfigFile(GameSettings.CLASS_CONFIG_DIR + GameSettings.NAME + "/settings_db.txt", settings);
    }
    
    public void deserializeDataAll() {
        settings = MmgHelper.ReadClassConfigFile(GameSettings.CLASS_CONFIG_DIR + GameSettings.NAME + "/settings_db.txt");
        MmgCfgFileEntry entry;
        if(settings.containsKey("dataBin") == true) {
            entry = settings.get("dataBin");
            deserializeDataBin(entry.str);
        }
        
        if(settings.containsKey("dataStr") == true) {
            entry = settings.get("dataStr");
            deserializeDataString(entry.str);
        }        
    }
    
    public KeyValuePair deserializeDataBinEntry(String ret) {
        String[] kvp = ret.split(KEY_VAL_DELIM);
        String key = kvp[0];
        String[] vals = kvp[1].split(COL_DELIM);
        byte[] b = new byte[vals.length];
        for(int i = 0; i < vals.length; i++) {
            b[i] = (byte)Integer.parseInt(vals[i]);
        }
        Logger.wr("deserializeDataBinEntry: found entry: " + key);        
        return new KeyValuePair(key, b);
    }
    
    public String serializeDataBinEntry(byte[] val, String key) {
        String ret = "";
        for(int i = 0; i < val.length; i++) {
            ret += val[i];
            if(i < val.length - 1) {
                ret += COL_DELIM;
            }
        }
        return key += KEY_VAL_DELIM + ret;
    }
    
    public KeyValuePair deserializeDataStringEntry(String ret) {
        String[] kvp = ret.split(KEY_VAL_DELIM);
        String key = kvp[0];
        String val = kvp[1];
        Logger.wr("deserializeDataStringEntry: found entry: " + key + " with value: " + val);
        return new KeyValuePair(key, val);
    }    
    
    public String serializeDataStringEntry(String val, String key) {
        return key += KEY_VAL_DELIM + val;
    }    
    
    public String serializeDataBin() {
        String ret = "";
        int count = 0;
        int len = dataBin.size();
        Logger.wr("SettingsDB: serializeDataBin: Found " + len + " bin entries to serialize");
        for(String key : dataBin.keySet()) {
            byte[] b = dataBin.get(key);
            ret += serializeDataBinEntry(b, key);
            if(count < len - 1) {
                ret += ROW_DELIM;
            }
            count++;
        }
        return ret;
    }
    
    public void deserializeDataBin(String ret) {
        String[] vals = ret.split(ROW_DELIM);
        Logger.wr("SettingsDB: deserializeDataBin: Found " + vals.length + " bin entries to deserialize");
        KeyValuePair kvp;
        for(String entry : vals) {
            kvp = deserializeDataBinEntry(entry);
            if(dataBin.containsKey(kvp.key) == true) {
                dataBin.remove(kvp.key);
            }
            dataBin.put(kvp.key, (byte[])kvp.val);
        }
    }
    
    public String serializeDataString() {
        String ret = "";
        int count = 0;
        int len = dataStr.size();
        Logger.wr("SettingsDB: serializeDataString: Found " + len + " string entries to serialize");        
        for(String key : dataStr.keySet()) {
            String b = dataStr.get(key);
            ret += serializeDataStringEntry(b, key);
            if(count < len - 1) {
                ret += ROW_DELIM;
            }
            count++;
        }
        return ret;
    }   
    
    public void deserializeDataString(String ret) {
        String[] vals = ret.split(ROW_DELIM);
        Logger.wr("SettingsDB: deserializeDataString: Found " + vals.length + " bin entries to deserialize");        
        KeyValuePair kvp;
        for(String entry : vals) {
            kvp = deserializeDataStringEntry(entry);
            if(dataStr.containsKey(kvp.key) == true) {
                dataStr.remove(kvp.key);
            }            
            dataStr.put(kvp.key, (String)kvp.val);
        }
    }    
    
    public Application getAutoSyncNotifyee() {
        return autoSyncNotifyee;
    }
    
    public void setAutoSyncNotifyee(Application App) {
        autoSyncNotifyee = App;
    }
    
    public byte[] getBytes(String id) throws SettingsDBException {
        if(dataStr == null) {
            throw new SettingsDBException("getStringValue: String datastore is null)");        
        }
        
        if(dataBin.containsKey(id)) {
            return dataBin.get(id);
        } else {
            Logger.wr("getBytes: Could not find byte value:" + id);
            throw new SettingsDBException("getStringValue: String datastore is null)");
        }
    }
    
    public void setBytes(String id, byte[] data) {
        Logger.wr("setBytes: " + id);
        if(dataBin.containsKey(id) == true) {
            dataBin.remove(id);
        }
        dataBin.put(id, data);
    }
    
    public String getStringValue(String id) throws SettingsDBException {
        if(dataStr == null) {
            throw new SettingsDBException("getStringValue: String datastore is null)");        
        }
        
        if(dataStr.containsKey(id)) {
            return dataStr.get(id);
        } else {
            Logger.wr("getStringValue: Could not find string value:" + id);
            throw new SettingsDBException("getStringValue: String datastore is null)");  
        }
    }
    
    public void setStringValue(String id, String data) {
        if(dataStr.containsKey(id) == true) {
            dataStr.remove(id);
        }        
        dataStr.put(id, data);
    }
    
    public void clearAll() {
        dataStr.clear();
        dataBin.clear();
    }
        
    public void dumpDB() {
        Logger.wr("===== TyreSK -> SettingsDB.dumpDB =====");
        boolean printBytes = false;
        
        Logger.wr("");
        Logger.wr("===== SETTINGS DB STRINGS =====");
        for(String key : dataStr.keySet()) {
            Logger.wr(key + ": " + dataStr.get(key));
        }
        
        String prefix = "\t";
        Logger.wr("");
        Logger.wr("===== SETTINGS DB  =====");
        for(String key : dataBin.keySet()) {
            //limit to flags only for now
            if(key != null && key.equals("flgs") == true) {
                Logger.wr("");
                Logger.wr("_____ " + key + " _____");
                if(printBytes == true) {
                    byte[] data = dataBin.get(key);
                    String tmp = "";
                    for(int i  = 0; i < data.length; i++) {
                        tmp += prefix + "Index: " + i + " Val: " + data[i] + ", "; //System.lineSeparator();
                        if(i > 0 && i % 10 == 0) {
                            Logger.wr(tmp + System.lineSeparator());
                            tmp = "";
                        }
                    }

                    if(tmp.equals("") == false) {
                        Logger.wr(tmp + System.lineSeparator());
                        tmp = "";
                    }
                }
            }
        }
        
        Logger.wr("");
        Logger.wr("===== CONSOLE STRINGS =====");
        Logger.wr("CONSTR_TORCH_WARNING: " + Constants.CONSTR_TORCH_WARNING);
        Logger.wr("CONSTR_ANTISLEEPINGEST: " + Constants.CONSTR_ANTISLEEPINGEST);
        Logger.wr("CONSTR_CONTINUEMSG: " + Constants.CONSTR_CONTINUEMSG);
        Logger.wr("CONSTR_MAINMENUMSG: " + Constants.CONSTR_MAINMENUMSG);
        Logger.wr("CONSTR_CLOSEWIN: " + Constants.CONSTR_CLOSEWIN);
        
        Logger.wr("CONSTR_STATISTICS: " + Constants.CONSTR_STATISTICS);
        Logger.wr("CONSTR_NAME: " + Constants.CONSTR_NAME);
        Logger.wr("CONSTR_GOLD: " + Constants.CONSTR_GOLD);
        Logger.wr("CONSTR_HP: " + Constants.CONSTR_HP);
        Logger.wr("CONSTR_AP: " + Constants.CONSTR_AP);
        
        Logger.wr("CONSTR_DP: " + Constants.CONSTR_DP);
        Logger.wr("CONSTR_SPEED: " + Constants.CONSTR_SPEED);
        Logger.wr("CONSTR_HITSTAKEN: " + Constants.CONSTR_HITSTAKEN);
        Logger.wr("CONSTR_HITSGIVEN: " + Constants.CONSTR_HITSGIVEN);
        Logger.wr("CONSTR_MISSES: " + Constants.CONSTR_MISSES);

        Logger.wr("CONSTR_RUN: " + Constants.CONSTR_RUN);
        Logger.wr("CONSTR_ITEMS: " + Constants.CONSTR_ITEMS);
        Logger.wr("CONSTR_KILLS: " + Constants.CONSTR_KILLS);
        Logger.wr("CONSTR_DEATHS: " + Constants.CONSTR_DEATHS);
        Logger.wr("CONSTR_INVENTORY: " + Constants.CONSTR_INVENTORY);
        
        Logger.wr("CONSTR_INVENTORYEMPTY: " + Constants.CONSTR_INVENTORYEMPTY);
        Logger.wr("CONSTR_AMOUNT: " + Constants.CONSTR_AMOUNT);
        Logger.wr("CONSTR_VALUE: " + Constants.CONSTR_VALUE);
        Logger.wr("CONSTR_BUY: " + Constants.CONSTR_BUY);
        Logger.wr("CONSTR_SELL: " + Constants.CONSTR_SELL);
        
        Logger.wr("CONSTR_YOUDIED: " + Constants.CONSTR_YOUDIED);
        Logger.wr("CONSTR_RETURNTOQUEST: " + Constants.CONSTR_RETURNTOQUEST);
        Logger.wr("CONSTR_MAINMENU: " + Constants.CONSTR_MAINMENU);
        Logger.wr("CONSTR_STARTNEW: " + Constants.CONSTR_STARTNEW); 
        Logger.wr("CONSTR_CONTINUE: " + Constants.CONSTR_CONTINUE);
        
        Logger.wr("CONSTR_SETTINGS: " + Constants.CONSTR_SETTINGS);
        Logger.wr("CONSTR_HELP: " + Constants.CONSTR_HELP);
        Logger.wr("CONSTR_PROLOGUE: " + Constants.CONSTR_PROLOGUE);
        Logger.wr("CONSTR_WOULDYOULIKETOSELL: " + Constants.CONSTR_WOULDYOULIKETOSELL);
        Logger.wr("CONSTR_NOTENOUGHGOLD: " + Constants.CONSTR_NOTENOUGHGOLD);

        Logger.wr("CONSTR_YOUHAVEBOUGHT: " + Constants.CONSTR_YOUHAVEBOUGHT);
        Logger.wr("CONSTR_SFOR: " + Constants.CONSTR_SFOR);
        Logger.wr("CONSTR_GOLDPERIOD: " + Constants.CONSTR_GOLDPERIOD);
        Logger.wr("CONSTR_CANTSTORE: " + Constants.CONSTR_CANTSTORE);
        Logger.wr("CONSTR_S: " + Constants.CONSTR_S);
        
        Logger.wr("CONSTR_YOUHAVESOLD: " + Constants.CONSTR_YOUHAVESOLD);
        Logger.wr("CONSTR_HASGAINED: " + Constants.CONSTR_HASGAINED);
        Logger.wr("CONSTR_HPPERIOD: " + Constants.CONSTR_HPPERIOD);
        Logger.wr("CONSTR_NOWHAS: " + Constants.CONSTR_NOWHAS);
        Logger.wr("CONSTR_HERBSHOPGREETING: " + Constants.CONSTR_HERBSHOPGREETING);
        
        Logger.wr("CONSTR_FOUNDNOTHING: " + Constants.CONSTR_FOUNDNOTHING);
        Logger.wr("CONSTR_YES: " + Constants.CONSTR_YES);
        Logger.wr("CONSTR_NO: " + Constants.CONSTR_NO);
        Logger.wr("CONSTR_STARTNEWCONFIRM: " + Constants.CONSTR_STARTNEWCONFIRM);
        Logger.wr("CONSTR_WOULDYOULIKETOBUY: " + Constants.CONSTR_WOULDYOULIKETOBUY);

        Logger.wr("CONSTR_NOBODYTOTALK: " + Constants.CONSTR_NOBODYTOTALK);
        Logger.wr("CONSTR_NOTHINGOFINTEREST: " + Constants.CONSTR_NOTHINGOFINTEREST);
        Logger.wr("CONSTR_POISONED: " + Constants.CONSTR_POISONED);
        Logger.wr("CONSTR_DODGES: " + Constants.CONSTR_DODGES);
        Logger.wr("CONSTR_STRHITSGIVEN: " + Constants.CONSTR_STRHITSGIVEN);
        
        Logger.wr("CONSTR_STRHITSTAKEN: " + Constants.CONSTR_STRHITSTAKEN);
        Logger.wr("CONSTR_PROLOGUETITLE: " + Constants.CONSTR_PROLOGUETITLE);
        Logger.wr("CONSTR_JOURNEYBEGIN: " + Constants.CONSTR_JOURNEYBEGIN);
        Logger.wr("CONSTR_NAMECHAR: " + Constants.CONSTR_NAMECHAR);
        Logger.wr("CONSTR_DONE: " + Constants.CONSTR_DONE);
        
        Logger.wr("CONSTR_SOUNDS: " + Constants.CONSTR_SOUNDS);
        Logger.wr("CONSTR_VIBRATION: " + Constants.CONSTR_VIBRATION);
        Logger.wr("CONSTR_ON: " + Constants.CONSTR_ON);
        Logger.wr("CONSTR_OFF: " + Constants.CONSTR_OFF);
        Logger.wr("CONSTR_AMBUSHED: " + Constants.CONSTR_AMBUSHED);
        
        Logger.wr("CONSTR_PREPARETOFIGHT: " + Constants.CONSTR_PREPARETOFIGHT);
        Logger.wr("CONSTR_CANTRUN: " + Constants.CONSTR_CANTRUN);
        Logger.wr("CONSTR_BLOCKED: " + Constants.CONSTR_BLOCKED);
        Logger.wr("CONSTR_MISSED: " + Constants.CONSTR_MISSED);
        Logger.wr("CONSTR_TOLLKEEPERGREETING: " + Constants.CONSTR_TOLLKEEPERGREETING);

        Logger.wr("CONSTR_PAYTOLL: " + Constants.CONSTR_PAYTOLL);
        Logger.wr("CONSTR_DONTPAYTOLL: " + Constants.CONSTR_DONTPAYTOLL);
        Logger.wr("CONSTR_PLAYERNOTPOISONED: " + Constants.CONSTR_PLAYERNOTPOISONED);
        Logger.wr("CONSTR_PLAYERCUREPOISON: " + Constants.CONSTR_PLAYERCUREPOISON);
        Logger.wr("CONSTR_CANTSELLQUESTITEM: " + Constants.CONSTR_CANTSELLQUESTITEM);
        
        Logger.wr("CONSTR_HPRESTORED: " + Constants.CONSTR_HPRESTORED);
        Logger.wr("CONSTR_SAVEFORCOMBAT: " + Constants.CONSTR_SAVEFORCOMBAT);
        Logger.wr("CONSTR_NOTHINGHAPPENS: " + Constants.CONSTR_NOTHINGHAPPENS);
        Logger.wr("CONSTR_STATSHEADER1: " + Constants.CONSTR_STATSHEADER1);
        Logger.wr("CONSTR_STATSHEADER2: " + Constants.CONSTR_STATSHEADER2);
        Logger.wr("CONSTR_STATSHEADER3: " + Constants.CONSTR_STATSHEADER3);
        Logger.wr("CONSTR_TREASUREFOUND: " + Constants.CONSTR_TREASUREFOUND);

        Logger.wr("");
        Logger.wr("===== ITEM STRINGS =====");
        Logger.wr("ITEM_COMPASS: " + Constants.ITEM_COMPASS);
        Logger.wr("ITEM_RESTOREPOTION: " + Constants.ITEM_RESTOREPOTION);
        Logger.wr("ITEM_FERNSLETTER: " + Constants.ITEM_FERNSLETTER);
        Logger.wr("ITEM_KALINSMESSAGE: " + Constants.ITEM_KALINSMESSAGE);
        Logger.wr("ITEM_ANTIPOISONORB: " + Constants.ITEM_ANTIPOISONORB);

        Logger.wr("ITEM_ANTISTUNORB: " + Constants.ITEM_ANTISTUNORB);
        Logger.wr("ITEM_BALEFIREORB: " + Constants.ITEM_BALEFIREORB);
        Logger.wr("ITEM_ANTISLEEPORB: " + Constants.ITEM_ANTISLEEPORB);
        Logger.wr("ITEM_HERBALANTIDOTE: " + Constants.ITEM_HERBALANTIDOTE);
        Logger.wr("ITEM_HERBALANTISLEEP: " + Constants.ITEM_HERBALANTISLEEP);

        Logger.wr("ITEM_HEALINGHERB: " + Constants.ITEM_HEALINGHERB);
        Logger.wr("ITEM_TORCH: " + Constants.ITEM_TORCH);
        Logger.wr("ITEM_GOLD: " + Constants.ITEM_GOLD);
        Logger.wr("ITEM_HEALINGSALVE: " + Constants.ITEM_HEALINGSALVE);
        Logger.wr("ITEM_SWORD1: " + Constants.ITEM_SWORD1);

        Logger.wr("ITEM_SWORD2: " + Constants.ITEM_SWORD2);
        Logger.wr("ITEM_SWORD3: " + Constants.ITEM_SWORD3);
        Logger.wr("ITEM_SWORD4: " + Constants.ITEM_SWORD4);
        Logger.wr("ITEM_ARMOR1: " + Constants.ITEM_ARMOR1);
        Logger.wr("ITEM_ARMOR2: " + Constants.ITEM_ARMOR2);

        Logger.wr("ITEM_ARMOR3: " + Constants.ITEM_ARMOR3);
        Logger.wr("ITEM_ARMOR4: " + Constants.ITEM_ARMOR4);
        Logger.wr("ITEM_LIFECONTAINER: " + Constants.ITEM_LIFECONTAINER);
        Logger.wr("ITEM_PILEOFGOLD: " + Constants.ITEM_PILEOFGOLD);
        Logger.wr("ITEM_INJUREDSOLDIER: " + Constants.ITEM_INJUREDSOLDIER);

        Logger.wr("ITEM_MAP: " + Constants.ITEM_MAP);
        Logger.wr("ITEM_SAPLING: " + Constants.ITEM_SAPLING);
        Logger.wr("ITEM_MAJIKWERKSCERTIFICATE: " + Constants.ITEM_MAJIKWERKSCERTIFICATE);
        Logger.wr("ITEM_MADELINESLIST: " + Constants.ITEM_MADELINESLIST);
        Logger.wr("ITEM_BAGOFSTUFF: " + Constants.ITEM_BAGOFSTUFF);

        Logger.wr("ITEM_HERTERODSMYTHS: " + Constants.ITEM_HERTERODSMYTHS);
        Logger.wr("ITEM_STRANGELETTER: " + Constants.ITEM_STRANGELETTER);
        Logger.wr("ITEM_CHRISTOFFS_LETTER: " + Constants.ITEM_CHRISTOFFS_LETTER);
        Logger.wr("ITEM_ANTIQUE_SWORD: " + Constants.ITEM_ANTIQUE_SWORD);
        Logger.wr("ITEM_HERTERODS_MYTHS_P80: " + Constants.ITEM_HERTERODS_MYTHS_P80);

        Logger.wr("ITEM_SMUGGLERS_CODE: " + Constants.ITEM_SMUGGLERS_CODE);
        Logger.wr("ITEM_BOFFENS_DIRECTIONS: " + Constants.ITEM_BOFFENS_DIRECTIONS);
        Logger.wr("ITEM_HERTERODS_MYTHS_P17: " + Constants.ITEM_HERTERODS_MYTHS_P17);
        Logger.wr("ITEM_BALEFIREINSTRUCTIONS: " + Constants.ITEM_BALEFIREINSTRUCTIONS);
        Logger.wr("ITEM_KRYSYSTREASURE: " + Constants.ITEM_KRYSYSTREASURE);

        Logger.wr("ITEM_JIMMYSTREASURE: " + Constants.ITEM_JIMMYSTREASURE);
        Logger.wr("ITEM_TORNPAGE: " + Constants.ITEM_TORNPAGE);
        Logger.wr("ITEM_WIZZARDOSNOTEWRONG: " + Constants.ITEM_WIZZARDOSNOTEWRONG);
        Logger.wr("ITEM_WIZZARDOSNOTERIGHT: " + Constants.ITEM_WIZZARDOSNOTERIGHT);
        Logger.wr("ITEM_MAGICWERKS_DOOR_PASS: " + Constants.ITEM_MAGICWERKS_DOOR_PASS);

        Logger.wr("ITEM_TOLLDIRECTIONS: " + Constants.ITEM_TOLLDIRECTIONS);
        Logger.wr("ITEM_DELIVERYLETTER: " + Constants.ITEM_DELIVERYLETTER);
        Logger.wr("ITEM_HERTERODS_MYTHS_P23: " + Constants.ITEM_HERTERODS_MYTHS_P23);
        Logger.wr("ITEM_HERTERODS_MYTHS_P145: " + Constants.ITEM_HERTERODS_MYTHS_P145);
        Logger.wr("ITEM_CARRIESTREASURE: " + Constants.ITEM_CARRIESTREASURE);

        Logger.wr("ITEM_STEVES_NOTE: " + Constants.ITEM_STEVES_NOTE);
        Logger.wr("ITEM_MW_LOG_ENTRY: " + Constants.ITEM_MW_LOG_ENTRY);
        Logger.wr("ITEM_MAGICAL_WARP: " + Constants.ITEM_MAGICAL_WARP);
        Logger.wr("ITEM_SAPLING_LIFEFORCE: " + Constants.ITEM_SAPLING_LIFEFORCE);
        Logger.wr("ITEM_BLIZZARD_STAFF: " + Constants.ITEM_BLIZZARD_STAFF);

        Logger.wr("ITEM_HUGHS_TREASURENOTE: " + Constants.ITEM_HUGHS_TREASURENOTE);
        Logger.wr("ITEM_INVENTORY_SIZE: " + Constants.ITEM_INVENTORY_SIZE);
        
        Logger.wr("");
        Logger.wr("===== ITEM TYPES =====");
        Logger.wr("ITEMTYPE_OTHER: " + Constants.ITEMTYPE_OTHER);
        Logger.wr("ITEMTYPE_ANTISLEEP: " + Constants.ITEMTYPE_ANTISLEEP);
        Logger.wr("ITEMTYPE_RESTOREPOTION: " + Constants.ITEMTYPE_RESTOREPOTION);
        Logger.wr("ITEMTYPE_READABLE: " + Constants.ITEMTYPE_READABLE);
        Logger.wr("ITEMTYPE_SWORD: " + Constants.ITEMTYPE_SWORD);
        Logger.wr("ITEMTYPE_ARMOR: " + Constants.ITEMTYPE_ARMOR);
        Logger.wr("ITEMTYPE_SPECIALCOMBAT: " + Constants.ITEMTYPE_SPECIALCOMBAT);
        
        Logger.wr("");
        Logger.wr("===== ROOM TYPES =====");
        Logger.wr("ROOMTYPE_WILDERNESS: " + Constants.ROOMTYPE_WILDERNESS);
        Logger.wr("ROOMTYPE_INSIDE: " + Constants.ROOMTYPE_INSIDE);
        Logger.wr("ROOMTYPE_TUNNEL: " + Constants.ROOMTYPE_TUNNEL);
        Logger.wr("ROOMTYPE_HOUSEMYSTERIOUS: " + Constants.ROOMTYPE_HOUSEMYSTERIOUS);

        Logger.wr("");
        Logger.wr("===== NPC TYPES =====");
        Logger.wr("NPCTYPE_SHOPKEEPER: " + Constants.NPCTYPE_SHOPKEEPER);
        Logger.wr("NPCTYPE_PERSON: " + Constants.NPCTYPE_PERSON);
        Logger.wr("NPCTYPE_TOLLKEEPER: " + Constants.NPCTYPE_TOLLKEEPER);
        Logger.wr("NPCTYPE_TREEFOLK: " + Constants.NPCTYPE_TREEFOLK);
        Logger.wr("NPCTYPE_HEALER: " + Constants.NPCTYPE_HEALER);
        
        Logger.wr("");
        Logger.wr("===== PLAYER INFO =====");
        Logger.wr("PLAYER_NAME_INDEX: " + Constants.PLAYER_NAME_INDEX);
        Logger.wr("MALE_PLAYER_NAME_INDEX: " + Constants.MALE_PLAYER_NAME_INDEX);
        Logger.wr("FEMALE_PLAYER_NAME_INDEX: " + Constants.FEMALE_PLAYER_NAME_INDEX);
        
        Logger.wr("");
        Logger.wr("===== MUSIC TYPES =====");        
        Logger.wr("MUSIC_OUTSIDE: " + Constants.MUSIC_OUTSIDE);
        Logger.wr("MUSIC_INSIDE: " + Constants.MUSIC_INSIDE);
        Logger.wr("MUSIC_BATTLE: " + Constants.MUSIC_BATTLE);
        Logger.wr("MUSIC_BOSSMODE: " + Constants.MUSIC_BOSSMODE);
        Logger.wr("MUSIC_TUNNEL: " + Constants.MUSIC_TUNNEL);
        Logger.wr("MUSIC_MENU: " + Constants.MUSIC_MENU);

        Logger.wr("");
        Logger.wr("===== BOSSES =====");
        Logger.wr("BOSS_THIEF: " + Constants.BOSS_THIEF);
        Logger.wr("BOSS_BLIZZARDQUEEN: " + Constants.BOSS_BLIZZARDQUEEN);
        
        Logger.wr("");
        Logger.wr("===== FINAL BATTLE =====");        
        Logger.wr("BATTLE_FINAL: " + Constants.BATTLE_FINAL);
        Logger.wr("BATTLE_FINAL_BOSS1: " + Constants.BATTLE_FINAL_BOSS1);
        Logger.wr("BATTLE_FINAL_BOSS2: " + Constants.BATTLE_FINAL_BOSS2);
        Logger.wr("BATTLE_FINAL_BOSS3: " + Constants.BATTLE_FINAL_BOSS3);
        
        Logger.wr("");
        Logger.wr("===== OTHER =====");        
        Logger.wr("FINALBOSS_SLOWSPEED: " + Constants.FINALBOSS_SLOWSPEED);
        Logger.wr("TOTAL_HELP_PAGE_INDEXES: " + Constants.TOTAL_HELP_PAGE_INDEXES);
        Logger.wr("MAX_ITEM_QTY: " + Constants.MAX_ITEM_QTY);

        Logger.wr("");
        Logger.wr("===== MAIN WINDOW OTHER =====");
        Logger.wr("TREEFOLK_IMAGEID: " + MainWindow.TREEFOLK_IMAGEID);
        Logger.wr("MAGICAL_WARP_FLAG_ID: " + MainWindow.MAGICAL_WARP_FLAG_ID);
        Logger.wr("characterName: " + MainWindow.characterName);
        Logger.wr("PROLOGUE_STRING_INDEX: " + MainWindow.PROLOGUE_STRING_INDEX);
        Logger.wr("EPILOGUE_STRING_INDEX: " + MainWindow.EPILOGUE_STRING_INDEX);
        
        Logger.wr("");
        Logger.wr("===== TYRE OTHER =====");
        Logger.wr("currentRoomIdx: " + Tyre.currentRoomIdx);
    }
}