package element.fields;

import java.io.Serializable;

/**
 * 数据文件字段类型
 */
public enum FieldType implements Serializable {
    INT ("int") {
        public int getLength(int i) {
            return 4;
        }
    }, LONG ("long") {
        public int getLength(int i) {
            return 8;
        }
    }, BOOLEAN ("boolean") {
        public int getLength(int i) {
            return 1;
        }
    }, CHAR ("char") {
        public int getLength(int i) {
            return i;
        }
    }, VARCHAR ("varchar") {
        public int getLength(int i) {
            return i + 4;
        }
    }, DATE ("date") {
        public int getLength(int i) {
            return 8;
        }
    };

    private String code;
    FieldType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public abstract int getLength(int i);
}
