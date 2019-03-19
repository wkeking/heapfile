package operate;

import config.DefaultConfig;
import fields.Field;
import fields.FieldFactory;
import fields.FieldType;

import java.io.*;
import java.text.ParseException;
import java.util.Map;

public class Write {
    private int pageSize;
    private int recordNum;
    private String dataFilePath;
    private int realSize;

    public Write(int pageSize, String dataFilePath) {
        this.pageSize = pageSize;
        this.dataFilePath = dataFilePath;
        recordNum = 0;
        realSize = FieldType.INT.getLength (0);
    }

    public void write() {
        long start = System.currentTimeMillis ();
        String heapPath = DefaultConfig.PAGENAME + "." + pageSize;
        File file = new File (heapPath);
        try (InputStream in = new FileInputStream (dataFilePath);
             InputStreamReader isr = new InputStreamReader (in, DefaultConfig.UTF8);
             LineNumberReader lnr = new LineNumberReader (isr);
             FileOutputStream fos = new FileOutputStream (file);
             BufferedOutputStream bos = new BufferedOutputStream (fos);
             DataOutputStream dos = new DataOutputStream (bos)) {
            String[] fields = lnr.readLine ().split (DefaultConfig.SEPARATOR);
            DefaultConfig.initTableInfo (fields);//初始化表文件结构
            while ((realSize + DefaultConfig.RECORDLENGTH) <= pageSize) {
                String[] record = lnr.readLine ().split (DefaultConfig.SEPARATOR);
                write (dos, record);
            }
            int space = pageSize - realSize;
            StringBuilder sb = new StringBuilder ();
            for (int i = 0; i < space; i++) {
                sb.append (" ");
            }
            dos.writeBytes (sb.toString ());
            dos.writeInt (recordNum);
            dos.flush ();
            long stop = System.currentTimeMillis ();
            System.out.println ("Records Number=" + recordNum);
            System.out.println ("Page Number=1");
            System.out.println ("Time:" + (stop - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private void write(DataOutputStream dos, String... record) throws IOException, ParseException {
        for (int i = 0; i < record.length; i++) {
            Map<String, Object> fieldInfo = DefaultConfig.tableInfo.get (i);
            Field field = FieldFactory.getField ((String) fieldInfo.get (DefaultConfig.TYPE), record[i], (Integer) fieldInfo.get (DefaultConfig.LENGTH));
            field.serialize (dos);
        }
        recordNum++;
        realSize += DefaultConfig.RECORDLENGTH;
    }
}
