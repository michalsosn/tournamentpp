package pl.lodz.p.ftims.tournamentpp.generator;

import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.OrganizerRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.trees.Format;

import java.util.Arrays;

import static pl.lodz.p.ftims.tournamentpp.generator.TimeGenerator.*;

/**
 * @author Michał Sośnicki
 */
public final class TournamentGenerator {

    private TournamentGenerator() {
    }

    public static Generator<TournamentEntity> makeTournament(
            Format format, OrganizerRoleEntity organizer,
            CompetitorRoleEntity... competitors
    ) {
        return env -> {
            final TournamentEntity tournament = new TournamentEntity();

            tournament.setLocation(makeShortString().apply(env));
            tournament.setDescription(makeLongString().apply(env));
            tournament.setStartTime(makeFutureTime(14).apply(env));
            tournament.setFormat(format);

            tournament.setOrganizer(organizer);
            organizer.getTournaments().add(tournament);

            tournament.getCompetitors().addAll(Arrays.asList(competitors));
            Arrays.stream(competitors).forEach(competitor ->
                    competitor.getTournaments().add(tournament)
            );

            return tournament;
        };
    }

    private static Generator<String> makeShortString() {
        return NumberGenerator.makeInt(1, 255).flatMap(StringGenerator::makeAlpha);
    }

    private static Generator<String> makeLongString() {
        return NumberGenerator.makeInt(1, 2048).flatMap(StringGenerator::makeAlpha);
    }

}
