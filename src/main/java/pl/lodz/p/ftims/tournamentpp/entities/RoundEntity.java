package pl.lodz.p.ftims.tournamentpp.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 2016-05-09.
 */
@Entity(name = "Round")
@Table(name = "rounds")
@SequenceGenerator(name = "round_gen", sequenceName = "round_gen", allocationSize = 1)
public class RoundEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "round_gen")
    @Column(name = "round_id", nullable = false, updatable = false)
    private Long id;

    @Version
    @Column(name = "version")
    private Long version;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "tournament_id", nullable = false, updatable = false)
    private TournamentEntity tournament;

    @OneToMany(mappedBy = "round", orphanRemoval = true)
    private List<GameEntity> games = new ArrayList<>();

    public RoundEntity() {
    }

    public RoundEntity(LocalDateTime startTime, LocalDateTime endTime,
                       TournamentEntity tournament) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.tournament = tournament;
    }

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

    public boolean isFinished() {
        return games.stream().allMatch(gameEntity -> gameEntity.getWinner() != null);
    }
}
