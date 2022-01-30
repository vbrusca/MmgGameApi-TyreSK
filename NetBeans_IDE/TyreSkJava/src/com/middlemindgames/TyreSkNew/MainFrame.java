package com.middlemindgames.TyreSkNew;

import com.middlemindgames.TyreSkOrig.Tyre;
import danger.util.Logger;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import net.middlemind.MmgGameApiJava.MmgBase.MmgHelper;
import net.middlemind.MmgGameApiJava.MmgCore.RunFrameRate;

/**
 * An instance of the MainFrame class that extends the MmgCore MainFrame class.
 * Created by Middlemind Games 02/19/2020
 * 
 * @author Victor G. Brusca, Middlemind Games
 */
public class MainFrame extends net.middlemind.MmgGameApiJava.MmgCore.MainFrame {
    /**
     * The default constructor of the MainFrame class.
     * 
     * @param WinWidth      The total window width.
     * @param WinHeight     The total window height.
     * @param PanWidth      The total panel width.
     * @param PanHeight     The total panel height.
     * @param GameWidth     The target game width.
     * @param GameHeight    The target game height.
     */
    public MainFrame(int WinWidth, int WinHeight, int PanWidth, int PanHeight, int GameWidth, int GameHeight) {
        super(WinWidth, WinHeight, PanWidth, PanHeight, GameWidth, GameHeight);
    }
    
    /**
     * A simplified constructor for the MainFrame class.
     * 
     * @param WinWidth      The total window width.
     * @param WinHeight     The total window height.
     */
    public MainFrame(int WinWidth, int WinHeight) {
        super(WinWidth, WinHeight);
    }
    
    @Override
    public void InitComponents() {
        super.InitComponents();
        addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            @SuppressWarnings("CallToPrintStackTrace")
            public void windowClosing(WindowEvent e) {
                try {
                    Logger.wr("TyreSK: MainFrame: WindowClosing");
                    Tyre tyre = ((com.middlemindgames.TyreSkNew.GamePanel)GetGamePanel()).screenMain.tyre;
                    if(tyre != null) {
                        Logger.wr("TyreSK: MainFrame: tyre.suspend called");
                        tyre.suspend();
                    }
                } catch (Exception ex) {
                    MmgHelper.wrErr(ex);
                }
                dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });        
    }
}