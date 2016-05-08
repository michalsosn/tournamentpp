/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.ftims.tournamentpp.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author r4pt0r
 */
public class GameEntity {
    protected long id;
    protected long version;
    protected RoundEntity round;
    protected List<CompetitorRoleEntity> competitors = new ArrayList<>();
    protected Map<CompetitorRoleEntity, Integer> scores = new HashMap<>();

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
     * @return the round
     */
    public RoundEntity getRound() {
        return round;
    }

    /**
     * @param round the round to set
     */
    public void setRound(RoundEntity round) {
        this.round = round;
    }

    /**
     * @return the competitors
     */
    public List<CompetitorRoleEntity> getCompetitors() {
        return competitors;
    }

    /**
     * @param competitors the competitors to set
     */
    public void setCompetitors(List<CompetitorRoleEntity> competitors) {
        this.competitors = competitors;
    }

    /**
     * @return the scores
     */
    public Map<CompetitorRoleEntity, Integer> getScores() {
        return scores;
    }

    /**
     * @param scores the scores to set
     */
    public void setScores(Map<CompetitorRoleEntity, Integer> scores) {
        this.scores = scores;
    }
}
