package pl.lodz.p.ftims.tournamentpp.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michał Sośnicki
 */
@Entity(name = "Competitor")
@DiscriminatorValue(Role.COMPETITOR)
public class CompetitorRoleEntity extends RoleEntity {

    @ManyToMany(mappedBy = "competitors")
    private List<TournamentEntity> tournaments = new ArrayList<>();

    @OneToMany(mappedBy = "competitor", orphanRemoval = true)
    private List<ApplicationEntity> applications = new ArrayList<>();

    public CompetitorRoleEntity() {
    }

    public CompetitorRoleEntity(boolean active, AccountEntity account) {
        super(active, account);
    }

    public List<TournamentEntity> getTournaments() {
        return tournaments;
    }

    public List<ApplicationEntity> getApplications() {
        return applications;
    }
}
