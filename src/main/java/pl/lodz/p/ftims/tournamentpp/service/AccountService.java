package pl.lodz.p.ftims.tournamentpp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;
import pl.lodz.p.ftims.tournamentpp.repository.AccountRepository;
import pl.lodz.p.ftims.tournamentpp.service.dto.AccountDto;
import pl.lodz.p.ftims.tournamentpp.service.dto.ProfileDto;

import java.util.Optional;

/**
 * @author Michał Sośnicki
 */
@Transactional
@Service
public class AccountService {

    private final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;

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

}
