package pl.lodz.p.ftims.tournamentpp.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Collection;
import java.util.Map;

/**
 * @author Michał Sośnicki
 */
public final class EntityUtils {
    private EntityUtils() {
    }

    @SuppressFBWarnings("RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT")
    public static void loadEager(Collection<?> collection) {
        collection.isEmpty();
    }

    @SuppressFBWarnings("RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT")
    public static void loadEager(Map<?, ?> map) {
        map.isEmpty();
    }
}
