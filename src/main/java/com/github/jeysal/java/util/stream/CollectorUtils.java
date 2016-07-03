package com.github.jeysal.java.util.stream;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides useful {@link Collector}s in addition to those offered by {@link Collectors}.
 *
 * @author Tim Seckinger
 * @since 08.04.2016
 */
@UtilityClass
public class CollectorUtils {

    /**
     * Returns a {@link Collector} that produces the cumulative sum of an integer-valued function
     * applied to the input elements. If no elements are present, the result is an empty list.
     * <p>
     * <b>Example:</b><br>
     * {@code assert asList(1, 3, 0).equals(Stream.of(1, 2, -3).collect(cumulativelySummingInt(i -> i)))}
     * </p>
     *
     * @param <T>    the type of the input elements
     * @param mapper a function extracting the property to be cumulatively summed
     * @return a {@link Collector} that produces the cumulative sum of a derived property
     */
    public static <T> Collector<T, List<Integer>, List<Integer>> cumulativelySummingInt(ToIntFunction<T> mapper) {
        return Collector.of(
                ArrayList::new,
                (l, i) -> l.add(mapper.applyAsInt(i) + (l.isEmpty() ? 0 : l.get(l.size() - 1))),
                (l, r) -> l.isEmpty() ? r : Stream.concat(
                        l.stream(),
                        r.stream().map(i -> i + l.get(l.size() - 1))).collect(Collectors.toList()));
    }

    /**
     * Returns a {@link Collector} that produces the cumulative sum of a long-valued function
     * applied to the input elements. If no elements are present, the result is an empty list.
     * <p>
     * <b>Example:</b><br>
     * {@code assert asList(1L, 3L, 0L).equals(Stream.of(1L, 2L, -3L).collect(cumulativelySummingLong(l -> l)))}
     * </p>
     *
     * @param <T>    the type of the input elements
     * @param mapper a function extracting the property to be cumulatively summed
     * @return a {@link Collector} that produces the cumulative sum of a derived property
     */
    public static <T> Collector<T, List<Long>, List<Long>> cumulativelySummingLong(ToLongFunction<T> mapper) {
        return Collector.of(
                ArrayList::new,
                (l, i) -> l.add(mapper.applyAsLong(i) + (l.isEmpty() ? 0 : l.get(l.size() - 1))),
                (l, r) -> l.isEmpty() ? r : Stream.concat(
                        l.stream(),
                        r.stream().map(i -> i + l.get(l.size() - 1))).collect(Collectors.toList()));
    }

    /**
     * Returns a {@link Collector} that produces the cumulative sum of a double-valued function
     * applied to the input elements. If no elements are present, the result is an empty list.
     * <p>
     * <b>Example:</b><br>
     * {@code assert asList(1., 3., 0.).equals(Stream.of(1., 2., -3.).collect(cumulativelySummingDouble(d -> d)))}
     * </p>
     *
     * @param <T>    the type of the input elements
     * @param mapper a function extracting the property to be cumulatively summed
     * @return a {@link Collector} that produces the cumulative sum of a derived property
     */
    public static <T> Collector<T, List<Double>, List<Double>> cumulativelySummingDouble(ToDoubleFunction<T> mapper) {
        return Collector.of(
                ArrayList::new,
                (l, i) -> l.add(mapper.applyAsDouble(i) + (l.isEmpty() ? 0 : l.get(l.size() - 1))),
                (l, r) -> l.isEmpty() ? r : Stream.concat(
                        l.stream(),
                        r.stream().map(i -> i + l.get(l.size() - 1))).collect(Collectors.toList()));
    }

}
