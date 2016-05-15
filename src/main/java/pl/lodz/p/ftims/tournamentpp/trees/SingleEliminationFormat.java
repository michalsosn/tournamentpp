package pl.lodz.p.ftims.tournamentpp.trees;

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
public class SingleEliminationFormat implements TournamentFormat {

    @Override
    public RoundEntity prepareRound(TournamentEntity tournament) {

        if (tournament.getRounds().isEmpty()) {
            return prepareFirstRound(tournament.getCompetitors());
        } else {
            return prepareRoundBasedOnRound(
                    tournament.getRounds()
                            .get(tournament.getRounds().size() - 1));
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

    private RoundEntity prepareFirstRound(List<CompetitorRoleEntity> competitors) {
        RoundEntity roundEntity = new RoundEntity();
        List<CompetitorRoleEntity> competitorsInRounds
                = new ArrayList<>(competitors);

        for (CompetitorRoleEntity c : competitorsInRounds) {
            System.out.println(c.getAccount().getName());
        }
        Random random = new Random();
        for (int i = 0; i < competitors.size() / 2; i++) {
            GameEntity game = new GameEntity();
            game.setRound(roundEntity);
            int firstPlayer = random.nextInt(competitorsInRounds.size());
            game.getCompetitors().add(competitorsInRounds.get(firstPlayer));
            competitorsInRounds.remove(firstPlayer);
            int secondPlayer = random.nextInt(competitorsInRounds.size());
            game.getCompetitors().add(competitorsInRounds.get(secondPlayer));
            competitorsInRounds.remove(secondPlayer);
            roundEntity.getGames().add(game);
        }

        return roundEntity;
    }
}
