package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import structures.AVL;
import structures.SCHashMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import static resources.ArrayGenerator.generateIntegerArray;

public class MapBenchmarker {

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkAVLInsertions(final Blackhole blackhole) {
        benchmarkInsertions(blackhole, new AVL<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkTreeInsertions(final Blackhole blackhole) {
        benchmarkInsertions(blackhole, new TreeMap<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkHashMapInsertions(final Blackhole blackhole) {
        benchmarkInsertions(blackhole, new HashMap<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkSCHashMapInsertions(final Blackhole blackhole) {
        benchmarkGets(blackhole, new SCHashMap<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkAVLGets(final Blackhole blackhole) {
        benchmarkGets(blackhole, new AVL<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkTreeGets(final Blackhole blackhole) {
        benchmarkGets(blackhole, new TreeMap<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkHashMapGets(final Blackhole blackhole) {
        benchmarkGets(blackhole, new HashMap<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkSCHashMapGets(final Blackhole blackhole) {
        benchmarkGets(blackhole, new SCHashMap<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkAVLRemovals(final Blackhole blackhole) {
        benchmarkRemovals(blackhole, new AVL<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkTreeRemovals(final Blackhole blackhole) {
        benchmarkRemovals(blackhole, new TreeMap<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkHashMapRemovals(final Blackhole blackhole) {
        benchmarkRemovals(blackhole, new HashMap<>());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    public void benchmarkSCHashMapRemovals(final Blackhole blackhole) {
        benchmarkRemovals(blackhole, new SCHashMap<>());
    }

    private void benchmarkInsertions(final Blackhole blackhole, Map<Integer, Integer> map) {
        Arrays.stream(generateIntegerArray()).forEach(i -> map.put(i, i));
        blackhole.consume(map);
    }

    private void benchmarkGets(final Blackhole blackhole, Map<Integer, Integer> map) {
        Arrays.stream(generateIntegerArray()).forEach(i -> map.put(i, i));

        int total = 0;
        for (int i = Integer.MIN_VALUE + 1; i < Integer.MAX_VALUE; ++i) {
            Integer temp = map.get(i);
            if (temp != null) total += temp;
        }
        blackhole.consume(total);
    }

    private void benchmarkRemovals(final Blackhole blackhole, Map<Integer, Integer> map) {
        Arrays.stream(generateIntegerArray()).forEach(i -> map.put(i, i));

        int total = 0;
        for (int i = Integer.MIN_VALUE + 1; i < Integer.MAX_VALUE; ++i) {
            Integer temp = map.remove(i);
            if (temp != null) total += temp;
        }
        blackhole.consume(total);
    }
}
