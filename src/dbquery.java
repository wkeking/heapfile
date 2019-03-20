import operate.Load;

public class dbquery {
    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        String path = "heap.4096";
        Load load = new Load(4096);
        load.read();
        long l1 = System.currentTimeMillis();
        System.out.println(l1 - l);
    }
}
