package element.records;

import config.TableConfig;

/**
 * 数据文件单条记录
 */
public class Record {
    private final String[] fields;
    private final int size;
    private long pageId;
    private int recordId;

    public Record(String[] fields, long pageId, int recordId) {
        this.size = fields.length;
        this.fields = fields;
        this.pageId = pageId;
        this.recordId = recordId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder (fields[0]);
        for (int i = 1; i < size; i ++) {
            sb.append (TableConfig.SEPARATOR);
            sb.append (fields[i]);
        }
        return sb.toString ();
    }

    public int size() {
        return size;
    }

    public String[] getFields() {
        return fields;
    }

    public long getPageId() {
        return pageId;
    }

    public int getRecordId() {
        return recordId;
    }
}
