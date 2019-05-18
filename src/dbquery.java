import config.Condition;
import config.TableConfig;
import element.pages.Page;
import element.records.Record;
import operate.Load;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class dbquery {
    public static void main(String[] args) {
        if (args.length != 2 && args.length != 3) {
            System.out.println("The parameter must have two(equality) or three(range)");
            return;
        }
        Condition condition = null;
        String size = null;
        if (args.length == 2) {
            condition = Condition.EQUALITY;
            TableConfig.KEYWORDS = args[0].trim ();
            if (TableConfig.KEYWORDS == null || TableConfig.KEYWORDS.isEmpty ()) {
                System.out.println("Keywords is required");
                return;
            }
            size = args[1].trim ();
            if (size == null || size.isEmpty ()) {
                System.out.println("PageSize is required");
                return;
            }

        } else if (args.length == 3) {
            condition = Condition.RANGE;
            String key1 = args[0].trim ();
            String key2 = args[1].trim ();
            if (key1 == null || key1.isEmpty () || key2 == null || key2.isEmpty ()) {
                System.out.println("Keywords is required");
                return;
            }
            int i = key1.compareTo (key2);
            if (i == 0) {
                System.out.println("Two keywords are equal");
                return;
            }
            if (i > 0) {
                TableConfig.RANGS_KEYS[0] = key2;
                TableConfig.RANGS_KEYS[1] = key1;
            } else {
                TableConfig.RANGS_KEYS[0] = key1;
                TableConfig.RANGS_KEYS[1] = key2;
            }

            size = args[2].trim ();
            if (size == null || size.isEmpty ()) {
                System.out.println("PageSize is required");
                return;
            }
        }

        int pageSize = 0;
        try {
            pageSize = Integer.parseInt (size);
        } catch (Exception e) {
            System.out.println("The size of page need int type");
        }
        Load load = null;
        try {
            long total = 0L;
            long start = System.currentTimeMillis ();
            load = new Load (pageSize);
            while (load.hasNext ()) {
                Page page = load.next ();
                load.query (page, condition);
                List<Record> recordList = page.getList ();
                total += recordList.size ();
                if (recordList.size () > 0) {
                    recordList.forEach (r -> {
                        System.out.println("Page ID:" + r.getPageId () + ",Record ID:" + r.getRecordId ());
                        System.out.println ("Record Value:" + r.toString ());
                    });
                }
            }
            long stop = System.currentTimeMillis ();
            System.out.println("The number of amount to query the heap file is " + total);
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
