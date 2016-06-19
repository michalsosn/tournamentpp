package pl.lodz.p.ftims.tournamentpp.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michał Sośnicki
 */
@Entity(name = "Competitor")
@DiscriminatorValue(Role.COMPETITOR)
public class CompetitorRoleEntity extends RoleEntity {

    private static final long serialVersionUID = 1L;

    @ManyToMany(mappedBy = "competitors")
    private List<TournamentEntity> tournaments = new ArrayList<>();

    public CompetitorRoleEntity() {
    }

    public CompetitorRoleEntity(boolean active, AccountEntity account) {
        super(active, account);
    }

    public List<TournamentEntity> getTournaments() {
        return tournaments;
    }
}
