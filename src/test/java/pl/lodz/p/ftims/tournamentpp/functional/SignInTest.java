package pl.lodz.p.ftims.tournamentpp.functional;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Page;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.lodz.p.ftims.tournamentpp.entities.Role;
import pl.lodz.p.ftims.tournamentpp.generator.Environment;
import pl.lodz.p.ftims.tournamentpp.service.dto.AccountDto;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static pl.lodz.p.ftims.tournamentpp.generator.AccountGenerator.makeAccountDto;

public class SignInTest extends BaseFunctionalTest {

    @Page
    private NavBar navBar;

    @Page
    private RegisterPage registerPage;

    @Page
    private SignInPage signInPage;

    @FindBy(css = "#error-panel header")
    private WebElement errorHeader;

    @FindBy(css = "#signin-panel header")
    private WebElement signInHeader;

    @FindBy(css = "#createTournament-panel header")
    private WebElement createTournamentHeader;

    private Environment env;

    @Before
    public void setUp() throws Exception {
        env = Environment.makeDefault();
    }

    @Test
    public void shouldGetErrorWhenUnauthorized() {
        browser.get(deploymentUrl + "organizer/createTournament");

        assertThat(signInHeader.getText()).contains("Sign in");
    }

    @Test
    public void shouldGetPageWhenAuthorized() {
        final AccountDto accountDto = makeAccountDto(Role.ROLE_ORGANIZER).apply(env);
        accountDto.setBirthdate(null);

        Graphene.goTo(RegisterPage.class);
        registerPage.register(accountDto);

        Graphene.goTo(SignInPage.class);
        signInPage.signIn(accountDto);

        browser.get(deploymentUrl + "organizer/createTournament");

        assertThat(createTournamentHeader.getText()).contains("Create tournament");

        navBar.signOut();
    }

    @Test
    public void shouldNotGetPageAfterLogout() {
        final AccountDto accountDto = makeAccountDto(Role.ROLE_ORGANIZER).apply(env);
        accountDto.setBirthdate(null);

        Graphene.goTo(RegisterPage.class);
        registerPage.register(accountDto);

        Graphene.goTo(SignInPage.class);
        signInPage.signIn(accountDto);

        navBar.signOut();

        browser.get(deploymentUrl + "organizer/createTournament");

        assertThat(signInHeader.getText()).contains("Sign in");
    }

    @Test
    public void shouldLoginWithRegistrationCredentials() {
        final AccountDto accountDto = makeAccountDto(Role.ROLE_ORGANIZER).apply(env);
        accountDto.setBirthdate(null);

        Graphene.goTo(RegisterPage.class);
        registerPage.register(accountDto);

        Graphene.goTo(SignInPage.class);
        signInPage.signIn(accountDto);

        assertThat(navBar.currentUsername()).contains(accountDto.getUsername());

        navBar.signOut();
    }

    @Test
    public void shouldLoginAfterLogout() {
        final AccountDto accountDto = makeAccountDto(Role.ROLE_ORGANIZER).apply(env);
        accountDto.setBirthdate(null);

        Graphene.goTo(RegisterPage.class);
        registerPage.register(accountDto);

        Graphene.goTo(SignInPage.class);
        signInPage.signIn(accountDto);

        navBar.signOut();

        Graphene.goTo(SignInPage.class);
        signInPage.signIn(accountDto);

        assertThat(navBar.currentUsername()).contains(accountDto.getUsername());

        navBar.signOut();
    }
}
