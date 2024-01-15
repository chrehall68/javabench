package sort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeSortTest extends SortTester {
    @Test
    void testSortGeneric() {
        Integer[] arr = generateIntegerArray(testSize);

        Integer[] mergeSortedArr = new MergeSort().sort(Arrays.copyOf(arr, arr.length));
        Arrays.sort(arr);
        assertArrayEquals(mergeSortedArr, arr);
    }

    @Test
    void testSortInt() {
        int[] arr = generateIntArray(testSize);
        int[] mergeSortedArr = new MergeSort().sort(Arrays.copyOf(arr, arr.length));
        Arrays.sort(arr);
        assertArrayEquals(mergeSortedArr, arr);
    }

    @Test
    void testSortDouble() {
        double[] arr = generateDoubleArray(testSize);
        double[] mergeSortedArr = new MergeSort().sort(Arrays.copyOf(arr, arr.length));
        Arrays.sort(arr);
        assertArrayEquals(mergeSortedArr, arr);
    }
}