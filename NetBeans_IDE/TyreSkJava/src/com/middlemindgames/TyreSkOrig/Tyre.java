package com.middlemindgames.TyreSkOrig;

import danger.ui.Color;
import danger.app.*;
import danger.ui.*;
import danger.audio.*;
import com.middlemindgames.dat.*;
import danger.util.ByteArray;
import danger.util.Decompressor;
import danger.util.Logger;
import net.middlemind.MmgGameApiJava.MmgBase.MmgColor;

/*
 * Tyre.java 1.1 03/02/2006
 * Victor G. Brusca 01/16/2022
 * Copyright 2006, Middlemind Games, LLC.  All Rights Reserved.
 * Middlemind Games PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/**
 * A Tyre is the extension of the Application class that runs on the
 * SideKick 2.
 *
 * @author Victor G. Brusca
 * @version 1.1, 03/01/2006
 */
@SuppressWarnings({"CallToPrintStackTrace", "LocalVariableHidesMemberVariable", "UnusedAssignment"})
public class Tyre extends Application {
    public static Settings theSettings;
    public static final int TILESIZE = 8;
    public static final int WIDTH = 30;
    public static final int HEIGHT = 17;
    public static MainWindow mWindow;
    public static Timer mTimer = null;
    private static Resource res;
    public static ResourceContainer rc;
    public static Bitmap logoImage;
    public static Bitmap aboutlogoImage;
    private static Bitmap lrgMessageContent;    
    public static Sequence[] sequences;
    public static Bitmap[] bitmaps;
    public static Bitmap[] consoleBitmaps;
    public static character player;
    public static character charselector;
    public static npc[] roomChars;
    public static int roomCharCount;
    public static tyreObject[] roomObjects;
    public static tyreObject[] linkObjects;
    public static tyreObject[] canWalkDynamic;
    public static int roomObjectCount;
    public static int linkObjectCount;
    public static int canWalkDynamicCount;
    public static int[] rObjects;
    public static tileRef[] cantWalk;
    public static int cantWalkCount;
    public static int currentRoomIdx;
    public static Bitmap currentRoom;
    public static Bitmap layerTwo;
    public static Bitmap[] adjRoom;
    public static Bitmap[] adjLayerTwo;
    public static boolean[] adjBuildFlag;

    private static int i;
    private static int j;
    private static int k;
    private static int z;
    private static int len;
    private static int max;
    private static int tmp;
    private static int lower;
    private static int upper;
    public static boolean dataLoaded;
    private static boolean consumed;
    public static loadDatThread datLoader;
    private static roomData rDat;
    private static roomData[] roomDataInfo;
    private static Pen pen;
    private static int x;
    private static int y;
    private static int w;
    private static int h;
    private static int xw;
    private static int yh;
    private static int sTmp;
    private static int qsI;
    private static tyreObject tmpObj;
    public static boolean gameStart;
    public static Tyre cApp;
    public static boolean firstRun;

    // new variable to track if restore Battle needs to be called
    public static boolean firstContinue;
    public static boolean hasLink;
    public static boolean hasCanWalkDynamic;
    public static double speedMultiplier;
    public static int defaultSpeed;
    public static item[] itemsMirror;
    public static boolean[] flagsMirror;
    public static byte[] serializedGameDataMirror;
    public static byte[] serializedStateDataMirror;
    public static byte[] serializedInvDataMirror;

    // used to prevent exceptions in the app jump function
    public static boolean suspended;
    public static int lastState;
    public static int lastMenuState;

    public static Bitmap idSmallIcon;
    public static Bitmap idLargeIcon;
    public static Bitmap idSplashScreen;
    public static Resource bgClear;    
    
    public Tyre() {
        cApp = this;
        dataLoaded = false;
        gameStart = true;
        datLoader = new loadDatThread();
        mWindow = new MainWindow();
        currentRoomIdx = 0;
        currentRoom = null;
        layerTwo = null;

        // bitmaps to hold the adjacent rooms
        adjRoom = new Bitmap[8];
        adjLayerTwo = new Bitmap[8];
        adjBuildFlag = new boolean[8];
        for (i = 0; i < adjRoom.length; i++) {
            adjRoom[i] = null;
            adjLayerTwo[i] = null;
            adjBuildFlag[i] = true;
        }

        lrgMessageContent = null;
        firstRun = true;
        logoImage = getBitmap(Resources.mmglogo);
        aboutlogoImage = getBitmap(Resources.aboutlogo);

        idSmallIcon = getBitmap(AppResources.ID_SMALL_ICON);
        idLargeIcon = getBitmap(AppResources.ID_LARGE_ICON);
        idSplashScreen = getBitmap(AppResources.ID_SPLASH_SCREEN);
        currentRoom = new Bitmap(240, 136, MmgColor.GetBlack());
        layerTwo = new Bitmap(240, 136);
        bgClear = getResource(AppResources.ID_TYRE_BG_CLEAR_APP, AppResources.ID_BIN_RES_TYPE);
        
        try {
            speedMultiplier = Double.valueOf(Tyre.cApp.getString(Resources.SPEED_MULTIPLIER).trim()).doubleValue();
        } catch (NumberFormatException nfe) {
            speedMultiplier = 1.0;
        }

        try {
            defaultSpeed = Integer.valueOf(Tyre.cApp.getString(Resources.PLAYER_SPEED).trim()).intValue();
        } catch (NumberFormatException nfe) {
            defaultSpeed = 4;
        }
    }

    @Override
    public boolean receiveEvent(Event e) {
        consumed = false;
        switch (e.type) {
            case Event.EVENT_TIMER:
                mWindow.TimerTick();
                consumed = true;
                break;
        }
        if (consumed) {
            return true;
        } else {
            return (super.receiveEvent(e));
        }
    }

    public void resume() {
        TyreAudio.prepareAudio();
        mWindow.changeState(8);
        mWindow.show();
        if (null == mTimer) {
            mTimer = new Timer(20, true, this);
        }
        mTimer.start();
        MainWindow.datKilled = false;
        loadData();
        suspended = false;
    }

    public void suspend() {
        Logger.wr("Tyre: suspend");
        suspended = true;
        if (MainWindow.gameState == 8) {
            if (MainWindow.datKilled == false) {
                MainWindow.datKilled = true;
                killDatLoad();
            }
        } else {
            if (!gameStart) {
                storeSettings();
                serializedGameDataMirror = storeSerializedGameData();
                serializedInvDataMirror = storeSerializedInvData();
                serializedStateDataMirror = storeSerializedStateData();

                //VGB 01/17/2022
                theSettings.getSettingsDB().serializeDataAll();                
            }
        }

        if (mTimer != null) {
            mTimer.stop();
            cancelEvents(Event.EVENT_TIMER, Event.EVENT_TIMER);
        }

        TyreAudio.cleanup();
        flagsMirror = ResourceContainer.flags;
        itemsMirror = ResourceContainer.items;
        freeResources();
        lastState = MainWindow.gameState;
        lastMenuState = MainWindow.mainMenuSubState;
    }

    public void loadData() {
        dataLoaded = false;
        //res = getResource(257, 25);
        res = getResource(AppResources.ID_TYRE_DAT_ZIP, AppResources.ID_BIN_RES_TYPE);
        //454818
        Logger.wr("loadData: found resource length:" + res.getSize());
        
        if (rc != null) {
            rc = null;
        }
        
        rc = new ResourceContainer();
        datLoader = new loadDatThread(rc);
        //loadNormalDatFile();
        loadCompressedDatFile();
        //datLoader.start();
    }

    /*
    //KEEP THESE JUST IN CASE
    private void loadCompressedDatFileNEW() {
        byte[] result = new byte[res.getSize()];
        res.getBytes(result, 0, result.length);
        res = null;
        loadDat.chapter = result;
        loadDat.len = result.length;
    }
    */
    
