import config.TableConfig;
import element.records.Record;
import element.tree.Index;
import utils.RecordUtil;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        String query = "query.txt";
        String search = "search.txt";
        Set<String> querySet = read (query);
        System.out.println("query.txt has records number is " + querySet.size ());
        Set<String> searchSet = read (search);
        System.out.println("search.txt has records number is " + searchSet.size ());
        querySet.forEach (s -> {
            boolean remove = searchSet.remove (s);
            if (! remove) {
                System.out.println("Not included in the search list:" + s);
            }
        });
        System.out.println("---------------------------------------------------");
        searchSet.forEach (s -> {
            System.out.println("Not included in the query list:" + s);
        });
    }

    public static Set<String> read(String path) {
        Set<String> tree = new TreeSet<> ();
        File file = new File (path);
        try (FileReader fr = new FileReader (file);
             BufferedReader br = new BufferedReader(fr, TableConfig.BUFFERSIZE);
             LineNumberReader lnr = new LineNumberReader (br)) {
            String line = null;
            while ((line = lnr.readLine ()) != null) {
                if (lnr.getLineNumber () % 2 != 0) {
                    String index = parse (line);
                    tree.add (index);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return tree;
    }

    private static String parse(String line) {
        String[] split = line.split (TableConfig.SEPARATOR);
        int p = split[0].indexOf (":");
        String pageId = split[0].substring (p + 1);
        int r = split[1].indexOf (":");
        String recordId = split[1].substring (r + 1);
        return pageId + TableConfig.NULL + recordId;
    }

    private static void getRecord(Integer pageSize, String i) {
        TableConfig.initTableInfo();
        try (RandomAccessFile raf = new RandomAccessFile (TableConfig.PAGENAME + TableConfig.POINT + String.valueOf (pageSize), "r")) {
            Index index = new Index (i);
            long pageId = index.getPageId () - 1;
            int recordId = index.getRecordId () - 1;
            byte[] recordByte = new byte[TableConfig.RECORDLENGTH];
            raf.seek (pageId * pageSize + recordId * TableConfig.RECORDLENGTH);
            raf.read(recordByte);
            String[] records = RecordUtil.parseRecord(recordByte);
            Record record = new Record (records, pageId + 1, recordId + 1);
            System.out.println("Page ID:" + record.getPageId () + ",Record ID:" + record.getRecordId ());
            System.out.println ("Record Value:" + record.toString ());
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}
