package pl.lodz.p.ftims.tournamentpp.trees;

import org.junit.Test;
import pl.lodz.p.ftims.tournamentpp.builders.GameEntityBuilder;
import pl.lodz.p.ftims.tournamentpp.builders.RoundEntityBuilder;
import pl.lodz.p.ftims.tournamentpp.builders.TournamentEntityBuilder;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Daniel on 2016-05-16.
 */
public class DoubleEliminationFormatTest extends EliminationFormatTest {

    public DoubleEliminationFormatTest() {
        super(new DoubleEliminationFormat());
    }

    @Test
    public void shouldCreateSecondRoundForDoubleElimination() {
        final TournamentEntity tournament = linker.getTournaments().get(0);
        final List<CompetitorRoleEntity> competitors = tournament.getCompetitors();

        linker.makeRound().apply(env);
        GameEntity game11 = linker.makeGame().apply(env);
        game11.setWinner(competitors.get(0));
        game11.getCompetitors().add(competitors.get(0));
        game11.getCompetitors().add(competitors.get(1));
        GameEntity game12 = linker.makeGame().apply(env);
        game12.setWinner(competitors.get(3));
        game12.getCompetitors().add(competitors.get(2));
        game12.getCompetitors().add(competitors.get(3));

        GameEntity game13 = linker.makeGame().apply(env);
        game13.setWinner(competitors.get(4));
        game13.getCompetitors().add(competitors.get(4));
        game13.getCompetitors().add(competitors.get(5));
        GameEntity game14 = linker.makeGame().apply(env);
        game14.setWinner(competitors.get(6));
        game14.getCompetitors().add(competitors.get(6));
        game14.getCompetitors().add(competitors.get(7));

        List<CompetitorRoleEntity> expected = new ArrayList<>();
        expected.add(game11.getCompetitors().get(0));
        expected.add(game12.getCompetitors().get(1));
        expected.add(game13.getCompetitors().get(0));
        expected.add(game14.getCompetitors().get(0));
        expected.add(game11.getCompetitors().get(1));
        expected.add(game12.getCompetitors().get(0));
        expected.add(game13.getCompetitors().get(1));
        expected.add(game14.getCompetitors().get(1));

        // when
        final RoundEntity round = format.prepareRound(tournament, new Random());

        // then
        final List<CompetitorRoleEntity> roundCompetitors = round.getGames().stream()
                .flatMap(game -> game.getCompetitors().stream())
                .collect(Collectors.toList());
        assertThat(roundCompetitors).containsExactly(expected.toArray(new CompetitorRoleEntity[expected.size()]));
    }

    @Test
    public void shouldCreateThirdRoundForDoubleElimination(){

        RoundEntity r1 = RoundEntityBuilder.aRoundEntity().build();
        List<CompetitorRoleEntity> participants = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            participants.add(aCompetitor((long) i+1));
        }

        GameEntity g1 = GameEntityBuilder
                .aGameEntity()
                .withCompetitor(participants.get(0))
                .withCompetitor(participants.get(3))
                .withWinner(participants.get(0))
                .build();
        GameEntity g2 = GameEntityBuilder
                .aGameEntity()
                .withCompetitor(participants.get(4))
                .withCompetitor(participants.get(6))
                .withWinner(participants.get(6))
                .build();
        GameEntity g3 = GameEntityBuilder
                .aGameEntity()
                .withCompetitor(participants.get(1))
                .withCompetitor(participants.get(2))
                .withWinner(participants.get(1))
                .build();
        GameEntity g4 = GameEntityBuilder
                .aGameEntity()
                .withCompetitor(participants.get(5))
                .withCompetitor(participants.get(7))
                .withWinner(participants.get(5))
                .build();

        RoundEntity r2 = RoundEntityBuilder
                .aRoundEntity()
                .withGame(g1)
                .withGame(g2)
                .withGame(g3)
                .withGame(g4)
                .build();



        TournamentEntity tournamentEntity = TournamentEntityBuilder.aTournamentEntity()
                .withCompetitors(
                        LongStream.rangeClosed(1L, 8L)
                                .mapToObj(this::aCompetitor)
                                .collect(Collectors.toList()))
                .withRound(r1)
                .withRound(r2)
                .build();

        List<CompetitorRoleEntity> expected = new ArrayList<>();
        expected.add(participants.get(0));
        expected.add(participants.get(6));
        expected.add(participants.get(3));
        expected.add(participants.get(1));
        expected.add(participants.get(4));
        expected.add(participants.get(5));

        System.out.println("Tournament rounds "+tournamentEntity.getRounds().size());
        // when
        final RoundEntity round = format.prepareRound(tournamentEntity, new Random());

