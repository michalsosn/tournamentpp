package pl.lodz.p.ftims.tournamentpp.trees;

import org.springframework.stereotype.Component;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        List<CompetitorRoleEntity> competitorsInThisRound = new ArrayList<>();
        for (GameEntity game : lastGames) {
            competitorsInThisRound.add(game.getWinner());
        }

        addNextRoundCompetitorsToRound(roundEntity, competitorsInThisRound);

        return roundEntity;
    }

}
