package pl.lodz.p.ftims.tournamentpp.functional;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Page;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.lodz.p.ftims.tournamentpp.entities.Role;
import pl.lodz.p.ftims.tournamentpp.generator.Environment;
import pl.lodz.p.ftims.tournamentpp.service.dto.AccountDto;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.lodz.p.ftims.tournamentpp.generator.AccountGenerator.makeAccountDto;

public class RegisterTest extends BaseFunctionalTest {

    @Page
    private NavBar navBar;

    @Page
    private RegisterPage registerPage;

    @Page
    private SignInPage signInPage;

    @Page
    private AccountPage accountPage;

    @FindBy(css = "#register-form #password + span")
    private WebElement registerPasswordError;

    @FindBy(css = "#error-panel")
    private WebElement errorPanel;

    private Environment env;

    @Before
    public void setUp() throws Exception {
        env = Environment.makeDefault();
    }

    @Test
    public void shouldRegisterSuccessfully() {
        final AccountDto accountDto = makeAccountDto(Role.ROLE_COMPETITOR).apply(env);
        accountDto.setBirthdate(null);

        Graphene.goTo(RegisterPage.class);
        registerPage.register(accountDto);

        Graphene.goTo(SignInPage.class);
        signInPage.signIn(accountDto);

        Graphene.goTo(AccountPage.class);
        final AccountDto retrievedDto = accountPage.readDto();

        assertThat(retrievedDto).isEqualToIgnoringGivenFields(
                accountDto, "password", "roles", "__cobertura_counters"
        );

        navBar.signOut();
    }

    @Test
    public void shouldNotRegisterWhenPasswordTooShort() {
        final AccountDto accountDto = makeAccountDto(Role.ROLE_COMPETITOR).apply(env);
        accountDto.setBirthdate(null);
        accountDto.setPassword(accountDto.getPassword().substring(0, 3));

        Graphene.goTo(RegisterPage.class);
        registerPage.register(accountDto);

        assertThat(registerPasswordError.getText()).contains("size must be between");
    }

    @Test
    public void shouldNotRegisterTwiceWithSameLogin() {
        final AccountDto accountDto = makeAccountDto(Role.ROLE_COMPETITOR).apply(env);
        accountDto.setBirthdate(null);

        Graphene.goTo(RegisterPage.class);
        registerPage.register(accountDto);
        registerPage.register(accountDto);

        assertThat(errorPanel.findElement(By.tagName("header")).getText())
                .contains("An error has occurred");
    }

}
