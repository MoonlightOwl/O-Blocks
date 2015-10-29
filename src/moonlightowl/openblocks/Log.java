package moonlightowl.openblocks;

/**
 * OpenBlocks.Log
 * Created by MoonlightOwl on 10/25/15.
 */

public class Log {
    public static void out(String message){
        System.out.println(message);
    }
    public static void trace(Exception e){
        e.printStackTrace();
    }

    public static void error(String message){
        out("[ERROR] " + message);
    }
    public static void error(String message, Exception e){
        error(message);
        trace(e);
    }
}
