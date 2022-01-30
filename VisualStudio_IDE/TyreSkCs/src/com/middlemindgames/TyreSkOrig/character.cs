using com.middlemindgames.dat;
using danger.system;
using danger.ui;
using System;

namespace com.middlemindgames.TyreSkOrig {
    /*
     * character.java
     * Victor G. Brusca 01/16/2022
     * Created on June 1, 2005, 10:57 PM by Middlemind Games
     */
    public class character : charCore {
        public Random rand;
        public int enm;
        public int ex;
        public int charType;

        //battle stats
        public int totalMisses;
        public int battleMisses;
        public int totalDodges;
        public int battleDodges;
        public int totalHitsTaken;
        public int battleHitsTaken;
        public int totalHitsGiven;
        public int battleHitsGiven;
        public int totalGlancingBlowGiven;
        public int battleGlancingBlowGiven;
        public int totalGlancingBlowTaken;
        public int battleGlancingBlowTaken;
        public int totalSuperAttackGiven;
        public int battleSuperAttackGiven;
        public int totalSuperAttackTaken;
        public int battleSuperAttackTaken;
        public int totalRun;
        public int battleRun;
        public int totalKills;
        public int battleKills;
        public int totalItems;
        public int battleItems;
        public int attackBonus;
        public int defenseBonus;
        public int speedBonus;
        public int totalDeaths;

        public int goldCoins;
        public int[][] items;
        public int walkFreeze;
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
        public int dir;
        public int walkStart;
        public bool walk;
        public int i;
        public int j;
        public int k;
        public int r;

        public int targetUpperLimit;
        public int targetLowerLimit;
        public int targetLeftLimit;
        public int targetRightLimit;
        public int sourceUpperLimit;
        public int sourceLowerLimit;
        public int sourceLeftLimit;
        public int sourceRightLimit;
        public int toggle = 0;
        public int determineAttackCount = 0;
        public bool draw = true;
        public collisionBot cbot;
        public int width;
        public int height;

        //cooldown timer between player attacks
        public int PLAYERWAIT_TIME;

        //properties set by weapon type
        public bool canSelectAny = false;
        public bool canSelectAll = false;

        //battle affliction protections
        public bool antisleep = false;
        public bool antistun = false;
        public bool antipoison = false;

        //track the orb separately since herbal antisleep overwrites the variable
        public bool antisleeporb = false;
        public int weapon;
        public int armor;
        public int tgold;

        public character() {
            dir = 0;
            posX = 80;
            posY = 72;
            mpUsage = 0;
            totalHitsTaken = 0;
            battleHitsTaken = 0;
            totalHitsGiven = 0;
            battleHitsGiven = 0;
            totalGlancingBlowGiven = 0;
            battleGlancingBlowGiven = 0;
            totalGlancingBlowTaken = 0;
            battleGlancingBlowTaken = 0;
            totalSuperAttackGiven = 0;
            battleSuperAttackGiven = 0;
            totalSuperAttackTaken = 0;
            battleSuperAttackTaken = 0;
            totalMisses = 0;
            battleMisses = 0;
            battleRun = 0;
            totalRun = 0;
            totalKills = 0;
            battleKills = 0;
            damage = 0;
            attackBonus = 0;
            defenseBonus = 0;
            speedBonus = 0;
            goldCoins = 0;

            //which character is choosen
            charType = 0;
            weapon = -2;
            armor = -2;

            items = MainWindow.NewMdIntArray(Constants.ITEM_INVENTORY_SIZE, 2); // new int[Constants.ITEM_INVENTORY_SIZE][2];
            for (i = 0; i < Constants.ITEM_INVENTORY_SIZE; i++) {
                items[i][0] = -2;
                items[i][1] = -2;
            }

            rand = new Random();
            walk = false;
            draw = true;
            width = Tyre.bitmaps[fc].getWidth();
            height = Tyre.bitmaps[fc].getHeight();
            cbot = new collisionBot(width, height);
            cooldown = 0;
        }

        public void loadCharData(int index) {
            charType = index;
            ex = ResourceContainer.players[index].ex;
            lv = ResourceContainer.players[index].lv;
            hp = ResourceContainer.players[index].hp;
            hp = 12;
            ap = ResourceContainer.players[index].ap;
            dp = ResourceContainer.players[index].dp;
            mp = ResourceContainer.players[index].mp;
            speed = ResourceContainer.players[index].speed;
            stringIndex = Constants.PLAYER_NAME_INDEX;

            fc = ResourceContainer.players[index].fc;
            fr = ResourceContainer.players[index].fr;
            fl = ResourceContainer.players[index].fl;

            bc = ResourceContainer.players[index].bc;
            br = ResourceContainer.players[index].br;
            bl = ResourceContainer.players[index].bl;

            rc = ResourceContainer.players[index].rc;
            rr = ResourceContainer.players[index].rr;
            rl = ResourceContainer.players[index].rl;

            lc = ResourceContainer.players[index].lc;
            lr = ResourceContainer.players[index].lr;
            ll = ResourceContainer.players[index].ll;

            width = Tyre.bitmaps[fc].getWidth();
            height = Tyre.bitmaps[fc].getHeight();
            cbot = new collisionBot(width, height);
        }

