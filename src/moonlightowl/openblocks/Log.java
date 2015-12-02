package moonlightowl.openblocks;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * OpenBlocks.Log
 * Created by MoonlightOwl on 10/25/15.
 */

public class Log {
    private static PrintWriter writer;
    public static void init() {
        if(Settings.LOG_FILE) {
            try {
                writer = new PrintWriter(new Date().toString() + ".log", "UTF-8");
            } catch (Exception e) {
                error("Cannot create log file! ;)", e);
            }
        }
    }

    public static void out(String message){
        message = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME)
                + " : " + message;
        if(writer != null){ writer.println(message); writer.flush(); }
        System.out.println(message);
    }
    public static void trace(Throwable e){
        if(writer != null){ e.printStackTrace(writer); writer.flush(); }
        e.printStackTrace();
    }

    public static void error(String message){
        out("[ERROR] " + message);
    }
    public static void error(String message, Throwable e){
        error(message);
        trace(e);
    }

    public static void close() {
        if(writer != null) writer.close();
    }
}
