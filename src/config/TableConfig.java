package config;

import utils.TypeUtil;

import java.util.*;
import java.util.stream.Stream;

/**
 * 表结构初始化
 */
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

    public static final String DATEFORMAT = "MM/dd/yyyy KK:mm:ss aa";
    public static final String BRACKET_B = "(";
    public static final String BRACKET_A = ")";
    public static final String SEPARATOR = ",";
    public static final String POINT = ".";
    public static final int BUFFERSIZE = 1024 * 1024 * 2;//读取数据流文件缓冲区大小

    public static final String PAGENAME = "heap";
    public static int RECORDLENGTH = 0;
    public static String KEYWORDS;//查找数据流文件的key

    public static final Map<String, String> defindTable = new LinkedHashMap<> ();//初始化表结构
    public static final List<Map<String, Object>> tableInfo = new ArrayList<> ();//加载元数据对比表头，固定字段顺序

    static {//表结构
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
        defindTable.put ("Vehicle Present", TableConfig.BOOLEAN + "(1)");
    }

    //加载元数据初始化表结构
    public static void initTableInfo(String... fields) {
        Stream.of (fields).forEach (f -> {
            String attr = defindTable.get (f);
            initTable(f, attr);
        });
    }

    //加载数据流文件初始化表结构
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
