package com.github.jeysal.java.util.function;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Tim Seckinger
 * @since 12.03.2016
 */
@UtilityClass
public class SupplierUtils {

    /**
     * Returns a {@link Supplier} that supplies the first present {@link Optional} supplied by given suppliers in order,
     * or an empty {@link Optional} if none of the suppliers return a present {@link Optional}.
     *
     * @param suppliers The suppliers to call in order
     * @param <T>       The type of the {@link Optional}
     * @return The {@link Supplier} that supplies the first present {@link Optional}
     */
    @SafeVarargs
    public static <T> Supplier<Optional<T>> firstPresent(Supplier<Optional<T>>... suppliers) {
        return () -> Arrays.stream(suppliers)
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .findFirst().orElse(Optional.empty());
    }

    /**
     * Returns a {@link Supplier} that supplies the content of the first present {@link Optional} supplied by given
     * suppliers in order, or the object supplied by given fallback supplier if none of the suppliers return a present
     * {@link Optional}.
     *
     * @param fallback  The fallback supplier
     * @param suppliers The suppliers to call in order
     * @param <T>       The type of the {@link Optional} content or fallback object
     * @return The {@link Supplier} that supplies the first present {@link Optional}
     */
    @SafeVarargs
    public static <T> Supplier<T> firstPresent(Supplier<T> fallback, Supplier<Optional<T>>... suppliers) {
        return () -> Arrays.stream(suppliers)
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .findFirst().orElseGet(() ->
                        Optional.of(fallback.get())
                ).get();
    }

    /**
     * Returns a {@link Supplier} that delegates calls to given throwingSupplier and returns an {@link Optional} of the
     * result or an empty {@link Optional} if given throwingSupplier returned null or threw an {@link Exception}.
     *
     * @param throwingSupplier The {@link Supplier} to delegate to
     * @param <R>              The return type of the {@link Supplier}
     * @return The {@link Supplier} that delegates calls to throwingSupplier
     */
    public static <R> Supplier<Optional<R>> trying(ThrowingSupplier<R, ?> throwingSupplier) {
        return () -> {
            try {
                return Optional.ofNullable(throwingSupplier.get());
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }

    /**
     * Returns a {@link Supplier} that delegates calls to given throwingSupplier and throws a new
     * {@link RuntimeException} if given throwingSupplier threw an {@link Exception}.<br>
     * The {@link RuntimeException} has the original {@link Exception} as its {@link Throwable#getCause() cause}.
     *
     * @param throwingSupplier The {@link Supplier} to delegate to
     * @param <R>              The return type of the {@link Supplier}
     * @return The {@link Supplier} that delegates calls to throwingSupplier
     */
    public static <R> Supplier<R> rethrowing(ThrowingSupplier<R, ?> throwingSupplier) {
        return rethrowing(throwingSupplier, RuntimeException::new);
    }

    /**
     * Returns a {@link Supplier} that delegates calls to given throwingSupplier and throws a {@link RuntimeException}
     * created by given exceptionMapper if given throwingSupplier threw an {@link Exception}.<br>
     * The original {@link Exception} is passed as the argument to given exceptionMapper.
     * <p>
     * <b>Example:</b><br>
     * {@code rethrowing(() -> { throw new Exception("example"); }, IllegalArgumentException::new)}
     * </p>
     *
     * @param throwingSupplier The {@link Supplier} to delegate to
     * @param exceptionMapper  The original {@link Exception} to rethrown {@link RuntimeException} mapper
     * @param <R>              The return type of the {@link Supplier}
     * @return The {@link Supplier} that delegates calls to throwingSupplier
     */
    public static <R> Supplier<R> rethrowing(
            ThrowingSupplier<R, ?> throwingSupplier,
            Function<Exception, ? extends RuntimeException> exceptionMapper) {
        return () -> {
            try {
                return throwingSupplier.get();
            } catch (Exception e) {
                throw exceptionMapper.apply(e);
            }
        };
    }

}
