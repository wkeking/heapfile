package element.fields;

import config.TableConfig;
import utils.DateUtil;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 字段工厂，享元模式
 */
public class FieldFactory {
    private static final Map<String, Field> container = new HashMap<>();

    /**
     * 字段对象复用工厂
     * @param type 字段类型
     * @param value 字段值
     * @param defindLength 表结构定义字节长度
     * @return
     * @throws ParseException
     */
    public static Field getField(String type, String value, int defindLength) throws ParseException {
        switch (type) {
            case TableConfig.INT:
                IntField intField = (IntField) container.get (type);
                if (intField == null){
                    intField = new IntField (FieldType.INT);
                    container.put (type, intField);
                }
                if (value != null) intField.setValue (Integer.parseInt (value));
                intField.setDefindLength (defindLength);
                return intField;
            case TableConfig.LONG:
                LongField longField = (LongField) container.get (type);
                if (longField == null){
                    longField = new LongField (FieldType.LONG);
                    container.put (type, longField);
                }
                if (value != null)longField.setValue (Long.parseLong (value));
                longField.setDefindLength (defindLength);
                return longField;
            case TableConfig.CHAR:
                CharField charField = (CharField) container.get (type);
                if (charField == null){
                    charField = new CharField (FieldType.CHAR);
                    container.put (type, charField);
                }
                if (value != null) charField.setValue (value);
                charField.setDefindLength (defindLength);
                return charField;
            case TableConfig.VARCHAR:
                VarcharField varcharField = (VarcharField) container.get (type);
                if (varcharField == null){
                    varcharField = new VarcharField (FieldType.VARCHAR);
                    container.put (type, varcharField);
                }
                if (value != null) varcharField.setValue (value);
                varcharField.setDefindLength (defindLength);
                return varcharField;
            case TableConfig.DATE:
                DateField dateField = (DateField) container.get (type);
                if (dateField == null){
                    dateField = new DateField (FieldType.DATE);
                    container.put (type, dateField);
                }
                if (value != null) dateField.setValue (DateUtil.strToLong (value));
                dateField.setDefindLength (defindLength);
                return dateField;
            case TableConfig.BOOLEAN:
                BooleanField booleanField = (BooleanField) container.get (type);
                if (booleanField == null){
                    booleanField = new BooleanField (FieldType.BOOLEAN);
                    container.put (type, booleanField);
                }
                if (value != null) booleanField.setValue (value);
                booleanField.setDefindLength (defindLength);
                return booleanField;
            default:
                return null;
        }
    }

    public static Field getField(String type, int defindLength) throws ParseException {
        return getField(type, null, defindLength);
    }
}
