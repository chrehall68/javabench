import sort.MergeSort;
import sort.QuickSort;
import sort.Radix;
import structures.JBLinkedList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Main {
    public static void stressTest() {
        int[] tests = { 10, (int) 1e5, (int) 1e8 };
        int min = -(int) 1e8;
        int max = (int) 1e8;
        int[] bases = { 2, 7, 0, -1, -2 };
        ArrayList<Random> randoms = new ArrayList<>();
        for (int i = 0; i < bases.length; ++i) {
            randoms.add(new Random(2024));
        }

        // test
        for (int test : tests) {
            System.out.println("testing for " + test);
            System.out.println("===============");
            int[] arr = new int[test];
            for (int baseIdx = 0; baseIdx < bases.length; ++baseIdx) {
                // initialize array
                for (int i = 0; i < test; ++i) {
                    arr[i] = randoms.get(baseIdx).nextInt(max - min) + min;
                }

                // run test
                long startTime = System.nanoTime();
                if (bases[baseIdx] > 1) {
                    Radix.sort(arr, bases[baseIdx]);
                } else if (bases[baseIdx] == 0) {
                    MergeSort.sort(arr);
                } else if (bases[baseIdx] == -1) {
                    QuickSort.sort(arr);
                } else {
                    Arrays.sort(arr);
                }

                long endTime = System.nanoTime();
                System.out.println("took " + (endTime - startTime) + "ns for " + bases[baseIdx]);
            }
            System.out.println("=================");
        }
    }

    public static void llTest() {
        LinkedList<Integer> realLinkedList = new LinkedList<>();
        JBLinkedList<Integer> jbLinkedList = new JBLinkedList<>();

        int[] tests = { (int) 1e4, (int) 1e5 };
        int n = 5; // do 5 tests and average
        Random random = new Random(2024);
        int max = (int) 1e8;
        int min = (int) -1e8;

        // test
        for (int test : tests) {
            System.out.println("===============");
            System.out.println("testing for " + test);
            Integer[] arr = new Integer[test];
            Integer[] toRemove = new Integer[test / 4];

            // initialize array
            for (int i = 0; i < test; ++i) {
                arr[i] = random.nextInt(max - min) + min;
            }
            for (int i = 0; i < toRemove.length; ++i) {
                toRemove[i] = random.nextInt(max - min) + min;
            }

            // run test
            long startTime = System.nanoTime();
            for (int attempt = 0; attempt < n; ++attempt) {
                for (Integer i : arr) {
                    realLinkedList.add(i);
                }
                for (Integer i : toRemove) {
                    realLinkedList.remove(i);
                }
                realLinkedList.clear();
            }
            long endTime = System.nanoTime();
            System.out.println("took " + (endTime - startTime) + "ns for real linked list");
            System.out.println("this averages to " + (double) (endTime - startTime) / (double) n + "ns");
            System.out.println("or about " + (double) (endTime - startTime) / (double) n / 1e9 + "s");
            System.out.println("------------");

            // run test
            startTime = System.nanoTime();
            for (int attempt = 0; attempt < n; ++attempt) {
                for (Integer i : arr) {
                    jbLinkedList.add(i);
                }
                for (Integer i : toRemove) {
                    jbLinkedList.remove(i);
                }
                jbLinkedList.clear();
            }
            endTime = System.nanoTime();
            System.out.println("took " + (endTime - startTime) + "ns for jb linked list");
            System.out.println("this averages to " + (double) (endTime - startTime) / (double) n + "ns");
            System.out.println("or about " + (double) (endTime - startTime) / (double) n / 1e9 + "s");
        }

    }

    public static void main(String[] args) {
        // stressTest();
        llTest();
    }
}