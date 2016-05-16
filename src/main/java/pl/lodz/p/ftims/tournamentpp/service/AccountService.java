package pl.lodz.p.ftims.tournamentpp.service;

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
import pl.lodz.p.ftims.tournamentpp.repository.AccountRepository;

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
        checkUsernameUnique(account);
        AccountEntity accountEntity = new AccountEntity();
        account.applyToEntity(accountEntity, passwordEncoder);
        accountEntity.setActive(true);
        accountRepository.save(accountEntity);
        log.info("Account {} registered", accountEntity.getUsername());
    }

    private void checkUsernameUnique(AccountDto account) {
        if (accountRepository.findByUsername(account.getUsername()).isPresent()) {
            throw new IllegalArgumentException(
                    "User " + account.getUsername() + " already exists"
            );
        }

    }

    public Optional<AccountEntity> showProfile() {
        Optional<AccountEntity> accountEntity = accountRepository.findByUsername(
                                                checkLoggedUser());
        return accountEntity;
    }

    private String checkLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String name = authentication.getName();
        return name;
    }

    public void updateAccount(ProfileDto account) {
         Long id = accountRepository.findByUsername(checkLoggedUser()).get().getId();
         AccountEntity accountEntity = accountRepository.findOne(id);
         if (account.getPassword().isEmpty()) {
             account.applyToEntityWithoutPassword(accountEntity);
         } else {
             account.applyToEntity(accountEntity, passwordEncoder);
         }
         log.info("Account {} updated", checkLoggedUser());
    }

}
