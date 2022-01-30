package com.middlemindgames.TyreSkOrig;

import com.middlemindgames.dat.*;

/*
 * loadDatThread.java
 * Victor G. Brusca 01/16/2022
 * Created on June 1, 2005, 10:57 PM by Middlemind Games
 */
public class loadDatThread extends Thread {
    public static loadDat datLoader;

    public loadDatThread() {
        datLoader = new loadDat();
        loadDat.stop = false;
    }

    public loadDatThread(ResourceContainer rc) {
        datLoader = new loadDat(rc);
        loadDat.stop = false;
    }

    @Override
    public void run() {
        datLoader.readWorkbookGlobals();
        if (!loadDat.stop) {
            loadDat.foundEOF = false;
            Tyre.cApp.finalizeLoad();
        }
    }
}