package element.tree;

import config.TableConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BPlusTree<K extends Comparable<? super K>, V> implements Serializable {

    private static final int DEFAULT_BRANCHING_FACTOR = 128;

    private int branchingFactor;

    private Node root;

    public BPlusTree() {
        this(DEFAULT_BRANCHING_FACTOR);
    }

    public BPlusTree(int branchingFactor) {
        if (branchingFactor <= 2)
            throw new IllegalArgumentException("Illegal branching factor: "
                    + branchingFactor);
        this.branchingFactor = branchingFactor;
        root = new LeafNode();
    }

    public V search(K key) {
        return root.getValue(key);
    }

    public void insert(K key, V value) {
        root.insertValue(key, value);
    }

    public String toString() {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
        queue.add(Arrays.asList(root));
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            while (!queue.isEmpty()) {
                List<Node> nodes = queue.remove();
                sb.append('{');
                Iterator<Node> it = nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext())
                        sb.append(", ");
                    if (node instanceof BPlusTree.InternalNode)
                        nextQueue.add(((InternalNode) node).children);
                }
                sb.append('}');
                if (!queue.isEmpty())
                    sb.append(", ");
                else
                    sb.append('\n');
            }
            queue = nextQueue;
        }

        return sb.toString();
    }

    private abstract class Node implements Serializable {
        List<K> keys;

        int keyNumber() {
            return keys.size();
        }

        abstract V getValue(K key);

        abstract void insertValue(K key, V value);

        abstract K getFirstLeafKey();

        abstract Node split();

        abstract boolean isOverflow();

        abstract boolean isUnderflow();

        public String toString() {
            return keys.toString();
        }
    }

    private class InternalNode extends Node {
        List<Node> children;

        InternalNode() {
            this.keys = new ArrayList<K>();
            this.children = new ArrayList<Node>();
        }

        @Override
        V getValue(K key) {
            return getChild(key).getValue(key);
        }

        @Override
        void insertValue(K key, V value) {
            Node child = getChild(key);
            child.insertValue(key, value);
            if (child.isOverflow()) {
                Node sibling = child.split();
                insertChild(sibling.getFirstLeafKey(), sibling);
            }
            if (root.isOverflow()) {
                Node sibling = split();
                InternalNode newRoot = new InternalNode();
                newRoot.keys.add(sibling.getFirstLeafKey());
                newRoot.children.add(this);
                newRoot.children.add(sibling);
                root = newRoot;
            }
        }

        @Override
        K getFirstLeafKey() {
            return children.get(0).getFirstLeafKey();
        }

        @Override
        Node split() {
            int from = keyNumber() / 2 + 1, to = keyNumber();
            InternalNode sibling = new InternalNode();
            sibling.keys.addAll(keys.subList(from, to));
            sibling.children.addAll(children.subList(from, to + 1));

            keys.subList(from - 1, to).clear();
            children.subList(from, to + 1).clear();

            return sibling;
        }

        @Override
        boolean isOverflow() {
            return children.size() > branchingFactor;
        }

        @Override
        boolean isUnderflow() {
            return children.size() < (branchingFactor + 1) / 2;
        }

        Node getChild(K key) {
            int loc = Collections.binarySearch(keys, key);
            int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
            return children.get(childIndex);
        }

        void insertChild(K key, Node child) {
            int loc = Collections.binarySearch(keys, key);
            int childIndex = loc >= 0 ? loc + 1 : -loc - 1;
            if (loc >= 0) {
                children.set(childIndex, child);
            } else {
                keys.add(childIndex, key);
                children.add(childIndex + 1, child);
            }
        }
    }

    private class LeafNode extends Node {
        List<V> values;
        LeafNode next;

        LeafNode() {
            keys = new ArrayList<K>();
            values = new ArrayList<V>();
        }

        @Override
        V getValue(K key) {
            int loc = Collections.binarySearch(keys, key);
            return loc >= 0 ? values.get(loc) : null;
        }

        @Override
        void insertValue(K key, V value) {
            int loc = Collections.binarySearch(keys, key);
            int valueIndex = loc >= 0 ? loc : -loc - 1;
            if (loc >= 0) {
                StringBuilder sb = new StringBuilder (values.get (valueIndex).toString ());
                sb.append (TableConfig.SEPARATOR);
                value = (V) sb.append (value).toString ();
                values.set(valueIndex, value);
            } else {
                keys.add(valueIndex, key);
                values.add(valueIndex, value);
            }
            if (root.isOverflow()) {
                Node sibling = split();
                InternalNode newRoot = new InternalNode();
                newRoot.keys.add(sibling.getFirstLeafKey());
                newRoot.children.add(this);
                newRoot.children.add(sibling);
                root = newRoot;
            }
        }

        @Override
        K getFirstLeafKey() {
            return keys.get(0);
        }

        @Override
        Node split() {
            LeafNode sibling = new LeafNode();
            int from = (keyNumber() + 1) / 2, to = keyNumber();
            sibling.keys.addAll(keys.subList(from, to));
            sibling.values.addAll(values.subList(from, to));

            keys.subList(from, to).clear();
            values.subList(from, to).clear();

            sibling.next = next;
            next = sibling;
            return sibling;
        }

        @Override
        boolean isOverflow() {
            return values.size() > branchingFactor - 1;
        }

        @Override
        boolean isUnderflow() {
            return values.size() < branchingFactor / 2;
        }
    }
}
