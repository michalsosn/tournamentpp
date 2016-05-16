package pl.lodz.p.ftims.tournamentpp.trees;

import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Daniel on 2016-05-15.
 */
public class SingleEliminationFormat implements TournamentFormat {

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

    private RoundEntity prepareRoundBasedOnRound(RoundEntity lastRoundEntity) {
        RoundEntity roundEntity = new RoundEntity();
        List<GameEntity> lastGames = lastRoundEntity.getGames();
        List<CompetitorRoleEntity> competitorsInThisRound = new ArrayList<>();
        for (GameEntity game : lastGames) {
            competitorsInThisRound.add(game.getWinner());
        }

        for (int i = 0; i < competitorsInThisRound.size(); i += 2) {
            GameEntity game = new GameEntity();
            game.getCompetitors().add(competitorsInThisRound.get(i));
            game.getCompetitors().add(competitorsInThisRound.get(i + 1));
            roundEntity.getGames().add(game);
        }

        return roundEntity;
    }

    private RoundEntity prepareFirstRound(
            List<CompetitorRoleEntity> competitors, Random random
    ) {
        RoundEntity roundEntity = new RoundEntity();
        List<CompetitorRoleEntity> competitorsInRounds = new ArrayList<>(competitors);

        Collections.shuffle(competitorsInRounds, random);
        for (int i = 0; i < competitorsInRounds.size() / 2; ++i) {
            final CompetitorRoleEntity first = competitorsInRounds.get(2 * i);
            final CompetitorRoleEntity second = competitorsInRounds.get(2 * i + 1);
            GameEntity game = new GameEntity(roundEntity);
            game.getCompetitors().add(first);
            game.getCompetitors().add(second);
            roundEntity.getGames().add(game);
        }

        return roundEntity;
    }
}
