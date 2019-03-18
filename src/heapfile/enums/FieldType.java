package heapfile.enums;

import java.io.Serializable;

public enum FieldType implements Serializable {
    INT() {
        public int getLength(int i) {
            return 4;
        }
    },BOOLEAN() {
        public int getLength(int i) {
            return 1;
        }
    },CHAR() {
        public int getLength(int i) {
            return i;
        }
    },VARCHAR() {
        public int getLength(int i) {
            return i + 4;
        }
    },DATE() {
        public int getLength(int i) {
            return 8;
        }
    };

    public abstract int getLength(int i);
}
