using System;
using danger.util;
using net.middlemind.MmgGameApiCs.MmgBase;
using net.middlemind.MmgGameApiCs.MmgCore;

namespace com.middlemindgames.TyreSkNew {
    /// <summary>
    /// An application specific extension of the MmgCore API's GamePanel class.
    /// Created by Middlemind Games 02/19/2020
    /// 
    /// @author Victor G. Brusca
    /// </summary>
    public class GamePanel : net.middlemind.MmgGameApiCs.MmgCore.GamePanel
    {
        /// <summary>
        /// The Screen responsible for drawing the game.
        /// </summary>
        public ScreenMain screenMain;

        public static int pW;
        public static int pH;
        public static int wW;
        public static int wH;

        /// <summary>
        /// The basic constructor for this GamePanel extended class.
        /// 
        /// <param name="Mf">The MainFrame that is associated with this GamePanel.</param>
        /// <param name="WinWidth">The width to use for this GamePanel.</param>
        /// <param name="WinHeight">The height to use for this GamePanel.</param>
        /// <param name="X">The X position of this GamePanel.</param>
        /// <param name="Y">The Y position of this GamePanel.</param>
        /// <param name="GameWidth">The width of the game drawn on this GamePanel.</param>
        /// <param name="GameHeight">The height of the game drawn on this GamePanel.</param>
        /// </summary>
        public GamePanel(int WinWidth, int WinHeight, int X, int Y, int GameWidth, int GameHeight) : base(WinWidth, WinHeight, X, Y, GameWidth, GameHeight)
        {
            screenSplash.SetGenericEventHandler(this);
            screenLoading.SetGenericEventHandler(this);
            screenMain = new ScreenMain(GameStates.MAIN_GAME, this);
            SetBgColor(danger.ui.Color.GetColorFromInt(danger.ui.Color.JAVA_GRAY).GetColor());
        }

        public override void windowClosing(object sender, EventArgs e) {
            screenMain.tyre.suspend();
            base.windowClosing(sender, e);
        }

        public override void windowActivated(object sender, EventArgs e) {
            base.windowActivated(sender, e);
            setSize(wW, wH);
            Logger.wr("ClientBounds: " + base.Window.ClientBounds.Width + "x" + base.Window.ClientBounds.Height);
            Logger.wr("MmgApiGame: " + MmgApiGame.WIN_WIDTH + "x" + MmgApiGame.WIN_HEIGHT);
            Logger.wr("MmgApiGame: " + myX + "x" + myY);
        }

        /// <summary>
        /// Changes the currently visible game screen and cleans up the previously visible game screen.
        /// 
        /// <param name="g">The game state associated with the currently visible game screen.</param>
        /// </summary>
        public override void SwitchGameState(GameStates g)
        {
            if (gameState != prevGameState)
            {
                prevGameState = gameState;
            }

            if (g != gameState)
            {
                gameState = g;
            }
            else
            {
                return;
            }

            //unload
            if (prevGameState == GameStates.BLANK)
            {
                MmgHelper.wr("Hiding BLANK screen.");

            }
            else if (prevGameState == GameStates.SPLASH)
            {
                MmgHelper.wr("Hiding SPLASH screen.");
                screenSplash.Pause();
                screenSplash.SetIsVisible(false);
                screenSplash.UnloadResources();

            }
            else if (prevGameState == GameStates.LOADING)
            {
                MmgHelper.wr("Hiding LOADING screen.");
                screenLoading.Pause();
                screenLoading.SetIsVisible(false);
                screenLoading.UnloadResources();
                MmgHelper.wr("Hiding LOADING screen DONE.");

            }
            else if (prevGameState == GameStates.MAIN_GAME)
            {
                MmgHelper.wr("Hiding MAIN_GAME screen.");
                screenMain.Pause();
                screenMain.SetIsVisible(false);
                screenMain.UnloadResources();

            }

            //load
            MmgHelper.wr("Switching Game State To: " + gameState);
            if (gameState == GameStates.BLANK)
            {
                MmgHelper.wr("Showing BLANK screen.");

            }
            else if (gameState == GameStates.SPLASH)
            {
                MmgHelper.wr("Showing SPLASH screen.");
                screenSplash.LoadResources();
                screenSplash.UnPause();
                screenSplash.SetIsVisible(true);
                screenSplash.StartDisplay();
                currentScreen = screenSplash;

            }
            else if (gameState == GameStates.LOADING)
            {
                MmgHelper.wr("Showing LOADING screen.");
                screenLoading.LoadResources();
                screenLoading.UnPause();
                screenLoading.SetIsVisible(true);
                screenLoading.StartDatLoad();
                currentScreen = screenLoading;

            }
            else if (gameState == GameStates.MAIN_GAME)
            {
                MmgHelper.wr("Showing MAIN_GAME screen.");
                screenMain.LoadResources();
                screenMain.UnPause();
                screenMain.SetIsVisible(true);
                currentScreen = screenMain;

            }
        }

        /// <summary>
        /// The generic event handler method used to handle events from different game screens like the splash screen and the loading screen.
        /// 
        /// <param name="obj">The generic event message to process.</param>
        /// </summary>
        public override void HandleGenericEvent(GenericEventMessage obj)
        {
            if (obj != null)
            {
                if (obj.GetGameState() == GameStates.LOADING)
                {
                    if (obj.GetId() == ScreenLoading.EVENT_LOAD_COMPLETE)
                    {
                        //Final loading steps
                        DatExternalStrings.LOAD_EXT_STRINGS();
                        SwitchGameState(GameStates.MAIN_GAME);
                    }

                }
                else if (obj.GetGameState() == GameStates.SPLASH)
                {
                    if (obj.GetId() == ScreenSplash.EVENT_DISPLAY_COMPLETE)
                    {
                        SwitchGameState(GameStates.LOADING);
                    }

                }
            }
        }
    }
}