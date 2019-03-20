package config;

import utils.TypeUtil;

import java.util.*;
import java.util.stream.Stream;

public class TableConfig {
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
    public static final int BUFFERSIZE = 1024 * 1024;

    public static final String PAGENAME = "heap";
    public static int RECORDLENGTH = 0;

    public static final Map<String, String> defindTable = new LinkedHashMap<> ();
    public static final List<Map<String, Object>> tableInfo = new ArrayList<> ();

    static {
        defindTable.put ("DeviceId", TableConfig.INT + "(4)");
        defindTable.put ("ArrivalTime", TableConfig.DATE + "(8)");
        defindTable.put ("DepartureTime", TableConfig.DATE + "(8)");
        defindTable.put ("DurationSeconds", TableConfig.LONG + "(8)");
        defindTable.put ("StreetMarker", TableConfig.CHAR + "(6)");
        defindTable.put ("Sign", TableConfig.CHAR + "(40)");
        defindTable.put ("Area", TableConfig.CHAR + "(20)");
        defindTable.put ("StreetId", TableConfig.INT + "(4)");
        defindTable.put ("StreetName", TableConfig.CHAR + "(30)");
        defindTable.put ("BetweenStreet1", TableConfig.CHAR + "(30)");
        defindTable.put ("BetweenStreet2", TableConfig.CHAR + "(30)");
        defindTable.put ("Side Of Street", TableConfig.INT + "(4)");
        defindTable.put ("In Violation", TableConfig.BOOLEAN + "(1)");
    }

    public static void initTableInfo(String... fields) {
        Stream.of (fields).forEach (f -> {
            String attr = defindTable.get (f);
            initTable(f, attr);
        });
    }

    public static void initTableInfo() {
        TableConfig.defindTable.forEach ((k, v) -> TableConfig.initTable (k, v));
    }

    private static void initTable(String fieldName, String attr) {
        String type = TypeUtil.getType (attr);
        int length = TypeUtil.getTypeLen (attr);
        HashMap<String, Object> map = new HashMap<> ();
        map.put (TableConfig.NAME, fieldName);
        map.put (TableConfig.TYPE, type);
        map.put (TableConfig.LENGTH, length);
        tableInfo.add (map);
        TableConfig.RECORDLENGTH = TableConfig.RECORDLENGTH + length;
    }
}
