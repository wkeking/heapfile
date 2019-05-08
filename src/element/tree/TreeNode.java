package element.tree;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class TreeNode implements Serializable {

    private boolean isLeaf;//是否为叶子节点

    private boolean isRoot;//是否为根节点

    private TreeNode parent;//父节点

    private TreeNode previous;//叶节点的前节点

    private TreeNode next;//叶节点的后节点

    private List<Entry<Comparable, Object>> entries;//节点的关键字

    private List<TreeNode> children;//子节点

    public TreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;
        entries = new ArrayList<Entry<Comparable, Object>> ();

        if (!isLeaf) {
            children = new ArrayList<TreeNode> ();
        }
    }

    public TreeNode(boolean isLeaf, boolean isRoot) {
        this (isLeaf);
        this.isRoot = isRoot;
    }

    public Object get(Comparable key) {

        if (isLeaf) {//如果是叶子节点
            for (Entry<Comparable, Object> entry : entries) {
                if (entry.getKey ().compareTo (key) == 0) {
                    //返回找到的对象
                    return entry.getValue ();
                }
            }
            //未找到所要查询的对象
            return null;
        } else {//如果不是叶子节点
            //如果key小于等于节点最左边的key，沿第一个子节点继续搜索
            if (key.compareTo (entries.get (0).getKey ()) <= 0) {
                return children.get (0).get (key);
                //如果key大于节点最右边的key，沿最后一个子节点继续搜索
            } else if (key.compareTo (entries.get (entries.size () - 1).getKey ()) >= 0) {
                return children.get (children.size () - 1).get (key);
                //否则沿比key大的前一个子节点继续搜索
            } else {
                for (int i = 0; i < entries.size (); i++) {
                    if (entries.get (i).getKey ().compareTo (key) <= 0 && entries.get (i + 1).getKey ().compareTo (key) > 0) {
                        return children.get (i).get (key);
                    }
                }
            }
        }
        return null;
    }

    public void insert(Comparable key, Object obj, BPlusTree tree) {

        if (isLeaf) {//如果是叶子节点
            //不需要分裂，直接插入或更新
            if (contains (key) || entries.size () < tree.getOrder ()) {
                insert (key, obj);
                if (parent != null) {
                    //更新父节点
                    parent.updateAfterInsert (tree);
                }
            } else {//需要分裂
                //分裂成左右两个节点
                TreeNode left = new TreeNode (true);
                TreeNode right = new TreeNode (true);
                //设置链接
                if (previous != null) {
                    previous.setNext (left);
                    left.setPrevious (previous);
                }
                if (next != null) {
                    next.setPrevious (right);
                    right.setNext (next);
                }
                if (previous == null) {
                    tree.setHead (left);
                }

                left.setNext (right);
                right.setPrevious (left);
                previous = null;
                next = null;

                //左右两个节点关键字长度
                int leftSize = (tree.getOrder () + 1) / 2 + (tree.getOrder () + 1) % 2;
                int rightSize = (tree.getOrder () + 1) / 2;
                //复制原节点关键字到分裂出来的新节点
                insert (key, obj);
                for (int i = 0; i < leftSize; i++) {
                    left.getEntries ().add (entries.get (i));
                }
                for (int i = 0; i < rightSize; i++) {
                    right.getEntries ().add (entries.get (leftSize + i));
                }

                siteNode (tree, left, right);
            }
        } else {//如果不是叶子节点
            //如果key小于等于节点最左边的key，沿第一个子节点继续搜索
            if (key.compareTo (entries.get (0).getKey ()) <= 0) {
                children.get (0).insert (key, obj, tree);
                //如果key大于节点最右边的key，沿最后一个子节点继续搜索
            } else if (key.compareTo (entries.get (entries.size () - 1).getKey ()) >= 0) {
                children.get (children.size () - 1).insert (key, obj, tree);
                //否则沿比key大的前一个子节点继续搜索
            } else {
                for (int i = 0; i < entries.size (); i++) {
                    if (entries.get (i).getKey ().compareTo (key) <= 0 && entries.get (i + 1).getKey ().compareTo (key) > 0) {
                        children.get (i).insert (key, obj, tree);
                        break;
                    }
                }
            }
        }
    }

    //插入节点后中间节点的更新
    protected void updateAfterInsert(BPlusTree tree) {
        adjust (this, tree);

        //如果子节点数超出阶数，则需要分裂该节点
        if (children.size () > tree.getOrder ()) {
            //分裂成左右两个节点
            TreeNode left = new TreeNode (false);
            TreeNode right = new TreeNode (false);
            //左右两个节点关键字长度
            int leftSize = (tree.getOrder () + 1) / 2 + (tree.getOrder () + 1) % 2;
            int rightSize = (tree.getOrder () + 1) / 2;
            //复制子节点到分裂出来的新节点，并更新关键字
            for (int i = 0; i < leftSize; i++) {
                left.getChildren ().add (children.get (i));
                left.getEntries ().add (new SimpleEntry (children.get (i).getEntries ().get (0).getKey (), null));
                children.get (i).setParent (left);
            }
            for (int i = 0; i < rightSize; i++) {
                right.getChildren ().add (children.get (leftSize + i));
                right.getEntries ().add (new SimpleEntry (children.get (leftSize + i).getEntries ().get (0).getKey (), null));
                children.get (leftSize + i).setParent (right);
            }

            siteNode (tree, left, right);
        }
    }

    private void siteNode(BPlusTree tree, TreeNode left, TreeNode right) {
        if (parent != null) {//如果不是根节点
            //调整父子节点关系
            int index = parent.getChildren ().indexOf (this);
            parent.getChildren ().remove (this);
            left.setParent (parent);
            right.setParent (parent);
            parent.getChildren ().add (index, left);
            parent.getChildren ().add (index + 1, right);
            setEntries (null);
            setChildren (null);

            //父节点更新关键字
            parent.updateAfterInsert (tree);
            setParent (null);
        } else {//如果是根节点
            isRoot = false;
            TreeNode parent = new TreeNode (false, true);
            tree.setRoot (parent);
            left.setParent (parent);
            right.setParent (parent);
            parent.getChildren ().add (left);
            parent.getChildren ().add (right);
            setEntries (null);
            setChildren (null);

            //更新根节点
            parent.updateAfterInsert (tree);
        }
    }

    //调整节点关键字
    private void adjust(TreeNode treeNode, BPlusTree tree) {
        // 如果关键字个数与子节点个数相同
        if (treeNode.getEntries ().size () == treeNode.getChildren ().size ()) {
            for (int i = 0; i < treeNode.getEntries ().size (); i++) {
                Comparable key = treeNode.getChildren ().get (i).getEntries ().get (0).getKey ();
                if (treeNode.getEntries ().get (i).getKey ().compareTo (key) != 0) {
                    treeNode.getEntries ().remove (i);
                    treeNode.getEntries ().add (i, new SimpleEntry (key, null));
                    if (!treeNode.isRoot ()) {
                        adjust (treeNode.getParent (), tree);
                    }
                }
            }
            // 如果子节点数不等于关键字个数但仍大于M / 2并且小于M，并且大于2
        } else if (treeNode.isRoot () && treeNode.getChildren ().size () >= 2
                || treeNode.getChildren ().size () >= tree.getOrder () / 2
                && treeNode.getChildren ().size () <= tree.getOrder ()
                && treeNode.getChildren ().size () >= 2) {
            treeNode.getEntries ().clear ();
            for (int i = 0; i < treeNode.getChildren ().size (); i++) {
                Comparable key = treeNode.getChildren ().get (i).getEntries ().get (0).getKey ();
                treeNode.getEntries ().add (new SimpleEntry (key, null));
                if (!treeNode.isRoot ()) {
                    adjust (treeNode.getParent (), tree);
                }
            }
        }
    }

    //判断当前节点是否包含该关键字
    private boolean contains(Comparable key) {
        for (Entry<Comparable, Object> entry : entries) {
            if (entry.getKey ().compareTo (key) == 0) {
                return true;
            }
        }
        return false;
    }

    //插入到当前节点的关键字中
    protected void insert(Comparable key, Object obj) {
        Entry<Comparable, Object> entry = new SimpleEntry<Comparable, Object> (key, obj);
        //如果关键字列表长度为0，则直接插入
        if (entries.size () == 0) {
            entries.add (entry);
            return;
        }
        //否则遍历列表
        for (int i = 0; i < entries.size (); i++) {
            //如果该关键字键值已存在，则更新
            if (entries.get (i).getKey ().compareTo (key) == 0) {
                //entries.get (i).setValue (obj);
                List<Index> indices = (List<Index>) entries.get (i).getValue ();
                indices.addAll ((List<Index>) obj);
                entries.get (i).setValue (indices);
                return;
                //否则插入
            } else if (entries.get (i).getKey ().compareTo (key) > 0) {
                //插入到链首
                if (i == 0) {
                    entries.add (0, entry);
                    return;
                    //插入到中间
                } else {
                    entries.add (i, entry);
                    return;
                }
            }
        }
        //插入到末尾
        entries.add (entries.size (), entry);
    }

    public TreeNode getPrevious() {
        return previous;
    }

    public void setPrevious(TreeNode previous) {
        this.previous = previous;
    }

    public TreeNode getNext() {
        return next;
    }

    public void setNext(TreeNode next) {
        this.next = next;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public List<Entry<Comparable, Object>> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry<Comparable, Object>> entries) {
        this.entries = entries;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "isLeaf=" + isLeaf +
                ", isRoot=" + isRoot +
                ", parent=" + parent +
                ", previous=" + previous +
                ", next=" + next +
                '}';
    }
}
