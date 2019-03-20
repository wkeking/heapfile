package element.pages;

import element.records.Record;

import java.util.ArrayList;
import java.util.List;

public class Page {
    private final List<Record> list;
    private final int pageId;
    private int recordNum;

    public Page(int pageId, int recordNum) {
        this.pageId = pageId;
        this.recordNum = recordNum;
        this.list = new ArrayList<> ();
    }

    public int getPageId() {
        return this.pageId;
    }

    public int getRecordNum() {
        return this.recordNum;
    }

    public List<Record> getList() {
        return this.list;
    }
}
