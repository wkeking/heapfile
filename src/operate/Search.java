package operate;

import config.TableConfig;
import element.tree.BTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Search {

    private BTree tree;

    public Search() throws IOException, ClassNotFoundException {
        TableConfig.initTableInfo();
        File file = new File (TableConfig.INDEXNAME);
        try (FileInputStream fis = new FileInputStream (file);
             ObjectInputStream ois = new ObjectInputStream (fis)) {
            tree = (BTree) ois.readObject ();
        } catch (Exception e) {
            throw e;
        }
    }


}
