using com.middlemindgames.dat;
using danger.util;
using System;
using System.Collections.Generic;
using System.Text;

namespace com.middlemindgames.dat {
    /*
     * loadDat.java
     * Victor G. Brusca 01/16/2022
     * Created on June 1, 2005, 10:57 PM by Middlemind Games
     */
    public class loadDat {
        public static byte[] chapter;

        //dat reader variables
        private static int objectType;
        public static int pos;
        public static bool foundEOF;
        private static byte[] png;
        private static int k;
        public static int len;
        private static int ltmp;
        public static bool stop;
        public static bool loadFlags;
        public static ResourceContainer rc;

        public loadDat() {
            stop = false;
        }

        public loadDat(ResourceContainer rc) {
            stop = false;
            loadDat.rc = rc;
        }

        public void setContainerObj(ResourceContainer rc) {
            loadDat.rc = rc;
        }

        public bool readWorkbookGlobals() {
            try {
                objectType = 0;
                foundEOF = false;
                pos = 0;

                while (!foundEOF) {
                    try {
                        objectType = byteToInt(1);
                        //Logger.wr("readWorkbookGlobals: Found objectType:" + objectType + " pos:" + pos);

                        if (objectType == 9) {
                            foundEOF = true;
                            break;
                        } else {
                            parseObject(objectType);
                        }
                    } catch (Exception e) {
                        Logger.wr("Exception encountered at pos: " + pos);
                        Logger.wr(e.Message);
                        Logger.wr(e.StackTrace);
                        foundEOF = true;
                    }
                }
                return true;

            } catch (Exception e) {
                return false;
            }
        }

