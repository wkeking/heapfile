package heapfile.fields;

import heapfile.config.FT;
import heapfile.enums.FieldType;

import java.util.HashMap;
import java.util.Map;

public class FieldFactory {
    private static final Map<String, Field> container = new HashMap();

    public static Field getField(String type, Object value, int defindLength) {
        switch (type) {
            case FT.INT:
                IntField intField = (IntField) container.get (type);
                if (intField == null){
                    intField = new IntField (FieldType.INT);
                    container.put (type, intField);
                }
                intField.setValue ((Integer) value);
                intField.setDefindLength (defindLength);
                return intField;
            case FT.LONG:
                LongField longField = (LongField) container.get (type);
                if (longField == null){
                    longField = new LongField (FieldType.LONG);
                    container.put (type, longField);
                }
                longField.setValue ((Long) value);
                longField.setDefindLength (defindLength);
                return longField;
            case FT.CHAR:
                CharField charField = (CharField) container.get (type);
                if (charField == null){
                    charField = new CharField (FieldType.CHAR);
                    container.put (type, charField);
                }
                charField.setValue ((String) value);
                charField.setDefindLength (defindLength);
                return charField;
            case FT.VARCHAR:
                VarcharField varcharField = (VarcharField) container.get (type);
                if (varcharField == null){
                    varcharField = new VarcharField (FieldType.VARCHAR);
                    container.put (type, varcharField);
                }
                varcharField.setValue ((String) value);
                varcharField.setDefindLength (defindLength);
                return varcharField;
            case FT.DATE:
                DateField dateField = (DateField) container.get (type);
                if (dateField == null){
                    dateField = new DateField (FieldType.DATE);
                    container.put (type, dateField);
                }
                dateField.setValue ((Long) value);
                dateField.setDefindLength (defindLength);
                return dateField;
            case FT.BOOLEAN:
                BooleanField booleanField = (BooleanField) container.get (type);
                if (booleanField == null){
                    booleanField = new BooleanField (FieldType.BOOLEAN);
                    container.put (type, booleanField);
                }
                booleanField.setValue ((String) value);
                booleanField.setDefindLength (defindLength);
                return booleanField;
        }
        return null;
    }
}
