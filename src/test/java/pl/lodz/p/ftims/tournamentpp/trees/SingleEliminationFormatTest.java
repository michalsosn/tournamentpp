package pl.lodz.p.ftims.tournamentpp.trees;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.TournamentPpApplication;
import pl.lodz.p.ftims.tournamentpp.entities.*;
import pl.lodz.p.ftims.tournamentpp.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Daniel on 2016-05-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TournamentPpApplication.class)
@WebAppConfiguration
public class SingleEliminationFormatTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TournamentRepository tournamentRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    RoundRepository roundRepository;
    @Autowired
    OrganizerRoleRepository organizerRoleRepository;
    @Autowired
    CompetitorRoleRepository competitorRoleRepository;

    private TournamentFormat format = new SingleEliminationFormat();

    @Before
    public void setUp() throws Exception {
        PasswordEncoder pe = new BCryptPasswordEncoder();
        AccountEntity a1 = new AccountEntity(
                "Majkelus Sośnickus",
                pe.encode("AAA"), true);
        a1.setName("Majkelus Sośnickus");
        AccountEntity a2 = new AccountEntity("Danielus Pęczkus",
                pe.encode("BBB"), true);
        a2.setName("Danielus Pęczkus");
        AccountEntity a3 = new AccountEntity("Kamilus Lorenckus",
                pe.encode("CCC"), true);
        a3.setName("Kamilus Lorenckus");
        AccountEntity a4 = new AccountEntity("Sebastianus Grafus",
                pe.encode("DDD"), true);
        a4.setName("Sebastianus Grafus");
        AccountEntity a5 = new AccountEntity("Mistrzus",
                pe.encode("DDD"), true);

        OrganizerRoleEntity org = new OrganizerRoleEntity(true, a5);

        LocalDateTime start = LocalDateTime.now();

        TournamentEntity tournament = new TournamentEntity(
                "Planet X",
                "Mortal Kombat Tournament",
                start, org);

        CompetitorRoleEntity c1 = new CompetitorRoleEntity(true, a1);
        CompetitorRoleEntity c2 = new CompetitorRoleEntity(true, a2);
        CompetitorRoleEntity c3 = new CompetitorRoleEntity(true, a3);
        CompetitorRoleEntity c4 = new CompetitorRoleEntity(true, a4);

        accountRepository.save(a1);
        accountRepository.save(a2);
        accountRepository.save(a3);
        accountRepository.save(a4);
        accountRepository.save(a5);

        organizerRoleRepository.save(org);

        tournamentRepository.save(tournament);

        competitorRoleRepository.save(c1);
        competitorRoleRepository.save(c2);
        competitorRoleRepository.save(c3);
        competitorRoleRepository.save(c4);

        tournament.getCompetitors().add(c1);
        tournament.getCompetitors().add(c2);
        tournament.getCompetitors().add(c3);
        tournament.getCompetitors().add(c4);

        tournamentRepository.save(tournament);
    }

    @After
    public void tearDown() throws Exception {
        gameRepository.deleteAll();
        roundRepository.deleteAll();
        tournamentRepository.deleteAll();
        competitorRoleRepository.deleteAll();
        organizerRoleRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void shouldBeOK(){
        assertTrue(true);
    }

    @Test
    public void shouldCreateFirstRoundForSingleElimination(){
        TournamentEntity tournament = getFirst();
        System.out.println(tournament.getId());
        List<CompetitorRoleEntity> tournamentCompetitors = tournament.getCompetitors();
        RoundEntity round = format.prepareRound(tournament);

        for(GameEntity game : round.getGames()){
            for(CompetitorRoleEntity competitor : game.getCompetitors()){
                boolean found = false;
                for(CompetitorRoleEntity tournamentCompetitor : tournamentCompetitors){
                    if(tournamentCompetitor.getId() == competitor.getId()){
                        found = true;
                    }
                }
                if(found == false){
                    fail("All competitors should be in first round");
                }
            }
        }
        assertTrue(true);
    }

    @Test
    public void shouldCreateNextRoundForSingleElimination(){
        TournamentEntity tournament = getFirst();
        System.out.println(tournament.getId());
        List<CompetitorRoleEntity> competitors = tournament.getCompetitors();
        RoundEntity r1 = new RoundEntity();
        r1.setTournament(tournament);
        r1.setStartTime(LocalDateTime.now());
        r1.setEndTime(LocalDateTime.now().plusHours(1));
        GameEntity g1 = new GameEntity();
        g1.setRound(r1);
        g1.setWinner(competitors.get(0));
        GameEntity g2 = new GameEntity();
        g2.setRound(r1);
        g2.setWinner(competitors.get(2));
        roundRepository.save(r1);
        gameRepository.save(g1);
        gameRepository.save(g2);
        r1.getGames().add(g1);
        r1.getGames().add(g2);
        roundRepository.save(r1);
        tournament.getRounds().add(r1);
        tournamentRepository.save(tournament);

        TournamentEntity tournament2 = getFirst();

        if (tournament.getId() != tournament2.getId()) {
            fail("Wrong getted tournaments");
        }

        if(tournament2.getRounds().size() < 1){
            fail("Wrong number of rounds");
        }

        List<CompetitorRoleEntity> lastWinners = new ArrayList<>();
        List<RoundEntity> rounds = tournament2.getRounds();
        for (GameEntity game : rounds.get(rounds.size() - 1).getGames()) {
            lastWinners.add(game.getWinner());
        }

        RoundEntity round = format.prepareRound(tournament);
        List<CompetitorRoleEntity> competitorsInActualRound = new ArrayList<>();
        for (GameEntity game : round.getGames()) {
            for (CompetitorRoleEntity competitor : game.getCompetitors()) {
                competitorsInActualRound.add(competitor);
            }
        }
        for (int i = 0; i < lastWinners.size(); i ++) {
            if (lastWinners.get(i).getId() != competitorsInActualRound.get(i).getId()) {
                fail("Competitors in new round should be paired from top to down");
            }
        }

        assertTrue(true);
    }

    private TournamentEntity getFirst(){
        Iterable<TournamentEntity> iter = tournamentRepository.findAll();
        return iter.iterator().next();
    }

}