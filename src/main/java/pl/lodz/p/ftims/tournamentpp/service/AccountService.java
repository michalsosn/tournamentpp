package pl.lodz.p.ftims.tournamentpp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

}
