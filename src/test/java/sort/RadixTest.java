package sort;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import resources.ArrayGenerator;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RadixTest {
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 9, 10, 13})
    void testSortInt(int base) {
        int[] arr = ArrayGenerator.generateIntArray();

        new Radix().sort(arr, base);

        // make sure it is sorted
        int[] arraySorted = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arraySorted);
        assertArrayEquals(arraySorted, arr);
    }
}