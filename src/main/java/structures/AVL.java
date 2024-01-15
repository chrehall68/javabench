package structures;

import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class AVL<K extends Comparable<K>, V> implements Map<K, V> {
    // AVL attributes
    private Node root = null;
    private int size = 0;

    public String toString() {
        return root == null ? "{}" : root.toString();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // ========================================
    // Miscellaneous Section
    // ========================================

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
    }

    private void useIterator(Iterator<Node> iterator, Consumer<Node> c) {
        while (iterator.hasNext()) {
            c.accept(iterator.next());
        }
    }

    @Override
    public V get(Object key) {
        return getOrDefault(key, null);
    }

    // ========================================
    // Search Section
    // ========================================

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        try {
            Node n = search((K) key);
            return n == null ? defaultValue : n.val;

        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    @Override
    public boolean containsKey(Object key) {
        try {
            Node n = search((K) key);
            return n == null;
        } catch (ClassCastException e) {
            return false;
        }
    }

    private Node search(K key) {
        Node cur = root;
        while (cur != null) {
            if (key.equals(cur.key)) {
                return cur;
            } else if (key.compareTo(cur.key) < 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return null;
    }

    /**
     * BFS to find value
     */
    @Override
    public boolean containsValue(Object value) {
        try {
            V val = (V) value;
            Iterator<Node> iterator = new AVLBFSIterator(root);
            while (iterator.hasNext()) {
                if (iterator.next().val.equals(val)) {
                    return true;
                }
            }
            // exhausted all levels w/o finding it
            return false;
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> ret = new HashSet<>(); // TODO: maybe change this for different strategy later
        useIterator(new AVLBFSIterator(root), node -> ret.add(node.key));
        return ret;
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> ret = new ArrayList<>(); // TODO: maybe change this for different strategy later
        useIterator(new AVLBFSIterator(root), node -> ret.add(node.val));
        return ret;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        HashSet<Entry<K, V>> ret = new HashSet<>();
        useIterator(new AVLBFSIterator(root), node -> ret.add(node));
        return ret;
    }

    @Override
    public V remove(Object key) {
        try {
            return remove((K) key);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public V remove(K key) {
        if (root == null) {
            return null;
        }
        if (root.key.equals(key)) {
            V ret = root.val;
            root = delete(root);
            return ret;
        }
        V ret = delete(root, key);
        checkRotate(root);
        root.updateHeight();
        return ret;
    }

    // ========================================
    // Deletion Section
    // ========================================

    /**
     * Attempts to delete `key` from cur's subtree
     *
     * @param cur - the Node to start searching at
     * @param key - the key to search for and delete
     * @return V - the value stored at that Node, or null if it wasn't found
     */
    private V delete(Node cur, K key) {
        V ret = null;
        if (key.compareTo(cur.key) < 0) {
            if (cur.left == null) {
                return null;
            }

            // it's the left one
            if (cur.left.key.equals(key)) {
                ret = cur.left.val;
                cur.left = delete(cur.left);
            } else {
                // else, search deeper in the subtree
                ret = delete(cur.left, key);
            }
        } else { // key.compareTo(cur.key) > 0 ; key > cur.key
            if (cur.right == null) {
                return null;
            }

            // it's the left one
            if (cur.right.key.equals(key)) {
                ret = cur.right.val;
                cur.right = delete(cur.right);
            } else {
                // else, search deeper in the subtree
                ret = delete(cur.right, key);
            }
        }

        return ret;
    }

    /**
     * Deletes the given node, and returns either null or the resulting
     * subtree (formed via the inorder successor)
     *
     * @param node - the node to remove from the tree
     * @return Node - the new Node at this position after balancing, or null if no
     * Node will exist here
     */
    private Node delete(Node node) {
        // leaf node; easy delete
        if (node.left == null && node.right == null) {
            // complete leaf node, no need to balance here
            node = null;
        } else if (node.left == null && node.right != null) {
            // no need to balance here bc left was null, meaning that right is ok
            node = node.right;
        } else if (node.left != null && node.right == null) {
            // no need to balance here bc right was null, so left is ok
            node = node.left;
        } else {
            // find inorder successor and store those values
            Node successor = deleteInorderSuccessor(node);
            node.key = successor.key;
            node.val = successor.val;

            // checkrotate and update height
            node = checkRotate(node);
            node.updateHeight();
        }

        // since we actually removed a node, we can decrement our size
        --size;

        return node;
    }

    /**
     * Deletes the inorder sucessor and takes care of balancing
     * any nodes that were disturbed
     *
     * @param start - should have a non-null right
     * @return
     */
    private Node deleteInorderSuccessor(Node start) {
        Node ret = null;
        if (start.right.left == null) {
            // case where the right just keeps going
            // so what we do here is just delete start.right
            ret = start.right;

            // since left is nothing, just use right
            start.right = start.right.right;
            return ret;
        } else {
            ret = recursiveDeleteInorderSuccessor(start.right);
            start.right = checkRotate(start.right);
            start.right.updateHeight();
        }
        return ret;
    }

    /**
     * Delete the inorder successor and balance all the affected
     * nodes between the deleted inorder successor and the starting node
     *
     * @param cur - the node to start at
     * @return Node - the deleted inorder successor
     */
    private Node recursiveDeleteInorderSuccessor(Node cur) {
        if (cur.left.left == null) {
            // we are done
            Node ret = cur.left;
            cur.left = cur.left.right; // store the left's right subtree that way we don't lose that
            cur.updateHeight();
            // don't need to check rotate bc the right tree hasn't changed
            return ret;
        } else {
            Node ret = recursiveDeleteInorderSuccessor(cur.left);

            // balance and then update height
            cur.left = checkRotate(cur.left);
            cur.right = checkRotate(cur.right);
            cur.updateHeight();
            return ret;
        }
    }

    @Override
    public V put(K key, V val) {
        if (root == null) {
            this.root = new Node(key, val);
            return val;
        } else {
            insert(root, key, val);
            checkRotate(root);
            return val;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    // ========================================
    // Insertion section
    // ========================================

    /**
     * Recursively add (key, val), starting at cur
     *
     * @param cur - the current Node
     * @param key - the key to add. If the key already exists, then the node is
     *            updated and no new node is added
     * @param val - the value to add
     */
    private void insert(Node cur, K key, V val) {
        // base case equal to
        if (key.compareTo(cur.key) == 0) {
            cur.val = val;
            return;
        }

        // lt
        if (key.compareTo(cur.key) < 0) {
            if (cur.left == null) {
                ++size; // we know for sure that this is a new node
                cur.left = new Node(key, val);
            } else {
                insert(cur.left, key, val);
            }
        }
        // gt
        else if (key.compareTo(cur.key) > 0) {
            if (cur.right == null) {
                ++size; // we know for sure that this is a new node
                cur.right = new Node(key, val);
            } else {
                insert(cur.right, key, val);
            }
        }

        // balance left and/or right
        cur.left = checkRotate(cur.left);
        cur.right = checkRotate(cur.right);
        cur.updateHeight();
    }

    /**
     * Check if cur needs to be rotated, and if so, perform the necessary rotation
     *
     * @param cur - the node to check and possible rotate
     * @return Node - the node at this spot (either the original node if no rotation
     * was performed
     * or the node that took this spot after rotation)
     */
    private Node checkRotate(Node cur) {
        if (cur == null) {
            return null;
        }
        // see if we need to rotate
        if (Math.abs(cur.getBalance()) > 1) {
            if (cur.getBalance() == 2) { // right heavy
                if (cur.right.getBalance() == 1) { // it's also right heavy
                    // so we just do a left rotate
                    cur = leftRotate(cur);
                } else { // cur.right.balance == -1
                    // do a right left rotate
                    cur.right = rightRotate(cur.right);
                    cur = leftRotate(cur);
                }

            } else { // cur.balance == -2; left heavy
                if (cur.left.getBalance() == -1) {
                    // it's also left heavy, so we right rotate
                    cur = rightRotate(cur);
                } else {
                    // do a left right rotate
                    cur.left = leftRotate(cur.left);
                    cur = rightRotate(cur);
                }
            }
        }
        return cur;
    }

    /**
     * Performs a left rotate and updates the heights of the nodes involved
     *
     * @param origin - has bf of 2
     * @return new origin
     */
    private Node leftRotate(Node origin) {
        Node ret = origin.right;
        origin.right = ret.left;
        ret.left = origin;

        if (origin == this.root) {
            this.root = ret;
        }
        origin.updateHeight(); // update origin height first since ret height depends on that
        ret.updateHeight();
        return ret;
    }

    /**
     * Performs a right rotate and updates the heights of the nodes involved
     *
     * @param origin - has bf of -2
     * @return new origin
     */
    private Node rightRotate(Node origin) {
        Node ret = origin.left;
        origin.left = ret.right;
        ret.right = origin;

        if (origin == this.root) {
            this.root = ret;
        }
        origin.updateHeight(); // update origin height first since ret height depends on that
        ret.updateHeight();
        return ret;
    }

    private class AVLBFSIterator implements Iterator<Node> {
        ArrayList<Node> curLevel;
        ArrayList<Node> nextLevel;
        int idx = 0;

        public AVLBFSIterator(Node root) {
            curLevel = new ArrayList<>();
            nextLevel = new ArrayList<>();
            idx = 0;
            if (root != null) {
                curLevel.add(root);
            }
        }

        @Override
        public Node next() {
            // safety
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            // incremental bfs
            Node ret = curLevel.get(idx++);
            if (ret.left != null) {
                nextLevel.add(ret.left);
            }
            if (ret.right != null) {
                nextLevel.add(ret.right);
            }
            return ret;
        }

        @Override
        public boolean hasNext() {
            // advance to the next level
            if (idx == curLevel.size()) {
                idx = 0;
                changeLevel();
            }
            return !curLevel.isEmpty();
        }

        private void changeLevel() {
            curLevel = nextLevel;
            nextLevel = new ArrayList<>();
        }
    }

    private class Node implements Entry<K, V> {
        K key;
        V val;
        Node left;
        Node right;
        int height;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
            this.height = 1;
        }

        public int getBalance() {
            // BF(x) = Height(right_subtree(x)) - Height(left_subtree(x))
            return (right == null ? 0 : right.height) - (left == null ? 0 : left.height);
        }

        public String toString() {
            return key + ":" + val + "{" + (left == null ? "" : left) + "," + (right == null ? "" : right) + "}";
        }

        public void updateHeight() {
            // should be called in a bottom up way
            // otherwise this won't work
            height = Math.max((left == null ? 0 : left.height) + 1, (right == null ? 0 : right.height + 1));
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return val;
        }

        @Override
        public V setValue(V value) {
            val = value;
            return val;
        }

    }
}
