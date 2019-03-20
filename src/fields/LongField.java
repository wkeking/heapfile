package fields;

import exception.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;

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
        value = (0xff00000000000000L & ((long) bytes[0] << 56)) |
                (0x00ff000000000000L & ((long) bytes[1] << 48)) |
                (0x0000ff0000000000L & ((long) bytes[2] << 40)) |
                (0x000000ff00000000L & ((long) bytes[3] << 32)) |
                (0x00000000ff000000L & ((long) bytes[4] << 24)) |
                (0x0000000000ff0000L & ((long) bytes[5] << 16)) |
                (0x000000000000ff00L & ((long) bytes[6] << 8)) |
                (0x00000000000000ffL & (long) bytes[7]);
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
