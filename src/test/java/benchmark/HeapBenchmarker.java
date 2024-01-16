package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import structures.ArrayHeap;
import structures.BinomialHeap;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static resources.ArrayGenerator.generateIntegerArray;

public class HeapBenchmarker {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkPQ(final Blackhole blackhole) {
        benchmark(blackhole, new PriorityQueue<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkArrayHeap(final Blackhole blackhole) {
        benchmark(blackhole, new ArrayHeap<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkBinomialHeap(final Blackhole blackhole) {
        benchmark(blackhole, new BinomialHeap<>());
    }

    private void benchmark(final Blackhole blackhole, Queue<Integer> queue) {
        Integer[] arr = generateIntegerArray();
        Collections.addAll(queue, arr);

        // poll
        IntStream.range(0, arr.length).forEach(i -> arr[i] = queue.poll());
        // consume
        blackhole.consume(arr);
    }
}
