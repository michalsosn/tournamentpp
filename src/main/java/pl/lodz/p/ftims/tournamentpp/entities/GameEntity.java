package pl.lodz.p.ftims.tournamentpp.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 2016-05-09.
 */
@Entity(name = "game")
@Table(name = "games")
@SequenceGenerator(name = "game_gen", sequenceName = "game_gen", allocationSize = 1)
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_gen")
    @Column(name = "game_id", nullable = false, updatable = false)
    private Long id;

    @Version
    @Column(name = "version")
    private Long version;

    @ManyToOne(optional = false)
    @JoinColumn(name = "round_id", nullable = false, updatable = false)
    private RoundEntity round;

    @OneToOne
    @JoinColumn(name = "winner", referencedColumnName = "role_id")
    private CompetitorRoleEntity winner;

    @ManyToMany
    @JoinTable(name = "game_competitor",
            joinColumns = @JoinColumn(
                    name = "game_id",
                    referencedColumnName = "game_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "role_id"
            )
    )
    private List<CompetitorRoleEntity> competitors = new ArrayList<>();

    public GameEntity() {
    }

    public GameEntity(RoundEntity round) {
        this.round = round;
    }

    public Long getId() {
        return id;
    }

    public void setRound(RoundEntity round) {
        this.round = round;
    }

    public RoundEntity getRound() {
        return round;
    }

    public void setWinner(CompetitorRoleEntity winner) {
        this.winner = winner;
    }

    public CompetitorRoleEntity getWinner() {
        return winner;
    }

    public List<CompetitorRoleEntity> getCompetitors() {
        return competitors;
    }
}
