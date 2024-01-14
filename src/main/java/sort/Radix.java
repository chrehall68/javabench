package sort;

public class Radix{
    private static int getMaxPower(int[] arr, int base){
        int maxPower = 0;
        int curPower;
        for (int val : arr){
            // reset the current power
            curPower = 0;

            // don't use negatives (those should be handled later)
            if (val < 0){
                val = -val;
            }
            // find max(ciel(log_base(val)))
            while (val != 0){
                if (base == 2){
                    val >>=1;
                }
                else{
                    val /= base;
                }
                ++curPower;
            }

            maxPower = Math.max(curPower, maxPower);
        }
        return maxPower;
    }
    private  static  void emptyBuckets(int[] arr, int[][] buckets, int[] indexes){
        int arrIdx = 0;
        for (int baseIdx = 0; baseIdx < buckets.length; ++baseIdx){
            int tempIdx = 0;
            while (tempIdx < indexes[baseIdx]){
                arr[arrIdx] = buckets[baseIdx][tempIdx];
                ++arrIdx;
                ++tempIdx;
            }
        }
    }

    public static void sort(int[] arr, int base){
        assert base > 1;
        int maxPower = getMaxPower(arr, base);

        // initialize buckets
        int[][] buckets = new int[base][arr.length];

        // sort
        int curVal = 1;
        for (int i = 0; i < maxPower; ++i){
            int[] idxs = new int[base];
            for (int val: arr){
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
        for (int val : arr){
            if (val < 0){
                buckets[0][bucket0Idx] = val;
                ++bucket0Idx;
            }
            else{
                buckets[1][bucket1Idx] = val;
                ++bucket1Idx;
            }
        }
        // empty buckets
        int arrIdx = 0;
        while (arrIdx < arr.length){
            if (arrIdx < bucket0Idx){
                arr[arrIdx]  = buckets[0][bucket0Idx-1-arrIdx];
            }
            else{
                arr[arrIdx] = buckets[1][arrIdx-bucket0Idx];
            }
            ++arrIdx;
        }
    }
}