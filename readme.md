#heapfile数据库
##数据结构定义
- int：4bytes，整形，null时存储‘####’
- long：8bytes，长整形，null时存储‘########’
- char：nbytes，定长字符串，不够末位‘#’补全
- varchar：（m+4）bytes，不定长字符串，开头4bytes存储字符串实际长度m，后跟m个字符
- date：8bytes，日期类型，时间戳毫秒数，null时存储‘########’
- boolean：1byte，布尔类型，null时存储‘#’，false存储‘0’，true存储‘1’
