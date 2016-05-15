package pl.lodz.p.ftims.tournamentpp.generator;

import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;

import java.time.LocalDateTime;

import static pl.lodz.p.ftims.tournamentpp.generator.NumberGenerator.makeInt;
import static pl.lodz.p.ftims.tournamentpp.generator.TimeGenerator.makeFutureTime;

/**
 * @author Michał Sośnicki
 */
public final class RoundGenerator {

    private RoundGenerator() {
    }

    public static Generator<RoundEntity> makeRound(TournamentEntity tournament) {
        return env -> {
            final LocalDateTime startTime = makeFutureTime(0).apply(env);
            final LocalDateTime endTime = makeInt(10, 20)
                    .map(startTime::plusMinutes).apply(env);

            final RoundEntity round = new RoundEntity(startTime, endTime, tournament);
            tournament.getRounds().add(round);
            return round;
        };
    }

}
