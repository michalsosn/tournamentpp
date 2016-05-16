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
public class DoubleEliminationFormat extends EliminationFormat implements TournamentFormat {

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
        List<CompetitorRoleEntity> winners = new ArrayList<>();
        List<CompetitorRoleEntity> losers = new ArrayList<>();
        List<CompetitorRoleEntity> luckyWinners = new ArrayList<>();
        //TODO - trzeba sobie popaczeć co się odpierdala dla różnych wielkości
        //Zwycięzców z pierwszej połowy gier do pierwszej połowy sparować
        //Przegrani z pierwszej dołączają do zwycięzców z drugiej
        for (int i = 0; i < lastGames.size(); i++) {

            if (i < lastGames.size() / 2) {
//                String winner = lastGames.get(i).getWinner().getAccount().getUsername();
                winners.add(lastGames.get(i).getWinner());
                for (CompetitorRoleEntity competitor : lastGames
                        .get(i).getCompetitors()) {
                    if (!lastGames.get(i).getWinner().equals(competitor)) {
                        losers.add(competitor);
                        break;
                    }
                }
            } else {
                luckyWinners.add(lastGames.get(i).getWinner());
            }
        }

        List<CompetitorRoleEntity> competitorsForThisRound = new ArrayList<>();
        for (CompetitorRoleEntity competitor : winners) {
            competitorsForThisRound.add(competitor);
        }

        for (int i = 0; i < luckyWinners.size(); i++) {
            competitorsForThisRound.add(luckyWinners.get(i));
            competitorsForThisRound.add(losers.get(i));
        }

        addNextRoundCompetitorsToRound(roundEntity, competitorsForThisRound);

//        System.out.println(competitorsForThisRound);
        return roundEntity;
    }
}
