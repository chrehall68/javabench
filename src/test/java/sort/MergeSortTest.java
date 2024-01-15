package sort;

import org.junit.jupiter.api.Test;
import resources.ArrayGenerator;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeSortTest {
    @Test
    void testSortGeneric() {
        Integer[] arr = ArrayGenerator.generateIntegerArray();

        Integer[] mergeSortedArr = new MergeSort().sort(Arrays.copyOf(arr, arr.length));
        Arrays.sort(arr);
        assertArrayEquals(mergeSortedArr, arr);
    }

    @Test
    void testSortInt() {
        int[] arr = ArrayGenerator.generateIntArray();
        int[] mergeSortedArr = new MergeSort().sort(Arrays.copyOf(arr, arr.length));
        Arrays.sort(arr);
        assertArrayEquals(mergeSortedArr, arr);
    }

    @Test
    void testSortDouble() {
        double[] arr = ArrayGenerator.generateDoubleArray();
        double[] mergeSortedArr = new MergeSort().sort(Arrays.copyOf(arr, arr.length));
        Arrays.sort(arr);
        assertArrayEquals(mergeSortedArr, arr);
    }
}