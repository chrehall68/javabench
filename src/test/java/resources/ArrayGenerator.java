package resources;

import java.util.Random;

public class ArrayGenerator {
    private static final int testSize = (int) 1e4;  // length of tests
    private static final int maxIntVal = (int) 5e3;
    private static final int minIntVal = -(int) 5e3;
    private static final double maxDoubleVal = maxIntVal;
    private static final double minDoubleVal = minIntVal;

    public static int[] generateIntArray() {
        int[] arr = new int[testSize];
        Random r = new Random();
        for (int i = 0; i < testSize; ++i) {
            arr[i] = r.nextInt(maxIntVal - minIntVal) + minIntVal;
        }

        return arr;
    }

    public static double[] generateDoubleArray() {
        double[] arr = new double[testSize];
        Random r = new Random();
        for (int i = 0; i < testSize; ++i) {
            arr[i] = r.nextDouble() * (maxDoubleVal - minDoubleVal) + minDoubleVal;
        }

        return arr;
    }

    public static Integer[] generateIntegerArray() {
        int[] temp = generateIntArray();
        Integer[] arr = new Integer[testSize];
        for (int i = 0; i < testSize; ++i) {
            arr[i] = temp[i];
        }
        return arr;
    }
}