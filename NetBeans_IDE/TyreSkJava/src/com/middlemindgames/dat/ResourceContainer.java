package com.middlemindgames.dat;

/*
 * ResourceContainer.java
 * Victor G. Brusca 01/16/2022
 * Created on June 1, 2005, 10:57 PM by Middlemind Games
 */
public class ResourceContainer {
    /**
     * An array used to store image data in buffer for Bitmaps in the DAT file.
     */
    public static bitmapBuffer[] bitmaps;

    /**
     * An array used to store console images in the DAT file.  
     * Console images are special bitmaps that are only used in user interface 
     * aspects of the game.
     */
    public static bitmapBuffer[] consoleBitmaps;

    /**
     * An array used to store all the player data used in the game.
     * @see com.middlemindgames.dat.player
     */
    public static player[] players;
    
    //enemy objects
    public static enemy[][] enemies;
    public static enemy[] bosses;
    
    /**
     * An array storing all of the attackRoll objects in the DAT file.
     * @see com.middlemindgames.dat.attackRoll
     */
    public static attackRoll[] attackRolls;

    /**
     * An array used to store all battle objects found in the DAT file.
     * @see com.middlemindgames.dat.battles
     */
    public static battle[] battles;
    
    /**
     * The local array used to store all conversation objects in the game.
     * @see com.middlemindgames.dat.conversation
     */
    public static conversation[] conversations;

    /**
     * An array of booleans that represent all of the flag triggers in the game.
     */
    public static boolean[] flags;
    
    /**
     * An array used to store all item objects stored in the DAT file.
     * @see com.middlemindgames.dat.item
     */
    public static item[] items;
        
    //link table objects
    public static linkTable[] linkTables;

    /**
     * An array used to store all the npc objects used in the game.
     * @see com.middlemindgames.dat.npc
     */
    public static npcdata[] npcs;

    /**
     * An array used to store all the object information stored in the DAT file.  The game engine supports reading and
     * displaying objects of the following types.
     * 0 = link table object
     * 1 = can't walk over, collidable object
     * 2 = can walk under, layer two image
     * 3 = player characters object
     * 4 = can walk over, layer one
     * 5 = battle mode link object
     * 6 = npc object
     * 7 = hidden search object
     * 8 = can walk over with image rotation, layer one
     * 9 = object sets without rotation, layer one
     * 10 = object sets with rotation, layer one
     * 11 = object sets can't walk
     * @see com.middlemindgames.dat.tyreObject
     */
    public static tyreObject[] objects;
    
    /**
     * A multidimensional array used to store all the object sets in the DAT file.  An
     * object set is an array of indexes into the object array.
     */
    public static int[][] objectSets;
    
    /**
     * An array used to store all the room objects read in from the DAT file.
     * @see com.middlemindgames.dat.room
     */
    public static room[] rooms;
    
    /**
     * An array storing all the search objects in the DAT file.
     * @see com.middlemindgames.dat.search
     */
    public static search[] searches;

    public static sequenceBuffer[] sequences;
    
    //string objects
    public static String[] strings;
    public static String[] consoleStrings;
   
    /**
     * An array used store all the tile information contained in the DAT file.
     * @see com.middlemindgames.dat.tile
     */
    public static tile[] tiles;

    public ResourceContainer() { 
    }
}