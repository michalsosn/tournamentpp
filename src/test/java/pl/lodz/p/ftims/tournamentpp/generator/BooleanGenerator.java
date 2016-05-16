package pl.lodz.p.ftims.tournamentpp.generator;

/**
 * @author Michał Sośnicki
 */
public final class BooleanGenerator {

    private BooleanGenerator() {
    }

    public static <T> Generator<T> maybeNull(Generator<T> generator) {
        return env -> env.getRandom().nextBoolean() ? null : generator.apply(env);
    }

}
