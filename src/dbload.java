import operate.Write;

public class dbload {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("The parameter must have three");
            return;
        }
        String pageSizeStr = args[1].trim ();
        if (pageSizeStr == null || pageSizeStr.isEmpty ()) {
            System.out.println("PageSize is required");
            return;
        }
        String dataFile = args[2].trim ();
        if (dataFile == null || dataFile.isEmpty ()) {
            System.out.println("DataFile is required");
            return;
        }
        int pageSize = 0;
        try {
            pageSize = Integer.parseInt (pageSizeStr);
        } catch (Exception e) {
            System.out.println("The size of page need int type");
        }
        Write write = new Write (pageSize, dataFile);
        long start = System.currentTimeMillis ();
        write.write ();
        long stop = System.currentTimeMillis ();
        System.out.println ("The number of milliseconds to create the heap file is " + (stop - start) + "ms");
    }
}
