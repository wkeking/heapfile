package operate;

import config.TableConfig;
import element.fields.Field;
import element.fields.FieldFactory;
import element.fields.FieldType;

import java.io.*;
import java.util.Map;

public class Write {
    private int pageSize;
    private String dataFilePath;
    private long recordsNum;//记录总条数

    public Write(int pageSize, String dataFilePath) {
        this.pageSize = pageSize;
        this.dataFilePath = dataFilePath;
        recordsNum = 0L;
    }

    //读取元数据，写成固定格式的数据流文件
    public void write() {
        String heapPath = TableConfig.PAGENAME + "." + pageSize;
        File file = new File (heapPath);
        try (FileReader fr = new FileReader (dataFilePath);
             BufferedReader lnr = new BufferedReader(fr, TableConfig.BUFFERSIZE);
             FileOutputStream fos = new FileOutputStream (file);
             BufferedOutputStream bos = new BufferedOutputStream (fos, pageSize * 2);
             DataOutputStream dos = new DataOutputStream (bos)) {
            String[] fields = lnr.readLine ().split (TableConfig.SEPARATOR);
            TableConfig.initTableInfo (fields);
            int pageNum = 0;
            int realSize = FieldType.INT.getLength (0);
            int pRecordNum = 0;
            String record;
            while ((record = lnr.readLine ()) != null) {//一次写一页文件
                if ((realSize + TableConfig.RECORDLENGTH) > pageSize) {
                    int space = pageSize - realSize;
                    StringBuilder sb = new StringBuilder ();
                    for (int i = 0; i < space; i++) {
                        sb.append (" ");
                    }
                    dos.writeBytes (sb.toString ());
                    dos.writeInt (pRecordNum);
                    dos.flush ();
                    pageNum ++;
                    realSize = FieldType.INT.getLength (0);
                    pRecordNum = 0;
                }
                String[] rs = record.split (TableConfig.SEPARATOR);
                for (int i = 0; i < rs.length; i++) {
                    Map<String, Object> fieldInfo = TableConfig.tableInfo.get (i);
                    Field field = FieldFactory.getField ((String) fieldInfo.get (TableConfig.TYPE), rs[i], (Integer) fieldInfo.get (TableConfig.LENGTH));
                    field.serialize (dos);
                }
                pRecordNum ++;
                realSize += TableConfig.RECORDLENGTH;
                recordsNum ++;
            }
            System.out.println ("The number of records loaded is " + recordsNum);
            System.out.println ("The number of pages saved is " + pageNum);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}
