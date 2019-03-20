package operate;

import config.DefaultConfig;
import fields.FieldType;
import utils.RecordUtil;

import java.io.RandomAccessFile;

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
                String[] records = RecordUtil.parseRecord(record);
                StringBuilder sb = new StringBuilder(records[0]);
                for (int l = 1; l < records.length; l++) {
                    sb.append(",");
                    sb.append(records[l]);
                }
                System.out.println(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
