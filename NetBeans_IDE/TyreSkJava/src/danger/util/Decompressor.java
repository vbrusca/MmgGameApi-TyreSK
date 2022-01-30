package danger.util;

import java.util.Arrays;
import java.util.zip.Inflater;

/*
 * Decompressor.java
 * Victor G. Brusca 01/30/2022
 */
@SuppressWarnings("CallToPrintStackTrace")
public class Decompressor {
    public byte[] input;
    public boolean started = false;
    public boolean stopped = false;
    
    public void reset() {
        input = null;
        started = false;
        stopped = false;
    }
    
    public void setInput(byte[] b, int off, int len) {
        //Logger.wr("setInput: Off:" + off + " Len:" + len + " b.length:" + b.length);
        input = Arrays.copyOfRange(b, off, len);
        //Logger.wr("setInput: input.length:" + input.length);
    }
    
    public int inflate(byte[] b, int off, int len) {
        started = true;
        stopped = false;
        
        try {
            //Logger.wr("=========================================================");
            //Logger.wr("Decompressor: Input Len:" + input.length);
            Inflater decompressor = new Inflater();
            decompressor.setInput(input);
            int length = decompressor.inflate(b);
            //Logger.wr("Decompressor: Output Len:" + length);            
            end();
            return length;
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return -1;
    }
    
    public void end() {
        stopped = true;
    }
    
    public boolean finished() {
        if(started && stopped) {
            return true;
        } else {
            return false;
        }
    }
}