package pl.lodz.p.ftims.tournamentpp.generator;

import java.util.List;

/**
 * @author Michał Sośnicki
 */
public final class CollectionGenerator {

    private CollectionGenerator() {
    }

    public static <T> Generator<T> getAny(List<T> list) {
        return Generator.withRandom(random -> list.get(random.nextInt(list.size())));
    }

}
