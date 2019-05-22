package utils;

import config.TableConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat (TableConfig.DATEFORMAT, Locale.ENGLISH);
    private static SimpleDateFormat sdf1 = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss");

    public static long strToLong(String str) throws ParseException {
        return sdf.parse (str).getTime ();
    }

    public static String longToStr(long l) {
        return sdf.format (l);
    }

    public static String regex(String str) throws ParseException {
        Date parse = sdf.parse (str);
        String format = sdf1.format (parse);
        return format;
    }
}
