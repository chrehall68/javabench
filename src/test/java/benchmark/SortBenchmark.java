package benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import sort.MergeSort;
import sort.QuickSort;
import sort.Radix;
import sort.ShellSort;
import structures.ArrayHeap;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static resources.ArrayGenerator.generateIntegerArray;

@FunctionalInterface
interface IntegerSorter {
    Integer[] method(Integer[] arr);
}

public class SortBenchmark {
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(SortBenchmark.class.getSimpleName())
                .warmupForks(0)
                .warmupIterations(3)
                .forks(1)
                .measurementIterations(3)
                .timeUnit(TimeUnit.MILLISECONDS)
                .mode(Mode.AverageTime)
                .build();
        new Runner(options).run();
    }

    @Benchmark
    public void benchmarkMergesortInteger(final Blackhole blackhole) {
        benchmarkSort(blackhole, generateIntegerArray(), e -> new MergeSort().sort(e));
    }

    @Benchmark
    public void benchmarkShellsortInteger(final Blackhole blackhole) {
        benchmarkSort(blackhole, generateIntegerArray(), e -> new ShellSort().sort(e));
    }

    @Benchmark
    public void benchmarkQuicksortInteger(final Blackhole blackhole) {
        benchmarkSort(blackhole, generateIntegerArray(), e -> new QuickSort().sort(e));
    }

    @Benchmark
    public void benchmarkHeapsortInteger(final Blackhole blackhole) {
        benchmarkSort(blackhole, generateIntegerArray(), ArrayHeap::heapSort);
    }

    @Benchmark
    public void benchmarkRadixsortInteger(final Blackhole blackhole) {
        benchmarkSort(blackhole, generateIntegerArray(), e -> new Radix().sort(e));
    }

    @Benchmark
    public void benchmarkArraysInteger(final Blackhole blackhole) {
        benchmarkSort(blackhole, generateIntegerArray(), e -> {
            Arrays.sort(e);
            return e;
        });
    }

    private void benchmarkSort(final Blackhole blackhole, Integer[] arr, IntegerSorter sorter) {
        blackhole.consume(sorter.method(arr));
    }

}