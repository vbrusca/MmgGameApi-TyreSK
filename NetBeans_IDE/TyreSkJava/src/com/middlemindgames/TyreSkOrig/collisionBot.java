package com.middlemindgames.TyreSkOrig;

import com.middlemindgames.dat.ResourceContainer;

/*
 * collisionBot.java
 * Victor G. Brusca 01/16/2022
 * Created on March 9, 2006, 6:07 PM By Middlemind Games
 */
@SuppressWarnings("CallToPrintStackTrace")
public class collisionBot {
    private int targetUpperLimit;
    private int targetLowerLimit;
    private int targetLeftLimit;
    private int targetRightLimit;
    private int sourceUpperLimit;
    private int sourceLowerLimit;
    private int sourceLeftLimit;
    private int sourceRightLimit;
    private boolean cw;
    private boolean tmpBoolean;
    public int sHeight;
    public int sWidth;
    private int i;
    private boolean isPlayer;
    private npc sNpc;
    public int collisionDiff;
    public int collisionDiffVert;
    public boolean torchMessage;
    public boolean linktColl;

    public collisionBot(int width, int height) {
        isPlayer = true;
        sHeight = height;
        sWidth = width;
        torchMessage = false;
        linktColl = false;
    }

    public collisionBot(int width, int height, npc Snpc) {
        isPlayer = false;
        sNpc = Snpc;
        sHeight = height;
        sWidth = width;
    }

    public boolean collision(int x, int y, int width, int height, int pX, int pY, int diff, int posX, int posY) {
        targetUpperLimit = (y + height - diff);
        targetLowerLimit = (y + height);
        targetLeftLimit = x;
        targetRightLimit = (x + width);
        sourceUpperLimit = (pY + (sHeight - 4));
        sourceLowerLimit = (pY + sHeight);
        sourceLeftLimit = pX;
        sourceRightLimit = (pX + sWidth);
        collisionDiff = -1;
        collisionDiffVert = -1;
        return collisionCalculation(posX, posY);
    }

    private boolean tileCollisionCalculation() {
        //determine if there is any overlapping
        //test sourceLowerLimit
        if (sourceLowerLimit <= targetLowerLimit && sourceLowerLimit >= targetUpperLimit) {
            if (sourceLeftLimit >= targetLeftLimit && sourceLeftLimit <= targetRightLimit) {
                //both sourceLowerLimit and sourceLeftLimit hit, found overlap
                if (sourceLeftLimit > targetRightLimit) {
                    collisionDiff = (sourceLeftLimit - targetRightLimit);
                } else {
                    collisionDiff = (targetRightLimit - sourceLeftLimit);
                }
                return true;
            } else if (sourceRightLimit >= targetLeftLimit && sourceRightLimit <= targetRightLimit) {
                //both sourceLowerLimit and sourceRightLimit hit, found overlap
                if (sourceRightLimit > targetLeftLimit) {
                    collisionDiff = (sourceRightLimit - targetLeftLimit);
                } else {
                    collisionDiff = (targetLeftLimit - sourceRightLimit);
                }
                return true;
            } else if (targetLeftLimit >= sourceLeftLimit && targetRightLimit <= sourceRightLimit) {
                return true;
            }
        } else if (sourceUpperLimit <= targetLowerLimit && sourceUpperLimit >= targetUpperLimit) {
            if (sourceLeftLimit >= targetLeftLimit && sourceLeftLimit <= targetRightLimit) {
                //both sourceUpperLimit and sourceLeftLimit hit, found overlap
                if (sourceLeftLimit > targetRightLimit) {
                    collisionDiff = (sourceLeftLimit - targetRightLimit);
                } else {
                    collisionDiff = (targetRightLimit - sourceLeftLimit);
                }
                return true;
            } else if (sourceRightLimit >= targetLeftLimit && sourceRightLimit <= targetRightLimit) {
                //both sourceUpperLimit and sourceRightLimit hit, found overlap
                if (sourceRightLimit > targetLeftLimit) {
                    collisionDiff = (sourceRightLimit - targetLeftLimit);
                } else {
                    collisionDiff = (targetLeftLimit - sourceRightLimit);
                }
                return true;
            } else if (targetLeftLimit >= sourceLeftLimit && targetRightLimit <= sourceRightLimit) {
                return true;
            }
        }
        return false;
    }

    public boolean tileCollision(int x, int y, int width, int height, int pX, int pY, int posX, int posY) {
        targetUpperLimit = y;
        targetLowerLimit = (y + height);
        targetLeftLimit = x;
        targetRightLimit = (x + width);
        sourceUpperLimit = (pY + (sHeight - 4));
        sourceLowerLimit = (pY + sHeight);
        sourceLeftLimit = pX;
        sourceRightLimit = (pX + sWidth);
        collisionDiff = -1;
        collisionDiffVert = -1;
        return collisionCalculation(posX, posY);
    }

