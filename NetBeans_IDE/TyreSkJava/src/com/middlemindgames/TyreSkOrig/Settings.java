// Copyright 2001-2004, Danger, Inc. All Rights Reserved.
// This file is subject to the Danger, Inc. Sample Code License,
// which is provided in the file SAMPLE_CODE_LICENSE.
// Copies are also available from http://developer.danger.com/

package com.middlemindgames.TyreSkOrig;

import danger.app.SettingsDB;
import danger.app.SettingsDBException;
import danger.app.AppResources;

/**
 * Manager for settings. This object is the ultimate arbiter of the settings for
 * the program. Before settings are displayed, they should be pulled from here
 * to get their most recent and correct state. The main reason to store them
 * here is for simplicity. There are lots of places where they are used, but it
 * helps to have one source for definitive information on their states. Here.
 *
 * Also, this is more self-contained, making it more flexible to be used as
 * template code for other applications.
 *
 * The Settings object is created as a singleton, because there should only ever
 * be one object that contains the settings states, and a second copy can only
 * lead to skinned knees and tears. It is referenced by getting the single
 * instance using Settings.getInstance().
 */
@SuppressWarnings("CallToPrintStackTrace")
public class Settings {
    static private boolean REQUEST_AUTO_SYNC = true;
    
    /**
     * The sSettings is the single reference to the single instance of this
     * singleton object. It is private and static to prevent any other code from
     * accessing it accidentally. It is initialized upon the first call to
     * getInstance().
     */
    private static Settings sSettings = null;
    
    /**
     * Always non-null; datastore for all the prefs.
     */
    private static SettingsDB sSettingsDB;
    
    /**
     * The local instance of our programmatic high score.
     */
    private static byte[] flags;
    private static byte[] serializedGameData;
    private static byte[] serializedStateData;
    private static byte[] serializedInvData;
    private static boolean[] flagsOut;
    private static int i;
    
    private static int ex;
    private static int lv;
    private static int charType;
    private static int totalHitsTaken;
    private static int totalHitsGiven;
    private static int totalMisses;
    private static int totalDodges;
    private static int totalGlancingBlowTaken;
    private static int totalGlancingBlowGiven;
    private static int totalSuperAttackTaken;
    private static int totalSuperAttackGiven;
    
    private static int totalRun;
    private static int totalKills;
    private static int totalItems;
    private static int totalDeaths;
    private static int damage;
    private static int speed;
    private static int hp;
    private static int ap;
    private static int mp;
    private static int mpUsage;
    private static int dp;
    private static int goldCoins;
    private static int posX;
    private static int posY;
    private static int dir;
    private static int weapon;
    private static int armor;
    private static int waittime;
    private static int currentRoomIdx;
    private static byte gameStart;
    private static int isPoisoned;
    private static int hasSound;
    private static int hasVibration;
    private static byte temp;
    private static boolean tmpBool;
    private static int gameSubState;
    private static int prevGameState;
    private static int herbShopSubState;
    private static int battleModeState;

    private static byte[][] items;
    private static byte tb1;
    private static byte tb2;
    private static int[] handOff;
    private static byte[] tmpBytes;
    private static String playerName;    
    private static int antiStun;
    private static int antiSleep;
    private static int antiSleepOrb;
    private static int antiPoison;
    
    /**
     * The parameters to be saved from the Settings:General window. These are
     * the local copies that are the final word as to state. They are loaded
     * either from programmatic defaults or from the DB entries when they exist.
     */
    
    // ------------------------------------------------------------------------
    
    /**
     * Constructor for Settings.
     *
     * Most of the defaults are stored in the resource file, as resource
     * settings.
     *
     * When created, this object must be the arbiter of all settings. So, to
     * properly reflect the default no-DataBase case, the instance variables are
     * initially set to invalid values. The App will then initialize them from
     * the SettingsScreen resource based defaults, and finally update each of
     * them to reflect any DB based settings. This way, any non-DB items are
     * left properly set to reflect the resource defaults.
     *
     * This constructor is private, so that it cannot be instantiated by any
     * other code. This makes it a singleton.
     */
    private Settings() {        
        //Create the object in any case. If nothing is saved, the DB will still
        //not exist, and there will be no network traffic. The referenced
        //object is always needed.
        sSettingsDB = new SettingsDB(Tyre.cApp.getString(AppResources.ID_APP_NAME), REQUEST_AUTO_SYNC);
        sSettingsDB.setAutoSyncNotifyee(Tyre.cApp);

        //For debugging purposes, dump the contents of the DB to the console.
        //dumpDB();
        
        //All instance variables are default initialized to invalid values, so
        //that they can be properly set to programmatic or resource based
        //defaults.
        flags = null;
        
        ex = -1;
        lv = -1;
        
        //default to 0 (male char)
        charType = 0;
        totalHitsTaken = -1;
        totalHitsGiven = -1;
        totalMisses = -1;
        totalDodges = -1;
        totalGlancingBlowTaken = -1;
        totalGlancingBlowGiven = -1;
        totalSuperAttackTaken = -1;
        totalSuperAttackGiven = -1;
        totalRun = -1;
        totalKills = -1;
        totalItems = -1;
        totalDeaths = -1;
        damage = -1;
        speed = -1;
        hp = -1;
        ap = -1;
        mp = -1;
        mpUsage = -1;
        dp = -1;
        goldCoins = -1;
        posX = -1;
        posY = -1;
        dir = -1;
        weapon = -1;
        armor = -1;
        waittime = -1;
        currentRoomIdx = -1;
        gameStart = -1;
        isPoisoned = -1;
        hasVibration = -1;
        hasSound = -1;
        gameSubState = -1;
        prevGameState = -1;
        herbShopSubState = -1;
        battleModeState = -1;
        playerName = "";
        items = new byte[50][];
        
        for(i = 0; i < items.length; i++) {
            items[i] = new byte[] {-1, -1};
        }
    }
    
