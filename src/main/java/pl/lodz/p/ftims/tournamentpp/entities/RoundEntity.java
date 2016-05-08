/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.ftims.tournamentpp.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

/**
 *
 * @author r4pt0r
 */
@Entity(name = "Round")
@Table(name = "round")
@SequenceGenerator(name = "round_sequence", sequenceName = "round_sequence",
                   allocationSize = 1)
public class RoundEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "round_sequence")
    @Column(name = "round_id", nullable = false, updatable = false)
    private long id;

    @Version
    @Column(name = "version")
    private long version;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private TournamentEntity tournament;

    @OneToMany(mappedBy = "round")
    private List<GameEntity> games = new ArrayList<>();

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the startTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the tournament
     */
    public TournamentEntity getTournament() {
        return tournament;
    }

    /**
     * @param tournament the tournament to set
     */
    public void setTournament(TournamentEntity tournament) {
        this.tournament = tournament;
    }

    /**
     * @return the games
     */
    public List<GameEntity> getGames() {
        return games;
    }

    /**
     * @param games the games to set
     */
    public void setGames(List<GameEntity> games) {
        this.games = games;
    }
}
