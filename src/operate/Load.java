package operate;

import config.TableConfig;
import element.fields.FieldType;
import element.pages.Page;
import element.records.Record;
import utils.RecordUtil;
import utils.TypeUtil;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;

public class Load {
    private static long pageNum;//加载的数据流文件总页数
    private static long pagePoint;//当前需要解析的页指针

    private int pageSize;
    private String dataFilePath;
    private RandomAccessFile raf;

    public Load(int pageSize) throws IOException {
        TableConfig.initTableInfo();
        this.pageSize = pageSize;
        this.dataFilePath = TableConfig.PAGENAME + TableConfig.POINT + String.valueOf (pageSize);
        raf = new RandomAccessFile(dataFilePath, "r");
        pageNum = raf.length () / pageSize;
        pagePoint = 0;
    }

    //加载的数据流文件是否有下一页
    public boolean hasNext() {
        return pagePoint < pageNum;
    }

    //获取数据流文件下一页
    public Page next() throws IOException {
        Page page = nextPage ();
        pagePoint ++;
        return page;
    }

    //关闭流
    public void close() throws IOException {
        if (raf != null) {
            raf.close ();
        }
    }

    private Page nextPage() throws IOException {
        long beginIndex = pagePoint * pageSize;
        long numIndex = beginIndex + pageSize - FieldType.INT.getLength(0);
        raf.seek(numIndex);
        byte[] numBytes = new byte[4];
        raf.readFully(numBytes);//当前页记录总条数
        int recordNum = TypeUtil.bytesToInt (numBytes);
        return new Page ( pagePoint + 1, recordNum);
    }

    //对传入的页进行解析，查找指定的记录
    public void query(Page page) throws IOException, ParseException {
        byte[] recordByte = new byte[TableConfig.RECORDLENGTH];
        raf.seek ((page.getPageId () - 1) * pageSize);
        for (int i = 0; i < page.getRecordNum (); i++) {
            raf.read(recordByte);
            String[] records = RecordUtil.parseRecord(recordByte);
            Record record = new Record (records, page.getPageId (), i + 1);
            String result = record.getFields ()[0] + record.getFields ()[1];
            if (TableConfig.KEYWORDS.equals (result))
                page.getList ().add (record);
        }
    }
}
