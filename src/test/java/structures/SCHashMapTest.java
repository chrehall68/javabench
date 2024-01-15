package structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static resources.ArrayGenerator.generateIntegerArray;

class SCHashMapTest {
    private SCHashMap<Integer, Integer> scHashMap;
    private Integer[] arr;

    @BeforeEach
    void init() {
        scHashMap = new SCHashMap<>();
        arr = generateIntegerArray();
    }

    @Test
    void remove() {
        scHashMap.put(33, 33);
        scHashMap.remove(33);
        assertNull(scHashMap.get(33));
    }

    @Test
    void putGet() {
        scHashMap.put(33, 33);
        assertEquals(33, scHashMap.get(33));
        scHashMap.put(33, 35);
        assertEquals(35, scHashMap.get(33));
    }

    @Test
    void size() {
        for (Integer i : arr) {
            scHashMap.put(i, i);
        }
        assertEquals(Arrays.stream(arr).distinct().count(), scHashMap.size());
    }

    @Test
    void clear() {
        for (Integer i : arr) {
            scHashMap.put(i, i);
        }
        scHashMap.clear();
        for (Integer i : arr) {
            assertNull(scHashMap.get(i));
        }
    }
}