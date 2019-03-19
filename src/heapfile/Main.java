package heapfile;

import heapfile.config.DefaultConfig;

public class Main {

    public static void main(String[] args) {
        DefaultConfig.initTableInfo (null);
        System.out.println(DefaultConfig.tableInfo);
    }
}