        public void parseObject(int type) {
            try {
                switch (type) {
                    case 0:
                        //found BOF
                        return;

                    case 2:
                        //found image list object, image list count		
                        ResourceContainer.bitmaps = new bitmapBuffer[byteToInt(4)];
                        ltmp = 0;
                        return;

                    case 1:
                        //found image object
                        len = byteToInt(4);
                        ResourceContainer.bitmaps[ltmp] = new bitmapBuffer();
                        ResourceContainer.bitmaps[ltmp].png = new byte[len];
                        //System.arraycopy(chapter, pos, ResourceContainer.bitmaps[ltmp].png, 0, len);
                        Array.Copy(chapter, pos, ResourceContainer.bitmaps[ltmp].png, 0, len);
                        pos += len;
                        ltmp++;
                        return;

                    case 4:
                        //found tile list object, tile list count
                        ResourceContainer.tiles = new tile[byteToInt(4)];
                        ltmp = 0;
                        return;

                    case 3:
                        //found tile object
                        ResourceContainer.tiles[ltmp] = new tile();
                        //tile image id
                        ResourceContainer.tiles[ltmp].imageIndex = byteToInt(2);
                        //tile can walk
                        ResourceContainer.tiles[ltmp].setCanWalk(byteToInt(1));
                        ltmp++;
                        return;

                    case 6:
                        //found character list object
                        ResourceContainer.players = new player[byteToInt(2)];
                        ltmp = 0;
                        return;

                    case 5:
                        //found character
                        ResourceContainer.players[ltmp] = new player();
                        //found string index, NI
                        ResourceContainer.players[ltmp].stringIndex = byteToInt(2);
                        //found experience, EX
                        ResourceContainer.players[ltmp].ex = byteToInt(1);
                        //found level, LV
                        ResourceContainer.players[ltmp].lv = byteToInt(1);
                        //found hit points, HP
                        ResourceContainer.players[ltmp].hp = byteToInt(1);
                        //found magic points, MP
                        ResourceContainer.players[ltmp].mp = byteToInt(1);
                        //found attack points, AP, index into attackRolls array
                        ResourceContainer.players[ltmp].ap = byteToInt(1);
                        //found defense points, DP
                        ResourceContainer.players[ltmp].dp = byteToInt(1);
                        //found speed, S
                        ResourceContainer.players[ltmp].speed = byteToInt(1);
                        //found starting room
                        ResourceContainer.players[ltmp].startingRoom = byteToInt(2);
                        //found fc
                        ResourceContainer.players[ltmp].fc = byteToInt(2);
                        //found fr
                        ResourceContainer.players[ltmp].fr = byteToInt(2);
                        //found fl
                        ResourceContainer.players[ltmp].fl = byteToInt(2);
                        //found bc
                        ResourceContainer.players[ltmp].bc = byteToInt(2);
                        //found br
                        ResourceContainer.players[ltmp].br = byteToInt(2);
                        //found bl
                        ResourceContainer.players[ltmp].bl = byteToInt(2);
                        //found rc
                        ResourceContainer.players[ltmp].rc = byteToInt(2);
                        //found rr
                        ResourceContainer.players[ltmp].rr = byteToInt(2);
                        //found rl
                        ResourceContainer.players[ltmp].rl = byteToInt(2);
                        //found lc
                        ResourceContainer.players[ltmp].lc = byteToInt(2);
                        //found lr
                        ResourceContainer.players[ltmp].lr = byteToInt(2);
                        //found ll
                        ResourceContainer.players[ltmp].ll = byteToInt(2);
                        ltmp++;
                        return;

                    case 8:
                        //found room list object, Room count
                        ResourceContainer.rooms = new room[byteToInt(4)];
                        ltmp = 0;
                        return;

                    case 7:
                        //found room object
                        ResourceContainer.rooms[ltmp] = new room();
                        ResourceContainer.rooms[ltmp].roomDataInfo = new roomData[byteToInt(1)];
                        ResourceContainer.rooms[ltmp].roomType = byteToInt(1);
                        len = ResourceContainer.rooms[ltmp].roomDataInfo.Length;

                        for (k = 0; k < len; k++) {
                            ResourceContainer.rooms[ltmp].roomDataInfo[k] = new roomData();
                            ResourceContainer.rooms[ltmp].roomDataInfo[k].tiles_id = byteToInt(2);
                            ResourceContainer.rooms[ltmp].roomDataInfo[k].top = byteToInt(1);
                            ResourceContainer.rooms[ltmp].roomDataInfo[k].left = byteToInt(1);
                            ResourceContainer.rooms[ltmp].roomDataInfo[k].bottom = byteToInt(1);
                            ResourceContainer.rooms[ltmp].roomDataInfo[k].right = byteToInt(1);
                        }
                        ltmp++;
                        return;

                    case 9:
                        //found EOF
                        foundEOF = true;
                        return;

                    case 11:
                        //found object list object, object count
                        ResourceContainer.objects = new tyreObject[byteToInt(4)];
                        ltmp = 0;
                        return;

                    case 10:
                        //found object file
                        ResourceContainer.objects[ltmp] = new tyreObject();
                        //found id, object type
                        ResourceContainer.objects[ltmp].type = byteToInt(1);
                        switch (ResourceContainer.objects[ltmp].type) {
                            case 0:
                                //link table object
                                //found ii, image index
                                ResourceContainer.objects[ltmp].imageIndex = processInt(byteToInt(2));
                                //found lt, link to
                                ResourceContainer.objects[ltmp].linkTo = processInt(byteToInt(2));
                                //found px, posX
                                ResourceContainer.objects[ltmp].posX = processInt(byteToInt(2));
                                //found py, posY
                                ResourceContainer.objects[ltmp].posY = processInt(byteToInt(2));
                                //flag index
                                ResourceContainer.objects[ltmp].flagIndex = processInt(byteToInt(2));
                                //dest posX
                                ResourceContainer.objects[ltmp].destPosX = byteToInt(1);
                                //dest posY
                                ResourceContainer.objects[ltmp].destPosY = byteToInt(1);
                                //flag dir
                                ResourceContainer.objects[ltmp].flagDir = byteToInt(1);
                                //dest dir
                                ResourceContainer.objects[ltmp].destDir = byteToInt(1);
                                break;

                            case 1:
                                //can't walk object
                                //found ii, image index
                                ResourceContainer.objects[ltmp].imageIndex = processInt(byteToInt(2));
                                //found px, posX
                                ResourceContainer.objects[ltmp].posX = processInt(byteToInt(2));
                                //found py, posY
                                ResourceContainer.objects[ltmp].posY = processInt(byteToInt(2));
                                //flag index
                                ResourceContainer.objects[ltmp].flagIndex = processInt(byteToInt(2));
                                //found index into string array
                                ResourceContainer.objects[ltmp].stringIndex = processInt(byteToInt(2));
                                //flag dir
                                ResourceContainer.objects[ltmp].flagDir = byteToInt(1);
                                //vertical offset
                                ResourceContainer.objects[ltmp].vertOffset = byteToInt(1);
                                break;

                            case 2:
                                //can walk under object
                                //found ii, image index
                                ResourceContainer.objects[ltmp].imageIndex = processInt(byteToInt(2));
                                //found px, posX
                                ResourceContainer.objects[ltmp].posX = processInt(byteToInt(2));
                                //found py, posY
                                ResourceContainer.objects[ltmp].posY = processInt(byteToInt(2));
                                break;

                            case 3:
                                //character
                                //found character index
                                ResourceContainer.objects[ltmp].charIndex = processInt(byteToInt(2));
                                //found px, posX
                                ResourceContainer.objects[ltmp].posX = processInt(byteToInt(2));
                                //found py, posY
                                ResourceContainer.objects[ltmp].posY = processInt(byteToInt(2));
                                break;

                            case 4:
                                //can walk over
                                //found ii, image index
                                ResourceContainer.objects[ltmp].imageIndex = processInt(byteToInt(2));
                                //found px, posX
                                ResourceContainer.objects[ltmp].posX = processInt(byteToInt(2));
                                //found py, posY
                                ResourceContainer.objects[ltmp].posY = processInt(byteToInt(2));
                                break;

                            case 5:
                                //enemy attack object
                                //found ii, image index
                                ResourceContainer.objects[ltmp].imageIndex = processInt(byteToInt(2));
                                //found px, posX
                                ResourceContainer.objects[ltmp].posX = processInt(byteToInt(2));
                                //found py, posY
                                ResourceContainer.objects[ltmp].posY = processInt(byteToInt(2));
                                //flag index
                                ResourceContainer.objects[ltmp].flagIndex = processInt(byteToInt(2));
                                //battle index
                                ResourceContainer.objects[ltmp].battleIndex = processInt(byteToInt(2));
                                //flag dir
                                ResourceContainer.objects[ltmp].flagDir = byteToInt(1);
                                break;

                            case 6:
                                //npc
                                //found ci, npc index
                                ResourceContainer.objects[ltmp].npcIndex = processInt(byteToInt(2));
                                //found px, posX
                                ResourceContainer.objects[ltmp].posX = processInt(byteToInt(2));
                                //found py, posY
                                ResourceContainer.objects[ltmp].posY = processInt(byteToInt(2));
                                //flag index
                                ResourceContainer.objects[ltmp].flagIndex = processInt(byteToInt(2));
                                //conversation index
                                ResourceContainer.objects[ltmp].conversationIndex = processInt(byteToInt(2));
                                //flag dir
                                ResourceContainer.objects[ltmp].flagDir = byteToInt(1);
                                //string index
                                ResourceContainer.objects[ltmp].stringIndex = processInt(byteToInt(2));
                                break;

                            case 7:
                                //hidden item object
                                //found px, posX
                                ResourceContainer.objects[ltmp].posX = processInt(byteToInt(2));
                                //found py, posY
                                ResourceContainer.objects[ltmp].posY = processInt(byteToInt(2));
                                //found search item
                                ResourceContainer.objects[ltmp].searchItem = processInt(byteToInt(2));
                                break;

                            case 8:
                                //can walk over with rotation
                                //found ii, image index
                                ResourceContainer.objects[ltmp].imageIndex = processInt(byteToInt(2));
                                //found px, posX
                                ResourceContainer.objects[ltmp].posX = processInt(byteToInt(2));
                                //found py, posY
                                ResourceContainer.objects[ltmp].posY = processInt(byteToInt(2));
                                //angle of rotation
                                ResourceContainer.objects[ltmp].angle = processInt(byteToInt(2));
                                break;

                            case 9:
                                //object set without rotation
                                ResourceContainer.objects[ltmp].objectSetsIndex = processInt(byteToInt(2));
                                //found px, posX
                                ResourceContainer.objects[ltmp].posX = processInt(byteToInt(2));
                                //found py, posY
                                ResourceContainer.objects[ltmp].posY = processInt(byteToInt(2));
                                break;

                            case 10:
                                //object set with rotation
                                ResourceContainer.objects[ltmp].objectSetsIndex = processInt(byteToInt(2));
                                //found px, posX
                                ResourceContainer.objects[ltmp].posX = processInt(byteToInt(2));
                                //found py, posY
                                ResourceContainer.objects[ltmp].posY = processInt(byteToInt(2));
                                //angle of rotation
                                ResourceContainer.objects[ltmp].angle = processInt(byteToInt(2));
                                break;

                            case 11:
                                //object set can't walk
                                ResourceContainer.objects[ltmp].objectSetsIndex = processInt(byteToInt(2));
                                //found px, posX
                                ResourceContainer.objects[ltmp].posX = processInt(byteToInt(2));
                                //found py, posY
                                ResourceContainer.objects[ltmp].posY = processInt(byteToInt(2));
                                break;

                            case 12:
                                //can walk over (dynamic)
                                //found ii, image index
                                ResourceContainer.objects[ltmp].imageIndex = processInt(byteToInt(2));
                                //found px, posX
                                ResourceContainer.objects[ltmp].posX = processInt(byteToInt(2));
                                //found py, posY
                                ResourceContainer.objects[ltmp].posY = processInt(byteToInt(2));
                                //flag index
                                ResourceContainer.objects[ltmp].flagIndex = processInt(byteToInt(2));
                                //flag dir
                                ResourceContainer.objects[ltmp].flagDir = byteToInt(1);
                                break;
                        }
                        ltmp++;
                        return;
                    case 13:
                        //found linkTable list object, linkTable count
                        ResourceContainer.linkTables = new linkTable[byteToInt(4)];
                        ltmp = 0;
                        return;

                    case 12:
                        //found linktable file
                        ResourceContainer.linkTables[ltmp] = new linkTable();
                        //found rm, room index
                        ResourceContainer.linkTables[ltmp].roomIndex = byteToInt(2);
                        //found lt, link top
                        ResourceContainer.linkTables[ltmp].linkTop = processInt(byteToInt(2));
                        //found ll, link left
                        ResourceContainer.linkTables[ltmp].linkLeft = processInt(byteToInt(2));
                        //found lb, link bottom
                        ResourceContainer.linkTables[ltmp].linkBottom = processInt(byteToInt(2));
                        //found lr, link right
                        ResourceContainer.linkTables[ltmp].linkRight = processInt(byteToInt(2));
                        //found fr, enemy frequency
                        ResourceContainer.linkTables[ltmp].setFrequency(byteToInt(1));
                        //flag index
                        ResourceContainer.linkTables[ltmp].flagIndex = processInt(byteToInt(2));
                        //string index
                        ResourceContainer.linkTables[ltmp].stringIndex = processInt(byteToInt(2));
                        //object set index
                        ResourceContainer.linkTables[ltmp].objectSetIndex = processInt(byteToInt(2));
                        //enemy levels
                        ResourceContainer.linkTables[ltmp].setEnemyLevel(byteToInt(1));
                        ltmp++;
                        return;

                    case 15:
                        //found string list object, strings count
                        ResourceContainer.strings = new String[byteToInt(4)];
                        ltmp = 0;
                        return;

                    case 14:
                        //found string object
                        len = byteToInt(2);
                        ResourceContainer.strings[ltmp] = Encoding.UTF8.GetString(chapter, pos, len); //new String(chapter, pos, len, "UTF-8");
                        pos += len;
                        ltmp++;
                        return;

                    case 17:
                        //found enemy list object
                        byteToInt(4);
                        //enemy count
                        byteToInt(4);
                        ltmp = 0;
                        return;

                    case 16:
                        //found enemy file
                        k = byteToInt(1) - 1;
                        ResourceContainer.enemies[k][ltmp] = new enemy();
                        //store level information
                        ResourceContainer.enemies[k][ltmp].lv = (k + 1);
                        //found hit points, hp
                        ResourceContainer.enemies[k][ltmp].hp = byteToInt(1);
                        //found magic points, mp
                        ResourceContainer.enemies[k][ltmp].mp = byteToInt(1);
                        //found attack points, ap
                        ResourceContainer.enemies[k][ltmp].ap = byteToInt(1);
                        //found defence points, dp
                        ResourceContainer.enemies[k][ltmp].dp = byteToInt(1);
                        //found speed, s
                        ResourceContainer.enemies[k][ltmp].speed = byteToInt(1);
                        //found image index, ii
                        ResourceContainer.enemies[k][ltmp].imageIndex = byteToInt(2);
                        //found enemy name, en
                        ResourceContainer.enemies[k][ltmp].stringIndex = byteToInt(2);
                        //founnd ai type
                        ResourceContainer.enemies[k][ltmp].aiType = byteToInt(1);
                        //found max gold coins, gm
                        ResourceContainer.enemies[k][ltmp].goldCoinsMax = byteToInt(1);
                        if (ltmp == (ResourceContainer.enemies[k].Length - 1)) {
                            ltmp = 0;
                        } else {
                            ltmp++;
                        }
                        return;

                    case 19:
                        //found console image list object, image list count
                        ResourceContainer.consoleBitmaps = new bitmapBuffer[byteToInt(4)];
                        ltmp = 0;
                        return;

                    case 18:
                        //found console image object
                        len = byteToInt(4);
                        ResourceContainer.consoleBitmaps[ltmp] = new bitmapBuffer();
                        ResourceContainer.consoleBitmaps[ltmp].png = new byte[len];
                        //System.arraycopy(chapter, pos, ResourceContainer.consoleBitmaps[ltmp].png, 0, len);
                        Array.Copy(chapter, pos, ResourceContainer.consoleBitmaps[ltmp].png, 0, len);
                        pos += len;
                        ltmp++;
                        return;

                    case 21:
                        //found attackRoll list object, attackRoll count
                        ResourceContainer.attackRolls = new attackRoll[byteToInt(1)];
                        ltmp = 0;
                        return;

                    case 20:
                        //found attackRoll file
                        ResourceContainer.attackRolls[ltmp] = new attackRoll();
                        //found MX, max
                        ResourceContainer.attackRolls[ltmp].max = byteToInt(1);
                        //found MN, min
                        ResourceContainer.attackRolls[ltmp].min = byteToInt(1);
                        //found TM, times
                        ResourceContainer.attackRolls[ltmp].times = byteToInt(1);
                        ltmp++;
                        return;

                    case 23:
                        //found npcs list object
                        byteToInt(4);
                        //npcs count
                        ResourceContainer.npcs = new npcdata[byteToInt(4)];
                        ltmp = 0;
                        return;

                    case 22:
                        //found npc
                        ResourceContainer.npcs[ltmp] = new npcdata();
                        //set npcType
                        ResourceContainer.npcs[ltmp].npcType = byteToInt(2);
                        //found string index, NI
                        ResourceContainer.npcs[ltmp].stringIndex = byteToInt(2);
                        //found fc
                        ResourceContainer.npcs[ltmp].fc = byteToInt(2);
                        //found fr
                        ResourceContainer.npcs[ltmp].fr = byteToInt(2);
                        //found fl
                        ResourceContainer.npcs[ltmp].fl = byteToInt(2);
                        //found bc
                        ResourceContainer.npcs[ltmp].bc = byteToInt(2);
                        //found br
                        ResourceContainer.npcs[ltmp].br = byteToInt(2);
                        //found bl
                        ResourceContainer.npcs[ltmp].bl = byteToInt(2);
                        //found rc
                        ResourceContainer.npcs[ltmp].rc = byteToInt(2);
                        //found rr
                        ResourceContainer.npcs[ltmp].rr = byteToInt(2);
                        //found rl
                        ResourceContainer.npcs[ltmp].rl = byteToInt(2);
                        //found lc
                        ResourceContainer.npcs[ltmp].lc = byteToInt(2);
                        //found lr
                        ResourceContainer.npcs[ltmp].lr = byteToInt(2);
                        //found ll
                        ResourceContainer.npcs[ltmp].ll = byteToInt(2);
                        //set can walk
                        ResourceContainer.npcs[ltmp].setCanWalk(byteToInt(1));
                        //set dir
                        ResourceContainer.npcs[ltmp].dir = byteToInt(1);
                        ltmp++;
                        return;

                    case 27:
                        //found item list object
                        byteToInt(4);
                        //item count
                        ResourceContainer.items = new item[byteToInt(4)];
                        ltmp = 0;
                        return;

                    case 26:
                        //found item
                        ResourceContainer.items[ltmp] = new item();
                        //found type
                        ResourceContainer.items[ltmp].type = byteToInt(2);
                        //found attack roll
                        ResourceContainer.items[ltmp].ap = byteToInt(2);
                        //found flags index
                        ResourceContainer.items[ltmp].flagsIndex = byteToInt(2);
                        //read strings index
                        ResourceContainer.items[ltmp].readStringsIndex = byteToInt(2);
                        //found string index
                        ResourceContainer.items[ltmp].stringIndex = byteToInt(2);
                        //found image entry
                        ResourceContainer.items[ltmp].imageIndex = byteToInt(2);
                        //cost
                        ResourceContainer.items[ltmp].cost = byteToInt(2);
                        ltmp++;
                        return;

                    case 29:
                        //found flag list object
                        byteToInt(4);
                        //flag count
                        k = byteToInt(4);
                        if (ResourceContainer.flags == null) {
                            loadFlags = true;
                            ResourceContainer.flags = new bool[k];
                        } else if (ResourceContainer.flags != null && ResourceContainer.flags.Length != k) {
                            loadFlags = true;
                            ResourceContainer.flags = new bool[k];
                        } else {
                            loadFlags = false;
                        }
                        return;

                    case 31:
                        //found search list object
                        byteToInt(4);
                        //search count
                        ResourceContainer.searches = new search[byteToInt(4)];
                        ltmp = 0;
                        return;

                    case 30:
                        //found search
                        ResourceContainer.searches[ltmp] = new search();
                        //found item 1
                        ResourceContainer.searches[ltmp].item1 = processInt(byteToInt(2));
                        //found quantity 1
                        ResourceContainer.searches[ltmp].quantity1 = processInt(byteToInt(1));
                        //found item 2
                        ResourceContainer.searches[ltmp].item2 = processInt(byteToInt(2));
                        //found quantity 2
                        ResourceContainer.searches[ltmp].quantity2 = processInt(byteToInt(1));
                        //found item 3
                        ResourceContainer.searches[ltmp].item3 = processInt(byteToInt(2));
                        //found quantity 3
                        ResourceContainer.searches[ltmp].quantity3 = processInt(byteToInt(1));
                        //found flag index
                        ResourceContainer.searches[ltmp].flagIndex = processInt(byteToInt(2));
                        //flag dir
                        ResourceContainer.searches[ltmp].flagDir = byteToInt(1);
                        //found flag index 2
                        ResourceContainer.searches[ltmp].flagIndex2 = processInt(byteToInt(2));
                        //found flag index 3
                        ResourceContainer.searches[ltmp].flagIndex3 = processInt(byteToInt(2));
                        //found flag index 4
                        ResourceContainer.searches[ltmp].flagIndex4 = processInt(byteToInt(2));
                        //found flag index 5
                        ResourceContainer.searches[ltmp].flagIndex5 = processInt(byteToInt(2));
                        ltmp++;
                        return;

                    case 33:
                        //found object sets list object
                        byteToInt(4);
                        //object sets count
                        ResourceContainer.objectSets = new int[byteToInt(4)][];
                        ltmp = 0;
                        return;

                    case 32:
                        len = byteToInt(1);
                        ResourceContainer.objectSets[ltmp] = new int[len];
                        len = ResourceContainer.objectSets[ltmp].Length;
                        for (k = 0; k < len; k++) {
                            ResourceContainer.objectSets[ltmp][k] = byteToInt(2);
                        }
                        ltmp++;
                        return;

                    case 35:
                        //found enemy dimensions list object
                        byteToInt(4);
                        //enemies dimensions count
                        ResourceContainer.enemies = new enemy[byteToInt(4)][];
                        return;

                    case 34:
                        len = ResourceContainer.enemies.Length;
                        for (k = 0; k < len; k++) {
                            ResourceContainer.enemies[k] = new enemy[byteToInt(1)];
                        }
                        return;

                    case 37:
                        //found conversations list object
                        byteToInt(4);
                        //conversations count
                        ltmp = 0;
                        ResourceContainer.conversations = new conversation[byteToInt(4)];
                        return;

                    case 36:
                        //found conversation object
                        ResourceContainer.conversations[ltmp] = new conversation();
                        ResourceContainer.conversations[ltmp].stringIdNpc = processInt(byteToInt(2));
                        ResourceContainer.conversations[ltmp].searchIndex = processInt(byteToInt(2));
                        ResourceContainer.conversations[ltmp].flagIndex = processInt(byteToInt(2));
                        ResourceContainer.conversations[ltmp].conversationIndex = processInt(byteToInt(2));
                        ResourceContainer.conversations[ltmp].setSelfTriggered(byteToInt(1));
                        ResourceContainer.conversations[ltmp].setTakeItem(byteToInt(1));
                        ResourceContainer.conversations[ltmp].battlesIndex = processInt(byteToInt(1));
                        ResourceContainer.conversations[ltmp].flagIndex1 = processInt(byteToInt(2));
                        ltmp++;
                        return;

                    case 39:
                        //found battles list object, conversations count
                        ltmp = 0;
                        ResourceContainer.battles = new battle[byteToInt(1)];
                        return;

                    case 38:
                        //found battle object
                        ResourceContainer.battles[ltmp] = new battle();
                        ResourceContainer.battles[ltmp].enemyId1 = processInt(byteToInt(1));
                        ResourceContainer.battles[ltmp].enemyDim1 = processInt(byteToInt(1));
                        ResourceContainer.battles[ltmp].enemyId2 = processInt(byteToInt(1));
                        ResourceContainer.battles[ltmp].enemyDim2 = processInt(byteToInt(1));
                        ResourceContainer.battles[ltmp].enemyId3 = processInt(byteToInt(1));
                        ResourceContainer.battles[ltmp].enemyDim3 = processInt(byteToInt(1));
                        ResourceContainer.battles[ltmp].bossId = processInt(byteToInt(1));
                        ResourceContainer.battles[ltmp].flagIndex = processInt(byteToInt(2));
                        ResourceContainer.battles[ltmp].flagIndex1 = processInt(byteToInt(2));
                        ResourceContainer.battles[ltmp].searchId = processInt(byteToInt(2));
                        ResourceContainer.battles[ltmp].flagIndex2 = processInt(byteToInt(2));
                        ltmp++;
                        return;

                    case 41:
                        //found console string list object
                        byteToInt(4);
                        //consoleStrings count
                        ResourceContainer.consoleStrings = new String[byteToInt(4)];
                        ltmp = 0;
                        return;

                    case 40:
                        //found string object
                        len = byteToInt(2);                        
                        ResourceContainer.consoleStrings[ltmp] = Encoding.UTF8.GetString(chapter, pos, len); //new String(chapter, pos, len, "UTF-8");
                        pos += len;
                        ltmp++;
                        return;

                    case 43:
                        //found sounds list object
                        byteToInt(4);
                        //consoleStrings count
                        ResourceContainer.sequences = new sequenceBuffer[byteToInt(4)];
                        ltmp = 0;
                        return;

                    case 42:
                        len = byteToInt(4);
                        ResourceContainer.sequences[ltmp] = new sequenceBuffer();
                        ResourceContainer.sequences[ltmp].png = new byte[len];
                        k = pos;
                        pos += len;
                        //System.arraycopy(chapter, k, ResourceContainer.sequences[ltmp].png, 0, len);
                        Array.Copy(chapter, k, ResourceContainer.sequences[ltmp].png, 0, len);
                        ltmp++;
                        return;

                    case 45:
                        ResourceContainer.bosses = new enemy[byteToInt(1)];
                        ltmp = 0;
                        return;

                    case 44:
                        //found bosses file
                        ResourceContainer.bosses[ltmp] = new enemy();
                        //store level information
                        ResourceContainer.bosses[ltmp].lv = byteToInt(1);
                        //found hit points, hp
                        ResourceContainer.bosses[ltmp].hp = byteToInt(1);
                        //found magic points, mp
                        ResourceContainer.bosses[ltmp].mp = byteToInt(1);
                        //found attack points, ap
                        ResourceContainer.bosses[ltmp].ap = byteToInt(1);
                        //found defence points, dp
                        ResourceContainer.bosses[ltmp].dp = byteToInt(1);
                        //found speed, s
                        ResourceContainer.bosses[ltmp].speed = byteToInt(1);
                        //found image index, ii
                        ResourceContainer.bosses[ltmp].imageIndex = byteToInt(2);
                        //found enemy name, en
                        ResourceContainer.bosses[ltmp].stringIndex = byteToInt(2);
                        //found ai type
                        ResourceContainer.bosses[ltmp].aiType = byteToInt(1);
                        //found max gold coins, gm
                        ResourceContainer.bosses[ltmp].goldCoinsMax = byteToInt(1);
                        ltmp++;
                        return;
                    default:
                        foundEOF = true;
                        return;
                }
            } catch (Exception e) {
                Logger.wr(e.Message);
                Logger.wr(e.StackTrace);
                foundEOF = true;
            }

            Logger.wr("parseObject: pos:" + pos);
        }

