package heapfile.fields;

import heapfile.enums.FieldType;
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

    //获取字段长度
    int getLength();
}
