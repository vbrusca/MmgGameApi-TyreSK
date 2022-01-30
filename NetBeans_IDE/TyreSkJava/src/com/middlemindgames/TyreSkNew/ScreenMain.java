package com.middlemindgames.TyreSkNew;

import com.middlemindgames.TyreSkOrig.Tyre;
import danger.app.Event;
import danger.ui.Pen;
import danger.util.Logger;
import net.middlemind.MmgGameApiJava.MmgBase.MmgBmp;
import net.middlemind.MmgGameApiJava.MmgCore.GameSettings;
import net.middlemind.MmgGameApiJava.MmgCore.GamePanel.GameStates;
import net.middlemind.MmgGameApiJava.MmgBase.MmgDrawableBmpSet;
import net.middlemind.MmgGameApiJava.MmgBase.MmgHelper;
import net.middlemind.MmgGameApiJava.MmgBase.MmgPen;
import net.middlemind.MmgGameApiJava.MmgBase.MmgScreenData;

/**
 * A game screen object, ScreenGame, that extends the MmgGameScreen base
 * class. This class is for testing new UI widgets, etc.
 * Created by Middlemind Games 03/22/2020
 *
 * @author Victor G. Brusca
 */
public class ScreenMain extends net.middlemind.MmgGameApiJava.MmgCore.Screen {
    /**
     * A private variable used in drawing routine methods.
     */
    private boolean lret;
    
    public Tyre tyre;
    public Pen pen;
    public MmgDrawableBmpSet sk;
    public MmgBmp tmp;
    public boolean dpadPress = false;
    public boolean keyPress = false;
    public boolean bPress = false;
    public boolean aPress = false;    
    
    /**
     * Constructor, sets the game state associated with this screen, and sets
     * the owner GamePanel instance.
     *
     * @param State The game state of this game screen.
     * @param Owner The owner of this game screen.
     */
    @SuppressWarnings({"LeakingThisInConstructor", "CallToPrintStackTrace"})
    public ScreenMain(GameStates State, GamePanel Owner) {
        super(State, Owner);
        isDirty = false;
        pause = false;
        ready = false;
        state = State;
        owner = Owner;
    }

    /**
     * Loads all the resources needed to display this game screen.
     */
    @SuppressWarnings("UnusedAssignment")
    @Override
    public void LoadResources() {
        pause = true;
        SetHeight(MmgScreenData.GetGameHeight());
        SetWidth(MmgScreenData.GetGameWidth());
        SetPosition(MmgScreenData.GetPosition());

        classConfig = MmgHelper.ReadClassConfigFile(GameSettings.CLASS_CONFIG_DIR + GameSettings.NAME + "/screen_main.txt");
                
        sk = MmgHelper.CreateDrawableBmpSet(GetWidth(), GetHeight(), true);
        //tyre = new Tyre();
        //tyre.resume();        
        pen = new Pen();
        
        isDirty = true;
        ready = true;
        pause = false;
    }

    /**
     * A method to handle A click events from the MainFrame.
     * 
     * @return      A boolean indicating if the click event was handled by this Screen.
     */
    @Override
    public boolean ProcessAClick(int src) {
        return true;
    }

    /**
     * 
     * @param src
     * @return 
     */
    @Override
    public boolean ProcessAPress(int src) {
        if (aPress == false) {
            aPress = true;
            return Tyre.mWindow.eventWidgetDown(Event.DEVICE_WHEEL_BUTTON, null);
        }
        return true;
    }
    
    /**
     * 
     * @param src
     * @return 
     */
    @Override
    public boolean ProcessARelease(int src) {
        if (aPress == true) {
            aPress = false;
            return Tyre.mWindow.eventWidgetUp(Event.DEVICE_WHEEL_BUTTON, null);
        }
        return true;
    }

    @Override
    public boolean ProcessKeyClick(char c, int code) {
        return true;    
    }
    
