package heapfile.fields;

import heapfile.enums.FieldType;
import heapfile.exception.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class BooleanField implements Field<String>, Comparable<BooleanField> {
    private String value;
    private FieldType type;

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
        if (bytes.length != type.getLength ()) {
            throw new ParseException("Parse Error:BooleanBytesLength=" + bytes.length);
        }
        String s = new String (bytes);
        switch (s) {
            case "0":
                value = Boolean.FALSE.toString ();
                break;
            case "1":
                value = Boolean.TRUE.toString ();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass () != o.getClass ()) return false;
        BooleanField that = (BooleanField) o;
        return Objects.equals (value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash (value);
    }
}
