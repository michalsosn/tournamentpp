package pl.lodz.p.ftims.tournamentpp.entities.builders;

import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;
import pl.lodz.p.ftims.tournamentpp.entities.RoundEntity;

import java.util.ArrayList;
import java.util.List;

public final class GameEntityBuilder {
    private RoundEntity round;
    private CompetitorRoleEntity winner;
    private List<CompetitorRoleEntity> competitors = new ArrayList<>();

    private GameEntityBuilder() {
    }

    public static GameEntityBuilder aGameEntity() {
        return new GameEntityBuilder();
    }

    public GameEntityBuilder withRound(RoundEntity round) {
        this.round = round;
        return this;
    }

    public GameEntityBuilder withWinner(CompetitorRoleEntity winner) {
        this.winner = winner;
        return this;
    }

    public GameEntityBuilder withCompetitor(CompetitorRoleEntity competitor) {
        this.competitors.add(competitor);
        return this;
    }

    public GameEntity build() {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setRound(round);
        gameEntity.setWinner(winner);
        gameEntity.getCompetitors().addAll(competitors);
        return gameEntity;
    }
}
