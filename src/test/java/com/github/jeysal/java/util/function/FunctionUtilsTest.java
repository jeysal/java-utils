package com.github.jeysal.java.util.function;

import org.junit.Test;

import java.util.Optional;
import java.util.function.Function;

import static org.junit.gen5.api.Assertions.assertEquals;

/**
 * @author Tim Seckinger
 * @since 13.03.2016
 */
public class FunctionUtilsTest {

    @Test
    public void testFirstPresent() {
        assertEquals(Optional.of(1),
                FunctionUtils.firstPresent(
                        (Function<Object, Optional<Integer>>) o -> Optional.empty(),
                        (Function<Object, Optional<Integer>>) o -> Optional.of(1)
                ).apply(null)
        );
    }

    @Test
    public void testFirstPresentFirstMatch() {
        assertEquals(Optional.of(0),
                FunctionUtils.firstPresent(
                        (Function<Object, Optional<Integer>>) o -> Optional.of(0),
                        (Function<Object, Optional<Integer>>) o -> Optional.of(1)
                ).apply(null)
        );
    }

    @Test
    public void testFirstPresentNoMatch() {
        assertEquals(Optional.empty(),
                FunctionUtils.firstPresent(
                        (Function<Object, Optional<Integer>>) o -> Optional.empty(),
                        (Function<Object, Optional<Integer>>) o -> Optional.empty()
                ).apply(null)
        );
    }


    @Test
    public void testFirstPresentFallback() {
        assertEquals(1, FunctionUtils.firstPresent(
                (Function<Object, Integer>) o -> -1,
                o -> Optional.empty(),
                o -> Optional.of(1)
                ).apply(null)
        );
    }

    @Test
    public void testFirstPresentFallbackFirstMatch() {
        assertEquals(0, FunctionUtils.firstPresent(
                (Function<Object, Integer>) o -> -1,
                o -> Optional.of(0),
                o -> Optional.of(1)
                ).apply(null)
        );
    }

    @Test
    public void testFirstPresentFallbackNoMatch() {
        assertEquals(-1, FunctionUtils.firstPresent(
                (Function<Object, Integer>) o -> -1,
                o -> Optional.empty(),
                o -> Optional.empty()
                ).apply(null)
        );
    }

    @Test(expected = RuntimeException.class)
    public void testFirstPresentFallbackNull() {
        FunctionUtils.firstPresent(
                (Function<Object, Integer>) o -> null,
                o -> Optional.empty(),
                o -> Optional.empty()
        ).apply(null);
    }


    @Test
    public void testTrying() {
        assertEquals(Optional.of(1),
                FunctionUtils.trying(
                        o -> 1
                ).apply(null)
        );
    }

    @Test
    public void testTryingThrows() {
        assertEquals(Optional.empty(),
                FunctionUtils.trying(o -> {
                    throw new Exception();
                }).apply(null)
        );
    }

    @Test
    public void testTryingNull() {
        assertEquals(Optional.empty(),
                FunctionUtils.trying(
                        o -> null
                ).apply(null)
        );
    }

}
