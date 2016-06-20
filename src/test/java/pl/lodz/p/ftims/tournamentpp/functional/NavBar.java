package pl.lodz.p.ftims.tournamentpp.functional;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;

@Location("account/account")
public class NavBar {

    @FindBy(id = "authentication-username")
    private WebElement authenticationUsername;

    @FindBy(id = "signout-link")
    private WebElement signOutLink;

    public String currentUsername() {
        return authenticationUsername.getText();
    }

    public void signOut() {
        guardHttp(signOutLink).click();
    }
}
