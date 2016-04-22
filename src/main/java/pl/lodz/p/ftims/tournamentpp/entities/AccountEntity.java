package pl.lodz.p.ftims.tournamentpp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michał Sośnicki
 */
@Entity(name = "Account")
@Table(name = "account")
@SequenceGenerator(name = "account_sequence", sequenceName = "account_sequence",
                   allocationSize = 1)
public class AccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence")
    @Column(name = "account_id", nullable = false, updatable = false)
    private long id;

    @Version
    @Column(name = "version")
    private Long version;

    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotNull
    @Size(min = 59, max = 60)
    @Column(name = "password", columnDefinition = "char(60) not null")
    private String password;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "phone")
    private String phone;

    @Column(name = "description")
    private String description;

    @MapKeyEnumerated(EnumType.STRING)
    @MapKey(name = "type")
    @OneToMany(mappedBy = "account", orphanRemoval = true, cascade = {CascadeType.ALL})
    private Map<Role, RoleEntity> roles = new HashMap<>();

    public AccountEntity() {
    }

    public AccountEntity(String username, String password, boolean active) {
        this.username = username;
        this.password = password;
        this.active = active;
    }

    public long getId() {
        return id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public Map<Role, RoleEntity> getRoles() {
        return roles;
    }
}