    /**
     * getInstance() The routine returns the singleton reference to 'this'.
     * Because of the private constructor, and getInstance(), there will only be one instance of this object.
     * @return
     */
    public static Settings getInstance() {
        if (sSettings == null) {
            sSettings = new Settings();
        }
        return (sSettings);
    }
    
    /**
     * Getters for the various fields to be saved to the service.Each Getter handles the possible Exception, 
     * because a specific record may not have been written.
     *
     * If an exception is thrown, that means the programmatic default must still be used. In that case, the current
     * variable is the right choice, as it will have been initialized to a 
     * programmatic default.
     * @return
     */
    public byte[] getSerializedInvData() {
        try {
            tmpBytes = sSettingsDB.getBytes("serializedinvdata");
            serializedInvData = new byte[tmpBytes.length];
            System.arraycopy(tmpBytes,0,serializedInvData,0,tmpBytes.length);
        }catch(SettingsDBException ex1) {
            //ex1.printStackTrace();
        }
        return serializedInvData;
    }
    
    public byte[] getSerializedGameData() {
        try {
            tmpBytes = sSettingsDB.getBytes("serializedgamedata");
            serializedGameData = new byte[tmpBytes.length];
            System.arraycopy(tmpBytes,0,serializedGameData,0,tmpBytes.length);
        }catch(SettingsDBException ex) {
            //ex.printStackTrace();            
        }
        return serializedGameData;
    }

    public byte[] getSerializedStateData() {
        try {
            tmpBytes = sSettingsDB.getBytes("serializedstatedata");
            serializedStateData = new byte[tmpBytes.length];
            System.arraycopy(tmpBytes,0,serializedStateData,0,tmpBytes.length);
        }catch(SettingsDBException ex) {
            //ex.printStackTrace();            
        }
        return serializedStateData;
    }
    
    public boolean[] getFlags(boolean[] currentFlags) {
        try {
            tmpBytes = sSettingsDB.getBytes("flgs");
            if(tmpBytes != null) {
                if(currentFlags.length > tmpBytes.length) {
                    flagsOut = new boolean[currentFlags.length];
                    for(i = 0; i < flagsOut.length; i++) {
                        if(i < tmpBytes.length) {
                            if(tmpBytes[i] == (byte) 1) {
                                flagsOut[i] = true;
                            }else{
                                flagsOut[i] = false;
                            }
                        }else{
                            flagsOut[i] = false;
                        }
                    }
                }else if(currentFlags.length == tmpBytes.length) {
                    flagsOut = new boolean[tmpBytes.length];
                    for(i = 0; i < flagsOut.length; i++) {
                        if(tmpBytes[i] == (byte) 1) {
                            flagsOut[i] = true;
                        }else{
                            flagsOut[i] = false;
                        }
                    }
                }
            }else{
                flagsOut = null;
            }
        }catch(SettingsDBException ex) {
            //ex.printStackTrace();            
            flagsOut = null;
        }
        return flagsOut;
    }
    
    public boolean[] getFlags() {
        try {
            tmpBytes = sSettingsDB.getBytes("flgs");
            if(tmpBytes != null) {
                flagsOut = new boolean[tmpBytes.length];
                for(i = 0; i < flagsOut.length; i++) {
                    if(tmpBytes[i] == (byte) 1) {
                        flagsOut[i] = true;
                    }else{
                        flagsOut[i] = false;
                    }
                }
            }
        }catch(SettingsDBException ex) {
            //ex.printStackTrace();            
        }
        return flagsOut;
    }
    
    public String getPlayerName() {
        try {
            String tmpStr = sSettingsDB.getStringValue("pname");
            if(!(tmpStr == null)) {
                playerName = tmpStr;
            }else{
                playerName = "";
            }
        } catch (SettingsDBException ex1) {
            //ex1.printStackTrace();
        }
        return playerName;
    }
    
