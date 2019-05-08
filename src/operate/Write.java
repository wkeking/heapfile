package operate;

import config.TableConfig;
import element.fields.Field;
import element.fields.FieldFactory;
import element.fields.FieldType;
import element.tree.BPlusTree;
import element.tree.Index;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
        BPlusTree tree = new BPlusTree (8);
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

                //创建DeviceId ArrivalTime索引
                List<Index> indices = new ArrayList<> ();
                Index index = new Index (pageNum, pRecordNum - 1);
                indices.add (index);
                String key = rs[0] + rs[1];
                tree.insert (key, indices);
            }

            //写索引文件
            writeIndexFile (tree);
            System.out.println ("The number of records loaded is " + recordsNum);
            System.out.println ("The number of pages saved is " + pageNum);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private void writeIndexFile(BPlusTree tree) throws IOException {
        File file = new File (TableConfig.INDEXNAME);
        try (FileOutputStream fos = new FileOutputStream (file);
             BufferedOutputStream bos = new BufferedOutputStream (fos, TableConfig.BUFFERSIZE);
             ObjectOutputStream oos = new ObjectOutputStream (bos)) {
            oos.writeObject (tree);
            System.out.println("Successfully written to the index file!");
        } catch (IOException e) {
            throw e;
        }
    }
}