    private boolean collisionCalculation(int posX, int posY) {
        //determine if there is any overlapping
        cw = tileCollisionCalculation();
        if (cw) {
            //an overlapping object was found
            if ((posY + sHeight) > targetLowerLimit) {
                //target is above us
                if (sourceUpperLimit < targetLowerLimit) {
                    collisionDiffVert = targetLowerLimit - sourceUpperLimit;
                    return true;
                } else {
                    return false;
                }
            } else {
                //target is below us
                if (sourceLowerLimit > targetUpperLimit) {
                    collisionDiffVert = sourceLowerLimit - targetUpperLimit;
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public int processWalk(int deltaX, int deltaY, int dir, int posX, int posY) {
        //FREEZE NPC WALKING
        if (MainWindow.gameState != 0) {
            return 0;
        } else {
            linktColl = false;
            tmpBoolean = false;
            switch (dir) {
                //down
                case 0:
                    if ((posY + sHeight + deltaY) < MainWindow.ROOM_HEIGHT) {
                        tmpBoolean = true;
                    } else {
                        posY = MainWindow.ROOM_HEIGHT - sHeight;
                    }
                    break;
                //right
                case 1:
                    if ((posX + sWidth + deltaX) < MainWindow.ROOM_WIDTH) {
                        tmpBoolean = true;
                    } else {
                        posX = MainWindow.ROOM_WIDTH - sWidth;
                    }
                    break;
                //up
                case 2:
                    if ((posY + deltaY) > 0) {
                        tmpBoolean = true;
                    } else {
                        posY = 0;
                    }
                    break;
                //left
                case 3:
                    if ((posX + deltaX) > 0) {
                        tmpBoolean = true;
                    } else {
                        posX = 0;
                    }
                    break;
            }

            if (tmpBoolean) {
                cw = false;
                for (i = 0; i < Tyre.cantWalk.length; i++) {
                    try {
                        cw = tileCollision(Tyre.cantWalk[i].left, Tyre.cantWalk[i].top, (Tyre.cantWalk[i].right - Tyre.cantWalk[i].left), (Tyre.cantWalk[i].bottom - Tyre.cantWalk[i].top), (posX + deltaX), (posY + deltaY), posX, posY);
                        if (cw) {
                            i = Tyre.cantWalk.length;
                            break;
                        }
                    } catch (Exception e) {
                        break;
                    }
                }

                if (!cw) {
                    if (Tyre.hasLink && isPlayer) {
                        for (i = 0; i < Tyre.linkObjects.length; i++) {
                            try {
                                if (Tyre.linkObjects[i].flagIndex == -1
                                        || (Tyre.linkObjects[i].flagDir == 0 && ResourceContainer.flags[Tyre.linkObjects[i].flagIndex] == true)
                                        || (Tyre.linkObjects[i].flagDir == 1 && ResourceContainer.flags[Tyre.linkObjects[i].flagIndex] == false)) {

                                    cw = tileCollision(Tyre.linkObjects[i].posX, Tyre.linkObjects[i].posY, Tyre.linkObjects[i].width, Tyre.linkObjects[i].height, (posX + deltaX), (posY + deltaY), posX, posY);

                                    if (cw) {
                                        linktColl = true;
                                        if (ResourceContainer.rooms[ResourceContainer.linkTables[Tyre.linkObjects[i].linkTo].roomIndex].roomType == Constants.ROOMTYPE_TUNNEL) {
                                            int inventoryIndex = Tyre.player.inInventory(Constants.ITEM_TORCH);
                                            if (inventoryIndex > -1) {
                                                //deduct one usage and shift up items
                                                Tyre.player.items[inventoryIndex][1]--;
                                                if (Tyre.player.items[inventoryIndex][1] == 0) {
                                                    Tyre.mWindow.adjustInventory(inventoryIndex);
                                                }
                                            } else {
                                                if (!torchMessage) {
                                                    MainWindow.storyType = 0;
                                                    MainWindow.lastStringUsed = Constants.CONSTR_TORCH_WARNING;
                                                    MainWindow.lastStringArrayUsed = 1;
                                                    Tyre.mWindow.formatStoryMessage(ResourceContainer.consoleStrings[Constants.CONSTR_TORCH_WARNING]);
                                                    MainWindow.gameState = 0;
                                                    MainWindow.gameSubState = 5;
                                                    torchMessage = true;
                                                }
                                                continue;
                                            }
                                        }

                                        Tyre.player.draw = false;
                                        MainWindow.gameState = 2;
                                        Tyre.currentRoomIdx = Tyre.linkObjects[i].linkTo;
                                        Tyre.player.posX = Tyre.linkObjects[i].destPosX;
                                        Tyre.player.posY = Tyre.linkObjects[i].destPosY;
                                        if (Tyre.linkObjects[i].destDir != 255) {
                                            Tyre.player.dir = Tyre.linkObjects[i].destDir;
                                        }
                                        i = Tyre.linkObjects.length;
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    for (i = 0; i < Tyre.roomObjects.length; i++) {
                        try {
                            switch (Tyre.roomObjects[i].type) {
                                case 1:
                                    //can't walk objectX
                                    if (Tyre.roomObjects[i].flagIndex == -1
                                            || (Tyre.roomObjects[i].flagDir == 0 && ResourceContainer.flags[Tyre.roomObjects[i].flagIndex] == true)
                                            || (Tyre.roomObjects[i].flagDir == 1 && ResourceContainer.flags[Tyre.roomObjects[i].flagIndex] == false)) {

                                        cw = collision(Tyre.roomObjects[i].posX, Tyre.roomObjects[i].posY, Tyre.roomObjects[i].width, Tyre.roomObjects[i].height, (posX + deltaX), (posY + deltaY), Tyre.roomObjects[i].vertOffset, posX, posY);
                                        if (cw) {
                                            i = Tyre.roomObjects.length;
                                        }
                                    }
                                    break;
                                case 3:
                                    //player
                                    if (!isPlayer) {
                                        cw = collision(Tyre.player.posX, Tyre.player.posY, Tyre.player.width, Tyre.player.height, (posX + deltaX), (posY + deltaY), 4, posX, posY);
                                        if (cw) {
                                            i = Tyre.roomObjects.length;
                                        }
                                    }
                                    break;
                                case 5:
                                    //enemy
                                    if (isPlayer) {
                                        if (Tyre.roomObjects[i].flagIndex == -1
                                                || (Tyre.roomObjects[i].flagDir == 0 && ResourceContainer.flags[Tyre.roomObjects[i].flagIndex] == true)
                                                || (Tyre.roomObjects[i].flagDir == 1 && ResourceContainer.flags[Tyre.roomObjects[i].flagIndex] == false)) {
                                            cw = collision(Tyre.roomObjects[i].posX, Tyre.roomObjects[i].posY, Tyre.roomObjects[i].width, Tyre.roomObjects[i].height, (posX + deltaX), (posY + deltaY), Tyre.roomObjects[i].height, posX, posY);
                                            if (cw) {
                                                Tyre.player.walk = false;
                                                Tyre.mWindow.startBattle(Tyre.roomObjects[i].battleIndex, ResourceContainer.battles[Tyre.roomObjects[i].battleIndex].flagIndex, ResourceContainer.battles[Tyre.roomObjects[i].battleIndex].flagIndex1, ResourceContainer.battles[Tyre.roomObjects[i].battleIndex].flagIndex2, ResourceContainer.battles[Tyre.roomObjects[i].battleIndex].searchId);
                                                i = Tyre.roomObjects.length;
                                            }
                                        }
                                    }
                                    break;
                                case 6:
                                    //npc
                                    if (isPlayer) {
                                        if (Tyre.roomObjects[i].flagIndex == -1
                                                || (Tyre.roomObjects[i].flagDir == 0 && ResourceContainer.flags[Tyre.roomObjects[i].flagIndex] == true)
                                                || (Tyre.roomObjects[i].flagDir == 1 && ResourceContainer.flags[Tyre.roomObjects[i].flagIndex] == false)) {
                                            cw = collision(Tyre.roomChars[Tyre.roomObjects[i].npcIndex].posX, Tyre.roomChars[Tyre.roomObjects[i].npcIndex].posY, Tyre.roomChars[Tyre.roomObjects[i].npcIndex].width, Tyre.roomChars[Tyre.roomObjects[i].npcIndex].height, (posX + deltaX), (posY + deltaY), 4, posX, posY);
                                            if (cw) {
                                                i = Tyre.roomObjects.length;
                                            }
                                        }
                                    } else {
                                        if (!Tyre.roomChars[Tyre.roomObjects[i].npcIndex].equals(sNpc)) {
                                            cw = collision(Tyre.roomChars[Tyre.roomObjects[i].npcIndex].posX, Tyre.roomChars[Tyre.roomObjects[i].npcIndex].posY, Tyre.roomChars[Tyre.roomObjects[i].npcIndex].width, Tyre.roomChars[Tyre.roomObjects[i].npcIndex].height, (posX + deltaX), (posY + deltaY), Tyre.player.speed, posX, posY);
                                            if (cw) {
                                                i = Tyre.roomObjects.length;
                                            }
                                        }
                                    }
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }

                if (cw) {
                    return 0;
                } else {
                    if (!linktColl && torchMessage) {
                        torchMessage = false;
                    }
                    return 1;
                }
            } else {
                return 2;
            }
        }
    }
}