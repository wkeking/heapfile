package element.fields;

import exception.ParseException;
import utils.TypeUtil;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 数据字段int类型
 */
public class IntField implements Field<Integer>, Comparable<IntField> {
    private Integer value;
    private FieldType type;
    private int defindLength;
    private int realLength;

    public IntField(FieldType type) {
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
        } else dos.writeInt (value);
    }

    @Override
    public Integer parse(byte[] bytes) throws ParseException {
        if (bytes.length != getDefindLength ()) {
            throw new ParseException ("Parse Error:IntBytesLength=" + bytes.length);
        }
        if ("#".equals ((char) bytes[0])) value = null;
        value = TypeUtil.bytesToInt(bytes);
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

    public void setDefindLength(int defindLength) {
        this.defindLength = defindLength;
    }

    public int getDefindLength() {
        return defindLength;
    }

    public void setRealLength(int realLength) {
        this.realLength = realLength;
    }
}
