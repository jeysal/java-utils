package com.github.jeysal.java.util.function;

import java.util.function.Supplier;

/**
 * A modified version of {@link Supplier} that can throw an {@link Exception}.
 *
 * @author Tim Seckinger
 * @since 7/31/16
 */
@FunctionalInterface
public interface ThrowingSupplier<R, E extends Exception> {
    R get() throws E;
}
