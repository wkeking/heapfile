# heapfile数据库
## 一期说明
### 数据结构定义
- int：4bytes，整形，null时存储‘####’
- long：8bytes，长整形，null时存储‘########’
- char：nbytes，定长字符串，不够末位‘#’补全
- varchar：（m+4）bytes，不定长字符串，开头4bytes存储字符串实际长度m，后跟m个字符
- date：8bytes，日期类型，时间戳毫秒数，null时存储‘########’
- boolean：1byte，布尔类型，null时存储‘#’，false存储‘0’，true存储‘1’
数据类型抽象：element.fields.*
### 页结构定义
文件存储结构划分为指定的页大小，每页大小由命令传入。页结构为固定大小的n条记录和一个记录n数字的int在组成。记录的大小由TableConfig.java定义。记录不跨页存储，如果页大小减去n条记录的大小，再减去一个int的大小，剩余的空间不足以记录一条完整的记录，则该页的剩余空间存储空格。
页类型抽象：element.pages.Page.java
记录类型抽象：element.records.Record.java
### 表结构定义
表结构定义在TableConfig.java。根据提供的元数据字段名和自定义的字段类型加长度组成，加载该类时初始化表结构到Map容器存储。加载元数据时会根据元数据表头进行重新排序，保证存储的二进制数据字段顺序和元数据一致。
如果元数据字段发生改变或数据长度、类型发生改变，则可以对TableConfig.java的第36至50行进行修改对应。
### 写入二进制数据
加载元数据并写入二进制数据主类为Write.java。根据自定义的字段类型和长度写入流文件，一次写一页，根据元数据的大小自动分为n页。
写入命令为：java dbload -p pagesize 元数据路径
### 查询数据
查询二进制数据主类为Load.java。根据提供的字段串查询二进制文件，一次检索一页，查询到的数据打印到控制台。查找的数据是由DeviceId和ArrivalTime拼接而成。如果查找的数据改变或者拼接的格式发生改变，可以修改Load.java的第68行进行对应。
查询命令为：java dbquery 查找的字符串 pagesize
## 二期说明
### B+Tree
B+Tree由BTree引申而来，请参考教科书...
### 写入二进制命令
本期写入命令与一期的不同点是加入索引文件的写入。索引是数据结构为B+Tree。DeviceId和ArrivalTime组成的字符串为关键字，Index为值，Index对象包含pageId和recordId，指向二进制文件的页ID和记录ID，可以精确的定位到查询的记录。
由于本次加入了索引文件的写入。具体命令：
java dbload -p pagesize 元数据路径
### 遍历查询命令
本次遍历查询命令与一期相同：
java dbquery 查找的字符串 pagesize
### 索引查询命令
命令：
java dbsearch 查找的字符串 pagesize 
