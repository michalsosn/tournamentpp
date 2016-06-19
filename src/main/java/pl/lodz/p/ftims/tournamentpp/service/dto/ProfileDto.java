package pl.lodz.p.ftims.tournamentpp.service.dto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;

import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * @author Łukasz Kluch
 */
public class ProfileDto {

    @Pattern(regexp = "(^$|.{4,})", message = "Must be empty or more than 4")
    private String password;

    private String name;

    private String email;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;

    private String phone;

    private String description;

    public ProfileDto() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void applyToEntity(AccountEntity accountEntity,
               PasswordEncoder passwordEncoder) {
        if (password != null && !password.isEmpty()) {
            accountEntity.setPassword(passwordEncoder.encode(password));
        }
        accountEntity.setName(name);
        accountEntity.setEmail(email);
        accountEntity.setBirthdate(birthdate);
        accountEntity.setPhone(phone);
        accountEntity.setDescription(description);
    }
}
