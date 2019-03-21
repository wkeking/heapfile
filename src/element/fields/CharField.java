package element.fields;

import exception.LengthException;
import exception.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 数据字段不可变长字符类型
 */
public class CharField implements Field<String>, Comparable<CharField> {
    private String value;
    private FieldType type;
    private int defindLength;
    private int realLength;

    public CharField(FieldType type) {
        this.type = type;
    }

    @Override
    public void serialize(DataOutputStream dos) throws IOException, LengthException {
        if (value == null) {
            StringBuilder sb = new StringBuilder ();
            for (int i = 0; i < getDefindLength (); i++) {
                sb.append ("#");
            }
            dos.writeBytes (sb.toString ());
        } else {
            if (value.length () > getDefindLength ())
                throw new LengthException ("Length Overrun:" + value.length () + ">" + getDefindLength ());
            StringBuilder sb = new StringBuilder (value);
            if (value.length () < getDefindLength ()) {
                for (int i = 0; i < (getDefindLength () - value.length ()); i++) {
                    sb.append ("#");
                }
            }
            dos.writeBytes (sb.toString ());
        }
    }

    @Override
    public String parse(byte[] bytes) throws ParseException {
        if (bytes.length != getDefindLength ()) {
            throw new ParseException ("Parse Error:CharBytesLength=" + bytes.length);
        }
        String s = new String (bytes);
        int i = s.indexOf ("#");
        if (i == -1) value = null;
        else value = s.substring (0, i);
        return value;
    }

    @Override
    public FieldType getType() {
        return type;
    }

    @Override
    public String getValue() {
        return value;
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
    public int compareTo(CharField o) {
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
