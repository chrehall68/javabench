package sort;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import resources.ArrayGenerator;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RadixTest {
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 9, 10, 13})
    void testSortInt(int base) {
        int[] arr = ArrayGenerator.generateIntArray();

        int[] sorted = new Radix().sort(arr, base);

        assertEquals(arr, sorted);  // make sure in-place

        // make sure it is sorted
        int[] arraySorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arraySorted);
        assertArrayEquals(arraySorted, sorted);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 9, 10, 13})
    void testSortInteger(int base) {
        Integer[] arr = ArrayGenerator.generateIntegerArray();

        Integer[] sorted = new Radix().sort(arr, base);

        assertEquals(arr, sorted);  // make sure in-place

        // make sure it is sorted
        Integer[] arraySorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arraySorted);
        assertArrayEquals(arraySorted, sorted);
    }
}