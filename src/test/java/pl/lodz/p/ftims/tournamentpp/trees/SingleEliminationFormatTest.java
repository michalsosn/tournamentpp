package pl.lodz.p.ftims.tournamentpp.trees;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.lodz.p.ftims.tournamentpp.entities.*;
import pl.lodz.p.ftims.tournamentpp.generator.Generator;
import pl.lodz.p.ftims.tournamentpp.generator.GeneratorLinker;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

/**
 * Created by Daniel on 2016-05-15.
 */
public class SingleEliminationFormatTest {

    private Generator.Environment env;
    private GeneratorLinker linker;
    private TournamentFormat format = new SingleEliminationFormat();

    @Before
    public void setUp() throws Exception {
        env = new Generator.Environment(
                new Random(),
                Clock.fixed(Instant.now(), ZoneId.systemDefault()),
                new BCryptPasswordEncoder()
        );
        linker = new GeneratorLinker();

        linker.makeAccount(true, Role.ROLE_ORGANIZER).apply(env);
        for (int i = 0; i < 6; ++i) {
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
    public void shouldCreateFirstRoundForSingleElimination(){
        final TournamentEntity tournament = linker.getTournaments().get(0);

        final RoundEntity round = format.prepareRound(tournament, new Random());

        final List<CompetitorRoleEntity> competitors = round.getGames().stream()
                .flatMap(game -> game.getCompetitors().stream())
                .collect(Collectors.toList());
        assertThat("All competitors are in the first round",
                competitors, containsInAnyOrder(tournament.getCompetitors().toArray())
        );
    }

    @Test
    public void shouldCreateNextRoundForSingleElimination(){
        final TournamentEntity tournament = linker.getTournaments().get(0);
        final List<CompetitorRoleEntity> competitors = tournament.getCompetitors();

        linker.makeRound().apply(env);
        GameEntity game11 = linker.makeGame().apply(env);
        game11.setWinner(competitors.get(0));
        GameEntity game12 = linker.makeGame().apply(env);
        game12.setWinner(competitors.get(2));

        // TODO To chujowe, że korzystamy z zewnętrznego formatu zamiast z TournamentEntity
        RoundEntity round2 = format.prepareRound(tournament, new Random());

        List<CompetitorRoleEntity> newCompetitors = round2.getGames().stream()
                .flatMap(game -> game.getCompetitors().stream())
                .collect(Collectors.toList());

        List<CompetitorRoleEntity> lastWinners = tournament.getRounds().stream()
                .flatMap(round -> round.getGames().stream())
                .map(GameEntity::getWinner)
                .collect(Collectors.toList());

        assertThat("Competitors in new round are paired from top to down",
                newCompetitors, contains(lastWinners.toArray())
        );
    }

}