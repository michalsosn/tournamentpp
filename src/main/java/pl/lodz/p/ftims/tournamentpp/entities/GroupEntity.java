package pl.lodz.p.ftims.tournamentpp.entities;

import javax.persistence.*;
import java.util.List;

/**
 *
 * @author r4pt0r
 */
@Entity(name = "Group")
@Table(name = "group")
@SequenceGenerator(name = "group_sequence", sequenceName = "group_sequence",
        allocationSize = 1)
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "group_sequence")
    @Column(name = "group_id", nullable = false, updatable = false)
    private long id;

    @Version
    @Column(name = "version")
    private long version;

    @ManyToMany
    @JoinTable(name = "group_competitor",
            joinColumns = @JoinColumn(
                    name = "group_id",
                    referencedColumnName = "group_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "competitor_id",
                    referencedColumnName = "role_id"
            )
    )
    private List<CompetitorRoleEntity> competitors;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private TournamentEntity tournament;

    public long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public List<CompetitorRoleEntity> getCompetitors() {
        return competitors;
    }

    public TournamentEntity getTournament() {
        return tournament;
    }
}
