package pl.lodz.p.ftims.tournamentpp.trees;

import org.junit.Test;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.builders.GameEntityBuilder;
import pl.lodz.p.ftims.tournamentpp.builders.RoundEntityBuilder;
import pl.lodz.p.ftims.tournamentpp.builders.TournamentEntityBuilder;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoundRobinTournamentFormatUnitTest {

    private RoundRobinTournamentFormat objectUnderTest = new RoundRobinTournamentFormat();

    @Test
    public void shouldPrepareFirstRoundForRoundRobin() throws Exception {
        //given
        TournamentEntity tournamentEntity = TournamentEntityBuilder.aTournamentEntity()
                .withCompetitors(
                        LongStream.rangeClosed(1L, 7L)
                                .mapToObj(this::aCompetitor)
                                .collect(Collectors.toList()))
                .build();
        //when
        RoundEntity result = objectUnderTest.prepareRound(tournamentEntity, null);
        //then
        assertThat(result.getGames()).hasSize(21);
    }

    @Test
    public void shouldPrepareNextRoundforRoundRobin() throws Exception {
        //given
        TournamentEntity tournamentEntity = TournamentEntityBuilder.aTournamentEntity()
                .withRound(aRound())
                .build();
        //when
        RoundEntity result = objectUnderTest.prepareRound(tournamentEntity, null);
        //then
        assertThat(result.getGames()).hasSize(1);
        assertThat(result.getGames().get(0).getCompetitors())
                .extracting(CompetitorRoleEntity::getId)
                .containsExactlyInAnyOrder(1L, 2L);
    }

    private CompetitorRoleEntity aCompetitor(Long id) {
        CompetitorRoleEntity competitor = mock(CompetitorRoleEntity.class);
        when(competitor.getId()).thenReturn(id);
        return competitor;
    }

    private GameEntity aGame(CompetitorRoleEntity... competitors) {
        GameEntityBuilder builder = GameEntityBuilder.aGameEntity();
        Arrays.stream(competitors).forEach(builder::withCompetitor);
        builder.withWinner(competitors[0]);
        return builder.build();
    }

    private RoundEntity aRound() {
        CompetitorRoleEntity competitor1 = aCompetitor(1L);
        CompetitorRoleEntity competitor2 = aCompetitor(2L);
        CompetitorRoleEntity competitor3 = aCompetitor(3L);
        return RoundEntityBuilder.aRoundEntity()
                .withGame(aGame(competitor1, competitor2))
                .withGame(aGame(competitor2, competitor3))
                .build();
    }
}