package element.fields;

import exception.ParseException;
import utils.TypeUtil;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 数据字段long类型
 */
public class LongField implements Field<Long>, Comparable<LongField> {
    private Long value;
    private FieldType type;
    private int defindLength;
    private int realLength;

    public LongField(FieldType type) {
        this.type = type;
    }

    @Override
    public void serialize(DataOutputStream dos) throws IOException {
        if (value == null) {
            StringBuilder sb = new StringBuilder ();
            for (int i = 0; i < getDefindLength (); i++) {
                sb.append ("#");
            }
            dos.writeBytes (sb.toString ());
        }
        dos.writeLong (value);
    }

    @Override
    public Long parse(byte[] bytes) throws ParseException {
        if (bytes.length != getDefindLength ()) {
            throw new ParseException ("Parse Error:LongBytesLength=" + bytes.length);
        }
        if ("#".equals ((char) bytes[0])) return null;
        value = TypeUtil.bytesToLong (bytes);
        return value;
    }

    @Override
    public FieldType getType() {
        return type;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public void setValue(Long value) {
        this.value = value;
        setRealLength (type.getLength (0));
    }

    @Override
    public String toString() {
        if (value == null) return "";
        return value.toString ();
    }

    @Override
    public int compareTo(LongField o) {
        return this.value.compareTo (o.getValue ());
    }

    public int getDefindLength() {
        return defindLength;
    }

    public void setDefindLength(int defindLength) {
        this.defindLength = defindLength;
    }

    public void setRealLength(int realLength) {
        this.realLength = realLength;
    }
}
