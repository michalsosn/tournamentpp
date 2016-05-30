package pl.lodz.p.ftims.tournamentpp.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.lodz.p.ftims.tournamentpp.TournamentPpApplication;
import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;
import pl.lodz.p.ftims.tournamentpp.entities.Role;
import pl.lodz.p.ftims.tournamentpp.generator.AccountGenerator;
import pl.lodz.p.ftims.tournamentpp.generator.Environment;
import pl.lodz.p.ftims.tournamentpp.repository.AccountRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.groups.Tuple.tuple;

/**
 * @author Michał Sośnicki
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TournamentPpApplication.class)
@WebAppConfiguration
@Commit
public class AccountServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    private Environment env;

    @Before
    public void setUp() throws Exception {
        env = Environment.makeDefault();
    }

    @Test
    public void shouldCreateNewAccount() throws Exception {
        // given
        final AccountDto accountDto = AccountGenerator.makeAccountDto().apply(env);

        // when
        accountService.createAccount(accountDto);

        TestTransaction.end();
        TestTransaction.start();

        // then
        final Optional<AccountEntity> account
                = accountRepository.findByUsername(accountDto.getUsername());
        assertThat(account).isPresent();
        assertThat(accountDto).isEqualToIgnoringGivenFields(
                account.get(), "password", "roles"
        );
    }

    @Test
    @Rollback
    public void shouldNotCreateAccountWhenPasswordTooShort() throws Exception {
        // given
        final AccountDto accountDto = AccountGenerator.makeAccountDto().apply(env);
        accountDto.setPassword("sht");

        // when
        final Throwable throwable = catchThrowable(() ->
            accountService.createAccount(accountDto)
        );

        // then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @Rollback
    public void shouldNotCreateAccountWhenLoginNotUnique() throws Exception {
        // given
        final AccountDto accountDto1 = AccountGenerator.makeAccountDto().apply(env);
        final AccountDto accountDto2 = AccountGenerator.makeAccountDto().apply(env);
        accountDto2.setUsername(accountDto1.getUsername());

        // when
        accountService.createAccount(accountDto1);
        final Throwable throwable = catchThrowable(() ->
                accountService.createAccount(accountDto1)
        );

        // then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldCreateAllRoles() throws Exception {
        // given
        final AccountDto accountDto
                = AccountGenerator.makeAccountDto(Role.ROLE_SUPPORT).apply(env);

        // when
        accountService.createAccount(accountDto);

        TestTransaction.end();
        TestTransaction.start();

        // then
        final AccountEntity account
                = accountRepository.findByUsername(accountDto.getUsername()).get();
        assertThat(account.getRoles()).containsOnlyKeys(Role.values());
    }

    @Test
    public void shouldActivateSelectedRoles() throws Exception {
        // given
        final Role[] activeRoles = { Role.ROLE_SUPPORT, Role.ROLE_COMPETITOR };
        final AccountDto accountDto
                = AccountGenerator.makeAccountDto(activeRoles).apply(env);

        // when
        accountService.createAccount(accountDto);

        TestTransaction.end();
        TestTransaction.start();

        // then
        final AccountEntity account
                = accountRepository.findByUsername(accountDto.getUsername()).get();
        assertThat(account.getRoles().values())
                .extracting("type", "active")
                .contains(tuple(Role.ROLE_COMPETITOR, true),
                          tuple(Role.ROLE_ORGANIZER, false),
                          tuple(Role.ROLE_SUPPORT, true));
    }

}