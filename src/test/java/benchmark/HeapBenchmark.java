package benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import structures.ArrayHeap;
import structures.BinomialHeap;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static resources.ArrayGenerator.generateIntegerArray;

public class HeapBenchmark {
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(HeapBenchmark.class.getSimpleName())
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
    public void benchmarkPQ(final Blackhole blackhole) {
        benchmark(blackhole, new PriorityQueue<>());
    }

    @Benchmark
    public void benchmarkArrayHeap(final Blackhole blackhole) {
        benchmark(blackhole, new ArrayHeap<>());
    }

    @Benchmark
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
