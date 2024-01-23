package benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import structures.ArrayQueue;
import structures.JBLinkedList;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static resources.ArrayGenerator.generateIntegerArray;

public class QueueBenchmark {
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(QueueBenchmark.class.getSimpleName())
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
    public void benchmarkArrayQueueMany(final Blackhole blackhole) {
        benchmarkMany(blackhole, new ArrayQueue<>());
    }

    @Benchmark
    public void benchmarkArrayDequeMany(final Blackhole blackhole) {
        benchmarkMany(blackhole, new ArrayDeque<>());
    }

    @Benchmark
    public void benchmarkJBLLMany(final Blackhole blackhole) {
        benchmarkMany(blackhole, new JBLinkedList<>());
    }

    @Benchmark
    public void benchmarkLLMany(final Blackhole blackhole) {
        benchmarkMany(blackhole, new LinkedList<>());
    }

    @Benchmark
    public void benchmarkArrayQueueRepeated(final Blackhole blackhole) {
        benchmarkRepeated(blackhole, new ArrayQueue<>());
    }

    @Benchmark
    public void benchmarkArrayDequeRepeated(final Blackhole blackhole) {
        benchmarkRepeated(blackhole, new ArrayDeque<>());
    }

    @Benchmark
    public void benchmarkJBLLRepeated(final Blackhole blackhole) {
        benchmarkRepeated(blackhole, new JBLinkedList<>());
    }

    @Benchmark
    public void benchmarkLLRepeated(final Blackhole blackhole) {
        benchmarkRepeated(blackhole, new LinkedList<>());
    }

    private void benchmarkMany(final Blackhole blackhole, Queue<Integer> queue) {
        Integer[] arr = generateIntegerArray();

        // test lots of additions / lots of removals
        Collections.addAll(queue, arr);
        IntStream.range(0, arr.length).forEach(i -> arr[i] = queue.poll());
        blackhole.consume(arr);
    }

    private void benchmarkRepeated(final Blackhole blackhole, Queue<Integer> queue) {
        Integer[] arr = generateIntegerArray();

        // test lots of (addition/removal) pairs
        int nTimes = 10;
        int mod = 100;
        for (int i = 0; i < arr.length * nTimes; ++i) {
            queue.add(arr[i % arr.length]);

            // poll mod times every mod times
            if (i % mod == 0) {
                while (!queue.isEmpty()) {
                    queue.poll();
                }
            }
        }
        blackhole.consume(arr);
    }
}
