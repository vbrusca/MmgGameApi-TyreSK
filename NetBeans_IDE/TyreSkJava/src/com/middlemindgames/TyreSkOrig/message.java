package com.middlemindgames.TyreSkOrig;

import danger.ui.*;

/*
 * message.java
 * Victor G. Brusca 01/16/2022
 * Created on January 21, 2006, 8:00 PM
 */
public class message {
    public int color;
    public String val;

    public message() {
        color = Color.WHITE;
    }

    @Override
    public String toString() {
        return val;
    }
}