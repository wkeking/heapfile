import config.TableConfig;
import operate.Search;

import java.io.IOException;
import java.text.ParseException;

public class dbsearch {
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
        try {
            long start = System.currentTimeMillis ();
            Search search = new Search (TableConfig.KEYWORDS, pageSize);
            search.search ();
            long stop = System.currentTimeMillis ();
            System.out.println("The number of milliseconds to query the heap file is " + (stop - start) + "ms");
        } catch (IOException e) {
            e.printStackTrace ();
        } catch (ClassNotFoundException e) {
            e.printStackTrace ();
        } catch (ParseException e) {
            e.printStackTrace ();
        }
    }
}
