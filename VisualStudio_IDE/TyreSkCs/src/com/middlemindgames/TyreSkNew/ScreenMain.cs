using System;
using danger.ui;
using net.middlemind.MmgGameApiCs.MmgBase;
using net.middlemind.MmgGameApiCs.MmgCore;
using static net.middlemind.MmgGameApiCs.MmgCore.GamePanel;
using danger.app;
using com.middlemindgames.TyreSkOrig;
using danger.util;
using Microsoft.Xna.Framework.Graphics;

namespace com.middlemindgames.TyreSkNew {
    /// <summary>
    /// A game screen object, ScreenMainMenu, that extends the MmgGameScreen base class. 
    /// This game screen is for displaying a main menu screen.
    /// Created by Middlemind Games 08/01/2015
    ///
    /// @author Victor G.Brusca
    /// </summary>
    [System.Diagnostics.CodeAnalysis.SuppressMessage("Style", "IDE1006:Naming Styles", Justification = "<Pending>")]
    public class ScreenMain : net.middlemind.MmgGameApiCs.MmgCore.Screen {
        /// <summary>
        /// A private variable used in drawing routine methods.
        /// </summary>
        private bool lret;

        public Tyre tyre;
        public Pen pen;
        //public MmgBmp xBmp;

        /// <summary>
        /// Reads the task objects written by the cross thread write class.
        /// </summary>
        private CrossThreadRead xTrdR;

        /// <summary>
        /// A task object passed around by the cross thread classes.
        /// </summary>
        private CrossThreadCommand xTrdC;

        public MmgDrawableBmpSet sk;
        //public MmgBmp tmp;
        public bool dpadPress = false;
        public bool keyPress = false;
        public bool bPress = false;
        public bool aPress = false;

        /// <summary>
        /// Constructor, sets the game state associated with this screen, and sets the owner GamePanel instance.
        /// </summary>
        /// <param name="State">The game state of this game screen.</param>
        /// <param name="Owner">The owner of this game screen.</param>
        public ScreenMain(GameStates State, GamePanel Owner) : base(State, Owner) {
            isDirty = false;
            pause = false;
            ready = false;
            state = State;
            owner = Owner;
        }

        /// <summary>
        /// Loads all the resources needed to display this game screen.
        /// </summary>
        public override void LoadResources() {
            pause = true;
            SetHeight(MmgScreenData.GetGameHeight());
            SetWidth(MmgScreenData.GetGameWidth());
            SetPosition(MmgScreenData.GetPosition());

            classConfig = MmgHelper.ReadClassConfigFile(GameSettings.CLASS_CONFIG_DIR + GameSettings.NAME + "/screen_main.txt");


            sk = MmgHelper.CreateDrawableBmpSet(GetWidth(), GetHeight(), true);
            pen = new Pen(sk.p);
            //tmp = MmgHelper.GetBasicCachedBmp("a_btn.png");
            //xBmp = MmgHelper.CreateFilledBmp(sk.buffImg.Width, sk.buffImg.Height, MmgColor.GetRed());

            isDirty = true;
            ready = true;
            pause = false;
        }

        /// <summary>
        /// A callback method used to process A click events.
        /// </summary>
        /// <param name="src">The source of the A click event, keyboard, GPIO gamepad, USB gamepad.</param>
        /// <returns>A bool flag indicating if work was done.</returns>
        public override bool ProcessAClick(int src) {
            return true;
        }

