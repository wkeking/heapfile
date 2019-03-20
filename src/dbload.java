import operate.Write;

public class dbload {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("The parameter must have three");
            return;
        }
        String pageSizeStr = null;
        String dataFile = null;
        for (int i = 0; i < args.length; i ++) {
            if ("-p".equals (args[i])) {
                pageSizeStr = args[i+1].trim ();
                dataFile = args[i+2].trim ();
                break;
            }
        }
        int pageSize = Integer.parseInt (pageSizeStr);
        Write write = new Write (pageSize, dataFile);
        write.write ();
    }
}
