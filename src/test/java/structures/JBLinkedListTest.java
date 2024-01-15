package structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static resources.ArrayGenerator.generateIntegerArray;

class JBLinkedListTest {

    private LinkedList<Integer> ll;
    private Integer[] arr;

    @BeforeEach
    void init() {
        arr = generateIntegerArray();
        ll = new LinkedList<>();
    }

    @Test
    void pollFirst() {
        ll.addLast(arr[0]);
        for (int i = 1; i < arr.length; ++i) {
            ll.addLast(arr[i]);

            assertEquals(arr[i - 1], ll.pollFirst());
        }
        assertEquals(arr[arr.length - 1], ll.pollFirst());
    }

    @Test
    void pollLast() {
        ll.addFirst(arr[0]);
        for (int i = 1; i < arr.length; ++i) {
            ll.addFirst(arr[i]);

            assertEquals(arr[i - 1], ll.pollLast());
        }
        assertEquals(arr[arr.length - 1], ll.pollLast());
    }

    @Test
    void size() {
        for (int i = 0; i < arr.length; ++i) {
            ll.addFirst(i);
            assertEquals(i + 1, ll.size());
        }
        for (int i = arr.length - 1; i > -1; --i) {
            ll.pollLast();
            assertEquals(i, ll.size());
        }
    }

    @Test
    void addFirst() {
        for (Integer i : arr) {
            ll.addFirst(i);
            assertEquals(i, ll.peekFirst());
        }
    }

    @Test
    void addLast() {
        for (Integer i : arr) {
            ll.addLast(i);
            assertEquals(i, ll.peekLast());
        }
    }
}