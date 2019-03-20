package operate;

import config.DefaultConfig;
import fields.Field;
import fields.FieldFactory;
import fields.FieldType;

import java.io.*;
import java.util.Map;

public class Write {
    private int pageSize;
    private String dataFilePath;
    private long dataSize;

    public Write(int pageSize, String dataFilePath) {
        this.pageSize = pageSize;
        this.dataFilePath = dataFilePath;
        dataSize = 0L;
    }

    public void write() {
        long start = System.currentTimeMillis ();
        String heapPath = DefaultConfig.PAGENAME + "." + pageSize;
        File file = new File (heapPath);
        try (InputStream in = new FileInputStream (dataFilePath);
             InputStreamReader isr = new InputStreamReader (in, DefaultConfig.UTF8);
             LineNumberReader lnr = new LineNumberReader (isr);
             FileOutputStream fos = new FileOutputStream (file);
             BufferedOutputStream bos = new BufferedOutputStream (fos, pageSize);
             DataOutputStream dos = new DataOutputStream (bos)) {
            String[] fields = lnr.readLine ().split (DefaultConfig.SEPARATOR);
            DefaultConfig.initTableInfo (fields);
            int pageNum = 0;
            int realSize = FieldType.INT.getLength (0);
            int recordNum = 0;
            String record;
            while ((record = lnr.readLine ()) != null) {
                if ((realSize + DefaultConfig.RECORDLENGTH) > pageSize) {
                    int space = pageSize - realSize;
                    StringBuilder sb = new StringBuilder ();
                    for (int i = 0; i < space; i++) {
                        sb.append (" ");
                    }
                    dos.writeBytes (sb.toString ());
                    dos.writeInt (recordNum);
                    dos.flush ();
                    pageNum ++;
                    realSize = FieldType.INT.getLength (0);
                    recordNum = 0;
                }
                String[] rs = record.split (DefaultConfig.SEPARATOR);
                for (int i = 0; i < rs.length; i++) {
                    Map<String, Object> fieldInfo = DefaultConfig.tableInfo.get (i);
                    Field field = FieldFactory.getField ((String) fieldInfo.get (DefaultConfig.TYPE), rs[i], (Integer) fieldInfo.get (DefaultConfig.LENGTH));
                    field.serialize (dos);
                }
                recordNum ++;
                realSize += DefaultConfig.RECORDLENGTH;
                dataSize ++;
            }
            long stop = System.currentTimeMillis ();
            System.out.println ("The number of records loaded is " + dataSize);
            System.out.println ("The number of pages saved is " + pageNum);
            System.out.println ("The number of milliseconds to create the heap file is " + (stop - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}
