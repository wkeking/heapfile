package heapfile.fields;

import heapfile.exception.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public interface Field<T> extends Serializable {
    //序列化到流
    void serialize(DataOutputStream dos) throws IOException;

    //反序列化流
    T parse(byte[] bytes) throws ParseException;

    //获取字段类型
    FieldType getType();

    //获取字段值
    T getValue();

    //设置字段值
    void setValue(T value);
}
