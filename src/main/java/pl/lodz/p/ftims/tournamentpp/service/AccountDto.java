package pl.lodz.p.ftims.tournamentpp.service;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;
import pl.lodz.p.ftims.tournamentpp.entities.Role;
import pl.lodz.p.ftims.tournamentpp.entities.RoleEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michał Sośnicki
 */
public class AccountDto {

    @NotNull
    @Size(min = 1)
    private String username;

    @NotNull
    @Size(min = 4)
    private String password;

    private String name;

    private String email;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthdate;

    private String phone;

    private String description;

    private List<Role> roles = new ArrayList<>();

    public AccountDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void applyToEntity(AccountEntity accountEntity,
                              PasswordEncoder passwordEncoder) {
        accountEntity.setUsername(username);
        accountEntity.setPassword(passwordEncoder.encode(password));
        accountEntity.setName(name);
        accountEntity.setEmail(email);
        accountEntity.setBirthdate(birthdate);
        accountEntity.setPhone(phone);
        accountEntity.setDescription(description);
        for (Role role : Role.values()) {
            boolean active = roles.contains(role);
            RoleEntity roleEntity = role.getConstructor().apply(active, accountEntity);
            accountEntity.getRoles().put(role, roleEntity);
        }
    }

}
