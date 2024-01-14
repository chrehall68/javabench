package structures;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

@SuppressWarnings("unchecked")
public class ArrayQueue<E> implements Queue<E> {
    private class ArrayQueueIterator implements Iterator<E> {
        private int cur;

        public ArrayQueueIterator() {
            cur = first;
        }

        @Override
        public boolean hasNext() {
            return cur != last;
        }

        @Override
        public E next() {
            return (E) arr[(cur++) % arr.length];
        }
    }

    private Object[] arr;
    private int first;
    private int last;
    private int size;
    private static final int INITIAL_SIZE = 16;

    public ArrayQueue() {
        this(INITIAL_SIZE);
    }

    public ArrayQueue(int initialSize) {
        arr = new Object[initialSize];
    }

    // ==============================
    // Contains/Remove Section
    // ==============================
    @Override
    public boolean contains(Object o) {
        for (E e : this) {
            if (e.equals(o)) {
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
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    // ==============================
    // Misc Section
    // ==============================
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
        for (int i = first; i != last; ++i) {
            ret[i - first] = (T) arr[i % arr.length];
        }
        return ret;
    }

    @Override
    public void clear() {
        first = 0;
        last = 0;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    public int maxSize() {
        return arr.length;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayQueueIterator();
    }

    public String toString() {
        String ret = "{";
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            ret += it.next();
            if (it.hasNext()) {
                ret += ", ";
            }
        }
        ret += "}";

        return ret;
    }

    // ==============================
    // Poll Section
    // ==============================
    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        --size;
        return (E) arr[(first++) % arr.length];
    }

    @Override
    public E remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return poll();
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return (E) arr[(first++) % arr.length];
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
        arr[(last++) % arr.length] = e;
        ++size;

        maybeResize();
        maybeReduce();
        return true;
    }

    private void maybeReduce() {
        if (first > arr.length && last > arr.length) {
            first %= arr.length;
            last = first + size;
        }
    }

    private void maybeResize() {
        if (size == arr.length) {
            resize();
        }
    }

    private void resize() {
        Object[] nextArr = new Object[arr.length * 2];
        toArray(nextArr);
        arr = nextArr;

        first = 0;
        last = size;

    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // resize beforehand if necessary to prevent more operations
        if (arr.length < size + c.size()) {
            resize();
        }

        for (E e : c) {
            add(e);
        }
        return true;
    }

    @Override
    public boolean offer(E e) {
        return add(e);
    }

}
