package com.github.jeysal.java.util.function;

import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;

import static com.github.jeysal.java.util.function.SupplierUtils.*;
import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.fail;

/**
 * @author Tim Seckinger
 * @since 13.03.2016
 */
public class SupplierUtilsTest {

    @Test
    public void testFirstPresent() {
        assertEquals(Optional.of(1),
                firstPresent(
                        (Supplier<Optional<Integer>>) Optional::empty,
                        () -> Optional.of(1)
                ).get()
        );
    }

    @Test
    public void testFirstPresentFirstMatch() {
        assertEquals(Optional.of(0),
                firstPresent(
                        (Supplier<Optional<Integer>>) () -> Optional.of(0),
                        () -> Optional.of(1)
                ).get()
        );
    }

    @Test
    public void testFirstPresentNoMatch() {
        assertEquals(Optional.empty(),
                firstPresent(
                        (Supplier<Optional<Integer>>) Optional::empty,
                        (Supplier<Optional<Integer>>) Optional::empty
                ).get()
        );
    }


    @Test
    public void testFirstPresentFallback() {
        assertEquals(1, firstPresent(
                () -> -1,
                Optional::empty,
                () -> Optional.of(1)
                ).get()
        );
    }

    @Test
    public void testFirstPresentFallbackFirstMatch() {
        assertEquals(0, firstPresent(
                () -> -1,
                () -> Optional.of(0),
                () -> Optional.of(1)
                ).get()
        );
    }

    @Test
    public void testFirstPresentFallbackNoMatch() {
        assertEquals(-1, firstPresent(
                () -> -1,
                Optional::empty,
                Optional::empty
                ).get()
        );
    }

    @Test(expected = RuntimeException.class)
    public void testFirstPresentFallbackNull() {
        firstPresent(
                (Supplier<Integer>) () -> null,
                Optional::empty,
                Optional::empty
        ).get();
    }


    @Test
    public void testTrying() {
        assertEquals(Optional.of(1),
                trying(
                        () -> 1
                ).get()
        );
    }

    @Test
    public void testTryingThrows() {
        assertEquals(Optional.empty(),
                trying(() -> {
                    throw new Exception();
                }).get()
        );
    }

    @Test
    public void testTryingNull() {
        assertEquals(Optional.empty(),
                trying(
                        () -> null
                ).get()
        );
    }

    @Test
    public void testRethrowing() {
        assertEquals(1,
                rethrowing(
                        () -> 1
                ).get()
        );
    }

    @Test
    public void testRethrowingThrows() {
        try {
            rethrowing(() -> {
                throw new Exception("asdf");
            }).get();
        } catch (RuntimeException e) {
            assertEquals(RuntimeException.class, e.getClass());
            assertEquals(Exception.class, e.getCause().getClass());
            assertEquals("asdf", e.getCause().getMessage());
            return;
        }

        fail("Rethrowing supplier did not throw");
    }

    @Test
    public void testRethrowingMapper() {
        try {
            rethrowing(() -> {
                throw new Exception("asdf");
            }, IllegalArgumentException::new).get();
        } catch (RuntimeException e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertEquals(Exception.class, e.getCause().getClass());
            assertEquals("asdf", e.getCause().getMessage());
            return;
        }

        fail("Rethrowing supplier did not throw");
    }

}
