package pl.lodz.p.ftims.tournamentpp.entities;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 2016-05-09.
 */
@Entity(name = "Round")
@Table(name = "rounds")
@SequenceGenerator(name = "round_gen", sequenceName = "round_gen", allocationSize = 1)
public class RoundEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "round_gen")
    @Column(name = "round_id", nullable = false, updatable = false)
    private Long id;

    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tournament_id", nullable = false, updatable = false)
    private TournamentEntity tournament;

    @OneToMany(mappedBy = "round")
    private List<GameEntity> games = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setTournament(TournamentEntity tournament) {
        this.tournament = tournament;
    }

    public TournamentEntity getTournament() {
        return tournament;
    }

    public List<GameEntity> getGames() {
        return games;
    }
}
