package pl.lodz.p.ftims.tournamentpp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.Role;
import pl.lodz.p.ftims.tournamentpp.repository.AccountRepository;
import pl.lodz.p.ftims.tournamentpp.repository.TournamentRepository;
import pl.lodz.p.ftims.tournamentpp.service.dto.AccountDto;
import pl.lodz.p.ftims.tournamentpp.service.dto.ProfileDto;

/**
 * @author Michał Sośnicki
 */
@Transactional
@Service
public class AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountService.class);

    private static final int PAGE_SIZE = 10;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;
    // nie wiem czy to powinno tuy byc
    @Autowired
    private TournamentRepository tournamentRepository;

    public AccountEntity findAccount() {
        return findLoggedUser().get();
    }

    public AccountEntity findAccountByUsername(String username) {
        return accountRepository.findByUsername(username).get();
    }

    public void createAccount(AccountDto account) {
        checkPassword(account.getPassword());
        checkUsernameUnique(account);
        AccountEntity accountEntity = new AccountEntity();
        account.applyToEntity(accountEntity, passwordEncoder);
        accountEntity.setActive(true);
        accountRepository.save(accountEntity);
        log.info("Account {} registered", accountEntity.getUsername());
    }

    public void updateAccount(ProfileDto accountDto) {
        checkPassword(accountDto.getPassword());
        AccountEntity accountEntity = findLoggedUser().get();
        accountDto.applyToEntity(accountEntity, passwordEncoder);
        log.info("Account {} updated", accountEntity.getUsername());
    }

    private Optional<AccountEntity> findLoggedUser() {
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        String username = authentication.getName();
        return accountRepository.findByUsername(username);
    }

    private void checkPassword(String password) {
        if (password != null && password.length() < 4) {
            throw new IllegalArgumentException(
                    "User password shorter than 4"
            );
        }
    }

    private void checkUsernameUnique(AccountDto account) {
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new IllegalArgumentException(
                    "User " + account.getUsername() + " already exists"
            );
        }
    }

    @Transactional(readOnly = true)
    public List<AccountEntity> listPlayers(long tournamentId) {

        // wszystkie
        Iterable<AccountEntity> accounts = accountRepository.findAll();

        // wszyscy Competitors
        List<AccountEntity> competitors = new ArrayList<AccountEntity>();
        for (AccountEntity accountEntity : accounts) {
            if (accountEntity.getRoles().get(Role.ROLE_COMPETITOR) != null
                    && accountEntity.getRoles().get(Role.ROLE_COMPETITOR).isActive()) {
                competitors.add(accountEntity);
            }
        }

        // tylko z danego tournamentu
        List<CompetitorRoleEntity> competitorRole =
                tournamentRepository.findOne(tournamentId).getCompetitors();

        List<AccountEntity> players = new ArrayList<AccountEntity>();
        for (CompetitorRoleEntity cr : competitorRole) {
            players.add(cr.getAccount());
        }

        // Competitors nie w tournamentcie (pewno slaby algotym)
        //List<AccountEntity> possibleCompetitors = new ArrayList<AccountEntity>();
        for (AccountEntity player : players) {
            for (int i = 0; i < competitors.size(); i++) {
                if (player == competitors.get(i)) {
                    competitors.remove(i);
                }
            }
        }

        return competitors;
    }

}
