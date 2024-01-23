package benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import resources.ArrayGenerator;
import structures.AVL;
import structures.SCHashMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static resources.ArrayGenerator.generateIntegerArray;

public class MapBenchmarker {
    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(MapBenchmarker.class.getSimpleName()).build();

        new Runner(options).run();
    }

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
        Integer[] arr = generateIntegerArray();
        Arrays.stream(arr).forEach(i -> map.put(i, i));

        IntStream.range(ArrayGenerator.minIntVal, ArrayGenerator.maxIntVal).boxed().forEach(integer -> {
            if (map.get(integer) != null && !integer.equals(map.get(integer))) {
                System.out.println("error");
            }
        });
    }

    private void benchmarkRemovals(final Blackhole blackhole, Map<Integer, Integer> map) {
        Arrays.stream(generateIntegerArray()).forEach(i -> map.put(i, i));

        IntStream.range(ArrayGenerator.minIntVal, ArrayGenerator.maxIntVal).boxed().forEach(integer -> {
            Integer temp = map.remove(integer);
            if (temp != null && !integer.equals(temp)) {
                System.out.println("error");
            }
        });
    }
}