    /**
     * 
     * @param c
     * @param code
     * @return 
     */
    @Override
    public boolean ProcessKeyPress(char c, int code) {
        Logger.wr("ProcessKeyPress: Found char:" + c + " with code:" + code);
        if (keyPress == false) {
            if (code == 27) {
                keyPress = true;
                return Tyre.mWindow.eventWidgetDown(Event.DEVICE_BUTTON_CANCEL, null);
            } else if (c == '\n' || c == '\r' || c == ' ') {
                keyPress = true;
                return Tyre.mWindow.eventWidgetDown(Event.DEVICE_WHEEL_BUTTON, null);
            } else if (c == '\t' || c == 'm' || c == 'M') {
                keyPress = true;
                return Tyre.mWindow.eventWidgetDown(Event.DEVICE_BUTTON_MENU, null);
            } else if (c == 'j' || c == 'J') {
                keyPress = true;
                return Tyre.mWindow.eventWidgetDown(Event.DEVICE_BUTTON_JUMP, null);
            }
        }
        return true;
    }
    
    @Override
    public boolean ProcessKeyRelease(char c, int code) {
        Logger.wr("ProcessKeyRelease: Found char:" + c + " with code:" + code);
        if (keyPress == true) {
            if (code == 27) {
                keyPress = false;
                return Tyre.mWindow.eventWidgetUp(Event.DEVICE_BUTTON_CANCEL, null);
            } else if (c == '\n' || c == '\r' || c == ' ') {
                keyPress = false;
                return Tyre.mWindow.eventWidgetUp(Event.DEVICE_WHEEL_BUTTON, null);
            } else if (c == '\t' || c == 'm' || c == 'M') {
                keyPress = false;
                return Tyre.mWindow.eventWidgetUp(Event.DEVICE_BUTTON_MENU, null);
            } else if (c == 'j' || c == 'J') {
                keyPress = false;
                return Tyre.mWindow.eventWidgetUp(Event.DEVICE_BUTTON_JUMP, null);
            } else if (c == 'p' || c == 'P') {
                keyPress = false;
                tyre.dumpDB();
                return true;
            }
        }
        return true;
    }    
    
    /**
     * 
     * @param src
     * @return 
     */    
    @Override
    public boolean ProcessBPress(int src) {
        if (bPress == false) {
            bPress = true;
            return Tyre.mWindow.eventWidgetDown(Event.DEVICE_BUTTON_BACK, null);
        }
        return true;
    }    
    
    /**
     * 
     * @param src
     * @return 
     */
    @Override
    public boolean ProcessBRelease(int src) {
        if (bPress == true) {
            bPress = false;
            return Tyre.mWindow.eventWidgetUp(Event.DEVICE_BUTTON_BACK, null);
        }
        return true;
    }
    
    /**
     * A method to handle dpad release events from the MainFrame.
     * 
     * @param dir       A dpad code indicating if the UP, DOWN, LEFT, RIGHT direction was released.
     * 
     * @return          A boolean indicating if the Screen handled the dpad release event.
     */
    @Override
    public boolean ProcessDpadRelease(int dir) {        
        if (dpadPress == true) {
            if (dir == GameSettings.UP_KEYBOARD || dir == GameSettings.UP_GAMEPAD_1) {
                dpadPress = false;
                return Tyre.mWindow.eventWidgetUp(Event.DEVICE_ARROW_UP, null);
            } else if (dir == GameSettings.DOWN_KEYBOARD || dir == GameSettings.DOWN_GAMEPAD_1) {
                dpadPress = false;
                return Tyre.mWindow.eventWidgetUp(Event.DEVICE_ARROW_DOWN, null);
            } else if (dir == GameSettings.LEFT_KEYBOARD || dir == GameSettings.LEFT_GAMEPAD_1) {
                dpadPress = false;
                return Tyre.mWindow.eventWidgetUp(Event.DEVICE_ARROW_LEFT, null);
            } else if (dir == GameSettings.RIGHT_KEYBOARD || dir == GameSettings.RIGHT_GAMEPAD_1) {
                dpadPress = false;
                return Tyre.mWindow.eventWidgetUp(Event.DEVICE_ARROW_RIGHT, null);
            }
        }
        return true;        
    }
    
