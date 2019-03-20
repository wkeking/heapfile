package utils;

import config.TableConfig;

public class TypeUtil {
    public static int getTypeLen(String type) {
        int begin = type.indexOf (TableConfig.BRACKET_B);
        int after = type.indexOf (TableConfig.BRACKET_A);
        String s = type.substring (begin + 1, after);
        return Integer.parseInt (s);
    }

    public static String getType(String type) {
        int begin = type.indexOf (TableConfig.BRACKET_B);
        return type.substring (0, begin);
    }
}
