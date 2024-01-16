package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import sort.MergeSort;
import sort.QuickSort;
import sort.ShellSort;
import structures.ArrayHeap;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static resources.ArrayGenerator.generateIntegerArray;

@FunctionalInterface
interface IntegerSorter {
    Integer[] method(Integer[] arr);
}

public class SortBenchmarker {

    @Fork(value = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(value = Mode.AverageTime)
    @Benchmark
    public void benchmarkMergesortInteger(final Blackhole blackhole) {
        benchmarkSort(blackhole, generateIntegerArray(), e -> new MergeSort().sort(e));
    }

    @Fork(value = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(value = Mode.AverageTime)
    @Benchmark
    public void benchmarkShellsortInteger(final Blackhole blackhole) {
        benchmarkSort(blackhole, generateIntegerArray(), e -> new ShellSort().sort(e));
    }


    @Fork(value = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(value = Mode.AverageTime)
    @Benchmark
    public void benchmarkQuicksortInteger(final Blackhole blackhole) {
        benchmarkSort(blackhole, generateIntegerArray(), e -> new QuickSort().sort(e));
    }


    @Fork(value = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(value = Mode.AverageTime)
    @Benchmark
    public void benchmarkHeapsortInteger(final Blackhole blackhole) {
        benchmarkSort(blackhole, generateIntegerArray(), ArrayHeap::heapSort);
    }

    @Fork(value = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(value = Mode.AverageTime)
    @Benchmark
    public void benchmarkArraysInteger(final Blackhole blackhole) {
        benchmarkSort(blackhole, generateIntegerArray(), e -> {
            Arrays.sort(e);
            return e;
        });
    }

    public void benchmarkSort(final Blackhole blackhole, Integer[] arr, IntegerSorter sorter) {
        blackhole.consume(sorter.method(arr));
    }

}