package utils;

import config.TableConfig;

public class CompareUtil {
    private static int LENGTH = 10;
    private static String FLAG = "/";

    public static <K> int compare(K key, K data) {
        String[] keys = String.valueOf (key).split (TableConfig.NULL);
        String[] datas = String.valueOf (data).split (TableConfig.NULL);
        String keyId = parseId (keys[0]);
        String dataId = parseId (datas[0]);
        int cmpId = keyId.compareTo (dataId);
        if (cmpId != 0) return cmpId;
        String[] keyTime = parseTime (keys[0]).split (FLAG);
        String[] dataTime = parseTime (datas[0]).split (FLAG);
        int cmp02 = keyTime[2].compareTo (dataTime[2]);
        if (cmp02 != 0) return cmp02;
        int cmp00 = keyTime[0].compareTo (dataTime[0]);
        if (cmp00 != 0) return cmp00;
        int cmp01 = keyTime[1].compareTo (dataTime[1]);
        if (cmp01 != 0) return cmp01;
        int cmp2 = keys[2].compareTo (datas[2]);
        if (cmp2 != 0) return cmp2;
        return keys[1].compareTo (datas[1]);
    }

    public static String parseTime(String str) {
        String substring = str.substring (str.length () - LENGTH, str.length ());
        return substring;
    }

    public static String parseId(String str) {
        return str.substring (0, str.length () - LENGTH);
    }
}
