import config.TableConfig;
import element.pages.Page;
import element.records.Record;
import operate.Load;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class dbquery {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("The parameter must have two");
            return;
        }
        TableConfig.KEYWORDS = args[0].trim ();
        if (TableConfig.KEYWORDS == null || TableConfig.KEYWORDS.isEmpty ()) {
            System.out.println("Keywords is required");
            return;
        }
        String size = args[1].trim ();
        if (size == null || size.isEmpty ()) {
            System.out.println("PageSize is required");
            return;
        }
        int pageSize = 0;
        try {
            pageSize = Integer.parseInt (size);
        } catch (Exception e) {
            System.out.println("The size of page need int type");
        }
        Load load = null;
        try {
            long start = System.currentTimeMillis ();
            load = new Load (pageSize);
            while (load.hasNext ()) {
                Page page = load.next ();
                load.query (page);
                List<Record> recordList = page.getList ();
                if (recordList.size () > 0) {
                    recordList.forEach (r -> {
                        System.out.println("Page ID:" + r.getPageId () + ",Record ID:" + r.getRecordId ());
                        System.out.println ("Record Value:" + r.toString ());
                    });
                }
            }
            long stop = System.currentTimeMillis ();
            System.out.println("The number of milliseconds to query the heap file is " + (stop - start) + "ms");
        } catch (IOException e) {
            e.printStackTrace ();
        } catch (ParseException e) {
            e.printStackTrace ();
        } finally {
            if (load != null) {
                try {
                    load.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        }
    }
}
