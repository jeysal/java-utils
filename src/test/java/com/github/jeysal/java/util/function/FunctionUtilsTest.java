package com.github.jeysal.java.util.function;

import org.junit.Test;

import java.util.Optional;
import java.util.function.Function;

import static com.github.jeysal.java.util.function.FunctionUtils.*;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.fail;

/**
 * @author Tim Seckinger
 * @since 13.03.2016
 */
public class FunctionUtilsTest {

    @Test
    public void testFirstPresent() {
        assertEquals(Optional.of(1),
                firstPresent(
                        (Function<Object, Optional<Integer>>) o -> Optional.empty(),
                        (Function<Object, Optional<Integer>>) o -> Optional.of(1)
                ).apply(null)
        );
    }

    @Test
    public void testFirstPresentFirstMatch() {
        assertEquals(Optional.of(0),
                firstPresent(
                        (Function<Object, Optional<Integer>>) o -> Optional.of(0),
                        (Function<Object, Optional<Integer>>) o -> Optional.of(1)
                ).apply(null)
        );
    }

    @Test
    public void testFirstPresentNoMatch() {
        assertEquals(Optional.empty(),
                firstPresent(
                        (Function<Object, Optional<Integer>>) o -> Optional.empty(),
                        (Function<Object, Optional<Integer>>) o -> Optional.empty()
                ).apply(null)
        );
    }


    @Test
    public void testFirstPresentFallback() {
        assertEquals(1, firstPresent(
                (Function<Object, Integer>) o -> -1,
                o -> Optional.empty(),
                o -> Optional.of(1)
                ).apply(null)
        );
    }

    @Test
    public void testFirstPresentFallbackFirstMatch() {
        assertEquals(0, firstPresent(
                (Function<Object, Integer>) o -> -1,
                o -> Optional.of(0),
                o -> Optional.of(1)
                ).apply(null)
        );
    }

    @Test
    public void testFirstPresentFallbackNoMatch() {
        assertEquals(-1, firstPresent(
                (Function<Object, Integer>) o -> -1,
                o -> Optional.empty(),
                o -> Optional.empty()
                ).apply(null)
        );
    }

    @Test(expected = RuntimeException.class)
    public void testFirstPresentFallbackNull() {
        firstPresent(
                (Function<Object, Integer>) o -> null,
                o -> Optional.empty(),
                o -> Optional.empty()
        ).apply(null);
    }


    @Test
    public void testTrying() {
        assertEquals(Optional.of(1),
                trying(
                        o -> 1
                ).apply(null)
        );
    }

    @Test
    public void testTryingThrows() {
        assertEquals(Optional.empty(),
                trying(o -> {
                    throw new Exception();
                }).apply(null)
        );
    }

    @Test
    public void testTryingNull() {
        assertEquals(Optional.empty(),
                trying(
                        o -> null
                ).apply(null)
        );
    }

    @Test
    public void testRethrowing() {
        assertEquals(1,
                rethrowing(
                        o -> 1
                ).apply(null)
        );
    }

    @Test
    public void testRethrowingThrows() {
        try {
            rethrowing(o -> {
                throw new Exception("asdf");
            }).apply(null);
        } catch (RuntimeException e) {
            assertEquals(RuntimeException.class, e.getClass());
            assertEquals(Exception.class, e.getCause().getClass());
            assertEquals("asdf", e.getCause().getMessage());
            return;
        }

        fail("Rethrowing function did not throw");
    }

    @Test
    public void testRethrowingMapper() {
        try {
            rethrowing(o -> {
                throw new Exception("asdf");
            }, IllegalArgumentException::new).apply(null);
        } catch (RuntimeException e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals(Exception.class, e.getCause().getClass());
            assertEquals("asdf", e.getCause().getMessage());
            return;
        }

        fail("Rethrowing function did not throw");
    }

}
