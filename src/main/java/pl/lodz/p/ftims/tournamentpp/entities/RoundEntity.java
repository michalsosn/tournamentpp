/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.ftims.tournamentpp.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author r4pt0r
 */
public class RoundEntity {
    protected long id;
    protected long version;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected TournamentEntity tournament;
    protected List<GameEntity> games = new ArrayList<>();

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
