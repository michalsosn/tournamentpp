package pl.lodz.p.ftims.tournamentpp.trees;

import org.junit.Test;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Daniel on 2016-05-15.
 */
public class SingleEliminationFormatTest extends EliminationFormatTest {

    public SingleEliminationFormatTest() {
        super(new SingleEliminationFormat());
    }

    @Test
    public void shouldCreateNextRoundForSingleElimination(){
        // given
        final TournamentEntity tournament = linker.getTournaments().get(0);
        final List<CompetitorRoleEntity> competitors = tournament.getCompetitors();

        linker.makeRound().apply(env);
        GameEntity game11 = linker.makeGame().apply(env);
        game11.setWinner(competitors.get(0));
        GameEntity game12 = linker.makeGame().apply(env);
        game12.setWinner(competitors.get(2));

        // when
        RoundEntity round2 = format.prepareRound(tournament, new Random());

        // then
        final List<CompetitorRoleEntity> newCompetitors = round2.getGames().stream()
                .flatMap(game -> game.getCompetitors().stream())
                .collect(Collectors.toList());

        final CompetitorRoleEntity[] lastWinners = tournament.getRounds().stream()
                .flatMap(round -> round.getGames().stream())
                .map(GameEntity::getWinner)
                .toArray(CompetitorRoleEntity[]::new);

        assertThat(newCompetitors).containsExactly(lastWinners);
    }

}