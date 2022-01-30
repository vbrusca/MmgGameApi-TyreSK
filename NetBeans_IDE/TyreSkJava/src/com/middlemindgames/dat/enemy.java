package com.middlemindgames.dat;

/*
 * enemy.java
 * Victor G. Brusca 01/16/2022
 * Created on June 1, 2005, 10:57 PM by Middlemind Games
 */
public class enemy extends charCore {
    public int imageIndex;
    public int aiType;
    public int goldCoinsMax;
    public boolean isDead;
    public boolean isHit;
    public int hitTime;
    public int shakeCount;

    public enemy() {
        damage = 0;
        isDead = false;
        isHit = false;
        hitTime = 0;
        shakeCount = 0;
        mpUsage = 0;
        cooldown = 0;
    }
}