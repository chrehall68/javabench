package structures;

import java.util.*;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class ArrayHeap<E> implements Queue<E> {
    // ==============================
    // Constructor/Attribute Section
    // ==============================
    private static final int INITIAL_SIZE = 15;
    private E[] heap;
    private int last;
    private Comparator<? super E> comparator;
    private int height;

    /**
     * Creates a Max Heap by default. To change this, pass Comparator.reverseOrder()
     */
    public ArrayHeap() {
        this(INITIAL_SIZE);
    }

    public ArrayHeap(int initialSize) {
        this(initialSize, null);
    }

    public ArrayHeap(Comparator<? super E> comparator) {
        this(INITIAL_SIZE, comparator);
    }

    public ArrayHeap(int initialSize, Comparator<? super E> comparator) {
        this.comparator = comparator;

        // we want an initial size of 2^n - 1
        // so we use the initial size as a guideline
        height = 0;
        while ((1 << height) - 1 < initialSize) {
            ++height;
        }
        heap = (E[]) new Object[(1 << height) - 1];
    }

    private ArrayHeap(Object[] arr) {
        heap = (E[]) arr;
        last = arr.length;
    }

    /**
     * Sorts arr in-place in increasing order
     *
     * @param <E> - type of the array
     * @param arr - the array to be sorted, in-place
     * @return arr - the input array, sorted in-place
     */
    public static <E> E[] heapSort(E[] arr) {
        ArrayHeap<E> h = new ArrayHeap<>(arr);
        // first, heapify
        for (int i = arr.length - 1; i > -1; --i) {
            h.heapifyDown(i);
        }

        // next, extract maxes and put them at the end
        for (int i = arr.length - 1; i > -1; --i) {
            arr[i] = h.poll();
        }
        return arr;
    }

    // ==============================
    // Misc Section
    // ==============================
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(heap, last);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        T[] ret = a;
        if (a.length < last) {
            ret = (T[]) new Object[last];
        }
        for (int i = 0; i < last; ++i) {
            ret[i] = (T) heap[i];
        }
        return ret;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < last; ++i) {
            if (heap[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return !isEmpty();
    }

    @Override
    public void clear() {
        last = 0;
    }

    @Override
    public int size() {
        return last;
    }

    @Override
    public boolean isEmpty() {
        return last == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new HeapIterator(this);
    }

    public String toString() {
        return "Heap (size: " + size() + ", first:" + peek() + ")";
    }

    // ==============================
    // Get Section
    // ==============================
    @Override
    public E poll() {
        if (last == 0) {
            return null;
        } else {
            // store the val
            E ret = heap[0];

            // replace the val in our heap w/ the last inserted node
            heap[0] = heap[--last];

            // heapify downwards to make sure our heap is still valid
            heapifyDown(0);
            return ret;
        }
    }

    @Override
    public E remove() {
        if (last == 0) {
            throw new NoSuchElementException();
        }
        return poll();
    }

    @Override
    public E peek() {
        return last == 0 ? null : heap[0];
    }

    @Override
    public E element() {
        if (last == 0) {
            throw new NoSuchElementException();
        }
        return peek();
    }

    // ==============================
    // Remove section
    // ==============================
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        for (Object o : c) {
            if (contains(o)) {
                remove(o);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean remove(Object o) {
        boolean removed = false;
        for (int i = 0; i < last; ++i) {
            if (heap[i].equals(o)) {
                removed = true;
                // store last inserted node here
                heap[i] = heap[--last];
                // heapify down
                heapifyDown(i);
                break;
            }
        }
        return removed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean anyRemoved = false;
        for (Object o : c) {
            anyRemoved = remove(o) || anyRemoved;
        }
        return anyRemoved;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        int i = 0;
        boolean changed = false;
        while (i < last && last > 0) {
            if (filter.test(heap[i])) {
                // remove it
                heap[i] = heap[--last];
                heapifyDown(i);
                changed = true;
            } else {
                // only increment if we didn't remove the current element
                ++i;
            }
        }
        return changed;
    }

    // ==============================
    // Add Section
    // ==============================
    @Override
    public boolean add(E e) {
        // potentially resize
        if (last == heap.length) {
            resizeHeap();
        }

        // store element
        heap[last++] = e;
        // heapify
        heapifyUp(last - 1);

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        c.forEach(this::add);
        return true;
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

    /**
     * Heapify starting from idx and going to children
     *
     * @param idx - idx to start from
     */
    private void heapifyDown(int idx) {
        // invalid idx
        if (idx >= last) {
            return;
        }

        // check the two children
        int leftChild = (idx + 1) * 2 - 1;
        int rightChild = (idx + 1) * 2;

        int largestIdx = idx;
        if (leftChild < last && compare(heap[leftChild], heap[largestIdx]) > 0) {
            // left child is bigger
            largestIdx = leftChild;
        }
        if (rightChild < last && compare(heap[rightChild], heap[largestIdx]) > 0) {
            // right child is bigger
            largestIdx = rightChild;
        }

        // swap current w/ largest if needed
        if (largestIdx != idx) {
            // do the swap
            E temp = heap[largestIdx];
            heap[largestIdx] = heap[idx];
            heap[idx] = temp;

            // heapify downwards
            heapifyDown(largestIdx);
        }
    }

    // ==============================
    // Internal Section
    // ==============================

    /**
     * Heapify starting from idx and going up to parents
     * Should be used after insertions
     *
     * @param idx - the idx of the recently inserted node
     */
    private void heapifyUp(int idx) {
        // invalid idx
        if (idx >= last) {
            return;
        }
        if (idx == 0) {
            return;
        }
        // 0
        // 1 2
        // 3 4 5 6
        int parent = idx / 2 - (1 - (idx & 0b1));

        // check the two children
        int leftChild = (parent + 1) * 2 - 1;
        int rightChild = (parent + 1) * 2;

        int largestIdx = parent;
        if (leftChild < last && compare(heap[leftChild], heap[largestIdx]) > 0) {
            // left child is bigger
            largestIdx = leftChild;
        }
        if (rightChild < last && compare(heap[rightChild], heap[largestIdx]) > 0) {
            // right child is bigger
            largestIdx = rightChild;
        }

        // swap parent w/ largest if needed
        if (largestIdx != parent) {
            // do the swap
            E temp = heap[largestIdx];
            heap[largestIdx] = heap[parent];
            heap[parent] = temp;

            // heapify upwards if the parent isn't the root
            // (if parent is root, nothing more to go up to)
            if (parent != 0) {
                heapifyUp(parent);
            }
        }
    }

    private int compare(E lhs, E rhs) {
        return comparator == null ? ((Comparable<E>) lhs).compareTo(rhs) : comparator.compare(lhs, rhs);
    }

    private void resizeHeap() {
        // add 1 layer of height
        heap = Arrays.copyOf(heap, (1 << ++height) - 1);
    }

    private class HeapIterator implements Iterator<E> {
        int idx;
        ArrayHeap<E> h;

        public HeapIterator(ArrayHeap<E> h) {
            this.h = h;
            idx = 0;
        }

        @Override
        public E next() {
            return h.heap[idx++];
        }

        @Override
        public boolean hasNext() {
            return idx < h.last;
        }

        @Override
        public void remove() {
            h.remove(h.heap[--idx]);
        }
    }
}
