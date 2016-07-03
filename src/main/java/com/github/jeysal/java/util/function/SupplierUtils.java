package com.github.jeysal.java.util.function;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Optional;
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

}
