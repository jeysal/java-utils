package com.github.jeysal.java.util.function;

import java.util.function.Function;

/**
 * A modified version of {@link Function} that can throw an {@link Exception}.
 *
 * @author Tim Seckinger
 * @see FunctionUtils#trying(ThrowingFunction)
 * @since 12.03.2016
 */
@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {
    R apply(T param) throws E;
}
