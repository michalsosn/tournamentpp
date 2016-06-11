package pl.lodz.p.ftims.tournamentpp.trees;

import org.springframework.stereotype.Component;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Daniel on 2016-05-15.
 */

@Component
public class SingleEliminationFormat extends EliminationFormat implements TournamentFormat {

    @Override
    public RoundEntity prepareRound(TournamentEntity tournament, Random random) {
        if (tournament.getRounds().isEmpty()) {
            return prepareFirstRound(tournament.getCompetitors(), random);
        } else {
            return prepareRoundBasedOnRound(
                    tournament.getRounds().get(tournament.getRounds().size() - 1)
            );
        }
    }

    @Override
    public boolean supportedFormat(Format format) {
        return format == Format.SINGLE_ELIMINATION;
    }

    private RoundEntity prepareRoundBasedOnRound(RoundEntity lastRoundEntity) {
        RoundEntity roundEntity = new RoundEntity();
        List<GameEntity> lastGames = lastRoundEntity.getGames();
        List<CompetitorRoleEntity> competitorsInThisRound = lastGames.stream()
                .map(GameEntity::getWinner)
                .collect(Collectors.toList());

        addNextRoundCompetitorsToRound(roundEntity, competitorsInThisRound);

        return roundEntity;
    }

}
