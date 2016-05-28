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
        } else if (tournament.getRounds().size() == 1) {
            return prepareSecondRound(
                    tournament.getRounds().get(tournament.getRounds().size() - 1)
            );
        } else {
            return prepareRoundBasedOnRound(
                    tournament.getRounds().get(tournament.getRounds().size() - 1),
                    tournament.getRounds().size(),
                    tournament.getCompetitors().size()
            );
        }
    }

    private RoundEntity prepareSecondRound(RoundEntity firstRound) {
        RoundEntity roundEntity = new RoundEntity();
        List<GameEntity> lastGames = firstRound.getGames();
        List<CompetitorRoleEntity> lastWinners = new ArrayList<>();
        List<CompetitorRoleEntity> lastLosers = new ArrayList<>();
        for (int i = 0; i < lastGames.size(); i++) {
            //Ostatni zwycięzca
            CompetitorRoleEntity lastWinner = lastGames.get(i).getWinner();
            lastWinners.add(lastWinner);
            for (CompetitorRoleEntity competitor : lastGames.get(i).getCompetitors()) {
                if (competitor.getAccount().getUsername()
                        != lastWinner.getAccount().getUsername()) {
                    lastLosers.add(competitor);
                    break;
                }
            }
        }

        List<CompetitorRoleEntity> competitorsForNextRound = new ArrayList<>();
        lastWinners.stream().forEach(competitorRoleEntity ->
                competitorsForNextRound.add(competitorRoleEntity));

        lastLosers.stream().forEach(competitorRoleEntity ->
                competitorsForNextRound.add(competitorRoleEntity));

        addNextRoundCompetitorsToRound(roundEntity, competitorsForNextRound);

        return roundEntity;
    }

    private RoundEntity prepareRoundBasedOnRound(RoundEntity lastRoundEntity,
                                                 int roundNumber,
                                                 int startCompetitors) {

        RoundEntity roundEntity = new RoundEntity();

        int winnerGamesFromLastRound = startCompetitors;
        for (int i = 0; i < roundNumber; i++) {
            winnerGamesFromLastRound /= 2;
            if (winnerGamesFromLastRound == 0) {
                winnerGamesFromLastRound = 1;
                break;
            }
        }

        List<GameEntity> lastRoundGames = lastRoundEntity.getGames();
        List<CompetitorRoleEntity> competitorsForThisRound = new ArrayList<>();

//        System.out.println("Round number " + roundNumber);
//        System.out.println("Tournament based competitors " + startCompetitors);
//        System.out.println("Winner Side Games from last
// round " + winnerGamesFromLastRound);
//        System.out.println("Last games size " + lastRoundGames.size());

        int lastGameParticipants = 0;
        for (GameEntity g : lastRoundGames) {
            lastGameParticipants += g.getCompetitors().size();
        }

        if (winnerGamesFromLastRound == 1 && lastRoundGames.size() != 2) {
//            System.out.println("Oczekiwanie, dół się robi");
            competitorsForThisRound.addAll(lastRoundGames.get(0).getCompetitors());
            for (int i = winnerGamesFromLastRound; i < lastRoundGames.size(); i++) {
                competitorsForThisRound.add(lastRoundGames.get(i).getWinner());
            }

        } else if (lastGameParticipants == 3) {
//            System.out.println("FINAL");
            competitorsForThisRound.add(lastRoundGames.get(0).getWinner());
            competitorsForThisRound.add(lastRoundGames.get(1).getWinner());
        } else if (winnerGamesFromLastRound == 1 && lastRoundGames.size() == 2) {
//            System.out.println("Przed finał");

            competitorsForThisRound.addAll(lastRoundGames.get(0).getCompetitors());
            competitorsForThisRound.add(lastRoundGames.get(1).getWinner());

            GameEntity game = new GameEntity();
            game.setWinner(competitorsForThisRound.get(0));
            game.getCompetitors().add(competitorsForThisRound.get(0));
            roundEntity.getGames().add(game);
            game = new GameEntity();
            game.getCompetitors().add(competitorsForThisRound.get(1));
            game.getCompetitors().add(competitorsForThisRound.get(2));
            roundEntity.getGames().add(game);
            return roundEntity;


        } else {
//            System.out.println("Normalna ustawka");

            List<CompetitorRoleEntity> lastWinners = new ArrayList<>();
            List<CompetitorRoleEntity> losersFromWinners = new ArrayList<>();
            List<CompetitorRoleEntity> luckyWinners = new ArrayList<>();

            for (int i = 0; i < winnerGamesFromLastRound; i++) {
                CompetitorRoleEntity winner = lastRoundGames.get(i).getWinner();
                lastWinners.add(winner);
                for (CompetitorRoleEntity competitor
                        : lastRoundGames.get(i).getCompetitors()) {
                    if (competitor.getId()
                            != winner.getId()) {
                        losersFromWinners.add(competitor);
                        break;
                    }
                }
            }

            for (int i = winnerGamesFromLastRound; i < lastRoundGames.size(); i++) {
                luckyWinners.add(lastRoundGames.get(i).getWinner());
            }

            competitorsForThisRound.addAll(lastWinners);
            for (int i = 0; i < losersFromWinners.size(); i++) {
                competitorsForThisRound.add(losersFromWinners.get(i));
                competitorsForThisRound.add(luckyWinners.get(i));
            }
        }
        addNextRoundCompetitorsToRound(roundEntity, competitorsForThisRound);

        return roundEntity;
    }
}
