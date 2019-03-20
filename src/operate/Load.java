package operate;

import config.TableConfig;
import fields.FieldType;
import utils.RecordUtil;

import java.io.RandomAccessFile;

public class Load {
    private int pageSize;
    private String dataFilePath;

    public Load(int pageSize) {
        this.pageSize = pageSize;
        this.dataFilePath = TableConfig.PAGENAME + TableConfig.POINT + String.valueOf (pageSize);
    }

    public void read() {
        TableConfig.initTableInfo();
        try (RandomAccessFile raf = new RandomAccessFile(dataFilePath, "r")) {
            long numIndex = pageSize - FieldType.INT.getLength(0);
            raf.seek(numIndex);
            byte[] numBytes = new byte[4];
            raf.readFully(numBytes);
            int num = numBytes[3] & 0xFF |
                    (numBytes[2] & 0xFF) << 8 |
                    (numBytes[1] & 0xFF) << 16 |
                    (numBytes[0] & 0xFF) << 24;
            raf.seek(0L);
            byte[] record = new byte[TableConfig.RECORDLENGTH];
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
