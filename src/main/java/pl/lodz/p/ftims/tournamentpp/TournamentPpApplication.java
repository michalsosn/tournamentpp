package pl.lodz.p.ftims.tournamentpp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@SpringBootApplication
public class TournamentPpApplication {

    public static void main(String[] args) {
        SpringApplication.run(TournamentPpApplication.class, args);
    }

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

   /* @Bean
    public CommandLineRunner demo(AccountRepository accountRepository,
                                  TournamentRepository tournamentRepository,
                                  GameRepository gameRepository,
                                  RoundRepository roundRepository,
                                  OrganizerRoleRepository organizerRoleRepository,
                                  CompetitorRoleRepository competitorRoleRepository) {
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

        RoundEntity r1 = new RoundEntity();
        r1.setTournament(tournament);
        r1.setStartTime(LocalDateTime.now());
        r1.setEndTime(LocalDateTime.now().plusHours(1));
        RoundEntity r2 = new RoundEntity();
        r2.setTournament(tournament);
        r2.setStartTime(LocalDateTime.now().plusHours(3));
        r2.setEndTime(LocalDateTime.now().plusHours(4));

        CompetitorRoleEntity c1 = new CompetitorRoleEntity(true, a1);
        CompetitorRoleEntity c2 = new CompetitorRoleEntity(true, a2);
        CompetitorRoleEntity c3 = new CompetitorRoleEntity(true, a3);
        CompetitorRoleEntity c4 = new CompetitorRoleEntity(true, a4);


        GameEntity g1 = new GameEntity();
        g1.setRound(r1);
        g1.setWinner(c1);
        g1.getCompetitors().add(c1);
        g1.getCompetitors().add(c2);

        GameEntity g2 = new GameEntity();
        g2.setRound(r1);
        g2.setWinner(c3);
        g2.getCompetitors().add(c3);
        g2.getCompetitors().add(c4);

        GameEntity g3 = new GameEntity();
        g3.setRound(r2);
        g3.setWinner(c1);
        g3.getCompetitors().add(c1);
        g3.getCompetitors().add(c3);


        return (args) -> {
            accountRepository.deleteAll();
            tournamentRepository.deleteAll();
            roundRepository.deleteAll();
            gameRepository.deleteAll();
            organizerRoleRepository.deleteAll();
            competitorRoleRepository.deleteAll();

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

            roundRepository.save(r1);
            roundRepository.save(r2);

            gameRepository.save(g1);
            gameRepository.save(g2);
            gameRepository.save(g3);
            r1.getGames().add(g1);
            r1.getGames().add(g2);
            r2.getGames().add(g3);

            roundRepository.save(r1);
            roundRepository.save(r2);

            tournament.getRounds().add(r1);
            tournament.getRounds().add(r2);
            tournamentRepository.save(tournament);
        };
    }*/
}
