package pl.lodz.p.ftims.tournamentpp.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michał Sośnicki
 */
@Entity(name = "Organizer")
@DiscriminatorValue(Role.ORGANIZER)
public class OrganizerRoleEntity extends RoleEntity {

    @OneToMany(mappedBy = "organizer")
    private List<TournamentEntity> tournaments = new ArrayList<>();

    public OrganizerRoleEntity() {
    }

    public OrganizerRoleEntity(boolean active, AccountEntity account) {
        super(active, account);
    }

    public List<TournamentEntity> getTournaments() {
        return tournaments;
    }
}
