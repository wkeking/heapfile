package element.tree;

import java.io.Serializable;

public interface BTree extends Serializable {
    Object get(Comparable key);//查询

    void remove(Comparable key);//移除

    void insert(Comparable key, Object obj);//插入
}
