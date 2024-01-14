package sort;

public class ShellSort {
    public static <E extends Comparable<E>> void sort(E[] arr) {
        for (int k = Math.max(arr.length / 3, 1); k > 0; --k) {
            bubbleSort(arr, k);
        }
    }

    private static <E extends Comparable<E>> void bubbleSort(E[] arr, int k) {
        for (int i = arr.length - k; i > -1; --i) {
            for (int j = i + k; j < arr.length; j += k) {
                // already sorted
                if (arr[j - k].compareTo(arr[j]) <= 0) {
                    break;
                } else {
                    // swap
                    E temp = arr[j - k];
                    arr[j - k] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    public static void sort(int[] arr) {
        for (int k = Math.max(arr.length / 3, 1); k > 0; --k) {
            bubbleSort(arr, k);
        }
    }

    private static void bubbleSort(int[] arr, int k) {
        for (int i = arr.length - k; i > -1; --i) {
            for (int j = i + k; j < arr.length; j += k) {
                // already sorted
                if (arr[j - k] <= arr[j]) {
                    break;
                } else {
                    // swap
                    int temp = arr[j - k];
                    arr[j - k] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}
