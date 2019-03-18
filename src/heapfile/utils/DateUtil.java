package heapfile.utils;

import heapfile.config.DefaultConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static long strToLong(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat (DefaultConfig.DATEFORMAT);
        Date date = sdf.parse (str);
        long time = date.getTime ();
        return time;
    }

    public static String longToStr(long l) {
        SimpleDateFormat sdf = new SimpleDateFormat (DefaultConfig.DATEFORMAT);
        Date date = new Date (l);
        String str = sdf.format (date);
        return str;
    }
}
