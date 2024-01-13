import sort.MergeSort;
import sort.QuickSort;
import sort.Radix;
import structures.AVL;
import structures.ArrayHeap;
import structures.BinomialHeap;
import structures.JBLinkedList;
import structures.SCHashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.TreeMap;

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

        int[] tests = { (int) 1e3, (int) 1e4 };
        int n = 50; // do tests and average
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
            System.out.println("took " + (endTime - startTime) + "ns for real linked list insertion ");
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
            System.out.println("took " + (endTime - startTime) + "ns for jb linked list insertion ");
            System.out.println("this averages to " + (double) (endTime - startTime) / (double) n + "ns");
            System.out.println("or about " + (double) (endTime - startTime) / (double) n / 1e9 + "s");
        }
    }

    public static void treeTest() {
        TreeMap<Integer, Integer> realTreeMap = new TreeMap<>();
        AVL<Integer, Integer> avl = new AVL<>();

        int[] tests = { (int) 1e3, (int) 1e5, (int) 1e6 };
        int n = 10; // do 10 tests and average
        Random random = new Random(2024);
        int max = (int) 1e8;
        int min = (int) -1e8;

        // test
        for (int test : tests) {
            System.out.println("===============");
            System.out.println("testing for " + test);
            Integer[] arr = new Integer[test];
            Integer[] toGet = new Integer[test / 2];
            Integer[] toRemove = new Integer[test / 4];

            // initialize array
            for (int i = 0; i < test; ++i) {
                arr[i] = random.nextInt(max - min) + min;
            }
            for (int i = 0; i < toGet.length; ++i) {
                toGet[i] = random.nextInt(test);
            }
            for (int i = 0; i < toRemove.length; ++i) {
                toRemove[i] = random.nextInt(max - min) + min;
            }

            // run test
            long startTime = System.nanoTime();
            for (int attempt = 0; attempt < n; ++attempt) {
                for (Integer i : arr) {
                    realTreeMap.put(i, i);
                }
                for (Integer i : toGet) {
                    assert realTreeMap.get(arr[i]).equals(arr[i]);
                }
                for (Integer i : toRemove) {
                    realTreeMap.remove(i);
                }
                realTreeMap.clear();
            }
            long endTime = System.nanoTime();
            System.out.println("took " + (endTime - startTime) + "ns for real treemap");
            System.out.println("this averages to " + (double) (endTime - startTime) / (double) n + "ns");
            System.out.println("or about " + (double) (endTime - startTime) / (double) n / 1e9 + "s");
            System.out.println("------------");

            // run test
            startTime = System.nanoTime();
            for (int attempt = 0; attempt < n; ++attempt) {
                for (Integer i : arr) {
                    avl.put(i, i);
                }
                for (Integer i : toGet) {
                    assert avl.get(arr[i]).equals(arr[i]);
                }
                for (Integer i : toRemove) {
                    avl.remove(i);
                }
                avl.clear();
            }
            endTime = System.nanoTime();
            System.out.println("took " + (endTime - startTime) + "ns for avl tree");
            System.out.println("this averages to " + (double) (endTime - startTime) / (double) n + "ns");
            System.out.println("or about " + (double) (endTime - startTime) / (double) n / 1e9 + "s");
        }
    }

    public static void hashmapTest() {
        LinkedHashMap<Integer, Integer> realHM = new LinkedHashMap<>();
        SCHashMap<Integer, Integer> scHashMap = new SCHashMap<>();

        int[] tests = { (int) 1e3, (int) 1e5, (int) 1e6 };
        int n = 25; // do 10 tests and average
        Random random = new Random(2024);
        int max = (int) 1e8;
        int min = (int) -1e8;

        // test
        for (int test : tests) {
            System.out.println("===============");
            System.out.println("testing for " + test);
            Integer[] arr = new Integer[test];
            Integer[] toGet = new Integer[test / 2];
            Integer[] toRemove = new Integer[test / 4];

            // initialize array
            for (int i = 0; i < test; ++i) {
                arr[i] = random.nextInt(max - min) + min;
            }
            for (int i = 0; i < toGet.length; ++i) {
                toGet[i] = random.nextInt(test);
            }
            for (int i = 0; i < toRemove.length; ++i) {
                toRemove[i] = random.nextInt(max - min) + min;
            }

            // run test
            long startTime = System.nanoTime();
            for (int attempt = 0; attempt < n; ++attempt) {
                for (Integer i : arr) {
                    realHM.put(i, i);
                }
                for (Integer i : toGet) {
                    if (!realHM.get(arr[i]).equals(arr[i])) {
                        System.out.println("NOOOOO!!");
                    }
                }
                // for (Integer i : toRemove) {
                // realHM.remove(i);
                // }
                realHM.clear();
            }
            long endTime = System.nanoTime();
            System.out.println("took " + (endTime - startTime) + "ns for real hashmap");
            System.out.println("this averages to " + (double) (endTime - startTime) / (double) n + "ns");
            System.out.println("or about " + (double) (endTime - startTime) / (double) n / 1e9 + "s");
            System.out.println("------------");

            // run test
            startTime = System.nanoTime();
            for (int attempt = 0; attempt < n; ++attempt) {
                for (Integer i : arr) {
                    scHashMap.put(i, i);
                }
                for (Integer i : toGet) {
                    if (!scHashMap.get(arr[i]).equals(arr[i])) {
                        System.out.println("NOOO!!");
                    }
                }
                // for (Integer i : toRemove) {
                // scHashMap.remove(i);
                // }
                scHashMap.clear();
            }
            endTime = System.nanoTime();
            System.out.println("took " + (endTime - startTime) + "ns for schashmap");
            System.out.println("this averages to " + (double) (endTime - startTime) / (double) n + "ns");
            System.out.println("or about " + (double) (endTime - startTime) / (double) n / 1e9 + "s");
        }
    }

    public static void pqHelper(Queue<Integer> q, Integer[] arr, int attempts, String name) {
        // run test
        long startTime = System.nanoTime();
        for (int attempt = 0; attempt < attempts; ++attempt) {
            for (Integer i : arr) {
                q.add(i);
            }
            Integer temp = Integer.MIN_VALUE;
            for (int i = 0; i < arr.length; ++i) {
                Integer val = q.poll();
                if (temp.compareTo(val) > 0) {
                    System.out.println(
                            "Something went wrong while testing " + name + "; " + temp + " is not less than " + val);
                    return;
                }
                temp = val;
            }
            q.clear();
        }
        long endTime = System.nanoTime();
        System.out.println("took " + (endTime - startTime) + "ns for " + name);
        System.out.println("this averages to " + (double) (endTime - startTime) / (double) attempts + "ns");
        System.out.println("or about " + (double) (endTime - startTime) / (double) attempts / 1e9 + "s");
        System.out.println("------------");
    }

    public static void pqTest() {
        PriorityQueue<Integer> pqueue = new PriorityQueue<>((int) 1e6, Comparator.naturalOrder());
        ArrayHeap<Integer> arrayHeap = new ArrayHeap<>((int) 1e6, Comparator.reverseOrder());
        BinomialHeap<Integer> bHeap = new BinomialHeap<>();

        int[] tests = { (int) 1e3, (int) 1e5, (int) 1e6, };
        int n = 20; // do 20 tests and average
        Random random = new Random(2024);
        int max = (int) 1e8;
        int min = (int) -1e8;

        // test
        for (int test : tests) {
            System.out.println("===============");
            System.out.println("testing for " + test);
            Integer[] arr = new Integer[test];

            // initialize array
            for (int i = 0; i < test; ++i) {
                arr[i] = random.nextInt(max - min) + min;
            }

            pqHelper(pqueue, arr, n, "pqueue");
            pqHelper(arrayHeap, arr, n, "arrayHeap");
            pqHelper(bHeap, arr, n, "binomial Heap");

        }
    }

    public static void main(String[] args) {
        // stressTest();
        // llTest();
        // treeTest();
        // pqTest();
        hashmapTest();

    }
}