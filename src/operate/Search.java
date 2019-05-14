package operate;

import config.TableConfig;
import element.records.Record;
import element.tree.BPlusTree;
import element.tree.Index;
import utils.RecordUtil;

import java.io.*;

public class Search {
    private String key;
    private int pageSize;
    private RandomAccessFile raf;
    private ObjectInputStream ois;

    public Search(String key, int pageSize) throws IOException {
        TableConfig.initTableInfo();
        this.key = key;
        this.pageSize = pageSize;
        raf = new RandomAccessFile(TableConfig.PAGENAME + TableConfig.POINT + String.valueOf (pageSize), "r");
        File file = new File (TableConfig.INDEXNAME);
        FileInputStream fis = new FileInputStream (file);
        BufferedInputStream bis = new BufferedInputStream(fis, TableConfig.TBUFFERSIZE);
        ois = new ObjectInputStream (bis);
    }

    public void search() {
        boolean flag = true;
        while (flag) {
            try {
                BPlusTree<String, String> tree = (BPlusTree) ois.readObject ();
                String values = tree.search (key);
                if (values != null) {
                    String[] indices = values.split (TableConfig.SEPARATOR);
                    for (String i : indices) {
                        Index index = new Index (i);
                        long pageId = index.getPageId ();
                        int recordId = index.getRecordId ();
                        byte[] recordByte = new byte[TableConfig.RECORDLENGTH];
                        raf.seek (pageId * pageSize + recordId * TableConfig.RECORDLENGTH);
                        raf.read(recordByte);
                        String[] records = RecordUtil.parseRecord(recordByte);
                        Record record = new Record (records, pageId + 1, recordId + 1);
                        System.out.println("Page ID:" + record.getPageId () + ",Record ID:" + record.getRecordId ());
                        System.out.println ("Record Value:" + record.toString ());
                    }
                }
            } catch (Exception e) {
                flag = false;
            }
        }
    }

    //关闭流
    public void close() throws IOException {
        if (raf != null) {
            raf.close ();
        }
        if (ois != null) {
            ois.close ();
        }
    }
}
