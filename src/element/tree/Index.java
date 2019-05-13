package element.tree;

import config.TableConfig;

import java.io.Serializable;

public class Index implements Serializable {
    private Long pageId;
    private Integer recordId;

    public Index(long pageId, int recordId) {
        this.pageId = pageId;
        this.recordId = recordId;
    }

    public Index(String serializ) {
        String[] split = serializ.split (TableConfig.NULL);
        pageId = Long.parseLong (split[0]);
        recordId = Integer.parseInt (split[1]);
    }

    public long getPageId() {
        return pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String serializ() {
        StringBuilder sb = new StringBuilder (Long.toString (pageId));
        sb.append (TableConfig.NULL);
        sb.append (Integer.toString (recordId));
        return sb.toString ();
    }

    @Override
    public String toString() {
        return "Index{" +
                "pageId=" + pageId +
                ", recordId=" + recordId +
                '}';
    }
}
