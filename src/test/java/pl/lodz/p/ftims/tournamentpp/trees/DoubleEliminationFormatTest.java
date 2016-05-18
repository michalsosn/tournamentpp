package pl.lodz.p.ftims.tournamentpp.trees;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.Role;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.generator.Generator;
import pl.lodz.p.ftims.tournamentpp.generator.GeneratorLinker;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Daniel on 2016-05-16.
 */
public class DoubleEliminationFormatTest {
    private Generator.Environment env;
    private GeneratorLinker linker;
    private TournamentFormat format = new DoubleEliminationFormat();

    @Before
    public void setUp() throws Exception {
        env = new Generator.Environment(
                new Random(),
                Clock.fixed(Instant.now(), ZoneId.systemDefault()),
                new BCryptPasswordEncoder()
        );
        linker = new GeneratorLinker();

        linker.makeAccount(true, Role.ROLE_ORGANIZER).apply(env);
        for (int i = 0; i < 8; ++i) {
            linker.makeAccount(true, Role.ROLE_COMPETITOR).apply(env);
        }
        linker.makeTournament(
                Format.DOUBLE_ELIMINATION,
                linker.getCompetitors().stream().toArray(CompetitorRoleEntity[]::new)
        ).apply(env);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test // TODO REMOVE HORRIBLE DUPLICATION
    public void shouldCreateFirstRoundForDoubleElimination(){
        // given
        final TournamentEntity tournament = linker.getTournaments().get(0);

        // when
        final RoundEntity round = format.prepareRound(tournament, new Random());

        // then
        final List<CompetitorRoleEntity> roundCompetitors = round.getGames().stream()
                .flatMap(game -> game.getCompetitors().stream())
                .collect(Collectors.toList());
        final CompetitorRoleEntity[] tournamentCompetitors
                = tournament.getCompetitors().toArray(new CompetitorRoleEntity[0]);
        assertThat(roundCompetitors).containsExactlyInAnyOrder(tournamentCompetitors);
    }

   /* @Test
    public void shouldCreateNextRoundForDoubleElimination(){
        final TournamentEntity tournament = linker.getTournaments().get(0);
        final List<CompetitorRoleEntity> competitors = tournament.getCompetitors();

        linker.makeRound().apply(env);
        GameEntity game11 = linker.makeGame().apply(env);
        game11.setWinner(competitors.get(0));
        game11.getCompetitors().add(competitors.get(0));
        game11.getCompetitors().add(competitors.get(1));
        GameEntity game12 = linker.makeGame().apply(env);
        game12.setWinner(competitors.get(2));
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

        RoundEntity round2 = format.prepareRound(tournament, new Random());

    }*/
}