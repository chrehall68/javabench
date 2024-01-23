package structures;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class JBLinkedList<T> implements Deque<T> {

    // actual Linked List stuff
    private final Node sentinel;
    private int size;

    public JBLinkedList() {
        sentinel = new Node(null);
    }

    public T poll() {
        return sentinel.removeNext().getVal();
    }

    @Override
    public T pollFirst() {
        return this.poll();
    }

    @Override
    public T pollLast() {
        return sentinel.removePrev().getVal();
    }

    @Override
    public T peek() {
        return this.getFirst();
    }

    @Override
    public T peekFirst() {
        return isEmpty() ? null : sentinel.getNext().getVal();
    }

    @Override
    public T peekLast() {
        return isEmpty() ? null : sentinel.getPrev().getVal();
    }

    @Override
    public T getFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return sentinel.getNext().getVal();
    }

    @Override
    public T getLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return sentinel.getPrev().getVal();
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.poll();
    }

    @Override
    public T removeFirst() {
        return this.pop();
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.pollLast();
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    // ==============================
    // Add Section
    // ==============================
    @Override
    public boolean add(T e) {
        sentinel.insertPrev(e);
        ++size;
        return true;
    }

    @Override
    public void push(T e) {
        sentinel.insertNext(e);
        ++size;
    }

    @Override
    public void addFirst(T e) {
        push(e);
    }

    @Override
    public void addLast(T e) {
        add(e);
    }

    @Override
    public boolean offer(T e) {
        return add(e);
    }

    @Override
    public boolean offerFirst(T e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(T e) {
        addLast(e);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T t : c) {
            add(t);
        }
        return true;
    }

    // ==============================
    // Removal Section
    // ==============================
    @Override
    public void clear() {
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        this.size = 0;
    }

    @Override
    public boolean remove(Object o) {
        Iterator<T> iterator = new JBLinkedListForwardIterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(o)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        Iterator<T> iterator = new JBLinkedListBackwardsIterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(o)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    // iterator
    @Override
    public Iterator<T> iterator() {
        return new JBLinkedListForwardIterator();
    }

    @Override
    public Iterator<T> descendingIterator() {
        return new JBLinkedListBackwardsIterator();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Iterator<T> iterator = new JBLinkedListForwardIterator();
        boolean changed = false;

        while (iterator.hasNext()) {
            // remove it
            T element = iterator.next();
            if (!c.contains(element)) {
                iterator.remove();
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Iterator<T> iterator = new JBLinkedListForwardIterator();
        boolean changed = false;

        while (iterator.hasNext()) {
            // remove it if it is w/i c
            T element = iterator.next();
            if (c.contains(element)) {
                iterator.remove();
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        Iterator<T> iterator = new JBLinkedListForwardIterator();
        boolean changed = false;

        while (iterator.hasNext()) {
            T element = iterator.next();
            if (filter.test(element)) {
                iterator.remove();
                changed = true;
            }
        }

        return changed;
    }

    @Override
    public boolean contains(Object o) {
        for (T element : this) {
            if (element.equals(o)) {
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
    public Object[] toArray() {
        Object[] arr = new Object[this.size];
        return this.toArray(arr);
    }

    public <U> U[] toArray(U[] a) {
        // potentially resize
        U[] ret = a;
        if (a.length < this.size) {
            ret = (U[]) Array.newInstance(a.getClass().getComponentType(), this.size);
        }

        // iterate through and store values
        int i = 0;
        for (T element : this) {
            ret[i++] = (U) element;
        }
        return ret;
    }

    @Override
    public T element() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return peek();
    }

    // toString
    public String toString() {
        StringBuilder ret = new StringBuilder("[");
        Iterator<T> it = new JBLinkedListForwardIterator();

        if (it.hasNext()) {
            T element = it.next();
            while (it.hasNext()) {
                ret.append(element.toString()).append(", ");
                element = it.next();
            }
            // last bit of the loop for pretty formatting
            ret.append(element.toString());
        }
        ret.append("]");
        return ret.toString();
    }

    private class Node {
        private final T val;
        private Node next;
        private Node prev;

        public Node(T val) {
            this.val = val;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrev() {
            return prev;
        }

        public T getVal() {
            return val;
        }

        /**
         * Removes the next node
         *
         * @return the removed node
         */
        public Node removeNext() {
            // store val
            Node node = next;
            // decrement size
            size = Math.min(--size, 0);

            // remove connections
            next = next.next;
            next.next.prev = this;
            return node;
        }

        /**
         * Removes the previous node
         *
         * @return the removed node
         */
        public Node removePrev() {
            // store val
            Node node = prev;
            // decrement size
            size = Math.min(--size, 0);

            // remove connections
            prev = prev.prev;
            prev.prev.next = this;
            return node;
        }

        public void insertNext(T val) {
            // set temp's connections
            Node temp = new Node(val);
            temp.prev = this;
            temp.next = next;

            // set our connections
            next.prev = temp;
            next = temp;
        }

        public void insertPrev(T val) {
            // set temp's connections
            Node temp = new Node(val);
            temp.next = this;
            temp.prev = prev;

            // set our connections
            prev.next = temp;
            prev = temp;
        }
    }

    private class JBLinkedListForwardIterator implements Iterator<T> {
        private Node cur;

        public JBLinkedListForwardIterator() {
            cur = sentinel;
        }

        @Override
        public boolean hasNext() {
            return cur.getNext() != sentinel;  // address comparison is fine here since sentinel's address will never change
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            cur = cur.getNext();
            return cur.getVal();
        }

        @Override
        public void remove() {
            cur = cur.getPrev().removeNext();
        }
    }

    private class JBLinkedListBackwardsIterator implements Iterator<T> {
        private Node cur;

        public JBLinkedListBackwardsIterator() {
            cur = sentinel;
        }

        @Override
        public boolean hasNext() {
            return cur.prev != sentinel;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            cur = cur.getPrev();
            return cur.getVal();
        }

        @Override
        public void remove() {
            cur = cur.getNext().removePrev();
        }
    }
}
