package structures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unchecked")
public class SCHashMap<K, V> implements Map<K, V> {
    private class Node implements Entry<K, V> {
        private K key;
        private V val;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
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
            this.val = value;
            return val;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }

        public boolean equals(Object o) {
            try {
                Node n = (Node) o;
                return key.equals(n.key) && val.equals(n.val);
            } catch (ClassCastException e) {
                return false;
            }
        }

        public String toString() {
            return "{" + key + ":" + val + "}";
        }
    }

    private static final double MAX_LOAD_FACTOR = .75F;
    private static final int INITIAL_SIZE = 16;
    private double maxLoadFactor;
    private int size;
    private ArrayList<LinkedList<Node>> hashTable;

    // separately chained hashmap
    public SCHashMap() {
        this(MAX_LOAD_FACTOR);
    }

    public SCHashMap(double maxLoadFactor) {
        this(maxLoadFactor, INITIAL_SIZE);
    }

    public SCHashMap(double maxLoadFactor, int initialSize) {
        this.maxLoadFactor = maxLoadFactor;
        hashTable = new ArrayList<>();
        for (int i = 0; i < initialSize; ++i) {
            hashTable.add(null);
        }
    }

    // =================================
    // Remove Section
    // =================================
    @Override
    public V remove(Object key) {
        int idx = getIdx(key);
        if (hashTable.get(idx) == null) {
            return null; // no such value
        }

        LinkedList<Node> ll = hashTable.get(idx);
        for (Node n : ll) {
            if (n.getKey().equals(key)) {
                ll.remove(n);
                return n.getValue();
            }
        }
        return null;
    }

    // =================================
    // Get Section
    // =================================

    @Override
    public V get(Object key) {
        int idx = getIdx(key);
        if (hashTable.get(idx) == null) {
            return null;
        }

        // look for it
        for (Node n : hashTable.get(idx)) {
            if (n.getKey().equals(key)) {
                return n.getValue();
            }
        }

        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        int idx = getIdx(key);
        if (hashTable.get(idx) == null) {
            return false;
        }

        // look for it
        for (Node n : hashTable.get(idx)) {
            if (n.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (LinkedList<Node> linkedList : hashTable) {
            if (linkedList == null) {
                continue;
            }
            for (Node n : linkedList) {
                if (n.getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    // =================================
    // Put Section
    // =================================
    @Override
    public V put(K key, V val) {
        int idx = getIdx(key);

        if (hashTable.get(idx) == null) {
            hashTable.set(idx, new LinkedList<>());
        }
        // see if it's already there
        LinkedList<Node> ll = hashTable.get(idx);
        for (Node n : ll) {
            if (n.getKey().equals(key)) {
                n.setValue(val);
                return val;
            }
        }
        ++size;
        ll.add(new Node(key, val));
        maybeResize();
        return val;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    private void maybeResize() {
        if (size > hashTable.size() * maxLoadFactor) {
            // resize to more
            ArrayList<LinkedList<Node>> temp = hashTable;
            hashTable = new ArrayList<>();

            int nextSize = temp.size();
            if (maxLoadFactor < 1) {
                nextSize = (int) (nextSize / maxLoadFactor);
            } else {
                nextSize = (int) (nextSize * maxLoadFactor);
            }
            for (int i = 0; i < nextSize; ++i) {
                hashTable.add(null);
            }

            // clear size
            size = 0;

            // then reinsert all elements
            for (LinkedList<Node> ll : temp) {
                if (ll == null) {
                    continue;
                }
                for (Node n : ll) {
                    put(n.getKey(), n.getValue());
                }
            }
        }
    }

    // =================================
    // Misc Section
    // =================================

    @Override
    public void clear() {
        for (int i = 0; i < hashTable.size(); ++i) {
            hashTable.set(i, null);
        }
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private int getIdx(Object o) {
        return Math.abs(o.hashCode()) % hashTable.size();
    }

    // =================================
    // Set Section
    // =================================
    @Override
    public Set<K> keySet() {
        HashSet<K> keySet = new HashSet<>();
        for (LinkedList<Node> ll : hashTable) {
            if (ll == null) {
                continue;
            }
            for (Node n : ll) {
                keySet.add(n.getKey());
            }
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        LinkedList<V> values = new LinkedList<>();
        for (LinkedList<Node> ll : hashTable) {
            if (ll == null) {
                continue;
            }
            for (Node n : ll) {
                values.add(n.getValue());
            }
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        HashSet<Entry<K, V>> entries = new HashSet<>();
        for (LinkedList<Node> ll : hashTable) {
            if (ll == null) {
                continue;
            }
            for (Node n : ll) {
                entries.add(n);
            }
        }
        return entries;
    }

    public String toString() {
        return hashTable.toString();
    }
}
