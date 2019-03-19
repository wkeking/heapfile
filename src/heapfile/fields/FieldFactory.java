package heapfile.fields;

import heapfile.config.DefaultConfig;
import heapfile.utils.DateUtil;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class FieldFactory {
    private static final Map<String, Field> container = new HashMap();

    public static Field getField(String type, String value, int defindLength) throws ParseException {
        switch (type) {
            case DefaultConfig.INT:
                IntField intField = (IntField) container.get (type);
                if (intField == null){
                    intField = new IntField (FieldType.INT);
                    container.put (type, intField);
                }
                intField.setValue (Integer.parseInt (value));
                intField.setDefindLength (defindLength);
                return intField;
            case DefaultConfig.LONG:
                LongField longField = (LongField) container.get (type);
                if (longField == null){
                    longField = new LongField (FieldType.LONG);
                    container.put (type, longField);
                }
                longField.setValue (Long.parseLong (value));
                longField.setDefindLength (defindLength);
                return longField;
            case DefaultConfig.CHAR:
                CharField charField = (CharField) container.get (type);
                if (charField == null){
                    charField = new CharField (FieldType.CHAR);
                    container.put (type, charField);
                }
                charField.setValue (value);
                charField.setDefindLength (defindLength);
                return charField;
            case DefaultConfig.VARCHAR:
                VarcharField varcharField = (VarcharField) container.get (type);
                if (varcharField == null){
                    varcharField = new VarcharField (FieldType.VARCHAR);
                    container.put (type, varcharField);
                }
                varcharField.setValue (value);
                varcharField.setDefindLength (defindLength);
                return varcharField;
            case DefaultConfig.DATE:
                DateField dateField = (DateField) container.get (type);
                if (dateField == null){
                    dateField = new DateField (FieldType.DATE);
                    container.put (type, dateField);
                }
                dateField.setValue (DateUtil.strToLong (value));
                dateField.setDefindLength (defindLength);
                return dateField;
            case DefaultConfig.BOOLEAN:
                BooleanField booleanField = (BooleanField) container.get (type);
                if (booleanField == null){
                    booleanField = new BooleanField (FieldType.BOOLEAN);
                    container.put (type, booleanField);
                }
                booleanField.setValue (value);
                booleanField.setDefindLength (defindLength);
                return booleanField;
        }
        return null;
    }
}
