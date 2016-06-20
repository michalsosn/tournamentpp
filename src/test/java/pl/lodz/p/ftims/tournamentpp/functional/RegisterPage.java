package pl.lodz.p.ftims.tournamentpp.functional;

import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.lodz.p.ftims.tournamentpp.entities.Role;
import pl.lodz.p.ftims.tournamentpp.service.dto.AccountDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;

@Location("register")
public class RegisterPage {

    @FindBy(id = "register-form")
    private WebElement registerForm;

    @FindBy(css = "#register-form #username")
    private WebElement usernameInput;

    @FindBy(css = "#register-form #password")
    private WebElement passwordInput;

    @FindBy(css = "#register-form #email")
    private WebElement emailInput;

    @FindBy(css = "#register-form #name")
    private WebElement nameInput;

    @FindBy(css = "#register-form #birthdate")
    private WebElement birthdateInput;

    @FindBy(css = "#register-form #phone")
    private WebElement phoneInput;

    @FindBy(css = "#register-form #description")
    private WebElement descriptionInput;

    @FindBy(css = "#register-form button[type = 'submit']")
    private WebElement submitButton;

    public void register(
            String username, String password, String email, String name,
            LocalDate birthdate, String phone, String description, Role... roles) {
        send(username, usernameInput::sendKeys);
        send(password, passwordInput::sendKeys);
        send(email, emailInput::sendKeys);
        send(name, nameInput::sendKeys);
        send(birthdate, item ->
                birthdateInput.sendKeys(item.format(DateTimeFormatter.ISO_DATE)));
        send(phone, phoneInput::sendKeys);
        send(description, descriptionInput::sendKeys);
        for (Role role : roles) {
            registerForm.findElement(By.xpath(
                    "//input[@type = 'checkbox'][@value = '" + role.name() + "']"
            )).click();
        }
        guardHttp(submitButton).click();
    }

    public void register(AccountDto accountDto) {
        register(
                accountDto.getUsername(), accountDto.getPassword(), accountDto.getEmail(),
                accountDto.getName(), accountDto.getBirthdate(), accountDto.getPhone(),
                accountDto.getDescription(), accountDto.getRoles().toArray(new Role[0])
        );
    }

    private <T> void send(T item, Consumer<T> consumer) {
        if (item != null) {
            consumer.accept(item);
        }
    }
}
