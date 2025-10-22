package ru.vsu.cs.tolkacheva_u_a.bstartree;

import java.util.ArrayList;
import java.util.List;

public class BStarTree<T extends Comparable<T>> implements BTreeOperations<T> {
    private final int order;
    private final int minKeys;
    private final int maxKeys;
    private Node root;
    private int size;

    public class Node implements TreeNode<T> {
        public List<T> keys;
        public List<Node> children;
        public boolean isLeaf;
        public Node parent;

        public Node(boolean isLeaf) {
            this.keys = new ArrayList<>();
            this.children = new ArrayList<>();
            this.isLeaf = isLeaf;
            this.parent = null;
        }

        public List<T> getKeys() { return keys; }
        public List<Node> getChildren() { return children; }
        public boolean isLeaf() { return isLeaf; }
        public Node getParent() { return parent; }

        public int findKeyIndex(T key) {
            int idx = 0;
            while (idx < keys.size() && keys.get(idx).compareTo(key) < 0) {
                idx++;
            }
            return idx;
        }
    }

    public BStarTree(int order) {
        this.order = order;
        this.minKeys = (int) Math.ceil((2 * order - 1) / 3.0);
        this.maxKeys = 2 * order - 1;
        this.root = createNode(true);
        this.size = 0;
    }

    public Node createNode(boolean isLeaf) {
        return new Node(isLeaf);
    }


    @Override
    public void insert(T key) {
        if (root.keys.size() == maxKeys) {
            Node newRoot = createNode(false);
            newRoot.children.add(root);
            root.parent = newRoot;
            splitChild(newRoot, 0);
            root = newRoot;
        }
        insertNonFull(root, key);
        size++;
    }

    @Override
    public boolean search(T key) {
        return searchNode(root, key) != null;
    }

    @Override
    public void remove(T key) {
        System.out.println("Remove operation called for: " + key);
        size = Math.max(0, size - 1);
    }

    @Override
    public void clear() {
        this.root = createNode(true);
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int getHeight() {
        return calculateHeight(root);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public int getOrder() {
        return order;
    }

    public int getMinKeys() {
        return minKeys;
    }

    public int getMaxKeys() {
        return maxKeys;
    }

    private void insertNonFull(Node node, T key) {
        if (node.isLeaf) {
            int i = node.findKeyIndex(key);
            node.keys.add(i, key);

            if (node.keys.size() > maxKeys) {
                handleOverflow(node);
            }
        } else {
            int i = node.findKeyIndex(key);
            Node child = node.children.get(i);

            if (child.keys.size() == maxKeys) {
                splitChild(node, i);
                if (key.compareTo(node.keys.get(i)) > 0) {
                    i++;
                }
            }

            insertNonFull(node.children.get(i), key);
        }
    }

    private Node searchNode(Node node, T key) {
        if (node == null) return null;

        int i = node.findKeyIndex(key);

        if (i < node.keys.size() && node.keys.get(i).equals(key)) {
            return node;
        }

        if (node.isLeaf) {
            return null;
        }

        return searchNode(node.children.get(i), key);
    }

    private void splitChild(Node parent, int childIndex) {
        Node child = parent.children.get(childIndex);
        int mid = child.keys.size() / 2;

        Node newChild = createNode(child.isLeaf);

        for (int i = mid + 1; i < child.keys.size(); i++) {
            newChild.keys.add(child.keys.get(i));
        }

        if (!child.isLeaf) {
            for (int i = mid + 1; i < child.children.size(); i++) {
                newChild.children.add(child.children.get(i));
                child.children.get(i).parent = newChild;
            }
            child.children.subList(mid + 1, child.children.size()).clear();
        }

        T middleKey = child.keys.get(mid);
        child.keys.subList(mid, child.keys.size()).clear();

        parent.keys.add(childIndex, middleKey);
        parent.children.add(childIndex + 1, newChild);
        newChild.parent = parent;
    }

    private void handleOverflow(Node node) {
        if (node == root) {
            Node newRoot = createNode(false);
            newRoot.children.add(root);
            root.parent = newRoot;
            splitChild(newRoot, 0);
            root = newRoot;
            return;
        }

        System.out.println("Handling overflow in node with keys: " + node.keys);
    }

    private int calculateHeight(Node node) {
        if (node == null) return 0;
        if (node.isLeaf) return 1;

        int maxHeight = 0;
        for (Node child : node.children) {
            maxHeight = Math.max(maxHeight, calculateHeight(child));
        }
        return maxHeight + 1;
    }

    private void validateAndRebuildIfNeeded() throws Exception {
        if (!isValidBStarTree(root)) {
            rebuildTree();
        }
    }

    private boolean isValidBStarTree(Node node) {
        if (node == null) return true;

        if (node != root && node.keys.size() < minKeys) {
            return false;
        }
        if (node.keys.size() > maxKeys) {
            return false;
        }

        for (int i = 1; i < node.keys.size(); i++) {
            if (node.keys.get(i).compareTo(node.keys.get(i-1)) <= 0) {
                return false;
            }
        }

        for (Node child : node.children) {
            if (!isValidBStarTree(child)) {
                return false;
            }
        }

        return true;
    }

    private void rebuildTree() throws Exception {
        List<T> allValues = collectAllValues(root);
        this.root = createNode(true);
        this.size = 0;

        for (T value : allValues) {
            insert(value);
        }
    }

    private List<T> collectAllValues(Node node) {
        List<T> values = new ArrayList<>();
        if (node == null) return values;

        values.addAll(node.keys);
        for (Node child : node.children) {
            values.addAll(collectAllValues(child));
        }

        return values;
    }
}
