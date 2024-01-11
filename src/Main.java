import sort.Radix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {
    public static void main(String[] args) {
        int[] tests = {10, (int)1e5, (int)1e7};
        int min = -(int)1e8;
        int max = (int)1e8;
        int[] bases = {2, 7, 10, -1};
        ArrayList<Random> randoms = new ArrayList<>();
        for (int i = 0; i < bases.length; ++i){
            randoms.add(new Random(2024));
        }

        // test
        for (int test : tests){
            System.out.println("testing for " + test);
            System.out.println("===============");
            int[] arr = new int[test];
            for (int baseIdx = 0; baseIdx < bases.length; ++baseIdx){
                // initialize array
                for (int i = 0; i < test; ++i){
                    arr[i] = randoms.get(baseIdx).nextInt(max-min)+min;
                }

                // run test
                long startTime = System.nanoTime();
                if (bases[baseIdx] != -1) {
                    Radix.sort(arr, bases[baseIdx]);
                }
                else{
                    Arrays.sort(arr);
                }

                long endTime = System.nanoTime();
                System.out.println("took " + (endTime-startTime) + "ns for " + bases[baseIdx]);
            }
            System.out.println("=================");
        }
    }
}