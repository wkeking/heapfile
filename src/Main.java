import config.TableConfig;
import element.records.Record;
import element.tree.Index;
import utils.RecordUtil;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

public class Main {
//    "1861901/29/2017 11:46:05 AM" "1861901/25/2017 11:46:05 AM" 10000
//    1861901/29/2017 09:20:12 AM
//    "1861901/25/2017 08:10:06 PM" "1861902/01/2017 08:10:06 PM" 10000
//    Page ID:438062,Record ID:18
//    Record Value:18619,02/01/2017 00:00:00 PM,02/01/2017 02:25:02 AM,8702,1952N,P/5 M-SUN 0:00-23:59 - No Park,City Square,528,COLLINS STREET,SWANSTON STREET,RUSSELL STREET,3,FALSE,FALSE

//    Page ID:515643,Record ID:29
//    Record Value:1861901/25/2017 00:46:05 PM
//    Page ID:533062,Record ID:3
//    Record Value:1861901/25/2017 00:00:00 PM
//    Page ID:99865,Record ID:7
//    Record Value:1861901/25/2017 00:42:20 PM
    public static void main(String[] args) {
        String query = "C:\\Users\\admin\\Desktop\\query.txt";
        String search = "C:\\Users\\admin\\Desktop\\search.txt";
        Set<String> querySet = read (query);
        Set<String> searchSet = read (search);
        querySet.forEach (s -> {
            boolean remove = searchSet.remove (s);
            if (! remove) {
                System.out.println(s);
            }
        });
        System.out.println("----------------");
        searchSet.forEach (s -> {
            System.out.println(s);
        });

//        String key = "1861901/25/2017 08:10:06 PM";
//        String value = "1861901/25/2017 00:46:05 PM";
//        int i = value.compareTo (key);
//        System.out.println(i);

//        getRecord (10000, "515642 29");

//        System.out.println("AM".compareTo ("PM"));
//        CompareUtil<String> util = new CompareUtil<> ();
//        int cmp1 = util.compare ("1861901/29/2017 09:20:12 AM", "1861901/25/2017 11:46:05 AM");
//        System.out.println(cmp1);
//        int cmp2 = util.compare ("1861901/29/2017 09:20:12 AM", "1861901/29/2017 11:46:05 AM");
//        System.out.println(cmp2);
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
