package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import structures.ArrayQueue;
import structures.JBLinkedList;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static resources.ArrayGenerator.generateIntegerArray;

public class QueueBenchmarker {
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkArrayQueueMany(final Blackhole blackhole) {
        benchmarkMany(blackhole, new ArrayQueue<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkArrayDequeMany(final Blackhole blackhole) {
        benchmarkMany(blackhole, new ArrayDeque<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkJBLLMany(final Blackhole blackhole) {
        benchmarkMany(blackhole, new JBLinkedList<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkLLMany(final Blackhole blackhole) {
        benchmarkMany(blackhole, new LinkedList<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkArrayQueueRepeated(final Blackhole blackhole) {
        benchmarkRepeated(blackhole, new ArrayQueue<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkArrayDequeRepeated(final Blackhole blackhole) {
        benchmarkRepeated(blackhole, new ArrayDeque<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkJBLLRepeated(final Blackhole blackhole) {
        benchmarkRepeated(blackhole, new JBLinkedList<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
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