    private void loadCompressedDatFile() {
        byte[] input = new byte[res.getSize()];
        res.getBytes(input, 0, input.length);
        res = null;

        byte[] result = new byte[ByteArray.readInt(input, 0)];
        Logger.wr("loadCompressedDatFile: Found decompressed size: " + result.length);
        Decompressor decompresser = new Decompressor();
        decompresser.reset();
        decompresser.setInput(input, 4, (input.length - 4));

        int resultLength = 0;
        try {
            //decompresser.
            resultLength = decompresser.inflate(result, 0, result.length);
            decompresser.end();
            if (decompresser.finished()) {
            }
            decompresser = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.wr("R0:" + result[0] + " R1:" + result[1] + " R2:" + result[2] + " R3:" + result[3]);        
        loadDat.chapter = result;
        loadDat.len = resultLength;
        Logger.wr("LoadDat.len:" + loadDat.len);        
    }
    
    /*
    private void loadNormalDatFile() {
        loadDat.len = res.getSize();
        loadDat.chapter = new byte[loadDat.len];
        loadDat.chapter = res.getBytes(loadDat.chapter, 0, loadDat.len);
        Logger.wr("loadNormalDatFile: found " + loadDat.chapter.length + " bytes.");
        Logger.wr("loadNormalDatFile: found " + loadDat.chapter[1] + " at byte 1.");        
    }
    */
    
    public void killDatLoad() {
        if (datLoader != null) {
            loadDat.foundEOF = true;
            loadDat.stop = true;
            datLoader = null;
        }
    }

    private void setSizeInfo() {
        len = ResourceContainer.objects.length;
        for (i = 0; i < len; i++) {
            switch (ResourceContainer.objects[i].type) {
                case 0:
                    //link table object
                    ResourceContainer.objects[i].height = bitmaps[ResourceContainer.objects[i].imageIndex].getHeight();
                    ResourceContainer.objects[i].width = bitmaps[ResourceContainer.objects[i].imageIndex].getWidth();
                    break;
                case 1:
                    //can't walk over
                    ResourceContainer.objects[i].height = bitmaps[ResourceContainer.objects[i].imageIndex].getHeight();
                    ResourceContainer.objects[i].width = bitmaps[ResourceContainer.objects[i].imageIndex].getWidth();
                    break;
                case 2:
                    ResourceContainer.objects[i].height = bitmaps[ResourceContainer.objects[i].imageIndex].getHeight();
                    ResourceContainer.objects[i].width = bitmaps[ResourceContainer.objects[i].imageIndex].getWidth();
                    break;
                case 3:
                    //characters
                    break;
                case 4:
                    //can walk over
                    ResourceContainer.objects[i].height = bitmaps[ResourceContainer.objects[i].imageIndex].getHeight();
                    ResourceContainer.objects[i].width = bitmaps[ResourceContainer.objects[i].imageIndex].getWidth();
                    break;
                case 5:
                    //enemy link object
                    ResourceContainer.objects[i].height = bitmaps[ResourceContainer.objects[i].imageIndex].getHeight();
                    ResourceContainer.objects[i].width = bitmaps[ResourceContainer.objects[i].imageIndex].getWidth();
                    break;
                case 6:
                    //npcs
                    int w = bitmaps[ResourceContainer.npcs[ResourceContainer.objects[i].npcIndex].fc].getWidth();
                    int h = bitmaps[ResourceContainer.npcs[ResourceContainer.objects[i].npcIndex].fc].getHeight();
                    ResourceContainer.npcs[ResourceContainer.objects[i].npcIndex].width = w;
                    ResourceContainer.npcs[ResourceContainer.objects[i].npcIndex].height = h;
                    break;
                case 7:
                    //hidden search item
                    break;
                case 8:
                    //can walk with rotation
                    ResourceContainer.objects[i].height = bitmaps[ResourceContainer.objects[i].imageIndex].getHeight();
                    ResourceContainer.objects[i].width = bitmaps[ResourceContainer.objects[i].imageIndex].getWidth();
                    break;
            }
        }
    }

    public String sanitizeSkChars(String s) {
        if (s.contains("\uF00A")) {
            s = s.replace("\uF00A", "A:");
        } else if (s.contains("\uF00B")) {
            s = s.replace("\uF00B", "L:");
        } else if (s.contains("\uF00C")) {
            s = s.replace("\uF00C", "R:");
        } else if (s.contains("\uF004")) {
            s = s.replace("\uF004", "U:");
        } else if (s.contains("\uF00D")) {
            s = s.replace("\uF00D", "D:");
        } else if (s.contains("\uF005")) {
            s = s.replace("\uF005", "B:");
        }
        return s;
    }    
    
    public final void finalizeLoad() {
        Logger.wr("finalizeLoad");        
        // post-process Sequence and Bitmap records
        try {
            for (int z = 0; z < ResourceContainer.consoleStrings.length; z++) {
                ResourceContainer.consoleStrings[z] = sanitizeSkChars(ResourceContainer.consoleStrings[z]);
                if (ResourceContainer.consoleStrings[z].contains("adjunct")) {
                    Logger.wr("Found background story at index: " + z);
                }
            }

            for (int z = 0; z < ResourceContainer.strings.length; z++) {
                ResourceContainer.strings[z] = sanitizeSkChars(ResourceContainer.strings[z]);
                if (ResourceContainer.strings[z].contains("adjunct")) {
                    Logger.wr("Found background story at index: " + z);
                }
            }
                
            // check for kill load
            if (loadDat.foundEOF) {
                return;
            }

            loadDat.pos = 0;
            loadDat.chapter = new byte[(ResourceContainer.bitmaps.length) + (ResourceContainer.consoleBitmaps.length) + (ResourceContainer.sequences.length * 25)];
            bitmaps = new Bitmap[ResourceContainer.bitmaps.length];

            for (i = 0; i < ResourceContainer.bitmaps.length; i++) {
                loadDat.pos++;
                bitmaps[i] = Bitmap.newFromPNGData(ResourceContainer.bitmaps[i].png);
                bitmaps[i].createPen();
                
                //check for kill load
                if (loadDat.foundEOF) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            consoleBitmaps = new Bitmap[ResourceContainer.consoleBitmaps.length + MainWindow.CONSOLEBITMAPS_NUM_DYNAMIC_IMAGES];
            for (i = 0; i < ResourceContainer.consoleBitmaps.length; i++) {
                loadDat.pos++;
                consoleBitmaps[i] = Bitmap.newFromPNGData(ResourceContainer.consoleBitmaps[i].png);
                consoleBitmaps[i].createPen();
                
                //check for kill load
                if (loadDat.foundEOF) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sequences = new Sequence[ResourceContainer.sequences.length];
            for (i = 0; i < ResourceContainer.sequences.length; i++) {
                loadDat.pos += 25;
                //check for kill load
                if (loadDat.foundEOF) {
                    return;
                }

                //NEW CODE
                try {
                    sequences[i] = AudioManager.createSequence(false);
                } catch (AudioException e) {
                    if (sequences[i] != null) {
                        sequences[i].destroy();
                        sequences[i] = null;
                    }
                }
                
                if (sequences[i] != null) {
                    byte b0 = ResourceContainer.sequences[i].png[0];
                    byte b1 = ResourceContainer.sequences[i].png[1];
                    byte b2 = ResourceContainer.sequences[i].png[2];
                    byte b3 = ResourceContainer.sequences[i].png[3];
                    Logger.wr("Loading audio with B0:" + b0 + " B1:" + b1 + " B2:" + b2 + " B3:" + b3);
                    
                    int len = ResourceContainer.sequences[i].png.length;
                    byte b4 = ResourceContainer.sequences[i].png[len - 4];
                    byte b5 = ResourceContainer.sequences[i].png[len - 3];
                    byte b6 = ResourceContainer.sequences[i].png[len - 2];
                    byte b7 = ResourceContainer.sequences[i].png[len - 1];
                    Logger.wr(i + ": Loading audio with B4:" + b4 + " B5:" + b5 + " B6:" + b6 + " B7:" + b7 + " Len:" + len);
                    
                    if (sequences[i].loadFromFile(ResourceContainer.sequences[i].png) == false) {
                    } else {
                        sequences[i].start(ToneFilter.NONE);
                        //music...
                        sequences[i].setLoops(10000);
                        sequences[i].stop();
                        sequences[i].start(ToneFilter.NONE);
                        sequences[i].setLoops(10000);
                        sequences[i].stop();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            setSizeInfo();
            //check for kill load
            if (loadDat.foundEOF) {
                return;
            }

            consoleBitmaps[MainWindow.DRAWMAPMSG_INDEX] = drawConsoleWindow(MainWindow.ROOM_WIDTH, (MainWindow.MESSAGE_FONT_HEIGHT * 4) + 9);
            consoleBitmaps[MainWindow.GAMEHUD_INDEX] = drawConsoleWindow(240, 46, -1);
            pen = consoleBitmaps[MainWindow.GAMEHUD_INDEX].createPen();

            int ind = MainWindow.CONSOLEBITMAPS_HUD_TILES_START_INDEX;
            for (i = 0; i < 5; i++) {
                pen.drawBitmap((48 * i), 2, Tyre.consoleBitmaps[(ind + i)]);
            }

            consoleBitmaps[MainWindow.ENEMYTARGET_INDEX] = drawTarget(75, 75);
            consoleBitmaps[MainWindow.MSGBOX_INDEX] = drawConsoleWindow(MainWindow.LRGMESSAGE_WIDTH, MainWindow.LRGMESSAGE_HEIGHT);
            if (lrgMessageContent != null) {
                consoleBitmaps[MainWindow.MSGCONTENT_INDEX] = lrgMessageContent;
            }

            //check for kill load
            if (loadDat.foundEOF) {
                return;
            }

            consoleBitmaps[MainWindow.ITEMGET_INDEX] = drawConsoleWindow(120, 96);
            consoleBitmaps[MainWindow.HERBSHOPMENU_INDEX] = drawConsoleWindow(60, 42);
            consoleBitmaps[MainWindow.HERBSHOPQUANTITY_INDEX] = drawConsoleWindow(75, 52);
            consoleBitmaps[MainWindow.MAINMENUBOX_INDEX] = drawConsoleWindow(MainWindow.MAINMENU_HUD_WIDTH, MainWindow.MAINMENU_HUD_HEIGHT);
            consoleBitmaps[MainWindow.BATTLEHUD_INDEX] = drawConsoleWindow(240, 46, -1);
            pen = consoleBitmaps[MainWindow.BATTLEHUD_INDEX].createPen();

            ind = MainWindow.CONSOLEBITMAPS_BATTLEHUD_TILES_START_INDEX;
            for (i = 0; i < 5; i++) {
                pen.drawBitmap((48 * i), 2, Tyre.consoleBitmaps[(ind + i)]);
            }

            //check for kill load
            if (loadDat.foundEOF) {
                return;
            }

            consoleBitmaps[MainWindow.CONVOCONTENT_INDEX] = drawConsoleWindow(MainWindow.CONVERSATIONBOX_WIDTH, MainWindow.CONVERSATIONBOX_HEIGHT);

            //SET SPEEDS BASED ON SPEED MULTIPLIER (allows slower SK2 to improve speeds)
            for (j = 0; j < ResourceContainer.enemies.length; j++) {
                for (i = 0; i < ResourceContainer.enemies[j].length; i++) {
                    int t = (int) (ResourceContainer.enemies[j][i].speed * speedMultiplier);
                    ResourceContainer.enemies[j][i].speed = t;
                }
            }

            for (i = 0; i < ResourceContainer.bosses.length; i++) {
                int t = (int) (ResourceContainer.bosses[i].speed * speedMultiplier);
                ResourceContainer.bosses[i].speed = t;
            }

            for (i = 0; i < ResourceContainer.players.length; i++) {
                ResourceContainer.players[i].speed = defaultSpeed;
            }

            //check for kill load
            if (loadDat.foundEOF) {
                return;
            }

            if (player == null) {
                player = new character();
            }
            charselector = null;

            //check for kill load
            if (loadDat.foundEOF) {
                return;
            }

            if (firstRun) {
                setDefaults();
                readSerializedGameData();
                readSerializedInvData();
                readSerializedStateData();
                restoreSettings();
                firstRun = false;
                firstContinue = true;
            } else {
                readSerializedGameData(serializedGameDataMirror);
                readSerializedInvData(serializedInvDataMirror);
                readSerializedStateData(serializedStateDataMirror);
                ResourceContainer.flags = flagsMirror;
                ResourceContainer.items = itemsMirror;
                firstContinue = false;
            }

            //check for kill load
            if (loadDat.foundEOF) {
                return;
            }

            flagsMirror = null;
            itemsMirror = null;
            serializedGameDataMirror = null;
            serializedInvDataMirror = null;
            serializedStateDataMirror = null;

            if (!dataLoaded) {
                dataLoaded = true;
            }

            //check for kill load
            if (loadDat.foundEOF) {
                return;
            }

            // if playerName has already been set reset strings array since dat load may have cleared it
            if (!"".equals(MainWindow.characterName)) {
                ResourceContainer.strings[Constants.PLAYER_NAME_INDEX] = MainWindow.characterName;
            }

            //System.out.println("****** STARTING FINALIZE LOAD MUSIC! ****");
            //System.out.println(" DATLOADER STOP VALUE " + loadDat.stop + " EOF " + loadDat.foundEOF);
            //System.out.println(" KILLDAT " + mWindow.datKilled);
            if (MainWindow.datKilled) {
                //dat load was halted, possibly by app deactivation (incoming call, etc)
                //do not start music or proceed to main menu, stay in load state
            } else {
                //System.out.println("Setting state back to menu from  "+ mWindow.gameState + ":" +mWindow.gameSubState);
                //this is used to preserve passive screens during suspend
                if (lastState == 10) {
                    MainWindow.skipMenuPrep = true;
                    mWindow.changeState(lastState, lastMenuState);
                } else {
                    mWindow.changeState(10, 0);
                }
                TyreAudio.startMusic(Constants.MUSIC_MENU);
            }

            loadDat.chapter = null;
            datLoader = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void freeResources() {
        if (consoleBitmaps != null) {
            lrgMessageContent = consoleBitmaps[MainWindow.MSGCONTENT_INDEX];
        }

        //TESTING THIS RIGHT NOW 09-04-2007
        bitmaps = null;
        consoleBitmaps = null;

        cantWalk = null;
        currentRoom = null;
        layerTwo = null;

        //release bitmaps for adjacent rooms
        for (i = 0; i < adjRoom.length; i++) {
            adjRoom[i] = null;
            adjLayerTwo[i] = null;
        }

        bgClear = null;        
        rObjects = null;
        roomChars = null;
        roomObjects = null;
        linkObjects = null;
        canWalkDynamic = null;
        sequences = null;
        rc = null;
    }

    private void buildAdjascentRoom(boolean sort, int index, int z) {
        if (z != -1) {
            //set graveyard to the state in the DAT file for rendering, then check after for repeating section
            setGraveyardSectionToDatState(z);
            adjRoom[index] = new Bitmap(MainWindow.ROOM_WIDTH, MainWindow.ROOM_HEIGHT);
            adjLayerTwo[index] = new Bitmap(MainWindow.ROOM_WIDTH, MainWindow.ROOM_HEIGHT);
            adjLayerTwo[index].setTransparentColor(Color.BLACK);
            buildRoom(sort, z, adjRoom[index], adjLayerTwo[index], false);
            mWindow.drawLayerOneFlaggedObjects(adjRoom[index].createPen(), false);
        } else {
            adjRoom[index] = new Bitmap(MainWindow.ROOM_WIDTH, MainWindow.ROOM_HEIGHT);
            pen = adjRoom[index].createPen();
            pen.setColor(0, 0, 0);
            pen.fillRect(0, 0, MainWindow.ROOM_WIDTH, MainWindow.ROOM_HEIGHT);
            adjLayerTwo[index] = null;
        }
    }

    public void buildAdjascentRooms(boolean sort) {
        //right
        if (adjBuildFlag[0] == true) {
            z = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkRight;
            buildAdjascentRoom(sort, 0, z);
            adjBuildFlag[0] = false;
        }

        //bottom
        if (adjBuildFlag[2] == true) {
            z = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkBottom;
            buildAdjascentRoom(sort, 2, z);
            adjBuildFlag[2] = false;
        }

        // bottom right (may not exist)
        // first make sure bottom room exists, if so, check if bottom's room right room exists
        // do all the pre-logic and set z to the bottom right room, or -1 if it doesn't exist
        if (adjBuildFlag[1] == true) {
            z = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkBottom;
            if (z != -1) {
                if (ResourceContainer.linkTables[z].linkRight != -1) {
                    z = ResourceContainer.linkTables[z].linkRight;
                } else {
                    z = -1;
                }
            }

            //if bottom right doesn't exist check right bottom
            if (z == -1) {
                z = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkRight;
                if (z != -1) {
                    if (ResourceContainer.linkTables[z].linkBottom != -1) {
                        z = ResourceContainer.linkTables[z].linkBottom;
                    } else {
                        z = -1;
                    }
                }
            }
            buildAdjascentRoom(sort, 1, z);
            adjBuildFlag[1] = false;
        }

        // bottom left (may not exist)
        // first make sure bottom room exists, if so, check if bottom's room left room exists
        // do all the pre-logic and set z to the bottom left room, or -1 if it doesn't exist
        if (adjBuildFlag[3] == true) {
            z = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkBottom;
            if (z != -1) {
                if (ResourceContainer.linkTables[z].linkLeft != -1) {
                    z = ResourceContainer.linkTables[z].linkLeft;
                } else {
                    z = -1;
                }
            }

            //if bottom right doesn't exist check right bottom
            if (z == -1) {
                z = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkLeft;
                if (z != -1) {
                    if (ResourceContainer.linkTables[z].linkBottom != -1) {
                        z = ResourceContainer.linkTables[z].linkBottom;
                    } else {
                        z = -1;
                    }
                }
            }
            buildAdjascentRoom(sort, 3, z);
            adjBuildFlag[3] = false;
        }

        //left
        if (adjBuildFlag[4] == true) {
            z = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkLeft;
            buildAdjascentRoom(sort, 4, z);
            adjBuildFlag[4] = false;
        }

        //top
        if (adjBuildFlag[6] == true) {
            z = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkTop;
            buildAdjascentRoom(sort, 6, z);
            adjBuildFlag[6] = false;
        }

        //top left (may not exist)
        //first make sure top room exists, if so, check if top's room left room exists
        //do all the pre-logic and set z to the top left room, or -1 if it doesn't exist
        if (adjBuildFlag[5] == true) {
            z = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkTop;
            if (z != -1) {
                if (ResourceContainer.linkTables[z].linkLeft != -1) {
                    z = ResourceContainer.linkTables[z].linkLeft;
                } else {
                    z = -1;
                }
            }

            //if top right doesn't exist check right top
            if (z == -1) {
                z = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkLeft;
                if (z != -1) {
                    if (ResourceContainer.linkTables[z].linkTop != -1) {
                        z = ResourceContainer.linkTables[z].linkTop;
                    } else {
                        z = -1;
                    }
                }
            }
            buildAdjascentRoom(sort, 5, z);
            adjBuildFlag[5] = false;
        }

        //top right (may not exist)
        //first make sure top room exists, if so, check if top's room right room exists
        //do all the pre-logic and set z to the top right room, or -1 if it doesn't exist
        if (adjBuildFlag[7] == true) {
            z = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkTop;
            if (z != -1) {
                if (ResourceContainer.linkTables[z].linkRight != -1) {
                    z = ResourceContainer.linkTables[z].linkRight;
                } else {
                    z = -1;
                }

            }

            //if top right doesn't exist check right top
            if (z == -1) {
                z = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkRight;
                if (z != -1) {
                    if (ResourceContainer.linkTables[z].linkTop != -1) {
                        z = ResourceContainer.linkTables[z].linkTop;
                    } else {
                        z = -1;
                    }
                }
            }
            buildAdjascentRoom(sort, 7, z);
            adjBuildFlag[7] = false;
        }
    }

    //change the destination room to be the source room to avoid rebuilding
    public static void swapAdjRooms(int src, int dest) {
        adjRoom[dest] = adjRoom[src];
        adjLayerTwo[dest] = adjLayerTwo[src];
        adjBuildFlag[dest] = false;
    }

    //shift rooms already built to avoid full rebuild
    public static void adjustAdjacentRooms(int side) {
        switch (side) {
            //up
            case 0:
                swapAdjRooms(4, 3);
                swapAdjRooms(0, 1);
                swapAdjRooms(7, 0);
                swapAdjRooms(5, 4);
                break;
            //right
            case 1:
                swapAdjRooms(6, 5);
                swapAdjRooms(2, 3);
                swapAdjRooms(7, 6);
                swapAdjRooms(1, 2);
                break;
            //down
            case 2:
                swapAdjRooms(0, 7);
                swapAdjRooms(4, 5);
                swapAdjRooms(3, 4);
                swapAdjRooms(1, 0);
                break;
            //left
            case 3:
                swapAdjRooms(6, 7);
                swapAdjRooms(2, 1);
                swapAdjRooms(5, 6);
                swapAdjRooms(3, 2);
                break;
        }
    }

    public void buildRoom(boolean sort) {
        Logger.wr("============================================buildRoom: sort:" + sort);
        //set graveyard to the state in the DAT file for rendering, then check after for repeating section
        setGraveyardSectionToDatState(Tyre.currentRoomIdx);
        if (MainWindow.RENDER_ADJACENT_ROOMS == 1) {
            buildAdjascentRooms(sort);
        }

        currentRoom = Bitmap.newFromPNGData(bgClear.res);
        //currentRoom = new Bitmap(240, 136, MmgColor.GetBlack());
        layerTwo = Bitmap.newFromPNGData(bgClear.res);
        //layerTwo = new Bitmap(240, 136);

        buildRoom(sort, Tyre.currentRoomIdx, currentRoom, layerTwo, true);
        checkGraveyardRepeatingSection(Tyre.currentRoomIdx);
    }

    //check if player is in repeating graveyard area and if so, alter linktable to make it repeating
    public void checkGraveyardRepeatingSection(int roomId) {
        //CHECK FOR GRAVEYARD REPEAT SECTION HERE
        if ((roomId == 207) || (roomId == 217) || (roomId == 118)
                || (roomId == 147) || (roomId == 161) || (roomId == 175)
                || (roomId == 188) || (roomId == 133)) {
            if ((Tyre.player.inInventory(Constants.ITEM_COMPASS) == -1) || (Tyre.player.inInventory(Constants.ITEM_MAP) == -1)) {
                ResourceContainer.linkTables[roomId].linkRight = roomId;
            } else {
                switch (roomId) {
                    case 175:
                        ResourceContainer.linkTables[roomId].linkRight = 225;
                        break;
                    default:
                        //roomID + 1 is one room to the right for these graveyard rooms
                        ResourceContainer.linkTables[roomId].linkRight = roomId + 1;
                        break;
                }
            }
        }
    }

    //sets the linkTable entries in the graveyard repeating section to the orignal DAT state for rendering
    public void setGraveyardSectionToDatState(int roomId) {
        //CHECK FOR GRAVEYARD REPEAT SECTION HERE
        if ((roomId == 207) || (roomId == 217) || (roomId == 118)
                || (roomId == 147) || (roomId == 161) || (roomId == 175)
                || (roomId == 188) || (roomId == 133)) {
            switch (roomId) {
                case 175:
                    ResourceContainer.linkTables[roomId].linkRight = 225;
                    break;
                default:
                    // roomID + 1 is one room to the right for these graveyard rooms
                    ResourceContainer.linkTables[roomId].linkRight = roomId + 1;
                    break;
            }
        }
    }

    //activeRoom is called when its the room the player is actually in (triggers room msg)
    public void buildRoom(boolean sort, int roomId, Bitmap roomImg, Bitmap layer2Img, boolean activeRoom) {
        Logger.wr("============================================buildRoom: sort:" + sort + " roomId:" + roomId + " activeRoom:" + activeRoom);
        //set game state to draw black screen
        player.draw = false;
        MainWindow.gameState = 9;

        roomObjectCount = 0;
        cantWalkCount = 0;
        roomCharCount = 0;
        linkObjectCount = 0;
        canWalkDynamicCount = 0;

        //graveyard code here
        //draw all the base tiles to the background image
        //count all the can't walk tiles and layer two tiles and characters
        roomDataInfo = ResourceContainer.rooms[ResourceContainer.linkTables[roomId].roomIndex].roomDataInfo;
        for (z = 0; z < roomDataInfo.length; z++) {
            rDat = roomDataInfo[z];
            if (!ResourceContainer.tiles[rDat.tiles_id].canWalk) {
                cantWalkCount++;
            }
            
            //change tile rendering dimensions to the extended dimensions for builds
            //that render adjacent rooms
            if (MainWindow.RENDER_ADJACENT_ROOMS == 1) {
                if (rDat.right == 255) {
                    rDat.right = 256;
                }
            }

            for (j = rDat.top; j < rDat.bottom; j += TILESIZE) {
                for (i = rDat.left; i < rDat.right; i += TILESIZE) {
                    //use new bitmap refs from arg
                    Bitmap.copyBits(roomImg, new Rect(i, j, i + TILESIZE, j + TILESIZE), bitmaps[ResourceContainer.tiles[rDat.tiles_id].imageIndex], new Rect(TILESIZE, TILESIZE));
                }
            }
        }

        //increment objects variables for array initialization
        hasLink = false;
        hasCanWalkDynamic = false;
        if (ResourceContainer.linkTables[roomId].objectSetIndex != -1) {
            for (i = 0; i < ResourceContainer.objectSets[ResourceContainer.linkTables[roomId].objectSetIndex].length; i++) {
                switch (ResourceContainer.objects[ResourceContainer.objectSets[ResourceContainer.linkTables[roomId].objectSetIndex][i]].type) {
                    case 0:
                        linkObjectCount++;
                        if (!hasLink) {
                            hasLink = true;
                        }
                        break;
                    case 1:
                        //can't walk over
                        roomObjectCount++;
                        break;
                    case 2:
                        //layer two object
                        tmpObj = ResourceContainer.objects[ResourceContainer.objectSets[ResourceContainer.linkTables[roomId].objectSetIndex][i]];
                        //Bitmap.copyBits(layer2Img, new Rect(tmpObj.posX, tmpObj.posY, (tmpObj.posX + tmpObj.width), (tmpObj.posY + tmpObj.height)), bitmaps[tmpObj.imageIndex], new Rect(0, 0, tmpObj.width, tmpObj.height));
                        break;
                    case 3:
                        //player object
                        //increment by one for the player (do this automatically)
                        break;
                    case 4:
                        //can walk without rotation
                        break;
                    case 5:
                        //enemy link object
                        roomObjectCount++;
                        break;
                    case 6:
                        //npc object
                        roomObjectCount++;
                        roomCharCount++;
                        break;
                    case 7:
                        //searchable object
                        roomObjectCount++;
                        break;
                    case 8:
                        //can walk with rotation
                        break;
                    case 9:
                        //object set without rotation
                        //roomObjectCount++;
                        break;
                    case 10:
                        //object set with rotation
                        //roomObjectCount++;
                        break;
                    case 11:
                        //object set can't walk
                        max = ResourceContainer.objectSets[ResourceContainer.objects[ResourceContainer.objectSets[ResourceContainer.linkTables[roomId].objectSetIndex][i]].objectSetsIndex].length;
                        for (j = 0; j < max; j++) {
                            roomObjectCount++;
                        }
                        break;
                    case 12:
                        //can walk (dynamic)
                        canWalkDynamicCount++;
                        if (!hasCanWalkDynamic) {
                            hasCanWalkDynamic = true;
                        }
                        break;
                }
            }
        }

        //and array of tiles the player cannot walk on
        cantWalk = new tileRef[cantWalkCount];

        //an array of integers that keeps the order of room object index
        //the order represents how they should be drawn on the screen
        //add one for the default main character
        rObjects = new int[roomObjectCount + 1];

        //the obects that are currently used in the active room
        //add one for the default main character
        roomObjects = new tyreObject[roomObjectCount + 1];
        linkObjects = new tyreObject[linkObjectCount];
        canWalkDynamic = new tyreObject[canWalkDynamicCount];

        //characters that exist in the current room
        roomChars = new npc[roomCharCount];

        //reset all the count variables so that we can use them as array population indexes
        cantWalkCount = 0;
        roomObjectCount = 0;
        roomCharCount = 0;
        linkObjectCount = 0;
        canWalkDynamicCount = 0;

        for (z = 0; z < roomDataInfo.length; z++) {
            rDat = roomDataInfo[z];
            if (!ResourceContainer.tiles[rDat.tiles_id].canWalk) {
                cantWalk[cantWalkCount] = new tileRef(rDat.top, rDat.left, rDat.bottom, rDat.right);
                cantWalkCount++;
            }
        }

        if (ResourceContainer.linkTables[roomId].objectSetIndex != -1) {
            for (i = 0; i < ResourceContainer.objectSets[ResourceContainer.linkTables[roomId].objectSetIndex].length; i++) {
                tmp = ResourceContainer.objectSets[ResourceContainer.linkTables[roomId].objectSetIndex][i];

                switch (ResourceContainer.objects[tmp].type) {
                    case 0:
                        //link table reference
                        linkObjects[linkObjectCount] = new tyreObject(ResourceContainer.objects[tmp].type, ResourceContainer.objects[tmp].imageIndex, ResourceContainer.objects[tmp].linkTo, ResourceContainer.objects[tmp].posX, ResourceContainer.objects[tmp].posY, ResourceContainer.objects[tmp].height, ResourceContainer.objects[tmp].width, ResourceContainer.objects[tmp].destPosX, ResourceContainer.objects[tmp].destPosY, ResourceContainer.objects[tmp].flagIndex, ResourceContainer.objects[tmp].flagDir, ResourceContainer.objects[tmp].destDir, ResourceContainer.objects[tmp].vertOffset);
                        linkObjectCount++;
                        break;
                    case 1:
                        //can't walk object
                        roomObjects[roomObjectCount] = new tyreObject(ResourceContainer.objects[tmp].type, ResourceContainer.objects[tmp].imageIndex, ResourceContainer.objects[tmp].linkTo, ResourceContainer.objects[tmp].npcIndex, ResourceContainer.objects[tmp].posX, ResourceContainer.objects[tmp].posY, ResourceContainer.objects[tmp].searchItem, ResourceContainer.objects[tmp].stringIndex, ResourceContainer.objects[tmp].charIndex, ResourceContainer.objects[tmp].height, ResourceContainer.objects[tmp].width, ResourceContainer.objects[tmp].conversationIndex, ResourceContainer.objects[tmp].flagIndex, ResourceContainer.objects[tmp].angle, ResourceContainer.objects[tmp].flagDir, ResourceContainer.objects[tmp].vertOffset);
                        rObjects[roomObjectCount] = roomObjectCount;
                        roomObjectCount++;
                        break;
                    case 2:
                        //layer two object
                        break;
                    case 3:
                        //character
                        break;
                    case 4:
                        //can walk without rotation
                        drawLayerOne(tmp, 0, 0, 0, roomImg);
                        break;
                    case 5:
                        //enemy link object
                        roomObjects[roomObjectCount] = new tyreObject(ResourceContainer.objects[tmp].type, ResourceContainer.objects[tmp].imageIndex, ResourceContainer.objects[tmp].posX, ResourceContainer.objects[tmp].posY, ResourceContainer.objects[tmp].height, ResourceContainer.objects[tmp].width, ResourceContainer.objects[tmp].battleIndex, ResourceContainer.objects[tmp].flagIndex, ResourceContainer.objects[tmp].flagDir, ResourceContainer.objects[tmp].vertOffset);
                        rObjects[roomObjectCount] = roomObjectCount;
                        roomObjectCount++;
                        break;
                    case 6:
                        //npc
                        roomObjects[roomObjectCount] = new tyreObject(ResourceContainer.objects[tmp].type, ResourceContainer.objects[tmp].imageIndex, ResourceContainer.objects[tmp].linkTo, ResourceContainer.objects[tmp].npcIndex, ResourceContainer.objects[tmp].posX, ResourceContainer.objects[tmp].posY, ResourceContainer.objects[tmp].searchItem, ResourceContainer.objects[tmp].stringIndex, ResourceContainer.objects[tmp].charIndex, ResourceContainer.objects[tmp].height, ResourceContainer.objects[tmp].width, ResourceContainer.objects[tmp].conversationIndex, ResourceContainer.objects[tmp].flagIndex, ResourceContainer.objects[tmp].angle, ResourceContainer.objects[tmp].flagDir, ResourceContainer.objects[tmp].vertOffset);
                        rObjects[roomObjectCount] = roomObjectCount;
                        roomChars[roomCharCount] = copyNpc(ResourceContainer.npcs[roomObjects[roomObjectCount].npcIndex]);

                        //set the character to their starting position
                        roomChars[roomCharCount].posX = roomObjects[roomObjectCount].posX;
                        roomChars[roomCharCount].posY = roomObjects[roomObjectCount].posY;
                        roomObjects[roomObjectCount].npcIndex = roomCharCount;
                        roomCharCount++;
                        roomObjectCount++;
                        break;
                    case 7:
                        //a hidden item
                        roomObjects[roomObjectCount] = new tyreObject(ResourceContainer.objects[tmp].type, ResourceContainer.objects[tmp].imageIndex, ResourceContainer.objects[tmp].linkTo, ResourceContainer.objects[tmp].npcIndex, ResourceContainer.objects[tmp].posX, ResourceContainer.objects[tmp].posY, ResourceContainer.objects[tmp].searchItem, ResourceContainer.objects[tmp].stringIndex, ResourceContainer.objects[tmp].charIndex, ResourceContainer.objects[tmp].height, ResourceContainer.objects[tmp].width, ResourceContainer.objects[tmp].conversationIndex, ResourceContainer.objects[tmp].flagIndex, ResourceContainer.objects[tmp].angle, ResourceContainer.objects[tmp].flagDir, ResourceContainer.objects[tmp].vertOffset);
                        rObjects[roomObjectCount] = roomObjectCount;
                        roomObjectCount++;
                        break;
                    case 8:
                        //can walk with rotation
                        drawLayerOne(tmp, ResourceContainer.objects[tmp].angle, 0, 0, roomImg);
                        break;
                    case 9:
                        //object set without rotation
                        max = ResourceContainer.objectSets[ResourceContainer.objects[tmp].objectSetsIndex].length;
                        for (j = 0; j < max; j++) {
                            drawLayerOne(ResourceContainer.objectSets[ResourceContainer.objects[tmp].objectSetsIndex][j], 0, ResourceContainer.objects[tmp].posX, ResourceContainer.objects[tmp].posY, roomImg);
                        }
                        break;
                    case 10:
                        //object set with rotation
                        max = ResourceContainer.objectSets[ResourceContainer.objects[tmp].objectSetsIndex].length;
                        for (j = 0; j < max; j++) {
                            drawLayerOne(ResourceContainer.objectSets[ResourceContainer.objects[tmp].objectSetsIndex][j], ResourceContainer.objects[tmp].angle, ResourceContainer.objects[tmp].posX, ResourceContainer.objects[tmp].posY, roomImg);
                        }
                        break;
                    case 11:
                        max = ResourceContainer.objectSets[ResourceContainer.objects[tmp].objectSetsIndex].length;
                        for (j = 0; j < max; j++) {
                            z = ResourceContainer.objectSets[ResourceContainer.objects[tmp].objectSetsIndex][j];
                            roomObjects[roomObjectCount] = new tyreObject(ResourceContainer.objects[z].type, ResourceContainer.objects[z].imageIndex, ResourceContainer.objects[z].linkTo, ResourceContainer.objects[z].npcIndex, ResourceContainer.objects[tmp].posX + ResourceContainer.objects[z].posX, ResourceContainer.objects[tmp].posY + ResourceContainer.objects[z].posY, ResourceContainer.objects[z].searchItem, ResourceContainer.objects[z].stringIndex, ResourceContainer.objects[z].charIndex, ResourceContainer.objects[z].height, ResourceContainer.objects[z].width, ResourceContainer.objects[z].conversationIndex, ResourceContainer.objects[z].flagIndex, ResourceContainer.objects[z].angle, ResourceContainer.objects[z].flagDir, ResourceContainer.objects[tmp].vertOffset);
                            rObjects[roomObjectCount] = roomObjectCount;
                            roomObjectCount++;
                        }
                        break;
                    case 12:
                        //can walk (dynamic)
                        canWalkDynamic[canWalkDynamicCount] = new tyreObject(ResourceContainer.objects[tmp].type, ResourceContainer.objects[tmp].imageIndex, ResourceContainer.objects[tmp].linkTo, ResourceContainer.objects[tmp].npcIndex, ResourceContainer.objects[tmp].posX, ResourceContainer.objects[tmp].posY, ResourceContainer.objects[tmp].searchItem, ResourceContainer.objects[tmp].stringIndex, ResourceContainer.objects[tmp].charIndex, ResourceContainer.objects[tmp].height, ResourceContainer.objects[tmp].width, ResourceContainer.objects[tmp].conversationIndex, ResourceContainer.objects[tmp].flagIndex, ResourceContainer.objects[tmp].angle, ResourceContainer.objects[tmp].flagDir, ResourceContainer.objects[tmp].vertOffset);
                        canWalkDynamicCount++;
                        break;
                }
            }
        }

        if (ResourceContainer.linkTables[roomId].objectSetIndex != -1) {
            for (i = 0; i < ResourceContainer.objectSets[ResourceContainer.linkTables[roomId].objectSetIndex].length; i++) {
                switch (ResourceContainer.objects[ResourceContainer.objectSets[ResourceContainer.linkTables[roomId].objectSetIndex][i]].type) {
                    case 0:
                        //object link
                        break;
                    case 1:
                        //can't walk over
                        break;
                    case 2:
                        //layer two object
                        tmpObj = ResourceContainer.objects[ResourceContainer.objectSets[ResourceContainer.linkTables[roomId].objectSetIndex][i]];
                        Bitmap.copyBits(layer2Img.pen, layer2Img, new Rect(tmpObj.posX, tmpObj.posY, (tmpObj.posX + tmpObj.width), (tmpObj.posY + tmpObj.height)), bitmaps[tmpObj.imageIndex], new Rect(0, 0, tmpObj.width, tmpObj.height));
                        break;
                    case 3:
                        //player object
                        //increment by one for the player (do this automatically)
                        break;
                    case 4:
                        //can walk without rotation
                        break;
                    case 5:
                        //enemy link object
                        break;
                    case 6:
                        //npc object
                        break;
                    case 7:
                        //searchable object
                        break;
                    case 8:
                        //can walk with rotation
                        break;
                    case 9:
                        //object set without rotation
                        break;
                    case 10:
                        //object set with rotation
                        break;
                    case 11:
                        //object set can't walk
                        break;
                    case 12:
                        //can walk (dynamic)
                        break;
                }
            }
        }

        //always add the main character to the room
        roomObjects[roomObjectCount] = new tyreObject(3);
        rObjects[roomObjectCount] = roomObjectCount;

        //sort the room objects by their y position plus their height
        //so they display correctly on the screen
        if (sort) {
            sortRoomObjects();
        }

        //only check for story message and background music for active room (room player is in)
        if (activeRoom) {
            if (ResourceContainer.linkTables[roomId].stringIndex != -1) {
                if (!ResourceContainer.flags[ResourceContainer.linkTables[roomId].flagIndex]) {
                    MainWindow.storyType = 0;
                    MainWindow.lastStringUsed = ResourceContainer.linkTables[roomId].stringIndex;
                    MainWindow.lastStringArrayUsed = 0;
                    mWindow.formatStoryMessage(ResourceContainer.strings[ResourceContainer.linkTables[roomId].stringIndex]);
                    MainWindow.gameSubState = 5;
                    Logger.wr("FLAGS: ROOM MSG: Index:" + ResourceContainer.linkTables[roomId].flagIndex + " = " + true + "< ResourceContainer.linkTables[roomId].flagIndex = true >");
                    ResourceContainer.flags[ResourceContainer.linkTables[roomId].flagIndex] = true;
                }
            }

            switch (ResourceContainer.rooms[ResourceContainer.linkTables[roomId].roomIndex].roomType) {
                case Constants.ROOMTYPE_WILDERNESS:
                    TyreAudio.startMusic(Constants.MUSIC_OUTSIDE);
                    break;
                case Constants.ROOMTYPE_TUNNEL:
                    TyreAudio.startMusic(Constants.MUSIC_TUNNEL);
                    break;
                case Constants.ROOMTYPE_HOUSEMYSTERIOUS:
                    TyreAudio.startMusic(Constants.MUSIC_TUNNEL);
                    break;
                default:
                    TyreAudio.startMusic(Constants.MUSIC_INSIDE);
                    break;
            }
            player.draw = true;
        }

        //set game state to draw room
        MainWindow.gameState = 0;
    }

    private void drawLayerOne(int tmp, int angle, int xOffset, int yOffset) {
        drawLayerOne(tmp, angle, xOffset, yOffset, currentRoom);
    }

    private void drawLayerOne(int tmp, int angle, int xOffset, int yOffset, Bitmap roomImg) {
        x = ResourceContainer.objects[tmp].posX + xOffset;
        y = ResourceContainer.objects[tmp].posY + yOffset;
        w = ResourceContainer.objects[tmp].width;
        h = ResourceContainer.objects[tmp].height;

        //test to see if image is off the screen
        //changing 240, 136 to mWindow.ROOM_WIDTH, mWindow.ROOM_HEIGHT
        if (x > MainWindow.ROOM_WIDTH) {
            x = MainWindow.ROOM_WIDTH / 2;
        }
        if (y > MainWindow.ROOM_HEIGHT) {
            y = MainWindow.ROOM_HEIGHT / 2;
        }
        xw = x + w;
        yh = y + h;

        if (xw > MainWindow.ROOM_WIDTH) {
            xw = MainWindow.ROOM_WIDTH;
            w = xw - x;
        }
        if (yh > MainWindow.ROOM_HEIGHT) {
            yh = MainWindow.ROOM_HEIGHT;
            h = yh - y;
        }
        
        if (angle == 0) {
            try {
                Bitmap.copyBits(roomImg, new Rect(x, y, xw, yh), bitmaps[ResourceContainer.objects[tmp].imageIndex], new Rect(0, 0, w, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Bitmap.copyBits(roomImg, new Rect(x, y, xw, yh), bitmaps[ResourceContainer.objects[tmp].imageIndex].rotateBy(angle), new Rect(0, 0, w, h));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public enemy copyEnemy(enemy from) {
        enemy to = new enemy();
        to.aiType = from.aiType;
        to.damage = from.damage;
        to.speed = from.speed;
        to.hp = from.hp;
        to.ap = from.ap;
        to.mp = from.mp;
        to.dp = from.dp;
        to.stringIndex = from.stringIndex;
        to.imageIndex = from.imageIndex;
        to.goldCoinsMax = from.goldCoinsMax;
        to.lv = from.lv;
        return to;
    }

    public npc copyNpc(npcdata from) {
        npc to = new npc();
        to.npcType = from.npcType;
        to.stringIndex = from.stringIndex;
        to.dir = from.dir;
        to.toggle = from.toggle;
        to.walkFreeze = from.walkFreeze;
        to.fc = from.fc;
        to.fr = from.fr;
        to.fl = from.fl;
        to.bc = from.bc;
        to.br = from.br;
        to.bl = from.bl;
        to.rc = from.rc;
        to.rr = from.rr;
        to.rl = from.rl;
        to.lc = from.lc;
        to.lr = from.lr;
        to.ll = from.ll;
        to.posX = from.posX;
        to.posY = from.posY;
        if (from.canWalk) {
            to.setCanWalk(1, from.height, from.width);
        } else {
            to.setCanWalk(0, from.height, from.width);
        }
        return to;
    }

    public void quickSort(int[] data, int first, int last) {
        lower = first + 1;
        upper = last;
        swap(data, first, (first + last) / 2);
        while (lower <= upper) {
            while (translate(data[lower]) < translate(data[first])) {
                lower++;
            }
            while (translate(data[first]) < translate(data[upper])) {
                upper--;
            }
            if (lower < upper) {
                swap(data, lower++, upper--);
            } else {
                lower++;
            }
        }
        swap(data, upper, first);
        if (first < upper - 1) {
            quickSort(data, first, upper - 1);
        }
        if (upper + 1 < last) {
            quickSort(data, upper + 1, last);
        }
    }

    private void quickSort(int[] data) {
        if (data.length < 2) {
            return;
        }
        max = 0;
        //find the larget element and put it at the end of data
        for (qsI = 1; qsI < data.length; qsI++) {
            if (translate(data[qsI]) > translate(data[max])) {
                max = qsI;
            }
        }
        swap(data, data.length - 1, max);
        quickSort(data, 0, data.length - 2);
    }

    private int translate(int index) {
        switch (roomObjects[index].type) {
            case 0:
                return roomObjects[index].posY + roomObjects[index].height;
            case 1:
                return roomObjects[index].posY + roomObjects[index].height;
            case 3:
                //characters
                return player.posY + player.height;
            case 5:
                return roomObjects[index].posY + roomObjects[index].height;
            case 6:
                //npcs
                return roomChars[roomObjects[index].npcIndex].posY + roomChars[roomObjects[index].npcIndex].height;
        }
        return 0;
    }

    private void swap(int[] a, int e1, int e2) {
        sTmp = a[e1];
        a[e1] = a[e2];
        a[e2] = sTmp;
    }

    public final void sortRoomObjects() {
        quickSort(rObjects);
    }

    public Bitmap drawConsoleWindow(int width, int height) {
        return drawConsoleWindow(width, height, 0);
    }

    public Bitmap drawConsoleWindow(int width, int height, int trans) {
        Bitmap window = new Bitmap(width, height);
        pen = window.createPen();
        if (trans > -1) {
            window.setTransparentColor(trans);
        }
        pen.fillRect(0, 0, width, height);
        pen.setColor(-16248224);
        pen.drawRoundRect(0, 0, (width), (height), 3, 3);
        pen.setColor(-15524477);
        pen.drawRoundRect(1, 1, (width - 1), (height - 1), 2, 2);
        pen.setColor(-15194448);
        pen.drawRoundRect(2, 2, (width - 2), (height - 2), 1, 1);
        pen.setColor(-16448251);
        pen.fillRect(3, 3, (width - 3), (height - 3));
        return window;
    }

    public final void drawConsoleWindow(Pen pen, int width, int height, int xoff, int yoff) {
        pen.fillRect(xoff, yoff, xoff + width, yoff + height);
        pen.setColor(-16248224);
        pen.drawRoundRect(xoff, yoff, (xoff + width), (yoff + height), 3, 3);
        pen.setColor(-15524477);
        pen.drawRoundRect(xoff + 1, yoff + 1, (xoff + width - 1), (yoff + height - 1), 2, 2);
        pen.setColor(-15194448);
        pen.drawRoundRect(xoff + 2, yoff + 2, (xoff + width - 2), (yoff + height - 2), 1, 1);
        pen.setColor(-16448251);
        pen.fillRect(xoff + 3, yoff + 3, (xoff + width - 3), (yoff + height - 3));
    }

    public final Bitmap drawTarget(int width, int height) {
        return drawTarget(width, height, 9, 2, Color.RED);
    }

    public final Bitmap drawTarget(int width, int height, int blength, int bheight, int color) {
        Bitmap window = new Bitmap(width, height);
        pen = window.createPen();
        window.setTransparentColor(0);
        pen.fillRect(0, 0, width, height);
        pen.setColor(color);

        //top left
        pen.fillRect(0, 0, bheight, blength);
        pen.fillRect(bheight, 0, blength, bheight);

        //bottom left
        pen.fillRect(0, height - blength, bheight, height);
        pen.fillRect(bheight, height - bheight, blength, height);

        //top right
        pen.fillRect(width - blength, 0, width, bheight);
        pen.fillRect(width - bheight, bheight, width, blength);

        //bottom right
        pen.fillRect(width - blength, height - bheight, width, height);
        pen.fillRect(width - bheight, height - blength, width, height);
        return window;
    }

    public final void restoreSettings() {
        if (theSettings == null) {
            theSettings = Settings.getInstance();
        }

        boolean[] flgTmp = theSettings.getFlags(ResourceContainer.flags);
        if (flgTmp != null) {
            ResourceContainer.flags = flgTmp;
        }

        String tmpStr = theSettings.getPlayerName();
        if (!"".equals(tmpStr) && tmpStr != null) {
            ResourceContainer.strings[Constants.PLAYER_NAME_INDEX] = tmpStr;
            //store char name since dat reload will overwrite it
            MainWindow.characterName = tmpStr;
        }
    }

    public void dumpDB() {
        Logger.wr("===== Tyre.dumpDB =====");
        Tyre.theSettings.dumpDB();
    }

    public int calcGameDataSize() {
        int len = 0;
        len += 156;
        return len;
    }

    public byte[] readSerializedGameData() {
        if (theSettings == null) {
            theSettings = Settings.getInstance();
        }
        byte[] d = theSettings.getSerializedGameData();
        return readSerializedGameData(d);
    }

    public byte[] readSerializedGameData(byte[] d) {
        if (d != null) {
            x = 0;
            player.charType = GetIntDataFromBytes(d, x);
            if (player.charType != -1) {
                player.loadCharData(player.charType);
            }
            x += 4;
            player.ex = GetIntDataFromBytes(d, x);
            x += 4;
            player.lv = GetIntDataFromBytes(d, x);
            x += 4;
            player.totalHitsTaken = GetIntDataFromBytes(d, x);
            x += 4;
            player.totalHitsGiven = GetIntDataFromBytes(d, x);
            x += 4;
            player.totalSuperAttackTaken = GetIntDataFromBytes(d, x);
            x += 4;
            player.totalSuperAttackGiven = GetIntDataFromBytes(d, x);
            x += 4;
            player.totalMisses = GetIntDataFromBytes(d, x);
            x += 4;
            player.totalDodges = GetIntDataFromBytes(d, x);
            x += 4;
            player.totalRun = GetIntDataFromBytes(d, x);
            x += 4;
            player.totalKills = GetIntDataFromBytes(d, x);
            x += 4;
            player.totalItems = GetIntDataFromBytes(d, x);
            x += 4;
            player.totalDeaths = GetIntDataFromBytes(d, x);
            x += 4;
            player.damage = GetIntDataFromBytes(d, x);
            x += 4;
            player.speed = GetIntDataFromBytes(d, x);
            x += 4;
            player.hp = GetIntDataFromBytes(d, x);
            x += 4;
            player.ap = GetIntDataFromBytes(d, x);
            x += 4;
            player.dp = GetIntDataFromBytes(d, x);
            x += 4;
            player.goldCoins = GetIntDataFromBytes(d, x);
            x += 4;
            player.posX = GetIntDataFromBytes(d, x);
            x += 4;
            player.posY = GetIntDataFromBytes(d, x);
            x += 4;
            player.dir = GetIntDataFromBytes(d, x);
            x += 4;
            player.weapon = GetIntDataFromBytes(d, x);
            player.equip_weapon(player.ap, player.weapon);
            x += 4;
            player.armor = GetIntDataFromBytes(d, x);
            player.equip_armor(player.dp, player.armor);
            x += 4;
            player.PLAYERWAIT_TIME = GetIntDataFromBytes(d, x);
            x += 4;
            player.antipoison = GetBooleanDataFromBytes(d, x);
            x += 4;
            player.antistun = GetBooleanDataFromBytes(d, x);
            x += 4;
            player.antisleep = GetBooleanDataFromBytes(d, x);
            x += 4;
            player.antisleeporb = GetBooleanDataFromBytes(d, x);
            x += 4;
            currentRoomIdx = GetIntDataFromBytes(d, x);
            x += 4;
            gameStart = GetBooleanDataFromBytes(d, x);
            x += 4;
            MainWindow.isPoisoned = GetBooleanDataFromBytes(d, x);
            x += 4;
            MainWindow.hasVibration = GetBooleanDataFromBytes(d, x);
            x += 4;
            MainWindow.hasSound = GetBooleanDataFromBytes(d, x);
            x += 4;
            MainWindow.gameSubState = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.prevGameState = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.herbShopSubState = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.battleModeState = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.antisleepCount = GetIntDataFromBytes(d, x);
            x += 4;
        }
        return d;
    }

    public byte[] readSerializedInvData() {
        if (theSettings == null) {
            theSettings = Settings.getInstance();
        }
        byte[] d = theSettings.getSerializedInvData();
        return readSerializedInvData(d);
    }

    public byte[] readSerializedInvData(byte[] d) {
        if (d != null) {
            x = 0;
            for (i = 0; i < Constants.ITEM_INVENTORY_SIZE; i++) {
                if (d[(i * 2)] == -1) {
                    player.items[i][0] = -1;
                    player.items[i][1] = -1;
                } else if (d[(i * 2)] == -2) {
                    player.items[i][0] = -2;
                    player.items[i][1] = -2;
                } else {
                    player.items[i][0] = FORCE_UBYTE(d[(i * 2)]);
                    player.items[i][1] = FORCE_UBYTE(d[(i * 2) + 1]);
                }
            }
            x += 4;
        }
        return d;
    }

    //////////////////////////////////////////////
    
    //game state data (when game exits during dynamic states)
    public int calcGameStateSize() {
        int len = 0;
        len += 340;
        return len;
    }

    public byte[] storeSerializedStateData() {
        byte[] b = serializeStateData();
        theSettings.setSerializedStateData(b);
        return b;
    }

    public byte[] serializeStateData() {
        if (theSettings == null) {
            theSettings = Settings.getInstance();
        }
        
        if (player == null) {
            return null;
        }
        
        byte[] d = new byte[calcGameStateSize()];
        int x = 0;
        
        PutIntDataIntoBytes(d, x, MainWindow.lastStringUsed);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.lastStringArrayUsed);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.storyType);
        x += 4;

        PutIntDataIntoBytes(d, x, MainWindow.itemfound_i);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.itemfound_t);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.itemfound_type[0]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.itemfound_type[1]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.itemfound_type[2]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.itemfound_qty[0]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.itemfound_qty[1]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.itemfound_qty[2]);
        x += 4;

        PutIntDataIntoBytes(d, x, MainWindow.lrgMessageOffsetY);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.lrgMessageScrollOffsetY);
        x += 4;

        PutIntDataIntoBytes(d, x, MainWindow.itemCursorPos);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.itemcur_x);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.itemcur_y);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.item_maxu);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.item_maxv);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.selItem);
        x += 4;

        PutIntDataIntoBytes(d, x, booleanToInt(MainWindow.tmpBool));
        x += 4;
        PutIntDataIntoBytes(d, x, booleanToInt(MainWindow.inConv));
        x += 4;
        PutIntDataIntoBytes(d, x, booleanToInt(MainWindow.inConvPlayer));
        x += 4;
        PutIntDataIntoBytes(d, x, booleanToInt(MainWindow.inConvStayOnCurrent));
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.inConvNpcIndex);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.inConvPos);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.inConvPosTotal);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.lastNpcIndex);
        x += 4;

