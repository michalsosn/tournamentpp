package pl.lodz.p.ftims.tournamentpp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "Application")
@Table(name = "applications", indexes = {
        @Index(name = "application_index", unique = true,
               columnList = "tournament_id, competitor_id")
})
@SequenceGenerator(name = "application_sequence", sequenceName = "application_sequence",
                   allocationSize = 1)
public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "application_sequence")
    @Column(name = "application_id", nullable = false, updatable = false)
    private Long id;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "tournament_id", nullable = false, updatable = false)
    private TournamentEntity tournament;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "competitor_id", nullable = false, updatable = false)
    private CompetitorRoleEntity competitor;

    public ApplicationEntity() {
    }

    public ApplicationEntity(String message, TournamentEntity tournament,
                             CompetitorRoleEntity competitor) {
        this.message = message;
        this.tournament = tournament;
        this.competitor = competitor;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TournamentEntity getTournament() {
        return tournament;
    }

    public void setTournament(TournamentEntity tournament) {
        this.tournament = tournament;
    }

    public CompetitorRoleEntity getCompetitor() {
        return competitor;
    }

    public void setCompetitor(CompetitorRoleEntity competitor) {
        this.competitor = competitor;
    }
}
