package pl.lodz.p.ftims.tournamentpp.trees;

import org.springframework.util.CollectionUtils;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;

import java.util.*;
import java.util.stream.Collectors;

public class RoundRobinTournamentFormat implements TournamentFormat {

    @Override
    public RoundEntity prepareRound(TournamentEntity tournament, Random random) {
        if (CollectionUtils.isEmpty(tournament.getRounds())) {
            return prepareFirstRound(tournament);
        }
        return prepareNextRound(tournament);
    }

    private RoundEntity prepareNextRound(TournamentEntity tournament) {
        RoundEntity lastRound = tournament.getRounds()
                .get(tournament.getRounds().size() - 1);
        Map<CompetitorRoleEntity, Long> winners = lastRound.getGames().stream()
                .map(GameEntity::getWinner)
                .collect(Collectors.groupingBy(
                        gameWinner -> gameWinner, Collectors.counting()));

        List<CompetitorRoleEntity> winnersWithSameScore = winners.entrySet().stream()
                .filter(entry ->
                        Collections.frequency(winners.values(), entry.getValue()) > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return prepareRound(winnersWithSameScore);
    }

    private RoundEntity prepareFirstRound(TournamentEntity tournament) {
        return prepareRound(tournament.getCompetitors());
    }

    private RoundEntity prepareRound(List<CompetitorRoleEntity> competitors) {
        final Collection<CompetitorRoleEntity> filteredCompetitors = new ArrayList<>();
        final RoundEntity round = new RoundEntity();
        competitors.stream()
                .forEach(firstCompetitor -> {
                        filteredCompetitors.add(firstCompetitor);
                        competitors.stream()
                                .filter(competitor ->
                                        !filteredCompetitors.contains(competitor))
                                .forEach(secondCompetitor -> {
                                    GameEntity game = new GameEntity();
                                    game.setRound(round);
                                    game.getCompetitors().add(firstCompetitor);
                                    game.getCompetitors().add(secondCompetitor);
                                    round.getGames().add(game);
                        });
        });
        return round;
    }
}
