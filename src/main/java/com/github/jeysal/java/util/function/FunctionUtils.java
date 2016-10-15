package com.github.jeysal.java.util.function;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Tim Seckinger
 * @since 09.03.2016
 */
@UtilityClass
public class FunctionUtils {

    /**
     * Returns a {@link Function} that yields the first present {@link Optional} yielded by given functions when applied
     * to the same argument in order, or an empty {@link Optional} if none of the functions return a present
     * {@link Optional}.
     *
     * @param functions The functions to apply in order
     * @param <T>       The type of the function argument
     * @param <R>       The type of the {@link Optional}
     * @return The {@link Function} that yields the first present {@link Optional}
     */
    @SafeVarargs
    public static <T, R> Function<T, Optional<R>> firstPresent(Function<T, Optional<R>>... functions) {
        return t -> Arrays.stream(functions)
                .map(f -> f.apply(t))
                .filter(Optional::isPresent)
                .findFirst().orElse(Optional.empty());
    }

    /**
     * Returns a {@link Function} that yields the content of the first present {@link Optional} yielded by given
     * functions when applied to the same argument in order, or the object yielded by given fallback function if none of
     * the functions yield a present {@link Optional}.
     *
     * @param fallback  The fallback function
     * @param functions The functions to apply in order
     * @param <T>       The type of the function argument
     * @param <R>       The type of the {@link Optional} content or fallback object
     * @return The {@link Function} that yields the first present {@link Optional}
     */
    @SafeVarargs
    public static <T, R> Function<T, R> firstPresent(Function<T, R> fallback, Function<T, Optional<R>>... functions) {
        return t -> Arrays.stream(functions)
                .map(f -> f.apply(t))
                .filter(Optional::isPresent)
                .findFirst().orElseGet(() ->
                        Optional.of(fallback.apply(t))
                ).get();
    }

    /**
     * Returns a {@link Function} that delegates calls to given throwingFunction and returns an {@link Optional} of the
     * result or an empty {@link Optional} if given throwingFunction returned null or threw an {@link Exception}.
     *
     * @param throwingFunction The {@link Function} to delegate to
     * @param <T>              The parameter type of the {@link Function}
     * @param <R>              The return type of the {@link Function}
     * @return The {@link Function} that delegates calls to throwingFunction
     */
    public static <T, R> Function<T, Optional<R>> trying(ThrowingFunction<T, R, ?> throwingFunction) {
        return t -> {
            try {
                return Optional.ofNullable(throwingFunction.apply(t));
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }

    /**
     * Returns a {@link Function} that delegates calls to given throwingFunction and throws a new
     * {@link RuntimeException} if given throwingFunction threw an {@link Exception}.<br>
     * The {@link RuntimeException} has the original {@link Exception} as its {@link Throwable#getCause() cause}.
     *
     * @param throwingFunction The {@link Function} to delegate to
     * @param <T>              The parameter type of the {@link Function}
     * @param <R>              The return type of the {@link Function}
     * @return The {@link Function} that delegates calls to throwingFunction
     */
    public static <T, R> Function<T, R> rethrowing(ThrowingFunction<T, R, ?> throwingFunction) {
        return rethrowing(throwingFunction, RuntimeException::new);
    }

    /**
     * Returns a {@link Function} that delegates calls to given throwingFunction and throws a {@link RuntimeException}
     * created by given exceptionMapper if given throwingFunction threw an {@link Exception}.<br>
     * The original {@link Exception} is passed as the argument to given exceptionMapper.
     * <p>
     * <b>Example:</b><br>
     * {@code rethrowing(o -> { throw new Exception("example"); }, IllegalArgumentException::new)}
     * </p>
     *
     * @param throwingFunction The {@link Function} to delegate to
     * @param exceptionMapper  The original {@link Exception} to rethrown {@link RuntimeException} mapper
     * @param <T>              The parameter type of the {@link Function}
     * @param <R>              The return type of the {@link Function}
     * @return The {@link Function} that delegates calls to throwingFunction
     */
    public static <T, R> Function<T, R> rethrowing(
            ThrowingFunction<T, R, ?> throwingFunction,
            Function<Exception, ? extends RuntimeException> exceptionMapper) {
        return t -> {
            try {
                return throwingFunction.apply(t);
            } catch (Exception e) {
                throw exceptionMapper.apply(e);
            }
        };
    }

}
