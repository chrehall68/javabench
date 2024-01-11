package sort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class Radix{
    @SafeVarargs
    private static void emptyBuckets(int[] arr, LinkedBlockingDeque<Integer>...buckets){
        int idx = 0;
        for (LinkedBlockingDeque<Integer> bucket : buckets){
            while (!bucket.isEmpty()){
                arr[idx] = bucket.poll();
                ++idx;
            }
        }
    }
    private static void emptyBuckets(int[] arr, List<LinkedBlockingDeque<Integer>> buckets){
        int idx = 0;
        for (LinkedBlockingDeque<Integer> bucket : buckets){
            while (!bucket.isEmpty()){
                arr[idx] = bucket.poll();
                ++idx;
            }
        }
    }
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

    public static void sortBase2(int[] arr){
        // calculate the maximum power of two needed to sort
        int maxPower = getMaxPower(arr, 2);

        // initialize buckets
        LinkedBlockingDeque<Integer> bucket1 = new LinkedBlockingDeque<>();
        LinkedBlockingDeque<Integer> bucket0 = new LinkedBlockingDeque<>();

        // sort
        int curVal = 1;
        for (int i = 0; i < maxPower; ++i){
            // go through the arr and add to the buckets
            for (int val : arr){
                if ( ((val < 0 ? -val : val) & curVal) != 0 ){
                    bucket1.add(val);
                }
                else{
                    bucket0.add(val);
                }
            }

            // now empty the buckets into the arr
            emptyBuckets(arr, bucket0, bucket1);

            // increment curVal
            curVal <<= 1;
        }

        // sort by negatives
        for (int val : arr){
            if (val < 0){
                bucket0.addFirst(val);
            }
            else{
                bucket1.add(val);
            }
        }
        emptyBuckets(arr, bucket0, bucket1);
    }
    public static void sort(int[] arr, int base){
        assert base > 1;
        if (base == 2){
            sortBase2(arr);
            return;
        }

        int maxPower = getMaxPower(arr, base);

        // initialize buckets
        ArrayList<LinkedBlockingDeque<Integer>> buckets = new ArrayList<>(base);
        for (int i = 0; i < base; ++i){
            buckets.add(new LinkedBlockingDeque<>());
        }

        // sort
        int curVal = 1;
        for (int i = 0; i < maxPower; ++i){
            for (int val: arr){
                buckets.get(((val < 0 ? -val : val) / curVal) % base).add(val);
            }
            emptyBuckets(arr, buckets);
            curVal *= base;
        }

        // take care of negatives
        for (int val : arr){
            if (val < 0){
                buckets.get(0).addFirst(val);
            }
            else{
                buckets.get(1).add(val);
            }
        }
        emptyBuckets(arr, buckets);
    }
}