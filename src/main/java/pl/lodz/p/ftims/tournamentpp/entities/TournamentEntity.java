package pl.lodz.p.ftims.tournamentpp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michał Sośnicki
 */
@Entity(name = "Tournament")
@Table(name = "tournament")
@SequenceGenerator(name = "tournament_sequence", sequenceName = "tournament_sequence",
        allocationSize = 1)
public class TournamentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tournament_sequence")
    @Column(name = "tournament_id", nullable = false, updatable = false)
    private long id;

    @Version
    @Column(name = "version")
    private Long version;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "description", length = 2048, nullable = false)
    private String description;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "organizer_id", referencedColumnName = "role_id",
            nullable = false)
    private OrganizerRoleEntity organizer;

    @ManyToMany
    @JoinTable(name = "tournament_competitor",
            joinColumns = @JoinColumn(
                    name = "tournament_id",
                    referencedColumnName = "tournament_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "competitor_id",
                    referencedColumnName = "role_id"
            )
    )
    private List<CompetitorRoleEntity> competitors = new ArrayList<>();

    @OneToMany
    private List<RoundEntity> rounds = new ArrayList<>();

    public TournamentEntity() {
    }

    public TournamentEntity(String location, String description,
                            LocalDateTime startTime, OrganizerRoleEntity organizer) {
        this.location = location;
        this.description = description;
        this.startTime = startTime;
        this.organizer = organizer;
    }

    public long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public OrganizerRoleEntity getOrganizer() {
        return organizer;
    }

    public void setOrganizer(OrganizerRoleEntity organizer) {
        this.organizer = organizer;
    }

    public List<CompetitorRoleEntity> getCompetitors() {
        return competitors;
    }

    public List<RoundEntity> getRounds() {
        return rounds;
    }
}
