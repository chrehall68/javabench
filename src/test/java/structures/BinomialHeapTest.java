package structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static resources.ArrayGenerator.generateIntegerArray;

class BinomialHeapTest {
    private BinomialHeap<Integer> binomialHeap;
    private Integer[] arr;

    @BeforeEach
    void init() {
        binomialHeap = new BinomialHeap<>();
        arr = generateIntegerArray();
    }

    @Test
    void clear() {
        Collections.addAll(binomialHeap, arr);
        binomialHeap.clear();
        assertNull(binomialHeap.poll());
    }

    @Test
    void size() {
        for (int i = 0; i < arr.length; ++i) {
            binomialHeap.add(arr[i]);
            assertEquals(i + 1, binomialHeap.size());
        }
        for (int i = arr.length - 1; i > -1; --i) {
            assertNotNull(binomialHeap.poll());
            assertEquals(i, binomialHeap.size());
        }
    }

    @Test
    void poll() {
        Collections.addAll(binomialHeap, arr);

        Integer prev = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; ++i) {
            Integer temp = binomialHeap.poll();

            assertNotNull(temp);
            assertTrue(prev.compareTo(temp) <= 0);
            prev = temp;
        }
    }
}