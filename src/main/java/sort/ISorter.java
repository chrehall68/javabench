package sort;

public interface ISorter {

    /**
     * Sorts the given array
     *
     * @param arr - the array to sort
     * @return - the sorted array; either a new array or the same array (if it was sorted in place)
     * @throws UnsupportedOperationException - if this sorting method doesn't support this operation on this datatype
     */
    int[] sort(int[] arr) throws UnsupportedOperationException;

    /**
     * Sorts the given array
     *
     * @param arr - the array to sort
     * @return - the sorted array; either a new array or the same array (if it was sorted in place)
     * @throws UnsupportedOperationException - if this sorting method doesn't support this operation on this datatype
     */
    double[] sort(double[] arr) throws UnsupportedOperationException;


    /**
     * Sorts the given array
     *
     * @param arr - the array to sort
     * @param <T> - the type of the array
     * @return - the sorted array; either a new array or the same array (if it was sorted in place)
     * @throws UnsupportedOperationException - if this sorting method doesn't support this operation on this datatype
     */
    <T extends Comparable<T>> T[] sort(T[] arr) throws UnsupportedOperationException;

}
