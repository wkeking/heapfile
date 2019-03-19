package heapfile;

import heapfile.operate.Load;

public class Main {

    public static void main(String[] args) {
        String s = "heapfile\\test.csv";
        Load load = new Load (4096, s);
        load.load ();
    }
}
