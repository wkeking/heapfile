package utils;

import config.TableConfig;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteUtil {
    private static BufferedWriter bw;
    private static long number = 0L;
    private static String line;

    public static void write(String data) {
        try {
            bw.write (data);
            bw.write (line);
            number ++;
            if (number == 100) {
                bw.flush ();
                number = 0L;
            }
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public static void close() {
        try {
            bw.flush ();
            bw.close ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public static void setFile(String path) {
        line = System.getProperty ("line.separator");
        try {
            FileWriter fw = new FileWriter (path);
            bw = new BufferedWriter (fw, TableConfig.BUFFERSIZE);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}
