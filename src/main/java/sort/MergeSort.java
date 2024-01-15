package sort;

import java.util.Arrays;

public class MergeSort implements ISorter {
    /**
     * Sorts the array using mergesort
     *
     * @param arr - array to be sorted
     * @param <T> - type of array to sort
     */
    public <T extends Comparable<T>> T[] sort(T[] arr) {
        // recursive case
        if (arr.length > 1) {
            int half = arr.length / 2;
            T[] sortedFirstHalf = sort(Arrays.copyOfRange(arr, 0, half));
            T[] sortedSecondHalf = sort(Arrays.copyOfRange(arr, half, arr.length));

            int idx1 = 0;
            int idx2 = 0;
            while (idx1 < sortedFirstHalf.length || idx2 < sortedSecondHalf.length) {
                if (idx1 < sortedFirstHalf.length && idx2 < sortedSecondHalf.length) {
                    if (sortedFirstHalf[idx1].compareTo(sortedSecondHalf[idx2]) < 0) {
                        arr[idx1 + idx2] = sortedFirstHalf[idx1];
                        ++idx1;
                    } else {
                        arr[idx1 + idx2] = sortedSecondHalf[idx2];
                        ++idx2;
                    }
                } else if (idx1 < sortedFirstHalf.length) {
                    arr[idx1 + idx2] = sortedFirstHalf[idx1];
                    ++idx1;
                } else {
                    arr[idx1 + idx2] = sortedSecondHalf[idx2];
                    ++idx2;
                }
            }
        }
        return arr;  // base (or recursive) case
    }

    /**
     * Sorts the array using mergesort for primitive ints
     *
     * @param arr - array to be sorted
     */
    public int[] sort(int[] arr) {
        // recursive case
        if (arr.length > 1) {
            int half = arr.length / 2;
            int[] sortedFirstHalf = sort(Arrays.copyOfRange(arr, 0, half));
            int[] sortedSecondHalf = sort(Arrays.copyOfRange(arr, half, arr.length));

            int idx1 = 0;
            int idx2 = 0;
            while (idx1 < sortedFirstHalf.length || idx2 < sortedSecondHalf.length) {
                if (idx1 < sortedFirstHalf.length && idx2 < sortedSecondHalf.length) {
                    if (sortedFirstHalf[idx1] < sortedSecondHalf[idx2]) {
                        arr[idx1 + idx2] = sortedFirstHalf[idx1];
                        ++idx1;
                    } else {
                        arr[idx1 + idx2] = sortedSecondHalf[idx2];
                        ++idx2;
                    }
                } else if (idx1 < sortedFirstHalf.length) {
                    arr[idx1 + idx2] = sortedFirstHalf[idx1];
                    ++idx1;
                } else {
                    arr[idx1 + idx2] = sortedSecondHalf[idx2];
                    ++idx2;
                }
            }
        }
        return arr;  // base (or recursive) case
    }

    /**
     * Sorts the array using mergesort for primitive doubles
     *
     * @param arr - array to be sorted
     */
    public double[] sort(double[] arr) {
        // recursive case
        if (arr.length > 1) {
            int half = arr.length / 2;
            double[] sortedFirstHalf = sort(Arrays.copyOfRange(arr, 0, half));
            double[] sortedSecondHalf = sort(Arrays.copyOfRange(arr, half, arr.length));

            int idx1 = 0;
            int idx2 = 0;
            while (idx1 < sortedFirstHalf.length || idx2 < sortedSecondHalf.length) {
                if (idx1 < sortedFirstHalf.length && idx2 < sortedSecondHalf.length) {
                    if (sortedFirstHalf[idx1] < sortedSecondHalf[idx2]) {
                        arr[idx1 + idx2] = sortedFirstHalf[idx1];
                        ++idx1;
                    } else {
                        arr[idx1 + idx2] = sortedSecondHalf[idx2];
                        ++idx2;
                    }
                } else if (idx1 < sortedFirstHalf.length) {
                    arr[idx1 + idx2] = sortedFirstHalf[idx1];
                    ++idx1;
                } else {
                    arr[idx1 + idx2] = sortedSecondHalf[idx2];
                    ++idx2;
                }
            }
        }
        return arr;  // base (or recursive) case
    }
}
