package pl.lodz.p.ftims.tournamentpp.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.lodz.p.ftims.tournamentpp.TournamentPpApplication;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;
import pl.lodz.p.ftims.tournamentpp.entities.Role;
import pl.lodz.p.ftims.tournamentpp.entities.TournamentEntity;
import pl.lodz.p.ftims.tournamentpp.generator.Environment;
import pl.lodz.p.ftims.tournamentpp.generator.GeneratorLinker;
import pl.lodz.p.ftims.tournamentpp.rules.EnvironmentRule;
import pl.lodz.p.ftims.tournamentpp.rules.RepeatRule;
import pl.lodz.p.ftims.tournamentpp.trees.Format;

import java.util.List;

/**
 * @author Michał Sośnicki
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TournamentPpApplication.class)
@WebAppConfiguration
@Commit
public class GameServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Rule
    public RepeatRule repeatRule = new RepeatRule();

    @Rule
    public EnvironmentRule environmentRule = new EnvironmentRule();

    @Autowired
    private GeneratorLinker linker;

    private Environment env;

    @Before
    public void setUp() throws Exception {
        env = environmentRule.getEnvironment();

        linker.makeAccount(true, Role.ROLE_ORGANIZER).apply(env);
        for (int i = 0; i < 16; ++i) {
            linker.makeAccount(true, Role.ROLE_COMPETITOR).apply(env);
        }
       final TournamentEntity tournament =  linker.makeTournament(
                Format.SINGLE_ELIMINATION,
                linker.getCompetitors().stream().toArray(CompetitorRoleEntity[]::new)
        ).apply(env);

        final List<CompetitorRoleEntity> competitors = tournament.getCompetitors();
        linker.makeRound().apply(env);

        GameEntity game11 = linker.makeGame().apply(env);
        game11.getCompetitors().add(competitors.get(0));
        game11.getCompetitors().add(competitors.get(1));
        GameEntity game12 = linker.makeGame().apply(env);
        game12.getCompetitors().add(competitors.get(2));
        game12.getCompetitors().add(competitors.get(3));

        GameEntity game13 = linker.makeGame().apply(env);
        game13.getCompetitors().add(competitors.get(4));
        game13.getCompetitors().add(competitors.get(5));
        GameEntity game14 = linker.makeGame().apply(env);
        game14.getCompetitors().add(competitors.get(6));
        game14.getCompetitors().add(competitors.get(7));
    }

    @Test
    public void shouldSaveResults() throws Exception {

    }

}