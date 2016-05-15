package pl.lodz.p.ftims.tournamentpp.generator;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Clock;
import java.util.Random;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.Supplier;

/**
 * @author Michał Sośnicki
 */
@FunctionalInterface
public interface Generator<T> extends Function<Generator.Environment, T> {

    class Environment {
        private final Random random;
        private final Clock clock;
        private PasswordEncoder passwordEncoder;
        private long uniqueCounter = 0;

        public Environment(Random random, Clock clock, PasswordEncoder passwordEncoder) {
            this.random = random;
            this.clock = clock;
            this.passwordEncoder = passwordEncoder;
        }

        public Random getRandom() {
            return random;
        }

        public Clock getClock() {
            return clock;
        }

        public PasswordEncoder getPasswordEncoder() {
            return passwordEncoder;
        }

        public long nextUnique() {
            return uniqueCounter++;
        }

        @Override
        public String toString() {
            return "Environment{"
                  + "random=" + random
                  + ", clock=" + clock
                  + ", passwordEncoder=" + passwordEncoder
                  + ", uniqueCounter=" + uniqueCounter
                  + '}';
        }
    }

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
