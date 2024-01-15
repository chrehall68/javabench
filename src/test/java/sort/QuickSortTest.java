package sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


class QuickSortTest extends SortTester {
    @Test
    void testSortInt() {
        int[] arr = generateIntArray(testSize);
        int[] sorted = new QuickSort().sort(arr);

        assertEquals(arr, sorted);  // should be true since quicksort should be in place

        // make sure it is sorted
        int[] arraySorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arraySorted);
        assertArrayEquals(arraySorted, sorted);
    }

    @Test
    void testSortDouble() {
        double[] arr = generateDoubleArray(testSize);
        double[] sorted = new QuickSort().sort(arr);

        assertEquals(arr, sorted);  // should be true since quicksort should be in place

        // make sure it is sorted
        double[] arraySorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arraySorted);
        assertArrayEquals(arraySorted, sorted);
    }

    @Test
    void testSortInteger() {
        Integer[] arr = generateIntegerArray(testSize);
        Integer[] sorted = new QuickSort().sort(arr);

        assertEquals(arr, sorted);  // should be true since quicksort should be in place

        // make sure it is sorted
        Integer[] arraySorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arraySorted);
        assertArrayEquals(arraySorted, sorted);
    }
}