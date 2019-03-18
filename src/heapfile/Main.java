package heapfile;

import heapfile.config.FT;
import heapfile.enums.FieldType;
import heapfile.factory.FieldFactory;
import heapfile.fields.Field;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        Date date = new Date ();
        SimpleDateFormat format = new SimpleDateFormat ("dd/MM/yyyy KK:mm:ss aa", Locale.ENGLISH);
        String format1 = format.format (date);
        System.out.println(format1);
    }
}
