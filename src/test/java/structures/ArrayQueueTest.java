package structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static resources.ArrayGenerator.generateIntegerArray;

class ArrayQueueTest {
    private ArrayQueue<Integer> arrayQueue;
    private Integer[] arr;

    @BeforeEach
    void init() {
        arrayQueue = new ArrayQueue<>();
        arr = generateIntegerArray();
    }

    @Test
    void clear() {
        Collections.addAll(arrayQueue, arr);
        arrayQueue.clear();
        assertNull(arrayQueue.poll());
    }

    @Test
    void size() {
        for (int i = 0; i < arr.length; ++i) {
            arrayQueue.add(arr[i]);
            assertEquals(i + 1, arrayQueue.size());
        }
        for (int i = arr.length - 1; i > -1; --i) {
            arrayQueue.poll();
            assertEquals(i, arrayQueue.size());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 10})
    void poll(int k) {
        arrayQueue.addAll(Arrays.asList(arr).subList(0, k));
        for (int i = k; i < arr.length; ++i) {
            arrayQueue.add(arr[i]);
            assertEquals(arr[i - k], arrayQueue.poll());
        }
        for (int i = k - 1; i > -1; --i) {
            assertEquals(arr[arr.length - 1 - i], arrayQueue.poll());
        }
    }
}