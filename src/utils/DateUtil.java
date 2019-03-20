package utils;

import config.TableConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil {
    public static long strToLong(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat (TableConfig.DATEFORMAT, Locale.ENGLISH);
        return sdf.parse (str).getTime ();
    }

    public static String longToStr(long l) {
        SimpleDateFormat sdf = new SimpleDateFormat (TableConfig.DATEFORMAT, Locale.ENGLISH);
        return sdf.format (l);
    }
}
