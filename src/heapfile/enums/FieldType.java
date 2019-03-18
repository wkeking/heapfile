package heapfile.enums;

import java.io.Serializable;

public enum FieldType implements Serializable {
    INT() {
        public int getLength(int n) {
            return 4;
        }
    },FLOAT() {
        public int getLength(int n) {
            return 8;
        }
    },CHAR() {
        public int getLength(int n) {
            return n;
        }
    },VARCHAR() {
        public int getLength(int n) {
            return n + 4;
        }
    },DATE() {
        public int getLength(int n) {
            return 8;
        }
    };

    public abstract int getLength(int n);
}
