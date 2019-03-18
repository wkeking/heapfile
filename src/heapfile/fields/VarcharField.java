package heapfile.fields;

import heapfile.enums.FieldType;
import heapfile.exception.LengthException;
import heapfile.exception.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class VarcharField implements Field<String>, Comparable<VarcharField> {
    private String value;
    private FieldType type;
    private int defindLength;//字段定义长度

    public VarcharField(FieldType type) {
        this.type = type;
    }

    @Override
    public void serialize(DataOutputStream dos) throws IOException {
        if (value.length () > getDefindLength())
            throw new LengthException ("Length Overrun:" + value.length () + ">" + getDefindLength());
        int i = 0;
        if (value != null) i = value.length ();
        dos.writeInt (i);
        if (value != null) dos.writeBytes (value);
    }

    @Override
    public String parse(byte[] bytes) throws ParseException {
        if (bytes == null) return null;
        if (bytes.length > getDefindLength()) {
            throw new ParseException("Parse Error:VarcharBytesLength=" + bytes.length);
        }
        return new String (bytes);
    }

    @Override
    public FieldType getType() {
        return this.type;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value == null) return "";
        else return value;
    }

    @Override
    public int compareTo(VarcharField o) {
        return this.value.compareTo (o.getValue ());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        VarcharField that = (VarcharField) o;
        return Objects.equals (value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash (value);
    }

    public int getDefindLength() {
        return defindLength;
    }

    public void setDefindLength(int defindLength) {
        this.defindLength = defindLength;
    }
}
