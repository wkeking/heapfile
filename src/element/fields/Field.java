package element.fields;

import exception.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public interface Field<T> extends Serializable {
    void serialize(DataOutputStream dos) throws IOException;

    T parse(byte[] bytes) throws ParseException;

    FieldType getType();

    T getValue();

    void setValue(T value);
}
