package operate;

import config.DefaultConfig;
import fields.Field;
import fields.FieldFactory;
import fields.FieldType;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Map;

public class Load {

    public void read(String path) {
        DefaultConfig.initTableInfo();
        int index = path.indexOf(DefaultConfig.POINT);
        String substring = path.substring(index + 1);
        int pageSize = Integer.parseInt(substring);
        try (RandomAccessFile raf = new RandomAccessFile(path, "r")) {
            long numIndex = pageSize - FieldType.INT.getLength(0);
            raf.seek(numIndex);
            byte[] numBytes = new byte[4];
            raf.readFully(numBytes);
            int num = numBytes[3] & 0xFF |
                    (numBytes[2] & 0xFF) << 8 |
                    (numBytes[1] & 0xFF) << 16 |
                    (numBytes[0] & 0xFF) << 24;
            raf.seek(0L);
            byte[] record = new byte[DefaultConfig.RECORDLENGTH];
            for (int i = 0; i < num; i++) {
                raf.read(record);
                String[] records = parseRecord(record);
                StringBuilder sb = new StringBuilder(records[0]);
                for (int l = 1; l < records.length; l++) {
                    sb.append(",");
                    sb.append(records[l]);
                }
                System.out.println(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private String[] parseRecord(byte[] bytes) throws ParseException {
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
