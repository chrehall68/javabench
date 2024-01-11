package sort;

public class QuickSort {
    public static int[] sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
        return arr;
    }

    /**
     * 
     * @param arr
     * @param low  inclusive
     * @param high inclusive
     */
    private static void quickSort(int[] arr, int low, int high) {
        if (low >= high) {
            return;
        }
        int[] indexes = partition(arr, low, high);
        quickSort(arr, low, indexes[0] - 1);
        quickSort(arr, indexes[1] + 1, high);
    }

    /**
     * 
     * @param arr
     * @param low  inclusive
     * @param high inclusive
     * @return int[] - the indexes; the first item is the last index of an element
     *         <= partition; the second
     *         item is the first index of an element >= partition
     */
    private static int[] partition(int[] arr, int low, int high) {
        int pivot = arr[low + (high - low) / 2];

        int lowIdx = low;
        int highIdx = high;
        int eqIdx = low;
        int temp;

        while (eqIdx <= highIdx) {
            if (arr[eqIdx] < pivot) {
                // we have smth less than pivot at the equal idx
                // we don't want that
                temp = arr[eqIdx];
                arr[eqIdx] = arr[lowIdx];
                arr[lowIdx] = temp;

                ++lowIdx;
                ++eqIdx;
            } else if (arr[eqIdx] > pivot) {
                // this should be greater than
                temp = arr[eqIdx];
                arr[eqIdx] = arr[highIdx];
                arr[highIdx] = temp;

                --highIdx;
            } else {
                // equal to pivot
                ++eqIdx;
            }
        }

        int[] ret = { lowIdx, highIdx };
        return ret;
    }
}