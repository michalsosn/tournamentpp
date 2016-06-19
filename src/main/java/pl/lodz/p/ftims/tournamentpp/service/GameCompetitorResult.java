package pl.lodz.p.ftims.tournamentpp.service;

import pl.lodz.p.ftims.tournamentpp.entities.AccountEntity;
import pl.lodz.p.ftims.tournamentpp.entities.CompetitorRoleEntity;
import pl.lodz.p.ftims.tournamentpp.entities.GameEntity;

import java.util.List;

public class GameCompetitorResult {

    private final AccountEntity competitor1;
    private final AccountEntity competitor2;
    private final AccountEntity winner;

    public GameCompetitorResult(GameEntity game) {
        winner = game.getWinner() == null ? null : game.getWinner().getAccount();
        final List<CompetitorRoleEntity> competitors = game.getCompetitors();
        competitor1 = competitors.get(0).getAccount();
        competitor2 = competitors.get(1).getAccount();
    }

    public String getCompetitor1() {
        return competitor1.getUsername();
    }

    public String getCompetitor2() {
        return competitor2.getUsername();
    }

    public long getCompetitor1Id() {
        return competitor1.getId();
    }

    public long getCompetitor2Id() {
        return competitor2.getId();
    }

    public String getWinner() {
        return winner == null ? null : winner.getUsername();
    }

    public Long getWinnerId() {
        return winner == null ? null : winner.getId();
    }

}
