package heapfile.utils;

import heapfile.config.DefaultConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static long strToLong(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat (DefaultConfig.DATEFORMAT, Locale.ENGLISH);
        Date date = sdf.parse (str);
        long time = date.getTime ();
        return time;
    }

    public static String longToStr(long l) {
        SimpleDateFormat sdf = new SimpleDateFormat (DefaultConfig.DATEFORMAT, Locale.ENGLISH);
        String str = sdf.format (l);
        return str;
    }
}
