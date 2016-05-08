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
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author r4pt0r
 */
@Entity(name = "Game")
@Table(name = "game")
@SequenceGenerator(name = "game_sequence", sequenceName = "game_sequence",
        allocationSize = 1)
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "game_sequence")
    @Column(name = "game_id", nullable = false, updatable = false)
    private long id;

    @Version
    @Column(name = "version")
    private long version;

    @ManyToOne
    @JoinColumn(name = "round_id")
    private RoundEntity round;

    @ElementCollection
    @JoinTable(
            name = "game_competitor",
            joinColumns = @JoinColumn(name = "game_id")
    )
    @MapKeyJoinColumn(name = "competitor_id", referencedColumnName = "role_id")
    @Column(name = "score")
    private Map<CompetitorRoleEntity, Integer> scores;

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
