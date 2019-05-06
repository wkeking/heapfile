package operate;

import config.TableConfig;
import element.records.Record;
import element.tree.BplusTree;
import element.tree.Index;
import utils.RecordUtil;

import java.io.*;
import java.text.ParseException;
import java.util.List;

public class Search {

    private String key;
    private int pageSize;
    private RandomAccessFile raf;
    private BplusTree tree;

    public Search(String key, int pageSize) throws IOException, ClassNotFoundException, ParseException {
        TableConfig.initTableInfo();
        this.key = key;
        this.pageSize = pageSize;
        raf = new RandomAccessFile(TableConfig.PAGENAME + TableConfig.POINT + String.valueOf (pageSize), "r");
        File file = new File (TableConfig.INDEXNAME);
        try (FileInputStream fis = new FileInputStream (file);
             ObjectInputStream ois = new ObjectInputStream (fis)) {
            tree = (BplusTree) ois.readObject ();
        } catch (Exception e) {
            throw e;
        }
    }

    public void search() throws IOException, ParseException {
        List<Index> indices = (List) tree.get (key);
        for (Index index : indices) {
            int pageId = index.getPageId ();
            int recordId = index.getRecordId ();
            byte[] recordByte = new byte[TableConfig.RECORDLENGTH];
            raf.seek ((long) (pageId * pageSize + recordId * TableConfig.RECORDLENGTH));
            raf.read(recordByte);
            String[] records = RecordUtil.parseRecord(recordByte);
            Record record = new Record (records, pageId + 1, recordId + 1);
            System.out.println("Page ID:" + record.getPageId () + ",Record ID:" + record.getRecordId ());
            System.out.println ("Record Value:" + record.toString ());
        }
    }


}
