package heapfile.enums;

import java.io.Serializable;

public enum FieldType implements Serializable {
    INT() {
        public int getLength() {
            return 4;
        }
    },BOOLEAN() {
        public int getLength() {
            return 1;
        }
    },CHAR() {
        public int getLength() {
            return 0;
        }
    },VARCHAR() {
        public int getLength() {
            return 0;
        }
    },DATE() {
        public int getLength() {
            return 8;
        }
    };

    public abstract int getLength();
}
