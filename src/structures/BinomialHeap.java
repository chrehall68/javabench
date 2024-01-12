package structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class BinomialHeap<E> implements Queue<E> {
    private class BinomialHeapIterator implements Iterator<E> {
        ArrayList<BinomialTree> curLevel;
        ArrayList<BinomialTree> nextLevel;
        int curLevelIdx = 0;

        public BinomialHeapIterator() {
            curLevel = new ArrayList<>();
            nextLevel = new ArrayList<>();
            if (root != null) {
                curLevel.add(root);
            }
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            BinomialTree temp = curLevel.get(curLevelIdx);

            if (temp.firstSubtree != null) {
                nextLevel.add(temp.firstSubtree);
            }

            if (temp.sibling != null) {
                curLevel.set(curLevelIdx, temp.sibling);
            } else {
                ++curLevelIdx;
                if (curLevelIdx == curLevel.size()) {
                    curLevel = nextLevel;
                    nextLevel = new ArrayList<>();
                    curLevelIdx = 0;
                }
            }
            return temp.val;
        }

        @Override
        public boolean hasNext() {
            return curLevelIdx < curLevel.size();
        }
    }

    private class BinomialTree {
        E val;
        BinomialTree firstSubtree;
        BinomialTree sibling;
        int depth;

        public BinomialTree(E val) {
            this.val = val;
        }

        void addSubtree(BinomialTree subtree) {
            subtree.sibling = firstSubtree;
            firstSubtree = subtree;
        }

        BinomialTree mergeSameOrder(BinomialTree o) {
            if (compare(o.val, val) < 0) {
                o.addSubtree(this);
                ++o.depth;
                return o;
            } else {
                addSubtree(o);
                ++depth;
                return this;
            }
        }

        public String toString() {
            String ret = "{" + val + ":";
            // link descendants first
            ret += firstSubtree == null ? "" : firstSubtree.toString();

            // then add siblings
            ret += "}";
            ret += sibling == null ? "" : "," + sibling.toString();

            return ret;
        }

        public boolean contains(Object o) {
            if (o.equals(val)) {
                return true;
            }

            boolean ret = false;
            if (firstSubtree != null) {
                ret |= firstSubtree.contains(o);
            }
            if (sibling != null) {
                ret |= sibling.contains(o);
            }
            return ret;
        }
    }

    // ==============================
    // Attributes/Constructor Section
    // ==============================
    private Comparator<? super E> comparator;
    private int size;
    private BinomialTree root;
    private BinomialTree tail;
    private BinomialTree min;

    public BinomialHeap() {

    }

    public BinomialHeap(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    // ==============================
    // Misc Section
    // ==============================
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {
        E[] ret = (E[]) new Object[size];
        return toArray(ret);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] ret = a;
        if (ret.length < size) {
            ret = (T[]) new Object[size];
        }
        int idx = 0;
        for (E val : this) {
            ret[idx++] = (T) val;
        }
        return ret;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    public String toString() {
        return root == null ? "Empty BHeap " : root.toString();
    }

    // ==============================
    // Get Section
    // ==============================
    @Override
    public E poll() {
        if (root == null) {
            return null;
        }
        BinomialTree cur = root;
        BinomialTree toAdd = null;
        E minVal = peek();
        if (root.val.equals(minVal)) {
            toAdd = root.firstSubtree;
            root = root.sibling;
            if (root == null) {
                tail = null;
            }
        }
        // else, iterate to find it
        else {
            while (!cur.sibling.val.equals(minVal)) {
                cur = cur.sibling;
            }

            toAdd = cur.sibling; // this one equals minVal
            cur.sibling = toAdd.sibling; // patch the tree
            toAdd = toAdd.firstSubtree;// remove toAdd by taking only the subtrees of toAdd
        }

        // reverse the order of toAdd to be in order of smallest to largest
        BinomialTree prev = null;
        cur = toAdd;
        while (cur != null) {
            // make cur point at prev
            BinomialTree temp = cur.sibling;
            cur.sibling = prev;

            // advance pointers
            prev = cur;
            cur = temp;
        }
        BinomialTree root2 = prev; // this is now the new head
        BinomialTree root1 = root;
        root = null;
        min = null;
        size = 0;
        mergeWith(root1, root2);

        return minVal;
    }

    @Override
    public E peek() {
        // return the min out of the roots
        if (root == null) {
            return null;
        }
        return min.val;
    }

    @Override
    public E remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return poll();
    }

    @Override
    public E element() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return peek();
    }

    // ==============================
    // Add Section
    // ==============================
    @Override
    public boolean add(E e) {
        insert(e);
        return true;
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
        return true;
    }

    private void insert(E val) {
        BinomialTree toInsert = new BinomialTree(val);
        mergeTree(toInsert);
    }

    private void mergeTree(BinomialTree tree) {
        if (min == null || compare(tree.val, min.val) < 0) {
            min = tree;
        }

        size += 1 << tree.depth;
        // see if we can add it anywhere
        while (root != null) {
            // case that it is less than root.depth
            // meaning we have smth like a 2-order BinomialTree as root
            // and we're inserting like a 1-order Binomial tree
            if (tree.depth < root.depth) {
                tree.sibling = root;
                root = tree;
                break;
            }
            // case where it's equal to root's depth
            // meaning we alr have a 1-order binomial tree
            else if (tree.depth == root.depth) {
                // store the next tree bc we'll have to compare w/ it
                BinomialTree temp = root.sibling;
                root.sibling = null;
                tree = tree.mergeSameOrder(root);
                root = temp;
            } else if (tree.depth > root.depth) {
                if (root.sibling == null) {
                    root.sibling = tree;
                    tail = tree;
                    break;
                } else if (tail.depth < tree.depth) {
                    // since trees must be complete
                    // we can just set the tail to this tree and call it a day
                    tail.sibling = tree;
                    tail = tree;
                    break;
                } else {
                    // find the nodes that it lies in between
                    BinomialTree cur = root;
                    while (true) {
                        while (cur.sibling != null && cur.sibling.depth < tree.depth) {
                            cur = cur.sibling;
                        }
                        if (cur.sibling == null) {
                            // made it to the end
                            cur.sibling = tree;
                            tail = tree;
                            break;
                        }
                        if (tree.depth < cur.sibling.depth) {
                            // just insert between
                            assert tree.depth != cur.depth;
                            BinomialTree temp = cur.sibling;
                            cur.sibling = tree;
                            tree.sibling = temp;
                            break;
                        } else if (tree.depth == cur.sibling.depth) {
                            BinomialTree temp = cur.sibling;
                            cur.sibling = temp.sibling;
                            temp.sibling = null;

                            tree = tree.mergeSameOrder(temp);
                        }
                    }
                    break;

                }
            }

        }
        if (root == null) {
            // we've reached the end (either increased our height or root was just null in
            // the first place)
            root = tree;
            tail = tree;
        }
    }

    // assmes this.root has been cleared
    private void mergeWith(BinomialTree root1, BinomialTree root2) {
        BinomialTree cur1 = root1;
        BinomialTree cur2 = root2;
        BinomialTree cur1Next = null;
        BinomialTree cur2Next = null;

        while (cur1 != null || cur2 != null) {
            if (cur1 != null && cur2 != null) {
                BinomialTree temp = null;
                // merge the two first
                if (cur1.depth < cur2.depth) {
                    temp = cur1;
                    cur1 = cur1.sibling;
                    temp.sibling = null;
                } else if (cur1.depth == cur2.depth) {
                    temp = cur1;
                    cur1 = cur1.sibling;
                    temp.sibling = null;

                    cur2Next = cur2.sibling;
                    cur2.sibling = null;

                    // merge
                    temp = temp.mergeSameOrder(cur2);
                    // update cur2
                    cur2 = cur2Next;
                } else { // cur1.depth > cur2.depth
                    temp = cur2;
                    cur2 = cur2.sibling;
                    temp.sibling = null;
                }

                // put into heap
                if (root == null) {
                    // add tree
                    root = temp;
                    min = temp;
                    size = 1 << temp.depth;
                } else {
                    // merge
                    mergeTree(temp);
                }
            }
            // if only 1 of the roots still has smth, just merge that tree into the heap
            else if (cur1 != null) {
                cur1Next = cur1.sibling;
                cur1.sibling = null;
                mergeTree(cur1);
                cur1 = cur1Next;
            } else {
                cur2Next = cur2.sibling;
                cur2.sibling = null;
                mergeTree(cur2);
                cur2 = cur2Next;
            }
        }
    }

    // ==============================
    // Contains/Remove Section
    // ==============================
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Object o) {
        return root != null && root.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return new BinomialHeapIterator();
    }

    // ==============================
    // Internals section
    // ==============================
    private int compare(E lhs, E rhs) {
        return comparator == null ? ((Comparable<E>) lhs).compareTo(rhs) : comparator.compare(lhs, rhs);
    }
}
