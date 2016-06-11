package pl.lodz.p.ftims.tournamentpp.trees;

import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Daniel on 2016-05-16.
 */
public class EliminationFormat {

    RoundEntity prepareFirstRound(List<CompetitorRoleEntity> competitors, Random random) {
        RoundEntity roundEntity = new RoundEntity();
        List<CompetitorRoleEntity> competitorsInRounds = new ArrayList<>(competitors);

        Collections.shuffle(competitorsInRounds, random);
        for (int i = 0; i < competitorsInRounds.size() / 2; ++i) {
            final CompetitorRoleEntity first = competitorsInRounds.get(2 * i);
            final CompetitorRoleEntity second = competitorsInRounds.get(2 * i + 1);
            GameEntity game = new GameEntity();
            game.getCompetitors().add(first);
            game.getCompetitors().add(second);
            roundEntity.getGames().add(game);
        }

        return roundEntity;
    }

    void addNextRoundCompetitorsToRound(RoundEntity roundEntity,
                                        List<CompetitorRoleEntity> competitors) {
        for (int i = 0; i < competitors.size(); i += 2) {
            GameEntity game = new GameEntity();
            game.getCompetitors().add(competitors.get(i));
            game.getCompetitors().add(competitors.get(i + 1));
            roundEntity.getGames().add(game);
        }
    }

}
