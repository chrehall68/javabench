package sort;

import org.junit.jupiter.api.Test;
import resources.ArrayGenerator;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ShellSortTest {
    @Test
    void testSortInt() {
        int[] arr = ArrayGenerator.generateIntArray();
        int[] sorted = new ShellSort().sort(arr);

        assertEquals(arr, sorted);  // should be true since shellsort should be in place

        // make sure it is sorted
        int[] arraySorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arraySorted);
        assertArrayEquals(arraySorted, sorted);
    }

    @Test
    void testSortDouble() {
        double[] arr = ArrayGenerator.generateDoubleArray();
        double[] sorted = new ShellSort().sort(arr);

        assertEquals(arr, sorted);  // should be true since shellsort should be in place

        // make sure it is sorted
        double[] arraySorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arraySorted);
        assertArrayEquals(arraySorted, sorted);
    }

    @Test
    void testSortInteger() {
        Integer[] arr = ArrayGenerator.generateIntegerArray();
        Integer[] sorted = new ShellSort().sort(arr);

        assertEquals(arr, sorted);  // should be true since shellsort should be in place

        // make sure it is sorted
        Integer[] arraySorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arraySorted);
        assertArrayEquals(arraySorted, sorted);
    }
}