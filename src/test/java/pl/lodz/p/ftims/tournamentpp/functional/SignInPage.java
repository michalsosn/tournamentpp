package pl.lodz.p.ftims.tournamentpp.functional;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.lodz.p.ftims.tournamentpp.service.dto.AccountDto;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;

@Location("signin")
public class SignInPage {

    @FindBy(css = "#signin-form #username")
    private WebElement usernameInput;

    @FindBy(css = "#signin-form #password")
    private WebElement passwordInput;

    @FindBy(css = "#signin-form button[type = 'submit']")
    private WebElement submitButton;

    public void signIn(String username, String password) {
        if (username != null) {
            usernameInput.sendKeys(username);
        }
        if (password != null){
            passwordInput.sendKeys(password);
        }
        guardHttp(submitButton).click();
    }

    public void signIn(AccountDto accountDto) {
        signIn(accountDto.getUsername(), accountDto.getPassword());
    }
}
