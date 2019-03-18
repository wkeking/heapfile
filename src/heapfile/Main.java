package heapfile;

import heapfile.config.FT;
import heapfile.enums.FieldType;
import heapfile.factory.FieldFactory;
import heapfile.fields.Field;

public class Main {

    public static void main(String[] args) {
        Field field = FieldFactory.getField (FT.INT, 123, FieldType.INT.getLength (0));
        String string = field.toString ();
        System.out.println(string);

    }
}
