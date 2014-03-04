package baan.utilities;
import java.util.*;
import java.io.*;

public class Log {
    BufferedWriter messageLog;
    
    public static void logThis(Object source, String msg) {
        System.out.println(new Date() + ": (" + source.getClass().getName() + "): " + msg);
    }
    
    public Log() {
        // create a logfile for the messages
        GregorianCalendar cal = new GregorianCalendar();
        try {
            //messageLog = new BufferedWriter(new FileWriter("logboek.log"));
            messageLog = new BufferedWriter(new FileWriter("data - " + cal.get(cal.DAY_OF_MONTH) + "-" + cal.get(cal.MONTH) + "-" + cal.get(cal.YEAR) + "-" + cal.get(cal.HOUR_OF_DAY) + "." + cal.get(cal.MINUTE) + ".log"));
        } catch (IOException e) {
            System.out.println("Could not create a logfile");
        }
    }
    
    public void logThis(String data) {
        try {
            messageLog.append("\n" + data);
            System.out.println(data);
            messageLog.flush();
        } catch (IOException ex) {
            Log.logThis(this, "Could not write to logfile.");
        }
    }
    
    public void closeLogs() {
        try {
            messageLog.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static String printBytes(byte[] bytes) {
        // prints the bytes in an array as hexadecimals
        String s = "";
        for (int i = 0; i < bytes.length; i++) {
            s += "<" + Integer.toHexString((int) bytes[i]) + ">, ";
        }
        System.out.println(s.substring(0, s.length() - 2));
        return s.substring(0,s.length()-2);
    }
}

