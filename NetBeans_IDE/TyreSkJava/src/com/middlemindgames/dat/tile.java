package com.middlemindgames.dat;

/*
 * tile.java
 * Victor G. Brusca 01/16/2022
 * Created on June 1, 2005, 10:57 PM by Middlemind Games
 */
public class tile {
    public int imageIndex;
    public boolean canWalk;

    public tile() {
    }

    public final void setCanWalk(int CW) {
        if (CW == 0) {
            canWalk = false;
        } else {
            canWalk = true;
        }
    }
}