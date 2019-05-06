package element.tree;

import java.io.Serializable;

public class Index implements Serializable {
    private int pageId;
    private int recordId;

    public Index(int pageId, int recordId) {
        this.pageId = pageId;
        this.recordId = recordId;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    @Override
    public String toString() {
        return "Index{" +
                "pageId=" + pageId +
                ", recordId=" + recordId +
                '}';
    }
}