    /**
     * A method to handle dpad press events from the MainFrame.
     * 
     * @param dir       A dpad code indicating if the UP, DOWN, LEFT, RIGHT direction was pressed.
     * 
     * @return          A boolean indicating if the Screen handled the dpad press event.
     */
    @Override
    public boolean ProcessDpadPress(int dir) {        
        if (dpadPress == false) {
            if (dir == GameSettings.UP_KEYBOARD || dir == GameSettings.UP_GAMEPAD_1) {
                dpadPress = true;
                return Tyre.mWindow.eventWidgetDown(Event.DEVICE_ARROW_UP, null);
            } else if (dir == GameSettings.DOWN_KEYBOARD || dir == GameSettings.DOWN_GAMEPAD_1) {
                dpadPress = true;
                return Tyre.mWindow.eventWidgetDown(Event.DEVICE_ARROW_DOWN, null);
            } else if (dir == GameSettings.LEFT_KEYBOARD || dir == GameSettings.LEFT_GAMEPAD_1) {
                dpadPress = true;
                return Tyre.mWindow.eventWidgetDown(Event.DEVICE_ARROW_LEFT, null);
            } else if (dir == GameSettings.RIGHT_KEYBOARD || dir == GameSettings.RIGHT_GAMEPAD_1) {
                dpadPress = true;
                return Tyre.mWindow.eventWidgetDown(Event.DEVICE_ARROW_RIGHT, null);
            }
        }
        return true;        
    }    
    
    /**
     * Unloads resources needed to display this game screen.
     */
    @Override
    public void UnloadResources() {
        isDirty = false;
        pause = true;
        
        SetIsVisible(false);
        SetBackground(null);
        SetMenu(null);
        ClearObjs();

        classConfig = null;        
        if (tyre != null) {
            tyre.suspend();
        }
        tyre = null;
        sk = null;
        
        super.UnloadResources();
        ready = false;
    }

    /**
     * Returns the game state of this game screen.
     *
     * @return      The game state of this game screen.
     */
    @Override
    public GameStates GetGameState() {
        return state;
    }

    /**
     * Returns the dirty state of the Screen.
     * If a Screen is dirty it will be redrawn via the DrawScreen method on the next update call.
     * 
     * @return      A boolean indicating the state of the class' dirty flag.
     */
    @Override
    public boolean GetIsDirty() {
        return isDirty;
    }

    /**
     * Sets the state of the Screen's dirty flag.
     * 
     * @param b     A boolean used to set the Screen class' dirty flag.
     */
    @Override
    public void SetIsDirty(boolean b) {
        isDirty = b;
    }

    /**
     * A method that sets the Screen's dirty flag to true forcing a redraw on the next update call.
     */
    public void MakeDirty() {
        isDirty = true;
    }

    /**
     * The main drawing routine.
     *
     * @param p An MmgPen object to use for drawing this game screen.
     */
    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public void MmgDraw(MmgPen p) {
        if (pause == false && GetIsVisible() == true) {
            /*
            pen.mmgPen = sk.p;
            Tyre.mWindow.paint(pen);
            p.DrawBmp(sk.img, GetX(), GetY());
            */
            try {
                if (tyre != null) {
                    p.DrawBmp(sk.img, GetX(), GetY());

                    //for debugging
                    //if (tmp != null) {
                        //p.DrawBmp(tmp, 10, 10);
                    //}
                } else {
                    tyre = new Tyre();
                    tyre.resume();
                    Tyre.datLoader.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The main update routine responsible for calling DrawnScreen when game updates are processed.
     * 
     * @param updateTick            A value indicating the number of the update call.
     * 
     * @param currentTimeMs         The current time in ms of the update call.
     * 
     * @param msSinceLastFrame      The number of ms between this update call and the previous update call.
     * 
     * @return      A boolean indicating if the update was processed.
     */
    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public boolean MmgUpdate(int updateTick, long currentTimeMs, long msSinceLastFrame) {
        lret = false;

        if (pause == false && isVisible == true) {
            if (super.MmgUpdate(updateTick, currentTimeMs, msSinceLastFrame) == true) {
                lret = true;
            }

            try {
                /*
                if (isDirty == true) {
                    lret = true;
                    DrawScreen();
                }
                */

                if (tyre != null) {
                    /*
                    Bitmap b = null;
                    if (Tyre.consoleBitmaps != null && Tyre.currentRoom != null) {
                        b = Tyre.currentRoom;
                        tmp = b.mmgBmp;
                    }
                    */
                    
                    //sk = MmgHelper.CreateDrawableBmpSet(GetWidth(), GetHeight(), true);
                    pen.mmgPen = sk.p;
                    Tyre.mWindow.paint(pen);

                    Tyre.mWindow.TimerTick();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return lret;
    }
}