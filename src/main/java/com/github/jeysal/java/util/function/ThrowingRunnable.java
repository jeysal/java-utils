package com.github.jeysal.java.util.function;

/**
 * A modified version of {@link Runnable} that can throw an {@link Exception}.
 *
 * @author Tim Seckinger
 * @since 7/31/16
 */
@FunctionalInterface
public interface ThrowingRunnable<E extends Exception> {
    void run() throws E;
}
