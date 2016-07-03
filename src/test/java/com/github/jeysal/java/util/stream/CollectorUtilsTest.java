package com.github.jeysal.java.util.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.gen5.api.Assertions.assertEquals;

/**
 * @author Tim Seckinger
 * @since 09.04.2016
 */
public class CollectorUtilsTest {

    @Test
    public void testCumulativelySummingInt() throws Exception {
        assertEquals(asList(1, 3, 6), Stream.of(1, 2, 3).collect(CollectorUtils.cumulativelySummingInt(i -> i)));
    }

    @Test
    public void testCumulativelySummingIntNegative() throws Exception {
        assertEquals(asList(-1, 1, -2), Stream.of(-1, 2, -3).collect(CollectorUtils.cumulativelySummingInt(i -> i)));
    }

    @Test
    public void testCumulativelySummingIntEmpty() throws Exception {
        assertEquals(new ArrayList<>(), Stream.<Integer>empty().collect(CollectorUtils.cumulativelySummingInt(i -> i)));
    }

    @Test
    public void testCumulativelySummingIntLargeParallel() throws Exception {
        final int count = 10_000;
        assertEquals(
                Stream.iterate(1, i -> 1 + i).limit(count).collect(Collectors.toList()),
                Stream.generate(() -> 1).limit(count).parallel().collect(CollectorUtils.cumulativelySummingInt(i -> i))
        );
    }

    @Test
    public void testCumulativelySummingIntMapper() throws Exception {
        assertEquals(asList(2, 6, 12), Stream.of(1, 2, 3).collect(CollectorUtils.cumulativelySummingInt(i -> 2 * i)));
    }


    @Test
    public void testCumulativelySummingLong() throws Exception {
        assertEquals(asList(1L, 3L, 6L), Stream.of(1L, 2L, 3L).collect(CollectorUtils.cumulativelySummingLong(l -> l)));
    }

    @Test
    public void testCumulativelySummingLongNegative() throws Exception {
        assertEquals(asList(-1L, 1L, -2L), Stream.of(-1L, 2L, -3L).collect(CollectorUtils.cumulativelySummingLong(l -> l)));
    }

    @Test
    public void testCumulativelySummingLongEmpty() throws Exception {
        assertEquals(new ArrayList<>(), Stream.<Long>empty().collect(CollectorUtils.cumulativelySummingLong(l -> l)));
    }

    @Test
    public void testCumulativelySummingLongLargeParallel() throws Exception {
        final int count = 10_000;
        assertEquals(
                Stream.iterate(1L, l -> 1L + l).limit(count).collect(Collectors.toList()),
                Stream.generate(() -> 1L).limit(count).parallel().collect(CollectorUtils.cumulativelySummingLong(l -> l))
        );
    }

    @Test
    public void testCumulativelySummingLongMapper() throws Exception {
        assertEquals(asList(2L, 6L, 12L), Stream.of(1L, 2L, 3L).collect(CollectorUtils.cumulativelySummingLong(l -> 2L * l)));
    }


    @Test
    public void testCumulativelySummingDouble() throws Exception {
        assertEquals(asList(1., 3., 6.), Stream.of(1., 2., 3.).collect(CollectorUtils.cumulativelySummingDouble(d -> d)));
    }

    @Test
    public void testCumulativelySummingDoubleNegative() throws Exception {
        assertEquals(asList(-1., 1., -2.), Stream.of(-1., 2., -3.).collect(CollectorUtils.cumulativelySummingDouble(d -> d)));
    }

    @Test
    public void testCumulativelySummingDoubleEmpty() throws Exception {
        assertEquals(new ArrayList<>(), Stream.<Double>empty().collect(CollectorUtils.cumulativelySummingDouble(d -> d)));
    }

    @Test
    public void testCumulativelySummingDoubleLargeParallel() throws Exception {
        final int count = 10_000;
        assertEquals(
                Stream.iterate(1., d -> 1. + d).limit(count).collect(Collectors.toList()),
                Stream.generate(() -> 1.).limit(count).parallel().collect(CollectorUtils.cumulativelySummingDouble(d -> d))
        );
    }

    @Test
    public void testCumulativelySummingDoubleMapper() throws Exception {
        assertEquals(asList(2., 6., 12.), Stream.of(1., 2., 3.).collect(CollectorUtils.cumulativelySummingDouble(d -> 2. * d)));
    }

    @Test
    public void testCumulativelySummingDoublePrecision() throws Exception {
        assertEquals(asList(.5, 1.5, 3.), Stream.of(.5, 1., 1.5).collect(CollectorUtils.cumulativelySummingDouble(d -> d)));
    }

}
