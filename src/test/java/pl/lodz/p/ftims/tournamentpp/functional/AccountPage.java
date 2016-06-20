package pl.lodz.p.ftims.tournamentpp.functional;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.lodz.p.ftims.tournamentpp.service.dto.AccountDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

@Location("account/account")
public class AccountPage {

    @FindBy(css = "#account-panel #username")
    private WebElement usernameInput;

    @FindBy(css = "#account-panel #name")
    private WebElement nameInput;

    @FindBy(css = "#account-panel #birthdate")
    private WebElement birthdateInput;

    @FindBy(css = "#account-panel #email")
    private WebElement emailInput;

    @FindBy(css = "#account-panel #phone")
    private WebElement phoneInput;

    @FindBy(css = "#account-panel #description")
    private WebElement descriptionInput;

    public AccountDto readDto() {
        final AccountDto accountDto = new AccountDto();
        set(usernameInput.getText(), accountDto::setUsername);
        set(nameInput.getText(), accountDto::setName);
        set(birthdateInput.getText(), item ->
                accountDto.setBirthdate(LocalDate.parse(item, DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
        set(emailInput.getText(), accountDto::setEmail);
        set(phoneInput.getText(), accountDto::setPhone);
        set(descriptionInput.getText(), accountDto::setDescription);
        return accountDto;
    }

    private void set(String item, Consumer<String> setter) {
        if (item != null && !item.isEmpty()) {
            setter.accept(item);
        }
    }
}
