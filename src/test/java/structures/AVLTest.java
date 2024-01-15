package structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static resources.ArrayGenerator.generateIntegerArray;

class AVLTest {
    private AVL<Integer, Integer> avl;
    private Integer[] arr;

    @BeforeEach
    void init() {
        avl = new AVL<>();
        arr = generateIntegerArray();
    }

    @Test
    void remove() {
        avl.put(33, 33);
        avl.remove(33);
        assertNull(avl.get(33));
    }

    @Test
    void putGet() {
        avl.put(33, 33);
        assertEquals(33, avl.get(33));
        avl.put(33, 35);
        assertEquals(35, avl.get(33));
    }

    @Test
    void size() {
        for (Integer i : arr) {
            avl.put(i, i);
        }
        assertEquals(Arrays.stream(arr).distinct().count(), avl.size());
    }

    @Test
    void clear() {
        for (Integer i : arr) {
            avl.put(i, i);
        }
        avl.clear();
        for (Integer i : arr) {
            assertNull(avl.get(i));
        }
    }
}