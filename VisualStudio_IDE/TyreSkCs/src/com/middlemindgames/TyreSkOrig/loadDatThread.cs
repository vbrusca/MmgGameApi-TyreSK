using com.middlemindgames.dat;
using net.middlemind.MmgGameApiCs.MmgCore;
using System.Threading;

namespace com.middlemindgames.TyreSkOrig {
    /*
     * loadDatThread.java
     * Victor G. Brusca 01/16/2022
     * Created on June 1, 2005, 10:57 PM by Middlemind Games
     */
    public class loadDatThread {
        public static loadDat datLoader;
        public ThreadStart ts;
        public Thread trd;
        public CrossThreadWrite xTrdW;

        public loadDatThread() {
            xTrdW = new CrossThreadWrite();
            datLoader = new loadDat();
            loadDat.stop = false;
            ts = new ThreadStart(run);
            trd = new Thread(ts);            
        }

        public loadDatThread(ResourceContainer rc) {
            xTrdW = new CrossThreadWrite();
            datLoader = new loadDat(rc);
            loadDat.stop = false;
            ts = new ThreadStart(run);
            trd = new Thread(ts);
        }

        public void run() {
            datLoader.readWorkbookGlobals();
            if (!loadDat.stop) {
                loadDat.foundEOF = false;
                Tyre.cApp.finalizeLoad();
            }
        }
    }
}