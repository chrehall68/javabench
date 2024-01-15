package sort;

import java.util.Random;

public abstract class SortTester {
    protected static final int testSize = (int) 1e4;  // length of tests
    private static final int maxIntVal = (int) 5e3;
    private static final int minIntVal = -(int) 5e3;
    private static final double maxDoubleVal = maxIntVal;
    private static final double minDoubleVal = minIntVal;

    public static int[] generateIntArray(int size) {
        int[] arr = new int[size];
        Random r = new Random();
        for (int i = 0; i < size; ++i) {
            arr[i] = r.nextInt(maxIntVal - minIntVal) + minIntVal;
        }

        return arr;
    }

    public static double[] generateDoubleArray(int size) {
        double[] arr = new double[size];
        Random r = new Random();
        for (int i = 0; i < size; ++i) {
            arr[i] = r.nextDouble() * (maxDoubleVal - minDoubleVal) + minDoubleVal;
        }

        return arr;
    }

    public static Integer[] generateIntegerArray(int size) {
        int[] temp = generateIntArray(size);
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; ++i) {
            arr[i] = temp[i];
        }
        return arr;
    }
}