        public bool convertToBool(int i) {
            if (i == 0) {
                return false;
            } else {
                return true;
            }
        }

        public int byteToInt(int num) {
            switch (num) {
                case 1:
                    pos++;
                    return (int)chapter[pos - 1] & 0xFF;

                case 2:
                    pos += 2;
                    return byteShortToInt(chapter, pos - 2);

                case 4:
                    pos += 4;
                    return byteIntToInt(chapter, pos - 4);
            }
            return 0;
        }

        public int processInt(int src) {
            if (src > 60000) {
                return -1 * ((65535 - src) + 1);
            } else {
                return src;
            }
        }

        // NOTE - this function uses little-endian format
        public static int byteShortToInt(byte[] a, int pos) {
            int i = 0;
            i |= (a[(pos + 1)] & 0xFF);
            i <<= 8;
            i |= (a[pos] & 0xFF);
            return i;
        }

        // NOTE - this function uses little-endian format
        public static int byteIntToInt(byte[] a, int pos) {
            int i = 0;
            i |= (a[(pos + 3)] & 0xFF);
            i <<= 8;
            i |= (a[(pos + 2)] & 0xFF);
            i <<= 8;
            i |= (a[(pos + 1)] & 0xFF);
            i <<= 8;
            i |= (a[pos] & 0xFF);
            return i;
        }
    }
}
