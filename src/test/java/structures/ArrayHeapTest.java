package structures;

import org.junit.jupiter.api.Test;
import resources.ArrayGenerator;

import java.util.Collections;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArrayHeapTest {

    @Test
    void contains() {
        Integer[] arr = ArrayGenerator.generateIntegerArray();
        ArrayHeap<Integer> arrayHeap = new ArrayHeap<>();
        Collections.addAll(arrayHeap, arr);
        for (Integer i : arr) {
            assertTrue(arrayHeap.contains(i));
        }
    }


    @Test
    void clear() {
        Integer[] arr = ArrayGenerator.generateIntegerArray();
        ArrayHeap<Integer> arrayHeap = new ArrayHeap<>();
        Collections.addAll(arrayHeap, arr);
        arrayHeap.clear();
        assertEquals(0, arrayHeap.toArray().length);
    }

    @Test
    void size() {
        Integer[] arr = ArrayGenerator.generateIntegerArray();
        ArrayHeap<Integer> arrayHeap = new ArrayHeap<>();
        for (int i = 0; i < arr.length; ++i) {
            arrayHeap.add(arr[i]);
            assertEquals(i + 1, arrayHeap.size());
        }
    }

    @Test
    void isEmpty() {
        ArrayHeap<Integer> arrayHeap = new ArrayHeap<>();
        assertTrue(arrayHeap.isEmpty());
        Integer[] arr = ArrayGenerator.generateIntegerArray();
        for (Integer integer : arr) {
            arrayHeap.add(integer);
            arrayHeap.poll();
            assertTrue(arrayHeap.isEmpty());
        }
    }

    @Test
    void poll() {
        Integer[] arr = ArrayGenerator.generateIntegerArray();
        ArrayHeap<Integer> arrayHeap = new ArrayHeap<>(Comparator.reverseOrder());
        Collections.addAll(arrayHeap, arr);
        Integer prev = Integer.MIN_VALUE;
        while (!arrayHeap.isEmpty()) {
            Integer cur = arrayHeap.poll();
            assertTrue(prev.compareTo(cur) <= 0);
            prev = cur;
        }
    }
}