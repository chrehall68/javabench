package structures;

import java.util.*;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class BinomialHeap<E> implements Queue<E> {
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
        E minVal = peek();
        BinomialTree prev = min.prev;
        BinomialTree next = min.next;

        // take care of root/tail cases
        if (root.equals(min)) {
            root = next;
        }
        if (tail.equals(min)) {
            tail = prev;
        }

        // regular patching
        if (prev != null) {
            prev.next = next;
        }
        if (next != null) {
            next.prev = null;
        }

        BinomialTree root2 = min.lastSubtree; // this is now the new head
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

    private void maybeSetMin(BinomialTree tree) {
        // tree.isChild + " a child");
        if (min == null || min.isChild) {
            min = tree;
        } else {
            if (compare(tree.val, min.val) < 0) {
                min = tree;
            } else if (tree.val.equals(min.val) && tree.depth < min.depth) {
                min = tree;
            }
        }
    }

    private void mergeTree(BinomialTree tree) {
        size += 1 << tree.depth;
        // see if we can add it anywhere
        while (root != null) {
            // case that it is less than root.depth
            // meaning we have smth like a 2-order BinomialTree as root
            // and we're inserting like a 1-order Binomial tree
            if (tree.depth < root.depth) {
                root.setPrev(tree);
                root = tree;
                maybeSetMin(tree);

                break;
            }
            // case where it's equal to root's depth
            // meaning we alr have a 1-order binomial tree
            else if (tree.depth == root.depth) {
                // store the next tree bc we'll have to compare w/ it
                BinomialTree temp = root;
                root = root.next;
                temp.removeConnections();

                tree = tree.mergeSameOrder(temp);
                maybeSetMin(tree);
            } else if (tree.depth > root.depth) {
                if (root.next == null) {
                    root.insertNext(tree);
                    tail = tree;
                    maybeSetMin(tree);
                    break;
                } else if (tail.depth < tree.depth) {
                    // since trees must be complete
                    // we can just set the tail to this tree and call it a day
                    tail.insertNext(tree);
                    tail = tree;
                    maybeSetMin(tree);
                    break;
                } else if (tail.depth == tree.depth) {
                    BinomialTree temp = tail.prev;
                    tail.removeConnections(); // handles removing tail from tail.prev
                    temp.insertNext(tail.mergeSameOrder(tree));
                    tail = temp.next;
                    maybeSetMin(tail);

                    break;
                } else {
                    // find the nodes that it lies in between
                    BinomialTree cur = root;
                    while (true) {
                        while (cur.next != null && cur.next.depth < tree.depth) {
                            cur = cur.next;
                        }
                        if (cur.next == null) {
                            // made it to the end
                            tail.insertNext(tree);
                            tail = tree;
                            maybeSetMin(tree);
                            break;
                        }
                        if (tree.depth < cur.next.depth) {
                            // just insert between
                            assert tree.depth != cur.depth;
                            cur.insertNext(tree);
                            maybeSetMin(tree);
                            break;
                        } else if (tree.depth == cur.next.depth) {
                            BinomialTree temp = cur.removeNext();
                            temp.removeConnections();

                            tree = tree.mergeSameOrder(temp);
                            maybeSetMin(tree);
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
            maybeSetMin(tree);
        }
    }

    // assmes this.root has been cleared
    private void mergeWith(BinomialTree root1, BinomialTree root2) {
        // the roots
        BinomialTree cur1 = root1; // goes forward
        BinomialTree cur2 = root2; // goes backwards

        // for swapping, etc
        BinomialTree temp = null;
        BinomialTree temp2 = null;

        while (cur1 != null || cur2 != null) {
            if (cur1 != null && cur2 != null) {
                temp = null;
                // merge the two first
                if (cur1.depth < cur2.depth) {
                    // cache the tree to merge and update cur1
                    temp = cur1;
                    cur1 = cur1.next;

                    temp.removeConnections();
                } else if (cur1.depth == cur2.depth) {
                    // cache cur1 and update cur1
                    temp = cur1;
                    cur1 = cur1.next;
                    // remove connections
                    temp.removeConnections();

                    // do same for cur2 (but use prev)
                    // cache the tree to merge and update cur1
                    temp2 = cur2;
                    cur2 = cur2.prev;
                    // remove connections
                    temp2.removeConnections();

                    // merge
                    temp = temp.mergeSameOrder(temp2);
                } else { // cur1.depth > cur2.depth
                    // cache the tree to merge and update cur1
                    temp = cur2;
                    cur2 = cur2.prev;

                    temp.removeConnections();
                }
            }
            // if only 1 of the roots still has smth, just merge that tree into the heap
            else if (cur1 != null) {
                // cache next and clear connections
                temp = cur1;
                cur1 = cur1.next;

                temp.removeConnections();
            } else {
                // cache prev and clear connections
                temp = cur2;
                cur2 = cur2.prev;
                temp.removeConnections();
            }

            // merge into this heap
            mergeTree(temp);
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

            if (temp.next != null) {
                curLevel.set(curLevelIdx, temp.next);
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
        BinomialTree lastSubtree;
        BinomialTree next;
        BinomialTree prev;
        int depth;
        boolean isChild;

        public BinomialTree(E val) {
            this.val = val;
        }

        public void addSubtree(BinomialTree subtree) {
            subtree.next = firstSubtree;
            if (firstSubtree != null) {
                firstSubtree.prev = subtree;
            } else {
                // no existing firstsubtree, so no existing last subtree
                lastSubtree = subtree;
            }
            firstSubtree = subtree;
            subtree.isChild = true;
        }

        public void removeConnections() {
            // remove any links to this
            if (next != null && next.prev != null && next.prev.equals(this)) {
                next.prev = null;
            }
            if (prev != null && prev.next != null && prev.next.equals(this)) {
                prev.next = null;
            }

            // remove any links from this
            next = null;
            prev = null;

            // also say that it's not a child
            isChild = false;
        }

        /**
         * Removes the next node and returns that node
         *
         * @return
         */
        public BinomialTree removeNext() {
            BinomialTree ret = next;

            // update links
            next = ret.next;
            if (ret.next != null) {
                ret.next.prev = this;
            }

            // clear ret's connections and return it
            ret.removeConnections();
            return ret;
        }

        public void setPrev(BinomialTree n) {
            if (prev == null) {
                prev = n;
                n.next = this;
            } else {
                // take care of the chance that there was smth at prev
                BinomialTree temp = prev;
                if (temp != null) {
                    temp.next = n;
                }
                n.prev = temp;

                // regular setting
                prev = n;
                n.next = this;
            }
        }

        public void insertNext(BinomialTree next) {
            if (this.next == null) {
                this.next = next;
                next.prev = this;
            } else {
                // known use case, so allow it
                BinomialTree temp = this.next;
                if (temp != null) {
                    temp.prev = next;
                }
                next.next = temp;

                // regular setting
                this.next = next;
                next.prev = this;

            }
        }

        public BinomialTree mergeSameOrder(BinomialTree o) {
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

            // then add nexts
            ret += "}";
            ret += next == null ? "" : "," + next;

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
            if (next != null) {
                ret |= next.contains(o);
            }
            return ret;
        }

        public boolean equals(BinomialTree o) {
            return this.val.equals(o.val) && depth == o.depth;
        }

        @Override
        public int hashCode() {
            return Objects.hash(val, depth);
        }
    }
}
