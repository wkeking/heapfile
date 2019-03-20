package operate;

import config.TableConfig;
import element.fields.FieldType;
import element.pages.Page;
import element.records.Record;
import utils.RecordUtil;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;

public class Load {
    private static int pageNum;
    private static int pagePoint;

    private int pageSize;
    private String dataFilePath;
    private RandomAccessFile raf;

    public Load(int pageSize) throws IOException {
        TableConfig.initTableInfo();
        this.pageSize = pageSize;
        this.dataFilePath = TableConfig.PAGENAME + TableConfig.POINT + String.valueOf(pageSize);
        raf = new RandomAccessFile(dataFilePath, "r");
        pageNum = (int) raf.length() / pageSize;
        pagePoint = 0;
    }

    public boolean hasNext() {
        return pagePoint < pageNum;
    }

    public Page next() throws IOException {
        Page page = nextPage();
        pagePoint++;
        return page;
    }

    public void close() throws IOException {
        if (raf != null) {
            raf.close();
        }
    }

    private Page nextPage() throws IOException {
        long beginIndex = pagePoint * pageSize;
        long numIndex = beginIndex + pageSize - FieldType.INT.getLength(0);
        raf.seek(numIndex);
        byte[] numBytes = new byte[4];
        raf.readFully(numBytes);
        int recordNum = numBytes[3] & 0xFF |
                (numBytes[2] & 0xFF) << 8 |
                (numBytes[1] & 0xFF) << 16 |
                (numBytes[0] & 0xFF) << 24;
        return new Page(pagePoint + 1, recordNum);
    }

    public void query(Page page) throws IOException, ParseException {
        byte[] recordByte = new byte[TableConfig.RECORDLENGTH];
        raf.seek((long) (page.getPageId() - 1) * pageSize);
        for (int i = 0; i < page.getRecordNum(); i++) {
            raf.read(recordByte);
            String[] records = RecordUtil.parseRecord(recordByte);
            Record record = new Record(records, page.getPageId(), i + 1);
            String result = record.getFields()[0] + record.getFields()[1];
            if (TableConfig.KEYWORDS.equals(result))
                page.getList().add(record);
        }
    }
}
