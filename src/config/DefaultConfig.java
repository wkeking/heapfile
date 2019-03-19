package config;

import utils.TypeUtil;

import java.util.*;
import java.util.stream.Stream;

public class DefaultConfig {
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String LENGTH = "length";
    public static final String INT = "int";
    public static final String LONG = "long";
    public static final String CHAR = "char";
    public static final String VARCHAR = "varchar";
    public static final String BOOLEAN = "boolean";
    public static final String DATE = "date";

    public static final String DATEFORMAT = "dd/MM/yyyy KK:mm:ss aa";
    public static final String BRACKET_B = "(";
    public static final String BRACKET_A = ")";
    public static final String SEPARATOR = ",";
    public static final String POINT = ".";
    public static final String UTF8 = "utf-8";

    public static final String PAGENAME = "heap";
    public static int RECORDLENGTH = 0;

    public static final Map<String, String> defindTable = new LinkedHashMap<> ();
    public static final List<Map<String, Object>> tableInfo = new ArrayList<> ();

    static {
        defindTable.put ("DeviceId", DefaultConfig.INT + "(4)");
        defindTable.put ("ArrivalTime", DefaultConfig.DATE + "(8)");
        defindTable.put ("DepartureTime", DefaultConfig.DATE + "(8)");
        defindTable.put ("DurationSeconds", DefaultConfig.LONG + "(8)");
        defindTable.put ("StreetMarker", DefaultConfig.CHAR + "(6)");
        defindTable.put ("Sign", DefaultConfig.CHAR + "(40)");
        defindTable.put ("Area", DefaultConfig.CHAR + "(20)");
        defindTable.put ("StreetId", DefaultConfig.INT + "(4)");
        defindTable.put ("StreetName", DefaultConfig.CHAR + "(30)");
        defindTable.put ("BetweenStreet1", DefaultConfig.CHAR + "(30)");
        defindTable.put ("BetweenStreet2", DefaultConfig.CHAR + "(30)");
        defindTable.put ("Side Of Street", DefaultConfig.INT + "(4)");
        defindTable.put ("In Violation", DefaultConfig.BOOLEAN + "(1)");
    }

    public static void initTableInfo(String... fields) {
        if (fields == null) {
            DefaultConfig.defindTable.forEach ((k, v) -> DefaultConfig.initTable (k, v));
        } else {
            Stream.of (fields).forEach (f -> {
                String attr = defindTable.get (f);
                initTable(f, attr);
            });
        }
    }

    private static void initTable(String fieldName, String attr) {
        String type = TypeUtil.getType (attr);
        int length = TypeUtil.getTypeLen (attr);
        HashMap<String, Object> map = new HashMap<> ();
        map.put (DefaultConfig.NAME, fieldName);
        map.put (DefaultConfig.TYPE, type);
        map.put (DefaultConfig.LENGTH, length);
        tableInfo.add (map);
        DefaultConfig.RECORDLENGTH = DefaultConfig.RECORDLENGTH + length;
    }
}
