import config.TableConfig;
import element.tree.BPlusTree;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        write (10);
        read ();

    }

    private static BPlusTree<String, String> createTree(int flag) {
        BPlusTree<String, String> tree = new BPlusTree<> ();
        tree.insert (flag + "111", flag + "a");
        tree.insert (flag + "222", flag + "b");
        tree.insert (flag + "333", flag + "c");
        tree.insert (flag + "444", flag + "d");
        tree.insert (flag + "555", flag + "e");
        tree.insert (flag + "666", flag + "f");
        tree.insert (flag + "777", flag + "g");
        tree.insert (flag + "111", flag + "h");
        tree.insert (flag + "111", flag + "i");
        return tree;
    }

    private static void write(int i) {
        File file = new File ("test.index");
        try (FileOutputStream fos = new FileOutputStream (file);
             BufferedOutputStream bos = new BufferedOutputStream (fos, TableConfig.BUFFERSIZE);
             ObjectOutputStream oos = new ObjectOutputStream(bos);) {
            for (int a = 0; a < i; a ++) {
                BPlusTree<String, String> tree = createTree (a);
                oos.writeObject (tree);
                oos.flush ();
                oos.reset ();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void read() {
        File file = new File ("test.index");
        try (FileInputStream fis = new FileInputStream (file);
             BufferedInputStream bis = new BufferedInputStream(fis, TableConfig.BUFFERSIZE);
             ObjectInputStream ois = new ObjectInputStream (bis);) {
            while (true) {
                BPlusTree<String, String> tree = (BPlusTree) ois.readObject ();
                System.out.println(tree);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
