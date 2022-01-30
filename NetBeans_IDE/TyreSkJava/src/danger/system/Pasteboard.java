package danger.system;

/*
 * Pasteboard.java
 * Victor G. Brusca 01/22/2022
 */
public class Pasteboard {
    public static String val = "";
    
    public static void setString(String s) {
        val = s;
    }
    
    public static String getString() {
        return val;
    }
}