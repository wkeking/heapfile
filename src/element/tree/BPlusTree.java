package element.tree;

public class BPlusTree implements BTree {

    protected TreeNode root;//根节点

    protected int order;//阶数，M值

    protected TreeNode head;//叶子节点的链表头

    public TreeNode getHead() {
        return head;
    }

    public void setHead(TreeNode head) {
        this.head = head;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public Object get(Comparable key) {
        return root.get (key);
    }

    @Override
    public void remove(Comparable key) {
        root.remove (key, this);

    }

    @Override
    public void insertOrUpdate(Comparable key, Object obj) {
        root.insertOrUpdate (key, obj, this);

    }

    public BPlusTree(int order) {
        if (order < 3) {
            System.out.print ("order must be greater than 2");
            System.exit (0);
        }
        this.order = order;
        root = new TreeNode (true, true);
        head = root;
    }

}