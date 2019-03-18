package heapfile.fields;

import heapfile.enums.FieldType;
import heapfile.exception.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class IntField implements Field<Integer>, Comparable<IntField> {
    private Integer value;
    private FieldType type;
    private int defindLength;//字段定义长度
    private int realLength;//字段真实长度

    public IntField(FieldType type) {
        this.type = type;
    }

    @Override
    public void serialize(DataOutputStream dos) throws IOException {
        if (value == null) {
            StringBuilder sb = new StringBuilder ();
            for(int i = 0; i < getDefindLength (); i ++) {
                sb.append ("#");
            }
            dos.writeBytes (sb.toString ());
        }
        else dos.writeInt (value);
    }

    @Override
    public Integer parse(byte[] bytes) throws ParseException {
        if (bytes.length != getDefindLength ()) {
            throw new ParseException("Parse Error:IntBytesLength=" + bytes.length);
        }
        if ("#".equals ((char) bytes[0])) value = null;
        value = bytes[3] & 0xFF |
                (bytes[2] & 0xFF) << 8 |
                (bytes[1] & 0xFF) << 16 |
                (bytes[0] & 0xFF) << 24;
        return value;
    }

    @Override
    public FieldType getType() {
        return type;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Integer value) {
        this.value = value;
        setRealLength (type.getLength (0));
    }

    @Override
    public String toString() {
        if (value == null) return "";
        else return value.toString ();
    }

    @Override
    public int compareTo(IntField o) {
        return this.value.compareTo (o.getValue ());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        IntField intField = (IntField) o;
        return Objects.equals (value, intField.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash (value);
    }

    public void setDefindLength(int defindLength) {
        this.defindLength = defindLength;
    }

    public int getDefindLength() {
        return defindLength;
    }

    public int getRealLength() {
        return realLength;
    }

    public void setRealLength(int realLength) {
        this.realLength = realLength;
    }
}
