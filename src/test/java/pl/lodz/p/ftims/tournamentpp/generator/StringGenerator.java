package pl.lodz.p.ftims.tournamentpp.generator;

import java.util.Random;

/**
 * @author Michał Sośnicki
 */
public final class StringGenerator {

    private StringGenerator() {
    }

    public static Generator<String> makeAlpha(int length) {
        return Generator.withRandom(random -> {
            StringBuilder stringBuilder = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                stringBuilder.append(makeAlpha(random));
            }
            return stringBuilder.toString();
        });
    }

    public static Generator<String> makeNumeric(int length) {
        return Generator.withRandom(random ->
                random.ints(length, 0, 10)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString()
        );
    }

    private static char makeAlpha(Random random) {
        final int numRange = '9' - '0' + 1;
        final int upperRange = 'Z' - 'A' + 1 + numRange;
        final int lowerRange = 'z' - 'a' + 1 + upperRange;

        final int next = random.nextInt(lowerRange);
        if (next < numRange) {
            return (char) (next + '0');
        } else if (next < upperRange) {
            return (char) (next + 'A');
        } else {
            return (char) (next + 'a');
        }
    }

}
