package utils;

import config.TableConfig;
import element.fields.Field;
import element.fields.FieldFactory;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Map;

public class RecordUtil {
    public static String[] parseRecord(byte[] bytes) throws ParseException {
        int size = TableConfig.tableInfo.size();
        String[] fields = new String[size];
        int index = 0;
        for (int i = 0; i < size; i++) {
            Map<String, Object> map = TableConfig.tableInfo.get(i);
            String type = (String) map.get(TableConfig.TYPE);
            Integer length = (Integer) map.get(TableConfig.LENGTH);
            Field field = FieldFactory.getField(type, length);
            byte[] fBytes = Arrays.copyOfRange(bytes, index, index + length);
            index += length;
            field.parse(fBytes);
            fields[i] = field.toString();
        }
        return fields;
    }
}
