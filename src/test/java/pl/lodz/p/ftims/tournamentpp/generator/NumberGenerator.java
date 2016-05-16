package pl.lodz.p.ftims.tournamentpp.generator;

/**
 * @author Michał Sośnicki
 */
public final class NumberGenerator {

    private NumberGenerator() {
    }

    public static Generator<Integer> makeInt(int min, int max) {
        return Generator.withRandom(random -> {
            final int range = max - min + 1;
            return random.nextInt(range) + min;
        });
    }

}
