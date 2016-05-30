package pl.lodz.p.ftims.tournamentpp.generator;

import java.time.Clock;
import java.util.Random;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.Supplier;

/**
 * @author Michał Sośnicki
 */
@FunctionalInterface
public interface Generator<T> extends Function<Environment, T> {

    static <T> Generator<T> withRandom(Function<Random, T> generator) {
        return env -> generator.apply(env.getRandom());
    }

    static <T> Generator<T> withClock(Function<Clock, T> generator) {
        return env -> generator.apply(env.getClock());
    }

    static <T> Generator<T> withUnique(LongFunction<T> generator) {
        return env -> generator.apply(env.nextUnique());
    }

    default <S> Generator<S> map(Function<? super T, ? extends S> after) {

        return env -> after.apply(apply(env));
    }

    default <S> Generator<S> flatMap(Function<? super T, Generator<? extends S>> after) {
        return env -> after.apply(apply(env)).apply(env);
    }

    default Supplier<T> toSupplier(Environment environment) {
        return () -> apply(environment);
    }

}
