using danger.ui;
using System;

namespace com.middlemindgames.TyreSkOrig {
    /*
     * npc.java
     * Victor G. Brusca 01/16/2022
     * Created on June 1, 2005, 10:57 PM by Middlemind Games
     */
    public class npc {
        public Random rand;
        public int stringIndex;
        public int fc;
        public int fr;
        public int fl;
        public int bc;
        public int br;
        public int bl;
        public int rc;
        public int rr;
        public int rl;
        public int lc;
        public int lr;
        public int ll;
        public int posX;
        public int posY;

        //animation variables
        public int dir;
        public bool canWalk;
        public int walkStart;
        public int walkFreeze;
        public int toggle = 0;
        public int speed;
        public bool walk = false;
        public bool cw;
        public int height;
        public int width;
        public int npcType;
        public bool tmpBoolean;

        public int i;
        public int j;
        public int k;
        public int targetUpperLimit;
        public int targetLowerLimit;
        public int targetLeftLimit;
        public int targetRightLimit;
        public int sourceUpperLimit;
        public int sourceLowerLimit;
        public int sourceLeftLimit;
        public int sourceRightLimit;
        public int changeDirCount;
        public int changeDirMatch;
        private collisionBot cbot;

        public npc() {
            cbot = null;
            walkFreeze = 14;
            speed = 2;
            canWalk = false;
            walk = false;
        }

        public void setCanWalk(int cw, int Height, int Width) {
            height = Height;
            width = Width;
            if (cw == 1) {
                changeDirMatch = MainWindow.rand.Next(6);
                cbot = new collisionBot(width, height, this);
                canWalk = true;
            } else {
                canWalk = false;
            }
        }

        public void setCanWalk(int cw) {
            if (cw == 1) {
                canWalk = true;
            } else {
                canWalk = false;
            }
        }

        private void finishWalk(int deltaX, int deltaY) {
            posY += deltaY;
            posX += deltaX;
            if (deltaY != 0) {
                MainWindow.resort = true;
            }
        }

        public void setWalk(bool w) {
            if (!w) {
                walkFreeze = 14;
            }
            walk = w;
        }

        public void paint(Pen inPen) {
            paint(inPen, 0, 0);
        }

        public void paint(Pen inPen, int x_off, int y_off) {
            if (MainWindow.count >= (walkStart + walkFreeze)) {
                toggle++;
                if (toggle >= 4) {
                    toggle = 0;
                }
                walkStart = MainWindow.count;

                if (canWalk && MainWindow.gameSubState == 0) {
                    if (changeDirCount == changeDirMatch) {
                        dir = MainWindow.rand.Next(4);
                        changeDirCount = 0;
                        changeDirMatch = MainWindow.rand.Next(6);
                        if (walk) {
                            walk = false;
                            walkFreeze = 14;
                        } else {
                            walk = true;
                            walkFreeze = 4;
                        }
                    } else {
                        changeDirCount++;
                    }
                }
            }

            switch (dir) {
                //down
                case 0:
                    if (canWalk && MainWindow.gameSubState == 0 && walk) {
                        if (cbot.processWalk(0, speed, dir, posX, posY) == 1) {
                            finishWalk(0, speed);
                        }
                    }
                    switch (toggle) {
                        case 0:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[fr]);
                            break;
                        case 1:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[fc]);
                            break;
                        case 2:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[fl]);
                            break;
                        case 3:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[fc]);
                            break;
                    }
                    break;
                //right
                case 1:
                    if (canWalk && MainWindow.gameSubState == 0 && walk) {
                        if (cbot.processWalk(speed, 0, dir, posX, posY) == 1) {
                            finishWalk(speed, 0);
                        }
                    }
                    switch (toggle) {
                        case 0:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[rr]);
                            break;
                        case 1:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[rc]);
                            break;
                        case 2:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[rl]);
                            break;
                        case 3:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[rc]);
                            break;
                    }
                    break;
                //up
                case 2:
                    if (canWalk && MainWindow.gameSubState == 0 && walk) {
                        if (cbot.processWalk(0, -speed, dir, posX, posY) == 1) {
                            finishWalk(0, -speed);
                        }
                    }
                    switch (toggle) {
                        case 0:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[br]);
                            break;
                        case 1:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[bc]);
                            break;
                        case 2:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[bl]);
                            break;
                        case 3:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[bc]);
                            break;
                    }
                    break;
                //left
                case 3:
                    if (canWalk && MainWindow.gameSubState == 0 && walk) {
                        if (cbot.processWalk(-speed, 0, dir, posX, posY) == 1) {
                            finishWalk(-speed, 0);
                        }
                    }
                    switch (toggle) {
                        case 0:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[lr]);
                            break;
                        case 1:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[lc]);
                            break;
                        case 2:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[ll]);
                            break;
                        case 3:
                            inPen.drawBitmap(posX + x_off, posY + y_off, Tyre.bitmaps[lc]);
                            break;
                    }
                    break;
            }
        }
    }
}