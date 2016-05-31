package pl.lodz.p.ftims.tournamentpp.trees;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.Role;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.generator.Environment;
import pl.lodz.p.ftims.tournamentpp.generator.GeneratorLinker;
import pl.lodz.p.ftims.tournamentpp.generator.MemoryLinker;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Daniel on 2016-05-28.
 */
public abstract class EliminationFormatTest {

    protected Environment env;
    protected GeneratorLinker linker;
    protected TournamentFormat format;

    public EliminationFormatTest(TournamentFormat format) {
        this.format = format;
    }

    @Before
    public void setUp() throws Exception {
        env = Environment.makeDefault();
        linker = new MemoryLinker();

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
