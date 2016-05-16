package pl.lodz.p.ftims.tournamentpp.generator;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Michał Sośnicki
 */
public final class TimeGenerator {

    private TimeGenerator() {
    }

    public static Generator<LocalDate> makePastDate(int year) {
        return env -> LocalDate.now(env.getClock())
                .minusYears(year > 0 ? env.getRandom().nextInt(year - 1) : 0)
                .minusDays(env.getRandom().nextInt(365));
    }

    public static Generator<LocalDateTime> makeFutureTime(int days) {
        return env -> LocalDateTime.now(env.getClock())
                .plusDays(days > 0 ? env.getRandom().nextInt(days) : 0)
                .plusHours(env.getRandom().nextInt(24))
                .plusMinutes(env.getRandom().nextInt(60));
    }

}