    /**
     * Assorted Setter functions for the various UI General:Settings.The 
     * philosophy for these setters is to update the DB immediately when the 
     * setter is called. Settings are done one at a time, and if a setting has 
     * not changed, it won't be rewritten. In fact, if it has not changed from 
     * its original programmatic default, it won't even be created, to save DB 
     * space.
     * 
     * The instance variables are initialized to an invalid state (-1 or null) 
     * to allow for them to be set to programmatic defaults, without having to 
     * create DB entries.
     * @param newInvData
     */
    public void setSerializedInvData(byte[] newInvData) {
        tmpBool = false;
        //serializedInvData
        if (newInvData == null || serializedInvData == null || newInvData.length != serializedInvData.length) {
            tmpBool = true;
        } else {
            for(i = 0; i < newInvData.length; i++ ) {
                if (newInvData[i] != serializedInvData[i]) {
                    tmpBool = true;
                    break;
                }
            }
        }        
        if(tmpBool) {
            serializedInvData = newInvData;
            sSettingsDB.setBytes("serializedinvdata", serializedInvData);
        }
        serializedInvData = newInvData;
    }
            
    public void setSerializedGameData(byte[] newGameState) {
        //if it's initialized and different from saved value, save it to the DB
        tmpBool = false;        
        if (newGameState == null || serializedGameData == null || newGameState.length != serializedGameData.length) {
            tmpBool = true;
        } else {
            for(i = 0; i < newGameState.length; i++ ) {
                if (newGameState[i] != serializedGameData[i]) {
                    tmpBool = true;
                    break;
                }
            }
        }        
        if(tmpBool) {
            serializedGameData = newGameState;
            sSettingsDB.setBytes("serializedgamedata", serializedGameData);
        }
        serializedGameData = newGameState;
    }

    public void setSerializedStateData(byte[] newGameState) {
        //if it's initialized and different from saved value, save it to the DB
        tmpBool = false;        
        if (newGameState == null || serializedStateData == null || newGameState.length != serializedStateData.length) {
            tmpBool = true;
        } else {
            for(i =0; i < newGameState.length; i++ ) {
                if (newGameState[i] != serializedStateData[i]) {
                    tmpBool = true;
                    break;
                }
            }
        }
        if(tmpBool) {
            serializedStateData = newGameState;
            sSettingsDB.setBytes("serializedstatedata", serializedStateData);
        }
        serializedStateData = newGameState;
    }
    
    public final void setFlags(boolean[] newFlags) {
        tmpBool = false;
        if(newFlags != null) {
            if(flags != null && newFlags.length == flags.length){
                for(i = 0; i < flags.length; i++) {
                    if(newFlags[i]) {
                        if(flags[i] != (byte) 1) {
                            tmpBool = true;
                            flags[i] = (byte) 1;
                        }
                    }else{
                        if(flags[i] != (byte) 0) {
                            tmpBool = true;
                            flags[i] = (byte) 0;
                        }
                    }
                }
            }else if(flags != null && newFlags.length > flags.length){
                tmpBool = true;
                flags = new byte[newFlags.length];
                for(i = 0; i < newFlags.length; i++) {
                    if(newFlags[i]) {
                        flags[i] = (byte)1;
                    }else{
                        flags[i] = (byte)0;
                    }
                }
            }else{
                flags = new byte[newFlags.length];
                for(i = 0; i < newFlags.length; i++) {
                    if(newFlags[i]) {
                        tmpBool = true;
                        flags[i] = (byte)1;
                    }else{
                        flags[i] = (byte)0;
                    }
                }
            }
        }
        if(tmpBool) {
            sSettingsDB.setBytes("flgs", flags);
        }
    }

    public SettingsDB getSettingsDB() {
        return Settings.sSettingsDB;
    }
    
    public void setPlayerName(String newPlayerName) {
        if (!playerName.equals("") && !newPlayerName.equals(playerName)) {
            sSettingsDB.setStringValue("pname", newPlayerName);
        }
        playerName = newPlayerName;
    }
    
    /**
     * Clear all the settings from the DB saved on the network. This is
     * something that should be done rarely, because it causes a lot of work for
     * the service. This routine is primarily here to allow for special cases,
     * like resetting the settings back to their programmatic defaults, with no
     * settings saved. Or for handling something terrible like a corrupted DB.
     *
     * In this program, it is called from a shortcut operation to reset the
     * program back to first states, in order to demonstrate how the program and
     * resource defaults are used before the DB is created.
     *
     * After the operation, the DB will be cleared, but the UI widget states
     * will be left as they were, and won't match the usual programmatic
     * defaults.
     *
     * There has been some confusion as to whether clearAll() actually works or
     * not. It does work as expected, but requires the usual 5 minute timeout
     * for DB changes to take effect. Rebooting the device is not necessary.
     */
    public void clearAllSettingsDB() {
        sSettingsDB.clearAll();
    }
    
    /**
     * For debugging purposes, it is useful to be able to specifically see what
     * is in the DB at a given moment. .dump() does not work under 1.1 or 2.0,
     * but gives full information under 2.3
     */
    public void dumpDB() {
        sSettingsDB.dumpDB();
    }
}