        /**
         * 
         * @param src
         * @return 
         */
        public override bool ProcessAPress(int src) {
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
        public override bool ProcessARelease(int src) {
            if (aPress == true) {
                aPress = false;
                return Tyre.mWindow.eventWidgetUp(Event.DEVICE_WHEEL_BUTTON, null);
            }
            return true;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="c"></param>
        /// <param name="code"></param>
        /// <returns></returns>
        public override bool ProcessKeyClick(char c, int code) {
            return true;
        }

        /**
         * 
         * @param c
         * @param code
         * @return 
         */
        public override bool ProcessKeyPress(char c, int code) {            
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

        public override bool ProcessKeyRelease(char c, int code) {
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
        public override bool ProcessBPress(int src) {
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
        public override bool ProcessBRelease(int src) {
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
        public override bool ProcessDpadRelease(int dir) {
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
        public override bool ProcessDpadPress(int dir) {
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

        /// <summary>
        /// Unloads resources needed to display this game screen.
        /// </summary>
        public override void UnloadResources() {
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

            base.UnloadResources();
            ready = false;
        }

        /// <summary>
        /// Returns the game state of this game screen.
        /// </summary>
        /// <returns>The game state of this game screen.</returns>
        public override GameStates GetGameState() {
            return state;
        }

        /// <summary>
        /// Gets a bool flag indicating if there is work to be done on the next MmgUpdate method call.
        /// </summary>
        /// <returns>A flag indicating if there is work to be done on the next MmgUpdate call.</returns>
        public override bool GetIsDirty() {
            return isDirty;
        }

        /// <summary>
        /// Sets a bool flag indicating if there is work to be done on the next MmgUpdate method call.
        /// </summary>
        /// <param name="b">A flag indicating if there is work to be done on the next MmgUpdate call.</param>
        public override void SetIsDirty(bool b) {
            isDirty = b;
        }

        /// <summary>
        /// The main drawing routine.
        /// </summary>
        /// <param name="p">An MmgPen object to use for drawing this game screen.</param>
        public override void MmgDraw(MmgPen p) {
            if (pause == false && GetIsVisible() == true) {
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
                        xTrdR = new CrossThreadRead(Tyre.datLoader.xTrdW);
                        Tyre.datLoader.trd.Start();
                    }
                } catch (Exception e) {
                    Logger.wr(e.Message);
                    Logger.wr(e.StackTrace);
                }
            }
        }

        /// <summary>
        /// Update the current sprite animation frame index.
        /// </summary>
        /// <param name="updateTick">The index of the MmgUpdate call.</param>
        /// <param name="currentTimeMs">The current time in milliseconds of the MmgUpdate call.</param>
        /// <param name="msSinceLastFrame">The number of milliseconds since the last MmgUpdate call.</param>
        /// <returns>A bool flag indicating if any work was done.</returns>
        public override bool MmgUpdate(int updateTick, long currentTimeMs, long msSinceLastFrame) {
            lret = false;

            if (pause == false && isVisible == true) {
                if (base.MmgUpdate(updateTick, currentTimeMs, msSinceLastFrame) == true) {
                    lret = true;
                }

                try {
                    if (xTrdC == null && xTrdR != null) {
                        xTrdC = xTrdR.GetNextCommand();
                        if (xTrdC != null && xTrdC.name.Equals("finalizeLoad")) {
                            Logger.wr("Error!! Found finalize load message.");
                            //Tyre.cApp.finalizeLoad();

                        } else if (xTrdC != null && xTrdC.name.Equals("xTrdHandleStrings")) {
                            Tyre.cApp.xTrdHandleStrings();

                        } else if (xTrdC != null && xTrdC.name.Equals("xTrdHandleBitmaps")) {
                            Tyre.cApp.xTrdHandleBitmaps();

                        } else if (xTrdC != null && xTrdC.name.Equals("xTrdHandleConsoleBitmaps")) {
                            Tyre.cApp.xTrdHandleConsoleBitmaps();

                        } else if (xTrdC != null && xTrdC.name.Equals("xTrdHandleSequences")) {
                            Tyre.cApp.xTrdHandleSequences();

                        } else if (xTrdC != null && xTrdC.name.Equals("xTrdHandlePrepConsoleBitmaps")) {
                            Tyre.cApp.xTrdHandlePrepConsoleBitmaps();

                        } else if (xTrdC != null) {
                            Logger.wr("Could not proces command: " + xTrdC.name);

                        }
                        xTrdC = null;
                    }

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
                        sk.p.GetGraphics().GraphicsDevice.SetRenderTarget(sk.buffImg);
                        sk.p.GetGraphics().Begin(SpriteSortMode.Immediate, BlendState.NonPremultiplied);
                        sk.p.GetGraphics().GraphicsDevice.Clear(MmgColor.GetBlack().GetColor());

                        pen.mmgPen = sk.p;
                        Tyre.mWindow.paint(pen);

                        sk.p.GetGraphics().End();
                        sk.p.GetGraphics().GraphicsDevice.SetRenderTarget(null);

                        Tyre.mWindow.TimerTick();
                    }
                } catch (Exception e) {
                    Logger.wr(e.Message);
                    Logger.wr(e.StackTrace);
                }
            }
            return lret;
        }
    }
}