        private void finalizeWalk(int deltaX, int deltaY) {
            posY += deltaY;
            posX += deltaX;
            if (deltaY != 0) {
                MainWindow.resort = true;
            }
            determineAttack();
        }

        private void processTransition() {
            switch (dir) {
                case 0:
                    MainWindow.transBottom++;
                    if (ResourceContainer.linkTables[Tyre.currentRoomIdx].linkBottom != -1 && MainWindow.gameState == 0) {
                        Tyre.currentRoomIdx = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkBottom;
                        Tyre.mWindow.changeState(6);
                        MainWindow.transBottom = 0;
                    } else {
                        MainWindow.transBottom = 0;
                        //sound buzzer
                        if (MainWindow.hasVibration) {
                            danger.audio.AudioManager.vibrate(100);
                        }
                        if (MainWindow.hasSound) {
                            Tyre.mWindow.PlaySystemSound(Meta.BEEP_COMMAND_REJECTED);
                        }
                    }
                    break;

                case 1:
                    MainWindow.transRight++;
                    if (ResourceContainer.linkTables[Tyre.currentRoomIdx].linkRight != -1 && MainWindow.gameState == 0) {
                        Tyre.currentRoomIdx = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkRight;
                        Tyre.mWindow.changeState(5);
                        MainWindow.transRight = 0;
                    } else {
                        MainWindow.transRight = 0;
                        //sound buzzer
                        if (MainWindow.hasVibration) {
                            danger.audio.AudioManager.vibrate(100);
                        }
                    }
                    break;

                case 2:
                    MainWindow.transTop++;
                    if (ResourceContainer.linkTables[Tyre.currentRoomIdx].linkTop != -1 && MainWindow.gameState == 0) {
                        Tyre.currentRoomIdx = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkTop;
                        Tyre.mWindow.changeState(4);
                        MainWindow.transTop = 0;
                    } else {
                        MainWindow.transTop = 0;
                        //sound buzzer
                        if (MainWindow.hasVibration) {
                            danger.audio.AudioManager.vibrate(100);
                        }
                    }
                    break;

                case 3:
                    MainWindow.transLeft++;
                    if (ResourceContainer.linkTables[Tyre.currentRoomIdx].linkLeft != -1 && MainWindow.gameState == 0) {
                        Tyre.currentRoomIdx = ResourceContainer.linkTables[Tyre.currentRoomIdx].linkLeft;
                        Tyre.mWindow.changeState(7);
                        MainWindow.transLeft = 0;
                    } else {
                        MainWindow.transLeft = 0;
                        //sound buzzer
                        if (MainWindow.hasVibration) {
                            danger.audio.AudioManager.vibrate(100);
                        }
                    }
                    break;
            }
        }

        public void paint(Pen inPen) {
            paint(inPen, 0, 0);
        }

