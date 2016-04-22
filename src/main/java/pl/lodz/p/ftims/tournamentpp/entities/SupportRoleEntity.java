package pl.lodz.p.ftims.tournamentpp.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Michał Sośnicki
 */
@Entity(name = "Support")
@DiscriminatorValue(Role.SUPPORT)
public class SupportRoleEntity extends RoleEntity {

    public SupportRoleEntity() {
    }

    public SupportRoleEntity(boolean active, AccountEntity account) {
        super(active, account);
    }

}
