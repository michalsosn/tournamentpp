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

    public void createAccount(AccountDto account) {
        checkDto(account);
        AccountEntity accountEntity = new AccountEntity();
        account.applyToEntity(accountEntity, passwordEncoder);
        accountEntity.setActive(true);
        accountRepository.save(accountEntity);
        log.info("Account {} registered", accountEntity.getUsername());
    }

    private void checkDto(AccountDto account) {
        if (account.getPassword().length() < 4) {
            throw new IllegalArgumentException(
                    "User password shorter than 4"
            );
        }
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new IllegalArgumentException(
                    "User " + account.getUsername() + " already exists"
            );
        }
    }

    public AccountEntity findAccount() {
        return findLoggedUser().get();
    }

    public void updateAccount(ProfileDto accountDto) {
         AccountEntity accountEntity = findLoggedUser().get();
         accountDto.applyToEntity(accountEntity, passwordEncoder);
         log.info("Account {} updated", accountEntity.getUsername());
    }

    private Optional<AccountEntity> findLoggedUser() {
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return accountRepository.findByUsername(username);
    }

}
