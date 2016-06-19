package pl.lodz.p.ftims.tournamentpp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import pl.lodz.p.ftims.tournamentpp.entities.*;
import pl.lodz.p.ftims.tournamentpp.repository.*;
import pl.lodz.p.ftims.tournamentpp.trees.Format;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

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

//    @Bean
    public CommandLineRunner demo(AccountRepository accountRepository,
                                  TournamentRepository tournamentRepository,
                                  GameRepository gameRepository,
                                  RoundRepository roundRepository) {
        PasswordEncoder pe = new BCryptPasswordEncoder();

        RoleEntity competitor;

        Collection<AccountEntity> competitorAccounts = new ArrayList<>();

        AccountEntity accountEntity;
        for (int i = 0; i < 8; i++) {
            accountEntity = new AccountEntity("FAJNY" + (i + 1), pe.encode("AAA"), true);
            accountEntity.setName("Zawodnik " + (i + 1));
            competitor = new CompetitorRoleEntity(true, accountEntity);
            accountEntity.getRoles().put(Role.ROLE_COMPETITOR, competitor);
            competitorAccounts.add(accountEntity);
        }

        AccountEntity organizerAccount = new AccountEntity("Mistrzus",
                pe.encode("DDD"), true);
        organizerAccount.setName("Organizator mega fajny");
        RoleEntity organizer = new OrganizerRoleEntity(true, organizerAccount);
        organizerAccount.getRoles().put(Role.ROLE_ORGANIZER, organizer);

        LocalDateTime start = LocalDateTime.now();
        TournamentEntity tournament = new TournamentEntity();
        tournament.setName("Mortal Kombat");
        tournament.setStartTime(start);
        tournament.setOrganizer((OrganizerRoleEntity)
                organizerAccount.getRoles().get(Role.ROLE_ORGANIZER));
        tournament.setFormat(Format.SINGLE_ELIMINATION);
        tournament.setLocation("Twoja matka");
        tournament.setDescription("Zabawy");

        TournamentEntity tournament2 = new TournamentEntity();
        tournament2.setName("Jedzenie kiślu");
        tournament2.setStartTime(start);
        tournament2.setOrganizer((OrganizerRoleEntity)
                organizerAccount.getRoles().get(Role.ROLE_ORGANIZER));
        tournament2.setFormat(Format.DOUBLE_ELIMINATION);
        tournament2.setLocation("Twoja matka");
        tournament2.setDescription("Zabawy");

        TournamentEntity tournament3 = new TournamentEntity();
        tournament3.setName("Polowanie na Robina");
        tournament3.setStartTime(start);
        tournament3.setOrganizer((OrganizerRoleEntity)
                organizerAccount.getRoles().get(Role.ROLE_ORGANIZER));
        tournament3.setFormat(Format.ROUND_ROBIN);
        tournament3.setLocation("Twoja matka");
        tournament3.setDescription("Zabawy");


        return (args) -> {
            accountRepository.deleteAll();
            tournamentRepository.deleteAll();
            roundRepository.deleteAll();
            gameRepository.deleteAll();

            competitorAccounts.forEach(accountRepository::save);

            accountRepository.save(organizerAccount);

//
            tournamentRepository.save(tournament);
//
            for (AccountEntity account: competitorAccounts) {
                tournament.getCompetitors()
                        .add((CompetitorRoleEntity)
                                account.getRoles().get(Role.ROLE_COMPETITOR));
            }

            tournamentRepository.save(tournament);

            tournamentRepository.save(tournament2);
//
            for (AccountEntity account: competitorAccounts) {
                tournament2.getCompetitors()
                        .add((CompetitorRoleEntity)
                                account.getRoles().get(Role.ROLE_COMPETITOR));
            }

            tournamentRepository.save(tournament2);

            tournamentRepository.save(tournament3);
//
            for (AccountEntity account: competitorAccounts) {
                tournament3.getCompetitors()
                        .add((CompetitorRoleEntity)
                                account.getRoles().get(Role.ROLE_COMPETITOR));
            }

            tournamentRepository.save(tournament3);

//            roundRepository.save(r1);
//            roundRepository.save(r2);
//
//            gameRepository.save(g1);
//            gameRepository.save(g2);
//            gameRepository.save(g3);
//            r1.getGames().add(g1);
//            r1.getGames().add(g2);
//            r2.getGames().add(g3);
//
//            roundRepository.save(r1);
//            roundRepository.save(r2);

//            tournament.getRounds().add(r1);
//            tournament.getRounds().add(r2);
//            tournamentRepository.save(tournament);
        };
    }
}
