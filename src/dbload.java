import operate.Write;

public class dbload {

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new RuntimeException ("Parameter error");
        }
        String pageSizeStr = null;
        String dataFile = null;
        for (int i = 0; i < args.length; i ++) {
            if ("-p".equals (args[i])) {
                pageSizeStr = args[i+1];
                dataFile = args[i+2];
                break;
            }
        }
        int pageSize = Integer.parseInt (pageSizeStr);
        Write write = new Write (pageSize, dataFile);
        write.write ();
    }
}