        //tmpBool
        //inConvNpcIndex
        //inConv
        //inConvPosTotal
        //inConvStayOnCurrent
        //inConvPlayer
        //inConvPos
        //restore battle state
        PutIntDataIntoBytes(d, x, MainWindow.battleModeState);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.battleSearchIndex);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.battleFlagIndex);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.battleFlagIndex1);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.battleFlagIndex2);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.gold);
        x += 4;

        PutIntDataIntoBytes(d, x, MainWindow.lastActiveEnemy);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.lastActiveEnemyTick);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.playerAttackType);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.whichEnemy);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.selectEnemyPosX);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.selectedEnemyPos);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.battleSong);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.currentBossBattleId);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.currentBossId);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.currentBossPos);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.splashEnemyCount);
        x += 4;
        PutIntDataIntoBytes(d, x, booleanToInt(MainWindow.allTargetsSelected));
        x += 4;
        PutIntDataIntoBytes(d, x, booleanToInt(MainWindow.isBoss));
        x += 4;
        PutIntDataIntoBytes(d, x, booleanToInt(MainWindow.selectEnemy));
        x += 4;

        if (MainWindow.enemies != null) {
            for (i = 0; i < MainWindow.enemies.length; i++) {
                if (MainWindow.enemies[i] != null) {
                    MainWindow.enemy_cooldown[i][0] = MainWindow.enemies[i].cooldown;
                    MainWindow.enemy_cooldown[i][1] = MainWindow.ENEMYWAIT_TIME[i];
                    MainWindow.enemy_hp[i] = MainWindow.enemies[i].hp;
                    MainWindow.enemy_isdead[i] = booleanToInt(MainWindow.enemies[i].isDead);
                } else {
                    MainWindow.enemy_isdead[i] = 1;
                }
            }
            PutIntDataIntoBytes(d, x, MainWindow.enemies.length);
            x += 4;
        } else {
            PutIntDataIntoBytes(d, x, 0);
            x += 4;
        }
        
        PutIntDataIntoBytes(d, x, MainWindow.enemy_index[0][0]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.enemy_index[0][1]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.enemy_index[1][0]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.enemy_index[1][1]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.enemy_index[2][0]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.enemy_index[2][1]);
        x += 4;

        PutIntDataIntoBytes(d, x, MainWindow.enemy_cooldown[0][0]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.enemy_cooldown[0][1]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.enemy_cooldown[1][0]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.enemy_cooldown[1][1]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.enemy_cooldown[2][0]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.enemy_cooldown[2][1]);
        x += 4;

        PutIntDataIntoBytes(d, x, MainWindow.enemy_hp[0]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.enemy_hp[1]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.enemy_hp[2]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.enemy_isdead[0]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.enemy_isdead[1]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.enemy_isdead[2]);
        x += 4;

        PutIntDataIntoBytes(d, x, MainWindow.attackType[0]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.attackType[1]);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.attackType[2]);
        x += 4;

        PutIntDataIntoBytes(d, x, player.cooldown);
        x += 4;
        PutIntDataIntoBytes(d, x, player.PLAYERWAIT_TIME);
        x += 4;

        PutIntDataIntoBytes(d, x, MainWindow.isEntrancedCount);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.isStunnedCount);
        x += 4;
        PutIntDataIntoBytes(d, x, booleanToInt(MainWindow.isEntranced));
        x += 4;
        PutIntDataIntoBytes(d, x, booleanToInt(MainWindow.isStunned));
        x += 4;
        PutIntDataIntoBytes(d, x, booleanToInt(MainWindow.superCharge));
        x += 4;

        // battle stats
        PutIntDataIntoBytes(d, x, player.battleMisses);
        x += 4;
        PutIntDataIntoBytes(d, x, player.battleDodges);
        x += 4;
        PutIntDataIntoBytes(d, x, player.battleKills);
        x += 4;
        PutIntDataIntoBytes(d, x, player.battleItems);
        x += 4;
        PutIntDataIntoBytes(d, x, player.battleRun);
        x += 4;
        PutIntDataIntoBytes(d, x, player.battleHitsGiven);
        x += 4;
        PutIntDataIntoBytes(d, x, player.battleHitsTaken);
        x += 4;
        PutIntDataIntoBytes(d, x, player.battleSuperAttackGiven);
        x += 4;
        PutIntDataIntoBytes(d, x, player.battleSuperAttackTaken);
        x += 4;
        return d;
    }

    public byte[] readSerializedStateData() {
        if (theSettings == null) {
            theSettings = Settings.getInstance();
        }
        byte[] d = theSettings.getSerializedStateData();
        return readSerializedStateData(d);
    }

    public byte[] readSerializedStateData(byte[] d) {
        if (d != null) {
            x = 0;
            MainWindow.lastStringUsed = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.lastStringArrayUsed = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.storyType = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.itemfound_i = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.itemfound_t = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.itemfound_type[0] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.itemfound_type[1] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.itemfound_type[2] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.itemfound_qty[0] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.itemfound_qty[1] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.itemfound_qty[2] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.tmplrgMessageOffsetY = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.tmplrgMessageScrollOffsetY = GetIntDataFromBytes(d, x);
            x += 4;

            MainWindow.tmpitemCursorPos = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.tmpitemcur_x = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.tmpitemcur_y = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.tmpitem_maxu = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.tmpitem_maxv = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.tmpselItem = GetIntDataFromBytes(d, x);
            x += 4;

            //conversation state
            MainWindow.tmpBool = intToBoolean(GetIntDataFromBytes(d, x));
            x += 4;
            MainWindow.inConv = intToBoolean(GetIntDataFromBytes(d, x));
            x += 4;
            MainWindow.inConvPlayer = intToBoolean(GetIntDataFromBytes(d, x));
            x += 4;
            MainWindow.inConvStayOnCurrent = intToBoolean(GetIntDataFromBytes(d, x));
            x += 4;
            MainWindow.inConvNpcIndex = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.inConvPos = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.inConvPosTotal = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.lastNpcIndex = GetIntDataFromBytes(d, x);
            x += 4;

            //restore last enemy state
            MainWindow.battleModeState = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.battleSearchIndex = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.battleFlagIndex = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.battleFlagIndex1 = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.battleFlagIndex2 = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.gold = GetIntDataFromBytes(d, x);
            x += 4;

            MainWindow.lastActiveEnemy = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.lastActiveEnemyTick = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.playerAttackType = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.whichEnemy = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.selectEnemyPosX = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.selectedEnemyPos = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.battleSong = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.currentBossBattleId = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.currentBossId = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.currentBossPos = GetIntDataFromBytes(d, x);
            x += 4;

            MainWindow.splashEnemyCount = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.allTargetsSelected = intToBoolean(GetIntDataFromBytes(d, x));
            x += 4;
            MainWindow.isBoss = intToBoolean(GetIntDataFromBytes(d, x));
            x += 4;
            MainWindow.selectEnemy = intToBoolean(GetIntDataFromBytes(d, x));
            x += 4;

            MainWindow.numEnemies = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_index[0][0] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_index[0][1] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_index[1][0] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_index[1][1] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_index[2][0] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_index[2][1] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_cooldown[0][0] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_cooldown[0][1] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_cooldown[1][0] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_cooldown[1][1] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_cooldown[2][0] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_cooldown[2][1] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_hp[0] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_hp[1] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_hp[2] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_isdead[0] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_isdead[1] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.enemy_isdead[2] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.attackType[0] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.attackType[1] = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.attackType[2] = GetIntDataFromBytes(d, x);
            x += 4;

            player.cooldown = GetIntDataFromBytes(d, x);
            x += 4;
            player.PLAYERWAIT_TIME = GetIntDataFromBytes(d, x);
            x += 4;

            MainWindow.isEntrancedCount = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.isStunnedCount = GetIntDataFromBytes(d, x);
            x += 4;
            MainWindow.isEntranced = intToBoolean(GetIntDataFromBytes(d, x));
            x += 4;
            MainWindow.isStunned = intToBoolean(GetIntDataFromBytes(d, x));
            x += 4;
            MainWindow.superCharge = intToBoolean(GetIntDataFromBytes(d, x));
            x += 4;

            player.battleMisses = GetIntDataFromBytes(d, x);
            x += 4;
            player.battleDodges = GetIntDataFromBytes(d, x);
            x += 4;
            player.battleKills = GetIntDataFromBytes(d, x);
            x += 4;
            player.battleItems = GetIntDataFromBytes(d, x);
            x += 4;
            player.battleRun = GetIntDataFromBytes(d, x);
            x += 4;
            player.battleHitsGiven = GetIntDataFromBytes(d, x);
            x += 4;
            player.battleHitsTaken = GetIntDataFromBytes(d, x);
            x += 4;
            player.battleSuperAttackGiven = GetIntDataFromBytes(d, x);
            x += 4;
            player.battleSuperAttackTaken = GetIntDataFromBytes(d, x);
            x += 4;
        }
        return d;
    }
    
    ///////////////////////////////////////////////

    public byte[] storeSerializedGameData() {
        Logger.wr("storeSerializedGameData");
        byte[] b = serializeGameData();
        theSettings.setSerializedGameData(b);
        return b;
    }

    public byte[] serializeGameData() {
        Logger.wr("serializeGameData");        
        if (theSettings == null) {
            theSettings = Settings.getInstance();
        }

        if (player == null) {
            return null;
        }

        byte[] d = new byte[calcGameDataSize()];
        int x = 0;
        PutIntDataIntoBytes(d, x, player.charType);
        x += 4;
        PutIntDataIntoBytes(d, x, player.ex);
        x += 4;
        PutIntDataIntoBytes(d, x, player.lv);
        x += 4;
        PutIntDataIntoBytes(d, x, player.totalHitsTaken);
        x += 4;
        PutIntDataIntoBytes(d, x, player.totalHitsGiven);
        x += 4;
        PutIntDataIntoBytes(d, x, player.totalSuperAttackTaken);
        x += 4;
        PutIntDataIntoBytes(d, x, player.totalSuperAttackGiven);
        x += 4;
        PutIntDataIntoBytes(d, x, player.totalMisses);
        x += 4;
        PutIntDataIntoBytes(d, x, player.totalDodges);
        x += 4;
        PutIntDataIntoBytes(d, x, player.totalRun);
        x += 4;
        PutIntDataIntoBytes(d, x, player.totalKills);
        x += 4;
        PutIntDataIntoBytes(d, x, player.totalItems);
        x += 4;
        PutIntDataIntoBytes(d, x, player.totalDeaths);
        x += 4;
        PutIntDataIntoBytes(d, x, player.damage);
        x += 4;
        PutIntDataIntoBytes(d, x, player.speed);
        x += 4;
        PutIntDataIntoBytes(d, x, player.hp);
        x += 4;
        PutIntDataIntoBytes(d, x, player.ap);
        x += 4;
        PutIntDataIntoBytes(d, x, player.dp);
        x += 4;
        PutIntDataIntoBytes(d, x, player.goldCoins);
        x += 4;
        PutIntDataIntoBytes(d, x, player.posX);
        x += 4;
        PutIntDataIntoBytes(d, x, player.posY);
        x += 4;
        PutIntDataIntoBytes(d, x, player.dir);
        x += 4;
        PutIntDataIntoBytes(d, x, player.weapon);
        x += 4;
        PutIntDataIntoBytes(d, x, player.armor);
        x += 4;
        PutIntDataIntoBytes(d, x, player.PLAYERWAIT_TIME);
        x += 4;
        PutBooleanDataIntoBytes(d, x, player.antipoison);
        x += 4;
        PutBooleanDataIntoBytes(d, x, player.antistun);
        x += 4;
        PutBooleanDataIntoBytes(d, x, player.antisleep);
        x += 4;
        PutBooleanDataIntoBytes(d, x, player.antisleeporb);
        x += 4;
        PutIntDataIntoBytes(d, x, currentRoomIdx);
        x += 4;
        PutBooleanDataIntoBytes(d, x, gameStart);
        x += 4;
        PutBooleanDataIntoBytes(d, x, MainWindow.isPoisoned);
        x += 4;
        PutBooleanDataIntoBytes(d, x, MainWindow.hasVibration);
        x += 4;
        PutBooleanDataIntoBytes(d, x, MainWindow.hasSound);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.gameSubState);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.prevGameState);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.herbShopSubState);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.battleModeState);
        x += 4;
        PutIntDataIntoBytes(d, x, MainWindow.antisleepCount);
        x += 4;

        return d;
    }

    public void storeSettings() {
        //this is really not the "settings" from settings menu, it is just player name and flags array
        if (theSettings == null) {
            theSettings = Settings.getInstance();
        }
        if (ResourceContainer.flags == null) {
            return;
        }
        if (ResourceContainer.strings == null) {
            return;
        }
        if (player == null) {
            return;
        }
        if (player.items == null) {
            return;
        }
        theSettings.setFlags(ResourceContainer.flags);
        theSettings.setPlayerName(ResourceContainer.strings[Constants.PLAYER_NAME_INDEX]);
    }

    public byte[] storeSerializedInvData() {
        byte[] b = serializeInvData();
        theSettings.setSerializedInvData(b);
        return b;
    }

    public byte[] serializeInvData() {
        if (theSettings == null) {
            theSettings = Settings.getInstance();
        }
        if (player == null) {
            return null;
        }
        byte[] d = new byte[Constants.ITEM_INVENTORY_SIZE * 2];
        int x = 0;
        for (i = 0; i < Constants.ITEM_INVENTORY_SIZE; i++) {
            d[(i * 2)] = (byte) player.items[i][0];
            d[(i * 2) + 1] = (byte) player.items[i][1];
        }
        return d;
    }

    private void setDefaults() {
        if (theSettings == null) {
            theSettings = Settings.getInstance();
        }
        theSettings.setFlags(null);
        theSettings.setPlayerName(ResourceContainer.strings[Constants.PLAYER_NAME_INDEX]);
    }

    public static void PutShortDataIntoBytes(byte[] a, int i, int d) {
        a[(i)] = (byte) ((d) >> 8);
        a[(i) + 1] = (byte) (d);
    }

    public static void PutIntDataIntoBytes(byte[] a, int i, int d) {
        a[(i)] = (byte) ((d) >> 24);
        a[(i) + 1] = (byte) ((d) >> 16);
        a[(i) + 2] = (byte) ((d) >> 8);
        a[(i) + 3] = (byte) (d);
    }

    public static void PutBooleanDataIntoBytes(byte[] a, int i, boolean d) {
        if (d) {
            PutIntDataIntoBytes(a, i, 1);
        } else {
            PutIntDataIntoBytes(a, i, 0);
        }
    }

    public static short GetShortDataFromBytes(byte[] a, int i) {
        return ((short) ((a[(i)] << 8) + FORCE_UBYTE(a[(i) + 1])));
    }

    public static boolean GetBooleanDataFromBytes(byte[] a, int i) {
        tmp = GetIntDataFromBytes(a, i);
        if (tmp == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static int GetIntDataFromBytes(byte[] a, int i) {
        return ((a[(i)] << 24) + (FORCE_UBYTE(a[(i) + 1]) << 16) + (FORCE_UBYTE(a[(i) + 2]) << 8) + FORCE_UBYTE(a[(i) + 3]));
    }

    public static int GetIntDataFromBytesLI(byte[] a, int i) {
        return ((a[(i) + 3] << 24) + (FORCE_UBYTE(a[(i) + 2]) << 16) + (FORCE_UBYTE(a[(i) + 1]) << 8) + FORCE_UBYTE(a[(i)]));
    }

    public static int FORCE_UBYTE(int a) {
        return ((a) & 0x000000ff);
    }

    public static int FORCE_USHORT(int a) {
        return ((a) & 0x0000ffff);
    }
    
    public static byte[] toByteArray(int j, byte[] array) {
        int tmp;
        byte b;
        for (int i = 0; i < array.length; i++) {
            tmp = j;
            if (i > 0) {
                tmp = tmp >> (i * 8);
            }
            tmp = tmp % 0xFF000000;
            b = (byte) tmp;
            array[i] = b;
        }
        return array;
    }

    public static boolean intToBoolean(int i) {
        if (i == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static int booleanToInt(boolean b) {
        if (b) {
            return 1;
        } else {
            return 0;
        }
    }
}