        public void paint(Pen inPen, int x_off, int y_off) {
            if (!draw) {
                return;
            }

            if (walk) {
                walkFreeze = 4;
            } else {
                walkFreeze = 14;
            }

            if (MainWindow.count >= (walkStart + walkFreeze)) {
                toggle++;
                if (toggle >= 4) {
                    toggle = 0;
                }
                walkStart = MainWindow.count;
            }

            switch (dir) {
                //down
                case 0:
                    if (draw && walk) {
                        r = cbot.processWalk(0, speed, dir, posX, posY);
                        if (!draw) {
                            return;
                        }
                        if (r == 1) {
                            finalizeWalk(0, speed);
                        } else if (r == 2) {
                            processTransition();
                        } else {
                            if (cbot.collisionDiffVert > 0 && cbot.collisionDiffVert <= speed) {
                                finalizeWalk(0, (speed - 1 - cbot.collisionDiffVert));
                            }
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
                    if (draw && walk) {
                        r = cbot.processWalk(speed, 0, dir, posX, posY);
                        if (!draw) {
                            return;
                        }
                        if (r == 1) {
                            finalizeWalk(speed, 0);
                        } else if (r == 2) {
                            processTransition();
                        } else {
                            if (cbot.collisionDiff >= 0 && cbot.collisionDiff < speed) {
                                finalizeWalk((speed - 1 - cbot.collisionDiff), 0);
                            }
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
                    if (draw && walk) {
                        r = cbot.processWalk(0, -speed, dir, posX, posY);
                        if (!draw) {
                            return;
                        }
                        if (r == 1) {
                            finalizeWalk(0, -speed);
                        } else if (r == 2) {
                            processTransition();
                        } else {
                            if (cbot.collisionDiffVert > 0 && cbot.collisionDiffVert <= speed) {
                                finalizeWalk(0, -(speed - 1 - cbot.collisionDiffVert));
                            }
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
                    if (draw && walk) {
                        r = cbot.processWalk(-speed, 0, dir, posX, posY);
                        if (!draw) {
                            return;
                        }
                        if (r == 1) {
                            finalizeWalk(-speed, 0);
                        } else if (r == 2) {
                            processTransition();
                        } else {
                            if (cbot.collisionDiff >= 0 && cbot.collisionDiff < speed) {
                                finalizeWalk(-(speed - 1 - cbot.collisionDiff), 0);
                            }
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

        public void determineAttack() {
            if (determineAttackCount > 5) {
                determineAttackCount = 0;
                if (ResourceContainer.linkTables[Tyre.currentRoomIdx].hasEnemies) {
                    enm = rand.Next(50) + 1;
                    i = ResourceContainer.linkTables[Tyre.currentRoomIdx].frequency;
                    if (enm % i == 0) {
                        if (MainWindow.hasVibration) {
                            danger.audio.AudioManager.vibrate(150);
                        }

                        //prepare enemies and enemy names
                        enm = rand.Next(15);
                        enm++;
                        if (enm % 5 == 0) {
                            startBattle(2);
                        } else {
                            startBattle(1);
                        }
                    }
                }
            } else {
                determineAttackCount++;
            }
        }

        public void startBattle(int enm) {
            //bug fix, clear boss battle status before each battle
            MainWindow.currentBossId = -1;
            MainWindow.currentBossBattleId = -1;
            MainWindow.currentBossPos = -1;

            MainWindow.enemies = new enemy[enm];
            MainWindow.gold = 0;
            MainWindow.battleSong = Constants.MUSIC_BATTLE;
            MainWindow.numEnemies = enm;
            for (i = 0; i < enm; i++) {
                k = rand.Next(ResourceContainer.linkTables[Tyre.currentRoomIdx].enemyLevel);
                j = rand.Next(ResourceContainer.enemies[k].Length);
                MainWindow.enemies[i] = Tyre.cApp.copyEnemy(ResourceContainer.enemies[k][j]);
                MainWindow.enemy_index[i][0] = k;
                MainWindow.enemy_index[i][1] = j;
                tgold = 0;

                if (MainWindow.enemies[i].goldCoinsMax != 0) {
                    tgold = rand.Next(MainWindow.enemies[i].goldCoinsMax);
                    tgold++;
                    if (tgold < 3) {
                        MainWindow.gold += 3;
                    } else {
                        MainWindow.gold += tgold;
                    }
                }
            }
            Tyre.mWindow.prepBattle(enm);
        }

        public void resetBattleStats() {
            battleMisses = 0;
            battleDodges = 0;
            battleGlancingBlowGiven = 0;
            battleGlancingBlowTaken = 0;
            battleHitsTaken = 0;
            battleHitsGiven = 0;
            battleSuperAttackGiven = 0;
            battleSuperAttackTaken = 0;
            battleKills = 0;
            battleItems = 0;
            battleRun = 0;
            attackBonus = 0;
            defenseBonus = 0;
            speedBonus = 0;
            MainWindow.gold = 0;
            MainWindow.isBoss = false;
            MainWindow.isEntranced = false;
            MainWindow.isStunned = false;
        }

        //move stats and clean up player bonuses
        public void moveBattleStats() {
            totalMisses += battleMisses;
            totalDodges += battleDodges;
            totalGlancingBlowGiven += battleGlancingBlowGiven;
            totalGlancingBlowTaken += battleGlancingBlowTaken;
            totalHitsTaken += battleHitsTaken;
            totalHitsGiven += battleHitsGiven;
            totalSuperAttackTaken += battleSuperAttackTaken;
            totalSuperAttackGiven += battleSuperAttackGiven;
            totalKills += battleKills;
            totalItems += battleItems;
            totalRun += battleRun;
            resetBattleStats();
        }

        public int inInventory(int type) {
            for (i = 0; i < items.Length; i++) {
                if (items[i][0] == type) {
                    return i;
                }
            }
            return -1;
        }

        public void equip_weapon(int new_ap, int index) {
            //player better sword in possession
            if (ap > new_ap) {
                return;
            }

            ap = new_ap;
            weapon = index;
            switch (ap) {
                //broad
                case 2:
                //magic
                case 3:
                    canSelectAll = true;
                    break;
                //wooden
                case 0:
                //long
                case 1:
                default:
                    canSelectAll = false;
                    break;
            }
        }

        public void equip_armor(int new_dp, int index) {
            //player better sword in possession
            if (dp > new_dp) {
                return;
            }
            dp = new_dp;
            armor = index;
        }

        //stores player's base recharge speed as active speed (when equipping new weapon or curing poison)
        public void setRechargeSpeed() {
            PLAYERWAIT_TIME = getRechargeSpeed();
        }

        //until this is data driven this function will handle the base recharge speed
        public int getRechargeSpeed() {
            switch (ap) {
                case 0:
                    return (int)(MainWindow.DEF_PLAYERWAIT_TIME * Tyre.speedMultiplier);
                case 1:
                    return (int)(45 * Tyre.speedMultiplier);
                case 2:
                    return (int)(38 * Tyre.speedMultiplier);
                case 3:
                    return (int)(34 * Tyre.speedMultiplier);
            }
            return (int)(MainWindow.DEF_PLAYERWAIT_TIME * Tyre.speedMultiplier);
        }
    }
}