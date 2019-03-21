package element.fields;

import exception.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * 字段接口
 * @param <T> java基本类型
 */
public interface Field<T> extends Serializable {
    //序列化字段
    void serialize(DataOutputStream dos) throws IOException;
    //反序列化字段
    T parse(byte[] bytes) throws ParseException;

    FieldType getType();

    T getValue();

    void setValue(T value);
}
