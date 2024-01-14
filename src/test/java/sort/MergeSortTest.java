package sort;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest extends  SortTester{
    @Test
    void testSortGeneric() {
        Integer[] arr = generateIntegerArray(testSize);

        Integer[] mergeSortedArr = MergeSort.sort(Arrays.copyOf(arr, arr.length));
        Arrays.sort(arr);
        assertArrayEquals(mergeSortedArr, arr);
    }

    @Test
    void testSortInt() {
        int[] arr = generateIntArray(testSize);
        int[] mergeSortedArr = MergeSort.sort(Arrays.copyOf(arr, arr.length));
        Arrays.sort(arr);
        assertArrayEquals(mergeSortedArr, arr);
    }

    @Test
    void testSortDouble() {
        double[] arr = generateDoubleArray(testSize);
        double[] mergeSortedArr = MergeSort.sort(Arrays.copyOf(arr, arr.length));
        Arrays.sort(arr);
        assertArrayEquals(mergeSortedArr, arr);
    }
}