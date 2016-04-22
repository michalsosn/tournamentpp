package pl.lodz.p.ftims.tournamentpp.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Michał Sośnicki
 */
@Entity(name = "Role")
@Table(name = "role")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@SequenceGenerator(name = "role_sequence", sequenceName = "role_sequence",
                   allocationSize = 3)
public abstract class RoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence")
    @Column(name = "role_id", nullable = false, updatable = false)
    private long id;

    @Version
    @Column(name = "version")
    private Long version;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 32,
            nullable = false, insertable = false, updatable = false)
    private Role type;

    @Column(name = "active", nullable = false)
    private boolean active;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id",
                nullable = false, updatable = false)
    private AccountEntity account;

    public RoleEntity() {
    }

    public RoleEntity(boolean active, AccountEntity account) {
        this.active = active;
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public Role getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public AccountEntity getAccount() {
        return account;
    }

}