        // then
        final List<CompetitorRoleEntity> roundCompetitors = round.getGames().stream()
                .flatMap(game -> game.getCompetitors().stream())
                .collect(Collectors.toList());
        assertThat(roundCompetitors).containsExactly(expected.toArray(new CompetitorRoleEntity[expected.size()]));

    }

    @Test
    public void shouldCreateFourthRoundForDoubleElimination(){
        RoundEntity r = RoundEntityBuilder.aRoundEntity().build();
        List<CompetitorRoleEntity> participants = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            participants.add(aCompetitor((long) i+1));
        }

        GameEntity g1 = GameEntityBuilder
                .aGameEntity()
                .withCompetitor(participants.get(0))
                .withCompetitor(participants.get(6))
                .withWinner(participants.get(0))
                .build();
        GameEntity g2 = GameEntityBuilder
                .aGameEntity()
                .withCompetitor(participants.get(1))
                .withCompetitor(participants.get(3))
                .withWinner(participants.get(1))
                .build();
        GameEntity g3 = GameEntityBuilder
                .aGameEntity()
                .withCompetitor(participants.get(4))
                .withCompetitor(participants.get(5))
                .withWinner(participants.get(4))
                .build();

        RoundEntity r3 = RoundEntityBuilder
                .aRoundEntity()
                .withGame(g1)
                .withGame(g2)
                .withGame(g3)
                .build();

        TournamentEntity tournamentEntity = TournamentEntityBuilder.aTournamentEntity()
                .withCompetitors(
                        LongStream.rangeClosed(1L, 8L)
                                .mapToObj(this::aCompetitor)
                                .collect(Collectors.toList()))
                .withRound(r)
                .withRound(r)
                .withRound(r3)
                .build();

        List<CompetitorRoleEntity> expected = new ArrayList<>();
        expected.add(participants.get(0));
        expected.add(participants.get(6));
        expected.add(participants.get(1));
        expected.add(participants.get(4));

        System.out.println("Tournament rounds "+tournamentEntity.getRounds().size());
        // when
        final RoundEntity round = format.prepareRound(tournamentEntity, new Random());

        // then
        final List<CompetitorRoleEntity> roundCompetitors = round.getGames().stream()
                .flatMap(game -> game.getCompetitors().stream())
                .collect(Collectors.toList());
        assertThat(roundCompetitors).containsExactly(expected.toArray(new CompetitorRoleEntity[expected.size()]));
    }

    @Test
    public void shouldCreateFifthRoundForDoubleElimination(){
        RoundEntity r = RoundEntityBuilder.aRoundEntity().build();
        List<CompetitorRoleEntity> participants = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            participants.add(aCompetitor((long) i+1));
        }

        GameEntity g1 = GameEntityBuilder
                .aGameEntity()
                .withCompetitor(participants.get(0))
                .withCompetitor(participants.get(6))
                .withWinner(participants.get(0))
                .build();
        GameEntity g2 = GameEntityBuilder
                .aGameEntity()
                .withCompetitor(participants.get(1))
                .withCompetitor(participants.get(4))
                .withWinner(participants.get(1))
                .build();


        RoundEntity r4 = RoundEntityBuilder
                .aRoundEntity()
                .withGame(g1)
                .withGame(g2)
                .build();

        TournamentEntity tournamentEntity = TournamentEntityBuilder.aTournamentEntity()
                .withCompetitors(
                        LongStream.rangeClosed(1L, 8L)
                                .mapToObj(this::aCompetitor)
                                .collect(Collectors.toList()))
                .withRound(r)
                .withRound(r)
                .withRound(r)
                .withRound(r4)
                .build();

        List<CompetitorRoleEntity> expected = new ArrayList<>();
        expected.add(participants.get(0));
        expected.add(participants.get(6));
        expected.add(participants.get(1));

        System.out.println("Tournament rounds "+tournamentEntity.getRounds().size());
        // when
        final RoundEntity round = format.prepareRound(tournamentEntity, new Random());

        // then
        final List<CompetitorRoleEntity> roundCompetitors = round.getGames().stream()
                .flatMap(game -> game.getCompetitors().stream())
                .collect(Collectors.toList());
        assertThat(roundCompetitors).containsExactly(expected.toArray(new CompetitorRoleEntity[expected.size()]));
    }

    @Test
    public void shouldCreateSixthRoundForDoubleElimination(){
        RoundEntity r = RoundEntityBuilder.aRoundEntity().build();
        List<CompetitorRoleEntity> participants = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            participants.add(aCompetitor((long) i+1));
        }

        GameEntity g1 = GameEntityBuilder
                .aGameEntity()
                .withCompetitor(participants.get(0))
                .withWinner(participants.get(0))
                .build();
        GameEntity g2 = GameEntityBuilder
                .aGameEntity()
                .withCompetitor(participants.get(6))
                .withCompetitor(participants.get(1))
                .withWinner(participants.get(6))
                .build();


        RoundEntity r6 = RoundEntityBuilder
                .aRoundEntity()
                .withGame(g1)
                .withGame(g2)
                .build();

        TournamentEntity tournamentEntity = TournamentEntityBuilder.aTournamentEntity()
                .withCompetitors(
                        LongStream.rangeClosed(1L, 8L)
                                .mapToObj(this::aCompetitor)
                                .collect(Collectors.toList()))
                .withRound(r)
                .withRound(r)
                .withRound(r)
                .withRound(r)
                .withRound(r6)
                .build();

        List<CompetitorRoleEntity> expected = new ArrayList<>();
        expected.add(participants.get(0));
        expected.add(participants.get(6));

        System.out.println("Tournament rounds "+tournamentEntity.getRounds().size());
        // when
        final RoundEntity round = format.prepareRound(tournamentEntity, new Random());

        // then
        final List<CompetitorRoleEntity> roundCompetitors = round.getGames().stream()
                .flatMap(game -> game.getCompetitors().stream())
                .collect(Collectors.toList());
        assertThat(roundCompetitors).containsExactly(expected.toArray(new CompetitorRoleEntity[expected.size()]));
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

}