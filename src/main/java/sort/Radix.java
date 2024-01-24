package sort;

import java.util.ArrayList;
import java.util.LinkedList;

public class Radix implements ISorter {
    public double[] sort(double[] arr) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    private int getMaxPower(int[] arr, int base) {
        int maxPower = 0;
        int curPower;
        for (int val : arr) {
            // reset the current power
            curPower = 0;

            // don't use negatives (those should be handled later)
            if (val < 0) {
                val = -val;
            }
            // find max(ciel(log_base(val)))
            while (val != 0) {
                if (base == 2) {
                    val >>= 1;
                } else {
                    val /= base;
                }
                ++curPower;
            }

            maxPower = Math.max(curPower, maxPower);
        }
        return maxPower;
    }

    private void emptyBuckets(int[] arr, int[][] buckets, int[] indexes) {
        int arrIdx = 0;
        for (int baseIdx = 0; baseIdx < buckets.length; ++baseIdx) {
            int tempIdx = 0;
            while (tempIdx < indexes[baseIdx]) {
                arr[arrIdx] = buckets[baseIdx][tempIdx];
                ++arrIdx;
                ++tempIdx;
            }
        }
    }

    public int[] sort(int[] arr) {
        return sort(arr, 10);
    }

    public int[] sort(int[] arr, int base) {
        assert base > 1;
        int maxPower = getMaxPower(arr, base);

        // initialize buckets
        int[][] buckets = new int[base][arr.length];

        // sort
        int curVal = 1;
        for (int i = 0; i < maxPower; ++i) {
            int[] idxs = new int[base];
            for (int val : arr) {
                int idx = ((val < 0 ? -val : val) / curVal) % base;

                buckets[idx][idxs[idx]] = val;
                ++idxs[idx];
            }

            // empty buckets
            emptyBuckets(arr, buckets, idxs);
            curVal *= base;
        }

        // take care of negatives
        // sort by negatives
        int bucket0Idx = 0;
        int bucket1Idx = 0;
        for (int val : arr) {
            if (val < 0) {
                buckets[0][bucket0Idx] = val;
                ++bucket0Idx;
            } else {
                buckets[1][bucket1Idx] = val;
                ++bucket1Idx;
            }
        }
        // empty buckets
        int arrIdx = 0;
        while (arrIdx < arr.length) {
            if (arrIdx < bucket0Idx) {
                arr[arrIdx] = buckets[0][bucket0Idx - 1 - arrIdx];
            } else {
                arr[arrIdx] = buckets[1][arrIdx - bucket0Idx];
            }
            ++arrIdx;
        }

        return arr;
    }

    public <T extends Comparable<T>> T[] sort(T[] arr) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public Integer[] sort(Integer[] arr) {
        return sort(arr, 10);
    }

    private int getMaxPower(Integer[] arr, int base) {
        int maxPower = 0;
        int curPower;
        for (int val : arr) {
            // reset the current power
            curPower = 0;

            // don't use negatives (those should be handled later)
            if (val < 0) {
                val = -val;
            }
            // find max(ciel(log_base(val)))
            while (val != 0) {
                if (base == 2) {
                    val >>= 1;
                } else {
                    val /= base;
                }
                ++curPower;
            }

            maxPower = Math.max(curPower, maxPower);
        }
        return maxPower;
    }

    private void emptyBuckets(Integer[] arr, ArrayList<LinkedList<Integer>> buckets) {
        int arrIdx = 0;
        for (LinkedList<Integer> bucket : buckets) {
            while (!bucket.isEmpty()) {
                arr[arrIdx++] = bucket.poll();
            }
        }
    }

    public Integer[] sort(Integer[] arr, int base) {
        assert base > 1;
        int maxPower = getMaxPower(arr, base);

        // initialize buckets
        ArrayList<LinkedList<Integer>> buckets = new ArrayList<>(base);
        for (int i = 0; i < base; ++i) {
            buckets.add(new LinkedList<>());
        }

        // sort
        int curVal = 1;
        for (int i = 0; i < maxPower; ++i) {
            for (int val : arr) {
                int idx = ((val < 0 ? -val : val) / curVal) % base;

                buckets.get(idx).add(val);
            }

            // empty buckets
            emptyBuckets(arr, buckets);
            curVal *= base;
        }

        // take care of negatives
        // sort by negatives
        for (int val : arr) {
            if (val < 0) {
                buckets.get(0).addFirst(val);  // add first for reversed order (big numbers that are negative are smaller)
            } else {
                buckets.get(1).add(val);
            }
        }
        // empty buckets
        emptyBuckets(arr, buckets);
        return arr;
    }
}