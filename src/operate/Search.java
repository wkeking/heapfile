package operate;

import config.Condition;
import config.TableConfig;
import element.records.Record;
import element.tree.BPlusTree;
import element.tree.Index;
import utils.RecordUtil;

import java.io.*;
import java.text.ParseException;
import java.util.List;

public class Search {
    private long total = 0L;
    private int pageSize;
    private RandomAccessFile raf;
    private ObjectInputStream ois;

    public Search(int pageSize) throws IOException {
        TableConfig.initTableInfo();
        this.pageSize = pageSize;
        raf = new RandomAccessFile(TableConfig.PAGENAME + TableConfig.POINT + String.valueOf (pageSize), "r");
        File file = new File (TableConfig.INDEXNAME);
        FileInputStream fis = new FileInputStream (file);
        BufferedInputStream bis = new BufferedInputStream(fis, TableConfig.TBUFFERSIZE);
        ois = new ObjectInputStream (bis);
    }

    public void search(Condition condition) {
        boolean flag = true;
        while (flag) {
            try {
                BPlusTree<String, String> tree = (BPlusTree) ois.readObject ();
                switch (condition) {
                    case EQUALITY:
                        String values = tree.search (TableConfig.KEYWORDS);
                        hit (values);
                        break;
                    case RANGE:
                        List<String> results = tree.searchRange (TableConfig.RANGS_KEYS[0], TableConfig.RANGS_KEYS[1], BPlusTree.RangePolicy.INCLUSIVE);
                        for (String result : results) {
                            hit (result);
                        }
                        break;
                }

            } catch (Exception e) {
                flag = false;
            }
        }
        System.out.println("The number of amount to search the heap file is " + total);
    }

    private void hit(String values) throws IOException, ParseException {
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
                total ++;
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
