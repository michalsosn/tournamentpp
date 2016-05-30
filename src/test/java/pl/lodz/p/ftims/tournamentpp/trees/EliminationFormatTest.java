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
 * Created by Daniel on 2016-05-28.
 */
public class EliminationFormatTest {
    protected Generator.Environment env;
    protected GeneratorLinker linker;
    protected TournamentFormat format = new SingleEliminationFormat();

    @Before
    public void setUp() throws Exception {
        env = new Generator.Environment(
                new Random(),
                Clock.fixed(Instant.now(), ZoneId.systemDefault()),
                new BCryptPasswordEncoder()
        );
        linker = new GeneratorLinker();

        linker.makeAccount(true, Role.ROLE_ORGANIZER).apply(env);
        for (int i = 0; i < 16; ++i) {
            linker.makeAccount(true, Role.ROLE_COMPETITOR).apply(env);
        }
        linker.makeTournament(
                Format.SINGLE_ELIMINATION,
                linker.getCompetitors().stream().toArray(CompetitorRoleEntity[]::new)
        ).apply(env);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldCreateFirstRoundForEliminationFormat(){
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
}