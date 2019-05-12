package utils;

import config.TableConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat (TableConfig.DATEFORMAT, Locale.ENGLISH);

    public static long strToLong(String str) throws ParseException {
        return sdf.parse (str).getTime ();
    }

    public static String longToStr(long l) {
        return sdf.format (l);
    }
}
