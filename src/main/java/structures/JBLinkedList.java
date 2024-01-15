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
    private Node head;
    private Node tail;
    private int size;

    @Override
    public T poll() {
        if (this.head == null) {
            return null;
        }
        Node ret = head;
        removeNode(ret);
        return ret.getVal();
    }

    @Override
    public T pollFirst() {
        return this.poll();
    }

    @Override
    public T pollLast() {
        if (this.tail == null) {
            return null;
        }
        Node ret = tail;
        removeNode(ret);
        return ret.getVal();
    }

    @Override
    public T peek() {
        return this.getFirst();
    }

    @Override
    public T peekFirst() {
        return this.head == null ? null : this.head.getVal();
    }

    @Override
    public T peekLast() {
        return this.tail == null ? null : this.tail.getVal();
    }

    @Override
    public T getFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.head.getVal();
    }

    @Override
    public T getLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.tail.getVal();
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

    // add section
    @Override
    public boolean add(T e) {
        Node temp = new Node(e);
        if (this.head == null) {
            this.head = temp;
        }

        if (this.tail == null) {
            this.tail = temp;
        } else {
            this.tail.setNext(temp);
            temp.setPrev(this.tail);
            this.tail = temp;
        }

        ++size;
        return true;
    }

    @Override
    public void push(T e) {
        Node temp = new Node(e);
        if (this.tail == null) {
            this.tail = temp;
        }
        if (this.head == null) {
            this.head = temp;
        } else {
            this.head.setPrev(temp);
            temp.setNext(this.head);
            this.head = temp;
        }
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

    // remove section
    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public boolean remove(Object o) {
        Node cur = this.head;
        while (cur != null) {
            if (cur.getVal().equals(o)) {
                removeNode(cur);
                return true;
            }
            cur = cur.getNext();
        }
        return false;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        Node cur = this.tail;
        while (cur != null) {
            if (cur.getVal().equals(o)) {
                removeNode(cur);
                return true;
            }
            cur = cur.getPrev();
        }
        return false;
    }

    // iterator
    @Override
    public Iterator<T> iterator() {
        return new JBLinkedListForwardIterator(this.head);
    }

    @Override
    public Iterator<T> descendingIterator() {
        return new JBLinkedListBackwardsIterator(this.tail);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Node cur = this.head;
        boolean changed = false;
        while (cur != null) {
            // remove it
            if (!c.contains(cur.getVal())) {
                Node temp = cur.getNext();
                removeNode(cur);
                cur = temp;

                changed = true;
            } else { // it is contained in c
                cur = cur.getNext();
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Node cur = this.head;
        boolean changed = false;
        while (cur != null) {
            // remove it if it is w/i c
            if (c.contains(cur.getVal())) {
                Node temp = cur.getNext();
                removeNode(cur);
                cur = temp;

                changed = true;
            } else {
                cur = cur.getNext();
            }
        }
        return changed;
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        // TODO change
        return Deque.super.removeIf(filter);
    }

    @Override
    public boolean contains(Object o) {
        Node cur = this.head;
        while (cur != null) {
            if (cur.getVal().equals(o)) {
                return true;
            }
            cur = cur.getNext();
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
        Node cur = this.head;
        for (int i = 0; i < this.size; ++i) {
            arr[i] = cur.getVal();
            cur = cur.getNext();
        }
        return arr;
    }

    public <U extends Object> U[] toArray(U[] a) {
        U[] ret = a;
        if (a.length < this.size) {
            ret = (U[]) Array.newInstance(a.getClass().getComponentType(), this.size);
        }
        Node cur = this.head;
        for (int i = 0; i < this.size; ++i) {
            ret[i] = (U) cur.getVal();
            cur.getNext();
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

    // Private Helper Section
    private void removeNode(Node node) {
        Node prev = node.getPrev();
        Node next = node.getNext();
        if (prev != null) {
            prev.setNext(next);
        }
        if (next != null) {
            next.setPrev(prev);
        }

        if (node == this.tail && node == this.head) {
            this.tail = null;
            this.head = null;
        } else if (node == this.head) {
            this.head = next;
        } else {
            this.tail = prev;
        }

        --size;
    }

    // toString
    public String toString() {
        String ret = "[";
        Node cur = this.head;
        for (int i = 0; i < this.size - 1; ++i) {
            ret += cur.getVal() + ", ";
            cur = cur.getNext();
        }
        ret += cur.getVal() + "]";
        return ret;
    }

    private class Node {
        private final T val;
        private Node next;
        private Node prev;

        public Node() {
            this.val = null;
        }

        public Node(T val) {
            this.val = val;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public T getVal() {
            return val;
        }
    }

    private class JBLinkedListForwardIterator implements Iterator<T> {
        Node cur;

        public JBLinkedListForwardIterator(Node cur) {
            this.cur = new Node();
            this.cur.setNext(cur);
        }

        @Override
        public boolean hasNext() {
            return cur != null && cur.getNext() != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            cur = cur.getNext();
            return cur.getVal();
        }
    }

    private class JBLinkedListBackwardsIterator implements Iterator<T> {
        Node cur;

        public JBLinkedListBackwardsIterator(Node cur) {
            this.cur = new Node();
            this.cur.setPrev(cur);
        }

        @Override
        public boolean hasNext() {
            return cur != null && cur.getPrev() != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            cur = cur.getPrev();
            return cur.getVal();
        }
    }
}
