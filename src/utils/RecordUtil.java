package utils;

import config.DefaultConfig;
import fields.Field;
import fields.FieldFactory;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Map;

public class RecordUtil {
    public static String[] parseRecord(byte[] bytes) throws ParseException {
        int size = DefaultConfig.tableInfo.size();
        String[] fields = new String[size];
        int index = 0;
        for (int i = 0; i < size; i++) {
            Map<String, Object> map = DefaultConfig.tableInfo.get(i);
            String type = (String) map.get(DefaultConfig.TYPE);
            Integer length = (Integer) map.get(DefaultConfig.LENGTH);
            Field field = FieldFactory.getField(type, null, length);
            byte[] fBytes = Arrays.copyOfRange(bytes, index, index + length);
            index += length;
            field.parse(fBytes);
            fields[i] = field.toString();
        }
        return fields;
    }
}
