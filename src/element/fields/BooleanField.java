package element.fields;

import exception.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 数据字段布尔类型
 */
public class BooleanField implements Field<String>, Comparable<BooleanField> {
    private String value;
    private FieldType type;
    private int defindLength;
    private int realLength;

    public BooleanField(FieldType type) {
        this.type = type;
    }

    @Override
    public void serialize(DataOutputStream dos) throws IOException {
        if (value == null) {
            dos.writeBytes ("#");
        } else {
            String s;
            if (Boolean.TRUE.toString ().equals (value.toLowerCase ())) s = "1";
            else s = "0";
            dos.writeBytes (s);
        }
    }

    @Override
    public String parse(byte[] bytes) throws ParseException {
        if (bytes.length != getDefindLength ()) {
            throw new ParseException ("Parse Error:BooleanBytesLength=" + bytes.length);
        }
        String s = new String (bytes);
        switch (s) {
            case "0":
                value = Boolean.FALSE.toString ().toUpperCase ();
                break;
            case "1":
                value = Boolean.TRUE.toString ().toUpperCase ();
                break;
            case "#":
                value = null;
                break;
        }
        return value;
    }

    @Override
    public FieldType getType() {
        return this.type;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
        setRealLength (type.getLength (0));
    }

    @Override
    public String toString() {
        if (value == null) return "";
        else return value;
    }

    @Override
    public int compareTo(BooleanField o) {
        return this.getValue ().compareTo (o.getValue ());
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
