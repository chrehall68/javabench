package sort;

import org.junit.jupiter.api.Test;
import resources.ArrayGenerator;
import structures.ArrayHeap;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeapSortTest {
    @Test
    public void sort() {
        Integer[] arr = ArrayGenerator.generateIntegerArray();

        assertEquals(arr, ArrayHeap.heapSort(arr));
        Object[] sorted = Arrays.stream(Arrays.copyOf(arr, arr.length)).sorted().toArray();
        assertArrayEquals(arr, sorted);
    }
}
